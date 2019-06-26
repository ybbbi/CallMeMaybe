package com.ybbbi.googlestore.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ybbbi.googlestore.R;
import com.ybbbi.googlestore.adapter.MyBaseAdapter;
import com.ybbbi.googlestore.http.HttpHelper;
import com.ybbbi.googlestore.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.OnItemClick;

/**
 * ybbbi
 * 2019-06-24 14:24
 */
public abstract class ListViewFragment<X> extends BaseFragment implements AdapterView.OnItemClickListener {
    protected ListView mListview;
    protected SmartRefreshLayout smartRefreshLayout;
    protected ArrayList<X> list = new ArrayList<>();
    protected MyBaseAdapter<X> baseAdapter;

    @Override
    public View getSuccessView() {
        View inflateView = View.inflate(getContext(), R.layout.mainfragment, null);
        mListview = inflateView.findViewById(R.id.mainf_listview);
        smartRefreshLayout = inflateView.findViewById(R.id.mainf_smartrefresh);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                loadData();
                smartRefreshLayout.finishRefresh();
                ToastUtil.showToast("刷新成功");

            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
                smartRefreshLayout.finishLoadMore();
            }
        });
        baseAdapter = getAdapter();
        addHeader();
        mListview.setAdapter(baseAdapter);
        mListview.setOnItemClickListener(this);
        return inflateView;
    }

    protected void addHeader() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 设置得到的adapter
     *
     * @Time:2019-06-24||14:35
     */
    protected abstract MyBaseAdapter<X> getAdapter();

    @Override
    public void loadData() {
        HttpHelper.create().get(getURL(), new HttpHelper.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                stateLayout.showsuccessView();
                parseData(result);
            }

            @Override
            public void onFail(Exception e) {
                if (list.isEmpty()) {
                    stateLayout.showerrorView();

                }
            }
        }, getActivity());
    }


    /**
     * 获取服务器请求路径
     *
     * @Time:2019-06-24||14:41
     */
    protected abstract String getURL();

    /**
     * 解析服务器返回的数据
     *
     * @Time:2019-06-24||14:42
     */
    protected abstract void parseData(String result);
}
