package com.ybbbi.googlestore.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shitou.googleplay.lib.randomlayout.StellarMap;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.global.MyApp;
import com.ybbbi.googlestore.http.HttpHelper;
import com.ybbbi.googlestore.utils.ColorUtil;
import com.ybbbi.googlestore.utils.DimenUtil;
import com.ybbbi.googlestore.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *ybbbi
 *2019-06-19 13:43
 */public class RecommendFragment extends BaseFragment {


    private StellarMap stellarMap;
    private ArrayList<String> list;

    @Override
    public View getSuccessView() {
        stellarMap = new StellarMap(getContext());
        int padding = DimenUtil.getDimen(R.dimen.dp15);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        return stellarMap;
    }

    @Override
    public void loadData() {
        HttpHelper.create().get(NetUrl.URL_RECOMMEND, new HttpHelper.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                stateLayout.showsuccessView();
                Gson json = new Gson();
                list = json.fromJson(result, new TypeToken<List<String>>() {
                }.getType());
                stellarMap.setAdapter(new StellarMap.Adapter() {
                    /**
                     *组的个数（界面）
                     *@Time:2019-06-24||18:12
                     */
                    @Override
                    public int getGroupCount() {
                        return list.size() / getCount(0);
                    }

                    /**
                     *一个界面中有几个子View
                     *@Time:2019-06-24||18:06
                     */
                    @Override
                    public int getCount(int i) {
                        return 11;
                    }

                    /**
                     * 返回每个子view
                     * @param i 当前是第几组
                     * @param i1 当前组中的索引
                     * @param view
                     * @return
                     */
                    @Override
                    public View getView(int i, int i1, View view) {
                        final TextView tv=new TextView(getContext());
                        tv.setText(list.get(i*getCount(i)+i1));
                        Random random = new Random();
                        tv.setTextSize(random.nextInt(10)+12);
                        tv.setTextColor(ColorUtil.randomColor());
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showToast(tv.getText().toString());
                            }
                        });
                        return tv;
                    }

                    /**
                     *该方法一般不用
                     *@Time:2019-06-24||18:05
                     */
                    @Override
                    public int getNextGroupOnPan(int i, float v) {
                        return 0;
                    }

                    /**
                     *当缩放动画完毕后，下一组数据是哪一组
                     *@Time:2019-06-24||18:05
                     */
                    @Override
                    public int getNextGroupOnZoom(int i, boolean b) {
                        i++;
                        i %= getGroupCount();
                        return i;
                    }
                });
                stellarMap.setGroup(0,true);
                stellarMap.setRegularity(4,5);
            }


            @Override
            public void onFail(Exception e) {
                stateLayout.showerrorView();
            }
        }, getActivity());
    }
}
