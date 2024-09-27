package pasu.vadivasal.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.TournamentData;
import pasu.vadivasal.tournament.TournamentMatchesActivity;

/**
 * Created by developer on 22/11/17.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TournamentData> mList;
    private int TYPE;
    public HorizontalAdapter(Context mContext, ArrayList<?> mList, int TYPE) {
        this.mContext = mContext;
        this.mList = (ArrayList<TournamentData>)mList;
        this.TYPE = TYPE;
        setHasStableIds(true);
    }

    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.raw_dashboard_match_item, parent, false);
        switch (TYPE){
            case 0:v.getLayoutParams().width = (int) (getScreenWidth() / 1.1); break;
            case 1:v.getLayoutParams().width = getScreenWidth() / 3; break;
            case 2:v.getLayoutParams().width = getScreenWidth() / 1; break;
        }
        HorizontalAdapter.ViewHolder viewHolder = new HorizontalAdapter.ViewHolder(v);
        return viewHolder;
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    @Override
    public void onBindViewHolder(final HorizontalAdapter.ViewHolder holder, final int position) {
     //   holder.itemName.setText(mList.get(position));
        ViewHolder viewHolder = holder;
//        // we need to show the "normal" state
//        viewHolder.itemView.setBackgroundColor(Color.WHITE);
//        viewHolder.titleView.setVisibility(View.VISIBLE);
//        // viewHolder.titleView.setText(item);
//        viewHolder.venue.setText(matchShortSummaryData.getLocation());
        TournamentData datas=mList.get(position);
        viewHolder.tvMatchInfo.setText(""+datas.getType());
        viewHolder.tvTournamentTitle.setText(datas.getName());
        if((datas.getToDate())!=0)
        viewHolder.tvSummary.setText(""+ Utils.getDateOnly(datas.getDate())+" to "+ Utils.getDateOnly(datas.getToDate()));
        else
            viewHolder.tvSummary.setText(""+ Utils.getDateOnly(datas.getDate()));
        Picasso.with(mContext).load(datas.getTournamentCoverPhoto()).into(viewHolder.tournament_cover_photo);
        if (mList.get(position).getStatus() == 2) {
            viewHolder.bullwon.setVisibility(View.GONE);
            viewHolder.playerwon.setVisibility(View.GONE);
            viewHolder.bio.setVisibility(View.GONE);

            viewHolder.tvTeamAScore.setText(""+mList.get(position).getBullwon());
            viewHolder.tvTeamBScore.setText(""+mList.get(position).getPlayerwon());
        } else {
            viewHolder.playerwon.setVisibility(View.GONE);
            viewHolder.bullwon.setVisibility(View.GONE);
            viewHolder.bio.setVisibility(View.GONE);
            viewHolder.bio.setText(""+mList.get(position).getAbout());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tournament_cover_photo;
        ImageView
                home_team_image, away_team_image;
        TextView tvTournamentTitle;
        TextView tvSummary;
        TextView tvMatchInfo, tvTeamAScore, tvTeamBScore;
        TextView home_team_scr, away_team_scr, bio;
        CardView titleView;
        AppCompatImageView delete_item;
        ImageView online, share;
        LinearLayout bullwon, playerwon;


        public ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.main_card);
            bullwon = itemView.findViewById(R.id.bullwon);
            playerwon = itemView.findViewById(R.id.playerwon);
            bio = itemView.findViewById(R.id.bio);
            bio.setVisibility(View.GONE);
            tournament_cover_photo= itemView.findViewById(R.id.tournament_cover_photo);
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, TournamentMatchesActivity.class);
                    i.putExtra(Appconstants.TourID, mList.get(getAdapterPosition()).getKey());
                    i.putExtra("about", Utils.toString(mList.get(getAdapterPosition())));
                    System.out.println("tournamentID" + mList.get(getAdapterPosition()).getKey());
                    mContext.startActivity(i);
                }
            });
            tvTournamentTitle = itemView.findViewById(R.id.tvTournamentTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvMatchInfo = itemView.findViewById(R.id.tvMatchInfo);
//            away_team_name = (TextView) itemView.findViewById(R.id.away_team_name);
//            venue = (TextView) itemView.findViewById(R.id.venue);
//            delete_item = (AppCompatImageView) itemView.findViewById(R.id.delete_item);
            tvTeamAScore = itemView.findViewById(R.id.tvTeamAScore);
            tvTeamBScore = itemView.findViewById(R.id.tvTeamBScore);
          //  itemName = (TextView) itemView.findViewById(R.id.tvItem);
        }
    }
}

