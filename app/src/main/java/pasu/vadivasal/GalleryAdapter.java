package pasu.vadivasal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by developer on 21/10/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private int[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    GalleryAdapter(Context context, int[] data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

        System.out.println("data__" + mData);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gallery_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(mData[position])
                .resize(270, 220)
                .into(holder.myImageView);
        holder.myImageView.setImageResource(mData[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = (ImageView) itemView.findViewById(R.id.info_image);
            itemView.setOnClickListener(this);

            myImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ShareAct.class);
                    intent.putExtra("image_data", mData);
                    intent.putExtra("pos", position);
                    context.startActivity(intent);
                }
            });
        }


        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context, ShareAct.class);
//            intent.putExtra(myImageView.)
            Activity activity = (Activity) context;
            activity.startActivity(intent);
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Integer getItem(int id) {
        return mData[id];
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
