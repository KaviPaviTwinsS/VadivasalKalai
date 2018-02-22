package pasu.vadivasal;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import pasu.vadivasal.Profile.UserProfileActivity;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.PostModel;
import pasu.vadivasal.photographyInsta.CommentsAutoActivity;
import pasu.vadivasal.regLogin.SocialLoginCustom;
import pasu.vadivasal.view.FeedContextMenu;
import pasu.vadivasal.view.FeedContextMenuManager;

public class OpenShareContent extends AppCompatActivity implements FeedContextMenu.OnFeedContextMenuItemClickListener {
    @BindView(R.id.card_view)
    public CardView coordinatorLayout;
    @BindView(R.id.ivFeedCenter)
    public ImageView ivFeedCenter;
    @BindView(R.id.tvFeedBottom)
    public TextView tvFeedDescription;
    @BindView(R.id.btnComments)
    public ImageButton btnComments;
    @BindView(R.id.btnLike)
    ImageButton btnLike;
    @BindView(R.id.btnShare)
    ImageButton btnShare;
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
    @BindView(R.id.tvUserName)
    public TextView tvUserName;
    @BindView(R.id.vImageRoot)
    public FrameLayout vImageRoot;
    private Uri data;
    private String postId = "";
    private DatabaseReference mDatabase;
    private PostModel postModel;
    private Bitmap bitmapImage;
    private String urlImage;

    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 101;
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    boolean doubleClick = false;

