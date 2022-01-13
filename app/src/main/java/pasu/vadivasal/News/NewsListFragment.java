package pasu.vadivasal.News;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.globalModle.News;
import pasu.vadivasal.view.TextView;

/**
 * Created by Admin on 29-12-2017.
 */

public class NewsListFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, AdapterView.OnItemSelectedListener {
    private NewsLisAdapter adapter;
    int ballType = -1;
    private boolean batsman = true;
    private List<String> filterList;
    private List<String> filterListCode;
    private boolean initSpinListener = true;
    private ArrayList<News> itemArrayList;
    private boolean loadmore;
    int tournamentId;
    private RecyclerView rvMatches;
    ViewGroup viewEmpty;
    TextView tvDetail;
    private ProgressBar progressBar;
    private DataSnapshot baseResponse;
    private String TOUR_ID = "";
    private android.widget.TextView tvTitle;
    private ChildEventListener childAddListner;
    private long lastItemId = 0;

    class C13422 implements Runnable {
        C13422() {
        }

        public void run() {
            NewsListFragment.this.adapter.loadMoreEnd();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).changeToolbarImage();
        View rootView = inflater.inflate(R.layout.commentary_layout, container, false);
      // this.TOUR_ID = getActivity().getIntent().getStringExtra(Appconstants.TourID);
        progressBar = rootView.findViewById(R.id.progressBar);
        viewEmpty = rootView.findViewById(R.id.viewEmpty);
        tvDetail = rootView.findViewById(R.id.tvDetail);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        itemArrayList = new ArrayList<>();
        rvMatches = (RecyclerView) rootView.findViewById(R.id.rvMatches);
        this.rvMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        setData();

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
        final DatabaseReference myRef = database.getReference("News");
        final String TAG = "Commentary itemArrayList";

        Query queryRef;
        System.out.println("postionnnncomm" + page + TOUR_ID);
        if (page == null)
            queryRef = myRef
                    .orderByChild("date")
                    .limitToLast(10);
        else
            queryRef = myRef.orderByChild("date").endAt(datetime).limitToFirst(10);

