package com.ewcms.system.staticresource.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.ewcms.common.Constants;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * 压缩CSS/JS等静态文件的工具类，读取/WEB-INF/static目录下的文件和目录
 *
 * @author wu_zhijun
 */
public class YuiCompressorUtils {

	/**
	 * 压缩CSS/JS文件，自动生成压缩文件在当前目录下并以.min.css/.min.js结尾
	 * 
	 * @param fileName 文件名
	 * @return 压缩后的文件名
	 */
	public static String compress(final String fileName){
		String minFileName = null;
		final String extension = FilenameUtils.getExtension(fileName);
		if ("js".equalsIgnoreCase(extension)){
			minFileName = fileName.substring(0, fileName.length() - 3) + ".min.js";
		} else if ("css".equals(extension)){
			minFileName = fileName.substring(0, fileName.length() - 4) + ".min.css";
		} else {
			throw new RuntimeException("file type only is css/js, but was fileName : " + fileName + ", extension : " + extension);
		}
		Reader in = null;
		Writer out = null;
		try{
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Constants.ENCODING));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(minFileName), Constants.ENCODING));
			
			if ("js".equals(extension)){
				CustomErrorReporter errorReporter = new CustomErrorReporter();
				
				JavaScriptCompressor compressor = new JavaScriptCompressor(in, errorReporter);
				compressor.compress(out, 10, true, false, false, false);
				
				if (errorReporter.hasError()){
					throw new RuntimeException(errorReporter.getErrorMessage());
				}
			} else if ("css".equals(extension)){
				CssCompressor compressor = new CssCompressor(in);
				compressor.compress(out, 10);
			}
		} catch (Exception e){
			//如果发生异常， 直接做个副本，防止加载css/jss出错
			try{
				FileUtils.copyFile(new File(fileName), new File(minFileName));
			} catch (IOException e1){
				throw new RuntimeException("compress error : " + e1.getMessage(), e1);
			}
			throw new RuntimeException("compress error : " + e.getMessage(), e);
		} finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		
		if (FileUtils.sizeOf(new File(minFileName)) == 0){
			try{
				FileUtils.copyFile(new File(fileName), new File(minFileName));
			} catch (IOException e){
				throw new RuntimeException("compress error : " + e.getMessage(), e);
			}
		}
		
		return minFileName;
	}
	
	public static boolean hasCompress(String fileName){
		return fileName.toLowerCase().endsWith(".min.js") || fileName.endsWith(".min.css");
	}
	
	public static String getCompressFileName(String fileName){
		if (hasCompress(fileName)) return fileName;
		
		String compressFileName = null;
		final String extension = FilenameUtils.getExtension(fileName);
		if ("js".equalsIgnoreCase(extension)){
			compressFileName = fileName.substring(0, fileName.length() - 3) + ".min.js";
		} else if ("css".equals(extension)){
			compressFileName = fileName.substring(0, fileName.length() - 4) + ".min.css";
		}
		return compressFileName;
	}
	
	public static String getNoneCompressFileName(String fileName){
		if (hasCompress(fileName)) return fileName;
		
		String noneCompressFileName = null;
		final String extension = FilenameUtils.getExtension(fileName);
		if ("js".equalsIgnoreCase(extension)){
			noneCompressFileName = fileName.substring(0, fileName.length() - 7) + ".js";
		} else if ("css".equals(extension)){
			noneCompressFileName = fileName.substring(0, fileName.length() - 8) + ".css";
		}
		return noneCompressFileName;
	}
	
	private static class CustomErrorReporter implements ErrorReporter{
		
		private StringBuilder error = new StringBuilder();
		
		@Override
		public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
			if (line < 0){
				error.append("\n[ERROR] " + message);
			} else {
				error.append("\n[ERROR] " + line + " : " + lineOffset + " : " + message);
			}
		}

		@Override
		public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
			error(message, sourceName, line, lineSource, lineOffset);
			return new EvaluatorException(message);
		}

		@Override
		public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
			if (line < 0){
				error.append("\n[WARNING] " + message);
			} else {
				error.append("\n[WARNING] " + line + " : " + lineOffset + " : " + message);
			}
		}
		
		public boolean hasError(){
			return error.length() > 0;
		}
		
		public String getErrorMessage(){
			return error.toString();
		}
	}
}
