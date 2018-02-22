package pasu.vadivasal.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.globalModle.ProfileData;
import pasu.vadivasal.regLogin.SocialLoginCustom;
import pasu.vadivasal.tournament.SimpleImageAdapter;
import pasu.vadivasal.view.RevealBackgroundView;

/**
 * Created by Miroslaw Stanek on 14.01.15.
 */
public class UserProfileActivity extends BaseDrawerActivity implements RevealBackgroundView.OnStateChangeListener, AppBarLayout.OnOffsetChangedListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private static final int PICK_IMAGE_REQUEST = 234;

    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
//    @BindView(R.id.rvUserProfile)
//    RecyclerView rvUserProfile;

    @BindView(R.id.tlUserProfileTabs)
    TabLayout tlUserProfileTabs;

    @BindView(R.id.imgPlayer)
    ImageView ivUserProfilePhoto;
    @BindView(R.id.layCenter)
    View vUserDetails;
    //    @BindView(R.id.btnFollow)
//    Button btnFollow;
    @BindView(R.id.follow_lay)
    View vUserStats;
    @BindView(R.id.vUserProfileRoot)
    View vUserProfileRoot;

    private int avatarSize;
    private String profilePhoto;
    private ImageView edit_profile;
    @BindView(R.id.appBarLayout)
    AppBarLayout appbarLayout;
    private TabsAdapter tabAdapter;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private DatabaseReference myRef;
    @BindView(R.id.profile_lay)
    View profile_lay;
    private ValueEventListener valueEventListener;
    private ProfileData datav;
    private String profile_name;
    private boolean selfProfile;
    private String profile_id="";

    @BindView(R.id.btnFollow)
    TextView btnFollow;
    @BindView(R.id.profile_following_count)
    TextView profile_following_count;
    @BindView(R.id.profile_followers_count)
    TextView profile_followers_count;
    private ArrayList<Media> mediaData = new ArrayList<>();
    private String followId;
    private FloatingActionButton fab;
    private String destinationFileName = "profile_image";
    private Uri imageUri;
    private TextView tvPlayerName;
    private ValueEventListener followListner;
    private ProgressDialog progressDialog;
private String profileName;
    private String compressedImage="";
    // private UserProfileAdapter userPhotosAdapter;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity, boolean isSelfProfile, String profile_id,String name,String image) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra("isprofile", isSelfProfile);
        intent.putExtra("id", profile_id);
        intent.putExtra("name",name);
        intent.putExtra("image",image);
        System.out.println("idddddddd2"+profile_id);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (selfProfile && ivUserProfilePhoto != null && UserProfileActivity.this != null  && !SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE,UserProfileActivity.this).equals(""))
                    Picasso.with(UserProfileActivity.this)
                            .load(SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE, UserProfileActivity.this))
                            .placeholder(R.drawable.user)
                            .resize(avatarSize, avatarSize)
                            .centerCrop()
                            .error(R.drawable.user)
                            // .transform(new CircleTransformation())
                            .into(ivUserProfilePhoto);
            }
        }, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (getIntent() != null) {
            selfProfile = getIntent().getBooleanExtra("isprofile", false);
            if(getIntent().getStringExtra("id")!=null)
            profile_id = getIntent().getStringExtra("id");
            if(getIntent().getStringExtra("name")!=null)
            profileName= getIntent().getStringExtra("name");
            if(getIntent().getStringExtra("image")!=null)
                profilePhoto= getIntent().getStringExtra("image");

        }
        if(!Utils.isEmptyString(profilePhoto))
        Picasso.with(UserProfileActivity.this).load(profilePhoto).into(ivUserProfilePhoto);

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(UserProfileActivity.this,getString(R.string.comming_soon),Toast.LENGTH_SHORT).show();
//
//                if (btnFollow.getText().toString().equals(getString(R.string.follow)))
//                    followUnFollow(true);
//                else
//                    confirmUnfollow();
            }
        });
        System.out.println("idddddddd"+profile_id+"___"+SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this));
        if (FirebaseAuth.getInstance().getCurrentUser() != null && profile_id.equals(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this)))
            selfProfile = true;
        else

            selfProfile = false;
        fab = findViewById(R.id.add_media);
        tvPlayerName = (TextView) findViewById(R.id.tvPlayerName);
        tvPlayerName.setText(profileName);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        this.profilePhoto = getString(R.string.user_profile_photo);
        edit_profile = (ImageView) findViewById(R.id.edit_profile);
        followListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    btnFollow.setText(getString(R.string.following));
                    btnFollow.setBackgroundColor(ContextCompat.getColor(UserProfileActivity.this, R.color.divider));
                }
