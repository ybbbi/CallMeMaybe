package com.ybbbi.googlestore.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.adapter.CategoryAdapter;
import com.ybbbi.googlestore.bean.CategoryBean;
import com.ybbbi.googlestore.http.HttpHelper;
import com.ybbbi.googlestore.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/*
 *ybbbi
 *2019-06-19 13:43
 */public class CategoryFragment extends BaseFragment {
    private ArrayList<Object> alist=new ArrayList<Object>();
    private ListView listview;

    @Override
    public View getSuccessView() {
        listview = (ListView) View.inflate(getContext(), R.layout.category_lv, null);

        return listview;
    }

    @Override
    public void loadData() {
        HttpHelper.create().get(NetUrl.URL_CATEGORY, new HttpHelper.OnResultListener() {
            @Override
            public void onSuccess(String result) {

                stateLayout.showsuccessView();
                Gson json=new Gson();

                 ArrayList<CategoryBean> list1 = json.fromJson(result, new TypeToken<List<CategoryBean>>() {
                }.getType());
                    for(CategoryBean cate :list1){
                        alist.add(cate.title);
                        alist.addAll(cate.infos);
                    }
                    listview.setAdapter(new CategoryAdapter(alist));
            }

            @Override
            public void onFail(Exception e) {

            }
        },getActivity());

    }
}
