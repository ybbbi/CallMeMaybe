package com.example.day32_login;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;

public class StreamTools {
public static String readStream(InputStream in) throws Exception{
	ByteArrayOutputStream baos=new ByteArrayOutputStream();
	int len;
	byte arr[]=new byte[1024];
	while((len=in.read(arr))!=-1){
		baos.write(arr,0,len);
	}
	String content= new String(baos.toByteArray());
	return content;
}
}
