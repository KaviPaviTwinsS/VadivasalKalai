package pasu.vadivasal.News;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Media;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    List<Media> mediaArrayList = new ArrayList();

    public ImagePagerAdapter(Context context, List<Media> mediaList) {
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mediaArrayList = mediaList;
        this.context = context;
    }

    public int getCount() {
        return this.mediaArrayList.size();
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Media media = (Media) this.mediaArrayList.get(position);
        View rowView = this.mLayoutInflater.inflate(R.layout.raw_news_image, container, false);
        ImageView imgBg = (ImageView) rowView.findViewById(R.id.img_bg);
        if (Utils.isEmptyString(media.getMediaUrl())) {
            imgBg.setImageResource(R.mipmap.ic_launcher);
        } else {
            Picasso.with(this.context).load(media.getMediaUrl() + AppConstants.THUMB_IMAGE).error((int) R.mipmap.ic_launcher).fit().centerCrop().into(imgBg);
        }
        container.addView(rowView);
        return rowView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
