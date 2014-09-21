package com.qufeng.yuan.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
/**
 * 瀵瑰睘鎬ф枃浠舵搷浣滅殑宸ュ叿绫�
 * 鑾峰彇锛屾柊澧烇紝淇敼
 * 娉ㄦ剰锛�	浠ヤ笅鏂规硶璇诲彇灞炴�ф枃浠朵細缂撳瓨闂,鍦ㄤ慨鏀瑰睘鎬ф枃浠舵椂锛屼笉璧蜂綔鐢紝
 *銆�InputStream in = PropertiesUtils.class.getResourceAsStream("/config.properties");
 *銆�瑙ｅ喅鍔炴硶锛�
 *銆�String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
 * @author lanyuan
 * 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
public class PropertiesUtils {
	/**
	 * 鑾峰彇灞炴�ф枃浠剁殑鏁版嵁 鏍规嵁key鑾峰彇鍊�
	 * @param fileName 鏂囦欢鍚嶃��(娉ㄦ剰锛氬姞杞界殑鏄痵rc涓嬬殑鏂囦欢,濡傛灉鍦ㄦ煇涓寘涓嬶紟璇锋妸鍖呭悕鍔犱笂)
	 * @param key
	 * @return
	 */
	public static String findPropertiesKey(String key) {
		
		try {
			Properties prop = getProperties();
			return prop.getProperty(key);
		} catch (Exception e) {
			return "";
		}
		
	}

	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream in = PropertiesUtils.class
				.getResourceAsStream("/config.properties");
		try {
			prop.load(in);
			Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
				System.err.println((e.getKey().toString() + "" + e.getValue()
						.toString()));
			}
		} catch (Exception e) {
			
		}
	}

	/**
	 * 杩斿洖銆�Properties
	 * @param fileName 鏂囦欢鍚嶃��(娉ㄦ剰锛氬姞杞界殑鏄痵rc涓嬬殑鏂囦欢,濡傛灉鍦ㄦ煇涓寘涓嬶紟璇锋妸鍖呭悕鍔犱笂)
	 * @param 
	 * @return
	 */
	public static Properties getProperties(){
		Properties prop = new Properties();
		String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
		//浠ヤ笅鏂规硶璇诲彇灞炴�ф枃浠朵細缂撳瓨闂
//		InputStream in = PropertiesUtils.class
//				.getResourceAsStream("/config.properties");
		try {
			InputStream in =new BufferedInputStream(new FileInputStream(savePath));  
			prop.load(in);
		} catch (Exception e) {
			return null;
		}
		return prop;
	}
	/**
	 * 鍐欏叆properties淇℃伅
	 * 
	 * @param key
	 *            鍚嶇О
	 * @param value
	 *            鍊�
	 */
	public static void modifyProperties(String key, String value) {
		try {
			// 浠庤緭鍏ユ祦涓鍙栧睘鎬у垪琛紙閿拰鍏冪礌瀵癸級
			Properties prop = getProperties();
			prop.setProperty(key, value);
			String path = PropertiesUtils.class.getResource("/config.properties").getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
		}
	}
}
