package pasu.vadivasal.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import pasu.vadivasal.BaseActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.Utils;


public class PlayerProfileActivity extends BaseActivity implements OnTabSelectedListener, OnClickListener {
    public static final int RC_FILTER = 501;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private PlayerProfilePagerAdapter adapter;
    //    @BindView(2131755178)
//    AppBarLayout appBarLayout;
//    String associationYearId = null;
//    String assoiciationId;
//    private AwardFragment awardFragment;
//    private BadgesFragment badgesFragment;
//    private String ballType = null;
//    ArrayList<FilterModel> ballTypes = new ArrayList();
//    @BindView(2131755398)
//    TextView btnFollow;
//    @BindView(2131755683)
//    Button btnRetry;
//    boolean cameraGranted = false;
//    @BindView(2131755397)
//    CardView card_Follow;
//    private int cityId;
//    @BindView(2131755179)
    CollapsingToolbarLayout collapsing_toolbar;
    //    private ConnectionsFragment connectionsFragment;
//    @BindView(2131755394)
//    View divider;
//    @BindView(2131755365)
//    FloatingActionButton fabShare;
//    private int filterCount = 0;
//    private ArrayList<FilterPlayerProfile> filterData = new ArrayList();
//    private HashMap<Integer, String> filterMap = new HashMap();
//    @BindView(2131755386)
//    ImageView imgBlurBackground;
//    @BindView(2131755389)
//    ImageView imgPlayer;
//    @BindView(2131755390)
//    ImageView imgPremiumIcon;
//    private boolean isEdit;
//    private int isFollow;
    boolean isMyProfile;
    //    @BindView(2131755177)
//    LinearLayout layoutNoInternet;
//    @BindView(2131755202)
//    public RelativeLayout layoutPlayerProfile;
//    @BindView(2131755197)
    public RelativeLayout layoutcollapse;
    //    private String matchInning = null;
//    ArrayList<FilterModel> matchInnings = new ArrayList();
//    private MatchesFragment matchesFragment;
//    private MediaFragment mediaFragment;
//    private MyInfoFragment myInfoFragment;
//    private PlayerData playerData;
//    private int playerId;
//    private PlayerInfoFragment playerInfoFragment;
//    private List<Player> playerListFollowers = new ArrayList();
//    private List<Player> playerListFollowing = new ArrayList();
//    private String playerName = "Profile";
    private int position;
    //    boolean showFilter;
//    private StateFragment stateFragment;
//    @BindView(2131755181)
    TabLayout tabLayoutScoreCard;
    //    private TeamFragment teamFragment;
//    private String teamId = null;
//    ArrayList<FilterModel> teams = new ArrayList();
    private SpannableString titleSpan;
    //    @BindView(2131755180)
    Toolbar toolbar;
    //    private String tournamentId = null;
//    ArrayList<FilterModel> tournaments = new ArrayList();
//    @BindView(2131755391)
//    TextView tvPlayerName;
//    private TextView txtViewCount;
//    private String userImagePath = "";
//    @BindView(2131755183)
    public ViewPager viewPager;
    private AppBarLayout app_bar_layout;
    private boolean isExpanded;

    class C08281 implements OnClickListener {
        C08281() {
        }

        public void onClick(View view) {
            Utils.showToast(PlayerProfileActivity.this, PlayerProfileActivity.this.getString(R.string.msg_under_development), 3, false);
        }
    }

    class C08292 implements OnClickListener {
        C08292() {
        }

        public void onClick(View view) {
            //  PlayerProfileActivity.this.getPlayerProfileApi();
        }
    }

    class C08303 implements OnClickListener {
        C08303() {
        }

        public void onClick(View view) {
//            PlayerProfileActivity.this.userImagePath = null;
//            PlayerProfileActivity.this.requestForCameraPermission();
        }
    }

    class C08314 implements OnOffsetChangedListener {
        boolean isShow = false;
        int scrollRange = -1;

        C08314() {
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (this.scrollRange == -1) {
                this.scrollRange = appBarLayout.getTotalScrollRange();
            }
            if (this.scrollRange + verticalOffset == 0) {
                PlayerProfileActivity.this.collapsing_toolbar.setTitle(PlayerProfileActivity.this.titleSpan);
                this.isShow = true;
            } else if (this.isShow) {
                PlayerProfileActivity.this.collapsing_toolbar.setTitle(" ");
                this.isShow = false;
            }
        }
    }

    class C08325 implements DialogInterface.OnClickListener {
        C08325() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -2:
                    dialog.dismiss();
                    return;
                case -1:
                    dialog.dismiss();
                    PlayerProfileActivity.this.followUnfollowPlayer();
                    return;
                default:
                    return;
            }
        }
    }

