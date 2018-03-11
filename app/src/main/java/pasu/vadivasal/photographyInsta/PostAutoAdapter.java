package pasu.vadivasal.photographyInsta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.PhotoViewActivity;
import pasu.vadivasal.Profile.UserProfileActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.VideoFullScreenActivity;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Comments_Model;
import pasu.vadivasal.model.PostModel;
import pasu.vadivasal.regLogin.SignUpActivity;
import pasu.vadivasal.regLogin.SocialLoginCustom;
import pasu.vadivasal.view.CircleImageView;

/**
 * Created by developer on 18/12/17.
 */

//public class PostAutoAdapter extends BaseQuickAdapter<PostModel, BaseViewHolder> {
//    public static final int VIEW_TYPE_DEFAULT = 1;
//    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
//    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";
//    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
//    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
//    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
//    public ImageView ivFeedCenter;
//    public TextView tvFeedDescription;
//    public ImageButton btnComments;
//    public ImageButton btnLike;
//    public ImageButton btnMore;
//    public View vBgLike;
//    public ImageView ivLike;
//    public TextView tvLike;
//    public TextSwitcher tsLikesCounter;
//    public ImageView ivUserProfile;
//    public FrameLayout vImageRoot;
//    public CardView cardView;
//    public View view;
//    boolean doubleClick = false;
//    private Context context;
//    private String uName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
//    private String uEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//    private OnFeedItemClickListener onFeedItemClickListener;
//
//    public PostAutoAdapter(FragmentActivity activity, int item_feed, List<PostModel> mList) {
//        super(item_feed, mList);
//        context = activity;
//    }
//
//    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
//
//        if (tv.getTag() == null) {
//            tv.setTag(tv.getText());
//        }
//        ViewTreeObserver vto = tv.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//
//                ViewTreeObserver obs = tv.getViewTreeObserver();
//                obs.removeGlobalOnLayoutListener(this);
//                if (maxLine == 0) {
//                    int lineEndIndex = tv.getLayout().getLineEnd(0);
//                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
//                    tv.setText(text);
//                    tv.setMovementMethod(LinkMovementMethod.getInstance());
//                    tv.setText(
//                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
//                                    viewMore), TextView.BufferType.SPANNABLE);
//                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
//                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
//                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
//                    tv.setText(text);
//                    tv.setMovementMethod(LinkMovementMethod.getInstance());
//                    tv.setText(
//                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
//                                    viewMore), TextView.BufferType.SPANNABLE);
//                } else {
//                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
//                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
//                    tv.setText(text);
//                    tv.setMovementMethod(LinkMovementMethod.getInstance());
//                    tv.setText(
//                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
//                                    viewMore), TextView.BufferType.SPANNABLE);
//                }
//            }
//        });
//
//    }
//
//    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
//                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
//        String str = strSpanned.toString();
//        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
//
//        if (str.contains(spanableText)) {
//            ssb.setSpan(new ClickableSpan() {
//
//                @Override
//                public void onClick(View widget) {
//
//                    if (viewMore) {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, -1, "View Less", false);
//                    } else {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, 3, "View More", true);
//                    }
//
//                }
//            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
//
//        }
//        return ssb;
//
//    }
//
//    @Override
//    protected void convert(final BaseViewHolder helper, final PostModel modelItem) {
//
//        ivFeedCenter = helper.getView(R.id.ivFeedCenter);
//        tvFeedDescription = helper.getView(R.id.tvFeedBottom);
//        btnComments = helper.getView(R.id.btnComments);
//        btnLike = helper.getView(R.id.btnLike);
//        btnMore = helper.getView(R.id.btnMore);
//        vBgLike = helper.getView(R.id.vBgLike);
//        ivLike = helper.getView(R.id.ivLike);
//        tsLikesCounter = helper.getView(R.id.tsLikesCounter);
//        ivUserProfile = helper.getView(R.id.ivUserProfile);
//        vImageRoot = helper.getView(R.id.vImageRoot);
//        tvLike = helper.getView(R.id.tvLikes);
//        cardView = helper.getView(R.id.card_view);
//
//        if (modelItem.typeOfPost == 1) {
//
//            try {
//                byte [] encodeByte= Base64.decode(modelItem.videoThumbnail, Base64.DEFAULT);
//                Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//                ivFeedCenter.setImageBitmap(bitmap);
//            } catch(Exception e) {
//                e.getMessage();
//                ivFeedCenter.setImageResource(R.drawable.ic_avatar);
//            }
////            Picasso.with(context)
////                    .load(modelItem.videoThumbnail)
////                    .placeholder(R.drawable.loading)
////                    .error(R.drawable.ic_error)
////                    .into(ivFeedCenter);
//        }else {
//            Picasso.with(context)
//                    .load(modelItem.url)
//                    .placeholder(R.drawable.ic_loading)
//                    .error(R.drawable.ic_avatar)
//                    .into(ivFeedCenter);
//        }
//
//        if (modelItem.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid()) || modelItem.localLike == 1) {
//            btnLike.setImageResource(R.drawable.ic_heart_red);
//        } else {
//            btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
//        }
//        if (modelItem.description.length() > 100) {
//            tvFeedDescription.setText(modelItem.description);
//            makeTextViewResizable(tvFeedDescription, 3, "See More", true);
//        } else {
//            tvFeedDescription.setText(modelItem.description);
//        }
//
////        tvFeedDescription.setText(SpannableStringUtils.getBuilder(modelItem.description).append("more").setClickSpan(clickableSpan).create());
////        tvFeedDescription.setMovementMethod(LinkMovementMethod.getInstance());
//        tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
//                R.plurals.likes_count, modelItem.likesCount, modelItem.likesCount
//        ));
//        ivFeedCenter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (modelItem.typeOfPost == 1) {
//                        Toast.makeText(context, "Video", Toast.LENGTH_SHORT).show();
//                    }
//                    Handler handler = new Handler();
//                    Runnable r = new Runnable() {
//                        @Override
//                        public void run() {
//
//                            doubleClick = false;
//                        }
//                    };
//
//                    if (doubleClick) {
//                        if (isOnline()) {
//                            animateHeartphoto(helper);
//                            likORdislike(helper, helper.getAdapterPosition(), modelItem);
//                            Toast.makeText(context, "" + helper.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            showsnackbar("No Internet Connection!");
//                        }
//                        //your logic for double click action
//                        doubleClick = false;
//
//                    } else {
//                        doubleClick = true;
//                        handler.postDelayed(r, 500);
//                    }
//                }
//            });
//
//        btnLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isOnline()) {
//                    animateHeartphoto(helper);
//                    likORdislike(helper, helper.getAdapterPosition(), modelItem);
//                } else {
//                    showsnackbar("No Internet Connection!");
//                }
//            }
//        });
//
//        btnComments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFeedItemClickListener.onCommentsClick(v, helper.getAdapterPosition(), modelItem);
//            }
//        });
//        btnMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFeedItemClickListener.onMoreClick(v, helper.getAdapterPosition());
//            }
//        });
//    }
//
//    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
//        this.onFeedItemClickListener = onFeedItemClickListener;
//    }
//
//    void likORdislike(final BaseViewHolder holder, final int position, final PostModel item) {
//        int adapterPosition = position;
//        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("MyPosts/" + item.postId);
//        dRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                PostModel p = mutableData.getValue(PostModel.class);
//                HashMap<String, Object> result = new HashMap<>();
//                result.put("uId", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                result.put("name", uName);
//                result.put("email", uEmail);
//
//                if (p == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                if (p.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                    // Unstar the post and remove self from stars
//                    p.likesCount = p.likesCount - 1;
//                    item.localLike = 0;
//                    p.stars.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                } else {
//                    // Star the post and add self to stars
//                    p.likesCount = p.likesCount + 1;
//                    item.localLike = 1;
//                    p.stars.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), result);
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(p);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b,
//                                   DataSnapshot dataSnapshot) {
////                if (b)updateLikesCounter(item.likesCount);
//                System.out.println("bool " + b);
//                if (b) {
//
//                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
//                    System.out.println("bool " + postModel.url);
//                    animateHeartBtn(holder, postModel);
////                    updateLikesCounter(postModel.postId);
//                    notifyItemChanged(position, postModel);
//
//                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                        if (postModel.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                            showsnackbar("Liked");
//                        } else {
//                            showsnackbar("Un Liked");
//                        }
//                    }
//
//                }
//
//                // Transaction completed
//                Log.d("Adapter", "postTransaction:onComplete:" + databaseError);
////                notifyDataSetChanged();
//            }
//        });
//    }
//
//    private void animateHeartphoto(BaseViewHolder helper) {
//        View vBgLike = helper.getView(R.id.vBgLike);
//        View ivLike = helper.getView(R.id.ivLike);
//
//        vBgLike.setVisibility(View.VISIBLE);
//        ivLike.setVisibility(View.VISIBLE);
//
//        vBgLike.setScaleY(0.1f);
//        vBgLike.setScaleX(0.1f);
//        vBgLike.setAlpha(1f);
//        ivLike.setScaleY(0.1f);
//        ivLike.setScaleX(0.1f);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//
//        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(vBgLike, "scaleY", 0.1f, 1f);
//        bgScaleYAnim.setDuration(200);
//        bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(vBgLike, "scaleX", 0.1f, 1f);
//        bgScaleXAnim.setDuration(200);
//        bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(vBgLike, "alpha", 1f, 0f);
//        bgAlphaAnim.setDuration(200);
//        bgAlphaAnim.setStartDelay(150);
//        bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//
//        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(ivLike, "scaleY", 0.1f, 1f);
//        imgScaleUpYAnim.setDuration(300);
//        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(ivLike, "scaleX", 0.1f, 1f);
//        imgScaleUpXAnim.setDuration(300);
//        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//
//        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(ivLike, "scaleY", 1f, 0f);
//        imgScaleDownYAnim.setDuration(300);
//        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
//        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(ivLike, "scaleX", 1f, 0f);
//        imgScaleDownXAnim.setDuration(300);
//        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
//
//        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
//        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);
//
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                likORdislike(position,model);
//            }
//        });
//        animatorSet.start();
//    }
//
//    public void showsnackbar(String message) {
//        if (((Activity) context).getCurrentFocus() != null)
//            Snackbar.make(((Activity) context).getCurrentFocus(), message, 1000).show();
//    }
//
//    private void animateHeartBtn(BaseViewHolder holder, final PostModel pModel) {
//        final ImageButton btnLike = holder.getView(R.id.btnLike);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//
//        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(btnLike, "rotation", 0f, 360f);
//        rotationAnim.setDuration(300);
//        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
//
//        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(btnLike, "scaleX", 0.2f, 1f);
//        bounceAnimX.setDuration(300);
//        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
//
//        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(btnLike, "scaleY", 0.2f, 1f);
//        bounceAnimY.setDuration(300);
//        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
//        bounceAnimY.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                if (pModel.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                    btnLike.setImageResource(R.drawable.ic_heart_red);
//                } else {
//                    btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
//                }
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                int toValue = pModel.likesCount;
//                String likesCountTextFrom = tsLikesCounter.getResources().getQuantityString(
//                        R.plurals.likes_count, toValue - 1, toValue - 1
//                );
//                tsLikesCounter.setCurrentText(likesCountTextFrom);
//
//                String likesCountTextTo = tsLikesCounter.getResources().getQuantityString(
//                        R.plurals.likes_count, toValue, toValue
//                );
//                tsLikesCounter.setText(likesCountTextTo);
//            }
//        });
//
//        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
//        animatorSet.start();
////        btnLike.startAnimation(AnimationUtils.loadAnimation(context, R.anim.photo_rotate));
//    }
//
//    protected boolean isOnline() {
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//
//        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
//
//            return true;
//
//        } else {
//
//            return false;
//
//        }
//
//    }
//
//    public interface OnFeedItemClickListener {
//        void onCommentsClick(View v, int position, PostModel postModel);
//
//        //        void onStarClick(View v)
//        void onMoreClick(View v, int position);
//
//        void onProfileClick(View v);
//    }
//
//    public class MySpannable extends ClickableSpan {
//
//        private boolean isUnderline = false;
//
//        /**
//         * Constructor
//         */
//        public MySpannable(boolean isUnderline) {
//            this.isUnderline = isUnderline;
//        }
//
//        @Override
//        public void updateDrawState(TextPaint ds) {
//
//            ds.setUnderlineText(isUnderline);
//            ds.setColor(Color.parseColor("#343434"));
//
//        }
//
//        @Override
//        public void onClick(View widget) {
//
//        }
//    }
//}
public class PostAutoAdapter extends BaseQuickAdapter<PostModel, BaseViewHolder> {
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comments");

