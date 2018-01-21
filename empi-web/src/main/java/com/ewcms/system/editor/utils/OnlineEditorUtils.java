package com.ewcms.system.editor.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Sort;

import com.ewcms.common.Constants;
import com.ewcms.common.utils.FileExtension;
import com.ewcms.common.web.controller.entity.TreeNode;
import com.google.common.collect.Maps;

/**
 *
 * @author wu_zhijun
 */
public class OnlineEditorUtils {

	private static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final String DIRECTORY = "tree-folder";
	//private static final String CSS_FILE = "tree-file";

	private static final FileFilter DIRECTORY_FILTER = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};
	
	public static Map<String, Object> extractFileInfoAttributes(File currentFile, String rootPath) throws UnsupportedEncodingException {
		Map<String, Object> attributes = Maps.newHashMap();
		
		String currentAbsolutePath = currentFile.getAbsolutePath();
		attributes.put("path", URLEncoder.encode(currentAbsolutePath.replace(rootPath, ""), Constants.ENCODING));
		
		String parentAbsolutePath = currentFile.getParentFile().getAbsolutePath();
		attributes.put("parentPath", URLEncoder.encode(parentAbsolutePath.replace(rootPath, ""), Constants.ENCODING));
		
		attributes.put("canEdit", canEdit(currentFile.getName()));
		attributes.put("hasParent", !currentFile.getPath().equals(rootPath));
		attributes.put("isParent", hasSubFiles(currentFile));
		attributes.put("isDirectory", currentFile.isDirectory());
		attributes.put("size", currentFile.length());
		Date modifiedDate = new Date(currentFile.lastModified());
		attributes.put("lastModified", DateFormatUtils.format(modifiedDate, DATA_PATTERN));
		
		return attributes;
	}
	
	public static TreeNode<String> extractFileInfoTreeNode(File currentFile, String rootPath) throws UnsupportedEncodingException{
		Map<String, Object> attributes = extractFileInfoAttributes(currentFile, rootPath);
		
		TreeNode<String> treeNode = new TreeNode<String>();
		
		treeNode.setAttributes(attributes);
		//treeNode.setId(URLDecoder.decode(attributes.get("path").toString(), Constants.ENCODING));
		String decodePath = URLDecoder.decode(attributes.get("path").toString(), Constants.ENCODING);
		treeNode.setId(decodePath.replace(File.separator, ""));
		treeNode.setText(currentFile.getName());
		String iconCls = DIRECTORY;;
		if (!currentFile.isDirectory()){
			iconCls = FileExtension.getIconClsByFileName(currentFile.getName());
			treeNode.setState("open");
		} else{
			treeNode.setState("closed");
		}
		treeNode.setIconCls(iconCls);
		
		return treeNode;
	}

	public static TreeNode<Long> extractFileInfoTreeNode(File currentFile, String rootPath, long id, long parentId) throws UnsupportedEncodingException {
		Map<String, Object> attributes = extractFileInfoAttributes(currentFile, rootPath);
		attributes.put("pId", parentId);
		
		TreeNode<Long> treeNode = new TreeNode<Long>();
		
		treeNode.setAttributes(attributes);
		treeNode.setId(id);
		treeNode.setText(attributes.get("name").toString());
		if (attributes.get("path").equals("")) treeNode.setState("open");
		else treeNode.setState("closed");
		
		return treeNode;
	}

	private static final String[] CAN_EDIT_EXTENSION = new String[] { "js",
			"css", "html", "htm", "jsp", "jspx", "tld", "tag", "xml",
			"properties", "txt" };

	private static boolean canEdit(String name) {
		name = name.toLowerCase();
		for (String extension : CAN_EDIT_EXTENSION) {
			if (name.endsWith(extension))
				return true;
		}
		return false;
	}

	public static boolean hasParent(File currentFile, String rootPath) {
		return !currentFile.getPath().equals(rootPath);
	}

	public static boolean hasSubFiles(File file) {
		File[] subFiles = file.listFiles(DIRECTORY_FILTER);
		return subFiles != null && subFiles.length > 0;
	}

	public static void sort(final List<Map<Object, Object>> files, final Sort sort){
		Collections.sort(files, new Comparator<Map<Object, Object>>(){
			@Override
			public int compare(Map<Object, Object> o1, Map<Object, Object> o2) {
				if (sort == null) return 0;
				Sort.Order nameOrder = sort.getOrderFor("name");
				if (nameOrder != null){
					String n1 = (String) o1.get("name");
					String n2 = (String) o2.get("name");
					Boolean n1IsDirectory = (Boolean) o1.get("isDirectory");
					Boolean n2IsDirectory = (Boolean) o2.get("isDirectory");
					
					if (n1IsDirectory.equals(Boolean.TRUE) && n2IsDirectory.equals(Boolean.FALSE)){
						return -1;
					} else if (n1IsDirectory.equals(Boolean.FALSE) && n2IsDirectory.equals(Boolean.TRUE)){
						return 1;
					}
					
					if (nameOrder.getDirection() == Sort.Direction.ASC){
						return n1.compareTo(n2);
					} else {
						return -n1.compareTo(n2);
					}
				}
				
				Sort.Order lastModifiedOrder = sort.getOrderFor("lastModified");
				if (lastModifiedOrder != null){
					Long l1 = (Long) o1.get("lastModifiedForLong");
					Long l2 = (Long) o2.get("lastModifiedForLong");
					if (lastModifiedOrder.getDirection() == Sort.Direction.ASC){
						return l1.compareTo(l2);
					} else {
						return -l1.compareTo(l2);
					}
				}
				
				Sort.Order sizeOrder = sort.getOrderFor("size");
				if (sizeOrder != null){
					Long s1 = (Long) o1.get("size");
					Long s2 = (Long) o2.get("size");
					if (sizeOrder.getDirection() == Sort.Direction.ASC){
						return s1.compareTo(s2);
					} else {
						return -s1.compareTo(s2);
					}
				}
				
				return 0;
			}
		});
	}
}
