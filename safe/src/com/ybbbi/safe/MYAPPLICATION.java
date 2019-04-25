package com.ybbbi.safe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;



import android.app.Application;

public class MYAPPLICATION extends Application {
	@Override
	public void onCreate() {
		Thread.currentThread().setUncaughtExceptionHandler(new Myhandler());
		super.onCreate();
	}
	private class Myhandler implements UncaughtExceptionHandler{

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
		try {
			ex.printStackTrace(new PrintStream(new File("mnt/sdcard/error.log")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		android.os.Process.killProcess(android.os.Process.myPid());
		}
		
	}
}