//    class C08336 extends CallbackAdapter {
//        C08336() {
//        }
//
//        public void onApiResponse(ErrorResponse err, BaseResponse response) {
//            if (err != null) {
//                Logger.m176d("err " + err);
//                return;
//            }
//            JsonObject jsonObject = (JsonObject) response.getData();
//            if (jsonObject != null) {
//                Logger.m176d("jsonObject " + jsonObject.toString());
//                PlayerProfileActivity.this.isFollow = PlayerProfileActivity.this.isFollow == 1 ? 0 : 1;
//                if (PlayerProfileActivity.this.isFollow == 1) {
//                    Utils.showToast(PlayerProfileActivity.this, PlayerProfileActivity.this.getString(R.string.follow_player_msg), 2, true);
//                }
//                PlayerProfileActivity.this.changeFollowStatus();
//                if (PlayerProfileActivity.this.connectionsFragment == null) {
//                    PlayerProfileActivity.this.connectionsFragment = (ConnectionsFragment) PlayerProfileActivity.this.adapter.getFragment(PlayerProfileActivity.this.tabLayoutScoreCard.getTabCount() - 2);
//                    if (PlayerProfileActivity.this.connectionsFragment != null) {
//                        PlayerProfileActivity.this.connectionsFragment.setData(PlayerProfileActivity.this.playerId, PlayerProfileActivity.this.teamId, PlayerProfileActivity.this.tournamentId, PlayerProfileActivity.this.ballType, PlayerProfileActivity.this.matchInning, PlayerProfileActivity.this.playerListFollowing, PlayerProfileActivity.this.playerListFollowers);
//                        return;
//                    }
//                    return;
//                }
//                PlayerProfileActivity.this.connectionsFragment.setData(PlayerProfileActivity.this.playerId, PlayerProfileActivity.this.teamId, PlayerProfileActivity.this.tournamentId, PlayerProfileActivity.this.ballType, PlayerProfileActivity.this.matchInning, PlayerProfileActivity.this.playerListFollowing, PlayerProfileActivity.this.playerListFollowers);
//            }
//        }
//    }

    class C08347 implements OnClickListener {
        C08347() {
        }

        public void onClick(View v) {
            PlayerProfileActivity.this.onFilterActivity();
        }
    }

    class C08369 implements OnClickListener {
        C08369() {
        }

        public void onClick(View view) {
//            if (PlayerProfileActivity.this.playerData != null) {
//                Utils.showFullImage(PlayerProfileActivity.this, PlayerProfileActivity.this.playerData.getProfilePhoto());
//            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_player_profile);
        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        layoutcollapse = (RelativeLayout) findViewById(R.id.layoutcollapse);
        tabLayoutScoreCard = (TabLayout) findViewById(R.id.tabLayoutPlayer);
        viewPager = (ViewPager) findViewById(R.id.pagerPlayer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        this.playerId = getIntent().getIntExtra(AppConstants.EXTRA_PLAYER_ID, 0);
//        this.assoiciationId = getIntent().getStringExtra(AppConstants.EXTRA_ASSOCIATION_ID);
//        this.associationYearId = getIntent().getStringExtra(AppConstants.ASSOCIATIONS_YEARS);
//        Utils.removeNotifications(this);
        //Log.e("PLAYER ID ", "IS " + this.playerId);
        this.collapsing_toolbar.setTitle(" ");
        setTitleSpan(" ");

        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_photos));
        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_profile));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_awards));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_badges));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_teams));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_photos));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_connections));
//        this.tabLayoutScoreCard.addTab(this.tabLayoutScoreCard.newTab().setText((int) R.string.tab_title_profile));
        this.tabLayoutScoreCard.setTabGravity(0);
        this.tabLayoutScoreCard.setTabMode(0);
        this.isMyProfile = getIntent().getBooleanExtra(AppConstants.EXTRA_MY_PROFILE, false);
        FragmentManager manager = getSupportFragmentManager();
        this.position = getIntent().getIntExtra(AppConstants.EXTRA_POSITION, 0);
        this.adapter = new PlayerProfilePagerAdapter(manager, this.tabLayoutScoreCard.getTabCount(), this.isMyProfile);
        this.viewPager.setOffscreenPageLimit(this.tabLayoutScoreCard.getTabCount());
        this.viewPager.setAdapter(this.adapter);
        System.out.println("POS " + this.position);
        this.viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this.tabLayoutScoreCard));
        this.tabLayoutScoreCard.addOnTabSelectedListener(this);
        this.viewPager.setCurrentItem(this.position);
        this.viewPager.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_up));
        app_bar_layout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, final int verticalOffset) {


//                if (Math.abs(verticalOffset) != 0 || (appBarLayout.getTotalScrollRange()-(appBarLayout.getTotalScrollRange()/2))==0) {
//
//                    if (isExpanded) {
//                        appBarLayout.setExpanded(false);
//                    } else {
//                        appBarLayout.setExpanded(true);
//                    }
//
//
//                    //if(appBarLayout.getTotalScrollRange()>(appBarLayout.getTotalScrollRange()/2)){
////                                //   collapsing_toolbar.setExpanded
////
////                                appBarLayout.setExpanded(true);
////                            }else{
////                                appBarLayout.setExpanded(false);
////                            }
//
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            System.out.println("nagaagagaa"+(Math.abs(verticalOffset)+"___"+appBarLayout.getTotalScrollRange()));
////                            if(appBarLayout.getTotalScrollRange()>(appBarLayout.getTotalScrollRange()/2)){
////                                //   collapsing_toolbar.setExpanded
////
////                                appBarLayout.setExpanded(true);
////                            }else{
////                                appBarLayout.setExpanded(false);
////                            }
////                        }
////                    },500);
//                    //  Collapsed
//
//
//                } else {
//                    //Expanded
//
//
//                }
            }
        });
