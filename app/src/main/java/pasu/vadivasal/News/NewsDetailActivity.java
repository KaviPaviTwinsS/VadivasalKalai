package pasu.vadivasal.News;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.CircleIndicator;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.globalModle.News;
import pasu.vadivasal.tournament.TournamentMatchesActivity;
import pasu.vadivasal.view.Button;
import pasu.vadivasal.view.TextView;

public class NewsDetailActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    private List<Media> arrayList = new ArrayList();
    @BindView(R.id.btnArticle)
    Button btnArticle;
    @BindView(R.id.btnRetry)
    android.widget.Button btnRetry;
    @BindView(R.id.btnScore)
    Button btnScore;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.layCoordinate)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fabShare)
    FloatingActionButton fabShare;
    ImagePagerAdapter imagePagerAdapter;
    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.img_right)
    ImageView img_right;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.ivDefault)
    ImageView ivDefault;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.layNewsDetails)
    LinearLayout layNewsDetails;
    private int matchId;
    private int newsId;
    private String newsUrl;
    @BindView(R.id.pagerImages)
    ViewPager pagerImages;
    private NewsPhotosAdapter photosAdapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String title = "";
    private SpannableString titleSpan;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int tournamentId;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    @BindView(R.id.tvNewsDetail)
    TextView tvNewsDetail;
    @BindView(R.id.tvNewsTitle)
    TextView tvNewsTitle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.layoutNoInternet)
    View vHide;
    @BindView(R.id.viewEmpty)
    View viewEmpty;
    private News news;
    private boolean isfromNotificaiton;

    class C06401 implements OnClickListener {
        C06401() {
        }

        public void onClick(View view) {
            System.out.println("ON CLICK");
            NewsDetailActivity.this.vHide.setVisibility(View.GONE);
            NewsDetailActivity.this.coordinatorLayout.setVisibility(View.VISIBLE);
            NewsDetailActivity.this.getLocalNewsDetail();
        }
    }

    class C06412 implements OnClickListener {
        C06412() {
        }

        public void onClick(View v) {
            if (Utils.isNetworkAvailable(NewsDetailActivity.this)) {
                NewsDetailActivity.this.collapsing_toolbar.setVisibility(View.VISIBLE);
                NewsDetailActivity.this.vHide.setVisibility(View.GONE);
                NewsDetailActivity.this.getLocalNewsDetail();
            }
        }
    }

    class C06423 implements OnOffsetChangedListener {
        boolean isShow = false;
        int scrollRange = -1;

        C06423() {
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (this.scrollRange == -1) {
                this.scrollRange = appBarLayout.getTotalScrollRange();
            }
            if (this.scrollRange + verticalOffset == 0) {
                NewsDetailActivity.this.collapsing_toolbar.setTitle(NewsDetailActivity.this.titleSpan);
                this.isShow = true;
            } else if (this.isShow) {
                NewsDetailActivity.this.collapsing_toolbar.setTitle(" ");
                this.isShow = false;
            }
        }
    }

