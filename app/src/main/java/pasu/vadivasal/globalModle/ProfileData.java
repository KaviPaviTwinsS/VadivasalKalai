package pasu.vadivasal.globalModle;

import java.util.HashMap;

/**
 * Created by developer on 27/9/17.
 */

public class ProfileData {
    private String name;
    private String dob="";
    private String description="";
    private int type;
    private long phonenumber;
    private String mail;
    private String profileImageUrl;

    public String getGoogleUserID() {
        return googleUserID;
    }

    public void setGoogleUserID(String googleUserID) {
        this.googleUserID = googleUserID;
    }

    private String googleUserID;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


    private HashMap<String, String> followers= new HashMap<String, String>() {
    };
    private String City="";
    private String PlayedTeam="-";
    //private ArrayList<Media> media;

    HashMap<String, Media> media = new HashMap<String, Media>() {
    };
    HashMap<String, String> followings = new HashMap<String, String>() {
    };

    public HashMap<String, Media> getMedia() {
        return media;
    }

    public void setMedia(HashMap<String, Media> media) {
        this.media = media;
    }


    public String getPlayedTeam() {
        return PlayedTeam;
    }

    public void setPlayedTeam(String playedTeam) {
        PlayedTeam = playedTeam;
    }


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public HashMap<String, String> getFollowings() {
        return followings;
    }

    public void setFollowings(HashMap<String, String> followings) {
        this.followings = followings;
    }

    public HashMap<String, String> getFollowers() {
        return followers;
    }

    public void setFollowers(HashMap<String, String> followers) {
        this.followers = followers;
    }
}
