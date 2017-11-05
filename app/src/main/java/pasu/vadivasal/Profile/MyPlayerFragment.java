package pasu.vadivasal.Profile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;

import java.util.ArrayList;

import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.android.logger.Logger;
import pasu.vadivasal.globalModle.Player;
import pasu.vadivasal.view.Button;


public class MyPlayerFragment extends Fragment implements OnRefreshListener, RequestLoadMoreListener {
    public static final String PREFERENCES_NAME = "pref";
    static Player mplayer;
    static ArrayList<Player> playerDataSet = new ArrayList();
    Button btnAddPlayer;
    Button btnPlayerAdd;
    boolean callerActivity;
    private String ids = "";
    private boolean isFirstRun = true;
    LinearLayout layoutNoInternet;
    LinearLayout layoutNoPlayer;
    RecyclerView mRecyclerView;
    private String mTeamName;
    private PlayerAdapter playerAdapter;
  
    SwipeRefreshLayout swipeLayout;

//    class C09641 implements OnItemClickListener {
//        C09641() {
//        }
//
//        public void onItemClick(View view, int position, boolean selected) {
//        }
//    }

    class C09652 extends pasu.vadivasal.adapter.base.listener.OnItemClickListener {
        C09652() {
        }

        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            MyPlayerFragment.this.playerAdapter.onPlayerClick(view, (Player) MyPlayerFragment.this.playerAdapter.getItem(position), position);
            if (MyPlayerFragment.this.playerAdapter.getDataSelected().size() > 0) {
                MyPlayerFragment.this.addButtonVisibility(true);
            } else {
                MyPlayerFragment.this.addButtonVisibility(false);
            }
        }
    }

    class C09663 implements OnClickListener {
        C09663() {
        }

        public void onClick(View v) {
            MyPlayerFragment.this.playerAdapter.onPlayerSelected(MyPlayerFragment.this.getActivity());
        }
    }

    class C09674 implements OnClickListener {
        C09674() {
        }

        public void onClick(View v) {
            ((TabLayout) MyPlayerFragment.this.getActivity().findViewById(R.id.tabLayoutPlayer)).getTabAt(0).select();
        }
    }

    class C09685 implements DialogInterface.OnClickListener {
        C09685() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -2:
                    MyPlayerFragment.this.getActivity().finish();
                    return;
                case -1:
                    ((TabLayout) MyPlayerFragment.this.getActivity().findViewById(R.id.tabLayoutPlayer)).getTabAt(0).select();
                    return;
                default:
                    return;
            }
        }
    }

    class C09707 implements Runnable {
        C09707() {
        }

        public void run() {
            if (MyPlayerFragment.this.playerAdapter != null) {
                MyPlayerFragment.this.playerAdapter.loadMoreEnd();
            }
        }
    }

    class GetDataFromDbAsync extends AsyncTask<Void, Void, Void> {
        Dialog dialog;

        GetDataFromDbAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = Utils.showProgress(MyPlayerFragment.this.getActivity(), true);
        }

        protected Void doInBackground(Void... voids) {
            MyPlayerFragment.this.getMyPlayersFromDB(MyPlayerFragment.this.ids);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Utils.hideProgress(this.dialog);
            MyPlayerFragment.this.updateUI();
            if (MyPlayerFragment.this.playerAdapter != null) {
                MyPlayerFragment.this.playerAdapter.notifyDataSetChanged();
                MyPlayerFragment.this.loadMoreComplete();
            }
            if (MyPlayerFragment.this.swipeLayout.isRefreshing()) {
                MyPlayerFragment.this.swipeLayout.setRefreshing(false);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_player, container, false);
//        ButterKnife.bind((Object) this, rootView);
//        this.mTeam = (Team) getActivity().getIntent().getParcelableExtra(AppConstants.EXTRA_TEAM_NAME);
//        this.callerActivity = getActivity().getIntent().getBooleanExtra(AppConstants.EXTRA_FROM_START_MATCH, false);
//        this.preferenceUtil = PreferenceUtil.getInstance(getActivity(), PREFERENCES_NAME);
//        this.isFirstRun = this.preferenceUtil.getBoolean(getActivity().getString(R.string.pref_key_is_show_alert_message), true);
        playerDataSet.clear();
        return rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPageControl();
    }

    private void initPageControl() {
        this.swipeLayout.setOnRefreshListener(this);
        this.swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.green_text, R.color.orange_dark, R.color.blue);
        this.layoutNoInternet.setVisibility(View.GONE);
        this.layoutNoPlayer.setVisibility(View.GONE);
      //  this.mTeamName = this.mTeam.getName();
        getActivity().setTitle(String.format("Add Players to %s", new Object[]{this.mTeamName}));
        this.ids = getActivity().getIntent().getStringExtra(AppConstants.EXTRA_PLAYER_IDS);
        new GetDataFromDbAsync().execute(new Void[0]);
        if (this.callerActivity && this.isFirstRun) {
            showMessageDialog();
        }
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.playerAdapter = new PlayerAdapter(R.layout.raw_team_player_grid_activity, playerDataSet, getActivity());
        this.playerAdapter.isShowUnVerified = false;
        this.playerAdapter.showOvers = false;
        this.playerAdapter.isSelectMultiplePlayer = true;
       // this.playerAdapter.SetOnItemClickListener(new C09641());
        this.mRecyclerView.setAdapter(this.playerAdapter);
        this.mRecyclerView.addOnItemTouchListener(new C09652());
