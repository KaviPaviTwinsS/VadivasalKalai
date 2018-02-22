package pasu.vadivasal.globalModle;

/**
 * Created by developer on 20/12/17.
 */

public class Comments_Model {
    public String name;
    public String userID;
    public String comment;
    public long timeStamp;
public String imageUrl;
    public Comments_Model() {
    }

    public Comments_Model(String name, String userID, String comment, long timeStamp,String imageUrl) {
        this.name = name;
        this.userID = userID;
        this.comment = comment;
        this.timeStamp = timeStamp;
        this.imageUrl=imageUrl;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
