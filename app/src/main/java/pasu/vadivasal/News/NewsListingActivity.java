package pasu.vadivasal.News;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.News;
import pasu.vadivasal.view.TextView;

public class NewsListingActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    @BindView(R.id.btnRetry)
    Button btnRetry;
    private int cityId;
    private ArrayList<News> itemArrayList;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    private boolean loadMore;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mainLayoutForTab)
    RelativeLayout mainRel;
    private NewsLisAdapter newsLisAdapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rvNews)
    RecyclerView rvNews;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.layoutNoInternet)
    View vHide;
    @BindView(R.id.viewEmpty)
    View viewEmpty;

    class C06441 implements OnClickListener {
        C06441() {
        }

        public void onClick(View v) {
            if (Utils.isNetworkAvailable(NewsListingActivity.this)) {
                NewsListingActivity.this.mainRel.setVisibility(View.VISIBLE);
                NewsListingActivity.this.vHide.setVisibility(View.GONE);
                NewsListingActivity.this.loadMore = false;
                NewsListingActivity.this.getLocalNewsList(null, null, false);
            }
        }
    }

    class C06473 implements Runnable {
        C06473() {
        }

        public void run() {
            NewsListingActivity.this.newsLisAdapter.loadMoreEnd();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_news_listing);
        ButterKnife.bind((Activity) this);
        setTitle(getString(R.string.local_cricket_news));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.cityId = getIntent().getIntExtra("cityId", 1);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.green_text, R.color.orange_dark, R.color.blue);
        this.rvNews.setLayoutManager(new LinearLayoutManager(this));
        this.itemArrayList = new ArrayList();
        if (Utils.isNetworkAvailable(this)) {
            this.vHide.setVisibility(View.GONE);
            getLocalNewsList(null, null, false);
        } else {
            loadNoInternetConnectionView(R.id.layoutNoInternet, R.id.mainLayoutForTab);
        }
        this.btnRetry.setOnClickListener(new C06441());
    }

    public void onStop() {
      //  ApiCallManager.cancelCall("get_local_news");
        super.onStop();
    }

    private void getLocalNewsList(Long page, Long datetime, final boolean refresh) {
        if (!this.loadMore) {
            this.progressBar.setVisibility(View.VISIBLE);
        }
        this.loadMore = false;
        emptyViewVisibility(false, "");
//        ApiCallManager.enqueue("get_local_news", CricHeroes.apiClient.getLocalNews(Utils.udid(this), CricHeroes.getApp().getAccessToken(), this.cityId, page, datetime), new CallbackAdapter() {
//
//            class C06451 extends OnItemClickListener {
//                C06451() {
//                }
//
//                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
//                    Intent intent = new Intent(NewsListingActivity.this, NewsDetailActivity.class);
//                    intent.putExtra(AppConstants.EXTRA_NEWS_ID, ((News) ((ArrayList) adapter.getData()).get(i)).getNewsId());
//                    NewsListingActivity.this.startActivity(intent);
//                    Utils.activityChangeAnimationSlide(NewsListingActivity.this, true);
//                }
//            }
//
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                NewsListingActivity.this.progressBar.setVisibility(View.GONE);
//                NewsListingActivity.this.rvNews.setVisibility(View.VISIBLE);
//                if (err != null) {
//                    NewsListingActivity.this.loadMore = true;
//                    Logger.m176d("err " + err);
//                    NewsListingActivity.this.mSwipeRefreshLayout.setRefreshing(false);
//                    if (NewsListingActivity.this.newsLisAdapter != null) {
//                        NewsListingActivity.this.newsLisAdapter.loadMoreFail();
//                    }
//                    if (NewsListingActivity.this.itemArrayList.size() <= 0) {
//                        NewsListingActivity.this.emptyViewVisibility(true, err.getMessage());
//                        NewsListingActivity.this.rvNews.setVisibility(View.GONE);
//                        return;
//                    }
//                    return;
//                }
//                NewsListingActivity.this.baseResponse = response;
//                ArrayList<News> arrayList = new ArrayList();
//                Logger.m176d("getLocalNewsList " + response);
//                JsonArray json = (JsonArray) response.getData();
//                NewsListingActivity.this.emptyViewVisibility(false, "");
//                if (json != null) {
//                    try {
//                        JSONArray jsonArray = new JSONArray(json.toString());
//                        if (jsonArray != null) {
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                arrayList.add(new News(jsonArray.getJSONObject(i)));
//                            }
//                            if (NewsListingActivity.this.newsLisAdapter == null) {
//                                NewsListingActivity.this.itemArrayList.addAll(arrayList);
//                                NewsListingActivity.this.newsLisAdapter = new NewsLisAdapter(NewsListingActivity.this, R.layout.fragment_dashboard_news_item, NewsListingActivity.this.itemArrayList, true);
//                                NewsListingActivity.this.newsLisAdapter.setEnableLoadMore(true);
//                                NewsListingActivity.this.rvNews.setAdapter(NewsListingActivity.this.newsLisAdapter);
//                                NewsListingActivity.this.rvNews.addOnItemTouchListener(new C06451());
//                                NewsListingActivity.this.newsLisAdapter.setOnLoadMoreListener(NewsListingActivity.this, NewsListingActivity.this.rvNews);
//                                if (!(NewsListingActivity.this.baseResponse == null || NewsListingActivity.this.baseResponse.hasPage())) {
//                                    NewsListingActivity.this.newsLisAdapter.loadMoreEnd();
//                                }
//                            } else {
//                                if (refresh) {
//                                    NewsListingActivity.this.newsLisAdapter.getData().clear();
//                                    NewsListingActivity.this.itemArrayList.clear();
//                                    NewsListingActivity.this.itemArrayList.addAll(arrayList);
//                                    NewsListingActivity.this.newsLisAdapter.setNewData(arrayList);
//                                    NewsListingActivity.this.newsLisAdapter.setEnableLoadMore(true);
//                                } else {
//                                    NewsListingActivity.this.newsLisAdapter.addData((Collection) arrayList);
//                                    NewsListingActivity.this.newsLisAdapter.loadMoreComplete();
//                                }
//                                if (NewsListingActivity.this.baseResponse != null && NewsListingActivity.this.baseResponse.hasPage() && NewsListingActivity.this.baseResponse.getPage().getNextPage() == 0) {
//                                    NewsListingActivity.this.newsLisAdapter.loadMoreEnd();
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    NewsListingActivity.this.emptyViewVisibility(true, NewsListingActivity.this.getString(R.string.error_no_more_news));
//                }
//                NewsListingActivity.this.mSwipeRefreshLayout.setRefreshing(false);
//                NewsListingActivity.this.loadMore = true;
//                if (NewsListingActivity.this.itemArrayList.size() == 0) {
//                    NewsListingActivity.this.emptyViewVisibility(true, NewsListingActivity.this.getString(R.string.error_no_more_news));
//                }
//            }
//        });
    }

    private void emptyViewVisibility(boolean b, String msg) {
        if (b) {
            this.viewEmpty.setVisibility(View.VISIBLE);
            this.ivImage.setVisibility(View.INVISIBLE);
            this.tvTitle.setText(msg);
            this.tvDetail.setVisibility(View.GONE);
            return;
        }
        this.viewEmpty.setVisibility(View.GONE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            Utils.finishActivitySlide(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRefresh() {
        getLocalNewsList(null, null, true);
    }

    public void onBackPressed() {
        Utils.finishActivitySlide(this);
    }

    public void onLoadMoreRequested() {
//        System.out.println("LOAD MORE " + this.loadMore);
//        if (this.loadMore && this.baseResponse != null && this.baseResponse.hasPage() && this.baseResponse.getPage().hasNextPage()) {
//            getLocalNewsList(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
//        } else {
//            new Handler().postDelayed(new C06473(), 1500);
//        }
    }
}
