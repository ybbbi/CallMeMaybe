package com.ybbbi.googlestore.fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.adapter.MyBaseAdapter;
import com.ybbbi.googlestore.adapter.SubjectAdapter;
import com.ybbbi.googlestore.bean.HomeInfo;
import com.ybbbi.googlestore.bean.SubjectBean;

import java.util.ArrayList;
import java.util.List;

/*
 *ybbbi
 *2019-06-19 13:43
 */public class SubjectFragment extends ListViewFragment<SubjectBean> {


    @Override
    protected MyBaseAdapter getAdapter() {
        return new SubjectAdapter(list);
    }

    @Override
    protected String getURL() {
        return NetUrl.URL_SUBJECT + list.size();
    }

    @Override
    protected void parseData(String result) {
        Gson json = new Gson();
        ArrayList<SubjectBean> list = (ArrayList<SubjectBean>) json.fromJson(result, new TypeToken<List<SubjectBean>>() {
        }.getType());
        if (list != null) {
            this.list.addAll(list);
            baseAdapter.notifyDataSetChanged();
        }
    }
}