    private String uName = "";
    private String uEmail = "";
    private ProgressDialog mProgressDialog;

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_feed);
        ButterKnife.bind(this);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            uName = SessionSave.getSession(Appconstants.USER_PROFILE_NAME, this);
            uEmail = SessionSave.getSession(Appconstants.USER_PROFILE_EMAIL_ID, this);
        }
        Intent intent = getIntent();
        if (intent != null) {
            try {
                String action = intent.getAction();
                data = intent.getData();
                postId = data.getQueryParameter("id");
                System.out.println("uristring " + postId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (postId.equals(""))
                postId = intent.getStringExtra("id");
 /*           String[] arrSplit = data.toString().replaceFirst("^/", "").split("/");
           if(arrSplit.length!=0)
            {
                postId = arrSplit[arrSplit.length-1];
//                System.out.println("received "+arrSplit[i]);
            }*/
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("MyPosts").child(postId);
        
        
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postModel = dataSnapshot.getValue(PostModel.class);
                if (postModel != null)
                    updateViews(postModel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OpenShareContent.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateViews(final PostModel model) {




Context mContext=OpenShareContent.this;
        tvUserName.setText(model.ownerName);
        if(model.ownerProfileUrl!=null &&!model.ownerProfileUrl.equals(""))
            Picasso.with(mContext).load(model.ownerProfileUrl).into(ivUserProfile);
        else
            ivUserProfile.setVisibility(View.INVISIBLE);
        System.out.println("udidiid_"+model.uId+"__"+model.ownerName);
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(view.getVisibility()==View.VISIBLE){
                    if(model.uId!=null && !model.uId.equals("") && model.ownerName!=null && !model.ownerName.equals("")){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int[] startingLocation = new int[2];
                                view.getLocationOnScreen(startingLocation);
                                startingLocation[0] += view.getWidth() / 2;
                                //  startingLocation[1]=500;
                                Bundle b = new Bundle();

//                                Toast.makeText(g)
                                UserProfileActivity.startUserProfileFromLocation(startingLocation, (Activity) OpenShareContent.this, false, model.uId, model.ownerName, model.ownerProfileUrl);
                                ((Activity)OpenShareContent.this).overridePendingTransition(0, 0);
                            }
                        }, 100);
                    }
                }
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        if (model.typeOfPost == 1) {

            try {
                byte[] encodeByte = Base64.decode(model.videoThumbnail, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                ivFeedCenter.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.getMessage();
                ivFeedCenter.setImageResource(R.drawable.ic_avatar);
            }
//            Picasso.with(context)
//                    .load(model.videoThumbnail)
//                    .placeholder(R.drawable.loading)
//                    .error(R.drawable.ic_error)
//                    .into(ivFeedCenter);
        } else {
            Picasso.with(this)
                    .load(model.url)
                    .error(R.drawable.ic_bull_logo)
                    .into(ivFeedCenter);
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null && model.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            btnLike.setImageResource(R.drawable.ic_heart_red);
        } else {
            btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
        }
        if (model.description.length() > 100) {
            tvFeedDescription.setText(model.description);
            makeTextViewResizable(tvFeedDescription, 3, "See More", true);
        } else {
            tvFeedDescription.setText(model.description);
        }

//        tvFeedDescription.setText(SpannableStringUtils.getBuilder(model.description).append("more").setClickSpan(clickableSpan).create());
//        tvFeedDescription.setMovementMethod(LinkMovementMethod.getInstance());
        tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
                R.plurals.likes_count, model.likesCount, model.likesCount
        ));
        ivFeedCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (model.typeOfPost == 1) {
                        Toast.makeText(OpenShareContent.this, "Video", Toast.LENGTH_SHORT).show();
                    }
                    Handler handler = new Handler();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {

                            doubleClick = false;
                        }
                    };

                    if (doubleClick) {
                        if (isOnline()) {
                            animateHeartphoto();
                            likORdislike(model);
//                        Toast.makeText(context, "" + helper.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        } else {
                            showsnackbar("No Internet Connection!");
                        }
                        //your logic for double click action
                        doubleClick = false;

                    } else {
                        doubleClick = true;
                        handler.postDelayed(r, 500);
                    }
                } else {
                    Utils.showAlert(OpenShareContent.this, getString(R.string.kindly_login), getString(R.string.ask_login_message), getString(R.string.ok), getString(R.string.cancel), new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == -1) {
                                startActivity(new Intent(OpenShareContent.this, SocialLoginCustom.class));
                            }
                        }
                    }, true);
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  FeedContextMenuManager.getInstance().onMoreClick(view, helper.getAdapterPosition());
//                Toast.makeText(OpenShareContent.this, "Showing", Toast.LENGTH_SHORT).show();
                showProgressDialog();
                urlImage = postModel.url;
                if (postModel.typeOfPost == 1) {
                    Picasso.with(OpenShareContent.this)
                            .load(postModel.videoThumbnail)
                            .into(picassoImageTarget(OpenShareContent.this, "imageDir", "my_image.jpeg"));
                } else {
                    Picasso.with(OpenShareContent.this)
                            .load(postModel.url)
                            .into(picassoImageTarget(OpenShareContent.this, "imageDir", "my_image.jpeg"));
                }
            }
        });
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (isOnline()) {
                        animateHeartphoto();
                        likORdislike(model);
                    } else {
                        showsnackbar("No Internet Connection!");
                    }
                } else {
                    Utils.showAlert(OpenShareContent.this, getString(R.string.kindly_login), getString(R.string.ask_login_message), getString(R.string.ok), getString(R.string.cancel), new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == -1) {
                                startActivity(new Intent(OpenShareContent.this, SocialLoginCustom.class));
                            }
                        }
                    }, true);

                }
            }
        });

        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(OpenShareContent.this, CommentsAutoActivity.class);
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                intent.putExtra(CommentsAutoActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
                intent.putExtra("postID", model.postId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, btnMore.getHeight(), OpenShareContent.this);
            }
        });
    }

    void likORdislike(final PostModel item) {
//        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("MyPosts/" + item.postId);
        mDatabase.runTransaction(new Transaction.Handler() {
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
                if (b) {

                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                    System.out.println("bool " + postModel.url);
                    animateHeartBtn(postModel);
//                    updateLikesCounter(postModel.postId);
//                    notifyItemChanged(position, postModel);

                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if (postModel.stars.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            showsnackbar("Liked");
                        } else {
                            showsnackbar("Un Liked");
                        }
                    }

                }

                // Transaction completed
                Log.d("Adapter", "postTransaction:onComplete:" + databaseError);
//                notifyDataSetChanged();
            }
        });
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.processing));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void animateHeartphoto() {

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

    private void animateHeartBtn(final PostModel pModel) {

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

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }

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

    public void showsnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, 1000).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OpenShareContent.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        urlImage = postModel.url;
        if (postModel.typeOfPost == 1) {
            Picasso.with(this)
                    .load(postModel.videoThumbnail)
                    .into(picassoImageTarget(this, "imageDir", "my_image.jpeg"));
        } else {
            Picasso.with(this)
                    .load(postModel.url)
                    .into(picassoImageTarget(this, "imageDir", "my_image.jpeg"));
        }
        //  FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {

    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    public void addWaterMark(Bitmap src, String url, PostModel model) {
        int w = src.getWidth();
        int h = src.getHeight();
        Point point = new Point();
        point.set((int) (w / 1.5), (int) (h / 2.8));
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);


        Bitmap waterMark = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher), (w / 4), (h / 4), false);

        canvas.drawBitmap(waterMark, point.x, point.y, null);
        if (result != null) {
            if (isStoragePermissionGranted()) {
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), result, "title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.putExtra(Intent.EXTRA_TEXT, "https://ilovejallikattu.000webhostapp.com/images/image.jpg" + "?id=" + model.postId);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        }
//        return result;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            bitmapImage = bitmap;
                            addWaterMark(bitmap, urlImage, postModel);
                        }

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (bitmapImage != null) {
                        addWaterMark(bitmapImage, urlImage, postModel);
                    }

                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
