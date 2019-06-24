package com.ybbbi.googlestore.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.adapter.HomeAdapter;
import com.ybbbi.googlestore.adapter.HomeVPAdapter;
import com.ybbbi.googlestore.adapter.MyBaseAdapter;
import com.ybbbi.googlestore.bean.HomeInfo;

import java.util.List;

/**
 * ybbbi
 * 2019-06-19 13:43
 */
public class MainFragment extends ListViewFragment<HomeInfo.ListBean> {


    private ViewPager home_viewpager_header;


    //homeAdapter = new HomeAdapter(list);
    //addHeaderView();
    // mListview.setAdapter(homeAdapter);


    /**
     * 加载数据
     *
     * @Time:2019-06-23||14:16
     */


    // HttpHelper.create().get(NetUrl.URL_HOME + list.size(), new HttpHelper.OnResultListener() {
    @Override
    protected MyBaseAdapter getAdapter() {

        return new HomeAdapter(list);
    }

    @Override
    protected String getURL() {
        return NetUrl.URL_HOME + list.size();
    }

    @Override
    protected void parseData(String result) {
        Gson json = new Gson();
        HomeInfo homeInfo = json.fromJson(result, HomeInfo.class);
        if (homeInfo != null) {
            if (homeInfo.picture != null && homeInfo.picture.size() > 0) {

                home_viewpager_header.setAdapter(new HomeVPAdapter(homeInfo.picture));
            }

            List<HomeInfo.ListBean> stringList = homeInfo.list;
            list.addAll(stringList);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    baseAdapter.notifyDataSetChanged();

                }
            });

        }
    }

    @Override
    protected void addHeader() {
        View view = View.inflate(getActivity(), R.layout.home_listview_header, null);
        home_viewpager_header = view.findViewById(R.id.listview_header_viewpager);
        mListview.addHeaderView(view);
        super.addHeader();
    }
}



