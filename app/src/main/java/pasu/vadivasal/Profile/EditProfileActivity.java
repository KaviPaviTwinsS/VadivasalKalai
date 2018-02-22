package pasu.vadivasal.Profile;

/**
 * Created by developer on 14/10/17.
 */

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.globalModle.ProfileData;
import pasu.vadivasal.photographyInsta.CommentsAutoActivity;
import pasu.vadivasal.regLogin.VerificationActivity;

/**
 * Created by developer on 26/9/17.
 */

public class EditProfileActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 228;
    TextView sav_txt, dob, text_logout, phnum, role_txt;
    EditText name, city, email, about;
    String name_str, phnum_str, dob_str, city_str, email_str;
    String name_str_old, phnum_str_old, dob_str_old, city_str_old, email_str_old;
    ViewGroup imglay;
    private static int IMG_RESULT = 1;
    String ImageDecode;
    ImageView imageViewLoad;

    Intent intent;
    private static int RESULT_LOAD_IMG = 1;
    private DatabaseReference myRef;
    private ProfileData profileData;
    private String about_str, about_str_old;
    private SimpleDateFormat dateFormatter;
    private SlideDateTimeListener listener;
    private String destinationFileName = "profileImage";
    private Uri imageUri;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raw_edit_profile);
        listener = new DateTimeListner();
        this.dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        sav_txt = (TextView) findViewById(R.id.save_txt);
        text_logout = (TextView) findViewById(R.id.text_logout);
        name = (EditText) findViewById(R.id.name_txt);
        phnum = findViewById(R.id.phnumb_txt);
        role_txt = findViewById(R.id.role_txt);
        dob = (TextView) findViewById(R.id.dob_txt);
        city = (EditText) findViewById(R.id.city_txt);
        email = (EditText) findViewById(R.id.email_txt);
        imglay = (RelativeLayout) findViewById(R.id.img_lay);
        imageViewLoad = (ImageView) findViewById(R.id.profile_image);
        about = (EditText) findViewById(R.id.desc_text);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDateTimeField();
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        ScheduleNewGame.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH),
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd.setAccentColor(ContextCompat.getColor(getActivity(), R.color.black));
//                dpd.setCancelColor(ContextCompat.getColor(getActivity(), R.color.black));
//                dpd.setOkColor(ContextCompat.getColor(getActivity(), R.color.black));
//                dpd.setMinDate(now);
//                // dpd.setc(ContextCompat.getColor(getActivity(),R.color.black));
//                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SessionSave.saveSession(Appconstants.USER_PROFILE_PHONE_NUMBER, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.LOGIN_TYPE, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.USER_PROFILE_IMAGE, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.USER_PROFILE_NAME, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.USER_PROFILE_GOOGLE_ID, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.USER_PROFILE_EMAIL_ID, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.USER_PROFILE_PHONE_NUMBER, "", EditProfileActivity.this);
                SessionSave.saveSession(Appconstants.LOGIN_TYPE, 0, EditProfileActivity.this);
                startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
                finish();
            }
        });
        if (getIntent() != null)
            if (getIntent().getStringExtra("data") != null) {
                profileData = Utils.fromJson(getIntent().getStringExtra("data"), ProfileData.class);
                name_str_old = profileData.getName();
                phnum_str_old = String.valueOf(profileData.getPhonenumber());
                dob_str_old = String.valueOf(profileData.getDob());
                city_str_old = profileData.getCity();
                email_str_old = profileData.getMail();
                about_str_old = profileData.getDescription();
                Picasso.with(this).load(profileData.getProfileImageUrl()).placeholder(R.drawable.user).error(R.drawable.user).into(imageViewLoad);
                setProfileData();
            }
        sav_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name_str = name.getText().toString();
                phnum_str = phnum.getText().toString();
                dob_str = dob.getText().toString();
                city_str = city.getText().toString();
                email_str = email.getText().toString();
                about_str = about.getText().toString();

                if (name_str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter your  name", Toast.LENGTH_LONG).show();
                } else if (phnum_str.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Enter your phnum", Toast.LENGTH_LONG).show();

//                } else if (dob_str.length() == 0) {
//                    Toast.makeText(getApplicationContext(), "Enter your dob", Toast.LENGTH_LONG).show();

                } else if (city_str.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter your city", Toast.LENGTH_LONG).show();

                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                } else {


                    saveProfile();

                }
            }
        });

        imglay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
