package pasu.vadivasal.tournament;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.BlurTransformation;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.TournamentData;


public class TournamentMatchesActivity extends BaseActivity implements OnTabSelectedListener, OnClickListener {
    private TournamentPagerAdapter adapter;
    String coverPicUrl;
    Dialog dialog;
    String logoUrl;
    private int position;
    public String premium;
    private boolean showShareMenu;
    Target target = new C13776();
    Target target1 = new C13765();
    public String title = "";
    private SpannableString titleSpan;
    String tournamentId;
    public int type;
    String aboutData;


    AppBarLayout
            app_bar_layout;
    CollapsingToolbarLayout
            collapsing_toolbar;
    RelativeLayout
            layoutcollapse, container;
    ImageView
            imgBlurBackground, img_shadow;
    View vHide;
    LinearLayout
            layCenter;
    pasu.vadivasal.view.CircleImageView
            imgPlayer;
    androidx.appcompat.widget.AppCompatImageView
            imgPremiumIcon, ivInfo;
    pasu.vadivasal.view.TextView
            tvPlayerName, tvdetail, txt_date, txtChallange, btnFollow, tvViewer, txt_error;
    CardView
            card_challange, card_Follow;
    pasu.vadivasal.view.LikeButton
            ivFav;
    androidx.appcompat.widget.Toolbar
            toolbar;
    TabLayout
            tabLayoutScoreCard;
    ViewPager
            viewPager;
    FloatingActionButton
            fabFollow, fabShare;
    pasu.vadivasal.view.Button
            btnLeaveTeam;
    int isFavorite;
    private TournamentData tournamentData;
    private boolean isfromNotificaiton;

    class C13701 implements OnClickListener {
        C13701() {
        }

        public void onClick(View view) {
//            Intent intent = new Intent(TournamentMatchesActivity.this, ImageDetailActivity.class);
//            if (Utils.isEmptyString(TournamentMatchesActivity.this.logoUrl)) {
//                intent.putExtra("userPicUrl", "");
//            } else {
//                intent.putExtra("userPicUrl", TournamentMatchesActivity.this.logoUrl);
//            }
            //  TournamentMatchesActivity.this.startActivity(intent);
        }
    }

//    class C13712 implements OnLikeListener {
//        C13712() {
//        }
//
//        public void liked(LikeButton likeButton) {
//            likeButton.setLiked(Boolean.valueOf(true));
//            TournamentMatchesActivity.this.addTournamentToFavorite();
//        }
//
//        public void unLiked(LikeButton likeButton) {
//            likeButton.setLiked(Boolean.valueOf(false));
//            TournamentMatchesActivity.this.addTournamentToFavorite();
//        }
//    }

    class C13723 implements OnClickListener {
        C13723() {
        }

        public void onClick(View v) {
            Toast.makeText(TournamentMatchesActivity.this, TournamentMatchesActivity.this.getString(R.string.tournament_total_view_of_match_message), Toast.LENGTH_SHORT).show();
        }
    }

    class C13734 implements OnOffsetChangedListener {
        boolean isShow = false;
        int scrollRange = -1;

        C13734() {
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (this.scrollRange == -1) {
                this.scrollRange = appBarLayout.getTotalScrollRange();
            }
            System.out.println("scrollRange " + this.scrollRange);
            System.out.println("verticalOffset " + verticalOffset);
            System.out.println("isShow " + this.isShow);
            if (this.scrollRange + verticalOffset == 0) {
                TournamentMatchesActivity.this.collapsing_toolbar.setTitle(TournamentMatchesActivity.this.titleSpan);
                this.isShow = true;
            } else if (this.isShow) {
                TournamentMatchesActivity.this.collapsing_toolbar.setTitle(" ");
                this.isShow = false;
            }
        }
    }

    class C13765 implements Target {

        class C13752 implements Runnable {
            C13752() {
            }

            public void run() {
                TournamentMatchesActivity.this.imgBlurBackground.setImageBitmap(Utils.fastblur(BitmapFactory.decodeResource(TournamentMatchesActivity.this.getResources(), R.drawable.ic_bull_logo), TournamentMatchesActivity.this));
            }
        }

        C13765() {
        }

