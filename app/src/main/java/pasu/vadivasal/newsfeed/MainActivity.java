package pasu.vadivasal.newsfeed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import butterknife.BindView;
import butterknife.OnClick;
import pasu.vadivasal.Profile.UserProfileActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.view.FeedContextMenu;
import pasu.vadivasal.view.FeedContextMenuManager;


//public class MainActivity extends BaseDrawerActivity implements FeedAdapter.OnFeedItemClickListener,
//        FeedContextMenu.OnFeedContextMenuItemClickListener {
//    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
//
//    private static final int ANIM_DURATION_TOOLBAR = 300;
//    private static final int ANIM_DURATION_FAB = 400;
//
//    @BindView(R.id.rvFeed)
//    RecyclerView rvFeed;
//    @BindView(R.id.btnCreate)
//    FloatingActionButton fabCreate;
//    @BindView(R.id.content)
//    CoordinatorLayout clContent;
//
//    private FeedAdapter feedAdapter;
//
//    private boolean pendingIntroAnimation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.newsfeed_activity);
//        setupFeed();
//
//        if (savedInstanceState == null) {
//            pendingIntroAnimation = true;
//        } else {
//            feedAdapter.updateItems(false);
//        }
//    }
//
//    private void setupFeed() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
//            @Override
//            protected int getExtraLayoutSpace(RecyclerView.State state) {
//                return 300;
//            }
//        };
//        rvFeed.setLayoutManager(linearLayoutManager);
//
//        feedAdapter = new FeedAdapter(this);
//        feedAdapter.setOnFeedItemClickListener(this);
//        rvFeed.setAdapter(feedAdapter);
//        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
//            }
//        });
//        rvFeed.setItemAnimator(new FeedItemAnimator());
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
//            showFeedLoadingItemDelayed();
//        }
//    }
//
//    private void showFeedLoadingItemDelayed() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rvFeed.smoothScrollToPosition(0);
//                feedAdapter.showLoadingView();
//            }
//        }, 500);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        if (pendingIntroAnimation) {
//            pendingIntroAnimation = false;
//            startIntroAnimation();
//        }
//        return true;
//    }
//
//    private void startIntroAnimation() {
//        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
//
//        int actionbarSize = Utils.dpToPx(56);
//        getToolbar().setTranslationY(-actionbarSize);
//        getIvLogo().setTranslationY(-actionbarSize);
////        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);
//
////        getToolbar().animate()
////                .translationY(0)
////                .setDuration(ANIM_DURATION_TOOLBAR)
////                .setStartDelay(300);
////        getIvLogo().animate()
////                .translationY(0)
////                .setDuration(ANIM_DURATION_TOOLBAR)
////                .setStartDelay(400);
////        getInboxMenuItem().getActionView().animate()
////                .translationY(0)
////                .setDuration(ANIM_DURATION_TOOLBAR)
////                .setStartDelay(500)
////                .setListener(new AnimatorListenerAdapter() {
////                    @Override
////                    public void onAnimationEnd(Animator animation) {
////                        startContentAnimation();
////                    }
////                })
////                .start();
//        startContentAnimation();
//    }
//
//    private void startContentAnimation() {
//        fabCreate.animate()
//                .translationY(0)
//                .setInterpolator(new OvershootInterpolator(1.f))
//                .setStartDelay(300)
//                .setDuration(ANIM_DURATION_FAB)
//                .start();
//        feedAdapter.updateItems(true);
//    }
//
//    @Override
//    public void onCommentsClick(View v, int position) {
//        final Intent intent = new Intent(this, CommentsActivity.class);
//        int[] startingLocation = new int[2];
//        v.getLocationOnScreen(startingLocation);
//        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
//        startActivity(intent);
//        overridePendingTransition(0, 0);
//    }
//
//    @Override
//    public void onMoreClick(View v, int itemPosition) {
//        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
//    }
//
//    @Override
//    public void onProfileClick(View v) {
//        int[] startingLocation = new int[2];
//        v.getLocationOnScreen(startingLocation);
//        startingLocation[0] += v.getWidth() / 2;
////        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
////        overridePendingTransition(0, 0);
//    }
//
//    @Override
//    public void onReportClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onSharePhotoClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onCopyShareUrlClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
//    @Override
//    public void onCancelClick(int feedItem) {
//        FeedContextMenuManager.getInstance().hideContextMenu();
//    }
//
////    @OnClick(R.id.btnCreate)
////    public void onTakePhotoClick() {
////        int[] startingLocation = new int[2];
////        fabCreate.getLocationOnScreen(startingLocation);
////        startingLocation[0] += fabCreate.getWidth() / 2;
////        //TakePhotoActivity.startCameraFromLocation(startingLocation, this);
////        overridePendingTransition(0, 0);
////    }
//
//    public void showLikedSnackbar() {
//        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
//    }
//}


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;



public class MainActivity extends Fragment implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    RecyclerView rvFeed;
    FloatingActionButton fabCreate;
    CoordinatorLayout clContent;

    private FeedAdapter feedAdapter;

    private boolean pendingIntroAnimation=true;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        if (savedInstanceState == null) {
//            pendingIntroAnimation = true;
//        } else {
//            feedAdapter.updateItems(false);
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.newsfeed_activity, container, false);


        rvFeed= (RecyclerView) v.findViewById(R.id.rvFeed);
        fabCreate= (FloatingActionButton) v.findViewById(R.id.btnCreate);
        clContent= (CoordinatorLayout) v.findViewById(R.id.content);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupFeed();
            }
        },500);


        return v;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    private void setupFeed() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(getActivity());
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        //   showFeedLoadingItemDelayed();
        // Toast.makeText(getActivity(), " " +feedAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
//            showFeedLoadingItemDelayed();
//        }
//    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        if (pendingIntroAnimation) {
//            pendingIntroAnimation = false;
//            startIntroAnimation();
//        }
//        return true;
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        if (pendingIntroAnimation) {
//            pendingIntroAnimation = false;
//            startIntroAnimation();
//        }
    }

    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        startContentAnimation();
        int actionbarSize = Utils.dpToPx(56);
//        getToolbar().setTranslationY(-actionbarSize);
//        getIvLogo().setTranslationY(-actionbarSize);
//        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);
//
//        getToolbar().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(300);
//        getIvLogo().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(400);
//        getInboxMenuItem().getActionView().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        startContentAnimation();
//                    }
//                })
//                .start();
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        feedAdapter.updateItems(true);
    }

    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(getActivity(), CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
       // UserProfileActivity.startUserProfileFromLocation(startingLocation, getActivity());
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        //TakePhotoActivity.startCameraFromLocation(startingLocation, getActivity());
        getActivity().overridePendingTransition(0, 0);
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }
}