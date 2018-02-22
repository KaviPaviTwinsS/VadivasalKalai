package pasu.vadivasal.photographyInsta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sa90.materialarcmenu.ArcMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.R;
import pasu.vadivasal.model.PostModel;

/**
 * Created by developer on 18/12/17.
 */

public class InstaMaterial extends Fragment {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    private static DatabaseReference mDatabase;
    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.arcMenu)
    ArcMenu fabCreate;
    @BindView(R.id.content)
    CoordinatorLayout clContent;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
//    String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private List<PostModel> mList;
    private PostAdapter mAdapter;
    private PostAutoAdapter adapter;
    private boolean pendingIntroAnimation;
    private PostModel model;
    private static final String KEY_TITLE = "key_title";
    private static String TAG = "BackgroundService";
    private boolean loadmore;
    public InstaMaterial() {
    }

    public static InstaMaterial newInstance(String Title) {
        InstaMaterial fragmentAction = new InstaMaterial();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, Title);
        fragmentAction.setArguments(args);
        return fragmentAction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
//            mAdapter.notifyItemRangeInserted(0, mList.size());
            mAdapter.notifyDataSetChanged();
//            mAdapter.updateItems(mList,false);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Foreground Service");
        View rootView = inflater.inflate(R.layout.layout_firebasecontent, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initAdapter();
        setupFeed();
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
//            startIntroAnimation();
            startContentAnimation();
        }
    }
    private void initAdapter() {
        mAdapter = new PostAdapter(getActivity(), mList);
        rvFeed.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
//        rvFeed.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvFeed.setLayoutManager(linearLayoutManager);
//        mAdapter.setOnFeedItemClickListener(this);
//        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
//            }
//        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }

    private void initViews() {
        mDatabase = FirebaseDatabase.getInstance().getReference("MyPosts");
        mList = new ArrayList<>();
    }

    private void setupFeed() {

//        for (int i = 0; i<3 ; i++){
//            String pId = mDatabase.push().getKey();
//            mDatabase.child(pId).setValue(new Model(uId,"Bike "+i, "https://firebasestorage.googleapis.com/v0/b/fir-imagevideo.appspot.com/o/MyUploads1511877243983.jpg?alt=media&token=f90606f6-4f43-45ce-ba4a-9e9d540d85fd"
//                    , 0, pId));
//        }
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mList != null) {
                    mList.clear();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    model = snapshot.getValue(PostModel.class);
                    mList.add(model);
                    progressBar.setVisibility(View.GONE);
                }
                Log.i("mList", String.valueOf(mList.size()));
//                rvFeed.smoothScrollToPosition(0);
//                mAdapter.notifyItemRangeInserted(0, mList.size());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showLikedSnackbar(boolean flag) {
        if (flag) Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
        else Snackbar.make(clContent, "Unliked!", Snackbar.LENGTH_SHORT).show();
    }
    private void startIntroAnimation() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
//
//        int actionbarSize = Utils.dpToPx(56);
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
//        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRangeInserted(0, mList.size());
//        mAdapter.updateItems(mList, true);
    }

}
