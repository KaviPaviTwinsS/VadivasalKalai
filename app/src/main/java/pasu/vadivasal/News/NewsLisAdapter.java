package pasu.vadivasal.News;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pasu.vadivasal.MainActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.android.AppConstants;
import pasu.vadivasal.android.Utils;
import pasu.vadivasal.globalModle.News;

public class NewsLisAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    private boolean check;
    private Context context;
    private List<News> itemPlayer;
    int position = -1;

    public NewsLisAdapter(Context context, int layoutResId, List<News> data, boolean check) {
        super(layoutResId, data);
        this.itemPlayer = data;
        this.context = context;
        this.check = check;
    }

    protected void convert(BaseViewHolder holder, final News item) {
        if (this.check) {
//            holder.setText((int) R.id.tvTitle, item.getTitle());
            TextView NewsHeading = (TextView) holder.getView(R.id.NewsHeading);
            TextView newsShortDescription = (TextView) holder.getView(R.id.newsShortDescription);
            TextView news_type = (TextView) holder.getView(R.id.news_type);
            TextView news_time = (TextView) holder.getView(R.id.news_time);
            ImageView newsImage = (ImageView) holder.getView(R.id.newsImage);
            ViewGroup news_item_main_layout = holder.getView(R.id.news_item_main_layout);
            news_item_main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, NewsDetailActivity.class);
                    i.putExtra("isprofile", true);
                    i.putExtra("news_media",item);
                    context.startActivity(i);
                }
            });
//            //holder.setText((int) R.id.tvDate, Utils.changeDateformate(item.getDate(), "yyyy-MM-dd'T'HH:mm:ss", "dd MMMM, yyyy"));
            holder.setText((int) R.id.newsShortDescription, Html.fromHtml(Utils.limitToLength(item.getDescription(),100)));
            holder.setText((int) R.id.NewsHeading, Html.fromHtml(item.getTitle()));
            holder.setText((int) R.id.news_type, Html.fromHtml(item.getMediaType()));
            holder.setText((int) R.id.news_time, Utils.TimeAgo(item.getDate()));
            if(item.getMedia().size()>0)
            Picasso.with(context).load(item.getMedia().get(0).getThumbnail()).into(newsImage);
////            ((ImageView) holder.getView(R.id.imgShare)).setVisibility(View.GONE);
//            ImageView imgBg = (ImageView) holder.getView(R.id.imgMedia);
//            if (Utils.isEmptyString(item.getMediaUrl())) {
//                imgBg.setImageResource(R.mipmap.ic_launcher);
//            } else {
//                Picasso.with(this.context).load(item.getMediaUrl() + AppConstants.THUMB_IMAGE).error((int) R.mipmap.ic_launcher).into(imgBg);
//            }
//            holder.addOnClickListener(R.id.imgShare);
        }
    }

    private void openShareIntent(Context mContext, String newsUrl) {
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("text/plain");
        share.putExtra("android.intent.extra.TEXT", newsUrl);
        mContext.startActivity(Intent.createChooser(share, "Share via"));
    }

    public void setCheckItem(int pos) {
        this.position = pos;
        notifyDataSetChanged();
    }

    public int getNewsId() {
        if (this.position != -1) {
            return ((News) this.itemPlayer.get(this.position)).getNewsId();
        }
        return -1;
    }
}