    //    public ImageButton btnComments;
//    public ImageButton btnLike;
//    public ImageButton btnMore;
//    public TextView tvLike;
//    public ImageView ivUserProfile;
//    public FrameLayout vImageRoot;
//    public CardView cardView;
//    public View view;
    static int flag = 1;
    boolean doubleClick = false;
    List<PostModel> mList;
    LinearLayoutManager layoutManager;
    private Context context;
    private String uName = "";
    private String uEmail = "";
    private OnFeedItemClickListener onFeedItemClickListener;
    private int currentPosition;
    //
//    public PostAutoAdapter(FragmentActivity activity, int item_feed, List<PostModel> mList, LinearLayoutManager layoutManager) {
//        super(item_feed, mList);
//        context = activity;
//        this.mList = mList;
//        this.layoutManager = layoutManager;
//    }
    public PostAutoAdapter(FragmentActivity activity, int item_feed, List<PostModel> mList) {
        super(item_feed, mList);
        context = activity;
        this.mList = mList;
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            uName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            uEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        //  this.layoutManager = layoutManager;
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public void pauseOther(BaseViewHolder helper) {
        for (int i = 0; i < mList.size(); i++) {
            if (i != currentPosition) {
                ViewGroup viewGroup = (ViewGroup) layoutManager.findViewByPosition(i);
                SimpleExoPlayerView playerView = helper.getView(R.id.exoPlayer);
                if (playerView.getPlayer().getPlayWhenReady())
                    playerView.getPlayer().stop();
            }

        }

    }

    @Override
    protected void convert(final BaseViewHolder helper, final PostModel modelItem) {

        final ImageView ivFeedCenter = helper.getView(R.id.ivFeedCenter);
        final ImageView ivFeedLoading = helper.getView(R.id.ivFeedLoading);
        final TextView tvFeedDescription = helper.getView(R.id.tvFeedBottom);
        final TextView tvUserName = helper.getView(R.id.tvUserName);
        final ImageButton btnComments = helper.getView(R.id.btnComments);
        final ImageButton btnLike = helper.getView(R.id.btnLike);
        final ImageButton btnMore = helper.getView(R.id.btnMore);
        final  ImageButton btnShare=helper.getView(R.id.btnShare);
        final View vBgLike = helper.getView(R.id.vBgLike);
        final ImageView ivLike = helper.getView(R.id.ivLike);
        final TextSwitcher tsLikesCounter = helper.getView(R.id.tsLikesCounter);
        final CircleImageView ivUserProfile = helper.getView(R.id.ivUserProfile);
        final FrameLayout vImageRoot = helper.getView(R.id.vImageRoot);
        final TextView tvLike = helper.getView(R.id.tvLikes);
        final CardView cardView = helper.getView(R.id.card_view);
        final SimpleExoPlayerView playerView = helper.getView(R.id.exoPlayer);
        final EditText edComment = helper.getView(R.id.edAddComment);
        final ImageView imgAddComment = helper.getView(R.id.imgPostComment);
        final TextView tvTotalComments = helper.getView(R.id.tvCommentCount);
       // final ImageView imagePlayPause = helper.getView(R.id.imgPlayPause);


        tvTotalComments.setText("View all "+modelItem.commentsCount+" comments");
        imgAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edComment.getText().toString())) {
                    String comment = edComment.getText().toString();

                    if (FirebaseAuth.getInstance().getCurrentUser() != null ){
                        String name = SessionSave.getSession(Appconstants.USER_PROFILE_NAME,context);
                        String userId = SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,context);

                        Comments_Model cModel = new Comments_Model(name, userId, comment, System.currentTimeMillis()/1000,SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,context));
                        mDatabase.child(modelItem.postId).push().setValue(cModel, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(context, "Failed to Add Comment", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "You Commented on this post", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(context, "Please Login to give comment", Toast.LENGTH_SHORT).show();
                    }
//            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

                    edComment.setText(null);

                }else {
                    Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_SHORT).show();
                    imgAddComment.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_error));
                }
            }
        });

        tvTotalComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onCommentsClick(v, helper.getAdapterPosition(), modelItem);
            }
        });

        tvUserName.setText(modelItem.ownerName);

        if(modelItem.ownerProfileUrl!=null &&!modelItem.ownerProfileUrl.equals(""))
        Picasso.with(mContext).load(modelItem.ownerProfileUrl).into(ivUserProfile);
        else
        ivUserProfile.setVisibility(View.INVISIBLE);
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(view.getVisibility()==View.VISIBLE){
                    if(modelItem.uId!=null && !modelItem.uId.equals("") && modelItem.ownerName!=null && !modelItem.ownerName.equals("")){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int[] startingLocation = new int[2];
                                view.getLocationOnScreen(startingLocation);
                                startingLocation[0] += view.getWidth() / 2;
                                //  startingLocation[1]=500;
                                Bundle b = new Bundle();

//                                Toast.makeText(g)
                                UserProfileActivity.startUserProfileFromLocation(startingLocation, (Activity) mContext, false, modelItem.uId, modelItem.ownerName, modelItem.ownerProfileUrl);
                                ((Activity)mContext).overridePendingTransition(0, 0);
                            }
                        }, 100);
                    }
                }
            }
        });
