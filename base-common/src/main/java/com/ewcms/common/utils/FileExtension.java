package com.ewcms.common.utils;

import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author wu_zhijun
 */
public class FileExtension {

	private final static String ICON_FILE_PREFIX = "icon-file-";
	
	public static String getIconClsByFileName(String fileName){
		String extension = FilenameUtils.getExtension(fileName);
		return ICON_FILE_PREFIX + extension;
	}
	
	public static String getIconClsByExtension(String extension){
		return ICON_FILE_PREFIX + extension;
	}
	
}
