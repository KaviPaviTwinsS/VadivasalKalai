package pasu.vadivasal.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import pasu.vadivasal.R;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.ProfileData;


public class PlayerInfoFragment extends Fragment {
//    @BindView(2131756249)
//    CardView cardCatch;
//    @BindView(2131756256)
//    LinearLayout layoutBattingStyle;
//    @BindView(2131756258)
//    LinearLayout layoutBowlingStyle;
//    @BindView(2131756254)
//    LinearLayout layoutPlayingRole;
//    @BindView(2131756244)
//    LinearLayout lnrTopMatch;
//    @BindView(2131756243)
//    LinearLayout mainLinear;
//    PlayerData playerData;
//    @BindView(2131756257)
//    TextView tvBattingStyle;
//    @BindView(2131756259)
//    TextView tvBowlingStyle;
//    @BindView(2131756250)
//    TextView tvCatches;
//    @BindView(2131755240)
//    TextView tvDOB;
//    @BindView(2131756253)
//    TextView tvEmail;
//    @BindView(2131756251)
//    TextView tvLocation;
//    @BindView(2131756245)
//    TextView tvMatches;
//    @BindView(2131756255)
//    TextView tvPlayingRole;
//    @BindView(2131756072)
//    TextView tvRun;
//    @BindView(2131756246)
//    TextView tvRunTitle;
//    @BindView(2131756252)
//    TextView tvTitleDOBTitle;
//    @BindView(2131756247)
//    TextView tvWicket;
//    @BindView(2131756248)
//    TextView tvWicketTitle;

    TextView tvLocation, tvDOB, role_txt, tvBio;
ViewGroup main_linear;
ProgressBar progressBar;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.raw_player_profile_info, container, false);
        //  ButterKnife.bind((Object) this, rootView);
        tvLocation = rootView.findViewById(R.id.tvLocation);
        progressBar=rootView.findViewById(R.id.progressBar);
        tvDOB = rootView.findViewById(R.id.tvDOB);
        role_txt = rootView.findViewById(R.id.role_txt);
        tvBio = rootView.findViewById(R.id.tvBio);
        main_linear=rootView.findViewById(R.id.main_linear_card);
        return rootView;
    }

    public void setData(ProfileData profileData) {
        main_linear.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvLocation.setText(profileData.getCity());
        tvDOB.setText(profileData.getDob());
//        role_txt.setText(profileData.g());
        if (profileData.getType() == Appconstants.TYPE_BULL_OWNER)
            role_txt.setText(getString(R.string.register_bull_owner));
        else if (profileData.getType() == Appconstants.TYPE_PLAYER)
            role_txt.setText(getString(R.string.register_tamper));
        else if (profileData.getType() == Appconstants.TYPE_PHOTOGRAPHER)
            role_txt.setText(getString(R.string.register_photographer));
        tvBio.setText(profileData.getDescription());

    }

//    public void setData(PlayerData data) {
//        if (this.playerData == null) {
//            this.playerData = data;
//            if (this.playerData != null) {
//                this.mainLinear.setVisibility(View.VISIBLE);
//                this.lnrTopMatch.setVisibility(View.GONE);
//                this.tvMatches.setText(this.playerData.getMatches());
//                this.tvRun.setText(this.playerData.getRuns());
//                this.tvWicket.setText(this.playerData.getWickets());
//                this.tvCatches.setText(this.playerData.getCatches());
//                this.tvLocation.setText(this.playerData.getCityName());
//                if (Utils.isEmptyString(this.playerData.getDob())) {
//                    this.tvDOB.setText("--");
//                } else {
//                    this.tvDOB.setText(this.playerData.getDob());
//                }
//                if (Utils.isEmptyString(this.playerData.getBowlingStyle())) {
//                    this.tvBowlingStyle.setText("--");
//                } else {
//                    this.tvBowlingStyle.setText(this.playerData.getBowlingStyle());
//                }
//                if (Utils.isEmptyString(this.playerData.getBattingHand())) {
//                    this.tvBattingStyle.setText("--");
//                } else {
//                    this.tvBattingStyle.setText(this.playerData.getBattingHand());
//                }
//                if (Utils.isEmptyString(this.playerData.getPlayingRole())) {
//                    this.tvPlayingRole.setText("--");
//                } else {
//                    this.tvPlayingRole.setText(this.playerData.getPlayingRole());
//                }
//                CricHeroes.getApp();
//                for (int i = 0; i < CricHeroes.database.getCities().size(); i++) {
//                }
//            }
//        }
//    }

//    public void setTeamData(TeamData teamData) {
//        if (teamData != null) {
//            this.mainLinear.setVisibility(View.VISIBLE);
//            this.cardCatch.setVisibility(View.GONE);
//            this.layoutBowlingStyle.setVisibility(View.GONE);
//            this.layoutBattingStyle.setVisibility(View.GONE);
//            this.layoutPlayingRole.setVisibility(View.GONE);
//            this.tvTitleDOBTitle.setText("SINCE");
//            this.tvDOB.setText(Utils.changeDateformate(teamData.getDate(), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM, yyyy"));
//            this.tvLocation.setText(teamData.getCity());
//        }
//    }
}
