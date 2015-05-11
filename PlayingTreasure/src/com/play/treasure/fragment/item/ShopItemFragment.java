package com.play.treasure.fragment.item;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.bureak.common.ImageListAdapter;
import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.play.treasure.MallActivity;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.waterfall.WaterfallAdapter;

public class ShopItemFragment extends Fragment {
    private StaggeredGridView mGridView;

    //	private WaterfallAdapter mAdapter;
    private ImageListAdapter mAdapter;
    private int currentPage = 1;

    private PlayApplication mApplication;

    private String tabId;
    private CommonProgressDialog progressDialog;


    PullToRefreshStaggeredGridView mPullRefreshListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = PlayApplication.getApplication();
        progressDialog = CommonProgressDialog.getInstance(getActivity());
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_category_all, container, false);

        Bundle mBundle = getArguments();
        tabId = mBundle.getString("category");


        mPullRefreshListView = (PullToRefreshStaggeredGridView) view.findViewById(R.id.grid_view);
        mGridView = (StaggeredGridView) mPullRefreshListView.getRefreshableView();

        mAdapter = new ImageListAdapter(getActivity(),new ArrayList<Waterfall>());
        new WaterfallTask().execute();

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(mAdapter);

        initListView();
        return view;
    }

    public void initListView() {
        //mListItems = new LinkedList<String>();
        //mListItems.addAll(Arrays.asList(mStrings));
        //mAdapter = new ArrayAdapter<String>(this,
        //		android.R.layout.simple_list_item_1, mListItems);
        //	mAdapter = new ListViewAdapter(this.getActivity());
        mPullRefreshListView.setMode(Mode.BOTH);
        // ����ˢ��ʱ����ʾ�ı�����
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上次刷新时间");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("刷新完成");
        // �������ظ��ʱ����ʾ�ı�����
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上次加载时间");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("加载完成");

        //	mGridView = mPullRefreshListView.getRefreshableView();
        mGridView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                //	isScoll = false;
                float n_y;
                int eventAction = event.getAction();
                //int y = (int) event.getRawY();
                switch (eventAction) {
                    case MotionEvent.ACTION_UP:
                        Log.i("uevent", "" + event.getY());
                        //	n_y
                        break;
                    case MotionEvent.ACTION_DOWN:
                        Log.i("devent", "" + event.getY());
                        //o_y  = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("mevent", "" + event.getY());
                        break;
                    default:
                        break;
                }
                return false;
            }

        });
        mPullRefreshListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                Log.i("scrol", arg1 + "#" + arg2 + "#" + arg3);

            }

            @SuppressLint("NewApi")
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                // TODO Auto-generated method stub

            }

        });
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<StaggeredGridView>() {

            @SuppressLint("NewApi")
            @Override
            public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
                String label = DateUtils.formatDateTime(ShopItemFragment.this.getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                if (refreshView.isHeaderShown()) {

                    Message msg = MallActivity.controllHandler.obtainMessage();
                    msg.what = 2;
                    msg.sendToTarget();

                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    // Do work to refresh the list here.
                    //	new GetDataTask().execute();
                    // 	new GetHeaderDataTask().execute();
                    //	scroll_y =
                    //	p=0;
                    //	getData();
                    //	getListMasData(p,x,y,post_cate);
                    currentPage = 1;
                    mAdapter.clear();
                    new WaterfallTask().execute();
                    Log.i("", "刷新中... ");
                } else {
                    Log.i("", "加载中... ");
                    //founctionlist.clear();

                    //		p+=1;
                    //		getData();
                    //	 	scroll_y = founctionlist.size();
                    //	    getListMasData(p,x,y,post_cate);
                    //new GetBottomDataTask().execute();
                    currentPage++;
                    new WaterfallTask().execute();
                }

                // Update the LastUpdatedLabel
            }

        });
        //	mPullRefreshListView.setPadding(10, 10, 10, 10);
        //	lvShow.setGridPadding(10, 10, 10, 10);
        //lvShow.setI
        //	lvShow.setAdapter(mAdapter);
        //	mPullRefreshScrollView.setO
    }

    private class WaterfallTask extends AsyncTask<Void, Void, NetworkBeanArray> {
        @Override
        protected void onPreExecute() {
            if (!getActivity().isFinishing() && !progressDialog.isShowing()) {
                progressDialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected NetworkBeanArray doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().treasureMall(String.valueOf(currentPage), mApplication.getLongitude(), mApplication.getLatitude(), tabId);
            } catch (NullPointerException ex) {
                progressDialog.dismiss();
                currentPage--;
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBeanArray result) {
            if (result != null) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(result.getResult());
                    List<Waterfall> waterList = new ArrayList<Waterfall>();
                    Waterfall water = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        water = new Waterfall(jsonArray.getJSONObject(i));
                        waterList.add(water);
                    }
                    if (waterList.size() == 0) {
                        currentPage--;
                    } else {
//                        mAdapter.addAll(waterList);
//                        mAdapter.notifyDataSetChanged();
                        mAdapter.addItemLast(waterList);
                    }

                    mPullRefreshListView.onRefreshComplete();
                } catch (Exception e) {
                    currentPage--;
                    mPullRefreshListView.onRefreshComplete();
                    e.printStackTrace();
                }
            }
        }
    }
}