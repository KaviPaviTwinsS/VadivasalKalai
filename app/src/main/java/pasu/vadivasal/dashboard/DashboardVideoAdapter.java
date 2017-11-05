package pasu.vadivasal.dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;

import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.model.Video;


public class DashboardVideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    Context context;
    boolean reSize;
    int width;

    public DashboardVideoAdapter(Context context, List<Video> data, boolean reSize) {
        super(R.layout.fragment_dashboard_media_item, data);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dpWidth = (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
       // Log.e(SettingsJsonConstants.ICON_WIDTH_KEY, "= " + this.width);
        this.reSize = reSize;
        this.context = context;
        this.width = displayMetrics.widthPixels;
    }

    protected void convert(BaseViewHolder helper, Video media) {
        if (this.reSize) {
            ((CardView) helper.getView(R.id.main_card)).getLayoutParams().width = this.width;
        }
        helper.setVisible(R.id.tvTitle, true);
        helper.setVisible(R.id.ivPlay, true);
        helper.setVisible(R.id.ivShare, true);
        helper.addOnClickListener(R.id.ivShare);
        helper.setVisible(R.id.tvDescription, false);
        ((ImageView) helper.getView(R.id.imgMedia)).setScaleType(ScaleType.CENTER_CROP);
        try {
           // helper.setImageResource(R.id.imgMedia, R.drawable.banner_marketing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
