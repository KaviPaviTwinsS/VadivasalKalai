package pasu.vadivasal.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.entity.MultiItemEntity;
import pasu.vadivasal.model.DashBoardData;

/**
 * Created by developer on 20/9/17.
 */

public class DashboardMainFragment extends Fragment {
    private RecyclerView rvDashboard;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.temp_lay, container, false);
        rvDashboard = (RecyclerView) v.findViewById(R.id.rvDashboard);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        // mTextMessage = (TextView) v.findViewById(R.id.message);
//        DashBoardData data = new DashBoardData();
//        ArrayList<TournamentData> tarray = new ArrayList<>();
//        ArrayList<PlayerDash> plarray = new ArrayList<>();
//        ArrayList<PlayerDash> blarray = new ArrayList<>();
//        ArrayList<Video> vlarray = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            TournamentData tournamentData = new TournamentData();
//            tournamentData.setBullwon(100);
//            tournamentData.setPlayerwon(150);
//            tournamentData.setPlace("Allanganallur");
//            tournamentData.setType(2);
//            tarray.add(tournamentData);
//
//            PlayerDash bull = new PlayerDash();
//            bull.setImageUrl("dskjf");
//            bull.setName("Veeran");
//            blarray.add(bull);
//
//
//            PlayerDash tamper = new PlayerDash();
//            tamper.setImageUrl("dskjf");
//            tamper.setName("Veeran");
//            plarray.add(tamper);
//
//            Video vl = new Video();
//            vl.setDescription("helloooo");
//            vl.setLikes(125);
//            vlarray.add(vl);
//
//        }
//        data.setTournamentDatas(tarray);
//        data.setBull(plarray);
//        data.setPlayer(blarray);
//        data.setLatestVideos(vlarray);
//        System.out.println("dashdata" + toString(data));

        getData();
//        final List<MultiItemEntity> data = getMultipleItemData(new DashBoardData());
//        final DashboardAdapter multipleItemAdapter = new DashboardAdapter(getActivity(), data);
//        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
//        rvDashboard.setLayoutManager(manager);
//        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return 4;
//            }
//        });
//        rvDashboard.setAdapter(multipleItemAdapter);
        return v;
    }

    public static String toString(Object s) {
        return new Gson().toJson(s);
    }

    private void getData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        final String TAG = "Dashboard data";
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "Value is: " + dataSnapshot.getValue().toString());
                DashBoardData datav = dataSnapshot.getValue(DashBoardData.class);
//                datav.setTournamentDatas( dataSnapshot.getRef());
//                datav.setBull(plarray);
//                datav.setPlayer(blarray);
//                datav.setLatestVideos(vlarray);

                if (getActivity() != null) {
                    final List<MultiItemEntity> data = getMultipleItemData(datav);
                    final DashboardAdapter multipleItemAdapter = new DashboardAdapter(getActivity(), data);
                    final GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
                    rvDashboard.setLayoutManager(manager);
                    multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                            return 4;
                        }
                    });
                    rvDashboard.setAdapter(multipleItemAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public List<MultiItemEntity> getMultipleItemData(DashBoardData data) {
        List<MultiItemEntity> list = new ArrayList<>();
        list.add(new MultipleItem(getString(R.string.thirukural_head), data.getThirukural(), MultipleItem.TEXT));
//        ArrayList<TournamentData> data = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            data.add(new TournamentData());
//        }
//        ArrayList<Video> VideosArray = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            VideosArray.add(new Video());
//        }

        list.add(new DashboardMatchItem(getActivity(), getString(R.string.tournament), getString(R.string.tap_to_check), data.getTournamentDatas(), 11));
        list.add(new DashboardVideoItem(getActivity(), getActivity().getString(R.string.super_bull_heroes), "", data.getLatestVideos(), 99));
        //  list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_main), MultipleItem.TEXT));
//        ArrayList<PlayerDash> datas = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            datas.add(new PlayerDash());
//        }
        list.add(new DashboardPlayerItem(getActivity(), getActivity().getString(R.string.super_cric_heroes), getActivity().getString(R.string.heroes_msg), data.getPlayer(), 20));
        list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_sec), MultipleItem.TEXT));
        list.add(new DashboardPlayerItem(getActivity(), getActivity().getString(R.string.super_bull_heroes), getActivity().getString(R.string.bull_heroes_msg), data.getBull(), 20));


        //  list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_main), MultipleItem.IMG_TEXT));


//            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_TEXT_SPAN_SIZE));
//            list.add(new MultipleItem(MultipleItem.TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE, "sfjkh"));
//            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
        // list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
        //list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));

        return list;
    }

}
