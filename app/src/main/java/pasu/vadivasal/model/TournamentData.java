package pasu.vadivasal.model;

/**
 * Created by developer on 18/9/17.
 */

public class TournamentData  {
    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPlayerwon() {
        return playerwon;
    }

    public void setPlayerwon(int playerwon) {
        this.playerwon = playerwon;
    }

    public int getBullwon() {
        return bullwon;
    }

    public void setBullwon(int bullwon) {
        this.bullwon = bullwon;
    }

    public int getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(int totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public int getTotalBull() {
        return totalBull;
    }

    public void setTotalBull(int totalBull) {
        this.totalBull = totalBull;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    String tournamentName;
    long date;
    int playerwon,bullwon;
    int totalPlayer,totalBull;
    int type;
    String place;
}
