package pasu.vadivasal.globalModle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by developer on 15/9/17.
 */

public class Media {
    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    String mediaUrl;
    int likes;
    int type;
    String id;
    String ownerID;

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    HashMap<String, String> likedBy = new HashMap<String, String>() {
    };

    public HashMap<String, String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(HashMap<String, String> media) {
        this.likedBy = media;
    }
}