//                else {
//                    btnFollow.setText(getString(R.string.follow));
//                    btnFollow.setBackgroundColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorAccent));
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datav != null) {
                    String ss = Utils.toString(datav);
                    Intent i = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                    i.putExtra("data", ss);
                    startActivity(i);
                }
            }
        });
        if (selfProfile) {
            btnFollow.setVisibility(View.GONE);
            edit_profile.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.add_media).setVisibility(View.GONE);
            edit_profile.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 1000);
//            getData();
//        } else {
//           // startActivity(new Intent(UserProfileActivity.this, SocialLoginCustom.class));
//        }
        getData();
//        appbarLayout.addOnOffsetChangedListener(this);
//        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        tabAdapter = new TabsAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        tlUserProfileTabs.setupWithViewPager(viewPager);


        setupUserProfileGrid();
        setupRevealBackground(savedInstanceState);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void followUnFollow(boolean isFollow) {
        if (isFollow) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference followRef = database.getReference("profile/" + profile_id + "/followings").push();
            followRef.setValue(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this));
            followRef.addValueEventListener(followListner);

        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            database.getReference("profile/" + profile_id + "/followings/" + followId).removeValue();
        }
    }

    private void confirmUnfollow() {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.unfollow))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.btn_positive),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                followUnFollow(false);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.btn_negative),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create();
        d.setMessage(getString(R.string.unfollow_alert) + " " + profile_name + "?");
        d.show();
    }


    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        System.out.println("getDataaaa" + profile_id + "__" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this));
        myRef = database.getReference("profile/" + profile_id );
        Query queryRef;

        queryRef = myRef.orderByKey()
                .limitToLast(1);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // progress_bar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    profile_lay.setVisibility(View.VISIBLE);
                    datav = dataSnapshot.getValue(ProfileData.class);
                    System.out.println("came.." + dataSnapshot.getValue().toString() + "___" + dataSnapshot.toString());
//                    Picasso.with(UserProfileActivity.this).load(datav.getProfileImageUrl()).into(ivUserProfilePhoto);
                   datav.setName(profileName);
                  //  profile_name = datav.getName();
                    tvPlayerName.setText(profileName);
                    tabAdapter.setProfileData(datav);
                    if (datav.getFollowings() != null) {
                        profile_following_count.setText("" + datav.getFollowings().size());
                        ArrayList<String> ss = new ArrayList<String>(datav.getFollowings().keySet());
                        for (int i = 0; i < datav.getFollowings().size(); i++) {
                            if (datav.getFollowings().get(ss.get(i)).equals(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this))) {
                                // followId = ss.get(i);
                                DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("profile/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this) + "/followings/" + followId);
                                // followRef.addValueEventListener(followListner);
                                btnFollow.setText(getString(R.string.following));
                                btnFollow.setBackgroundColor(ContextCompat.getColor(UserProfileActivity.this, R.color.divider));
                            }
                        }
                    } else
                        profile_following_count.setText("0");
                    if (datav.getFollowers() != null)
                        profile_followers_count.setText("" + datav.getFollowers().size());
                    else
                        profile_followers_count.setText("0");
//                    GenericTypeIndicator<ArrayList<Media>> t = new GenericTypeIndicator <ArrayList<Media>>() {};

//                    GenericTypeIndicator<HashMap<Media>> t = new GenericTypeIndicator<HashMap<Media>>() {};
//
//                    HashMap<Media> s = dataSnapshot.getValue(t);
                    HashMap<String, Media> sh = datav.getMedia();
                    ArrayList<Media> s = new ArrayList<Media>(sh.values());
