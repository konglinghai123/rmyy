package com.ewcms.common.utils.forbidden;

import com.ewcms.common.Constants;
import com.ewcms.common.utils.fetch.RemoteFileFetcher;
import com.google.common.collect.Lists;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 屏蔽关键词 工具类
 * 
 * @author wu_zhijun
 */
public class ForbiddenWordUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForbiddenWordUtils.class);

    /**
     * 默认的遮罩文字
     */
    private static final String DEFAULT_MASK = "***";
    /**
     * 屏蔽关键词抓取的url
     */
    private static String forbiddenWordFetchURL;

    /**
     * 屏蔽关键词抓取时间间隔 毫秒
     */
    private static int reloadInterval = 60000; //10分钟

    /**
     * 屏蔽关键词
     */
    private static List<Pattern> forbiddenWords;

    public static void setForbiddenWordFetchURL(String forbiddenWordFetchURL) {
        ForbiddenWordUtils.forbiddenWordFetchURL = forbiddenWordFetchURL;
    }

    public static void setReloadInterval(int reloadInterval) {
        ForbiddenWordUtils.reloadInterval = reloadInterval;
    }

    /**
     * 替换input中的屏蔽关键词为默认的掩码
     *
     * @param input
     * @return
     */
    public static String replace(String input) {
        return replace(input, DEFAULT_MASK);
    }

    /**
     * 将屏蔽关键词 替换为 mask
     *
     * @param input
     * @param mask
     * @return
     */
    public static String replace(String input, String mask) {
        for (int i = 0, l = forbiddenWords.size(); i < l; i++) {
            Pattern forbiddenWordPattern = forbiddenWords.get(i);
            input = forbiddenWordPattern.matcher(input).replaceAll(mask);
        }
        return input;
    }


    /**
     * 是否包含屏蔽关键词
     *
     * @param input
     * @return
     */
    public static boolean containsForbiddenWord(String input) {
        for (int i = 0, l = forbiddenWords.size(); i < l; i++) {
            Pattern forbiddenWordPattern = forbiddenWords.get(i);
            if (forbiddenWordPattern.matcher(input).find()) {
                return true;
            }
        }
        return false;
    }


    static {
    	//Java7 新特性 try-with-resources语句
    	String fileName = "forbidden.txt";
    	try (InputStream is = ForbiddenWordUtils.class.getResourceAsStream(fileName);){
    		byte[] fileCBytes;
            fileCBytes = IOUtils.toByteArray(is);
            ForbiddenWordUtils.loadForbiddenWords(fileCBytes);
    	} catch (IOException e) {
    		LOGGER.error("read forbidden file failed", e);
    	}
//    	//Java6以前的写法
//        InputStream is = null;
//        try {
//            String fileName = "forbidden.txt";
//            is = ForbiddenWordUtils.class.getResourceAsStream(fileName);
//            byte[] fileCBytes;
//            fileCBytes = IOUtils.toByteArray(is);
//            ForbiddenWordUtils.loadForbiddenWords(fileCBytes);
//        } catch (IOException e) {
//            LOGGER.error("read forbidden file failed", e);
//        } finally {
//            IOUtils.closeQuietly(is);
//        }
    }

    /**
     * 初始化远程抓取配置
     */
    public static void initRemoteFetch() {
        RemoteFileFetcher.createPeriodFetcher(
                forbiddenWordFetchURL,
                reloadInterval,
                new RemoteFileFetcher.FileChangeListener() {
                    public void fileReloaded(byte[] fileConent) throws IOException {
                        ForbiddenWordUtils.loadForbiddenWords(fileConent);
                    }
                });
    }

    private static void loadForbiddenWords(byte[] fileCBytes) throws IOException {
    	//Java7 新特性 try-with-resources语句
    	try (Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileCBytes), Constants.ENCODING));){
    		List<String> forbiddenWordsStrList = IOUtils.readLines(reader);
            forbiddenWords = Lists.newArrayList();
            for (int i = forbiddenWordsStrList.size() - 1; i >= 0; i--) {
                String forbiddenWord = forbiddenWordsStrList.get(i).trim();
                if (forbiddenWord.length() == 0 || forbiddenWord.startsWith("#")) {
                    continue;
                } else {
                    forbiddenWords.add(Pattern.compile(forbiddenWord));
                }
            }
    	} catch (Exception e) {
    		LOGGER.error("load forbidden words failed", e);
    	}
    	
//    	//Java6以前的写法
//        Reader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileCBytes), Constants.ENCODING));
//            List<String> forbiddenWordsStrList = IOUtils.readLines(reader);
//            forbiddenWords = Lists.newArrayList();
//            for (int i = forbiddenWordsStrList.size() - 1; i >= 0; i--) {
//                String forbiddenWord = forbiddenWordsStrList.get(i).trim();
//                if (forbiddenWord.length() == 0 || forbiddenWord.startsWith("#")) {
//                    continue;
//                } else {
//                    forbiddenWords.add(Pattern.compile(forbiddenWord));
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("load forbidden words failed", e);
//        } finally {
//            IOUtils.closeQuietly(reader);
//        }
    }

}
