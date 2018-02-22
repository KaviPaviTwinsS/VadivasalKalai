package pasu.vadivasal.Profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import pasu.vadivasal.R;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.globalModle.ProfileData;
import pasu.vadivasal.regLogin.SocialLoginCustom;

public class MaterialUpConceptActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 122;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    private boolean selfProfile;
    private ValueEventListener valueEventListener;
    private Query myRef;
    private TabsAdapter tabAdapter;
    private CoordinatorLayout profile_lay;
    ArrayList<Media> mediaData = new ArrayList<>();
    private TextView profile_following_count, profile_followers_count, btnFollow;
    String profile_id;
    private String profile_name = "";
    private String followId = "";
    private ValueEventListener followListner;
    private ImageView imgPlayer;
    private Uri imageUri;
    private String destinationFileName = "profileImage";
    ImageView edit_profile;
    private ProfileData datav;
    LinearLayout no_data_lay;
    ProgressBar progress_bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
        profile_lay = (CoordinatorLayout) findViewById(R.id.profile_lay);
        profile_following_count = (TextView) findViewById(R.id.profile_following_count);
        profile_followers_count = (TextView) findViewById(R.id.profile_followers_count);
        btnFollow = (TextView) findViewById(R.id.btnFollow);
        no_data_lay = (LinearLayout) findViewById(R.id.no_data_lay);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        imgPlayer = (ImageView) findViewById(R.id.imgPlayer);
        edit_profile = (ImageView) findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datav != null) {
                    String ss = Utils.toString(datav);
                    Intent i = new Intent(MaterialUpConceptActivity.this, EditProfileActivity.class);
                    i.putExtra("data", ss);
                    startActivity(i);
                }
            }
        });
//        imgPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    System.out.println("MaterialUpimgPlayer1");
//                    if (ActivityCompat.checkSelfPermission(MaterialUpConceptActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                            || ActivityCompat.checkSelfPermission(MaterialUpConceptActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        System.out.println("MaterialUpimgPlayer2");
//                        ActivityCompat.requestPermissions(MaterialUpConceptActivity.this,
//                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                MY_PERMISSIONS_REQUEST_CAMERA);
//                        // return;
//                    } else {
//                        System.out.println("MaterialUpimgPlayer3");
//                        getCamera();
//                    }
//                } catch (Exception e) {
//
//                    // TODO: handle exception
//                    e.printStackTrace();
//                }
//            }
//        });

        //  Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
//        toolbar.setTitle("Thambidurai");
//        toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnFollow.getText().toString().equals(getString(R.string.follow)))
                    followUnFollow(true);
                else
                    confirmUnfollow();
            }
        });
        findViewById(R.id.add_media).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        if (getIntent() != null) {
            selfProfile = getIntent().getBooleanExtra("isprofile", false);
            profile_id = getIntent().getStringExtra("id");
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null && profile_id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            selfProfile = true;
        else
            selfProfile = false;
        if (selfProfile) {
            btnFollow.setVisibility(View.GONE);
            edit_profile.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.add_media).setVisibility(View.GONE);
            edit_profile.setVisibility(View.GONE);
        }
        //if (selfProfile) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getData();
        } else {
            startActivity(new Intent(MaterialUpConceptActivity.this, SocialLoginCustom.class));
        }
        //}
        followListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    btnFollow.setText(getString(R.string.following));
                    btnFollow.setBackgroundColor(ContextCompat.getColor(MaterialUpConceptActivity.this, R.color.divider));
                }
//                else {
//                    btnFollow.setText(getString(R.string.follow));
//                    btnFollow.setBackgroundColor(ContextCompat.getColor(MaterialUpConceptActivity.this, R.color.colorAccent));
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();
        tabAdapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);


        /** Dummy data **/
