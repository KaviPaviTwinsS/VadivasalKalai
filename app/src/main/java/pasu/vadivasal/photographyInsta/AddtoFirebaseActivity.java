package pasu.vadivasal.photographyInsta;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.emredavarci.circleprogressbar.CircleProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.android.SessionSave;
import pasu.vadivasal.globalModle.Appconstants;
import pasu.vadivasal.model.PostModel;
import pasu.vadivasal.model.Upload;

//public class AddtoFirebaseActivity extends AppCompatActivity {
//    private ImageView imageView;
//    private EditText editTextDescription;
//    private Button btnAdd;
//    private TextView textTitle;
//    private Uri imageUri;
//    //firebase objects
//    private StorageReference storageReference;
//    private DatabaseReference mDatabase;
//    private int flag;
//    private String description;
//    private String videoThumb = "";
//   // private CircleProgressBar progressBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addto_firebase);
//        initViews();
//        if (getIntent().getStringExtra("imagePath") != null) {
//            if (getIntent().getIntExtra("flag", 0) == 1) {
//                textTitle.setText("Selected Video Thumbnail");
//                imageUri = Uri.fromFile(new File(getIntent().getStringExtra("imagePath")));
//                videoThumb = getVideoThumb(getIntent().getStringExtra("imagePath"));
//            } else {
//                textTitle.setText("Selected Image");
//                imageUri = Uri.parse(getIntent().getStringExtra("imagePath"));
//                Log.d("path", getIntent().getStringExtra("imagePath"));
//            }
//            flag = getIntent().getIntExtra("flag", 0);
//
//        }
//    }
//
//    private String getVideoThumb(String videoPath) {
//        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
//        return BitMapToString(thumb);
//    }
//    public String BitMapToString(Bitmap bitmap){
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//        byte [] b=baos.toByteArray();
//        String temp= Base64.encodeToString(b, Base64.DEFAULT);
//        return temp;
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        imageView.setVisibility(View.VISIBLE);
////        if (getIntent().getIntExtra("flag", 0) == 1) {
////            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(String.valueOf(imageUri), MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
////            imageView.setImageBitmap(bitmap);
////        }else {
//            Picasso.with(AddtoFirebaseActivity.this)
//                    .load(imageUri)
//                    .into(imageView);
////        }
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                description = editTextDescription.getText().toString().trim();
//                if (imageUri == null && TextUtils.isEmpty(description)) {
//                    Toast.makeText(AddtoFirebaseActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
//                } else if (imageUri == null) {
//                    Toast.makeText(AddtoFirebaseActivity.this, "Select Image to Upload", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(description)) {
//                    Toast.makeText(AddtoFirebaseActivity.this, "Enter Description", Toast.LENGTH_SHORT).show();
//                } else {
//                    uploadFile();
//                }
//            }
//        });
//    }
//
//    private void initViews() {
//        storageReference = FirebaseStorage.getInstance().getReference();
//        mDatabase = FirebaseDatabase.getInstance().getReference("MyPosts");
//
//        imageView = (ImageView) findViewById(R.id.imageView);
//        textTitle = (TextView) findViewById(R.id.textTitle);
//        editTextDescription = (EditText) findViewById(R.id.editDescription);
//        btnAdd = (Button) findViewById(R.id.btnAdd);
//     //   progressBar = (CircleProgressBar) findViewById(R.id.circleprogressBar);
//
//    }
//
//    private void uploadFile() {
//        //checking if file is available
//        if (imageUri != null) {
//            //displaying progress dialog while image is uploading
//
//        //    progressBar.setVisibility(View.VISIBLE);
//            //getting the storage reference
//            StorageReference sRef = storageReference.child("MyUploads" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//            Log.d("Generated Uri", imageUri.toString());
//
//            //adding the file to reference
//            sRef.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            //displaying success toast
//                            Toast.makeText(getApplicationContext(), "File Uploaded in Storage", Toast.LENGTH_LONG).show();
//
//                            //creating the upload object to store uploaded image details
////                            Upload upload = new Upload(description, taskSnapshot.getDownloadUrl().toString());
//                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                                    //adding an upload to firebase database
//                                    String uploadId = mDatabase.push().getKey();
//                                    Upload upload = new Upload(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,AddtoFirebaseActivity.this),
//                                            description, taskSnapshot.getDownloadUrl().toString(),
//                                            uploadId, System.currentTimeMillis()/1000, flag, videoThumb);
//                                    mDatabase.child(uploadId).setValue(upload, new DatabaseReference.CompletionListener() {
//                                        @Override
//                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                            if (databaseError != null) {
//                                                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
//                                            }else {
//                                                Toast.makeText(getApplicationContext(), "File Updated in Db", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });
//                                    Intent intent = new Intent(AddtoFirebaseActivity.this, MainActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Failed to Update in Db ", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            //progressBar.setVisibility(View.GONE);
//                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            //displaying the upload progress
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                            //progressBar.setProgress((float) progress);
//                            //progressBar.setText(String.valueOf((int) progress));
//                        }
//                    });
//        } else {
//            //display an error if no file is selected
//        }
//    }
//
//    public String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//}

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddtoFirebaseActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText editTextDescription;
    private Button btnAdd;
    private TextView textTitle;
    private Uri imageUri;
    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private int flag;
    private String description;
    private String videoThumb = "";
    private ProgressDialog mProgressDialog;
