package com.ewcms.system.editor.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.Constants;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.LogUtils;
import com.ewcms.common.utils.MessageUtils;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.entity.TreeNode;
import com.ewcms.common.web.entity.AjaxUploadResponse;
import com.ewcms.common.web.upload.FileUploadUtils;
import com.ewcms.common.web.upload.exception.FileNameLengthLimitExceededException;
import com.ewcms.common.web.upload.exception.InvalidExtensionException;
import com.ewcms.common.web.utils.DownloadUtils;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.system.editor.utils.CompressUtils;
import com.ewcms.system.editor.utils.OnlineEditorUtils;
import com.google.common.collect.Lists;

/**
 *
 * @author wu_zhijun
 */
@Controller
@RequestMapping("/system/editor")
@RequiresPermissions("system:onlineEditor:*")
public class OnlineEditorController extends BaseController {

	private final String ROOT_DIR = "/";
	
	private final long MAX_SIZE = 20000000;//20MB
	
	private final String[] ALLOWED_EXTENSION = new String[]{
		"bmp", "gif", "jpeg", "jpg", "png",
		"pdf",
		"doc", "docx", "xls", "xlsx", "ppt", "ppx", 
		"zip", "rar",
		"jsp", "jspx", "jspf", "tag", "tld", "xml", "java", "htm", "html", "css", "js"
	};
	
	//允许多级目录创建
	private final String VALID_FILENAME_PATTERN = "[^\\s:\\*\\?\\\"<>\\|]?(\\x20|[^\\s:\\*\\?\\\"<>\\|])*[^\\s:\\*\\?\\\"<>\\|\\.]?$";
	
	@Autowired
	private ServletContext sc;
	
	@RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
	public String index(){
		return viewName("index");
	}
	
	@RequestMapping(value = "table")
    @ResponseBody
    public List<TreeNode<String>> table(@RequestParam(value = "path", required = false, defaultValue = "")String path) throws IOException{
		List<TreeNode<String>> treeNodeTables = Lists.newArrayList();
		
		String rootPath = sc.getRealPath(ROOT_DIR);
		File currentDirectory = null;
		if (EmptyUtil.isStringEmpty(path)){
			currentDirectory = new File(rootPath);
			
			TreeNode<String> currentTreeNode = OnlineEditorUtils.extractFileInfoTreeNode(currentDirectory, rootPath);
			
			List<TreeNode<String>> childNodes = Lists.newArrayList();
			
			TreeNode<String> childNode = null;
			for (File subFile : currentDirectory.listFiles()){
				childNode = OnlineEditorUtils.extractFileInfoTreeNode(subFile, rootPath);
				childNodes.add(childNode);
			}
			
			currentTreeNode.setChildren(childNodes);
			treeNodeTables.add(currentTreeNode);
		} else {
			path = URLDecoder.decode(path, Constants.ENCODING);
			currentDirectory = new File(rootPath + File.separator + path);
			
			TreeNode<String> childNode = null;
			for (File subFile : currentDirectory.listFiles()){
				childNode = OnlineEditorUtils.extractFileInfoTreeNode(subFile, rootPath);
				treeNodeTables.add(childNode);
			}
		}
		
		return treeNodeTables;
    }
	
