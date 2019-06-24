package com.ybbbi.googlestore.fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.adapter.HomeAdapter;
import com.ybbbi.googlestore.adapter.MyBaseAdapter;
import com.ybbbi.googlestore.bean.HomeInfo;

import java.util.ArrayList;
import java.util.List;

/*
 *ybbbi
 *2019-06-19 13:43
 */public class GameFragment extends ListViewFragment<HomeInfo.ListBean> {




    @Override
    protected MyBaseAdapter getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    protected String getURL() {
        return NetUrl.URL_GAME+list.size();
    }

    @Override
    protected void parseData(String result) {
        Gson json = new Gson();
        ArrayList<HomeInfo.ListBean> homeInfo = json.fromJson(result, new TypeToken<List<HomeInfo.ListBean>>() {
        }.getType());
        if (homeInfo != null) {
            list.addAll(homeInfo);
            baseAdapter.notifyDataSetChanged();
        }
    }
}
