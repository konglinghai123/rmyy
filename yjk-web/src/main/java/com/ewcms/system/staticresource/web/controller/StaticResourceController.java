package com.ewcms.system.staticresource.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.utils.FileExtension;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.entity.TreeNode;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.system.staticresource.utils.YuiCompressorUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 静态资源版本控制，所有需要版本控制的静态资源，以.jspf为后缀，放在webapp/WEB-INF/views/jspf下
 * 这样即可扫描这些文件，实施版本控制
 * 
 * @author wu_zhijun
 */
@Controller
@RequestMapping(value = "/system/staticresource")
@RequiresPermissions("system:staticResource:*")
public class StaticResourceController extends BaseController{
	
	private final String versionResourcePath = "/WEB-INF/views/jspf";
	private final Pattern scriptPattern = Pattern.compile("(.*<script.* src=[\'\"])(.*?)(\\??)(\\d*)([\'\"].*>.*)", Pattern.CASE_INSENSITIVE);
	private final Pattern linkPattern = Pattern.compile("(.*<link.* href=[\'\"])(.*?)(\\??)(\\d*)([\'\"].*>.*)", Pattern.CASE_INSENSITIVE);
	
	@Autowired
	private ServletContext sc;
	
	@RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
	public String index(Model model) throws IOException{
		//String realPath = sc.getRealPath(versionResourcePath);
		//model.addAttribute("resources", findStaticResources(realPath));
		return viewName("index");
	}
	
	@RequestMapping(value = "table")
    @ResponseBody
    public List<TreeNode<String>> table() throws IOException{
		String realPath = sc.getRealPath(versionResourcePath);
    	return findStaticResourcesTable(realPath);
    }
	
	@RequestMapping(value = "incVersion", method = RequestMethod.POST)
	@ResponseBody
	public String incVersion(@RequestParam("fileName")String fileName, @RequestParam("content")String content, @RequestParam("newVersion")String newVersion) throws IOException{
		String realPath = sc.getRealPath(versionResourcePath);
		return versionedStaticResourceContent(realPath + fileName, content, newVersion);
	}
	