	@RequestMapping(value = "save", method = RequestMethod.GET)
	public String showSaveFrom(@RequestParam(value = "path", required = false, defaultValue = "")String path, Model model, RedirectAttributes redirectAttributes) throws IOException{
		String rootPath = sc.getRealPath(ROOT_DIR);
		
		path = URLDecoder.decode(path, Constants.ENCODING);
		File file = new File(rootPath + File.separator + path);
		String parentPath = file.getParentFile().getAbsolutePath().replace(rootPath, "");
		
		boolean hasError = false;
		if (file.isDirectory()){
			hasError = true;
			redirectAttributes.addFlashAttribute(Constants.ERROR, path + "是目录，不能编辑！");
		}
		if (!file.exists()){
			hasError = true;
			redirectAttributes.addFlashAttribute(Constants.ERROR, path + "文件不存在，不能编辑！");
		}
		if (!file.canWrite()){
			hasError = true;
			redirectAttributes.addFlashAttribute(Constants.ERROR, path + "文件是只读的，不能编辑，请修改文件权限！");
		}
		if (!file.canRead()){
			hasError = true;
			redirectAttributes.addFlashAttribute(Constants.ERROR, path + "文件不能读取，不能编辑，请修改文件权限！");
		}
		if (hasError){
			redirectAttributes.addAttribute(path, parentPath);
			return redirectToUrl(viewName("index"));
		}
		
		String content = FileUtils.readFileToString(file);
		
		model.addAttribute("content", content);
		model.addAttribute("path", URLEncoder.encode(path, Constants.ENCODING));
		model.addAttribute("parentPath", URLEncoder.encode(parentPath, Constants.ENCODING));
		
		return viewName("edit");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestParam(value = "path")String path, @RequestParam(value = "content")String content, Model model, RedirectAttributes redirectAttributes) throws IOException {
		String rootPath = sc.getRealPath(ROOT_DIR);
		path = URLDecoder.decode(path, Constants.ENCODING);
		File file = new File(rootPath + File.separator + path);
		
		FileUtils.write(file, content);
		
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "编辑成功！");
		
		model.addAttribute("lastM", JSON.toJSONString(OnlineEditorUtils.extractFileInfoTreeNode(file, rootPath)));
		model.addAttribute("close", true);
	    
