package pasu.vadivasal.model;

import java.util.ArrayList;

import pasu.vadivasal.globalModle.Media;

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

    public String getVersionAlertTitle() {
        return versionAlertTitle;
    }

    public void setVersionAlertTitle(String versionAlertTitle) {
        this.versionAlertTitle = versionAlertTitle;
    }

    String versionAlertTitle;
    public int getLatestVerison() {
        return latestVerison;
    }

    public void setLatestVerison(int latestVerison) {
        this.latestVerison = latestVerison;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public String getAbout_app() {
        return about_app;
    }

    public void setAbout_app(String about_app) {
        this.about_app = about_app;
    }

    String about_app;

    public String getShare_app() {
        return share_app;
    }

    public void setShare_app(String share_app) {
        this.share_app = share_app;
    }

    String share_app;
    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getVersionAlert() {
        return versionAlert;
    }

    public void setVersionAlert(String versionAlert) {
        this.versionAlert = versionAlert;
    }

    int latestVerison;
    boolean forceUpdate;
    String versionAlert;

    public ArrayList<Media> getPhotos() {
      Media media=new Media();
      media.setMediaUrl("https://firebasestorage.googleapis.com/v0/b/jallikattu-1a841.appspot.com/o/IMG_3590%5B1%5D.JPG?alt=media&token=60a6691c-4d39-4f5d-955f-29859a36139b");
        photos.add(media);
        Media media1=new Media();
        media1.setMediaUrl("https://firebasestorage.googleapis.com/v0/b/jallikattu-1a841.appspot.com/o/IMG_3593%5B1%5D.JPG?alt=media&token=ad7c8268-ceb0-42a5-bdfe-0fca8fd4279b");
        photos.add(media1);
        Media media2=new Media();
        media2.setMediaUrl("https://firebasestorage.googleapis.com/v0/b/jallikattu-1a841.appspot.com/o/IMG_3594%5B1%5D.JPG?alt=media&token=d31b99c6-3c62-4ecc-9811-23b7c113dd0a");
        photos.add(media2);
        return photos;
    }

    public void setPhotos(ArrayList<Media> photos) {
        this.photos = photos;
    }

    ArrayList<Media> photos=new ArrayList<>();
    public ArrayList<Media> getLatestVideos() {
        return latestVideos;
    }

    public void setLatestVideos(ArrayList<Media> latestVideos) {
        this.latestVideos = latestVideos;
    }

    ArrayList<Media> latestVideos;
}
