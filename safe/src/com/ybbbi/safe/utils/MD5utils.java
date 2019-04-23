package com.ybbbi.safe.utils;

import java.io.BufferedReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5utils {

	/**
	 * @param args
	 */
	
	public static String toMD5(String string) {
		
		StringBuilder sb=new StringBuilder();
		// TODO Auto-generated method stub
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(string.getBytes());
			for (int i = 0; i < digest.length; i++) {
				int result=digest[i]&255;
				String hexString = Integer.toHexString(result);
				if(hexString.length()<2){
					sb.append("0");
				}
				sb.append(hexString);
			}
			return sb.toString();
			
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(string);
		return string;
	}

}
