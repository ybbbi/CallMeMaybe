package com.ybbbi.googlestore.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.http.HttpHelper;
import com.ybbbi.googlestore.utils.ColorUtil;
import com.ybbbi.googlestore.utils.DrawableUtil;
import com.ybbbi.googlestore.utils.ToastUtil;
import com.ybbbi.googlestore.view.FlowView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/*
 *ybbbi
 *2019-06-19 13:43
 */public class HotFragment extends BaseFragment {
    private TagFlowLayout flowLayout;


    private FlowView flowView;
    ScrollView scrollView;

    @Override
    public View getSuccessView() {
        /*scrollView=new ScrollView(getContext());
        flowLayout = new TagFlowLayout(getContext());
        flowLayout.setPadding(15,15,15,15);
        scrollView.addView(flowLayout);

        return scrollView;*/
        scrollView=new ScrollView(getContext());
        flowView = new FlowView(getContext());
        flowView.setPadding(15, 15, 15, 15);
        scrollView.addView(flowView);
        return scrollView;
    }

    @Override
    public void loadData() {
        HttpHelper.create().get(NetUrl.URL_HOT, new HttpHelper.OnResultListener() {

            private ArrayList<String> list;

            @Override
            public void onSuccess(String result) {
                stateLayout.showsuccessView();
                Gson json = new Gson();
                list = json.fromJson(result,
                        new TypeToken<List<String>>() {
                        }.getType());

               /*flowLayout.setAdapter(new TagAdapter<String>(list) {
                   @Override
                   public View getView(FlowLayout parent, int position, String s) {
                         final TextView textView=new TextView(getContext());
                       textView.setText(s);
                       textView.setTextSize(16);
                       textView.setTextColor(Color.WHITE);
                       textView.setBackgroundColor(ColorUtil.randomColor());
                       textView.setPadding(12,6,12,6);

                       return textView;
                   }


               });
               flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                   @Override
                   public boolean onTagClick(View view, int position, FlowLayout parent) {
                       ToastUtil.showToast(list.get(position));
                       return true;
                   }
               });*/
                for (int i = 0; i < list.size(); i++) {
                    final TextView textView = new TextView(getContext());
                    textView.setText(list.get(i));
                    textView.setTextSize(16);
                    textView.setTextColor(Color.WHITE);

                    textView.setBackground(DrawableUtil.createSelector(DrawableUtil.createShape(),DrawableUtil.createShape()));
                    textView.setPadding(8, 3, 8, 3);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.showToast(textView.getText().toString());
                        }
                    });
                    flowView.addView(textView);
                }

            }

            @Override
            public void onFail(Exception e) {
                stateLayout.showerrorView();
            }
        }, getActivity());
    }
}
