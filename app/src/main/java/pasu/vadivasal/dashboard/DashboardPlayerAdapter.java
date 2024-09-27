package pasu.vadivasal.dashboard;

import android.content.Context;
import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;

import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.model.PlayerDash;

public class DashboardPlayerAdapter extends BaseQuickAdapter<PlayerDash, BaseViewHolder> {
    Context context;
    List<PlayerDash> data;
    private int playerSelect = -1;
    int width;

    public DashboardPlayerAdapter(Context context, List<PlayerDash> data) {
        super(R.layout.raw_dashboard_player_and_team_item, data);
        this.data = data;
        this.context = context;

        this.width = (context.getResources().getDisplayMetrics().widthPixels * 28) / 100;
    }

    protected void convert(BaseViewHolder helper, PlayerDash player) {
        ((CardView) helper.getView(R.id.card_view)).getLayoutParams().width = this.width;
        ((TextView) helper.getView(R.id.tvName)).setText(player.getName());
        ImageView playerImage = ((ImageView) helper.getView(R.id.ivPlayer));
        Picasso.with(context).load(player.getImageUrl()).into(playerImage);


        // helper.setText((int) R.id.tvName, player.getName());
//        ImageView img = (SquaredImageView) helper.getView(R.id.ivPlayer);
//        if (player.getPhoto() == null) {
//            img.setImageResource(R.drawable.ic_p1);
//        } else {
//            Picasso.with(this.mContext).load(player.getPhoto()).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).into(img);
//        }
    }


    public PlayerDash getPlayer() {
        if (this.playerSelect != -1) {
            return (PlayerDash) this.data.get(this.playerSelect);
        }
        return null;
    }
}
