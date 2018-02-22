package pasu.vadivasal.Profile;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.tournament.SimpleImageAdapter;
import pasu.vadivasal.view.TextView;

/**
 * Created by developer on 8/11/17.
 */

public class ProfileGalleryFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, OnItemSelectedListener {
    private SimpleImageAdapter adapter;
    int ballType = -1;
    private boolean batsman=true;
    private List<String> filterList;
    private List<String> filterListCode;
    private boolean initSpinListener = true;
    private ArrayList<Media> itemArrayList;
    private boolean loadmore;
    int tournamentId;
    private RecyclerView rvMatches;
    ViewGroup viewEmpty;
    TextView tvDetail;
    private ProgressBar progressBar;
    private DataSnapshot baseResponse;
    private String TOUR_ID="tour1";
    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "https://api.learn2crack.com/android/images/donut.png",
            "https://api.learn2crack.com/android/images/eclair.png",
            "https://api.learn2crack.com/android/images/froyo.png",
            "https://api.learn2crack.com/android/images/ginger.png",
            "https://api.learn2crack.com/android/images/honey.png",
            "https://api.learn2crack.com/android/images/icecream.png",
            "https://api.learn2crack.com/android/images/jellybean.png",
            "https://api.learn2crack.com/android/images/kitkat.png",
            "https://api.learn2crack.com/android/images/lollipop.png",
            "https://api.learn2crack.com/android/images/marshmallow.png"
    };
    private String profile_id="";

    class C13422 implements Runnable {
        C13422() {
        }

        public void run() {
            ProfileGalleryFragment.this.adapter.loadMoreEnd();
        }
    }
