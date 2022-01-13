package pasu.vadivasal.matchList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.TournamentData;
import pasu.vadivasal.tournament.TournamentMatchesActivity;


/**
 * Created by developer on 21/2/17.
 */


public class SavedGameListAdapter extends RecyclerView.Adapter {
    private FirebaseDatabase database;
    private Context context;
    private static final int PENDING_REMOVAL_TIMEOUT = 1500; // 3sec

    //    List<String> items;
//    List<String> itemsPendingRemoval;
    int lastInsertedIndex; // so we can add some more items for testing purposes
    boolean undoOn = true; // is undo on, you can turn it on from the toolbar menu

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be
    List<TournamentData> data = new ArrayList<>();


    boolean viewer;
    private TournamentData onlineMatchID;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    private AlertDialog needSignUp;

//    public SavedGameListAdapter(Context context, RealmResults<TournamentData> data) {
//        auth = FirebaseAuth.getInstance();
//        this.data = realm.copyFromRealm(data);
//        this.context = context;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage(context.getString(R.string.processing));
//
//    }

    public SavedGameListAdapter(Context context, ArrayList<TournamentData> data) {

        this.data = data;
        this.context = context;
        this.viewer = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        TestViewHolder viewHolder = (TestViewHolder) holder;
//        // we need to show the "normal" state
//        viewHolder.itemView.setBackgroundColor(Color.WHITE);
//        viewHolder.titleView.setVisibility(View.VISIBLE);
//        // viewHolder.titleView.setText(item);
//        viewHolder.venue.setText(matchShortSummaryData.getLocation());
        TournamentData datas=data.get(position);
        viewHolder.tvMatchInfo.setText(""+datas.getType());
        viewHolder.tvTournamentTitle.setText(datas.getName());
        viewHolder.tvSummary.setText(""+ Utils.getDate(datas.getDate()));
        Picasso.with(context).load(datas.getTournamentCoverPhoto()).into(viewHolder.tournament_cover_photo);
        if (data.get(position).getStatus() == 2) {
            viewHolder.bullwon.setVisibility(View.GONE);
            viewHolder.playerwon.setVisibility(View.GONE);
            viewHolder.bio.setVisibility(View.GONE);
            viewHolder.tvTeamAScore.setText(""+data.get(position).getBullwon());
            viewHolder.tvTeamBScore.setText(""+data.get(position).getPlayerwon());
        } else {
            viewHolder.playerwon.setVisibility(View.GONE);
            viewHolder.bullwon.setVisibility(View.GONE);
            viewHolder.bio.setVisibility(View.GONE);
            viewHolder.bio.setText(""+data.get(position).getAbout());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public void addData(ArrayList<TournamentData> datas) {
//        System.out.println("md.getValue()$$" + datas.size() + "__" + data.size());
//        data.addAll(datas);
//        System.out.println("md.getValue()$" + datas.size() + "__" + data.size());
        notifyDataSetChanged();
    }


    /**
     * ViewHolder capable of presenting two states: "normal" and "undo" state.
     */
    class TestViewHolder extends RecyclerView.ViewHolder {

        ImageView
                home_team_image, away_team_image;
        TextView tvTournamentTitle;
        TextView tvSummary;
        TextView tvMatchInfo, tvTeamAScore, tvTeamBScore;
        TextView home_team_scr, away_team_scr, bio;
        CardView titleView;
        AppCompatImageView delete_item;
        ImageView tournament_cover_photo, share;
        LinearLayout bullwon, playerwon;

        public TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_dashboard_match_item, parent, false));
            titleView = itemView.findViewById(R.id.main_card);
            bullwon = itemView.findViewById(R.id.bullwon);
            playerwon = itemView.findViewById(R.id.playerwon);
            bio = itemView.findViewById(R.id.bio);
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, TournamentMatchesActivity.class);
                    i.putExtra(Appconstants.TourID, data.get(getAdapterPosition()).getKey());
                    i.putExtra("about",Utils.toString(data.get(getAdapterPosition())));
                    System.out.println("tournamentID" + data.get(getAdapterPosition()).getKey());
                    context.startActivity(i);
                }
            });
            tvTournamentTitle = itemView.findViewById(R.id.tvTournamentTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvMatchInfo = itemView.findViewById(R.id.tvMatchInfo);
//            away_team_name = (TextView) itemView.findViewById(R.id.away_team_name);
//            venue = (TextView) itemView.findViewById(R.id.venue);
            tournament_cover_photo = (AppCompatImageView) itemView.findViewById(R.id.tournament_cover_photo);
            tvTeamAScore = itemView.findViewById(R.id.tvTeamAScore);
            tvTeamBScore = itemView.findViewById(R.id.tvTeamBScore);
        }
    }
}
