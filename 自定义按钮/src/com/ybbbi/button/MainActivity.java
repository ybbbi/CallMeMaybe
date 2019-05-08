package com.ybbbi.button;

import com.ybbbi.button.view.myToggleButton;
import com.ybbbi.button.view.myToggleButton.clicklistener;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

	private void init() {
		myToggleButton button = (myToggleButton) findViewById(R.id.myButton);
		
		button.setBackgroundIcon(R.drawable.slide_background2, R.drawable.slide_icon);
		button.setState(true);
		button.setonClicklistener(new clicklistener() {
			
			@Override
			public void click(boolean state) {
				// TODO Auto-generated method stub
				if(state){
					Toast.makeText(getApplicationContext(), "开启", 0).show();
				}else{
					
					Toast.makeText(getApplicationContext(), "关闭", 0).show();
				}
			}
		});
	}


}
