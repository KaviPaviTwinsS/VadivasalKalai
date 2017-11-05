package pasu.vadivasal.tournament;

import pasu.vadivasal.globalModle.Media;

/**
 * Created by Admin on 05-11-2017.
 */

 class CommentaryMedia extends CommentaryData{
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    Media media;
}