		//return showSaveFrom(path, model, redirectAttributes);
		return viewName("edit");
	}
	
	@RequestMapping("rename")
	@ResponseBody
	public AjaxResponse rename(@RequestParam(value = "path") String path, @RequestParam(value = "newName") String newName) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		String rootPath = sc.getRealPath(ROOT_DIR);
		path = URLDecoder.decode(path, Constants.ENCODING);
		
		File current = new File(rootPath + File.separator + path);
		File parent = current.getParentFile();
		
		File renameToFile = new File(parent, newName);
		boolean result = current.renameTo(renameToFile);
		if (!result){
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("名称为[" + newName + "]的文件/目录已经存在");
		} else {
			ajaxResponse.setSuccess(Boolean.TRUE);
			ajaxResponse.setMessage(newName);
		}
		
		return ajaxResponse;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam(value = "paths[]")List<String> paths) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse(Boolean.TRUE, "删除成功!");
		
		String rootPath = sc.getRealPath(ROOT_DIR);
		for (String path : paths){
			path = URLDecoder.decode(path, Constants.ENCODING);
			File current = new File(rootPath + File.separator + path);
			FileUtils.deleteQuietly(current);
		}
		
		return ajaxResponse;
	}
	
	@RequestMapping("/create/directory")
	@ResponseBody
	public AjaxResponse createDirectory(@RequestParam(value = "parentPath")String parentPath, @RequestParam(value = "name")String name) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		//删除文件路径最后的/
		name = FilenameUtils.normalizeNoEndSeparator(name);
		
		if (isValidFileName(name)){
			String rootPath = sc.getRealPath(ROOT_DIR);
			parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
			
			File parent = new File(rootPath + File.separator + parentPath);
			File currentDirectory = new File(parent, name);
			boolean result = currentDirectory.mkdirs();
			if (result){
				ajaxResponse.setSuccess(Boolean.TRUE);
				ajaxResponse.setMessage("创建成功!");
			} else {
				ajaxResponse.setSuccess(Boolean.FALSE);
				ajaxResponse.setMessage("名称为[" + name + "]的文件/目录已经存在");
			}
		} else {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("文件/目录不符合规则");
		}
		return ajaxResponse;
	}
	
	@RequestMapping("/create/file")
	@ResponseBody
	public AjaxResponse createFile(@RequestParam(value = "parentPath")String parentPath, @RequestParam(value = "name")String name) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		if (isValidFileName(name)){
			String rootPath = sc.getRealPath(ROOT_DIR);
			parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
			
			File parent = new File(rootPath + File.separator + parentPath);
			File currentFile= new File(parent, name);
			currentFile.getParentFile().mkdirs();
			boolean result = currentFile.createNewFile();
			if (result){
				ajaxResponse.setSuccess(Boolean.TRUE);
				ajaxResponse.setMessage("创建成功!");
			} else {
				ajaxResponse.setSuccess(Boolean.FALSE);
				ajaxResponse.setMessage("名称为[" + name + "]的文件/目录已经存在");
			}
		} else {
			ajaxResponse.setSuccess(Boolean.FALSE);
			ajaxResponse.setMessage("文件/目录不符合规则");
		}
		return ajaxResponse;
	}
	
	private boolean isValidFileName(String fileName) {
        return fileName.matches(VALID_FILENAME_PATTERN);
    }
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam("parentPath")String parentPath, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
		String rootPath = sc.getRealPath(ROOT_DIR);
		parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
		File parent = new File(rootPath + File.separator + parentPath);
		if (!parent.exists()){
			redirectAttributes.addFlashAttribute(Constants.ERROR, parentPath + "目录不存在!");
			redirectAttributes.addAttribute("path", "");
			return redirectToUrl(viewName("index"));
		}
		
		model.addAttribute("parentPath", parentPath);
		return viewName("upload");
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxUploadResponse upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("parentPath")String parentPath, @RequestParam("conflict")String conflict, @RequestParam(value = "files[]", required = false)MultipartFile[] files) throws UnsupportedEncodingException{
		String rootPath = sc.getRealPath(ROOT_DIR);
		parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
		File parent = new File(rootPath + File.separator + parentPath);
		
		response.setContentType("text/plain");
		
		AjaxUploadResponse ajaxUploadResponse = new AjaxUploadResponse();
		
		if (ArrayUtils.isEmpty(files)){
			return ajaxUploadResponse;
		}
		
		for (MultipartFile file : files){
			String filename = file.getOriginalFilename();
			long size = file.getSize();
			try{
				File current = new File(parent, filename);
				if (current.exists() && "ignore".equals(conflict)){
					ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.conflict.error"));
					continue;
				}
				String url = FileUploadUtils.upload(request, parentPath, file, ALLOWED_EXTENSION, MAX_SIZE, false);
				String deleteURL = viewName("/delete") + "?paths=" + URLEncoder.encode(url, Constants.ENCODING);
				
				ajaxUploadResponse.add(filename, size, url, deleteURL);
				
				continue;
			} catch (IOException e){
				LogUtils.logError("file upload error", e);
				ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.server.error"));
				continue;
			} catch (InvalidExtensionException e){
				ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.not.allow.extension"));
				continue;
			} catch (FileUploadBase.FileSizeLimitExceededException e){
				ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.exceed.maxSize"));
				continue;
			} catch (FileNameLengthLimitExceededException e){
				ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.filename.exceed.length"));
				continue;
			}
		}
		return ajaxUploadResponse;
	}
	
	@RequestMapping("download")
	public String download(HttpServletRequest request, HttpServletResponse response, @RequestParam("path")String path) throws Exception{
		String rootPath = sc.getRealPath(ROOT_DIR);
		String filePath = rootPath + File.separator + URLDecoder.decode(path, Constants.ENCODING);
		filePath = filePath.replace("\\", "/");
		DownloadUtils.download(request, response, filePath, "");
		
		return null;
	}
	
	@RequestMapping("compress")
	public String compress(@RequestParam(value = "parentPath")String parentPath, @RequestParam(value = "paths")String[] paths, RedirectAttributes redirectAttributes) throws IOException{
		AjaxResponse ajaxResponse = new AjaxResponse();
		
		String rootPath = sc.getRealPath(ROOT_DIR);
		parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
		
		Date now = new Date();
		String pattern = "yyyyMMddHHmmss";
		
		String compressPath = parentPath + File.separator + "[系统压缩]" + DateFormatUtils.format(now, pattern) + "-" + System.nanoTime() + ".zip";
		
		for (int i = 0, k = paths.length; i < k; i++){
			String path = paths[i];
			path = URLDecoder.decode(path, Constants.ENCODING);
			paths[i] = rootPath + File.separator + path;
		}
		
		try{
			CompressUtils.zip(rootPath+ File.separator + compressPath, paths);
			String msg = "压缩成功，<a href='%s/%s?path=%s' target='_blank'>点击下载</a>，下载完成后，请手工删除生成的压缩包";
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, String.format(msg, sc.getContextPath(), viewName("download"), URLEncoder.encode(compressPath, Constants.ENCODING)));
		} catch (Exception e){
			redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
		}
		redirectAttributes.addAttribute("path", URLEncoder.encode(parentPath, Constants.ENCODING));
		return redirectToUrl(viewName("index"));
	}
	
	@RequestMapping("uncompress")
	public String uncompress(@RequestParam(value = "descPath")String descPath, @RequestParam(value = "paths")String[] paths, @RequestParam(value = "conflict")String conflict, RedirectAttributes redirectAttributes) throws IOException{
		String rootPath = sc.getRealPath(ROOT_DIR);
		descPath = URLDecoder.decode(descPath, Constants.ENCODING);
		for (int i = 0, k = paths.length; i < k; i++){
			String path = paths[i];
			path = URLDecoder.decode(path, Constants.ENCODING);
			if (!path.toLowerCase().endsWith(".zip")) continue;
			paths[i] = rootPath + File.separator + path;
		}
		try{
			String descAbsolutePath = rootPath + File.separator + descPath;
			for (String path : paths){
				CompressUtils.unzip(path, descAbsolutePath, "override".equals(conflict));
			}
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "解压成功!");
		} catch (Exception e){
			redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
		}
		
		redirectAttributes.addAttribute("path", URLEncoder.encode(descPath, Constants.ENCODING));
		return redirectToUrl(viewName("index"));
	}
	
	@RequestMapping("move")
	public String move(@RequestParam(value = "descPath")String descPath, @RequestParam(value = "paths[]")String[] paths, @RequestParam(value = "conflict")String conflict, RedirectAttributes redirectAttributes) throws IOException{
		String rootPath = sc.getRealPath(ROOT_DIR);
		descPath = URLDecoder.decode(descPath, Constants.ENCODING);
		
		for (int i = 0, k = paths.length; i < k; i++){
			String path = paths[i];
			path = URLDecoder.decode(path, Constants.ENCODING);
			paths[i] = (rootPath + File.separator + path).replace("\\", "/");
		}
		
		try{
			File descPathFile = new File(rootPath + File.separator + descPath);
			for (String path : paths){
				File sourceFile = new File(path);
				File descFile = new File(descPathFile, sourceFile.getName());
				if (descFile.exists() && "ignore".equals(conflict)) continue;
				
				FileUtils.deleteQuietly(descFile);
				
				if (sourceFile.isDirectory()){
					FileUtils.moveDirectoryToDirectory(sourceFile, descPathFile, true);
				} else {
					FileUtils.moveFileToDirectory(sourceFile, descPathFile, true);
				}
			}
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "移动成功!");
		} catch(Exception e){
			redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
		}
		
		redirectAttributes.addAttribute("path", URLEncoder.encode(descPath, Constants.ENCODING));
		
		return redirectToUrl(viewName("index"));
	}
	
	@RequestMapping("copy")
	public String copy(@RequestParam(value = "descPath")String descPath, @RequestParam(value = "paths")String[] paths, @RequestParam(value = "conflict")String conflict, RedirectAttributes redirectAttributes) throws IOException{
		String rootPath = sc.getRealPath(ROOT_DIR);
		descPath = URLDecoder.decode(descPath, Constants.ENCODING);
		
		for (int i = 0, k = paths.length; i < k; i++){
			String path = paths[i];
			path = URLDecoder.decode(path, Constants.ENCODING);
			paths[i] = (rootPath + File.separator + path).replace("\\", "/");
		}
		
		try{
			File descPathFile = new File(rootPath + File.separator + descPath);
			for (String path : paths){
				File sourceFile = new File(path);
				File descFile = new File(descPathFile, sourceFile.getName());
				if (descFile.exists() && "ignore".equals(conflict)) continue;
				
				FileUtils.deleteQuietly(descFile);
				
				if (sourceFile.isDirectory()){
					FileUtils.copyDirectoryToDirectory(sourceFile, descPathFile);
				} else {
					FileUtils.copyFileToDirectory(sourceFile, descPathFile);
				}
			}
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "复制成功!");
		} catch (Exception e){
			redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
		}
		
		redirectAttributes.addAttribute("path", URLEncoder.encode(descPath, Constants.ENCODING));
		return redirectToUrl(viewName("index"));
	}
}
