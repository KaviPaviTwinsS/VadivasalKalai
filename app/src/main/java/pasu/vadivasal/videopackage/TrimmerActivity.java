package pasu.vadivasal.videopackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import pasu.vadivasal.R;
import pasu.vadivasal.photographyInsta.AddtoFirebaseActivity;
import pasu.vadivasal.photographyInsta.AutoLoadingFragment;


public class TrimmerActivity extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {

    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);

        Intent extraIntent = getIntent();
        String path = "";

        if (extraIntent != null) {
            path = extraIntent.getStringExtra(AutoLoadingFragment.EXTRA_VIDEO_PATH);
        }

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading");

        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(10);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setOnK4LVideoListener(this);
            mVideoTrimmer.setDestinationPath("/storage/emulated/0/TrimmedVideos/");
            mVideoTrimmer.setMaxDuration(30);
            mVideoTrimmer.setVideoURI(Uri.parse(path));
            mVideoTrimmer.setVideoInformationVisibility(true);

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mProgressDialog.show();
//    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(TrimmerActivity.this, "Video saved at"+ uri.getPath(), Toast.LENGTH_SHORT).show();
//            }
//        });
        Log.d("Generated Uri",uri.toString());
        Intent intent = new Intent(TrimmerActivity.this, AddtoFirebaseActivity.class);
        intent.putExtra("imagePath", uri.getPath());
        intent.putExtra("flag",1);
        startActivity(intent);
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        mVideoTrimmer.onClickVideoPlayPause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mVideoTrimmer.onClickVideoPlayPause();
            }
        },500);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivity.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
