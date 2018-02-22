package pasu.vadivasal.dashboard;

import android.content.Context;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.model.Video;

public class DashboardVideoItem extends BaseDashboardMultiItem {
    private DashboardVideoAdapter adapter;
    private ArrayList<Media> data;

    public DashboardVideoItem(Context context, String title, String description, ArrayList<Media> data, int itemType) {
        super(title, description, itemType,data);
        this.data = data;
        this.adapter = new DashboardVideoAdapter(context, this.data, true);
    }

    protected BaseQuickAdapter getAdapter() {
        return this.adapter;
    }
}