//        FirebaseUser user = FirebaseAuth.getInstance().g modelItem.uId;

//        imagePlayPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentPosition = helper.getAdapterPosition();
//                pauseOther(helper);
//                AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
//                int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
//
//                if (flag == 1) {
//                    flag = 0;
////                    playerView.getPlayer().setVolume(0.0f);
//                    playerView.getPlayer().setPlayWhenReady(true);
//                    imagePlayPause.setImageResource(R.drawable.exo_controls_play);
//                } else {
//                    flag = 1;
////                    playerView.getPlayer().setVolume(currentVolume);
//                    playerView.getPlayer().setPlayWhenReady(false);
//                    imagePlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
//                }
//
//
//            }
//        });

        if (modelItem.typeOfPost == 1) {
            ivFeedLoading.setVisibility(View.VISIBLE);
            ivFeedCenter.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            try {
                System.out.println("Drawable sss" + ivFeedCenter.getDrawable());
                if (ivFeedCenter.getDrawable() == null) {
                    byte[] encodeByte = Base64.decode(modelItem.videoThumbnail, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//                ivFeedLoading.setVisibility(View.GONE);
                    ivFeedLoading.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                System.out.println("imageLoadingError " + e.getMessage());
            }
            Picasso.with(context)
                    .load(modelItem.videoThumbnail)
                    .into(ivFeedCenter);
//            SimpleExoPlayer player;
//            ivFeedLoading.setVisibility(View.GONE);
//            ivFeedCenter.setVisibility(View.GONE);
//            playerView.setVisibility(View.VISIBLE);
//            player = ExoPlayerFactory.newSimpleInstance(
//                    new DefaultRenderersFactory(mContext),
//                    new DefaultTrackSelector(), new DefaultLoadControl());
//
//            playerView.setPlayer(player);
//
////            player.setPlayWhenReady(true);
////            player.seekTo(currentWindow, playbackPosition);
//
//            Uri uri = Uri.parse(modelItem.url);
//            MediaSource mediaSource = buildMediaSource(uri);
//            player.prepare(mediaSource, true, false);
//            try {
//                byte[] encodeByte = Base64.decode(modelItem.thumbNail, Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//                ivFeedCenter.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("imageLoadingError " + e.getMessage());
//                ivFeedCenter.setImageResource(R.drawable.ic_error);
//            }

        } else {
            ivFeedLoading.setVisibility(View.VISIBLE);
            ivFeedCenter.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            try {
                System.out.println("Drawable sss" + ivFeedCenter.getDrawable());
                if (ivFeedCenter.getDrawable() == null) {
                    byte[] encodeByte = Base64.decode(modelItem.thumbnail, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//                ivFeedLoading.setVisibility(View.GONE);
                    ivFeedLoading.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                System.out.println("imageLoadingError " + e.getMessage());
            }
            Picasso.with(context)
                    .load(modelItem.url)
                    .into(ivFeedCenter);
        }

        if (FirebaseAuth.getInstance().getCurrentUser()!=null && modelItem.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid()) | modelItem.localLike == 1) {
            btnLike.setImageResource(R.drawable.ic_heart_red);
        } else {
            btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
        }
        if (modelItem.description.length() > 100) {
            tvFeedDescription.setText(modelItem.description);
            makeTextViewResizable(tvFeedDescription, 3, "See More", true);
        } else {
            tvFeedDescription.setText(modelItem.description);
        }

//        tvFeedDescription.setText(SpannableStringUtils.getBuilder(modelItem.description).append("more").setClickSpan(clickableSpan).create());
//        tvFeedDescription.setMovementMethod(LinkMovementMethod.getInstance());
        tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
                R.plurals.likes_count, modelItem.likesCount, modelItem.likesCount
        ));
        ivFeedCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelItem.typeOfPost == 1) {
                    Toast.makeText(context, "Video", Toast.LENGTH_SHORT).show();
                }

                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        if (doubleClick) {
                            if (modelItem.typeOfPost == 1) {
                                Intent intent = new Intent(context, VideoFullScreenActivity.class);
                                intent.putExtra("videos", modelItem.url);
                                context.startActivity(intent);
                            }else {
                                Intent intent = new Intent(context, PhotoViewActivity.class);
                                intent.putExtra("imageUrl", modelItem.url);
                                context.startActivity(intent);
                            }
                        }
                        doubleClick = false;
                    }
                };

                if (doubleClick) {
                    doubleClick = false;
                    if (isOnline()) {
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            animateHeartphoto(helper);
                            likORdislike(helper, helper.getAdapterPosition(), modelItem);
                        }else{
                            Utils.showAlert(mContext,mContext.getString(R.string.kindly_login),mContext.getString(R.string.ask_login_message),mContext.getString(R.string.ok),mContext.getString(R.string.cancel), new Dialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i == -1){
                                        mContext.startActivity(new Intent(mContext, SocialLoginCustom.class));
                                    }
                                }
                            },true);
                        }
                    //    Toast.makeText(context, "" + helper.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    } else {
                        showsnackbar("No Internet Connection!");
                    }
                    //your logic for double click action


                } else {
                    doubleClick = true;
                    handler.postDelayed(r, 500);

                }
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        animateHeartphoto(helper);
                        likORdislike(helper, helper.getAdapterPosition(), modelItem);}else{
                        Utils.showAlert(mContext,mContext.getString(R.string.kindly_login),mContext.getString(R.string.ask_login_message),mContext.getString(R.string.ok),mContext.getString(R.string.cancel), new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == -1){
                                    mContext.startActivity(new Intent(mContext, SocialLoginCustom.class));
                                }
                            }
                        },true);
                    }
                } else {
                    showsnackbar("No Internet Connection!");
                }
            }
        });

        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onCommentsClick(v, helper.getAdapterPosition(), modelItem);
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onMoreClick(v, helper.getAdapterPosition());
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFeedItemClickListener.onMoreClick(view, helper.getAdapterPosition());
            }
        });
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    void likORdislike(final BaseViewHolder holder, final int position, final PostModel item) {
        final ImageButton btnLike = holder.getView(R.id.btnLike);
        final TextView tvFeedDescription = holder.getView(R.id.tvFeedBottom);
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("MyPosts/" + item.postId);
        dRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                PostModel p = mutableData.getValue(PostModel.class);
                HashMap<String, Object> result = new HashMap<>();
                result.put("uId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                result.put("ownerName", SessionSave.getSession(Appconstants.USER_PROFILE_NAME,mContext));
                result.put("ownerProfileUrl", SessionSave.getSession(Appconstants.USER_PROFILE_NAME,mContext));

                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    // Unstar the post and remove self from stars
                    p.likesCount = p.likesCount - 1;
                    item.localLike = 0;
                    p.stars.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                } else {
                    // Star the post and add self to stars
                    p.likesCount = p.likesCount + 1;
                    item.localLike = 1;
                    p.stars.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), result);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