//        this.fabShare.setVisibility(8);
//        this.fabShare.setOnClickListener(new C08281());
//        this.layoutNoInternet.setVisibility(8);
//        this.btnFollow.setOnClickListener(this);
//        this.btnRetry.setOnClickListener(this);
//        Utils.hideDivider(this.divider);
//        this.isEdit = getIntent().getBooleanExtra("edit", false);
        //  Picasso.with(this).load((int) R.drawable.ic_placeholder_player).into(this.imgPlayer);
        if (Utils.isNetworkAvailable(this)) {
            // getPlayerProfileApi();
        } else {
            loadNoInternetConnectionView(R.id.layoutNoInternet, R.id.container, new C08292());
        }
        if (this.isMyProfile) {
//            this.playerName = CricHeroes.getApp().getCurrentUser().getName();
//            this.tvPlayerName.setText(this.playerName);
//            this.btnFollow.setVisibility(8);
//            this.card_Follow.setVisibility(8);
//            this.imgPlayer.setOnClickListener(new C08303());
        }
//        if (!CricHeroes.getApp().isGuestUser() && CricHeroes.getApp().getCurrentUser().getUserId() == this.playerId) {
//            this.btnFollow.setVisibility(8);
//        }
//        this.filterMap.put(Integer.valueOf(0), getString(R.string.title_teams));
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("PlayerProfile", "on new intent : " + intent.getExtras());
    }

    private void setTitleCollapse() {
        //  this.appBarLayout.addOnOffsetChangedListener(new C08314());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFollow:
//                if (this.playerData != null && !CricHeroes.getApp().isGuestUser()) {
//                    if (this.isFollow == 1) {
//                        DialogInterface.OnClickListener listener = new C08325();
//                        Utils.showAlert(this, getString(R.string.following), getString(R.string.alert_msg_unfollow, new Object[]{this.playerData.getName()}), getString(R.string.unfollow), getString(R.string.btn_cancel), listener, true);
//                        return;
//                    }
//                    followUnfollowPlayer();
//                    return;
//                }
                return;
            default:
                return;
        }
    }

    private void followUnfollowPlayer() {
//        if (this.playerData != null) {
//            Call<JsonObject> call;
//            PlayerIdRequest request = new PlayerIdRequest(String.valueOf(this.playerData.getPlayerId()));
//            if (this.isFollow == 0) {
//                call = CricHeroes.apiClient.followPlayer(Utils.udid(this), CricHeroes.getApp().getAccessToken(), request);
//            } else {
//                call = CricHeroes.apiClient.unFollowPlayer(Utils.udid(this), CricHeroes.getApp().getAccessToken(), request);
//            }
//            ApiCallManager.enqueue("follow-player", call, new C08336());
//        }
    }

    private void changeFollowStatus() {
//        if (CricHeroes.getApp().isGuestUser()) {
//            this.btnFollow.setVisibility(8);
//        }
//        if (this.isFollow == 1) {
//            this.btnFollow.setText(getString(R.string.following));
//            this.btnFollow.setTextColor(getResources().getColor(R.color.gray_text));
//            this.card_Follow.setBackgroundResource(R.drawable.ripple_btn_from_gallary_corner);
//            return;
//        }
//        this.btnFollow.setText(getString(R.string.follow));
//        this.btnFollow.setTextColor(getResources().getColor(R.color.white));
//        this.card_Follow.setBackgroundResource(R.drawable.ripple_btn_save_corner);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Utils.finishActivitySlide(this);
                break;
//            case R.id.action_share:
//                shareState();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareState() {
//        if (VERSION.SDK_INT < 23) {
//            shareBitmap(this.layoutcollapse);
//        } else if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
//            shareBitmap(this.layoutcollapse);
//        } else {
//            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 102);
//        }
    }

    private void shareBitmap(View rowView) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(rowView.getWidth(), rowView.getHeight(), Config.ARGB_8888);
            Bitmap dataBitmap = null;
            rowView.draw(new Canvas(bitmap));

