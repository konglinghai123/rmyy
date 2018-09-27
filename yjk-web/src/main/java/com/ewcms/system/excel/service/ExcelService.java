package com.ewcms.system.excel.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.security.user.entity.User;
import com.ewcms.system.editor.utils.CompressUtils;

/**
 *
 * @author wu_zhijun
 */
@Service
public class ExcelService {

	private final Logger log = LoggerFactory.getLogger(ExcelService.class);
	
	private int batchSize = 1000;
	private int pageSize = 1000;
	
	private final int MAX_EXPORT_FILE_SIZE = 10 * 10 * 1024;
	
	private final String storePath = "upload/excel";
	private final String EXPROT_FILENAME_PREFIX = "excel";
	
	private String compressAndDeleterOriginal(final String fileName){
		String newFileName = FilenameUtils.removeExtension(fileName) + ".zip";
		compressAndDeleterOriginal(newFileName, fileName);
		return newFileName;
	}
	
	private void compressAndDeleterOriginal(final String newFileName, final String... needCompressFilenames){
		CompressUtils.zip(newFileName, needCompressFilenames);
		for (String needCompressFilename : needCompressFilenames){
			FileUtils.deleteQuietly(new File(needCompressFilename));
		}
	}
	
	private boolean needCompress(final File file){
		return file.length() > MAX_EXPORT_FILE_SIZE;
	}
	
	private String generateFilename(final User user, final String contextRootPath, final String extension){
		return generateFilename(user, contextRootPath, null, extension);
	}
	
	private String generateFilename(final User user, final String contextRootPath, Integer index, final String extension){
		String path = FilenameUtils.concat(contextRootPath, storePath);
		path = FilenameUtils.concat(path, user.getUsername());
		path = FilenameUtils.concat(path, EXPROT_FILENAME_PREFIX + "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + (index != null ? ("_" + index) : "") + "." + extension);
		File file = new File(path);
		if (!file.exists()){
			File parentFile = file.getParentFile();
			if (!parentFile.exists()){
				parentFile.mkdirs();
			}
			return path;
		}
		return generateFilename(user, contextRootPath, extension);
	}
}