//                if (b)updateLikesCounter(item.likesCount);
                System.out.println("bool " + b);
                if (b && dataSnapshot.getValue() != null) {

                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                    System.out.println("bool " + postModel.url);
                    mList.set(position, postModel);
                    animateHeartBtn(holder, postModel);
                    if (postModel.description.length() > 100) {
                        tvFeedDescription.setText(postModel.description);
                        makeTextViewResizable(tvFeedDescription, 3, "See More", true);
                    } else {
                        tvFeedDescription.setText(postModel.description);
                    }

//                    updateLikesCounter(postModel.postId);
//                    notifyDataSetChanged();
//                    Picasso.with(context)
//                            .load(postModel.url)
//                            .into(holder.getView(R.id.ivFeedCenter));
                    //     notifyItemChanged(position);

                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if (postModel.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            btnLike.setImageResource(R.drawable.ic_heart_red);
                            showsnackbar("Liked");
                        } else {
                            btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
                            showsnackbar("Un Liked");
                        }
                    }else{
                        Utils.showAlert(mContext,mContext.getString(R.string.kindly_login),mContext.getString(R.string.ask_login_message),mContext.getString(R.string.ok),mContext.getString(R.string.cancel), new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == -1){
                                    mContext.startActivity(new Intent(mContext, SocialLoginCustom.class));
                                }
                            }
                        },true);
                    }

                }

                // Transaction completed
                Log.d("Adapter", "postTransaction:onComplete:" + databaseError);
