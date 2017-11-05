package pasu.vadivasal.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.globalModle.Media;

/**
 * Created by Admin on 17-09-2017.
 */
public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {
    private final Context context;
    private List<Media> values;
    private DatabaseReference myRef;
    private ValueEventListener valueEventListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image_share,image_delete;
        public ImageView media_viewer, image_like;
//        public View layout;

        public ViewHolder(View v) {
            super(v);
            //  layout = v;
            media_viewer = v.findViewById(R.id.media_viewer);
            image_share = (ImageView) v.findViewById(R.id.image_share);
            image_like = v.findViewById(R.id.image_like);
            image_delete= v.findViewById(R.id.image_delete);
            image_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("shareClicked");
                    following(values.get(getAdapterPosition()), (ImageView) view);
                }
            });
            image_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final Media media = values.get(getAdapterPosition());
                    myRef = database.getReference("profile/" + media.getOwnerID() + "/media/" + media.getId());
                    myRef.removeValue();
                }
            });
//            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Media item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ImageGridAdapter(Context context, List<Media> myDataset) {
        this.context = context;
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImageGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.simple_image_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Media name = values.get(position);
        System.out.println("mediaUrl"+name.getMediaUrl());
        Picasso.with(context).load(name.getMediaUrl()).into(holder.media_viewer);
//        holder.txtHeader.setText(name);
//        holder.txtHeader.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(position);
//            }
//        });
//
//        holder.txtFooter.setText("Footer: " + name);
    }


    private void following(Media media, final ImageView view) {

        //https://ilovecricket-5c636.firebaseio.com/profile/vPEkOsCuMnQA8dQQSsytIhEEScj2/media/-KwESjwB7lMUZe-jcHEU
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("profile/" + media.getOwnerID() + "/media/" + media.getId()+"/likedby");
        myRef.push().setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  progress_bar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    //   ProfileData datav = dataSnapshot.getValue(ProfileData.class);
                    System.out.println("came..");
                    view.setImageResource(R.drawable.ic_favorite_red_24dp);
                } else {
                    System.out.println("databaseerrorss");
//                        no_data_lay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("databaseerror" + databaseError.getMessage());
//                progress_bar.setVisibility(View.GONE);
//                if (datas == null || datas.size() == 0)
//                    no_data_lay.setVisibility(View.VISIBLE);
//                else
//                    no_data_lay.setVisibility(View.GONE);
            }
        };
        myRef.addListenerForSingleValueEvent(valueEventListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}