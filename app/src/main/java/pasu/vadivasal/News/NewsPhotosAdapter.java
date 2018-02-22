package pasu.vadivasal.News;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.Media;

public class NewsPhotosAdapter extends BaseQuickAdapter<Media, BaseViewHolder> {
    private Context context;
    List<Media> data;

    public NewsPhotosAdapter(List<Media> data, Context context) {
        super(R.layout.raw_news_image, data);
        this.data = data;
        this.context = context;
    }

    protected void convert(BaseViewHolder holder, Media item) {
        ImageView imgBg = (ImageView) holder.getView(R.id.img_bg);
        if (Utils.isEmptyString(item.getMediaUrl())) {
            imgBg.setImageResource(R.mipmap.ic_launcher);
        } else {
            Picasso.with(this.context).load(item.getMediaUrl() + AppConstants.THUMB_IMAGE).error((int) R.mipmap.ic_launcher).fit().centerCrop().into(imgBg);
        }
    }
}