//                notifyDataSetChanged();
            }
        });
    }

    private void animateHeartphoto(BaseViewHolder helper) {
        View vBgLike = helper.getView(R.id.vBgLike);
        View ivLike = helper.getView(R.id.ivLike);

        vBgLike.setVisibility(View.VISIBLE);
        ivLike.setVisibility(View.VISIBLE);

        vBgLike.setScaleY(0.1f);
        vBgLike.setScaleX(0.1f);
        vBgLike.setAlpha(1f);
        ivLike.setScaleY(0.1f);
        ivLike.setScaleX(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(vBgLike, "scaleY", 0.1f, 1f);
        bgScaleYAnim.setDuration(200);
        bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(vBgLike, "scaleX", 0.1f, 1f);
        bgScaleXAnim.setDuration(200);
        bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(vBgLike, "alpha", 1f, 0f);
        bgAlphaAnim.setDuration(200);
        bgAlphaAnim.setStartDelay(150);
        bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(ivLike, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(ivLike, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(ivLike, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(ivLike, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                likORdislike(position,model);
            }
        });
        animatorSet.start();
    }

    public void showsnackbar(String message) {
        if (((Activity) context).getCurrentFocus() != null)
            Snackbar.make(((Activity) context).getCurrentFocus(), message, 1000).show();
    }

    private void animateHeartBtn(BaseViewHolder holder, final PostModel pModel) {
        final ImageButton btnLike = holder.getView(R.id.btnLike);
        final TextSwitcher tsLikesCounter = holder.getView(R.id.tsLikesCounter);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(btnLike, "rotation", 0f, 360f);
        rotationAnim.setDuration(300);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(btnLike, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(btnLike, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (pModel.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    btnLike.setImageResource(R.drawable.ic_heart_red);
                } else {
                    btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int toValue = pModel.likesCount;
                String likesCountTextFrom = tsLikesCounter.getResources().getQuantityString(
                        R.plurals.likes_count, toValue - 1, toValue - 1
                );
                tsLikesCounter.setCurrentText(likesCountTextFrom);

                String likesCountTextTo = tsLikesCounter.getResources().getQuantityString(
                        R.plurals.likes_count, toValue, toValue
                );
                tsLikesCounter.setText(likesCountTextTo);
            }
        });

        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
        animatorSet.start();
//        btnLike.startAnimation(AnimationUtils.loadAnimation(context, R.anim.photo_rotate));
    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }

    }

    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position, PostModel postModel);

        //        void onStarClick(View v)
        void onMoreClick(View v, int position);

        void onProfileClick(View v);
    }

    public class MySpannable extends ClickableSpan {

        private boolean isUnderline = false;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {

            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#343434"));

        }

        @Override
        public void onClick(View widget) {

        }
    }
}

