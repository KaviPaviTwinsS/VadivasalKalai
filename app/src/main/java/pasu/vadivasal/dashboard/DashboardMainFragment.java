package pasu.vadivasal.dashboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.DashBoardData;

/**
 * Created by developer on 20/9/17.
 */

public class DashboardMainFragment extends Fragment {
    private RecyclerView rvDashboard;
    private ProgressBar progressBar;
public static boolean versionAlertShown;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.temp_lay, container, false);
        rvDashboard = v.findViewById(R.id.rvDashboard);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        getData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public static String toString(Object s) {
        return new Gson().toJson(s);
    }

    private void getData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        final String TAG = "Dashboard data";
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "Value is: " + dataSnapshot.getValue().toString());
                DashBoardData datav = dataSnapshot.getValue(DashBoardData.class);
                int verCode =0;
                PackageInfo pInfo=null;
                try {
                     pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                    String version = pInfo.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if(pInfo!=null)
                verCode= pInfo.versionCode;
                if(verCode<datav.getLatestVerison() && !versionAlertShown){

                if(datav.isForceUpdate())
                    Utils.showAlert(getActivity(),datav.getVersionAlertTitle(),datav.getVersionAlert(),getString(R.string.ok),"", new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName()));
                            getActivity().startActivity(intent);
                            SessionSave.saveSession(Appconstants.FORCE_UPDATE,true,getActivity());
                        }
                    },false);
                else if(!datav.isForceUpdate()){
                    versionAlertShown=true;
                    Utils.showAlert(getActivity(),datav.getVersionAlertTitle(),datav.getVersionAlert(),getString(R.string.ok),getString(R.string.btn_maybe_later), new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("position___"+i);
                            if(i!=-2){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName()));
                            getActivity().startActivity(intent);}
                        }
                    },true);}}
                if (getActivity() != null && !(datav.isForceUpdate() && verCode<datav.getLatestVerison())) {
                    SessionSave.saveSession(Appconstants.ABOUT_APP,datav.getAbout_app(),getActivity());
                    SessionSave.saveSession(Appconstants.SHARE_CONTENT,datav.getShare_app(),getActivity());
                    SessionSave.saveSession(Appconstants.FORCE_UPDATE,false,getActivity());
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

        list.add(new DashboardMatchItem(getActivity(), getString(R.string.title_tournament), getString(R.string.tap_to_check), data.getTournamentDatas(), 11));

        //  list.add(new MultipleItem(getString(R.string.thirukural_head), getString(R.string.thirukural_main), MultipleItem.TEXT));
//        ArrayList<PlayerDash> datas = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            datas.add(new PlayerDash());
//        }
        list.add(new DashboardPlayerItem(getActivity(), getActivity().getString(R.string.super_cric_heroes), getActivity().getString(R.string.heroes_msg), data.getPlayer(), 20));
        list.add(new DashboardVideoItem(getActivity(), getActivity().getString(R.string.super_bull_heroes), "", data.getLatestVideos(), 99));
        list.add(new DashboardPhotoItem(getActivity(),getString(R.string.trending_photos), getString(R.string.tap_to_see),data.getPhotos(), MultipleItem.IMG));
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
