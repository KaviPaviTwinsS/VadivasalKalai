package pasu.vadivasal.model;

import java.util.ArrayList;

/**
 * Created by developer on 18/9/17.
 */

public class DashBoardData {
    public String getThirukural() {
        return thirukural;
    }

    public void setThirukural(String thirukural) {
        this.thirukural = thirukural;
    }

    public String getThirukuralNumber() {
        return thirukuralNumber;
    }

    public void setThirukuralNumber(String thirukuralNumber) {
        this.thirukuralNumber = thirukuralNumber;
    }

    public ArrayList<PlayerDash> getBull() {
        return bull;
    }

    public void setBull(ArrayList<PlayerDash> bull) {
        this.bull = bull;
    }

    public ArrayList<PlayerDash> getPlayer() {
        return player;
    }

    public void setPlayer(ArrayList<PlayerDash> player) {
        this.player = player;
    }

    public ArrayList<TournamentData> getTournamentDatas() {
        return tournamentDatas;
    }

    public void setTournamentDatas(ArrayList<TournamentData> tournamentDatas) {
        this.tournamentDatas = tournamentDatas;
    }

    String thirukural;
    String thirukuralNumber;
    ArrayList<PlayerDash> bull;
    ArrayList<PlayerDash> player;
    ArrayList<TournamentData> tournamentDatas;

    public ArrayList<Video> getLatestVideos() {
        return latestVideos;
    }

    public void setLatestVideos(ArrayList<Video> latestVideos) {
        this.latestVideos = latestVideos;
    }

    ArrayList<Video> latestVideos;
}
