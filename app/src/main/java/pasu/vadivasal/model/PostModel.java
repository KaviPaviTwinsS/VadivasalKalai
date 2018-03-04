package pasu.vadivasal.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by developer on 18/12/17.
 */

public class PostModel {
    public String uId;
    public String description;
    public String url;
    public int likesCount = 0;
    public int commentsCount = 0;
    public String postId;
    public int typeOfPost;
    public String videoThumbnail;
    public String ownerName="";
    public String ownerProfileUrl;
    @Exclude
    public int localLike = -1;
    public String thumbnail;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long time;
    //    public Map<String, Boolean> stars = new HashMap<>();
    public Map<String, Map<String,Object>> stars = new HashMap<>();
    public PostModel() {
    }
    public PostModel(String uId, String description, String url, String postId, long time, int typeOfPost, String videoThumbnail,String ownerName,String ownerProfileUrl) {
        this.uId = uId;
        this.description = description;
        this.url = url;
        this.postId = postId;
        this.time = time;
        this.typeOfPost = typeOfPost;
        this.videoThumbnail = videoThumbnail;
        this.ownerName = ownerName;
        this.ownerProfileUrl = ownerProfileUrl;
    }
//    public PostModel(String uId, String description, String url, int likesCount, String postId) {
//        this.uId = uId;
//        this.description = description;
//        this.url= url;
//        this.likesCount = likesCount;
//        this.postId = postId;
//    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uId", uId);
        result.put("description", description);
        result.put("url", url);
        result.put("likesCount", likesCount);
        result.put("postId", postId);
        result.put("stars", stars);

        return result;
    }


}

