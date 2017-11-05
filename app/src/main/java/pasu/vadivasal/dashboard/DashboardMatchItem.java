package pasu.vadivasal.dashboard;

import android.content.Context;


import java.util.ArrayList;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.globalModle.Match;
import pasu.vadivasal.model.TournamentData;

public class DashboardMatchItem extends BaseDashboardMultiItem {
    private DashboardMatchAdapter adapter;
    private ArrayList<TournamentData> data;

    public DashboardMatchItem(Context context, String title, String description, ArrayList<TournamentData> data, int itemType) {
        super(title, description, itemType);
        this.data = data;
        this.adapter = new DashboardMatchAdapter(context, this.data);
        System.out.println("datttttta"+data.size());
    }

    protected BaseQuickAdapter getAdapter() {
        return this.adapter;
    }
}