//                    for (int i = 0; i < 10; i++) {
//                        Media d=new Media();
//                        d.setLikes(i);
//                        d.setMediaUrl("http://www.gstatic.com/webp/gallery/1.jpg");
//                        d.setType(0);
//                        s.add(d);
//                    }

                    // System.out.println("databaseerr"+s.size());
//                    mediaData.addAll(s);
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("profile/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this) + "/media");
//                    myRef.setValue(s);
                    System.out.println("mediagot" + s.size());
                    if (s != null)
                        tabAdapter.setMatchData(s);

                } else {
                    System.out.println("databaseerrorss");
//                    no_data_lay.setVisibility(View.VISIBLE);
//                    profile_lay.setVisibility(View.GONE);
//                    ((TextView) no_data_lay.findViewById(R.id.error_header)).setText(getString(R.string.hi) + "  " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//                    ((TextView) no_data_lay.findViewById(R.id.error_long_description)).setText(getString(R.string.player_data_issue));
//                    (no_data_lay.findViewById(R.id.btnRetry)).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            onBackPressed();
//                        }
//                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("databaseerror");
//                progress_bar.setVisibility(View.GONE);
//                if (datas == null || datas.size() == 0)
//                    no_data_lay.setVisibility(View.VISIBLE);
//                else
//                    no_data_lay.setVisibility(View.GONE);
            }
        };
        myRef.addListenerForSingleValueEvent(valueEventListener);
    }


    private void setupTabs() {
        //  tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
    }

    private void setupUserProfileGrid() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        rvUserProfile.setLayoutManager(layoutManager);
//        rvUserProfile.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//               // userPhotosAdapter.setLockedAnimations(true);
//            }
//        });
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
            //    userPhotosAdapter.setLockedAnimations(true);
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            //rvUserProfile.setVisibility(View.VISIBLE);
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
//            userPhotosAdapter = new UserProfileAdapter(this);
//            rvUserProfile.setAdapter(userPhotosAdapter);
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            //  rvUserProfile.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void animateUserProfileOptions() {
        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
        vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
        ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
        vUserDetails.setTranslationY(-vUserDetails.getHeight());
        vUserStats.setAlpha(0);

        vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
        ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
        vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
        vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }


    private class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;
        ProfileGalleryFragment matchesFragment;
        PlayerInfoFragment playerInfoFragment;
//        addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
//    }

        TabsAdapter(FragmentManager fm) {
            super(fm);
            tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
            tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
        }

        void setMatchData(ArrayList<Media> media) {
            mediaData.addAll(media);
//            if (matchesFragment != null)
//                matchesFragment.setData(mediaData);
        }


        void setProfileData(ProfileData p) {
            if (playerInfoFragment != null)
                playerInfoFragment.setData(p);
        }


        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 1) {
                matchesFragment = new ProfileGalleryFragment();
                return matchesFragment;
            } else {
                playerInfoFragment = new PlayerInfoFragment();
                return playerInfoFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1)
                return getString(R.string.tab_title_photos);
            else
                return getString(R.string.tab_title_profile);
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            // ResultActivity.startWithUri(SampleActivity.this, resultUri);
            File f = new File(resultUri.getPath());
            Picasso.with(UserProfileActivity.this)
                    .invalidate(f);
            Picasso.with(this)
                    .load(f)
                    .placeholder(R.drawable.user)
                    .resize(avatarSize, avatarSize)
                    .centerCrop()
                    .error(R.drawable.user)
                    // .transform(new CircleTransformation())
                    .into(ivUserProfilePhoto);
            uploadFile(resultUri, "images/profileimage/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this) + ".jpg", true);
            System.out.println("Hellow" + resultUri);
        } else {
            // Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        if (requestcode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadFile(data.getData(), "images/" + new Date().getTime() + ".jpg", false);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else if (requestcode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        } else if (resultCode == RESULT_OK) {
            System.gc();
            switch (requestcode) {
                case 0:
                    try {
                        UCrop uCrop = UCrop.of(Uri.fromFile(new File(getRealPathFromURI(data.getDataString()))), Uri.fromFile(new File(UserProfileActivity.this.getCacheDir(), destinationFileName)))
                                .withAspectRatio(4, 4)
                                .withMaxResultSize(500, 500);
                        UCrop.Options options = new UCrop.Options();
                        options.setToolbarColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorPrimary));
                        options.setStatusBarColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorPrimary));
                        options.setToolbarWidgetColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorAccent));
                        uCrop.withOptions(options);
                        uCrop.start(UserProfileActivity.this);
                        //new ImageCompressionAsyncTask().execute(data.getDataString());
