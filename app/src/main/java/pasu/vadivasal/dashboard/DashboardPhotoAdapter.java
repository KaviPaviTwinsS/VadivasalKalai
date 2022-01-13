package pasu.vadivasal.dashboard;

import android.content.Context;

import androidx.cardview.widget.CardView;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.globalModle.Media;

/**
 * Created by Admin on 26-11-2017.
 */


public class DashboardPhotoAdapter extends BaseQuickAdapter<Media, BaseViewHolder> {
    Context context;
    boolean reSize;
    int width;

    public DashboardPhotoAdapter(Context context, List<Media> data, boolean reSize) {
        super(R.layout.raw_player_leaderboard, data);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dpWidth = (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
        // Log.e(SettingsJsonConstants.ICON_WIDTH_KEY, "= " + this.width);
        this.reSize = reSize;
        this.context = context;
        this.width = displayMetrics.widthPixels;
    }

    protected void convert(BaseViewHolder helper, final Media media) {
        if (this.reSize) {
            ((CardView) helper.getView(R.id.main_card)).getLayoutParams().width = (int) (this.width/2.3);
        }
        System.out.println("ccameeeeeeeeephoto");
        Picasso.with(context).load(media.getMediaUrl()).fit().into(((ImageView) helper.getView(R.id.ivPlayer)));

    }
}
