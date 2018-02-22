//package pasu.vadivasal;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.squareup.picasso.Picasso;
//
//import pasu.vadivasal.globalModle.Media;
//
//
//public class ViewPagerAdapter extends FragmentPagerAdapter {
//    Context mContext;
//    Media[] items;
//    int position;
//
//    public ViewPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
////public ViewPagerAdapter(Context context){
////
////}
//    ViewPagerAdapter(FragmentManager fm,Context context, Media[] items, int position) {
//        this.items = items;
//        this.mContext = context;
//        this.position = position;
//    }
//
//    @Override
//    public int getCount() {
//        return items.length;
//    }
////
////    @Override
////    public boolean isViewFromObject(View v, Object obj) {
////        return v == (obj);
////    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment f=new PhotoViewFragment();
//        Bundle b=new Bundle();
//        b.putString("imageUrl",items[position].getMediaUrl());
//        f.setArguments(b);
//        return  f;
//    }
////
////    @Override
////    public Object instantiateItem(ViewGroup container, int i) {
//////        ImageView mImageView = new ImageView(mContext);
//////        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//////        System.out.println("showing image of"+items[i].getMediaUrl());
//////        Picasso.with(mContext)
//////                .load(items[i].getMediaUrl())
//////                .into(mImageView);
//////        container.addView(mImageView);//(container).addView(mImageView,0);
//////        return mImageView;
//////        Fragment f=new PhotoViewFragment();
//////        Bundle b=new Bundle();
//////        b.putString("imageUrl",items[i].getMediaUrl());
//////        f.setArguments(b);
//////        return  f;
////    }
////
//
//
//    @Override
//    public void destroyItem(ViewGroup container, int i, Object obj) {
//        (container).removeView((ImageView) obj);
//    }
//}
