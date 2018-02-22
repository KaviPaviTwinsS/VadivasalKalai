package pasu.vadivasal.photographyInsta;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.PostModel;

/**
 * Created by developer on 18/12/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context mContext;
    private List<PostModel> mList;
    public static PostModel model;
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";
    private PostAdapter.OnFeedItemClickListener onFeedItemClickListener;
    private boolean showLoadingView = false;
    private String uName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    private String uEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    public PostAdapter(Context mContext, List<PostModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        setHasStableIds(true);
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_feed, parent, false);
//        v.getLayoutParams().width = (int) (getScreenWidth() / 2.5);
        PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(v);
        setupClickableViews(v, viewHolder);
        return viewHolder;
    }

    private void setupClickableViews(final View view, final ViewHolder viewHolder) {
        viewHolder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onCommentsClick(view, viewHolder.getAdapterPosition());
            }
        });
        viewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onMoreClick(v, viewHolder.getAdapterPosition());
            }
        });
        viewHolder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(viewHolder.getAdapterPosition(), ACTION_LIKE_IMAGE_CLICKED);
                    }
                }, 200);
                likORdislike(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(viewHolder.getAdapterPosition(), ACTION_LIKE_BUTTON_CLICKED);
                    }
                }, 200);
                likORdislike(viewHolder.getAdapterPosition());

            }
        });
        viewHolder.ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onProfileClick(view);
            }
        });
    }

    void likORdislike(int position) {
        int adapterPosition = position;
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("MyPosts/" + mList.get(adapterPosition).postId);
        dRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                PostModel p = mutableData.getValue(PostModel.class);
                HashMap<String, Object> result = new HashMap<>();
                result.put("uId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                result.put("name", uName);
                result.put("email", uEmail);

                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    // Unstar the post and remove self from stars
                    p.likesCount = p.likesCount - 1;
                    p.stars.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    p.likesCount = p.likesCount + 1;
                    p.stars.put(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,mContext), result);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("Adapter", "postTransaction:onComplete:" + databaseError);
            }
        });

//        if (mContext instanceof MainActivity) {
//            if (model.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                InstaMaterial.showLikedSnackbar(false);
//            } else {
//                InstaMaterial.showLikedSnackbar(true);
//            }
//
//        }
    }

    @Override
    public void onBindViewHolder(final PostAdapter.ViewHolder holder, final int position) {
       model = mList.get(position);
        Picasso.with(mContext)
                .load(model.url)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_avatar)
                .into(holder.ivFeedCenter, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.ivFeedCenter.animate()
                                .scaleX(1.f).scaleY(1.f)
                                .setInterpolator(new OvershootInterpolator())
                                .setDuration(400)
                                .setStartDelay(100)
                                .start();
                    }

                    @Override
                    public void onError() {
                    }
                });
             holder.ivFeedDescription.setText(model.description);
//            ivFeedCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_feed_center_1 : R.drawable.img_feed_center_2);
//        holder.ivFeedBottom.setImageResource(position % 2 == 0 ? R.drawable.img_feed_bottom_1 : R.drawable.img_feed_bottom_2);

        if (model.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.btnLike.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
        }

//            btnLike.setImageResource(model.stars.containsValue(FirebaseAuth.getInstance().getCurrentUser().getUid()) ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
        holder. tsLikesCounter.setCurrentText(holder.vImageRoot.getResources().getQuantityString(
                R.plurals.likes_count, model.likesCount, model.likesCount
        ));

    }
    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position);

        //        void onStarClick(View v)
        void onMoreClick(View v, int position);

        void onProfileClick(View v);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFeedCenter)
        public ImageView ivFeedCenter;
        @BindView(R.id.tvFeedBottom)
        public TextView ivFeedDescription;
        @BindView(R.id.btnComments)
        public ImageButton btnComments;
        @BindView(R.id.btnLike)
        public
        ImageButton btnLike;
        @BindView(R.id.btnMore)
        public ImageButton btnMore;
        @BindView(R.id.vBgLike)
        public View vBgLike;
        @BindView(R.id.ivLike)
        public ImageView ivLike;
        @BindView(R.id.tsLikesCounter)
        public TextSwitcher tsLikesCounter;
        @BindView(R.id.ivUserProfile)
        public ImageView ivUserProfile;
        @BindView(R.id.vImageRoot)
        public FrameLayout vImageRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public PostModel getFeedItem() {
            return model;
        }
    }
}