        public void onBitmapLoaded(final Bitmap bitmap, LoadedFrom from) {
            TournamentMatchesActivity.this.imgBlurBackground.post(new Runnable() {
                public void run() {
                    TournamentMatchesActivity.this.imgBlurBackground.setImageBitmap(Utils.fastblur(bitmap, TournamentMatchesActivity.this));
                }
            });
        }

        public void onBitmapFailed(Drawable errorDrawable) {
            TournamentMatchesActivity.this.imgBlurBackground.post(new C13752());
        }

        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }

    class C13776 implements Target {
        C13776() {
        }

        public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
            TournamentMatchesActivity.this.imgPlayer.setImageBitmap(bitmap);
        }

        public void onBitmapFailed(Drawable errorDrawable) {
            TournamentMatchesActivity.this.imgPlayer.setImageBitmap(BitmapFactory.decodeResource(TournamentMatchesActivity.this.getResources(), R.drawable.ic_bull_logo));
        }

        public void onPrepareLoad(Drawable placeHolderDrawable) {
            TournamentMatchesActivity.this.imgPlayer.setImageBitmap(BitmapFactory.decodeResource(TournamentMatchesActivity.this.getResources(), R.drawable.ic_bull_logo));
        }
    }

    class C13798 {
        C13798() {
        }

        // public void onApiResponse(ErrorResponse err, BaseResponse response) {
//            Utils.hideProgress(TournamentMatchesActivity.this.dialog);
//            if (err != null) {
//                Logger.m176d("err " + err);
//                Utils.showToast(TournamentMatchesActivity.this, err.getMessage(), 1, false);
//                TournamentMatchesActivity.this.setTitleCollapse();
//                return;
//            }
//            JsonObject json = (JsonObject) response.getData();
//            Logger.m176d("getTournamentDetail " + json);
//            if (json != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(json.toString());
//                    if (Utils.isEmptyString(TournamentMatchesActivity.this.title)) {
//                        TournamentMatchesActivity.this.title = jsonObject.optString("name");
//                        TournamentMatchesActivity.this.logoUrl = jsonObject.getString(AppConstants.EXTRA_TOURNAMENT_LOGO);
//                        TournamentMatchesActivity.this.coverPicUrl = jsonObject.getString(AppConstants.EXTRA_TOURNAMENT_COVER);
//                        TournamentMatchesActivity.this.setTitleSpan(TournamentMatchesActivity.this.title);
//                        TournamentMatchesActivity.this.setTournamentData();
//                    }
//                    TournamentMatchesActivity.this.isFavorite = jsonObject.optInt("is_favourite");
//                    TournamentMatchesActivity.this.type = jsonObject.optInt("type");
//                    System.out.println("isFavorite " + TournamentMatchesActivity.this.isFavorite);
//                    TournamentMatchesActivity.this.ivFav.setVisibility(View.VISIBLE);
//                    int totalViews = jsonObject.optInt("total_views");
//                    if (jsonObject.optInt("is_display_view") != 1 || totalViews <= 0) {
//                        TournamentMatchesActivity.this.tvViewer.setVisibility(View.GONE);
//                    } else {
//                        TournamentMatchesActivity.this.tvViewer.setVisibility(View.VISIBLE);
//                        TournamentMatchesActivity.this.tvViewer.setText(TournamentMatchesActivity.this.getString(R.string.views, new Object[]{String.valueOf(totalViews)}));
//                    }
//                    TournamentMatchesActivity.this.ivFav.setLiked(Boolean.valueOf(TournamentMatchesActivity.this.isFavorite != 0));
//                    JSONArray jsonArray = jsonObject.optJSONArray(AppConstants.EXTRA_TEAMS);
//                    TournamentTeamFragment tournamentTeamFragment = (TournamentTeamFragment) TournamentMatchesActivity.this.adapter.getFragment(5);
//                    if (tournamentTeamFragment != null) {
//                        tournamentTeamFragment.setData(jsonArray);
//                    }
//                    TournamentAboutUsFragment aboutUsFragment = (TournamentAboutUsFragment) TournamentMatchesActivity.this.adapter.getFragment(8);
//                    if (aboutUsFragment != null) {
//                        aboutUsFragment.setData(jsonObject, TournamentMatchesActivity.this.getResources().getString(R.string.about_blank_stat));
//                    }
//                    TournamentMatchesActivity.this.txt_date.setText("Date: " + Utils.changeDateformate(jsonObject.optString("from_date"), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM, yyyy") + " to " + Utils.changeDateformate(jsonObject.optString("to_date"), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM, yyyy"));
//                    TournamentMatchesActivity.this.premium = jsonObject.optString("tournament_category");
//                    if (!TextUtils.isEmpty(TournamentMatchesActivity.this.premium) && TournamentMatchesActivity.this.premium.equalsIgnoreCase(AppConstants.PREMIUM)) {
//                        TournamentMatchesActivity.this.imgPremiumIcon.setVisibility(View.VISIBLE);
//                        AppCompatTextView tv = (AppCompatTextView) ((LinearLayout) ((LinearLayout) TournamentMatchesActivity.this.tabLayoutScoreCard.getChildAt(0)).getChildAt(4)).getChildAt(1);
//                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sponsors_tab_badge, 0);
//                        tv.setCompoundDrawablePadding(10);
//                        tv.setPadding(0, 0, 0, 0);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            TournamentMatchesActivity.this.viewPager.setCurrentItem(TournamentMatchesActivity.this.position);
//            TournamentMatchesActivity.this.setTitleCollapse();
        //}
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_player_profile);
        this.tournamentId = getIntent().getStringExtra(Appconstants.TourID);
        try {
            this.aboutData = (getIntent().getStringExtra("about"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("tournamentID__" + tournamentId);
        app_bar_layout = (AppBarLayout
                ) findViewById(R.id.app_bar_layout);
        collapsing_toolbar = (CollapsingToolbarLayout
                ) findViewById(R.id.collapsing_toolbar);
        layoutcollapse = (RelativeLayout
                ) findViewById(R.id.layoutcollapse);
        imgBlurBackground = (ImageView
                ) findViewById(R.id.imgBlurBackground);

        img_shadow = (ImageView
                ) findViewById(R.id.img_shadow);
        layCenter = (LinearLayout
                ) findViewById(R.id.layCenter);
        imgPlayer = (pasu.vadivasal.view.CircleImageView
                ) findViewById(R.id.imgPlayer);
        imgPremiumIcon = (androidx.appcompat.widget.AppCompatImageView
                ) findViewById(R.id.imgPremiumIcon);
        tvPlayerName = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.tvPlayerName);
        tvdetail = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.tvdetail);
        txt_date = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.txt_date);
        card_challange = (CardView
                ) findViewById(R.id.card_challange);
        txtChallange = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.txtChallange);
        card_Follow = (CardView
                ) findViewById(R.id.card_Follow);
        btnFollow = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.btnFollow);
        tvViewer = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.tvViewer);
        ivFav = (pasu.vadivasal.view.LikeButton
                ) findViewById(R.id.ivFav);
        ivInfo = (androidx.appcompat.widget.AppCompatImageView
                ) findViewById(R.id.ivInfo);
        toolbar = (androidx.appcompat.widget.Toolbar
                ) findViewById(R.id.toolbar);
        tabLayoutScoreCard = (TabLayout
                ) findViewById(R.id.tabLayoutPlayer);
        vHide = findViewById(R.id.viewDivider);
        container = (RelativeLayout
                ) findViewById(R.id.container);
        viewPager = (ViewPager
                ) findViewById(R.id.pagerPlayer);
        fabFollow = (FloatingActionButton
                ) findViewById(R.id.fabFollow);
        fabShare = (FloatingActionButton
                ) findViewById(R.id.fabShare);
        txt_error = (pasu.vadivasal.view.TextView
                ) findViewById(R.id.txt_error);
        btnLeaveTeam = (pasu.vadivasal.view.Button
                ) findViewById(R.id.btnLeaveTeam);
        findViewById(R.id.ivBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
         tournamentData = Utils.fromJson(aboutData, TournamentData.class);
        if(tournamentData==null){
            isfromNotificaiton=true;
            if(getIntent().getStringExtra(Appconstants.TourID)!=null && !getIntent().getStringExtra(Appconstants.TourID).equals(""))
                FirebaseDatabase.getInstance().getReference("tournament-new/about/"+ getIntent().getStringExtra(Appconstants.TourID)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                        tournamentData = dataSnapshot.getValue( TournamentData.class);
                            Picasso.with(TournamentMatchesActivity.this).load(tournamentData.getTournamentCoverPhoto())
                                    // .resize(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT, 200)
                                    //.transform(new BlurTransformation(this))
                                    .into(TournamentMatchesActivity.this.imgBlurBackground);
                        tvViewer.setText(tournamentData.getViewers());
                        tvViewer.setVisibility(View.VISIBLE);
                        tvPlayerName.setText(tournamentData.getName());
                        if (tournamentData.getToDate() != 0)
                            txt_date.setText(Utils.getDateOnly(tournamentData.getDate()) + " to " + Utils.getDateOnly(tournamentData.getToDate()));
                        else
                            txt_date.setText(Utils.getDateOnly(tournamentData.getDate()).toString());
                        if(adapter.getFragment(0)!=null && adapter.getFragment(0) instanceof  InfoFragment){
                            ((InfoFragment)adapter.getFragment(0)).setData(tournamentData);
                        }
                        System.out.println("nagaa"+tournamentData.getAbout());}
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }else{

        Picasso.with(this).load(tournamentData.getTournamentCoverPhoto())
                // .resize(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT, 200)
                //.transform(new BlurTransformation(this))
                .into(this.imgBlurBackground);
//        Picasso.with(this).load(R.drawable.download)
//               // .resize(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT, 200)
//                //.transform(new BlurTransformation(this))
//                .into(this.imgPlayer);
        tvViewer.setText(tournamentData.getViewers());
        tvViewer.setVisibility(View.VISIBLE);
        tvPlayerName.setText(tournamentData.getName());
        if (tournamentData.getToDate() != 0)
            txt_date.setText(Utils.getDateOnly(tournamentData.getDate()) + " to " + Utils.getDateOnly(tournamentData.getToDate()));
        else
            txt_date.setText(Utils.getDateOnly(tournamentData.getDate()).toString());}
        //ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivInfo.setVisibility(View.GONE);
        ivInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(TournamentMatchesActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 102);
                        return;
                    }
                    shareBitmap(TournamentMatchesActivity.this.layoutcollapse);
                    return;
                }
                shareBitmap(TournamentMatchesActivity.this.layoutcollapse);
            }
        });
