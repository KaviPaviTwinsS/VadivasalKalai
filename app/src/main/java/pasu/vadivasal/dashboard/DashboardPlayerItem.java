package pasu.vadivasal.dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.globalModle.Player;
import pasu.vadivasal.model.PlayerDash;

/**
 * Created by developer on 15/9/17.
 */


public class DashboardPlayerItem extends BaseDashboardMultiItem {
    private DashboardPlayerAdapter adapter;
    private ArrayList<PlayerDash> data;
    int width;
    Context context;

    public DashboardPlayerItem(Context context, String title, String description, ArrayList<PlayerDash> data, int itemType) {
        super(title, description, itemType,data);
        this.data = data;
        this.context = context;
        this.adapter = new DashboardPlayerAdapter(context, this.data);
        System.out.println("datttttta" + data.size());
        this.width = (context.getResources().getDisplayMetrics().widthPixels * 30) / 100;
    }


    protected BaseQuickAdapter getAdapter() {
        return this.adapter;
    }
}