//    private CircleProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_firebase);
        initViews();
        if (getIntent().getStringExtra("imagePath") != null) {
            if (getIntent().getIntExtra("flag", 0) == 1) {
                textTitle.setText("Selected Video Thumbnail");
                imageUri = Uri.fromFile(new File(getIntent().getStringExtra("imagePath")));
                videoThumb = getVideoThumb(getIntent().getStringExtra("imagePath"));
            } else {
                textTitle.setText("Selected Image");
                imageUri = Uri.parse(getIntent().getStringExtra("imagePath"));
                Log.d("path", getIntent().getStringExtra("imagePath"));
                videoThumb = getImageThumb(imageUri);
            }
            flag = getIntent().getIntExtra("flag", 0);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private String getImageThumb(Uri imagePath) {
        Bitmap bitmap = null;
        final int THUMBSIZE = 10;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(bitmap,
                THUMBSIZE, THUMBSIZE);
        return BitMapToString(ThumbImage);
    }

    public String getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 5, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path).toString();
    }

    private String getVideoThumb(String videoPath) {
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        return BitMapToString(thumb);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 2, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView.setVisibility(View.VISIBLE);
//        if (getIntent().getIntExtra("flag", 0) == 1) {
//            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(String.valueOf(imageUri), MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
//            imageView.setImageBitmap(bitmap);
//        }else {
        Picasso.with(AddtoFirebaseActivity.this)
                .load(imageUri)
                .into(imageView);
//        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = editTextDescription.getText().toString().trim();
                if (imageUri == null && TextUtils.isEmpty(description)) {
                    Toast.makeText(AddtoFirebaseActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                } else if (imageUri == null) {
                    Toast.makeText(AddtoFirebaseActivity.this, "Select Image to Upload", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(description)) {
                    Toast.makeText(AddtoFirebaseActivity.this, "Enter Description", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    private void initViews() {
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("MyPosts");

        imageView = (ImageView) findViewById(R.id.imageView);
        textTitle = (TextView) findViewById(R.id.textTitle);
        editTextDescription = (EditText) findViewById(R.id.editDescription);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        //progressBar = (CircleProgressBar) findViewById(R.id.circleprogressBar);

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


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
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

    private void uploadFile() {
        //checking if file is available
        String compressedImage = "";
        if (imageUri != null) {
            showProgressDialog();
            try {
                compressedImage = compressImage(imageUri.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String child="Posts/MyUploads/"+SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,AddtoFirebaseActivity.this) + System.currentTimeMillis() + "." + getFileExtension(imageUri);
            StorageReference sRef = storageReference.child(child);

            Log.d("Generated Uri", child);

            //adding the file to reference
            sRef.putFile(Uri.fromFile(new File(compressedImage)))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //displaying success toast
                            // Toast.makeText(getApplicationContext(), "File Uploaded in Storage", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
//                            Upload upload = new Upload(description, taskSnapshot.getDownloadUrl().toString());
                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                    //adding an upload to firebase database
                                    String uploadId = mDatabase.push().getKey();
                                    PostModel upload = new PostModel(SessionSave.getSession(Appconstants.USER_PROFILE_GOOGLE_ID,AddtoFirebaseActivity.this),
                                            description, taskSnapshot.getDownloadUrl().toString(),
                                            uploadId, System.currentTimeMillis() / 1000, flag, videoThumb, SessionSave.getSession(Appconstants.USER_PROFILE_NAME, AddtoFirebaseActivity.this), SessionSave.getSession(Appconstants.USER_PROFILE_IMAGE, AddtoFirebaseActivity.this));
                                    mDatabase.child(uploadId).setValue(upload, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError != null) {
                                                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                            } else {
                                                hideProgressDialog();
                                                Toast.makeText(getApplicationContext(), getString(R.string.upload_success), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    Intent intent = new Intent(AddtoFirebaseActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    hideProgressDialog();
                                    Toast.makeText(getApplicationContext(), getString(R.string.try_again), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
//                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

//                            progressBar.setProgress((float) progress);
//                            progressBar.setText(String.valueOf((int) progress));
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
