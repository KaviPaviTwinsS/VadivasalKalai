package pasu.vadivasal.dashboard;
import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.TournamentData;
import pasu.vadivasal.tournament.TournamentMatchesActivity;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.view.View;


import java.util.List;



class DashboardMatchAdapter extends BaseQuickAdapter<TournamentData, BaseViewHolder> {
    String divider;
    int width;
    private Context context;


    DashboardMatchAdapter(Context context, List<TournamentData> data) {
        super(R.layout.raw_dashboard_match_item, data);
        this.divider = "";
        this.context=context;
        this.divider = "<font color='#444444'>&#160&#160 | &#160&#160</font>";
        this.width = (context.getResources().getDisplayMetrics().widthPixels * 97) / 100;
    }

    protected void convert(BaseViewHolder helper, final TournamentData match) {
        CharSequence matchSummary;
        CardView cardView = (CardView) helper.getView(R.id.cvMatchStatus);
        ((CardView) helper.getView(R.id.main_card)).getLayoutParams().width = this.width;
        helper.getView(R.id.main_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, TournamentMatchesActivity.class);
                i.putExtra(Appconstants.TourID,match.getKey());
                context.startActivity(i);
            }
        });
        String roundName = "";
//        if (!Utils.isEmptyString(match.getTournamentRoundName())) {
//            roundName = match.getTournamentRoundName();
//        }
//        if (Utils.isEmptyString(match.getMatchSummary())) {
//            matchSummary = "TournamentData summary will be here";
//        } else {
//            matchSummary = match.getMatchSummary() + (Utils.isEmptyString(match.getMatchEvent()) ? "" : " (" + match.getMatchEvent() + ")");
//        }
//        String matchInfo = match.getCityName() + this.divider + Utils.changeDateformate(match.getMatchStartTime(), "yyyy-MM-dd'T'HH:mm:ss", "dd-MMM-yy");
//        if (Utils.isEmptyString(roundName)) {
//            helper.setVisible(R.id.tvRoundName, true);
//            helper.setText((int) R.id.tvRoundName, (CharSequence) "");
//        } else {
//            helper.setVisible(R.id.tvRoundName, true);
//            helper.setText((int) R.id.tvRoundName, Html.fromHtml(roundName));
//        }
//        switch (match.getType()) {
//            case 1:
//                if (Utils.isEmptyString(match.getName())) {
//                    helper.setText((int) R.id.tvTournamentTitle, (CharSequence) "Individual TournamentData");
//                } else {
//                    helper.setText((int) R.id.tvTournamentTitle, match.getName());
//                }
//                if (Utils.isEmptyString(match.getMatchEventType()) || !(match.getMatchEventType().equalsIgnoreCase("STUMPS") || match.getMatchEventType().equalsIgnoreCase("HOLD"))) {
//                    helper.setText((int) R.id.tvMatchStatus, (CharSequence) "LIVE");
//                    cardView.setCardBackgroundColor(Color.parseColor("#B22110"));
//                } else {
//                    helper.setText((int) R.id.tvMatchStatus, match.getMatchEventType());
//                    cardView.setCardBackgroundColor(Color.parseColor("#72797f"));
//                }
//                helper.setText((int) R.id.tvTeamAName, match.getTeamA());
//                helper.setText((int) R.id.tvTeamBName, match.getTeamB());
//                helper.setText((int) R.id.tvSummary, matchSummary);
//                if (match.getMatchInning() == 1) {
//                    helper.setText((int) R.id.tvMatchInfo, Html.fromHtml(matchInfo + this.divider + match.getOvers() + " Ov."));
//                    helper.setText((int) R.id.tvTeamAScore, match.getTeamASummary());
//                    if (match.getTeamASummary().equalsIgnoreCase("Yet To Bat")) {
//                        helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                    } else if (match.getTeamAInnings() == null || match.getTeamAInnings().size() <= 0) {
//                        helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                    } else {
//                        helper.setText((int) R.id.tvTeamAOvers, ((MatchInning) match.getTeamAInnings().get(0)).getOverSummary());
//                    }
//                    if (match.getTeamAInnings() != null && match.getTeamAInnings().size() > 0) {
//                        if (((MatchInning) match.getTeamAInnings().get(0)).getInning() == match.getCurrentInning()) {
//                            helper.setTextColor(R.id.tvTeamAScore, Color.parseColor("#41AA85"));
//                        } else {
//                            helper.setTextColor(R.id.tvTeamAScore, Color.parseColor("#FFFFFF"));
//                        }
//                    }
//                    helper.setText((int) R.id.tvTeamBScore, match.getTeamBSummary());
//                    if (match.getTeamBSummary().equalsIgnoreCase("Yet To Bat")) {
//                        helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                    } else if (match.getTeamBInnings() == null || match.getTeamBInnings().size() <= 0) {
//                        helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                    } else {
//                        helper.setText((int) R.id.tvTeamBOvers, ((MatchInning) match.getTeamBInnings().get(0)).getOverSummary());
//                    }
//                    if (match.getTeamBInnings() != null && match.getTeamBInnings().size() > 0) {
//                        if (((MatchInning) match.getTeamBInnings().get(0)).getInning() == match.getCurrentInning()) {
//                            helper.setTextColor(R.id.tvTeamBScore, Color.parseColor("#41AA85"));
//                            return;
//                        } else {
//                            helper.setTextColor(R.id.tvTeamBScore, Color.parseColor("#FFFFFF"));
//                            return;
//                        }
//                    }
//                    return;
//                }
//                int i;
//                helper.setText((int) R.id.tvMatchInfo, Html.fromHtml(matchInfo));
//                helper.setText((int) R.id.tvTeamAScore, match.getTeamASummary());
//                helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                helper.setText((int) R.id.tvTeamBScore, match.getTeamBSummary());
//                helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                if (match.getTeamAInnings() != null) {
//                    i = 0;
//                    while (i < match.getTeamAInnings().size()) {
//                        if (((MatchInning) match.getTeamAInnings().get(i)).getInning() == match.getCurrentInning()) {
//                            helper.setText((int) R.id.tvTeamAOvers, ((MatchInning) match.getTeamAInnings().get(i)).getOverSummary());
//                            helper.setText((int) R.id.tvTeamAScore, Utils.makeSpannableText(((TextView) helper.getView(R.id.tvTeamAScore)).getText().toString()));
//                        } else {
//                            helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                            i++;
//                        }
//                    }
//                }
//                if (match.getTeamBInnings() != null) {
//                    for (i = 0; i < match.getTeamBInnings().size(); i++) {
//                        if (((MatchInning) match.getTeamBInnings().get(i)).getInning() == match.getCurrentInning()) {
//                            helper.setText((int) R.id.tvTeamBOvers, ((MatchInning) match.getTeamBInnings().get(i)).getOverSummary());
//                            helper.setText((int) R.id.tvTeamBScore, Utils.makeSpannableText(((TextView) helper.getView(R.id.tvTeamBScore)).getText().toString()));
//                            return;
//                        }
//                        helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                    }
//                    return;
//                }
//                return;
//            case 2:
//                if (Utils.isEmptyString(match.getName())) {
//                    helper.setText((int) R.id.tvTournamentTitle, (CharSequence) "Individual TournamentData");
//                } else {
//                    helper.setText((int) R.id.tvTournamentTitle, match.getName());
//                }
//                helper.setText((int) R.id.tvMatchStatus, (CharSequence) "UPCOMING");
//                cardView.setCardBackgroundColor(Color.parseColor("#14B493"));
//                helper.setText((int) R.id.tvSummary, matchSummary);
//                if (match.getMatchInning() == 1) {
//                    helper.setText((int) R.id.tvMatchInfo, Html.fromHtml(matchInfo + this.divider + match.getOvers() + " Ov."));
//                } else {
//                    helper.setText((int) R.id.tvMatchInfo, Html.fromHtml(matchInfo));
//                }
//                helper.setText((int) R.id.tvTeamAName, match.getTeamA());
//                helper.setText((int) R.id.tvTeamBName, match.getTeamB());
//                helper.setText((int) R.id.tvTeamAScore, (CharSequence) "");
//                helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                helper.setText((int) R.id.tvTeamBScore, (CharSequence) "");
//                helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                helper.setText((int) R.id.tvGround, Html.fromHtml(match.getCityName() + this.divider + String.format(Locale.getDefault(), "%s Ov", new Object[]{Integer.valueOf(match.getOvers())}) + roundName));
//                return;
//            case 3:
//                cardView.setCardBackgroundColor(Color.parseColor("#30393E"));
//                helper.setText((int) R.id.tvMatchStatus, (CharSequence) "RESULT");
//                if (Utils.isEmptyString(match.getName())) {
//                    helper.setText((int) R.id.tvTournamentTitle, (CharSequence) "Individual TournamentData");
//                } else {
//                    helper.setText((int) R.id.tvTournamentTitle, match.getName());
//                }
//                helper.setText((int) R.id.tvTeamAName, match.getTeamA());
//                helper.setText((int) R.id.tvTeamBName, match.getTeamB());
//                helper.setText((int) R.id.tvSummary, matchSummary);
//                helper.setTextColor(R.id.tvTeamAScore, Color.parseColor("#FFFFFF"));
//                helper.setTextColor(R.id.tvTeamBScore, Color.parseColor("#FFFFFF"));
//                if (match.getMatchInning() == 1) {
//                    helper.setText((int) R.id.tvMatchInfo, Html.fromHtml(matchInfo + this.divider + match.getOvers() + " Ov."));
//                    if (match.getTeamAInnings() == null || match.getTeamAInnings().size() <= 0) {
//                        helper.setText((int) R.id.tvTeamAScore, (CharSequence) "");
//                        helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                    } else {
//                        helper.setText((int) R.id.tvTeamAScore, ((MatchInning) match.getTeamAInnings().get(0)).getScoreSummary());
//                        helper.setText((int) R.id.tvTeamAOvers, ((MatchInning) match.getTeamAInnings().get(0)).getOverSummary());
//                    }
//                    if (match.getTeamBInnings() == null || match.getTeamBInnings().size() <= 0) {
//                        helper.setText((int) R.id.tvTeamBScore, (CharSequence) "");
//                        helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                    } else {
//                        helper.setText((int) R.id.tvTeamBScore, ((MatchInning) match.getTeamBInnings().get(0)).getScoreSummary());
//                        helper.setText((int) R.id.tvTeamBOvers, ((MatchInning) match.getTeamBInnings().get(0)).getOverSummary());
//                    }
//                } else {
//                    helper.setText((int) R.id.tvMatchInfo, Html.fromHtml(matchInfo));
//                    if (match.getTeamAInnings() == null || match.getTeamAInnings().size() <= 0) {
//                        helper.setText((int) R.id.tvTeamAScore, (CharSequence) "");
//                        helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                    } else {
//                        helper.setText((int) R.id.tvTeamAScore, match.getTeamASummary());
//                        helper.setText((int) R.id.tvTeamAOvers, (CharSequence) "");
//                    }
//                    if (match.getTeamBInnings() == null || match.getTeamBInnings().size() <= 0) {
//                        helper.setText((int) R.id.tvTeamBScore, (CharSequence) "");
//                        helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                    } else {
//                        helper.setText((int) R.id.tvTeamBScore, match.getTeamBSummary());
//                        helper.setText((int) R.id.tvTeamBOvers, (CharSequence) "");
//                    }
//                }
//                if (match.getWinningTeam().equalsIgnoreCase(match.getTeamA())) {
//                    helper.setTextColor(R.id.tvTeamAName, Color.parseColor("#41AA85"));
//                    Utils.setTypeFace(this.mContext, (TextView) helper.getView(R.id.tvTeamAName), this.mContext.getString(R.string.font_sourcesans_pro_semibold));
//                    helper.setTextColor(R.id.tvTeamBName, Color.parseColor("#FFFFFF"));
//                    Utils.setTypeFace(this.mContext, (TextView) helper.getView(R.id.tvTeamBName), this.mContext.getString(R.string.font_sourcesans_pro_regular));
//                    return;
//                } else if (match.getWinningTeam().equalsIgnoreCase(match.getTeamB())) {
//                    helper.setTextColor(R.id.tvTeamBName, Color.parseColor("#41AA85"));
//                    Utils.setTypeFace(this.mContext, (TextView) helper.getView(R.id.tvTeamBName), this.mContext.getString(R.string.font_sourcesans_pro_semibold));
//                    helper.setTextColor(R.id.tvTeamAName, Color.parseColor("#FFFFFF"));
//                    Utils.setTypeFace(this.mContext, (TextView) helper.getView(R.id.tvTeamAName), this.mContext.getString(R.string.font_sourcesans_pro_regular));
//                    return;
//                } else {
//                    helper.setTextColor(R.id.tvTeamAName, Color.parseColor("#FFFFFF"));
//                    helper.setTextColor(R.id.tvTeamBName, Color.parseColor("#FFFFFF"));
//                    Utils.setTypeFace(this.mContext, (TextView) helper.getView(R.id.tvTeamBName), this.mContext.getString(R.string.font_sourcesans_pro_regular));
//                    Utils.setTypeFace(this.mContext, (TextView) helper.getView(R.id.tvTeamAName), this.mContext.getString(R.string.font_sourcesans_pro_regular));
//                    return;
//                }
//            default:
//                helper.setVisible(R.id.txt_startTime, false);
//                helper.setVisible(R.id.cvMatchStatus, true);
//                helper.setText((int) R.id.tvMatchStatus, match.getStatus());
//                return;
//        }
    }
}
