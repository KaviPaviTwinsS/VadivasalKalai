package pasu.vadivasal.tournament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.entity.MultiItemEntity;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.dashboard.DashboardAdapter;
import pasu.vadivasal.dashboard.DashboardMatchItem;
import pasu.vadivasal.dashboard.DashboardPlayerItem;
import pasu.vadivasal.dashboard.DashboardVideoItem;
import pasu.vadivasal.dashboard.MultipleItem;
import pasu.vadivasal.globalModle.Media;

/**
 * Created by Admin on 05-11-2017.
 */

public class TournamentCommentary extends Fragment {
    private RecyclerView rvDashboard;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.temp_lay, container, false);
        rvDashboard = (RecyclerView) v.findViewById(R.id.rvDashboard);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
//        CommentaryData dataa =new CommentaryData();
//        Media media=new Media();
//        media.setLikes(23);
//        media.setType(1);
//        media.setMediaUrl("");
//        dataa.setComment(getString(R.string.dummy_bio));
//        dataa.setMedia(media);
//        dataa.setTime("29 sep 2017 5:30 PM");
 //       System.out.println("Mediaaa  "+ pasu.vadivasal.android.Utils.toString(dataa));
//        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);

         getData();
        return  v;
    }
    private void getData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Tournament/commentary");
        final String TAG = "Dashboard data";
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "Value is: " + dataSnapshot.getValue().toString());

                ArrayList<CommentaryData> datav = new ArrayList<> ( ((HashMap<String, CommentaryData>) dataSnapshot.getValue()).values());
            //    System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav)));
               // ArrayList<CommentaryData> datav = new ArrayList<CommentaryData>(sh.values());
                System.out.println("datavvv  "+ (pasu.vadivasal.android.Utils.toString(datav.get(0))));
                System.out.println("datavvv  "+ (Utils.fromJson(Utils.toString(datav),CommentaryData[].class)));
              //  CommentaryDataList datav = dataSnapshot.getValue(CommentaryDataList.class);
//                datav.setTournamentDatas( dataSnapshot.getRef());
//                datav.setBull(plarray);
//                datav.setPlayer(blarray);
//                datav.setLatestVideos(vlarray);

                if (getActivity() != null) {
                    rvDashboard.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvDashboard.setAdapter(new CommentaryRecyclerAdapter(new ArrayList<CommentaryData>(Arrays.asList(Utils.fromJson(Utils.toString(datav),CommentaryData[].class))),getActivity()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public List<MultiItemEntity> getMultipleItemData(CommentaryDataList dataList) {
        List<MultiItemEntity> list = new ArrayList<>();
        for(CommentaryData data:dataList.getData()){
            list.add(new MultipleItem(data.getTime(), data.getComment(), data.getMedia()==null?MultipleItem.TEXT:MultipleItem.IMG_TEXT));
        }
//        list.add(new MultipleItem(getString(R.string.thirukural_head), data.getThirukural(), MultipleItem.TEXT));
////        ArrayList<TournamentData> data = new ArrayList<>();
////        for (int i = 0; i < 10; i++) {
////            data.add(new TournamentData());
////        }
////        ArrayList<Video> VideosArray = new ArrayList<>();
////        for (int i = 0; i < 10; i++) {
////            VideosArray.add(new Video());
////        }
//
//        list.add(new DashboardMatchItem(getActivity(), getString(R.string.tournament), getString(R.string.tap_to_check), data.getTournamentDatas(), 11));
//        list.add(new DashboardVideoItem(getActivity(), getActivity().getString(R.string.super_bull_heroes), "", data.getLatestVideos(), 99));
//        //  list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_main), MultipleItem.TEXT));
////        ArrayList<PlayerDash> datas = new ArrayList<>();
////        for (int i = 0; i < 10; i++) {
////            datas.add(new PlayerDash());
////        }
//        list.add(new DashboardPlayerItem(getActivity(), getActivity().getString(R.string.super_cric_heroes), getActivity().getString(R.string.heroes_msg), data.getPlayer(), 20));
//        list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_sec), MultipleItem.TEXT));
//        list.add(new DashboardPlayerItem(getActivity(), getActivity().getString(R.string.super_bull_heroes), getActivity().getString(R.string.bull_heroes_msg), data.getBull(), 20));
//
//
//        //  list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_main), MultipleItem.IMG_TEXT));
//
//
////            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_TEXT_SPAN_SIZE));
////            list.add(new MultipleItem(MultipleItem.TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE, "sfjkh"));
////            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
//        // list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//        //list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));

        return list;
    }

}

