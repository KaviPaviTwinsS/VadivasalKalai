package pasu.vadivasal.Profile;

import android.app.Activity;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.view.View;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Player;

public class PlayerAdapter extends BaseQuickAdapter<Player, BaseViewHolder> {
    public boolean batsmanSelection = false;
    public ArrayList<Player> dataSelected;
    private int fragPos = -1;
    public boolean isHighlight;
    public boolean isSelectMultiplePlayer = false;
    boolean isShowRemove = false;
    public boolean isShowUnVerified = false;
    public boolean isSquadData = false;
    Activity mActivity;
    OnItemClickListener mItemClickListener;
    Boolean noSelection = Boolean.valueOf(false);
    int pos;
    Player selectedPlayer;
    String selectedPlayerName = "";
    private int selectedPos = -1;
    public boolean showOvers = false;

    public interface OnItemClickListener {
        void onItemClick(View view, int i, boolean z);
    }

    public PlayerAdapter(int layoutResId, ArrayList<Player> data, Activity activity) {
        super(layoutResId, data);
        this.mActivity = activity;
        this.dataSelected = new ArrayList();
    }

    public PlayerAdapter(int raw_team_player_grid_activity, ArrayList<Player> itemArrayList, Activity context, int position) {
        super(raw_team_player_grid_activity, itemArrayList);
        this.mActivity = context;
        this.dataSelected = new ArrayList();
        this.fragPos = position;
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void onPlayerSelected(Activity activity) {
        Intent intent;
        if (this.isSelectMultiplePlayer) {
            if (this.selectedPlayerName.isEmpty()) {
                Utils.showToast(activity, activity.getString(R.string.error_msg_please_select_any_player), 1, false);
                return;
            }
            intent = new Intent();
            //intent.putParcelableArrayListExtra(AppConstants.EXTRA_PLAYER_LIST_SELECTED, this.dataSelected);
            Activity activity2 = this.mActivity;
            activity.setResult(-1, intent);
            activity.finish();
        } else if (this.selectedPlayer == null) {
            Utils.showToast(activity, activity.getString(R.string.error_msg_please_select_any_player), 1, false);
        } else {
            this.mActivity = activity;
            if (this.showOvers || this.batsmanSelection) {
                intent = new Intent();
               // intent.putExtra(AppConstants.EXTRA_SELECTED_PLAYER, this.selectedPlayer);
                activity.setResult(-1, intent);
                activity.finish();
            }
//            intent = new Intent(activity, StartInningsActivity.class);
//            intent.putExtra(AppConstants.EXTRA_SELECTED_PLAYER, this.selectedPlayer);
//            activity.setResult(-1, intent);
//            activity.finish();
        }
    }

    protected void convert(final BaseViewHolder holder, Player player) {
//        holder.setOnClickListener(R.id.btnRemove, new OnClickListener() {
//            public void onClick(View v) {
//                PlayerAdapter.this.removeAt(holder.getLayoutPosition());
//            }
//        });
//        if (this.isShowUnVerified) {
//            if (player.getIsVerified() == 0) {
//                holder.setVisible(R.id.tvUnVerified, true);
//            } else {
//                holder.setVisible(R.id.tvUnVerified, false);
//            }
//        }
//        holder.setText((int) R.id.tvPlayerName, player.getName());
//        if (player.getPhoto() == null) {
//            holder.setImageResource(R.id.imgPlayer, R.drawable.ic_placeholder_player);
//        } else {
//            Picasso.with(this.mContext).load(player.getPhoto() + AppConstants.THUMB_IMAGE).placeholder((int) R.drawable.ic_placeholder_player).error((int) R.drawable.ic_placeholder_player).fit().centerCrop().into((ImageView) holder.getView(R.id.imgPlayer));
//        }
//        if (!this.isSelectMultiplePlayer) {
//            if (this.selectedPos >= 0) {
//                System.out.println("fragPos " + this.fragPos + "  selectedPos " + this.selectedPos);
//                if (this.selectedPos == holder.getLayoutPosition()) {
//                    selectTeamView(holder.convertView);
//                } else {
//                    deselectTeamView(holder.convertView);
//                }
//            } else {
//                deselectTeamView(holder.convertView);
//            }
//        }
//        if (this.isShowRemove) {
//            holder.setVisible(R.id.btnRemove, true);
//        } else {
//            holder.setVisible(R.id.btnRemove, false);
//        }
//        if (this.showOvers) {
//            holder.setVisible(R.id.tvOvers, true);
//            if (player.getBowlingInfo() != null) {
//                holder.setText((int) R.id.tvOvers, player.getBowlingInfo().getOvers() + " Overs");
//            } else {
//                holder.setText((int) R.id.tvOvers, (CharSequence) "0 Overs");
//            }
//        } else {
//            holder.setVisible(R.id.tvOvers, false);
//        }
//        if (this.isSelectMultiplePlayer) {
//            this.pos = holder.getLayoutPosition();
//            if (((Player) getData().get(this.pos)).isSelected()) {
//                selectTeamView(holder.convertView);
//                holder.setVisible(R.id.floatTick, false);
//            } else if (this.isHighlight && ((Player) getData().get(this.pos)).getIsInSquad() == 1) {
//                selectTeamViewBorder(holder.convertView);
//                holder.setVisible(R.id.floatTick, true);
//            } else {
//                deselectTeamView(holder.convertView);
//                holder.setVisible(R.id.floatTick, false);
//            }
//            holder.setTag(R.id.fmLayoutPlayerGrid, Integer.valueOf(this.pos));
//        }
//        if (!this.isSquadData) {
//            return;
//        }
//        if (player.getIsInSquad() == 1) {
//            selectTeamViewBorder(holder.convertView);
//            holder.setVisible(R.id.floatTick, true);
//            if (player.isSubstitute()) {
//                holder.setVisible(R.id.floatTick, true);
//                holder.setImageResource(R.id.floatTick, R.drawable.substitute);
//                return;
//            }
//            holder.setImageResource(R.id.floatTick, getImageResourse(player));
//            return;
//        }
//        System.out.println("SUBSTITUTE " + player.isSubstitute());
//        if (player.isSubstitute()) {
//            holder.setVisible(R.id.floatTick, true);
//            holder.setImageResource(R.id.floatTick, R.drawable.substitute);
//        } else {
//            holder.setVisible(R.id.floatTick, false);
//        }
//        deselectTeamView(holder.convertView);
    }

    private int getImageResourse(Player player) {
//        if (player.getIsCaptain() == 1 && player.getIsWicketKeeper() == 1) {
//            return R.drawable.captain_wicketkeeper;
//        }
//        if (player.getIsCaptain() == 1) {
//            return R.drawable.captain;
//        }
//        if (player.getIsWicketKeeper() == 1) {
//            return R.drawable.wicket_keeper;
//        }
        //return R.drawable.tick_active;
        return 0;
    }

    public void removeAt(int position) {
        showDialog(position);
    }

    public void showDialog(final int btnPosition) {
//        new Builder(this.mActivity, R.style.CustomAlertDialogStyle).setTitle((int) R.string.alert_title_remove_player).setMessage(this.mActivity.getString(R.string.alert_msg_remove_player)).setPositiveButton(this.mActivity.getString(R.string.alert_btn_remove), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case -1:
//                        PlayerAdapter.this.getData().remove(btnPosition);
//                        PlayerAdapter.this.notifyItemRemoved(btnPosition);
//                        PlayerAdapter.this.notifyItemRangeChanged(btnPosition, PlayerAdapter.this.getData().size());
//                        return;
//                    default:
//                        return;
//                }
//            }
//        }).setNegativeButton(this.mActivity.getString(R.string.btn_no), null).show();
    }

    public void onPlayerClick(View v, Player player, int position) {
//        FrameLayout rootLayout = (FrameLayout) v.findViewById(R.id.fmLayoutPlayerGrid);
//        if (this.isSelectMultiplePlayer) {
//            System.out.println("clicked Pos " + position);
//            if (((Player) getData().get(position)).isSelected()) {
//                refreshList(((Player) getData().get(position)).getPkPlayerId());
//                ((Player) getData().get(position)).setSelected(false);
//                deselectTeamView(v);
//            } else {
//                this.dataSelected.add(getData().get(position));
//                selectTeamView(v);
//                ((Player) getData().get(position)).setSelected(true);
//            }
//            this.selectedPlayerName = player.getName();
//        } else {
//            if (this.selectedPos >= 0) {
//                notifyDataSetChanged();
//            }
//            this.selectedPos = position;
//            notifyDataSetChanged();
//            this.selectedPlayer = player;
//        }
//        if (this.mItemClickListener != null) {
//            this.mItemClickListener.onItemClick(v, position, v.findViewById(R.id.tvUnVerified).isShown());
//        }
    }

    private void selectTeamView(View v) {
//        CardView cardView = (CardView) v.findViewById(R.id.card_view);
//        cardView.setCardBackgroundColor(ContextCompat.getColor(this.mContext, R.color.green_background_color));
//        v.findViewById(R.id.imgSelected).setVisibility(View.VISIBLE);
//        v.findViewById(R.id.cvSelectedBackground).setVisibility(View.VISIBLE);
       // cardView.setCardElevation(10.0f);
    }

    private void selectTeamViewBorder(View v) {
        CardView cardView = (CardView) v.findViewById(R.id.card_view);
      //  cardView.setCardBackgroundColor(ContextCompat.getColor(this.mContext, R.color.green_background_color));
        cardView.setCardElevation(10.0f);
    }

    private void deselectTeamView(View v) {
        CardView cardView = (CardView) v.findViewById(R.id.card_view);
      //  cardView.setCardBackgroundColor(ContextCompat.getColor(this.mContext, 17170443));
//        v.findViewById(R.id.imgSelected).setVisibility(View.GONE);
//        v.findViewById(R.id.cvSelectedBackground).setVisibility(View.GONE);
        cardView.setCardElevation(4.0f);
    }

    private void refreshList(int playerId) {
        for (int j = 0; j < this.dataSelected.size(); j++) {
//            if (((Player) this.dataSelected.get(j)).getPkPlayerId() == playerId) {
//                this.dataSelected.remove(j);
//                return;
//            }
        }
    }

    public void setSelectedPlayerList(ArrayList<Player> selectedPlayerList) {
        if (selectedPlayerList.size() != 0) {
            this.dataSelected = selectedPlayerList;
            for (int i = 0; i < getData().size(); i++) {
                int j = 0;
//                while (j < this.dataSelected.size()) {
//                    if (((Player) this.dataSelected.get(j)).getPkPlayerId() == ((Player) getData().get(i)).getPkPlayerId()) {
//                        ((Player) getData().get(i)).setSelected(true);
//                        break;
//                    } else {
//                        ((Player) getData().get(i)).setSelected(false);
//                        j++;
//                    }
//                }
            }
            notifyDataSetChanged();
        }
    }

    public void selectAllPlayer() {
        this.dataSelected.clear();
//        for (int i = 0; i < getData().size(); i++) {
//            ((Player) getData().get(i)).setSelected(true);
//        }
        this.dataSelected.addAll(getData());
        notifyDataSetChanged();
    }

    public void unSelectPlayer() {
        this.selectedPos = -1;
        this.selectedPlayer = null;
        System.out.println("fragPos " + this.fragPos + "  selectedPos >> " + this.selectedPos);
        notifyDataSetChanged();
    }

    public void setSelected(ArrayList<Player> itemPlayerSelected) {
        this.dataSelected.clear();
//        for (int i = 0; i < getData().size(); i++) {
//            for (int j = 0; j < itemPlayerSelected.size(); j++) {
//                if (((Player) getData().get(i)).getPkPlayerId() == ((Player) itemPlayerSelected.get(j)).getPkPlayerId()) {
//                    ((Player) getData().get(i)).setSelected(true);
//                    break;
//                }
//            }
//        }
        this.dataSelected.addAll(itemPlayerSelected);
        notifyDataSetChanged();
    }

    public Player getDataSelectedPlayer() {
        return this.selectedPlayer;
    }

    public void unSelectAllPlayer() {
        this.dataSelected.clear();
//        for (int i = 0; i < getData().size(); i++) {
//            ((Player) getData().get(i)).setSelected(false);
//        }
        notifyDataSetChanged();
    }

    public ArrayList<Player> getDataSelected() {
        return this.dataSelected;
    }
}