//        this.title = getIntent().getStringExtra("title");
//        this.collapsing_toolbar.setTitle(" ");

//        this.position = getIntent().getIntExtra(AppConstants.EXTRA_POSITION, 0);
//        if (!Utils.isEmptyString(this.title)) {
//            this.logoUrl = getIntent().getStringExtra(AppConstants.EXTRA_TOURNAMENT_LOGO);
//            this.coverPicUrl = getIntent().getStringExtra(AppConstants.EXTRA_TOURNAMENT_COVER);
//            setTitleSpan(this.title);
//            setTournamentData();
//        }
//        Log.e("coverPicUrl", "= " + this.coverPicUrl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_about));
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_commentary));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_Leaderboard));
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_statistics));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_heroes));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_sponsor));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.prizes));
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_media));
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_video));
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_sponsor));


        //        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_teams));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_standings));

        this.tabLayoutScoreCard.setTabGravity(0);
        this.tabLayoutScoreCard.setTabMode(TabLayout.MODE_SCROLLABLE);
//         this.vHide.setVisibility(View.GONE);
//        this.card_Follow.setVisibility(View.GONE);
//        this.fabShare.setVisibility(View.GONE);
        this.btnFollow.setVisibility(View.GONE);
//        this.txt_date.setVisibility(View.VISIBLE);
//        this.dialog = Utils.showProgress(this, false);
//        this.imgPlayer.setOnClickListener(new C13701());
        this.adapter = new TournamentPagerAdapter(getSupportFragmentManager(), this.tabLayoutScoreCard.getTabCount(), tournamentId);
        this.viewPager.setOffscreenPageLimit(this.tabLayoutScoreCard.getTabCount());
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this.tabLayoutScoreCard));
        this.tabLayoutScoreCard.addOnTabSelectedListener(this);
