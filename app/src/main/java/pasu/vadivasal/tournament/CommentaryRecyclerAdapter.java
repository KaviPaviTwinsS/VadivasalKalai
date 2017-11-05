package pasu.vadivasal.tournament;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pasu.vadivasal.R;
import pasu.vadivasal.videopackage.VideoAdapter;
import pasu.vadivasal.view.TextView;

/**
 * Created by Admin on 05-11-2017.
 */

 class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView commentary_time, commentary_text, genre;
   // SimpleExoPlayerView media_video;
    ImageView media_image;

    public MyViewHolder(View view) {
        super(view);
        commentary_time = (TextView) view.findViewById(R.id.commentary_time);
        commentary_text = (TextView) view.findViewById(R.id.commentary_text);
      //  media_video = (SimpleExoPlayerView) view.findViewById(R.id.media_video);
        media_image = (ImageView) view.findViewById(R.id.media_image);

    }
}



public class CommentaryRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<CommentaryData> data;
    Context c;
     CommentaryRecyclerAdapter (ArrayList<CommentaryData> data, Context c){
         this.data=data;
         this.c=c;
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.commentay_text, parent, false);

            return new MyViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.commentary_video, parent, false);

            return new MyViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return  data.get(position).getMedia()== null? 0:1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         CommentaryData commentaryData=data.get(position);
holder.commentary_text.setText(data.get(position).getComment());
        holder.commentary_time.setText(data.get(position).getTime());
        if(commentaryData.getMedia()!=null){
if(commentaryData.getMedia().getType()==0){
    holder.media_image.setVisibility(View.VISIBLE);
    //holder.media_video.setVisibility(View.GONE);
    Picasso.with(c).load(commentaryData.getMedia().getMediaUrl()).into(holder.media_image);

}else{
    holder.media_image.setVisibility(View.VISIBLE);
    //holder.media_video.setVisibility(View.GONE);
    Picasso.with(c).load(commentaryData.getMedia().getMediaUrl()).into(holder.media_image);
}
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