	@RequestMapping(value = "batchIncVersion", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse batchIncVersion(@RequestParam("fileNames[]")String[] fileNames, @RequestParam("contents[]")String[] contents, @RequestParam("newVersions[]")String[] newVersions) throws IOException{
		String realPath = sc.getRealPath(versionResourcePath);
		for (int i = 0, k = fileNames.length; i < k; i++){
			versionedStaticResourceContent(realPath + fileNames[i], contents[i], newVersions[i]);
		}
		return new AjaxResponse();
	}
	
	@RequestMapping(value = "clearVersion", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse clearVersion(@RequestParam("fileNames[]")String[] fileNames, @RequestParam("contents[]")String[] contents) throws IOException{
		String realPath = sc.getRealPath(versionResourcePath);
		for (int i = 0, k = fileNames.length; i <k; i++){
			versionedStaticResourceContent(realPath + fileNames[i], contents[i], null);
		}
		return new AjaxResponse();
	}
	
	@RequestMapping("compress")
	@ResponseBody
	public AjaxResponse compress(@RequestParam("fileName")String fileName, @RequestParam("content")String content){
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		String rootRealPath = sc.getRealPath("/WEB-INF");
		String versionedResourceRealPath = sc.getRealPath(versionResourcePath);
		
		try{
			String minFilePath = compressStaticResource(rootRealPath, versionedResourceRealPath + fileName, content);
			ajaxResponse.setMessage("压缩成功，压缩好的文件为: " + minFilePath);
		} catch (Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("压缩失败: " + e.getMessage());
		}
		return ajaxResponse;
	}
	
	@RequestMapping("batchCompress")
	@ResponseBody
	public AjaxResponse bathCompress(@RequestParam("fileNames[]")String[] fileNames, @RequestParam("contents[]")String[] contents) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse();

		String rootRealPath = sc.getRealPath("/WEB-INF");
		String versionedResourceRealPath = sc.getRealPath(versionResourcePath);
		
		StringBuilder success = new StringBuilder();
		StringBuilder error = new StringBuilder();
		
		for (int i = 0, k = fileNames.length; i < k; i++){
			try{
				String fileName = fileNames[i];
				String content = contents[i];
				String minFilePath = compressStaticResource(rootRealPath, versionedResourceRealPath + fileName, content);
				success.append("压缩成功， 压缩好的文件为: " + minFilePath +"<br/>");
			} catch (Exception e){
				error.append("压缩失败: " + e.getMessage() + "<br/>");
			}
		}
		
		ajaxResponse.setMessage(success.insert(0, "成功的压缩: <br/>").append("<br/>失败的压缩: <br/>").append(error).toString());
		return ajaxResponse;
	}
	
	@RequestMapping("switch")
	@ResponseBody
	public Object switchStaticResource(@RequestParam("fileName")String fileName, @RequestParam("conent")String content, @RequestParam(value = "min", required = false, defaultValue = "false")boolean isMin){
		Map<String, Object> data = Maps.newHashMap();
		data.put("msg", "切换成功");
		data.put("success", true);
		
		String rootRealPath = sc.getRealPath("/WEB-INF");
		String versionedResourceRealPath = sc.getRealPath(versionResourcePath);
		
		try{
			StaticResource resource = switchStaticResourceContent(rootRealPath, versionedResourceRealPath, fileName, content, isMin);
			data.put("content", resource.getContent());
			data.put("url", resource.getUrl());
			return data;
		} catch(Exception e){
			data.put("msg", "切换失败: " + e.getMessage());
			data.put("success", false);
			return data;
		}
	}
	
	@RequestMapping("batchSwitch")
	@ResponseBody
	public AjaxResponse batchSwitchStaticResource(@RequestParam("fileNames[]")String[] fileNames, @RequestParam("contents[]")String[] contents, @RequestParam(value = "min", required = false, defaultValue = "false")boolean isMin) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse();

		String rootRealPath = sc.getRealPath("/WEB-INF");
		String versionedResourceRealPath = sc.getRealPath(versionResourcePath);
		
		StringBuilder success = new StringBuilder();
		StringBuilder error = new StringBuilder();
		
		for (int i = 0, k = fileNames.length; i < k; i++){
			try{
				String fileName = fileNames[i];
				String content = contents[i];
				StaticResource resource = switchStaticResourceContent(rootRealPath, versionedResourceRealPath, fileName, content, isMin);
				success.append("切换成功，切换到的文件为: " + resource.getUrl() + "<br/>");
			} catch (Exception e){
				error.append("切换失败: " + e.getMessage() + "<br/>");
			}
		}
		
		ajaxResponse.setMessage(success.insert(0, "成功的切换: <br/>").append("<br/>失败的切换: <br/>").append(error).toString());
		return ajaxResponse;
	}
	
	private StaticResource switchStaticResourceContent(String rootRealPath, String versionedResourceRealPath, String fileName, String content, boolean isMin) throws IOException{
		StaticResource resource = extractResource(fileName, content);
		String filePath = resource.getUrl();
		filePath = filePath.replace("${ctx}", rootRealPath);
		if (isMin){
			File file = new File(YuiCompressorUtils.getCompressFileName(filePath));
			if (!file.exists()) throw new RuntimeException("请先压缩文件: " + resource.getUrl());
		} else {
			File file = new File(YuiCompressorUtils.getNoneCompressFileName(filePath));
			if (!file.exists()) throw new RuntimeException("没有压缩文件对应的非压缩版: " + resource.getUrl());
		}
		
		content = StringEscapeUtils.unescapeXml(content);
		File file = new File(versionedResourceRealPath + fileName);
		List<String> contents = FileUtils.readLines(file);
		for (int i = 0, k = contents.size(); i < k; i++){
			String fileContent = contents.get(i);
			if (content.equals(fileContent)){
				Matcher matcher = scriptPattern.matcher(content);
				if (!matcher.matches()) matcher = linkPattern.matcher(content);
				String newUrl = isMin ? YuiCompressorUtils.getCompressFileName(resource.getUrl()) : YuiCompressorUtils.getNoneCompressFileName(resource.getUrl());
				
				content = matcher.replaceAll("$1" + Matcher.quoteReplacement(newUrl) + "$3$4$5");
				contents.set(i, content);
				
				resource.setContent(content);
				resource.setUrl(newUrl);
				
				break;
			}
		}
		FileUtils.writeLines(file, contents);
		return resource;
	}
	
