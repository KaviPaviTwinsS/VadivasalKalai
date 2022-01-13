package pasu.vadivasal.photographyInsta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Comments_Model;
import pasu.vadivasal.view.SendCommentButton;

public class CommentsActivity extends AppCompatActivity implements SendCommentButton.OnSendClickListener {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";

    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;
    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    @BindView(R.id.llAddComment)
    LinearLayout llAddComment;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btnSendComment)
    SendCommentButton btnSendComment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CommentsAdapter commentsAdapter;
    private int drawingStartLocation;
    private DatabaseReference mDatabase;
    public String postId;
    private List<Comments_Model> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
            postId = getIntent().getStringExtra("postID");
        }
        setupComments();
        setupSendCommentButton();
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }

        getDatabaseData();
    }

    private void getDatabaseData() {
        mDatabase.child(postId).orderByChild("timeStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (commentsList.size() != 0) {
                    commentsList.clear();
                }
                for( DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Comments_Model cModel = snapshot.getValue(Comments_Model.class);
                        commentsList.add(cModel);
                        System.out.println("dataaaaaaa" + cModel.comment);
//                    }
                }
                Collections.reverse(commentsList);
               commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupComments() {
        mDatabase = FirebaseDatabase.getInstance().getReference("comments");
        commentsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(this, commentsList);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });
    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    private void startIntroAnimation() {
        ViewCompat.setElevation(toolbar, 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(200);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(toolbar, Utils.dpToPx(8));
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
//        commentsAdapter.updateItems();
        commentsAdapter.notifyDataSetChanged();
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(toolbar, 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CommentsActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
            String comment = etComment.getText().toString();

            if (FirebaseAuth.getInstance().getCurrentUser() != null ){
                String name = SessionSave.getSession(Appconstants.USER_PROFILE_NAME,CommentsActivity.this);
                String userId = SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,CommentsActivity.this);

                Comments_Model cModel = new Comments_Model(name, userId, comment, System.currentTimeMillis()/1000,SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,CommentsActivity.this));
                mDatabase.child(postId).push().setValue(cModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(CommentsActivity.this, "Failed to Add Comment", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CommentsActivity.this, "You Commented on this post", Toast.LENGTH_SHORT).show();
                            commentsAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }else {
                Toast.makeText(this, "Please Login to give comment", Toast.LENGTH_SHORT).show();
            }

            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
//            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }
}