//                System.out.println("llk" + RESULT_LOAD_IMG);

                try {
                    System.out.println("MaterialUpimgPlayer1");
                    if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        System.out.println("MaterialUpimgPlayer2");
                        ActivityCompat.requestPermissions(EditProfileActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                        // return;
                    } else {
                        System.out.println("MaterialUpimgPlayer3");
                        getCamera();
                    }
                } catch (Exception e) {

                    // TODO: handle exception
                    e.printStackTrace();
                }

            }

        });


    }

    private void getCamera() {
//        new android.app.AlertDialog.Builder(this).setMessage("" + getResources().getString(R.string.choose_an_image)).setTitle("" + getResources().getString(R.string.profile_image)).setCancelable(true).setNegativeButton("" + getResources().getString(R.string.gallery), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(final DialogInterface dialog, final int which) {
//                // TODO Auto-generated method stub
//                System.gc();
//                final Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                startActivityForResult(intent, 0);
//                dialog.cancel();
//            }
//        }).setPositiveButton("" + getResources().getString(R.string.camera), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(final DialogInterface dialog, final int which) {
//                // TODO Auto-generated method stub
//                dialog.cancel();
//                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                final File photo = new File(Environment.getExternalStorageDirectory() + "/"+getApplication().getPackageName() + "/img");
//                if (!photo.exists())
//                    photo.mkdirs();
//                final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                final File mediaFile = new File(photo.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
//                imageUri = Uri.fromFile(mediaFile);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, 1);
//            }
//        }).show();

        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 0);
    }

    private void setProfileData() {
        name.setText(name_str_old);
        dob.setText(dob_str_old);
        city.setText(city_str_old);
        phnum.setText(phnum_str_old);
        email.setText(email_str_old);
        about.setText(about_str_old);
        if (profileData.getType() == Appconstants.TYPE_BULL_OWNER)
            role_txt.setText(getString(R.string.register_bull_owner));
        else if (profileData.getType() == Appconstants.TYPE_PLAYER)
            role_txt.setText(getString(R.string.register_tamper));
        else if (profileData.getType() == Appconstants.TYPE_PHOTOGRAPHER)
            role_txt.setText(getString(R.string.register_photographer));
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        new SlideDateTimePicker.Builder(getSupportFragmentManager()).setListener(this.listener).setInitialDate(newCalendar.getTime()).setMaxDate(new Date()).setTheme(R.style.AppTheme).setIndicatorColor(Color.parseColor("#BB4235")).build().show();
    }

    class DateTimeListner extends SlideDateTimeListener {
        DateTimeListner() {
        }

        public void onDateTimeSet(Date date) {

            dob.setText(EditProfileActivity.this.dateFormatter.format(date));
        }

        public void onDateTimeCancel() {
            System.out.println("onDateTimeCancel");
        }
    }

    private void saveProfile() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String profile_id = SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, EditProfileActivity.this);
        System.out.println("getDataaaa" + profile_id + "__" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        if (!name_str.equals(name_str_old))
            database.getReference("profile/" + profile_id + "/name").setValue(name_str);
        if (!dob_str.equals(dob_str_old))
            database.getReference("profile/" + profile_id + "/dob").setValue(dob_str);
        if (!phnum_str.equals(phnum_str_old))
            database.getReference("profile/" + profile_id + "/phonenumber").setValue(Long.parseLong(phnum_str));
        if (!email_str.equals(email_str_old))
            database.getReference("profile/" + profile_id + "/mail").setValue(email_str);
        if (!city_str.equals(city_str_old))
            database.getReference("profile/" + profile_id + "/city").setValue(city_str);
        myRef = database.getReference("profile/" + profile_id + "/description");
//        ProfileData profileData = new ProfileData();
//        profileData.setName(name.getText().toString());
//        profileData.setCity(city.getText().toString());
//        profileData.setMail(email.getText().toString());
//        profileData.setDob(12328346L);
//        profileData.setDescription(email.getText().toString());
//        profileData.setPhonenumber(Long.parseLong(phnum.getText().toString()));
        myRef.setValue(about_str);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    SessionSave.saveSession(Appconstants.USER_PROFILE_NAME,name_str,EditProfileActivity.this);
                    startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
//                    int[] startingLocation = new int[2];
////                    view.getLocationOnScreen(startingLocation);
////                    startingLocation[0] += view.getWidth() / 2;
//                    UserProfileActivity.startUserProfileFromLocation(startingLocation, (AppCompatActivity) EditProfileActivity.this, true, SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,EditProfileActivity.this));
//                    ((AppCompatActivity) EditProfileActivity.this).overridePendingTransition(0, 0);
                    finish();
                    //   Toast.makeText(getApplicationContext(),, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        if (data != null) {
            imageUri = data.getData();
//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imageViewLoad.setImageBitmap(selectedImage);
//                Toast.makeText(EditProfileActivity.this, "picked n seted", Toast.LENGTH_LONG).show();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(EditProfileActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }


            if (requestcode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (resultCode == RESULT_OK) {
                System.gc();
                switch (requestcode) {
                    case 0:
                        try {
                            UCrop uCrop = UCrop.of(Uri.fromFile(new File(getRealPathFromURI(data.getDataString()))), Uri.fromFile(new File(EditProfileActivity.this.getCacheDir(), destinationFileName)))
                                    .withAspectRatio(4, 4)
                                    .withMaxResultSize(500, 500);
                            UCrop.Options options = new UCrop.Options();
                            options.setToolbarColor(ContextCompat.getColor(EditProfileActivity.this, R.color.colorPrimary));
                            options.setStatusBarColor(ContextCompat.getColor(EditProfileActivity.this, R.color.colorPrimary));
                            options.setToolbarWidgetColor(ContextCompat.getColor(EditProfileActivity.this, R.color.colorAccent));
                            uCrop.withOptions(options);
                            uCrop.start(EditProfileActivity.this);
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

                            UCrop.of(imageUri, Uri.fromFile(new File(EditProfileActivity.this.getCacheDir(), destinationFileName)))
                                    .withAspectRatio(4, 4)
                                    .withMaxResultSize(100, 100)
                                    .start(EditProfileActivity.this);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
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

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            // ResultActivity.startWithUri(SampleActivity.this, resultUri);
            File f = new File(resultUri.getPath());
            Picasso.with(EditProfileActivity.this)
                    .invalidate(f);
            Picasso.with(EditProfileActivity.this)
                    .load(f).into(imageViewLoad);
            uploadFile(resultUri, "images/profileimage/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, EditProfileActivity.this) + ".jpg", true);
            System.out.println("Hellow" + resultUri);
        } else {
            // Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadFile(Uri filePath, String riversRefPath, final boolean isProfileImage) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            //progressBar.setVisibility(View.VISIBLE);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            FirebaseOptions opts = FirebaseApp.getInstance().getOptions();
            Log.i("", "Bucket = " + opts.getStorageBucket());

            StorageReference storageReference = storage.getReferenceFromUrl("gs://jallikattu-1a841.appspot.com");
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
            database.getReference("profile/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, EditProfileActivity.this) + "/about/profileImageUrl").setValue(newFileUrl, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    if (databaseError != null)
                        Toast.makeText(getApplicationContext(), getString(R.string.try_again), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), getString(R.string.upload_success), Toast.LENGTH_LONG).show();
                }
            });
            SessionSave.saveSession(Appconstants.USER_PROFILE_IMAGE, newFileUrl, EditProfileActivity.this);
        } else {
            ref = database.getReference("profile/" + SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID, EditProfileActivity.this) + "/media").push();
            m.setId(ref.getKey());
            ref.setValue(m);
        }

    }
}








