package pasu.vadivasal.tournament;

import android.content.Intent;
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
import pasu.vadivasal.ShareAct;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.listener.OnItemClickListener;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.videopackage.VideoActivityMain;
import pasu.vadivasal.view.TextView;

/**
 * Created by developer on 8/11/17.
 */

public class TournamentGalleryFragment  extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, OnItemSelectedListener {
    private SimpleImageAdapter adapter;
    private boolean batsman=true;
    private boolean initSpinListener = true;
    private ArrayList<Media> itemArrayList;
    private boolean loadmore;
    String tournamentId;
    private RecyclerView rvMatches;
    ViewGroup viewEmpty;
    TextView tvDetail;
    private ProgressBar progressBar;
    private DataSnapshot baseResponse;
    private int type;
    String typeUrl="photos";

    class C13422 implements Runnable {
        C13422() {
        }

        public void run() {
            TournamentGalleryFragment .this.adapter.loadMoreEnd();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tournament_gallery_list, container, false);
      //  View v=inflater.inflate(R.layout.tournament_gallery_list,container,false);
      //  initViews(rootView);
        this.type=getArguments().getInt("type",0);
        this.tournamentId = getActivity().getIntent().getStringExtra(Appconstants.TourID);
        progressBar=rootView.findViewById(R.id.progressBar);
        viewEmpty=rootView.findViewById(R.id.viewEmpty);
        tvDetail=rootView.findViewById(R.id.tvDetail);
        itemArrayList = new ArrayList<>();
        rvMatches = (RecyclerView) rootView.findViewById(R.id.rvMatches);
        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        setData();
this.rvMatches.addOnItemTouchListener(new OnItemClickListener() {
    @Override
    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        System.out.println("typeofindia"+type);
        if(type==0){
            typeUrl="photos";
        String stockArr = pasu.vadivasal.android.Utils.toString(adapter.getData());
        System.out.println("clickedimage"+((Media)adapter.getData().get(position)).getMediaUrl());
        Intent intent = new Intent(getContext(), ShareAct.class);
        intent.putExtra("image_data", stockArr);
        intent.putExtra("pos", position);
        getActivity().startActivity(intent);}
        else if(type ==1){
            typeUrl="videos";
            String stockArr = pasu.vadivasal.android.Utils.toString(adapter.getData());
//            String s = pasu.vadivasal.android.Utils.toString(stockArr);
            Intent ss = new Intent(getContext(), VideoActivityMain.class);
            ss.putExtra("videos", stockArr);
            ss.putExtra("toplay", 5);
            getActivity().startActivity(ss);
        }else
            typeUrl="sponsors";
    }
});
        return rootView;
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
            this.progressBar.setVisibility(View.VISIBLE);
        }
        this.loadmore = false;
        emptyViewVisibility(false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tournament-new/"+typeUrl+"/"+tournamentId);
        final String TAG = "Gallery itemArrayList";

        Query queryRef;
        System.out.println("postionnnnGallery"+page+"__"+"tournament-new/"+typeUrl+"/"+tournamentId);
//if(page==null)
//        queryRef = myRef
//                .orderByKey()
//                .limitToFirst(10);
        if(page==null)
            queryRef = myRef
                    .orderByChild("position")
                    .limitToFirst(10);
        else {
            System.out.println("ppppp  " + itemArrayList.get(itemArrayList.size() - 1).getPosition()+"___"+itemArrayList.get(itemArrayList.size() - 1).getMediaUrl()+"__"+itemArrayList.get(itemArrayList.size() - 1).getId());
            queryRef = myRef.orderByChild("position").startAt((itemArrayList.get(itemArrayList.size() - 1).getPosition() + 1)).limitToFirst(10);
        }
//else
//    queryRef=myRef.orderByChild("position").endAt("197").limitToFirst(10)

        ValueEventListener valueEventListener=(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever itemArrayList at this location is updated.
                progressBar.setVisibility(View.GONE);
//                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
                if (getActivity() != null) {
                    if (!dataSnapshot.exists()) {
                        TournamentGalleryFragment .this.loadmore = true;
                        if (TournamentGalleryFragment .this.adapter != null) {
                            TournamentGalleryFragment .this.adapter.loadMoreFail();
                        }
                        if (TournamentGalleryFragment .this.itemArrayList.size() <= 0) {
                            TournamentGalleryFragment .this.emptyViewVisibility(true);
                            TournamentGalleryFragment .this.rvMatches.setVisibility(View.GONE);
                            return;
                        }
                        return;
                    }
                    TournamentGalleryFragment .this.baseResponse = dataSnapshot;
                  //  ArrayList<Media> arrayLists = new ArrayList<>(((HashMap<String, Media>) dataSnapshot.getValue()).values());
                    ArrayList<Media> arrayList=new ArrayList<>();

                    for (DataSnapshot md : dataSnapshot.getChildren()) {
                        System.out.println("data came"+md.getValue().toString());

                        if (md.getValue() != null && !md.getValue().equals("")) {
                            Media media= md.getValue(Media.class);
                            if (itemArrayList.size() > 0 && itemArrayList.get(itemArrayList.size() - 1).getPosition() == media.getPosition()) {

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
                    System.out.println("datavvvf  "+TAG + (pasu.vadivasal.android.Utils.toString(arrayList.get(0))));

                    if (TournamentGalleryFragment .this.adapter == null) {
                        System.out.println("NEW ADAPTER SETbat");
                        //TournamentGalleryFragment .this.itemArrayList.addAll(arrayList);
                        TournamentGalleryFragment .this.adapter = new SimpleImageAdapter(TournamentGalleryFragment .this.getActivity(), R.layout.gallery_image_item, TournamentGalleryFragment .this.itemArrayList);
                        TournamentGalleryFragment .this.adapter.setEnableLoadMore(true);
                        TournamentGalleryFragment .this.rvMatches.setHasFixedSize(true);
                        GridLayoutManager layoutManager;
                        if(type==2)
                          layoutManager = new GridLayoutManager(getActivity(),1);
                        else
                         layoutManager = new GridLayoutManager(getActivity(),2);
                        TournamentGalleryFragment .this.rvMatches.setLayoutManager(layoutManager);

                        TournamentGalleryFragment .this.rvMatches.setAdapter(TournamentGalleryFragment .this.adapter);
                        // TournamentGalleryFragment .this.rvMatches.addOnItemTouchListener(new C13401());
                        TournamentGalleryFragment .this.adapter.setOnLoadMoreListener(TournamentGalleryFragment .this, TournamentGalleryFragment .this.rvMatches);

                        System.out.println("datavvv  ss" + adapter.getData().size()+"__"+(arrayList.size() % 10 != 0));
                        if (arrayList.size() % 10 != 0) {
                            TournamentGalleryFragment .this.adapter.loadMoreEnd();
                        }
                    } else {
                        if (refresh) {
                            TournamentGalleryFragment .this.adapter.getData().clear();
                            TournamentGalleryFragment .this.itemArrayList.clear();
                            TournamentGalleryFragment .this.itemArrayList.addAll(arrayList);
                            TournamentGalleryFragment .this.adapter.setNewData(arrayList);
                            TournamentGalleryFragment .this.adapter.setEnableLoadMore(true);
                            TournamentGalleryFragment .this.rvMatches.scrollToPosition(0);
                        } else {
                            TournamentGalleryFragment .this.adapter.addData((Collection) arrayList);
                            TournamentGalleryFragment .this.adapter.loadMoreComplete();
                        }
                        if (TournamentGalleryFragment .this.baseResponse != null &&   arrayList.size() % 10 != 0) {
                            TournamentGalleryFragment .this.adapter.loadMoreEnd();
                        }
                    }
                    TournamentGalleryFragment .this.loadmore = true;
                    if (TournamentGalleryFragment .this.itemArrayList.size() == 0) {
                        TournamentGalleryFragment .this.emptyViewVisibility(true);
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
        if (b) {
            this.viewEmpty.setVisibility(View.VISIBLE);
//            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
            this.tvDetail.setText(R.string.tournament_gallery_empty);
//            this.tvDetail.setVisibility(View.GONE);
            return;
        }
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