//                            CropImage.activity( Uri.parse(data.getDataString()))
//                                    .start(getContext(),this);
                    } catch (final Exception e) {
                    }
                    break;
                case 1:
                    try {
                        //  new ImageCompressionAsyncTask().execute(imageUri.toString()).get();
//                            CropImage.activity(imageUri)
//                                    .start(getContext(),this);

                        UCrop.of(imageUri, Uri.fromFile(new File(UserProfileActivity.this.getCacheDir(), destinationFileName)))
                                .withAspectRatio(4, 4)
                                .withMaxResultSize(100, 100)
                                .start(UserProfileActivity.this);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private String getRealPathFromURI(final String contentURI) {

        final Uri contentUri = Uri.parse(contentURI);
        final Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null)
            return contentUri.getPath();
        else {
            cursor.moveToFirst();
            final int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void uploadFile(Uri filePath, String riversRefPath, final boolean isProfileImage) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading));
            progressDialog.show();
            try {
                compressedImage = compressImage(filePath.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            progressBar.setVisibility(View.VISIBLE);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            FirebaseOptions opts = FirebaseApp.getInstance().getOptions();
            Log.i("", "Bucket = " + opts.getStorageBucket());

            StorageReference storageReference = storage.getReferenceFromUrl("gs://jallikattu-1a841.appspot.com/" );
            System.out.println("pathhhhh"+"images/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, UserProfileActivity.this)+"/"+new Date().getTime() + ".jpg");
             StorageReference riversRef = storageReference.child("images/galleryImages/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, UserProfileActivity.this)+"/"+new Date().getTime() + ".jpg");
            riversRef.putFile(Uri.fromFile(new File(compressedImage)))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfulls
                            //hiding the progress dialog
                            // progressBar.setVisibility(View.GONE);

                            //and displaying a success toast


                            Log.d("Durai_FILE", "onSuccess: " + taskSnapshot.getDownloadUrl());
                            updateMediaToProfile(taskSnapshot.getDownloadUrl().toString(), isProfileImage);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
//                            progressDialog.dismiss();
                            //progressBar.setVisibility(View.GONE);

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("DURAIII", "onFailure: " + exception.getMessage());

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            //       progressBar.setProgress((int) progress);
//                            txtProgress.setText(((int) progress));
                            Log.d("DURAIIII", "onProgress: " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    private void updateMediaToProfile(String newFileUrl, boolean isProfileImage) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Media m = new Media();
        m.setMediaUrl(newFileUrl);
//        m.set(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,this));
        m.setOwnerID(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this));

        DatabaseReference ref;
        if (isProfileImage) {
            database.getReference("profile/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this) + "/profileImageUrl").setValue(newFileUrl);
        } else {
            ref = database.getReference( "profile_gallery/"+SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,UserProfileActivity.this) ).push();
            m.setId(ref.getKey());
            m.setDate(new Date().getTime());
            ref.setValue(m, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    if (databaseError != null)
                        Toast.makeText(getApplicationContext(), getString(R.string.try_again), Toast.LENGTH_LONG).show();
                    else {
//                       if(tabAdapter.getItem(1)!=null &&tabAdapter.getItem(1) instanceof  ProfileGalleryFragment){
//                           ((ProfileGalleryFragment) tabAdapter.getItem(1)).getBattingLeaderboard(null,null,true);
                        Toast.makeText(getApplicationContext(), getString(R.string.upload_success), Toast.LENGTH_LONG).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });

        }

    }



    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }





//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

}