//        ProfileData data =new ProfileData();
//        data.setName("Nagarajan S");
//        data.setDob(System.currentTimeMillis() % 1000);
//        data.setDescription(getString(R.string.dummy_bio));
//        ArrayList<Integer> datasss=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            datasss.add(i);
//        }
//        data.setFollowers(datasss);
//        data.setFollowings(datasss);
//        data.setMail("nagarajan929@gmail.com");
//        data.setPhonenumber(8667730776L);
//        data.setCity("Madurai");
//        data.setPlayedTeam("Alanganallur,Palamedu & Kangayam");
////
//if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("profile/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        myRef.setValue(data);}


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("selected*&^" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void getCamera() {
        new android.app.AlertDialog.Builder(this).setMessage("" + getResources().getString(R.string.choose_an_image)).setTitle("" + getResources().getString(R.string.profile_image)).setCancelable(true).setNegativeButton("" + getResources().getString(R.string.gallery), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                // TODO Auto-generated method stub
                System.gc();
                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 0);
                dialog.cancel();
            }
        }).setPositiveButton("" + getResources().getString(R.string.camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final File photo = new File(Environment.getExternalStorageDirectory() + getApplication().getPackageName() + "/img");
                if (!photo.exists())
                    photo.mkdirs();
                final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                final File mediaFile = new File(photo.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                imageUri = Uri.fromFile(mediaFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        }).show();
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            // ResultActivity.startWithUri(SampleActivity.this, resultUri);
            File f = new File(resultUri.getPath());
            Picasso.with(MaterialUpConceptActivity.this)
                    .invalidate(f);
            Picasso.with(MaterialUpConceptActivity.this)
                    .load(f).into(imgPlayer);
            uploadFile(resultUri, "images/profileimage/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg", true);
            System.out.println("Hellow" + resultUri);
        } else {
            // Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        if (requestcode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadFile(data.getData(), "images/mp" + new Random().nextInt(5000) + ".jpg", false);
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
                        UCrop uCrop = UCrop.of(Uri.fromFile(new File(getRealPathFromURI(data.getDataString()))), Uri.fromFile(new File(MaterialUpConceptActivity.this.getCacheDir(), destinationFileName)))
                                .withAspectRatio(4, 4)
                                .withMaxResultSize(500, 500);
                        UCrop.Options options = new UCrop.Options();
                        options.setToolbarColor(ContextCompat.getColor(MaterialUpConceptActivity.this, R.color.colorPrimary));
                        options.setStatusBarColor(ContextCompat.getColor(MaterialUpConceptActivity.this, R.color.colorPrimary));
                        options.setToolbarWidgetColor(ContextCompat.getColor(MaterialUpConceptActivity.this, R.color.colorAccent));
                        uCrop.withOptions(options);
                        uCrop.start(MaterialUpConceptActivity.this);
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

                        UCrop.of(imageUri, Uri.fromFile(new File(MaterialUpConceptActivity.this.getCacheDir(), destinationFileName)))
                                .withAspectRatio(4, 4)
                                .withMaxResultSize(100, 100)
                                .start(MaterialUpConceptActivity.this);
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void uploadFile(Uri filePath, String riversRefPath, final boolean isProfileImage) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading");
//            progressDialog.show();
            //progressBar.setVisibility(View.VISIBLE);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            FirebaseOptions opts = FirebaseApp.getInstance().getOptions();
            Log.i("", "Bucket = " + opts.getStorageBucket());

            StorageReference storageReference = storage.getReferenceFromUrl("gs://ilovecricket-5c636.appspot.com");
            StorageReference riversRef = storageReference.child(riversRefPath);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfulls
                            //hiding the progress dialog
                            // progressBar.setVisibility(View.GONE);

                            //and displaying a success toast


                            Log.d("Durai_FILE", "onSuccess: " + taskSnapshot.getDownloadUrl());
                            updateMediaToProfile(taskSnapshot.getDownloadUrl().toString(), isProfileImage);
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
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
        m.setOwnerID(FirebaseAuth.getInstance().getCurrentUser().getUid());

        DatabaseReference ref;
        if (isProfileImage) {
            database.getReference("profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profileImageUrl").setValue(newFileUrl);
        } else {
            ref = database.getReference("profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/media").push();
            m.setId(ref.getKey());
            ref.setValue(m);
        }

    }

    private void followUnFollow(boolean isFollow) {
        if (isFollow) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference followRef = database.getReference("profile/" +profile_id + "/followings").push();
            followRef.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            followRef.addValueEventListener(followListner);

        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            database.getReference("profile/" +profile_id+ "/followings/" + followId).removeValue();
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
        System.out.println("getDataaaa" + profile_id + "__" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef = database.getReference("profile/" + profile_id);
        Query queryRef;

        queryRef = myRef.orderByKey()
                .limitToLast(10);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progress_bar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    profile_lay.setVisibility(View.VISIBLE);
                    datav = dataSnapshot.getValue(ProfileData.class);
                    System.out.println("came.." + datav.getName() + "___" + datav.getProfileImageUrl());
                    Picasso.with(MaterialUpConceptActivity.this).load(datav.getProfileImageUrl()).into(imgPlayer);
                    profile_name = datav.getName();
                    tabAdapter.setProfileData(datav);
                    if (datav.getFollowings() != null) {
                        profile_following_count.setText("" + datav.getFollowings().size());
                        ArrayList<String> ss = new ArrayList<String>(datav.getFollowings().keySet());
                        for (int i = 0; i < datav.getFollowings().size(); i++) {
                            if (datav.getFollowings().get(ss.get(i)).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                followId = ss.get(i);
                                DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/followings/" + followId);
                                followRef.addValueEventListener(followListner);
                                btnFollow.setText(getString(R.string.following));
                                btnFollow.setBackgroundColor(ContextCompat.getColor(MaterialUpConceptActivity.this, R.color.divider));
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
//                    DatabaseReference myRef = database.getReference("profile/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/media");
//                    myRef.setValue(s);
                    System.out.println("mediagot" + s.size());
                    if (s != null)
                        tabAdapter.setMatchData(s);

                } else {
                    System.out.println("databaseerrorss");
                    no_data_lay.setVisibility(View.VISIBLE);
                    profile_lay.setVisibility(View.GONE);
                    ((TextView) no_data_lay.findViewById(R.id.error_header)).setText(getString(R.string.hi) + "  " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    ((TextView) no_data_lay.findViewById(R.id.error_long_description)).setText(getString(R.string.player_data_issue));
                    (no_data_lay.findViewById(R.id.btnRetry)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
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
        queryRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public ArrayList<Media> getMediaData() {
        return mediaData;
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, MaterialUpConceptActivity.class));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;
            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    private class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;
        MatchesFragment matchesFragment;
        PlayerInfoFragment playerInfoFragment;


        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        void setMatchData(ArrayList<Media> media) {
            mediaData.addAll(media);
            if (matchesFragment != null)
                matchesFragment.setData(mediaData);
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

            if (i == 0) {
                matchesFragment = new MatchesFragment();
                return matchesFragment;
            } else {
                playerInfoFragment = new PlayerInfoFragment();
                return playerInfoFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Photos";
            else
                return "Profile";
        }
    }


    public void onBackPressed() {
        super.onBackPressed();
        Utils.finishActivitySlide(this);
    }

}
