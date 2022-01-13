package pasu.vadivasal.photographyInsta;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Comments_Model;
import pasu.vadivasal.regLogin.SocialLoginCustom;
import pasu.vadivasal.view.SendCommentButton;

//public class CommentsAutoActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener, SendCommentButton.OnSendClickListener  {
//    @BindView(R.id.rvComments)
//    RecyclerView rvMatches;
//    @BindView(R.id.etComment)
//    EditText etComment;
//    @BindView(R.id.btnSendComment)
//    SendCommentButton btnSendComment;
//    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
//    private CommentsAutoAdapter adapter;
//    private boolean batsman = true;
//    private List<String> filterList;
//    private List<String> filterListCode;
//    private boolean initSpinListener = true;
//    private ArrayList<Comments_Model> itemArrayList;
//    private boolean loadmore;
//
//    private DataSnapshot baseResponse;
//    private String TOUR_ID = "tour1";
//    private ChildEventListener childAddListner;
//    private long lastItemId = 0;
//    public String postId;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_comments_auto);
//        ButterKnife.bind(this);
//        itemArrayList = new ArrayList<>();
////        adapter = new CommentsAutoAdapter(this,R.layout.item_comment,itemArrayList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        this.rvMatches.setLayoutManager(layoutManager); if (getIntent() != null) {
//            postId = getIntent().getStringExtra("postID");
//        }
////        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
//        setData();
//        setupSendCommentButton();
//    }
//    private void setupSendCommentButton() {
//        btnSendComment.setOnSendClickListener(this);
//    }
//    public void setData() {
////        this.progressBar.setVisibility(View.VISIBLE);
//        System.out.println("Setdata");
//        getBattingLeaderboard(null, null, false);
////        CommentsAutoActivity.this.adapter.setOnFeedItemClickListener(this);
//    }
//    public void getBattingLeaderboard(String page, Long datetime, final boolean refresh) {
//        if (!this.loadmore) {
//            //  this.progressBar.setVisibility(View.VISIBLE);
//        }
//        this.loadmore = false;
//        emptyViewVisibility(false);
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("comments").child(postId);
//        final String TAG = "Commentary itemArrayList";
//
//        Query queryRef;
//        System.out.println("postionnnncomm" + page + TOUR_ID);
//        if (page == null)
//            queryRef = myRef
//                    .orderByChild("timeStamp")
//                    .limitToLast(3);
//        else
//            queryRef = myRef.orderByChild("timeStamp").endAt(datetime).limitToFirst(3);
//
//        ValueEventListener valueEventListener = (new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever itemArrayList at this location is updated.
//                // progressBar.setVisibility(View.GONE);
////                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
//                if (CommentsAutoActivity.this != null) {
//                    if (!dataSnapshot.exists()) {
//                        CommentsAutoActivity.this.loadmore = true;
//                        if (CommentsAutoActivity.this.adapter != null) {
//                            CommentsAutoActivity.this.adapter.loadMoreFail();
//                        }
//                        if (CommentsAutoActivity.this.itemArrayList.size() <= 0) {
//                            CommentsAutoActivity.this.emptyViewVisibility(true);
//                            CommentsAutoActivity.this.rvMatches.setVisibility(View.GONE);
//                            return;
//                        }
//                        return;
//                    }
//                    CommentsAutoActivity.this.baseResponse = dataSnapshot;
//                    ArrayList<Comments_Model> arrayList = new ArrayList<>();
//                    for (DataSnapshot md : dataSnapshot.getChildren()) {
//                        if (md.getValue() != null && !md.getValue().equals("")) {
//                            Comments_Model matchDetails = md.getValue(Comments_Model.class);
//                            if (arrayList.size() > 0)
//                                System.out.println("datatacccc" + arrayList.size() + "__" + lastItemId + "___" + matchDetails.getTimeStamp());
//                            if (arrayList.size() > 0 && lastItemId == matchDetails.getTimeStamp()) {
//
//                                System.out.println("datatacccc");
//                            } else {
//
////                                    matchDetails.setMatch_id(Integer.parseInt(md.getTournament_key()));
////                                    matchDetails.setmatchShortSummary(CommanData.toString(md.getValue()));
//                                arrayList.add(matchDetails);
//                            }
//                        }
//                    }
////                    ArrayList<PostModel> arrayList = new ArrayList<>(Arrays.asList(Utils.fromJson(Utils.toString(arrayLists), PostModel[].class)));
////                    for (int i = 0; i < arrayList.size(); i++) {
////                        System.out.println("cameaddbat" + i + "____" + arrayList.get(i).getTime());
////                        itemArrayList.add(arrayList.get(i));
////                    }
//                    Collections.reverse(arrayList);
//                    lastItemId = arrayList.get(arrayList.size() - 1).getTimeStamp();
//                    if (itemArrayList.size() == 0)
//                        itemArrayList.addAll(arrayList);
//                    System.out.println("datataccccitem" + itemArrayList.size());
//                    //   itemArrayList.addAll(Arrays.asList(Utils.fromJson(Utils.toString(arrayList),PostModel[].class)));
//                    //    System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav)));
//                    // ArrayList<PostModel> datav = new ArrayList<PostModel>(sh.values());
////                    System.out.println("datavvvf  " + TAG + (pasu.vadivasal.android.Utils.toString(arrayList.get(0))));
//
//                    if (CommentsAutoActivity.this.adapter == null) {
//                        System.out.println("NEW ADAPTER SETbat");
//                        //AutoLoadingFragment .this.itemArrayList.addAll(arrayList);
//                        CommentsAutoActivity.this.adapter = new CommentsAutoAdapter(CommentsAutoActivity.this, R.layout.item_comment, CommentsAutoActivity.this.itemArrayList);
//                        CommentsAutoActivity.this.adapter.setEnableLoadMore(true);
//                        CommentsAutoActivity.this.rvMatches.setAdapter(CommentsAutoActivity.this.adapter);
//                        CommentsAutoActivity.this.adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//                        // AutoLoadingFragment .this.rvMatches.addOnItemTouchListener(new C13401());
//                        CommentsAutoActivity.this.adapter.setOnLoadMoreListener(CommentsAutoActivity.this, CommentsAutoActivity.this.rvMatches);
////                        CommentsAutoActivity.this.adapter.setOnFeedItemClickListener(CommentsAutoActivity.this);
//                        if (arrayList.size() % 3 != 0) {
//                            CommentsAutoActivity.this.adapter.loadMoreEnd();
//                        }
//                        final DatabaseReference myRefs = database.getReference("comments").child(postId);
//                        try {
//                            // String firstKey = (String) ((HashMap<String, PostModel>) dataSnapshot.getValue()).keySet().toArray()[0];
//                            System.out.println("datavvv  ssfirst key" + adapter.getData().size() + "__" + itemArrayList.get(0).getTimeStamp());
//                            myRefs.orderByChild("timeStamp").startAt(itemArrayList.get(0).getTimeStamp()).addChildEventListener(childAddListner);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        if (refresh) {
//                            CommentsAutoActivity.this.adapter.getData().clear();
//                            CommentsAutoActivity.this.itemArrayList.clear();
//                            CommentsAutoActivity.this.itemArrayList.addAll(arrayList);
//                            CommentsAutoActivity.this.adapter.setNewData(arrayList);
//                            CommentsAutoActivity.this.adapter.setEnableLoadMore(true);
//                            CommentsAutoActivity.this.rvMatches.scrollToPosition(0);
//                        } else {
//                            System.out.println("datataccccitemaddd" + arrayList.size() + CommentsAutoActivity.this.adapter.getItemCount());
//                            CommentsAutoActivity.this.adapter.addData((Collection) arrayList);
//                            CommentsAutoActivity.this.adapter.loadMoreComplete();
//                            System.out.println("datataccccitemafter" + arrayList.size() + CommentsAutoActivity.this.adapter.getItemCount());
//                        }
//
//                    }
//                    if (CommentsAutoActivity.this.baseResponse != null && arrayList.size() % 3 != 0) {
//                        CommentsAutoActivity.this.adapter.loadMoreEnd();
//                    } else
//                        CommentsAutoActivity.this.loadmore = true;
//                    if (CommentsAutoActivity.this.itemArrayList.size() == 0) {
//                        CommentsAutoActivity.this.emptyViewVisibility(true);
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//
//        });
//
//
//        childAddListner = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                System.out.println("commentary added" + dataSnapshot.getValue());
//
//                if (dataSnapshot != null && itemArrayList != null) {
//
//                    Comments_Model arrayLists = dataSnapshot.getValue(Comments_Model.class);
//                    if (itemArrayList.size() > 0 && itemArrayList.get(0).getTimeStamp() != (arrayLists).getTimeStamp()) {
//                        System.out.println("commentary adding data");
//                        CommentsAutoActivity.this.adapter.addData(0, arrayLists);
//                        CommentsAutoActivity.this.adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                //  System.out.println("nagaaaa_chile");
////                PostModel arrayLists = dataSnapshot.getValue(PostModel.class);
////                CommentsAutoActivity.this.adapter.notifyDataSetChanged();
////                System.out.println("arrayLists"+arrayLists.likesCount+"__"+arrayLists.url);
////                if (itemArrayList.size() > 0 && itemArrayList.get(0).getTime() != (arrayLists).getTime()) {
////                    System.out.println("commentary adding data");
////                    CommentsAutoActivity.this.adapter.addData(0, arrayLists);
////                    CommentsAutoActivity.this.adapter.notifyDataSetChanged();
////                }
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//
//        queryRef.addListenerForSingleValueEvent(valueEventListener);
//    }
//    private void emptyViewVisibility(boolean b) {
//        if (b) {
////            this.viewEmpty.setVisibility(View.VISIBLE);
//////            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
//////            this.tvTitle.setText(R.string.commentary_empty);
////            this.tvDetail.setVisibility(View.GONE);
//            return;
//        }
//        // this.viewEmpty.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        System.out.println("onLoadMoreRequested" + this.loadmore + "___" + (itemArrayList.size() % 10 != 0));
//        if (!this.loadmore || (itemArrayList.size() % 3 != 0)) {
//            new Handler().postDelayed(new C13422(), 1500);
//        } else if (this.batsman) {
//            System.out.println("Load more" + itemArrayList.get(itemArrayList.size() - 1).getTimeStamp());
//            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), (itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), false);
//        } else {
//            // getBowlingLeaderboard(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
//        }
//    }
//
//    @Override
//    public void onSendClickListener(View v) {
//        if (validateComment()) {
//            String comment = etComment.getText().toString();
//
//            if (FirebaseAuth.getInstance().getCurrentUser() != null ){
//                String name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                Comments_Model cModel = new Comments_Model(name, userId, comment, System.currentTimeMillis()/1000);
//                FirebaseDatabase.getInstance().getReference("comments").child(postId).push().setValue(cModel, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        if (databaseError != null) {
//                            Toast.makeText(CommentsAutoActivity.this, "Failed to Add Comment", Toast.LENGTH_SHORT).show();
//                        }else {
////                            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), (itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), false);
//                            Toast.makeText(CommentsAutoActivity.this, "You Commented on this post", Toast.LENGTH_SHORT).show();
////                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//
//            }else {
//                Toast.makeText(this, "Please Login to give comment", Toast.LENGTH_SHORT).show();
//            }
//
////            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());
//
//            etComment.setText(null);
//            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
//        }
//    }
//
//    private boolean validateComment() {
//        if (TextUtils.isEmpty(etComment.getText())) {
//            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
//            return false;
//        }
//
//        return true;
//    }
//
//    class C13422 implements Runnable {
//        C13422() {
//        }
//
//        public void run() {
//            CommentsAutoActivity.this.adapter.loadMoreEnd();
//        }
//    }
//}