//        getTournamentDetail();
////        if (!CricHeroes.getApp().isGuestUser()) {
////            this.ivFav.setOnLikeListener(new C13712());
////        }
//        this.tvViewer.setOnClickListener(new C13723());
    }

    private void setTitleSpan(String title) {
        this.titleSpan = new SpannableString(title);
        this.titleSpan.setSpan(new TypefaceSpan(getString(R.string.font_roboto_slab_regular)), 0, this.titleSpan.length(), 33);
    }

    private void setTitleCollapse() {
        ((AppBarLayout) findViewById(R.id.app_bar_layout)).addOnOffsetChangedListener(new C13734());
    }

    private void setTournamentData() {
        this.tvPlayerName.setText(this.title);
        if (Utils.isEmptyString(this.logoUrl)) {
            Picasso.with(this).load((int) R.drawable.ic_bull_logo).fit().centerCrop().into(this.imgPlayer);
        } else {
            Picasso.with(this).load(this.logoUrl + AppConstants.THUMB_IMAGE).placeholder((int) R.drawable.ic_bull_logo).error((int) R.drawable.ic_bull_logo).into(this.target);
        }
        if (Utils.isEmptyString(this.coverPicUrl)) {
            Picasso.with(this).load((int) R.drawable.ic_bull_logo)/*.resize(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT, 200)*/.transform(new BlurTransformation(this)).into(this.imgBlurBackground);
        } else {
            Picasso.with(this).load(this.coverPicUrl + AppConstants.THUMB_IMAGE).placeholder((int) R.drawable.ic_bull_logo).into(this.target1);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_ground, menu);
        MenuItem itemShare = menu.findItem(R.id.action_share);
        MenuItem itemFilter = menu.findItem(R.id.action_filter);
        MenuItem itemInfo = menu.findItem(R.id.action_info);
        itemFilter.setVisible(false);
        itemInfo.setVisible(true);
        if (this.showShareMenu) {
            itemShare.setVisible(true);
        } else {
            itemShare.setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Utils.finishActivitySlide(this);
                break;
            case R.id.action_info:
                this.viewPager.setCurrentItem(this.tabLayoutScoreCard.getTabCount() - 1);
                break;
            case R.id.action_share:
                if (VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 102);
                        break;
                    }
                    shareBitmap(this.layoutcollapse);
                    break;
                }
                shareBitmap(this.layoutcollapse);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("requestCode " + requestCode);
        if (requestCode == 102) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                shareBitmap(this.layoutcollapse);
            } else {
                Utils.showToast(this, getString(R.string.permission_not_granted), 1, false);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            case R.id.btnRetry:
                if (Utils.isNetworkAvailable(this)) {
                    this.vHide.setVisibility(View.GONE);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void addTournamentToFavorite() {
        int i = 1;
        //int i2 = this.tournamentId;
        if (this.isFavorite == 1) {
            i = 0;
        }
//        final SetTournametAsFavoriteRequest request = new SetTournametAsFavoriteRequest(i2, i);
//        ApiCallManager.enqueue("endorse-player", CricHeroes.apiClient.setTournamentAsFavorite(Utils.udid(this), CricHeroes.getApp().getAccessToken(), request), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                if (err != null) {
//                    Logger.m176d("err " + err);
//                    return;
//                }
//                JsonObject jsonObject = (JsonObject) response.getData();
//                if (jsonObject != null) {
//                    Logger.m176d("jsonObject " + jsonObject.toString());
//                    try {
//                        JSONObject object = new JSONObject(jsonObject.toString());
//                        if (request.isFavourite == 1) {
//                            Utils.showToast(TournamentMatchesActivity.this, object.optString("message"), 2, false);
//                        }
//                        TournamentMatchesActivity.this.isFavorite = request.isFavourite;
//                        if (request.isFavourite == 0) {
//                            TournamentMatchesActivity.this.ivFav.setLiked(Boolean.valueOf(false));
//                        } else {
//                            TournamentMatchesActivity.this.ivFav.setLiked(Boolean.valueOf(true));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    public void onTabSelected(Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
        if (tab.getPosition() == 1) {
            this.showShareMenu = true;
        } else {
            this.showShareMenu = false;
        }
        invalidateOptionsMenu();
//        switch (tab.getPosition()) {
//            case 1:
//                if (this.leaderBoardFragment == null) {
//                    this.leaderBoardFragment = (LeaderBoardFragment) this.adapter.getFragment(tab.getPosition());
//                    if (this.leaderBoardFragment != null) {
//                        this.leaderBoardFragment.setData();
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 2:
//                if (this.trackerFragment == null) {
//                    this.trackerFragment = (BoundaryTrackerFragment) this.adapter.getFragment(tab.getPosition());
//                    if (this.trackerFragment != null) {
//                        this.trackerFragment.setData(this.tournamentId);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 3:
//                if (this.heroesFragment == null) {
//                    this.heroesFragment = (HeroesFragment) this.adapter.getFragment(tab.getPosition());
//                    if (this.heroesFragment != null) {
//                        this.heroesFragment.setTournamentHeroesData(this.tournamentId);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 4:
//                if (this.sponsorsFragment == null) {
//                    this.sponsorsFragment = (SponsorsFragment) this.adapter.getFragment(tab.getPosition());
//                    if (this.sponsorsFragment != null) {
//                        this.sponsorsFragment.setData(this.premium);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 6:
//                if (this.standingsFragment == null) {
//                    this.standingsFragment = (StandingsFragment) this.adapter.getFragment(tab.getPosition());
//                    if (this.standingsFragment != null) {
//                        this.standingsFragment.setData();
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 7:
//                if (this.mediaFragment == null) {
//                    this.mediaFragment = (TournamentMediaFragment) this.adapter.getFragment(tab.getPosition());
//                    if (this.mediaFragment != null) {
//                        this.mediaFragment.setData();
//                        return;
//                    }
//                    return;
//                }
//                return;
//            default:
//                return;
//        }
    }

    public void onTabUnselected(Tab tab) {
    }

    public void onTabReselected(Tab tab) {
    }

    public void onStop() {
        //  ApiCallManager.cancelCall("get_tournament_detail");
        super.onStop();
    }

    public void getTournamentDetail() {
        // ApiCallManager.enqueue("get_tournament_detail", CricHeroes.apiClient.getTournamentDetail(Utils.udid(this), CricHeroes.getApp().getAccessToken(), this.tournamentId), new C13798());
    }

    private void shareBitmap(View rowView) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(rowView.getWidth(), rowView.getHeight(), Config.ARGB_8888);
            Bitmap dataBitmap = null;
            rowView.draw(new Canvas(bitmap));
//            if (this.adapter.getFragment(1) instanceof LeaderBoardFragment) {
//                LeaderBoardFragment fragment = (LeaderBoardFragment) this.adapter.getFragment(1);
//                if (fragment.getActivity() != null) {
//                    dataBitmap = fragment.shareStats();
//                }
//            }
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
            Bitmap finalBit = getLogoBitmap(temp);
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + (getApplicationContext().getPackageName() + File.separator + "photo-picker") + File.separator);
            root.mkdirs();
            String fname = "tournamentLeaderBoard.jpg";
            File file = new File(root, "tournamentLeaderBoard.jpg");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fOut = new FileOutputStream(file);
            finalBit.compress(CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            System.out.println("path " + file.getAbsolutePath());
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            intent.putExtra("android.intent.extra.TEXT", "Leader board of team " + this.title);
//            intent.addFlags(1);
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getLogoBitmap(Bitmap bitmap) {
        try {
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            Bitmap link = Bitmap.createBitmap(bitmap.getWidth(), 80, Config.ARGB_8888);
            Canvas c3 = new Canvas(link);
            Typeface tfBold = Typeface.createFromAsset(getAssets(), getString(R.string.font_sourcesans_pro_regular));
            Paint textPaint = new Paint();
            textPaint.setColor(ContextCompat.getColor(this, R.color.black));
            textPaint.setTextAlign(Align.CENTER);
            textPaint.setTypeface(tfBold);
            textPaint.setTextSize(40.0f);
            c3.drawColor(ContextCompat.getColor(this, R.color.background_color));
            c3.drawText(getString(R.string.website_link), (float) (c3.getWidth() / 2), 30.0f, textPaint);
            Bitmap finalBit = Bitmap.createBitmap(bitmap.getWidth(), (bitmap.getHeight() + icon.getHeight()) + link.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(finalBit);
            canvas.drawColor(ContextCompat.getColor(this, R.color.background_color));
            canvas.drawBitmap(icon, (float) ((bitmap.getWidth() / 2) - (icon.getWidth() / 2)), 10.0f, null);
            canvas.drawBitmap(bitmap, 0.0f, (float) (icon.getHeight() + 10), null);
            canvas.drawBitmap(link, 0.0f, (float) ((icon.getHeight() + bitmap.getHeight()) + 30), null);
            return finalBit;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