        ValueEventListener valueEventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever itemArrayList at this location is updated.
                progressBar.setVisibility(View.GONE);
//                Log.d(TAG, "Value is: Leader" + dataSnapshot.getValue().toString());
                if (getActivity() != null) {
                    if (!dataSnapshot.exists()) {
                        NewsListFragment.this.loadmore = true;
                        if (NewsListFragment.this.adapter != null) {
                            NewsListFragment.this.adapter.loadMoreFail();
                        }
                        if (NewsListFragment.this.itemArrayList.size() <= 0) {
                            NewsListFragment.this.emptyViewVisibility(true);
                            NewsListFragment.this.rvMatches.setVisibility(View.GONE);
                            return;
                        }
                        return;
                    }
                    NewsListFragment.this.baseResponse = dataSnapshot;
                    ArrayList<News> arrayList = new ArrayList<>();
                    for (DataSnapshot md : dataSnapshot.getChildren()) {
                        if (md.getValue() != null && !md.getValue().equals("")) {
                            System.out.println("naga____"+dataSnapshot.getValue().toString());
                            News matchDetails = md.getValue(News.class);
                            if (arrayList.size() > 0)
                                System.out.println("datatacccc" + arrayList.size() + "__" + lastItemId + "___" + matchDetails.getDate());
                            if (arrayList.size() > 0 && lastItemId == matchDetails.getDate()) {

                                System.out.println("datatacccc");
                            } else {

//                                    matchDetails.setMatch_id(Integer.parseInt(md.getTournament_key()));
//                                    matchDetails.setmatchShortSummary(CommanData.toString(md.getValue()));

                                arrayList.add(matchDetails);
                            }
                        }
                    }
//                    ArrayList<News> arrayList = new ArrayList<>(Arrays.asList(Utils.fromJson(Utils.toString(arrayLists), News[].class)));
//                    for (int i = 0; i < arrayList.size(); i++) {
//                        System.out.println("cameaddbat" + i + "____" + arrayList.get(i).getDate());
//                        itemArrayList.add(arrayList.get(i));
//                    }
                    Collections.reverse(arrayList);
                    lastItemId = arrayList.get(arrayList.size() - 1).getDate();
                    if (itemArrayList.size() == 0)
                        itemArrayList.addAll(arrayList);
                    System.out.println("datataccccitem" + itemArrayList.size());
                    //   itemArrayList.addAll(Arrays.asList(Utils.fromJson(Utils.toString(arrayList),News[].class)));
                    //    System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav)));
                    // ArrayList<News> datav = new ArrayList<News>(sh.values());
                    System.out.println("datavvvf  " + TAG + (pasu.vadivasal.android.Utils.toString(arrayList.get(0))));

                    if (NewsListFragment.this.adapter == null) {
                        System.out.println("NEW ADAPTER SETbat");
                        //NewsListFragment .this.itemArrayList.addAll(arrayList);
                        NewsListFragment.this.adapter = new NewsLisAdapter(NewsListFragment.this.getActivity(), R.layout.news_item, NewsListFragment.this.itemArrayList,true);
                        NewsListFragment.this.adapter.setEnableLoadMore(true);
                        NewsListFragment.this.rvMatches.setAdapter(NewsListFragment.this.adapter);
                        // NewsListFragment .this.rvMatches.addOnItemTouchListener(new C13401());
                        NewsListFragment.this.adapter.setOnLoadMoreListener(NewsListFragment.this, NewsListFragment.this.rvMatches);

                        if (arrayList.size() % 10 != 0) {
                            NewsListFragment.this.adapter.loadMoreEnd();
                        }
                        final DatabaseReference myRefs = database.getReference("News" );
                        try {
                            // String firstKey = (String) ((HashMap<String, News>) dataSnapshot.getValue()).keySet().toArray()[0];
                            System.out.println("datavvv  ssfirst key" + adapter.getData().size() + "__" + itemArrayList.get(0).getDate());
                            myRefs.orderByChild("time").startAt(itemArrayList.get(0).getDate()).addChildEventListener(childAddListner);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (refresh) {
                            NewsListFragment.this.adapter.getData().clear();
                            NewsListFragment.this.itemArrayList.clear();
                            NewsListFragment.this.itemArrayList.addAll(arrayList);
                            NewsListFragment.this.adapter.setNewData(arrayList);
                            NewsListFragment.this.adapter.setEnableLoadMore(true);
                            NewsListFragment.this.rvMatches.scrollToPosition(0);
                        } else {
                            System.out.println("datataccccitemaddd" + arrayList.size() + NewsListFragment.this.adapter.getItemCount());
                            NewsListFragment.this.adapter.addData((Collection) arrayList);
                            NewsListFragment.this.adapter.loadMoreComplete();
                            System.out.println("datataccccitemafter" + arrayList.size() + NewsListFragment.this.adapter.getItemCount());
                        }

                    }
                    if (NewsListFragment.this.baseResponse != null && arrayList.size() % 10 != 0) {
                        NewsListFragment.this.adapter.loadMoreEnd();
                    } else
                        NewsListFragment.this.loadmore = true;
                    if (NewsListFragment.this.itemArrayList.size() == 0) {
                        NewsListFragment.this.emptyViewVisibility(true);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });


        childAddListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("commentary added" + dataSnapshot.getValue());

                if (dataSnapshot != null && itemArrayList != null) {

                    News arrayLists = dataSnapshot.getValue(News.class);
                    if (itemArrayList.size() > 0 && itemArrayList.get(0).getDate() != (arrayLists).getDate()) {
                        System.out.println("commentary adding data");
                        NewsListFragment.this.adapter.addData(0, arrayLists);
                        NewsListFragment.this.adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        System.out.println("onLoadMoreRequested" + this.loadmore + "___" + (itemArrayList.size() % 10 != 0));
        if (!this.loadmore || (itemArrayList.size() % 10 != 0)) {
            new Handler().postDelayed(new NewsListFragment.C13422(), 1500);
        } else if (this.batsman) {
            System.out.println("Load more" + itemArrayList.get(itemArrayList.size() - 1).getDate());
            getBattingLeaderboard(String.valueOf(itemArrayList.get(itemArrayList.size() - 1).getDate()), (itemArrayList.get(itemArrayList.size() - 1).getDate()), false);
        } else {
            // getBowlingLeaderboard(Long.valueOf(this.baseResponse.getPage().getNextPage()), Long.valueOf(this.baseResponse.getPage().getDatetime()), false);
        }
    }

    private void emptyViewVisibility(boolean b) {
        if (b) {
            this.viewEmpty.setVisibility(View.VISIBLE);
//            this.ivImage.setImageResource(R.drawable.leaderboard_blankstate);
            this.tvTitle.setText(R.string.commentary_empty);
            this.tvDetail.setVisibility(View.GONE);
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
