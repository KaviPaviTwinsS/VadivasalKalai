package pasu.vadivasal.model;

/**
 * Created by developer on 28/11/17.
 */

public class Upload {
    public String uId;
    public String description;
    public String url;
    public int likesCount = 0;
    public String postId;
    public long time;
    public int typeOfPost;
    public String videoThumbnail;
    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String uId, String description, String url, String postId, long time, int typeOfPost, String videoThumbnail) {
        this.uId = uId;
        this.description = description;
        this.url = url;
        this.postId = postId;
        this.time = time;
        this.typeOfPost = typeOfPost;
        this.videoThumbnail = videoThumbnail;
    }
}
