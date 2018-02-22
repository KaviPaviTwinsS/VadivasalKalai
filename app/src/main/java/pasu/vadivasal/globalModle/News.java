package pasu.vadivasal.globalModle;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.share.internal.ShareConstants;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Admin on 25-12-2017.
 */

public class News implements Parcelable {
    public static final Creator<News> CREATOR = new C10971();
    private long date;
    private String description;
    private String formattedDateDashboard;
    private String mediaUrl;
    private int newsId;
    private String newsUrl;
    private String title="";

    public String getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    private String tournament_id;
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    private String mediaType="";

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    private ArrayList<Media> media;

    static class C10971 implements Creator<News> {
        C10971() {
        }

        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    }

    public News(JSONObject object) {
        this.newsId = object.optInt("news_id");
        this.newsUrl = object.optString("news_link");
        this.mediaUrl = object.optString(Appconstants.WEB_DIALOG_PARAM_MEDIA);
        this.title = object.optString("news_title");
        this.description = object.optString("description");
        this.date = object.optLong("news_date");
        this.tournament_id = object.optString("tournament_id");
    }

    protected News(Parcel in) {
        this.newsId = in.readInt();
        this.newsUrl = in.readString();
        this.mediaUrl = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.media=in.readArrayList(Media.class.getClassLoader());
    this.tournament_id=in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.newsId);
        dest.writeString(this.newsUrl);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeList(this.media);
        dest.writeString(this.tournament_id);
    }

    public int describeContents() {
        return 0;
    }

    public int getNewsId() {
        return this.newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsUrl() {
        return this.newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
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

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public News() {
    }

    public String getFormattedDateForDashboard() {
        if (this.formattedDateDashboard != null) {
            return this.formattedDateDashboard;
        }
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(this.date);
    }
}