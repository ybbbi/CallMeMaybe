package com.ybbbi.googlestore.moudle;

import android.widget.ImageView;
import android.widget.TextView;

import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.bean.AppInfo;

import butterknife.BindView;

/**
 * ybbbi
 * 2019-06-27 23:37
 */
public class DesMoudle extends BaseMoudle<AppInfo> {
    @BindView(R.id.des_image)
    ImageView desImage;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_tv_author)
    TextView tvTvAuthor;

    @Override
    public int getLayout() {
        return R.layout.des_detail;
    }

    @Override
    public void loadData(AppInfo appInfo) {
        tvDes.setText(appInfo.des);
        tvTvAuthor.setText(appInfo.author);
    }
}