//    class C06434 extends CallbackAdapter {
//        C06434() {
//        }
//
//        public void onApiResponse(ErrorResponse err, BaseResponse response) {
//            NewsDetailActivity.this.progressBar.setVisibility(View.GONE);
//            NewsDetailActivity.this.collapsing_toolbar.setVisibility(View.VISIBLE);
//            if (err != null) {
//                Logger.m176d("err " + err);
//                NewsDetailActivity.this.emptyViewVisibility(true, err.getMessage());
//                NewsDetailActivity.this.collapsing_toolbar.setVisibility(View.GONE);
//                return;
//            }
//            Logger.m176d("getLocalNewsDetail " + response);
//            NewsDetailActivity.this.vHide.setVisibility(View.GONE);
//            NewsDetailActivity.this.coordinatorLayout.setVisibility(View.VISIBLE);
//            JsonObject json = (JsonObject) response.getData();
//            NewsDetailActivity.this.emptyViewVisibility(false, "");
//            if (json != null) {
//                try {
//                    JSONObject object = new JSONObject(json.toString());
//                    if (object != null) {
//                        NewsDetailActivity.this.setData(object);
//                        return;
//                    } else {
//                        NewsDetailActivity.this.emptyViewVisibility(true, NewsDetailActivity.this.getString(R.string.error_no_news_detail));
//                        return;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    return;
//                }
//            }
//            NewsDetailActivity.this.emptyViewVisibility(true, NewsDetailActivity.this.getString(R.string.error_no_news_detail));
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_news_detail);

        news = getIntent().getParcelableExtra("news_media");
        ButterKnife.bind((Activity) this);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Color.parseColor("#80000000"));
            this.appBarLayout.setTransitionName(getString(R.string.activity_image_trans));
        }
        init();
        setTitleCollapse();
        try {
            System.out.println("news stirng " + Utils.toString(news));
            if (news == null) {
                isfromNotificaiton=true;
                // news=Utils.fromJson(getIntent().getStringExtra("content"),News.class);
                FirebaseDatabase.getInstance().getReference("News/" + getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        news = dataSnapshot.getValue(News.class);
                        try {
                            setData(new JSONObject(Utils.toString(news)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            if (news != null)
                setData(new JSONObject(Utils.toString(news)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        this.newsId = getIntent().getIntExtra(AppConstants.EXTRA_NEWS_ID, 0);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.collapsing_toolbar.setTitle(" ");
        this.btnArticle.setOnClickListener(this);
        this.btnScore.setOnClickListener(this);
        this.fabShare.setOnClickListener(this);
        if (Utils.isNetworkAvailable(this)) {
            this.vHide.setVisibility(View.GONE);
            getLocalNewsDetail();
        } else {
            loadNoInternetConnectionView(R.id.layoutNoInternet, R.id.layCoordinate, new C06401());
        }
        this.btnRetry.setOnClickListener(new C06412());
    }

    private void setTitleCollapse() {
        this.appBarLayout.addOnOffsetChangedListener(new C06423());
    }

    private void setData(JSONObject j) {
//         j = new JSONObject();
        progressBar.setVisibility(View.GONE);
        fabShare.setVisibility(View.VISIBLE);
//        try {
//
//            j.put("news_title", "sdfsdfs");
//            j.put("description", "I think it is a problem(aka. bug) with the API you are using.  JSONArray implements Collection (the json.org implementation from which this API is derived does not have JSONArray implement Collection). And JSONObject has an overloaded put() method which takes a Collection and wraps it in a JSONArray (thus causing the problem). I think you need to force the other JSONObject.put() method to be used:");
//            j.put(AppConstants.EXTRA_MATCH_ID, "sdghasdf");
//            j.put(AppConstants.EXTRA_TOURNAMENT_ID, "asDfsdfsd");
//            j.put("news_link", "asdfsdafc");
//            j.put("app_title", "sdfsadfsa");
//            j.put("news_date", "asdnhyw4trfw");
//
//
//            JSONArray arrays = new JSONArray();
//            for (int i = 0; i < 10; i++) {
//                JSONObject js = new JSONObject();
//                js.put("media_id", "sdfsd");
//                js.put("media_type", "0");
//                js.put("uploaded_by", "nagaaaagagaag");
//                js.put("orientation", "");
//                js.put(Appconstants.WEB_DIALOG_PARAM_MEDIA,"https://firebasestorage.googleapis.com/v0/b/jallikattu-1a841.appspot.com/o/23674850_1403567906433281_6960080986797619123_o.jpg?alt=media&token=03670a6a-2b56-4a9d-a3cd-996baf6abee4");
//
//                arrays.put(js);
//            }
//            j.put(Appconstants.WEB_DIALOG_PARAM_MEDIA, arrays);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        System.out.println("nagagag____" + j.toString());
        this.tvNewsTitle.setText(news.getTitle());
        this.tvNewsDetail.setText(Html.fromHtml(news.getDescription()));
        this.matchId = j.optInt(AppConstants.EXTRA_MATCH_ID);
        this.tournamentId = j.optInt(AppConstants.EXTRA_TOURNAMENT_ID);
        this.newsUrl = j.optString(news.getNewsUrl());
//        this.title = j.optString("app_title");
        this.titleSpan = new SpannableString(this.title);
        this.titleSpan.setSpan(new pasu.vadivasal.android.style.TypefaceSpan(this, getString(R.string.font_roboto_slab_regular)), 0, this.titleSpan.length(), 33);
        // this.tvDate.setText(Utils.changeDateformate(j.optString("news_date"), "yyyy-MM-dd'T'HH:mm:ss", "dd MMMM, yyyy") + ", " + j.optString("city_name"));
//        try {
//            JSONArray array = j.getJSONArray(Appconstants.WEB_DIALOG_PARAM_MEDIA);
//            if (array != null) {
//                for (int i = 0; i < news.getMedia().length(); i++) {
//                    JSONObject jsonObject = array.getJSONObject(i);
//                    this.arrayList.add(new Media(jsonObject.optString("media_id"), jsonObject.optInt("media_type"), jsonObject.optString(Appconstants.WEB_DIALOG_PARAM_MEDIA), jsonObject.optString("uploaded_by"), "", jsonObject.optString("orientation")));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        if (this.matchId == 0 && this.tournamentId == 0) {
            this.btnScore.setVisibility(View.GONE);
            findViewById(R.id.layBottom).setVisibility(View.VISIBLE);
        } else if (this.matchId != 0) {
            findViewById(R.id.layBottom).setVisibility(View.VISIBLE);
            this.btnScore.setVisibility(View.VISIBLE);
            this.btnScore.setText(R.string.view_score);
        } else if (this.tournamentId != 0) {
            findViewById(R.id.layBottom).setVisibility(View.VISIBLE);
            this.btnScore.setVisibility(View.VISIBLE);
            this.btnScore.setText(R.string.tournament);
        }
        System.out.println("tourID____"+news.getTournament_id());
        if (Utils.isEmptyString(news.getNewsUrl()) && Utils.isEmptyString(news.getTournament_id())) {
            this.btnArticle.setVisibility(View.GONE);
        } else {
            if(!Utils.isEmptyString(news.getTournament_id()))
                this.btnArticle.setText(getString(R.string.go_to_event));
            btnArticle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(news.getTournament_id()!=null && !news.getTournament_id().equals("")){
                        Intent i = new Intent(NewsDetailActivity.this,TournamentMatchesActivity.class);
                       // i.setData(Uri.parse(news.getNewsUrl()));
                        i.putExtra(Appconstants.TourID,news.getTournament_id());
                        startActivity(i);
                    }else {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(news.getNewsUrl()));
                        startActivity(i);
                    }
                }
            });
        }
        if (this.news.getMedia().size() > 0) {
            this.imagePagerAdapter = new ImagePagerAdapter(this, this.news.getMedia());
            this.ivDefault.setVisibility(View.GONE);
            this.pagerImages.setAdapter(this.imagePagerAdapter);
            this.indicator.setViewPager(this.pagerImages);
            this.pagerImages.setClipToPadding(false);
            return;
        }
        this.ivDefault.setVisibility(View.VISIBLE);
        this.pagerImages.setVisibility(View.GONE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            Utils.finishActivitySlide(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

        if(isfromNotificaiton){
            final Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        Utils.finishActivitySlide(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnArticle:
                System.out.println("URL " + this.newsUrl);
                openInAppBrowser(this.newsUrl);
                return;
            case R.id.btnScore:
                Intent intent;
                if (this.matchId != 0) {
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra(AppConstants.EXTRA_MATCH_ID, this.matchId);
                    intent.putExtra("news", true);
                    intent.putExtra(AppConstants.EXTRA_FROM_MATCH, true);
                    startActivity(intent);
                    Utils.activityChangeAnimationSlide(this, true);
                    return;
                } else if (this.tournamentId != 0) {
                    intent = new Intent(this, TournamentMatchesActivity.class);
                    intent.putExtra(AppConstants.EXTRA_MATCH_ID, this.matchId);
                    intent.putExtra(AppConstants.EXTRA_TOURNAMENTID, this.tournamentId);
                    startActivity(intent);
                    Utils.activityChangeAnimationSlide(this, true);
                    return;
                } else {
                    return;
                }
            case R.id.fabShare:
                if (VERSION.SDK_INT < 23) {
                    this.fabShare.setVisibility(View.INVISIBLE);
                    shareBitmap(this.pagerImages.getChildAt(0), this.layNewsDetails);
                    return;
                } else if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                    this.fabShare.setVisibility(View.INVISIBLE);
                    shareBitmap(this.pagerImages.getChildAt(0), this.layNewsDetails);
                    return;
                } else {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 102);
                    return;
                }
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("requestCode " + requestCode);
        if (requestCode == 102) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                this.fabShare.setVisibility(View.INVISIBLE);
                shareBitmap(this.pagerImages.getChildAt(0), this.layNewsDetails);
            } else {
                Utils.showToast(this, getString(R.string.permission_not_granted), 1, false);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void shareBitmap(View imageView, LinearLayout layNewDetails) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Config.ARGB_8888);
            imageView.draw(new Canvas(bitmap));
            Bitmap dataBitmap = shareText(layNewDetails);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            if (dataBitmap != null) {
                h += dataBitmap.getHeight();
            }
            Bitmap temp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            Canvas canvas = new Canvas(temp);
            canvas.drawBitmap(bitmap, 0.0f, (float) 0, null);
            if (dataBitmap != null) {
                canvas.drawBitmap(dataBitmap, 0.0f, (float) bitmap.getHeight(), null);
            }
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + (getApplicationContext().getPackageName() + File.separator + "photo-picker") + File.separator);
            root.mkdirs();
            String fname = "NewsDetail.jpg";
            File file = new File(root, "NewsDetail.jpg");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fOut = new FileOutputStream(file);
            temp.compress(CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            System.out.println("path " + file.getAbsolutePath());
            Intent share = new Intent("android.intent.action.SEND");
            share.setType("image/*");
            share.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            if (Utils.isEmptyString(this.newsUrl)) {
                share.putExtra("android.intent.extra.TEXT", getString(R.string.share_news_msg));
            } else {
                share.putExtra("android.intent.extra.TEXT", "News source: " + this.newsUrl + Utils.COMMAND_LINE_END + getString(R.string.share_news_msg));
            }
            share.addFlags(1);
            startActivity(Intent.createChooser(share, "Share via"));
            this.fabShare.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap shareText(LinearLayout layNewsDetails) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(layNewsDetails.getWidth(), layNewsDetails.getHeight(), Config.ARGB_8888);
            layNewsDetails.draw(new Canvas(bitmap));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    protected void onStop() {
        //  ApiCallManager.cancelCall("get_news_detail");
        super.onStop();
    }

    private void getLocalNewsDetail() {
        this.progressBar.setVisibility(View.VISIBLE);
        emptyViewVisibility(false, "");
        //  ApiCallManager.enqueue("get_news_detail", CricHeroes.apiClient.getLocalNewsDetail(Utils.udid(this), CricHeroes.getApp().getAccessToken(), this.newsId), new C06434());
    }
}