//        this.btnAddPlayer.setOnClickListener(new C09663());
//        this.btnPlayerAdd.setOnClickListener(new C09674());
        updateUI();
    }

    private String getPlayerIds() {
        String ids = "";
//        for (int i = 0; i < playerDataSet.size(); i++) {
//            if (i == 0) {
//                ids = ((Player) playerDataSet.get(i)).getPkPlayerId() + "";
//            } else {
//                ids = ids + "," + ((Player) playerDataSet.get(i)).getPkPlayerId();
//            }
//        }
        if (this.ids.equalsIgnoreCase("") || ids.equalsIgnoreCase("")) {
            return !this.ids.equalsIgnoreCase("") ? this.ids : ids;
        } else {
            return this.ids + "," + ids;
        }
    }

    private void getMyPlayersFromDB(String playerIds) {
//        CricHeroes.getApp();
//        ArrayList<Player> players = CricHeroes.database.getPlayersWhereIdNotIn(playerIds);
//        System.out.println("my player size " + players.size());
//        String ids = PreferenceUtil.getInstance(getActivity(), AppConstants.APP_PREF).getString(AppConstants.PREF_MY_PLAYER_IDS);
//        Logger.e("PREF_MY_PLAYER_IDS " + ids);
//        if (!ids.equalsIgnoreCase("")) {
//            String[] myPlayerIds = ids.split(",");
//            Iterator it = players.iterator();
//            while (it.hasNext()) {
//                Player player = (Player) it.next();
//                for (String s : myPlayerIds) {
//                    if (s.equalsIgnoreCase(String.valueOf(player.getPkPlayerId()))) {
//                        playerDataSet.add(player);
//                        break;
//                    }
//                }
//            }
//        }
    }

    private void addButtonVisibility(boolean visibility) {
        if (visibility) {
           // this.btnAddPlayer.setVisibility(View.VISIBLE);
        } else {
            //this.btnAddPlayer.setVisibility(View.GONE);
        }
    }

    public void updateUI() {
        if (playerDataSet.size() == 0) {
            this.layoutNoPlayer.setVisibility(View.VISIBLE);
            this.swipeLayout.setVisibility(View.GONE);
            ((TabLayout) getActivity().findViewById(R.id.tabLayoutPlayer)).getTabAt(0).select();
            return;
        }
        this.layoutNoPlayer.setVisibility(View.GONE);
        this.swipeLayout.setVisibility(View.VISIBLE);
    }

    public void showMessageDialog() {
        DialogInterface.OnClickListener dialogClickListener = new C09685();
        this.isFirstRun = false;
//        this.preferenceUtil.putBoolean(getActivity().getString(R.string.pref_key_is_show_alert_message), false);
//        Utils.showAlert(getActivity(), "", getActivity().getString(R.string.alert_msg_add_player), getActivity().getString(R.string.btn_yes), getActivity().getString(R.string.btn_no), dialogClickListener, true);
    }

    public void checkPlayerAvailability() {
        if (playerDataSet.size() == 0) {
            ((TabLayout) getActivity().findViewById(R.id.tabLayoutPlayer)).getTabAt(0).select();
        }
    }

    public void addNewPlayer(Player player) {
        mplayer = player;
        playerDataSet.add(0, player);
        this.playerAdapter.notifyDataSetChanged();
        updateUI();
    }

    public void onRefresh() {
        System.out.println(" refresh call");
        if (this.layoutNoPlayer.getVisibility() == View.GONE) {
            getMyPlayers(null, null);
        } else {
            ((TabLayout) getActivity().findViewById(R.id.tabLayoutPlayer)).getTabAt(0).select();
        }
    }

    public void onStop() {
       // ApiCallManager.cancelCall("get_my_player");
        super.onStop();
    }

    private void getMyPlayers(Long page, Long dateTime) {
        final Dialog dialog = Utils.showProgress(getActivity(), true);
//        ApiCallManager.enqueue("get_my_player", CricHeroes.apiClient.getMyPlayer(Utils.udid(getActivity()), CricHeroes.getApp().getAccessToken(), page, dateTime), new CallbackAdapter() {
//            public void onApiResponse(ErrorResponse err, BaseResponse response) {
//                Utils.hideProgress(dialog);
//                if (err != null) {
//                    Logger.i("err " + err);
//                    Utils.showToast(MyPlayerFragment.this.getActivity(), err.getMessage(), 1, false);
//                    if (MyPlayerFragment.this.swipeLayout.isRefreshing()) {
//                        MyPlayerFragment.this.swipeLayout.setRefreshing(false);
//                        return;
//                    }
//                    return;
//                }
//                MyPlayerFragment.this.playerResponse = response;
//                JsonArray json = (JsonArray) response.getData();
//                Logger.i("JSON " + json);
//                try {
//                    JSONArray jsonArray = new JSONArray(json.toString());
//                    if (jsonArray.length() > 0) {
//                        ContentValues[] contentValues = new ContentValues[jsonArray.length()];
//                        String ids = PreferenceUtil.getInstance(MyPlayerFragment.this.getActivity(), AppConstants.APP_PREF).getString(AppConstants.PREF_MY_PLAYER_IDS);
//                        Logger.i("ids>>>>  " + ids);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            Player player = new Player(jsonArray.getJSONObject(i), true);
//                            contentValues[i] = player.getContentValue();
//                            if (ids.equalsIgnoreCase("")) {
//                                ids = String.valueOf(player.getPkPlayerId());
//                            } else if (!ids.contains(String.valueOf(player.getPkPlayerId()))) {
//                                ids = ids + "," + String.valueOf(player.getPkPlayerId());
//                            }
//                        }
//                        Logger.i("ids<<<<<  " + ids);
//                        PreferenceUtil.getInstance(MyPlayerFragment.this.getActivity(), AppConstants.APP_PREF).putString(AppConstants.PREF_MY_PLAYER_IDS, ids);
//                        CricHeroes.getApp();
//                        CricHeroes.database.insert(UserMaster.TABLE, contentValues);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                MyPlayerFragment.this.ids = MyPlayerFragment.this.getPlayerIds();
//                new GetDataFromDbAsync().execute(new Void[0]);
//                MyPlayerFragment.this.playerAdapter.setOnLoadMoreListener(MyPlayerFragment.this, MyPlayerFragment.this.mRecyclerView);
//            }
//        });
    }

    public void onLoadMoreRequested() {
        Logger.i("onLoadMoreRequested");
//        if (this.playerResponse == null) {
//            loadMoreComplete();
//        } else if (this.playerResponse.hasPage() && this.playerResponse.getPage().hasNextPage()) {
//            getMyPlayers(Long.valueOf(this.playerResponse.getPage().getNextPage()), Long.valueOf(this.playerResponse.getPage().getDatetime()));
//        } else {
//            loadMoreComplete();
//        }
    }

    private void loadMoreComplete() {
        new Handler().postDelayed(new C09707(), 1500);
    }
}
