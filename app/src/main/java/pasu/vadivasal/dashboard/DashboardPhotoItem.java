package pasu.vadivasal.dashboard;

import android.content.Context;

import java.util.ArrayList;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.model.Video;

/**
 * Created by Admin on 26-11-2017.
 */

public class DashboardPhotoItem extends BaseDashboardMultiItem {
    private DashboardPhotoAdapter adapter;
    private ArrayList<Media> data;

    public DashboardPhotoItem(Context context, String title, String description, ArrayList<Media> data, int itemType) {
        super(title, description, itemType,data);
        this.data = data;
        this.adapter = new DashboardPhotoAdapter(context, this.data, true);
    }

    protected BaseQuickAdapter getAdapter() {
        return this.adapter;
    }
}
