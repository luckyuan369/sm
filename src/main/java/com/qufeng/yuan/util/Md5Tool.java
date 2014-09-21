package com.qufeng.yuan.util;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class Md5Tool {
	
	public static String getMd5(String password){
		String str = "";
		if(password !=null && !password.equals("")){
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				BASE64Encoder base = new BASE64Encoder();
				//鍔犲瘑鍚庣殑瀛楃涓�
				str = base.encode(md.digest(password.getBytes("utf-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

}
