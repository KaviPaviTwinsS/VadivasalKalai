package pasu.vadivasal.globalModle;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by developer on 15/9/17.
 */

public class Media implements Parcelable {
    public static final Creator<Media> CREATOR = new C10951();
    private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private int isPhoto;
    private boolean isSponsor;
    private String orientation;
    private String title;
    private String tournamentName;
    private String uploadedBy;
    private String uploadedDate;
    String mediaUrl;
    int likes;
    int type;
    String id;
    String ownerID;
    long date;
    String description;
    String thumbnail = "";
    int position;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }




    public Media() {

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


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


    static class C10951 implements Creator<Media> {
        C10951() {
        }

        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        public Media[] newArray(int size) {
            return new Media[size];
        }
    }

    public Media(String mediaId, int mediaType, String mediaUrl, String title, String description, String orientation) {
        this.id = mediaId;
        this.type = mediaType;
        this.mediaUrl = mediaUrl;
        this.title = title;
        this.description = description;
        this.orientation = orientation;
    }

    public Media(JSONObject jsonObject) {
        setMediaId(jsonObject.optString("media_id"));
        setMediaUrl(jsonObject.optString(Appconstants.WEB_DIALOG_PARAM_MEDIA));
        setMediaType(jsonObject.optInt("media_type"));
        setOrientation(jsonObject.optString("orientation"));
        setUploadedBy(jsonObject.optString("uploaded_by"));
        setUploadedDate(jsonObject.optString("uploaded_date"));
        setIsPhoto(jsonObject.optInt("is_photo", 1));
//        if (getIsPhoto() == 0) {
//            setVideoUrl(jsonObject.optString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO));
//        }
    }

    protected Media(Parcel in) {
        this.id = in.readString();
        this.type = in.readInt();
        this.mediaUrl = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.orientation = in.readString();
        this.uploadedBy = in.readString();
        this.uploadedDate = in.readString();
        this.isPhoto = in.readInt();
    }

    public static Media fromJson(JsonObject json) {
        return (Media) gson.fromJson((JsonElement) json, Media.class);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.orientation);
        dest.writeString(this.uploadedBy);
        dest.writeString(this.uploadedDate);
        dest.writeInt(this.isPhoto);
    }

    public int describeContents() {
        return 0;
    }


    public String getUploadedBy() {
        return this.uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadedDate() {
        return this.uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getMediaId() {
        return this.id;
    }

    public void setMediaId(String mediaId) {
        this.id = mediaId;
    }

    public int getMediaType() {
        return this.type;
    }

    public void setMediaType(int mediaType) {
        this.type = mediaType;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public boolean isPortrait() {
        return "portrait".equalsIgnoreCase(this.orientation);
    }


    public int getIsPhoto() {
        return this.isPhoto;
    }

    public void setIsPhoto(int isPhoto) {
        this.isPhoto = isPhoto;
    }

    public void setGiftDate(JSONObject jsonObj) {
        setMediaUrl("http://cricheroes.in/" + jsonObj.optString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PHOTO));
        setIsPhoto(1);
    }

    public boolean isSponsor() {
        return this.isSponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.isSponsor = sponsor;
    }

    public String getTournamentName() {
        return this.tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
}