public class CommentsAutoActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener, SendCommentButton.OnSendClickListener {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    public String postId;
    @BindView(R.id.rvComments)
    RecyclerView rvMatches;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btnSendComment)
    SendCommentButton btnSendComment;
    private CommentsAutoAdapter adapter;
    private boolean batsman = true;
    private List<String> filterList;
    private List<String> filterListCode;
    private boolean initSpinListener = true;
    private ArrayList<Comments_Model> itemArrayList;
    private boolean loadmore;
    private DatabaseReference mDatabase;
    private DataSnapshot baseResponse;
    private String TOUR_ID = "tour1";
    private ChildEventListener childAddListner;
    private long lastItemId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_auto);
        ButterKnife.bind(this);
        itemArrayList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("comments");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.rvMatches.setLayoutManager(layoutManager);
        if (getIntent() != null) {
            postId = getIntent().getStringExtra("postID");
        }

//        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        setData();
        setupSendCommentButton();
//        getDatabaseData();
    }

    private void getDatabaseData() {

        mDatabase.child(postId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (itemArrayList.size() != 0) {
                            itemArrayList.clear();
                        }
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Comments_Model cModel = snapshot.getValue(Comments_Model.class);
                            itemArrayList.add(cModel);
                            System.out.println("dataaaaaaa" + cModel.comment);
//                    }
                        }
                        Collections.reverse(itemArrayList);
                        adapter = new CommentsAutoAdapter(CommentsAutoActivity.this, R.layout.item_comment, itemArrayList);
                        rvMatches.setAdapter(adapter);
//                CommentsAutoActivity.this.adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    public void setData() {
//        this.progressBar.setVisibility(View.VISIBLE);
        System.out.println("Setdata");
        getBattingLeaderboard(null, null, false);
//        CommentsAutoActivity.this.adapter.setOnFeedItemClickListener(this);
    }

    public void getBattingLeaderboard(String page, Long datetime, final boolean refresh) {
        if (!this.loadmore) {
            //  this.progressBar.setVisibility(View.VISIBLE);
        }
        this.loadmore = false;
        emptyViewVisibility(false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("comments").child(postId);
        final String TAG = "Commentary itemArrayList";

        Query queryRef;
        System.out.println("postionnnncomm" + page + TOUR_ID);
        if (page == null)
            queryRef = myRef
                    .orderByChild("timeStamp")
                    .limitToLast(3);
        else
            queryRef = myRef.orderByChild("timeStamp").endAt(datetime).limitToFirst(3);

        ValueEventListener valueEventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever itemArrayList at this location is updated.
                // progressBar.setVisibility(View.GONE);
//                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
                if (CommentsAutoActivity.this != null) {
                    if (!dataSnapshot.exists()) {
                        CommentsAutoActivity.this.loadmore = true;
                        if (CommentsAutoActivity.this.adapter != null) {
                            CommentsAutoActivity.this.adapter.loadMoreFail();
                        }
                        if (CommentsAutoActivity.this.itemArrayList.size() <= 0) {
                            CommentsAutoActivity.this.emptyViewVisibility(true);
                            // CommentsAutoActivity.this.rvMatches.setVisibility(View.GONE);
                            return;
                        }
                        return;
                    }
                    CommentsAutoActivity.this.baseResponse = dataSnapshot;
                    ArrayList<Comments_Model> arrayList = new ArrayList<>();
                    for (DataSnapshot md : dataSnapshot.getChildren()) {
                        if (md.getValue() != null && !md.getValue().equals("")) {
                            Comments_Model matchDetails = md.getValue(Comments_Model.class);
                            if (itemArrayList.size() > 0)
                                System.out.println("datatacccc" + arrayList.size() + "__" + lastItemId + "___" + matchDetails.getTimeStamp());
                            if (itemArrayList.size() > 0 && lastItemId == matchDetails.getTimeStamp()) {
//                                CommentsAutoActivity.this.adapter.loadMoreComplete();
                                System.out.println("datatacccc");
                            } else {
                                arrayList.add(matchDetails);
                                System.out.println("arrayList in ValueEventListner " + arrayList.size());

                            }
                        }
                    }

                    if (arrayList.size() != 0) {
                        Collections.reverse(arrayList);
                        lastItemId = arrayList.get(arrayList.size() - 1).getTimeStamp();

                        if (itemArrayList.size() == 0) {
                            System.out.println("arrayList " + arrayList.size());
                            itemArrayList.addAll(arrayList);
                            System.out.println("itemArrayList" + itemArrayList.size());
                        }

                        if (CommentsAutoActivity.this.adapter == null) {
                            System.out.println("NEW ADAPTER SETbat");
                            //AutoLoadingFragment .this.itemArrayList.addAll(arrayList);
                            CommentsAutoActivity.this.adapter = new CommentsAutoAdapter(CommentsAutoActivity.this, R.layout.item_comment, CommentsAutoActivity.this.itemArrayList);
                            CommentsAutoActivity.this.adapter.setEnableLoadMore(true);
                            CommentsAutoActivity.this.rvMatches.setAdapter(CommentsAutoActivity.this.adapter);
                            CommentsAutoActivity.this.adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                            // AutoLoadingFragment .this.rvMatches.addOnItemTouchListener(new C13401());
                            CommentsAutoActivity.this.adapter.setOnLoadMoreListener(CommentsAutoActivity.this, CommentsAutoActivity.this.rvMatches);
//                        CommentsAutoActivity.this.adapter.setOnFeedItemClickListener(CommentsAutoActivity.this);
                            if (arrayList.size() % 3 != 0) {
                                CommentsAutoActivity.this.adapter.loadMoreEnd();
                            }
                            final DatabaseReference myRefs = database.getReference("comments").child(postId);
                            try {
                                // String firstKey = (String) ((HashMap<String, PostModel>) dataSnapshot.getValue()).keySet().toArray()[0];
                                System.out.println("datavvv  ssfirst key" + adapter.getData().size() + "__" + itemArrayList.get(0).getTimeStamp());
                                myRefs.orderByChild("timeStamp").startAt(itemArrayList.get(0).getTimeStamp()).addChildEventListener(childAddListner);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (refresh) {
                                CommentsAutoActivity.this.adapter.getData().clear();
                                CommentsAutoActivity.this.itemArrayList.clear();
                                System.out.println("arrayList in refresh " + arrayList.size());
                                CommentsAutoActivity.this.itemArrayList.addAll(arrayList);
                                System.out.println("itemArrayList in refresh " + itemArrayList.size());
                                CommentsAutoActivity.this.adapter.setNewData(arrayList);
                                CommentsAutoActivity.this.adapter.setEnableLoadMore(true);
                                CommentsAutoActivity.this.rvMatches.scrollToPosition(0);
                            } else {
                                System.out.println("datataccccitemaddd" + arrayList.size());
                                System.out.println("arrayList in else " + arrayList.size());
//                            if (arrayList.size() == 1) {
//                                CommentsAutoActivity.this.adapter.loadMoreComplete();
//                            }else {
                                CommentsAutoActivity.this.adapter.addData(arrayList);
                                System.out.println("itemArrayList in else " + itemArrayList.size());
                                CommentsAutoActivity.this.adapter.loadMoreComplete();
//                            }

                                System.out.println("datataccccitemafter" + arrayList.size() + CommentsAutoActivity.this.adapter.getItemCount());
                            }

                        }
                        if (CommentsAutoActivity.this.baseResponse != null && arrayList.size() % 3 != 0) {
                            CommentsAutoActivity.this.adapter.loadMoreEnd();
                        } else
                            CommentsAutoActivity.this.loadmore = true;
                        if (CommentsAutoActivity.this.itemArrayList.size() == 0) {
                            CommentsAutoActivity.this.emptyViewVisibility(true);
                        }

                    } else {
                        CommentsAutoActivity.this.adapter.loadMoreComplete();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }

        });


        childAddListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("commentary added" + dataSnapshot.getValue());

                if (dataSnapshot != null && itemArrayList != null) {

                    Comments_Model arrayLists = dataSnapshot.getValue(Comments_Model.class);
                    if (itemArrayList.size() > 0 && itemArrayList.get(0).getTimeStamp() != (arrayLists).getTimeStamp()) {
//                        if (itemArrayList.get(0).getTimeStamp() != (arrayLists).getTimeStamp()) {
//                            System.out.println("commentary adding data");
//                            CommentsAutoActivity.this.adapter.addData(0, arrayLists);
//                            CommentsAutoActivity.this.adapter.notifyDataSetChanged();
//                        }
//                        if (lastItemId != arrayLists.getTimeStamp()) {
                        System.out.println("commentary adding data");
                        CommentsAutoActivity.this.adapter.addData(0, arrayLists);
                        CommentsAutoActivity.this.adapter.notifyDataSetChanged();
//                        }
                    }
//                    lastItemId = itemArrayList.get(itemArrayList.size() - 1).getTimeStamp();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //  System.out.println("nagaaaa_chile");
//                PostModel arrayLists = dataSnapshot.getValue(PostModel.class);
//                CommentsAutoActivity.this.adapter.notifyDataSetChanged();
//                System.out.println("arrayLists"+arrayLists.likesCount+"__"+arrayLists.url);
//                if (itemArrayList.size() > 0 && itemArrayList.get(0).getTime() != (arrayLists).getTime()) {
//                    System.out.println("commentary adding data");
//                    CommentsAutoActivity.this.adapter.addData(0, arrayLists);
//                    CommentsAutoActivity.this.adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        queryRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void emptyViewVisibility(boolean b) {
        if (b) {
//            this.viewEmpty.setVisibility(View.VISIBLE);
////            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
////            this.tvTitle.setText(R.string.commentary_empty);
//            this.tvDetail.setVisibility(View.GONE);
            return;
        }
        // this.viewEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMoreRequested() {
        System.out.println("onLoadMoreRequested" + this.loadmore + "___" + (itemArrayList.size() % 10 != 0));
        if (!this.loadmore || (itemArrayList.size() % 3 != 0)) {
            new Handler().postDelayed(new C13422(), 1500);
        }
//        else if (this.batsman) {
//            System.out.println("Load more" + itemArrayList.get(itemArrayList.size() - 1).getTimeStamp());
//            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), (itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), false);
//        }
        else {
            System.out.println("Load more" + itemArrayList.get(itemArrayList.size() - 1).getTimeStamp());
            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), (itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), false);
//             getBowlingLeaderboard(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
        }
    }

    @Override
    public void onSendClickListener(View v) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (validateComment()) {
                String comment = etComment.getText().toString();
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    String name = SessionSave.getSession(Appconstants.USER_PROFILE_NAME, CommentsAutoActivity.this);
                    String userId = SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, CommentsAutoActivity.this);


                    Comments_Model cModel = new Comments_Model(name, userId, comment, System.currentTimeMillis() / 1000, SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE, CommentsAutoActivity.this));
                    FirebaseDatabase.getInstance().getReference("comments").child(postId).push().setValue(cModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(CommentsAutoActivity.this, "Failed to Add Comment", Toast.LENGTH_SHORT).show();
                            } else {
//                            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), (itemArrayList.get(itemArrayList.size() - 1).getTimeStamp()), false);
                                Toast.makeText(CommentsAutoActivity.this, "You Commented on this post", Toast.LENGTH_SHORT).show();
                                //  adapter.notifyDataSetChanged();
                                if (itemArrayList.size() == 0)
                                    getDatabaseData();
                                else
                                    System.out.println("__________notcalled" + itemArrayList.size());
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "Please Login to give comment", Toast.LENGTH_SHORT).show();
                }

//            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

                etComment.setText(null);
                btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
            }
        } else {
            Utils.showAlert(CommentsAutoActivity.this, getString(R.string.kindly_login), getString(R.string.ask_login_message), getString(R.string.ok), getString(R.string.cancel), new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -1) {
                        startActivity(new Intent(CommentsAutoActivity.this, SocialLoginCustom.class));
                    }
                }
            }, true);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }

    class C13422 implements Runnable {
        C13422() {
        }

        public void run() {
            CommentsAutoActivity.this.adapter.loadMoreEnd();
        }
    }
}
