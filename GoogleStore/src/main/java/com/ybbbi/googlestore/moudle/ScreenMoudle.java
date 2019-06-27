package com.ybbbi.googlestore.moudle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.activity.DetailActivity;
import com.ybbbi.googlestore.activity.ScreenActivity;
import com.ybbbi.googlestore.bean.AppInfo;
import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.global.OPTIONS;
import com.ybbbi.googlestore.utils.DimenUtil;

import butterknife.BindView;

/**
 * ybbbi
 * 2019-06-27 18:34
 */
public class ScreenMoudle extends BaseMoudle<AppInfo> {
    private int dp12;
    @BindView(R.id.screen_listView)
    LinearLayout screenListView;
    DetailActivity activity;
    public void setActivity(DetailActivity activity){
        this.activity=activity;
    }
    @Override
    public int getLayout() {
        return R.layout.screen;
    }

    @Override
    public void loadData(final AppInfo appInfo) {
            dp12=DimenUtil.getDimen(R.dimen.dp12);
        for (int i = 0; i < appInfo.screen.size(); i++) {

            ImageView iv=new ImageView(MyApp.context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120,200);
            params.leftMargin=(i==0?0:dp12);
            iv.setLayoutParams(params);
            final int position=i;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity, ScreenActivity.class);
                    intent.putStringArrayListExtra("url", appInfo.screen);
                    intent.putExtra("position",position);
                        activity.startActivity(intent);
                }
            });

            ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX+appInfo.screen.get(i),iv, OPTIONS.options);
            screenListView.addView(iv);
        }

    }
}
