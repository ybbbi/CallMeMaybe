package com.ybbbi.button.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class myToggleButton extends View {

	private Bitmap backgroundicon;
	private Bitmap buttonicon;
	private int distance;
	private boolean isUp=false;
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public interface clicklistener{
		void click(boolean state);
	}
	private clicklistener listener;
	private String namespace="http://schemas.android.com/apk/res/com.ybbbi.button";
	public void setonClicklistener(clicklistener listener){
		this.listener=listener;
	}
	public myToggleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		int bkg = attrs.getAttributeResourceValue(namespace, "background", -1);
		int icon = attrs.getAttributeResourceValue(namespace, "icon", -1);
		boolean b = attrs.getAttributeBooleanValue(namespace, "isOn", true);
		
		if(bkg!=-1&&icon!=-1){
			setBackgroundIcon(bkg, icon);
		}
		setState(b);
		
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public myToggleButton(Context context, AttributeSet attrs) {
		this(context,attrs,-1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public myToggleButton(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public void setBackgroundIcon(int background,int icon){
		backgroundicon = BitmapFactory.decodeResource(getResources(), background);
		buttonicon = BitmapFactory.decodeResource(getResources(), icon);
		
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(backgroundicon.getWidth(), backgroundicon.getHeight());
		
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		
		super.onLayout(changed, left, top, right, bottom);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if(distance<0){
			distance=0;
		}if(distance+buttonicon.getWidth()>backgroundicon.getWidth()){
			distance=backgroundicon.getWidth()-buttonicon.getWidth();
		}
		canvas.drawBitmap(backgroundicon, 0, 0, null);
		canvas.drawBitmap(buttonicon, distance, 0, null);
		if(isUp){
			boolean b=distance>0;
			if(listener!=null){
				listener.click(b);
			}
			isUp=false;
		}
		super.onDraw(canvas);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			distance = (int) (event.getX()-buttonicon.getWidth()/2);
			break;
		case MotionEvent.ACTION_MOVE:
			distance = (int) (event.getX()-buttonicon.getWidth()/2);
			
			break;
		case MotionEvent.ACTION_UP:
			isUp=true;
			if(distance>backgroundicon.getWidth()/2-buttonicon.getWidth()/2){
				distance=backgroundicon.getWidth()-buttonicon.getWidth();
			}else{
				distance=0;
			}
			break;
		}
		invalidate();
		return true;
	}
	public void setState(boolean isOpen){
		isUp=true;
		if(isOpen){
			distance=backgroundicon.getWidth()-buttonicon.getWidth();
		}else{
			distance=0;
		}
		invalidate();
	}


}
