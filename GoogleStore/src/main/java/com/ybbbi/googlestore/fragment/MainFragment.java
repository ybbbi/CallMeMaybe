package com.ybbbi.googlestore.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ybbbi.googlestore.NetURL.NetUrl;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.adapter.HomeAdapter;
import com.ybbbi.googlestore.bean.HomeInfo;
import com.ybbbi.googlestore.http.HttpHelper;
import com.ybbbi.googlestore.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ybbbi
 * 2019-06-19 13:43
 */
public class MainFragment extends BaseFragment {

    private ArrayList<HomeInfo.ListBean> list = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    public View getSuccessView() {
        View inflateView = View.inflate(getContext(), R.layout.mainfragment, null);
        ListView mListview = inflateView.findViewById(R.id.mainf_listview);
        smartRefreshLayout = inflateView.findViewById(R.id.mainf_smartrefresh);
        homeAdapter = new HomeAdapter(list);
        mListview.setAdapter(homeAdapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                homeAdapter.notifyDataSetChanged();
                loadData();
                smartRefreshLayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
                smartRefreshLayout.finishLoadMore();

            }
        });

        return inflateView;

    }
    /**
     *加载数据
     *@Time:2019-06-23||14:16
     */
    @Override
    public void loadData() {


        HttpHelper.create().get(NetUrl.URL_HOME+list.size(), new HttpHelper.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                stateLayout.showsuccessView();
                Gson json = new Gson();
                HomeInfo homeInfo = json.fromJson(result, HomeInfo.class);
                if (homeInfo != null) {

                    List<HomeInfo.ListBean> stringList = homeInfo.list;
                    list.addAll(stringList);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            homeAdapter.notifyDataSetChanged();

                        }
                    });

                }

            }

            @Override
            public void onFail(Exception e) {
                LogUtil.e("失败");

            }
        }, getActivity());
    }


}