	private String compressStaticResource(String rootRealPath, String includeFilePath, String content){
		StaticResource resource = extractResource(includeFilePath, content);
		String filePath = resource.getUrl();
		filePath = filePath.replace("${ctx}", rootRealPath);
		
		if (YuiCompressorUtils.hasCompress(filePath)) throw new RuntimeException("[" + filePath + "]文件已经是压缩过的了，不需要再压缩了");
		
		if (filePath.startsWith("http://")) throw new RuntimeException("[" + filePath + "]文件是互联网上的，无法压缩");
		
		String minFilePath = YuiCompressorUtils.compress(filePath);
		return minFilePath.replace(rootRealPath, "${ctx}");
	}
	
	private String versionedStaticResourceContent(String fileRealPath, String content, String newVersion) throws IOException{
		content = StringEscapeUtils.unescapeXml(content);
		if (newVersion != null && newVersion.equals("1")){
			newVersion = "?" + newVersion;
		}
		
		File file = new File(fileRealPath);
		List<String> contents = FileUtils.readLines(file);
		
		for (int i = 0, k = contents.size(); i < k; i++){
			String fileContent = contents.get(i);
			if (content.equals(fileContent)){
				Matcher matcher = scriptPattern.matcher(content);
				if (!matcher.matches()) matcher = linkPattern.matcher(content);
				
				if (newVersion == null) {//删除版本号
					content = matcher.replaceAll("$1$2$5");
				} else {
					content = matcher.replaceAll("$1$2$3" + newVersion + "$5");
				}
				contents.set(i, content);
				break;
			}
		}
		FileUtils.writeLines(file, contents);
		
		return content;
	}
	
	private List<TreeNode<String>> findStaticResourcesTable(String realPath) throws IOException{
		List<TreeNode<String>> treeNodes = Lists.newArrayList();
		
		final int realPathLength = realPath.length();
		Collection<File> files = FileUtils.listFiles(new File(realPath), new String[]{"jspf"}, true);
		
		for (File file :files){
			String fileName = file.getAbsolutePath().substring(realPathLength);
			
			TreeNode<String> rootNode = new TreeNode<String>();
			rootNode.setId(fileName);
			rootNode.setText(fileName.substring(1));
			rootNode.setState("open");
			
			List<String> contents = FileUtils.readLines(file);
			List<TreeNode<String>> childrenNodes = Lists.newArrayList();
			
			for (String content : contents){
				if (!StringUtils.isEmpty(content)){
					StaticResource resource = extractResource(fileName, content);
					if (resource != null){
						Map<String, Object> attributes = Maps.newHashMap();
				        
						String url = resource.getUrl();
						
				        attributes.put("content", resource.getContent());
				        attributes.put("url", url);
				        attributes.put("version", resource.getVersion());
				        
						TreeNode<String> childrenNode = new TreeNode<String>();
						
						childrenNode.setAttributes(attributes);
						childrenNode.setId(url.substring(url.lastIndexOf("/") + 1));
						childrenNode.setText(url.substring(url.lastIndexOf("/") + 1));
						childrenNode.setIconCls(FileExtension.getIconClsByFileName(childrenNode.getText()));
						childrenNode.setState("open");
						
						childrenNodes.add(childrenNode);
					}
				}
			}
			rootNode.setChildren(childrenNodes);
			
			treeNodes.add(rootNode);
		}
		
		return treeNodes;
	}
	
	private StaticResource extractResource(String fileName, String content){
		Matcher matcher = scriptPattern.matcher(content);
		if (!matcher.matches()) matcher = linkPattern.matcher(content);
		if (!matcher.matches()) return null;
		
		String url = matcher.group(2);
		String version = "";
		version = matcher.group(4);
		
		StaticResource resource = new StaticResource(fileName, content);
		resource.setUrl(url);
		resource.setVersion(version);
		
		return resource;
	}
	
	public static class StaticResource{
		private String fileName;
		private String content;
		private String url;
		private String version;
		
		private StaticResource(String fileName, String content){
			this.fileName = fileName;
			this.content = content;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
		
		@Override
		public String toString() {
			return "StaticResource{" +
                    "fileName='" + fileName + "\'" +
                    ", content='" + content + "\'" +
                    ", url='" + url + "\'" +
                    ", version=" + version +
                    "}";
		}
	}
}