//            if (this.adapter.getFragment(1) instanceof StateFragment) {
//                StateFragment fragment = (StateFragment) this.adapter.getFragment(1);
//                if (fragment.getActivity() != null) {
//                    dataBitmap = fragment.shareStats("Statistics of player " + this.playerName);
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
            String fname = "playerStat.jpg";
            File file = new File(root, "playerStat.jpg");
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
            //intent.putExtra("android.intent.extra.TEXT", "Statistics of player " + this.playerName);
            intent.addFlags(1);
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getLogoBitmap(Bitmap bitmap) {
        try {
//            Canvas c1 = new Canvas(bitmap);
//            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.cricheroes_logo_white);
//            c1.drawBitmap(icon, (float) ((bitmap.getWidth() / 2) - (icon.getWidth() / 2)), 20.0f, null);
//            Bitmap link = Bitmap.createBitmap(c1.getWidth(), 80, Config.ARGB_8888);
//            Canvas c3 = new Canvas(link);
//            Typeface tfBold = Typeface.createFromAsset(getAssets(), getString(R.string.font_sourcesans_pro_regular));
//            Paint textPaint = new Paint();
//            textPaint.setColor(ContextCompat.getColor(this, R.color.black_text));
//            textPaint.setTextAlign(Align.CENTER);
//            textPaint.setTypeface(tfBold);
//            textPaint.setTextSize(40.0f);
//            c3.drawColor(ContextCompat.getColor(this, R.color.background_color));
//            c3.drawText(getString(R.string.website_link), (float) (c3.getWidth() / 2), 30.0f, textPaint);
//            Bitmap finalBit = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() + link.getHeight(), Config.ARGB_8888);
//            Canvas canvas = new Canvas(finalBit);
//            canvas.drawBitmap(bitmap, 0.0f, (float) icon.getHeight(), null);
//            canvas.drawBitmap(link, 0.0f, (float) (bitmap.getHeight() + 20), null);
            //   return finalBit;
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onFilterActivity() {
//        Intent intent = new Intent(this, FilterPlayerProfileActivity.class);
//        intent.putExtra("filterMap", this.filterMap);
//        intent.putExtra(AppConstants.EXTRA_PLAYER_ID, this.playerId);
//        intent.putExtra("title", "Stats");
//        intent.putExtra(AppConstants.EXTRA_TOURNAMENTS, this.tournaments);
//        intent.putExtra(AppConstants.EXTRA_TEAMS, this.teams);
//        intent.putExtra(AppConstants.EXTRA_BALLTYPE, this.ballTypes);
//        intent.putExtra(AppConstants.EXTRA_MATCH_INNING, this.matchInnings);
//        intent.putParcelableArrayListExtra(AppConstants.EXTRA_FILTER_LIST, this.filterData);
//        startActivityForResult(intent, 501);
//        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_book_ground, menu);
//        MenuItem itemShare = menu.findItem(R.id.action_share);
//        menu.findItem(R.id.action_filter).setVisible(true);
//        View notificaitons = menu.findItem(R.id.action_filter).getActionView();
//        this.txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
//        updateFilterCount(this.filterCount);
//        notificaitons.setOnClickListener(new C08347());
//        if (this.showFilter) {
//            itemShare.setVisible(true);
//        } else {
//            itemShare.setVisible(false);
//        }
        return true;
    }

    public void updateFilterCount(final int count) {
//        if (this.txtViewCount != null) {
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    if (count == 0) {
//                        PlayerProfileActivity.this.txtViewCount.setVisibility(8);
//                        return;
//                    }
//                    PlayerProfileActivity.this.txtViewCount.setVisibility(0);
//                    PlayerProfileActivity.this.txtViewCount.setText(Integer.toString(count));
//                }
//            });
//        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Utils.finishActivitySlide(this);
    }

    public void onTabSelected(Tab tab) {
        System.out.println(" POS " + tab.getPosition());
        this.viewPager.setCurrentItem(tab.getPosition());
//        if (tab.getPosition() == 1) {
//            this.showFilter = true;
//        } else {
//            this.showFilter = false;
//        }
//        invalidateOptionsMenu();
//        if (!this.isMyProfile) {
//            this.imgPlayer.setOnClickListener(new C08369());
//        }
        initFragment(tab.getPosition());
    }

    private void initFragment(int position) {
        System.out.println(" position " + position);
        switch (position) {
//            case 0:
//                if (this.matchesFragment == null) {
//                    this.matchesFragment = (MatchesFragment) this.adapter.getFragment(position);
//                    if (this.matchesFragment != null) {
//                        this.matchesFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 1:
//                if (this.stateFragment == null) {
//                    this.stateFragment = (StateFragment) this.adapter.getFragment(position);
//                    if (this.stateFragment != null) {
//                        this.stateFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 2:
//                if (this.awardFragment == null) {
//                    this.awardFragment = (AwardFragment) this.adapter.getFragment(position);
//                    if (this.awardFragment != null) {
//                        this.awardFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 3:
//                if (this.badgesFragment == null) {
//                    this.badgesFragment = (BadgesFragment) this.adapter.getFragment(position);
//                    if (this.badgesFragment != null) {
//                        this.badgesFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning, this.playerData);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 4:
//                if (this.teamFragment == null) {
//                    this.teamFragment = (TeamFragment) this.adapter.getFragment(position);
//                    if (this.teamFragment != null) {
//                        this.teamFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 5:
//                if (this.mediaFragment == null) {
//                    this.mediaFragment = (MediaFragment) this.adapter.getFragment(position);
//                    if (this.mediaFragment != null) {
//                        this.mediaFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 6:
//                if (this.connectionsFragment == null) {
//                    this.connectionsFragment = (ConnectionsFragment) this.adapter.getFragment(position);
//                    if (this.connectionsFragment != null) {
//                        this.connectionsFragment.setData(this.playerId, this.teamId, this.tournamentId, this.ballType, this.matchInning, this.playerListFollowing, this.playerListFollowers);
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 7:
//                if (this.isMyProfile) {
//                    if (this.myInfoFragment == null) {
//                        this.myInfoFragment = (MyInfoFragment) this.adapter.getFragment(position);
//                        if (this.myInfoFragment != null) {
//                            this.myInfoFragment.setData(this.playerData, this.cityId);
//                            return;
//                        }
//                        return;
//                    }
//                    return;
//                } else if (this.playerInfoFragment == null) {
//                    this.playerInfoFragment = (PlayerInfoFragment) this.adapter.getFragment(position);
//                    if (this.playerInfoFragment != null) {
//                        this.playerInfoFragment.setData(this.playerData);
//                        return;
//                    }
//                    return;
//                } else {
//                    return;
//                }
//            default:
//                return;
        }
    }

    public void onTabUnselected(Tab tab) {
    }

    public void onTabReselected(Tab tab) {
    }

    //    private void getPlayerProfileApi() {
//        final Dialog dialog = Utils.showProgress(this, true);
//        ApiCallManager.enqueue("get_player_profile", CricHeroes.apiClient.getPlayerProfile(Utils.udid(this), CricHeroes.getApp().getAccessToken(), this.playerId), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                if (err != null) {
//                    Utils.hideProgress(dialog);
//                    Logger.m176d("err " + err);
//                    Utils.showToast(PlayerProfileActivity.this, err.getMessage(), 1, false);
//                    PlayerProfileActivity.this.setTitleCollapse();
//                    return;
//                }
//                JsonObject jsonObject = (JsonObject) response.getData();
//                if (jsonObject == null) {
//                    Utils.hideProgress(dialog);
//                    Utils.showToast(PlayerProfileActivity.this, "Please try again.", 1, false);
//                    return;
//                }
//                try {
//                    int i;
//                    FilterPlayerProfile filterPlayerProfile;
//                    JSONObject jsonObj = new JSONObject(jsonObject.toString());
//                    Logger.m176d("getPlayerProfileApi " + jsonObj);
//                    JSONArray arrayFilter = jsonObj.optJSONArray("filter");
//                    JSONArray arrayFollowing = jsonObj.optJSONArray("following");
//                    JSONArray arrayFollowers = jsonObj.optJSONArray("follower");
//                    PlayerData playerData = new PlayerData();
//                    playerData.setPlayerId(jsonObj.optString("player_id"));
//                    playerData.setName(jsonObj.optString("name"));
//                    PlayerProfileActivity.this.cityId = jsonObj.optInt("city_id", 1);
//                    PlayerProfileActivity.this.isFollow = jsonObj.optInt("is_follow", 0);
//                    PlayerProfileActivity.this.tvPlayerName.setText(playerData.getName());
//                    PlayerProfileActivity.this.playerName = playerData.getName();
//                    PlayerProfileActivity.this.setTitleSpan(PlayerProfileActivity.this.playerName);
//                    playerData.setProfilePhoto(jsonObj.optString(UpdateUserProfile.PROFILE_PHOTO));
//                    if (Utils.isEmptyString(playerData.getProfilePhoto())) {
//                        Picasso.with(PlayerProfileActivity.this).load((int) R.drawable.ic_placeholder_player).resize(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT, 200).transform(new BlurTransformation(PlayerProfileActivity.this)).into(PlayerProfileActivity.this.imgBlurBackground);
//                        PlayerProfileActivity.this.imgPlayer.setImageResource(R.drawable.ic_placeholder_player);
//                    } else {
//                        Picasso.with(PlayerProfileActivity.this).load(playerData.getProfilePhoto() + AppConstants.THUMB_IMAGE).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).transform(new BlurTransformation(PlayerProfileActivity.this)).into(PlayerProfileActivity.this.imgBlurBackground);
//                        Picasso.with(PlayerProfileActivity.this).load(playerData.getProfilePhoto() + AppConstants.THUMB_IMAGE).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).into(PlayerProfileActivity.this.imgPlayer);
//                    }
//                    PlayerProfileActivity.this.changeFollowStatus();
//                    playerData.setDob(jsonObj.optString(UpdateUserProfile.DOB));
//                    playerData.setPlayerSkill(jsonObj.optString(UpdateUserProfile.PLAYER_SKILL));
//                    playerData.setCityName(jsonObj.optString("city_name"));
//                    playerData.setBattingHand(jsonObj.optString(UpdateUserProfile.BATTING_HAND));
//                    playerData.setPlayingRole(jsonObj.optString("playing_role"));
//                    playerData.setBowlingTypeCode(jsonObj.optString("bowling_type_code"));
//                    playerData.setBowlingStyle(jsonObj.optString("bowling_style"));
//                    playerData.setMatches(jsonObj.optString("matches"));
//                    playerData.setRuns(jsonObj.optString("runs"));
//                    playerData.setWickets(jsonObj.optString("wickets"));
//                    playerData.setCatches(jsonObj.optString("catches"));
//                    playerData.setStumping(jsonObj.optString("stumping"));
//                    playerData.setRunout(jsonObj.optString("runout"));
//                    playerData.setAge(jsonObj.optString("age"));
//                    PlayerProfileActivity.this.playerData = playerData;
//                    PlayerProfileActivity.this.filterData.clear();
//                    for (i = 0; i < arrayFilter.length(); i++) {
//                        String assoId = arrayFilter.getJSONObject(i).optString(AppConstants.EXTRA_ASSOCIATION_ID);
//                        String assoYearId = arrayFilter.getJSONObject(i).optString("association_year_id");
//                        if ((PlayerProfileActivity.this.associationYearId == null && PlayerProfileActivity.this.assoiciationId == null) || (assoId == null && assoYearId == null)) {
//                            PlayerProfileActivity.this.filterData.add(new FilterPlayerProfile(arrayFilter.getJSONObject(i)));
//                        } else if (assoId.equalsIgnoreCase(PlayerProfileActivity.this.assoiciationId) && assoYearId.equalsIgnoreCase(PlayerProfileActivity.this.associationYearId)) {
//                            filterPlayerProfile = new FilterPlayerProfile(arrayFilter.getJSONObject(i));
//                            if (Utils.isEmptyString(PlayerProfileActivity.this.tournamentId)) {
//                                PlayerProfileActivity.this.filterCount = PlayerProfileActivity.this.filterCount + 1;
//                                PlayerProfileActivity.this.tournamentId = filterPlayerProfile.getTournamentId();
//                            } else if (!PlayerProfileActivity.this.tournamentId.contains(filterPlayerProfile.getTournamentId())) {
//                                PlayerProfileActivity.this.filterCount = PlayerProfileActivity.this.filterCount + 1;
//                                PlayerProfileActivity.this.tournamentId = PlayerProfileActivity.this.tournamentId + "," + filterPlayerProfile.getTournamentId();
//                            }
//                            PlayerProfileActivity.this.filterData.add(filterPlayerProfile);
//                        }
//                    }
//                    if (PlayerProfileActivity.this.filterCount > 0) {
//                        PlayerProfileActivity.this.tournaments = new ArrayList();
//                        for (i = 0; i < PlayerProfileActivity.this.filterData.size(); i++) {
//                            filterPlayerProfile = (FilterPlayerProfile) PlayerProfileActivity.this.filterData.get(i);
//                            if (!Utils.isEmptyString(filterPlayerProfile.getTournamentId())) {
//                                FilterModel filterModel = new FilterModel();
//                                filterModel.setCheck(true);
//                                filterModel.setId(filterPlayerProfile.getTournamentId());
//                                filterModel.setName(filterPlayerProfile.getTournamentName());
//                                PlayerProfileActivity.this.tournaments.add(filterModel);
//                            }
//                        }
//                        Map<Integer, FilterModel> map = new LinkedHashMap();
//                        Iterator it = PlayerProfileActivity.this.tournaments.iterator();
//                        while (it.hasNext()) {
//                            FilterModel ays = (FilterModel) it.next();
//                            map.put(Integer.valueOf(Integer.parseInt(ays.getId())), ays);
//                        }
//                        PlayerProfileActivity.this.tournaments.clear();
//                        PlayerProfileActivity.this.tournaments.addAll(map.values());
//                    }
//                    PlayerProfileActivity.this.invalidateOptionsMenu();
//                    if (arrayFollowing != null && arrayFollowing.length() > 0) {
//                        PlayerProfileActivity.this.playerListFollowing.clear();
//                        for (i = 0; i < arrayFollowing.length(); i++) {
//                            PlayerProfileActivity.this.playerListFollowing.add(new Player(arrayFollowing.getJSONObject(i), false));
//                        }
//                    }
//                    if (arrayFollowers != null && arrayFollowers.length() > 0) {
//                        PlayerProfileActivity.this.playerListFollowers.clear();
//                        for (i = 0; i < arrayFollowers.length(); i++) {
//                            PlayerProfileActivity.this.playerListFollowers.add(new Player(arrayFollowers.getJSONObject(i), false));
//                        }
//                    }
//                    if (PlayerProfileActivity.this.position == 0) {
//                        PlayerProfileActivity.this.initFragment(PlayerProfileActivity.this.position);
//                    } else if (PlayerProfileActivity.this.position == 1) {
//                        PlayerProfileActivity.this.initFragment(0);
//                        PlayerProfileActivity.this.initFragment(PlayerProfileActivity.this.position);
//                    } else {
//                        PlayerProfileActivity.this.initFragment(PlayerProfileActivity.this.position);
//                    }
//                    if (PlayerProfileActivity.this.isEdit) {
//                        PlayerProfileActivity.this.viewPager.setCurrentItem(PlayerProfileActivity.this.tabLayoutScoreCard.getTabCount() - 1);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Utils.hideProgress(dialog);
//                PlayerProfileActivity.this.setTitleCollapse();
//            }
//        });
//    }
//
    private void setTitleSpan(String title) {
        this.titleSpan = new SpannableString(title);
        //this.titleSpan.setSpan(new TypefaceSpan(this, getString(R.string.font_roboto_slab_regular)), 0, this.titleSpan.length(), 33);
    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == -1) {
//            switch (requestCode) {
//                case 501:
//                    if (data != null) {
//                        this.filterCount = 0;
//                        this.tournaments = data.getParcelableArrayListExtra(AppConstants.EXTRA_TOURNAMENTS);
//                        this.teams = data.getParcelableArrayListExtra(AppConstants.EXTRA_TEAMS);
//                        this.ballTypes = data.getParcelableArrayListExtra(AppConstants.EXTRA_BALLTYPE);
//                        this.matchInnings = data.getParcelableArrayListExtra(AppConstants.EXTRA_MATCH_INNING);
//                        this.tournamentId = getFilterTournamentIds();
//                        this.teamId = getFilterTeamIds();
//                        this.ballType = getFilterBallType();
//                        this.matchInning = getFilterMatchInning();
//                        if (this.filterCount > 0) {
//                            updateFilterCount(this.filterCount);
//                        } else {
//                            updateFilterCount(0);
//                        }
//                        invalidateOptionsMenu();
//                        nullAllFragments();
//                        return;
//                    }
//                    return;
//                default:
//                    return;
//            }
//        }
//    }
//
//    private void nullAllFragments() {
//        this.matchesFragment = null;
//        this.stateFragment = null;
//        this.awardFragment = null;
//        this.teamFragment = null;
//        this.badgesFragment = null;
//        this.mediaFragment = null;
//        initFragment(this.viewPager.getCurrentItem());
//    }
//
//    private String getFilterTournamentIds() {
//        if (this.tournaments == null || this.tournaments.size() <= 0) {
//            return null;
//        }
//        String ids = null;
//        for (int i = 0; i < this.tournaments.size(); i++) {
//            FilterModel model = (FilterModel) this.tournaments.get(i);
//            if (model.isCheck()) {
//                this.filterCount++;
//                if (Utils.isEmptyString(ids)) {
//                    ids = model.getId();
//                } else {
//                    ids = ids + "," + model.getId();
//                }
//            }
//        }
//        return ids;
//    }
//
//    private String getFilterTeamIds() {
//        if (this.teams == null || this.teams.size() <= 0) {
//            return null;
//        }
//        String ids = null;
//        for (int i = 0; i < this.teams.size(); i++) {
//            FilterModel model = (FilterModel) this.teams.get(i);
//            if (model.isCheck()) {
//                this.filterCount++;
//                if (Utils.isEmptyString(ids)) {
//                    ids = model.getId();
//                } else {
//                    ids = ids + "," + model.getId();
//                }
//            }
//        }
//        return ids;
//    }
//
//    private String getFilterBallType() {
//        if (this.ballTypes == null || this.ballTypes.size() <= 0) {
//            return null;
//        }
//        String ids = null;
//        for (int i = 0; i < this.ballTypes.size(); i++) {
//            FilterModel model = (FilterModel) this.ballTypes.get(i);
//            if (model.isCheck()) {
//                this.filterCount++;
//                if (Utils.isEmptyString(ids)) {
//                    ids = model.getName();
//                } else {
//                    ids = ids + "," + model.getName();
//                }
//            }
//        }
//        return ids;
//    }
//
//    private String getFilterMatchInning() {
//        if (this.matchInnings == null || this.matchInnings.size() <= 0) {
//            return null;
//        }
//        String ids = null;
//        for (int i = 0; i < this.matchInnings.size(); i++) {
//            FilterModel model = (FilterModel) this.matchInnings.get(i);
//            if (model.isCheck()) {
//                this.filterCount++;
//                if (Utils.isEmptyString(ids)) {
//                    ids = model.getId();
//                } else {
//                    ids = ids + "," + model.getId();
//                }
//            }
//        }
//        return ids;
//    }

    private void requestForCameraPermission() {
        if (checkAndRequestPermissions()) {
            launch();
        }
    }

    private boolean checkAndRequestPermissions() {
        if (VERSION.SDK_INT >= 23) {
            int writepermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
            int readpermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
            int camerapermission = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
            List<String> listPermissionsNeeded = new ArrayList();
            if (writepermission != 0) {
                listPermissionsNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
            if (readpermission != 0) {
                listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
            }
            if (camerapermission != 0) {
                listPermissionsNeeded.add("android.permission.CAMERA");
            } else {
                // this.cameraGranted = true;
            }
            if (!listPermissionsNeeded.isEmpty()) {
                requestPermissions((String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
                return false;
            }
        }
        return true;
    }

    private void launch() {
//        PreferenceUtil.getInstance(this, AppConstants.CAMERA_PREF).putString(AppConstants.KEY_PROFILE_IMAGE_PATH, "");
//        CameraManager.getInst().openCamera(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    int i = 0;
                    while (i < permissions.length) {
                        if (permissions[i].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                            if (grantResults[i] == 0) {
                                Log.e("msg", "storage granted");
                            }
                        } else if (permissions[i].equals("android.permission.READ_EXTERNAL_STORAGE")) {
                            if (grantResults[i] == 0) {
                                Log.e("msg", "READ granted");
                            }
                        } else if (permissions[i].equals("android.permission.CAMERA") && grantResults[i] == 0) {
                            Log.e("msg", "CAMERA granted");
                            //   this.cameraGranted = true;
                        }
                        i++;
                    }
                }
//                if (this.cameraGranted) {
//                    launch();
//                    return;
//                }
                return;
            case 102:
                if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    //  Utils.showToast(this, getString(R.string.permission_not_granted), 1, false);
                    return;
                } else if (this.layoutcollapse != null) {
                    shareBitmap(this.layoutcollapse);
                    return;
                } else {
                    return;
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }

    protected void onResume() {
        super.onResume();
//        String imagePath = PreferenceUtil.getInstance(this, AppConstants.CAMERA_PREF).getString(AppConstants.KEY_PROFILE_IMAGE_PATH);
//        if (!imagePath.equalsIgnoreCase("")) {
//            Log.e("filePath", "= " + imagePath);
//            if (!Utils.isEmptyString(imagePath)) {
//                File file = new File(imagePath);
//                if (this.userImagePath == null) {
//                    Log.e("userImagePath null", "= " + imagePath);
//                    this.userImagePath = imagePath;
//                    uploadPlayerProfilePic();
//                    Picasso.with(this).load(file).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).into(this.imgPlayer);
//                    Picasso.with(this).load(file).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).transform(new BlurTransformation(this)).into(this.imgBlurBackground);
//                } else if (!(Utils.isEmptyString(this.userImagePath) || this.userImagePath.equalsIgnoreCase(imagePath))) {
//                    this.userImagePath = imagePath;
//                    uploadPlayerProfilePic();
//                    Picasso.with(this).load(file).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).into(this.imgPlayer);
//                    Picasso.with(this).load(file).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).transform(new BlurTransformation(this)).into(this.imgBlurBackground);
//                }
//                this.userImagePath = imagePath;
//            }
//        }
    }

    public void onStop() {
//        ApiCallManager.cancelCall("upload_media");
//        ApiCallManager.cancelCall("get_player_profile");
//        ApiCallManager.cancelCall("follow-player");
        super.onStop();
    }

    private void uploadPlayerProfilePic() {
//        Part part = ProgressRequestBody.createMultipartBodyPart(new File(this.userImagePath), null);
//        final Dialog dialog = Utils.showProgress(this, true);
//        ApiCallManager.enqueue("upload_media", CricHeroes.apiClient.uploadMedia(Utils.udid(this), CricHeroes.getApp().isGuestUser() ? null : CricHeroes.getApp().getCurrentUser().getAccessToken(), Integer.valueOf(this.playerId), null, null, null, part), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                Utils.hideProgress(dialog);
//                if (err != null) {
//                    Logger.m176d("err " + err);
//                    Utils.showToast(PlayerProfileActivity.this, err.getMessage(), 1, false);
//                    return;
//                }
//                JsonObject json = (JsonObject) response.getData();
//                Logger.m176d("uploadPlayerProfilePic " + json);
//                try {
//                    User user = CricHeroes.getApp().getCurrentUser();
//                    JSONObject jsonObject = new JSONObject(json.toString());
//                    user.setProfilePhoto(jsonObject.optString("url"));
//                    CricHeroes.getApp().getCurrentUser().setProfilePhoto(jsonObject.optString("url"));
//                    CricHeroes.getApp().loginUser(user.toJson());
//                    CricHeroes.getApp();
//                    CricHeroes.database.insert(UserMaster.TABLE, new ContentValues[]{user.getContentValue()});
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
