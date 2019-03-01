package com.example.day46_music;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayer extends Service {

	private MediaPlayer player=new MediaPlayer();
	private String path="/data/data/jojo.mp3";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new myBinder();
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	
		try {
			player.setDataSource(path);
			player.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public class myBinder extends Binder{
		public int getDuration(){
			return player.getDuration();
			
		}
		public int getCurrent(){
			return player.getCurrentPosition();
		}
		public void playPause(){
			if(player.isPlaying()){
				player.pause();
			}else{
				player.start();
			}
		}
		public boolean isplaying(){
			return player.isPlaying();
		}
		public void  seekTO(int msec){
			player.seekTo(msec);
		}
	}
	
}
