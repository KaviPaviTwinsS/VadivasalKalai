package pasu.vadivasal.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pasu.vadivasal.R;
import pasu.vadivasal.globalModle.Media;

/**
 * Created by Admin on 17-09-2017.
 */

public class MatchesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageGridAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.simple_listview, container, false);
        recyclerView = v.findViewById(R.id.recyclerview);
        System.out.println("Cameeeeeeeecreate" + recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //  recyclerView.setAdapter(mAdapter);
//        if (getActivity() instanceof MaterialUpConceptActivity)
//            setData(((MaterialUpConceptActivity) getActivity()).getMediaData());
        return v;
    }

    public void setData(ArrayList<Media> mediaArrayList) {
        System.out.println("Cameeeeeeeeset" + recyclerView+"__"+mediaArrayList.size());
        if (recyclerView != null) {
            if (mAdapter == null) {
                mAdapter = new ImageGridAdapter(getActivity(),new ArrayList<Media>());
                recyclerView.setAdapter(mAdapter);
               mAdapter.add(mAdapter.getItemCount(),mediaArrayList);
            } else
              //  for (int i = 0; i < mediaArrayList.size(); i++) {
                    System.out.println("Cameeeeeeee" + mAdapter.getItemCount());
                    mAdapter.add(mAdapter.getItemCount(), mediaArrayList);
              //  }
           // mAdapter.notifyDataSetChanged();
        }

    }
}

