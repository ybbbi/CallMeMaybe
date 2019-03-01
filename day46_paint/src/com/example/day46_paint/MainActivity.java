package com.example.day46_paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "����ɹ�", Toast.LENGTH_SHORT)
					.show();

		}
	};

	private ImageView im_paint;
	private Canvas canvas;
	private Paint paint;
	private Bitmap copybm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		im_paint = (ImageView) findViewById(R.id.im_paint);
		// ������ͼ���س���
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.paint);
		// ����ͼƬ����
		copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
				bitmap.getConfig());
		canvas = new Canvas(copybm);
		paint = new Paint();
		canvas.drawBitmap(bitmap, new Matrix(), paint);

		im_paint.setImageBitmap(copybm);
		// ��imageView����һ����������
		im_paint.setOnTouchListener(new OnTouchListener() {

			private float startY;
			private float startX;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				switch (action) {
				case MotionEvent.ACTION_DOWN:
					System.out.println("����");
					// ��¼���µ�λ��
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					System.out.println("�ƶ�");

					float stopX = event.getX();
					float stopY = event.getY();
					// ʹ��canvas����
					canvas.drawLine(startX, startY, stopX, stopY, paint);

					startX = stopX;
					startY = stopY;
					// ���޸ĵ�ͼƬ���ص�ҳ����
					im_paint.setImageBitmap(copybm);

					break;
				case MotionEvent.ACTION_UP:

					System.out.println("�ɿ�");
					break;
				}

				return true;
			}
		});

	}

	public void color(View v) {
		paint.setColor(Color.RED);
	}

	public void width(View v) {
		paint.setStrokeWidth(6f);

	}

	public void save(View v) {
		try {
			File file = new File(Environment.getExternalStorageDirectory(),
					"save.png");
			CompressFormat format = CompressFormat.PNG;
			FileOutputStream stream = new FileOutputStream(file);

			copybm.compress(format, 100, stream);
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
			intent.setData(Uri.fromFile(Environment
					.getExternalStorageDirectory()));
			sendBroadcast(intent);

			Message msg = Message.obtain();

			handler.sendMessage(msg);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
