package pasu.vadivasal;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.model.PostModel;
import pasu.vadivasal.view.CustomViewPager;

public class ShareAct extends AppCompatActivity {
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 300;
    CustomViewPager mViewPager;
    ViewPagerAdapter adap;
    Media[] media;
    int position;
    TextView left_icon,right_icon;
    private Bitmap bitmapImage;
    private ProgressDialog mProgressDialog;

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mViewPager =  findViewById(R.id.image_view_pager);
        ImageButton backImg =  findViewById(R.id.slideImg);
        ImageButton shareImg=findViewById(R.id.shareImg);
        left_icon=(TextView)findViewById(R.id.left_icon);
        right_icon = (TextView)findViewById(R.id.right_icon);
        backImg.setVisibility(View.VISIBLE);
        shareImg.setVisibility(View.VISIBLE);
        backImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("backCalled");
                finish();
                return true;
            }
        });
        shareImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showProgressDialog();
                    Picasso.with(ShareAct.this)
                            .load(media[mViewPager.getCurrentItem()].getMediaUrl())
                            .into(picassoImageTarget(ShareAct.this, "imageDir", "my_image.jpeg"));

                return true;
            }
        });






//        shareImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            //    startActivity(new Intent(ShareAct.this, GallerySlideAdapter.class));
//
//            }
//        });

        Intent get = getIntent();
        media = Utils.fromJson(get.getStringExtra("image_data"),Media[].class);
        position = get.getIntExtra("pos", 0);
        adap = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adap);
        mViewPager.setCurrentItem(position);
        left_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem()!=0) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }

            }
        });
        right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });
    }
    public void addWaterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Point point = new Point();
        point.set((int) (w/1.5),(int) (h/2.8));
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);


        // Bitmap waterMark =Bitmap.createScaledBitmap( BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_stat_logosmall),(w/6),(h/6),false);

       // canvas.drawBitmap(src,point.x, point.y, null);
        if (result != null){
            if (isStoragePermissionGranted()) {
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), result,"title", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_news_msg) );
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent , "Share"));
            }
        }
//        return result;
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(ShareAct.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<210 upon installation
            Log.v("TAG","Permission is granted");
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
                            addWaterMark(bitmap);
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


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        //public ViewPagerAdapter(Context context){
//
//}
//        ViewPagerAdapter(FragmentManager fm,Context context, Media[] items, int position) {
//            this.items = items;
//            this.mContext = context;
//            this.position = position;
//        }

        @Override
        public int getCount() {
            return media.length;
        }
//
//    @Override
//    public boolean isViewFromObject(View v, Object obj) {
//        return v == (obj);
//    }

        @Override
        public Fragment getItem(int position) {
            Fragment f=new PhotoViewFragment();
            Bundle b=new Bundle();
            b.putString("imageUrl",media[position].getMediaUrl());
            f.setArguments(b);
            return  f;
        }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int i) {
////        ImageView mImageView = new ImageView(mContext);
////        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////        System.out.println("showing image of"+items[i].getMediaUrl());
////        Picasso.with(mContext)
////                .load(items[i].getMediaUrl())
////                .into(mImageView);
////        container.addView(mImageView);//(container).addView(mImageView,0);
////        return mImageView;
////        Fragment f=new PhotoViewFragment();
////        Bundle b=new Bundle();
////        b.putString("imageUrl",items[i].getMediaUrl());
////        f.setArguments(b);
////        return  f;
//    }
//


        @Override
        public void destroyItem(ViewGroup container, int i, Object obj) {
//            (container).removeView((ImageView) obj);
        }
    }












    }
