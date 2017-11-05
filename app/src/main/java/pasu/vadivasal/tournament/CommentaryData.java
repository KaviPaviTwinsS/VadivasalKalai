package pasu.vadivasal.tournament;

import pasu.vadivasal.globalModle.Media;

/**
 * Created by Admin on 05-11-2017.
 */

 class CommentaryData {
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    Media media;
    String comment,time;
}
