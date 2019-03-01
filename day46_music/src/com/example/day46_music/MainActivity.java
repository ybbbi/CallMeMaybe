package com.example.day46_music;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.day46_music.MusicPlayer.myBinder;

public class MainActivity extends Activity {
	private myBinder musicController;
	private ImageButton ib_play;
	private myConnection conn;
	private SeekBar sb_progress;
	private final int UPDATE_PROGRESS = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_PROGRESS:
				updateProgress();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ib_play = (ImageButton) findViewById(R.id.ib_play);
		Intent service = new Intent(this, MusicPlayer.class);
		conn = new myConnection();
		bindService(service, conn, BIND_AUTO_CREATE);
		startService(service);
		sb_progress = (SeekBar) findViewById(R.id.sb_progress);
		sb_progress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					musicController.seekTO(progress);
				}
			}
		});
	}

	public void playPause(View v) {

		musicController.playPause();
		update();
	}

	private void update() {
		if (musicController.isplaying()) {
			ib_play.setImageResource(R.drawable.btn_audio_pause);
			handler.sendEmptyMessage(UPDATE_PROGRESS);
		} else {
			ib_play.setImageResource(R.drawable.btn_audio_play);

		}
	}

	private void updateProgress() {
		int current = musicController.getCurrent();
		sb_progress.setProgress(current);

		handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 1000);

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(musicController!=null){
			handler.sendEmptyMessage(UPDATE_PROGRESS);
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		handler.removeCallbacksAndMessages(null);
	}

	public class myConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicController = (myBinder) service;
			update();
			sb_progress.setMax(musicController.getDuration());
			sb_progress.setProgress(musicController.getCurrent());
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onDestroy() {
		unbindService(conn);
		super.onDestroy();
	}

}
