package com.ybbbi.googlestore.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.bean.SubjectBean;
import com.ybbbi.googlestore.global.OPTIONS;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ybbbi
 * 2019-06-24 15:22
 */
public class SubjectAdapter extends MyBaseAdapter<SubjectBean> {


    @BindView(R.id.subject_imageview)
    ImageView subjectImageview;
    @BindView(R.id.subject_textview)
    TextView subjectTextview;

    public SubjectAdapter(ArrayList<SubjectBean> list) {
        super(list);
    }

    @Override
    public void setLayoutValue(SubjectBean subjectBean, Object holder, int position) {
        SubHolder sholder= (SubHolder) holder;
        sholder.subjectTextview.setText(subjectBean.des);
        ImageLoader.getInstance().displayImage(NetUrl.URL_IMAGE_PREFIX+subjectBean.url,sholder.subjectImageview, OPTIONS.options);
    }

    @Override
    public int bindLayout(int position) {
        return R.layout.subject_layout;
    }

    @Override
    public Object createHolder(View convertView,int position) {
        return new SubHolder(convertView);
    }

    static
    class SubHolder {
        @BindView(R.id.subject_imageview)
        ImageView subjectImageview;
        @BindView(R.id.subject_textview)
        TextView subjectTextview;

        SubHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