//    private void initViews(View v){
//        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.card_recycler_view);
//
//
//    }
    private ArrayList<Media> prepareData(){

        ArrayList<Media> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            Media androidVersion = new Media();
            androidVersion.setOwnerID(android_version_names[i]);
            androidVersion.setMediaUrl(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tournament_gallery_list, container, false);
      //  View v=inflater.inflate(R.layout.tournament_gallery_list,container,false);
      //  initViews(rootView);
        this.tournamentId = getActivity().getIntent().getIntExtra(AppConstants.EXTRA_TOURNAMENTID, 0);
        progressBar=rootView.findViewById(R.id.progressBar);
        viewEmpty=rootView.findViewById(R.id.viewEmpty);
        tvDetail=rootView.findViewById(R.id.tvDetail);
        itemArrayList = new ArrayList<>();
        rvMatches = (RecyclerView) rootView.findViewById(R.id.rvMatches);
        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));

        profile_id=getActivity().getIntent().getStringExtra("id");
        setData();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.itemArrayList = new ArrayList();
    }

    public void setData() {
        this.progressBar.setVisibility(View.VISIBLE);
        System.out.println("Setdata");
        getBattingLeaderboard(null, null, false);
    }

    public void onStop() {
//        ApiCallManager.cancelCall("get_bat_leader_board");
//        ApiCallManager.cancelCall("get_bowl_leader_board");
        super.onStop();
    }

    public void getBattingLeaderboard(String page, Long datetime, final boolean refresh) {
        if (!this.loadmore) {
            if(this.progressBar!=null)
            this.progressBar.setVisibility(View.VISIBLE);
        }
        this.loadmore = false;
        emptyViewVisibility(false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("profile_gallery/"+profile_id);
        final String TAG = "Proflie itemArrayList";

        Query queryRef;
        System.out.println("postionnnn"+page);
        if(page==null)
            queryRef = myRef
                    .orderByKey()
                    .limitToFirst(10);
        else {
            System.out.println("ppppp  " + itemArrayList.get(itemArrayList.size() - 1).getId());
            queryRef = myRef.orderByChild("date").startAt(itemArrayList.get(itemArrayList.size() - 1).getDate()).limitToFirst(10);
        }

        ValueEventListener valueEventListener=(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever itemArrayList at this location is updated.
                if(progressBar!=null)
                progressBar.setVisibility(View.GONE);
//                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
                if (getActivity() != null) {
                    if (!dataSnapshot.exists()) {
                        ProfileGalleryFragment.this.loadmore = true;
                        if (ProfileGalleryFragment.this.adapter != null) {
                            ProfileGalleryFragment.this.adapter.loadMoreFail();
                        }
                        if (ProfileGalleryFragment.this.itemArrayList.size() <= 0) {
                            ProfileGalleryFragment.this.emptyViewVisibility(true);
                            ProfileGalleryFragment.this.rvMatches.setVisibility(View.GONE);
                            return;
                        }
                        return;
                    }
                    ProfileGalleryFragment.this.baseResponse = dataSnapshot;
                  //  ArrayList<Media> arrayLists = new ArrayList<>(((HashMap<String, Media>) dataSnapshot.getValue()).values());
                    ArrayList<Media> arrayList=new ArrayList<>();

                    for (DataSnapshot md : dataSnapshot.getChildren()) {
                        System.out.println("data came"+md.getValue().toString());

                        if (md.getValue() != null && !md.getValue().equals("")) {
                            Media media= md.getValue(Media.class);
                            if (itemArrayList.size() > 0 && itemArrayList.get(itemArrayList.size() - 1).getId() == media.getId()) {

                                System.out.println("datatacccc");
                            } else {

                                itemArrayList.add(media);
                                arrayList.add(media);}
                            //   itemArrayList.add(arrayList.get(i));
                        }
                    }
                    //   itemArrayList.addAll(Arrays.asList(Utils.fromJson(Utils.toString(arrayList),Media[].class)));
                    //    System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav)));
                    // ArrayList<Media> datav = new ArrayList<Media>(sh.values());
                    System.out.println("datavvvf  "+TAG + (Utils.toString(arrayList.get(0))));

                    if (ProfileGalleryFragment.this.adapter == null) {
                        System.out.println("NEW ADAPTER SETbat");
                        //TournamentGalleryFragment .this.itemArrayList.addAll(arrayList);
                        ProfileGalleryFragment.this.adapter = new SimpleImageAdapter(ProfileGalleryFragment.this.getActivity(), R.layout.gallery_image_item, ProfileGalleryFragment.this.itemArrayList);
                        ProfileGalleryFragment.this.adapter.setEnableLoadMore(true);
                        ProfileGalleryFragment.this.rvMatches.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
                        ProfileGalleryFragment.this.rvMatches.setLayoutManager(layoutManager);

                        ProfileGalleryFragment.this.rvMatches.setAdapter(ProfileGalleryFragment.this.adapter);
                        // TournamentGalleryFragment .this.rvMatches.addOnItemTouchListener(new C13401());
                        ProfileGalleryFragment.this.adapter.setOnLoadMoreListener(ProfileGalleryFragment.this, ProfileGalleryFragment.this.rvMatches);

                        System.out.println("datavvv  ss" + adapter.getData().size()+"__"+(arrayList.size() % 10 != 0));
                        if (arrayList.size() % 10 != 0) {
                            ProfileGalleryFragment.this.adapter.loadMoreEnd();
                        }
                    } else {
                        if (refresh) {
                            ProfileGalleryFragment.this.adapter.getData().clear();
                            ProfileGalleryFragment.this.itemArrayList.clear();
                            ProfileGalleryFragment.this.itemArrayList.addAll(arrayList);
                            ProfileGalleryFragment.this.adapter.setNewData(arrayList);
                            ProfileGalleryFragment.this.adapter.setEnableLoadMore(true);
                            ProfileGalleryFragment.this.rvMatches.scrollToPosition(0);
                        } else {
                            ProfileGalleryFragment.this.adapter.addData((Collection) arrayList);
                            ProfileGalleryFragment.this.adapter.loadMoreComplete();
                        }
                        if (ProfileGalleryFragment.this.baseResponse != null &&   arrayList.size() % 10 != 0) {
                            ProfileGalleryFragment.this.adapter.loadMoreEnd();
                        }
                    }
                    ProfileGalleryFragment.this.loadmore = true;
                    if (ProfileGalleryFragment.this.itemArrayList.size() == 0) {
                        ProfileGalleryFragment.this.emptyViewVisibility(true);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });



        queryRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private String getHighlightedText(String text, boolean isHighlight) {
        if (isHighlight) {
            return "<b>" + text + "</b>";
        }
        return text;
    }

//    private View getEmptyView() {
//        if (getActivity() != null) {
//            this.viewEmpty = getActivity().getLayoutInflater().inflate(R.layout.raw_empty_view, null);
//            TextView tvTitle = (TextView) this.viewEmpty.findViewById(R.id.tvTitle);
//            TextView tvDetail = (TextView) this.viewEmpty.findViewById(R.id.tvDetail);
//            ((ImageView) this.viewEmpty.findViewById(R.id.ivImage)).setImageResource(R.drawable.about);
//            //tvTitle.setText(R.string.leaderbord_blank_stat);
//            tvDetail.setVisibility(View.GONE);
//        }
//        return this.viewEmpty;
//    }

    public void onLoadMoreRequested() {
        System.out.println("onLoadMoreRequested"+this.loadmore +"___"+(itemArrayList.size() % 10 != 0));
        if (!this.loadmore  || (itemArrayList.size() % 10 != 0)) {
            new Handler().postDelayed(new C13422(), 1500);
        } else if (this.batsman) {
            System.out.println("Load more");
            getBattingLeaderboard(String.valueOf(1), null, false);
        } else {
            // getBowlingLeaderboard(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
        }
    }

    private void emptyViewVisibility(boolean b) {
        if (b && viewEmpty!=null) {
            this.viewEmpty.setVisibility(View.VISIBLE);
//            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
//            this.tvTitle.setText(R.string.leaderbord_blank_stat);
            //this.tvDetail.setText(getString(R.string.profile_gallery_empty));
            this.tvDetail.setVisibility(View.VISIBLE);
            return;
        }
        if(viewEmpty!=null)
        this.viewEmpty.setVisibility(View.GONE);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.initSpinListener) {
            this.initSpinListener = false;
        } else if (this.batsman) {
            System.out.println("On iTem select");
            getBattingLeaderboard(null, null, true);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}

