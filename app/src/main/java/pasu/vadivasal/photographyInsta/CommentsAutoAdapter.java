package pasu.vadivasal.photographyInsta;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.android.RoundedTransformation;
import pasu.vadivasal.globalModle.Comments_Model;

/**
 * Created by developer on 20/12/17.
 */

public class CommentsAutoAdapter extends BaseQuickAdapter<Comments_Model, BaseViewHolder> {
    ImageView ivUserAvatar;
    TextView tvComment;
    TextView tvUser;
    private Context context;
    private int avatarSize ;
    public CommentsAutoAdapter(FragmentActivity activity, int item_feed, List<Comments_Model> mList) {
        super(item_feed, mList);
        context = activity;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final Comments_Model cModel) {
        ivUserAvatar = helper.getView(R.id.ivUserAvatar);
        tvComment = helper.getView(R.id.tvComment);
        tvUser = helper.getView(R.id.tvUser);

        tvComment.setText(cModel.comment);
        tvUser.setText(cModel.name);

        Picasso.with(context)
                .load(cModel.imageUrl)
                .centerCrop()
                .resize(avatarSize, avatarSize)
                .transform(new RoundedTransformation())
                .into(ivUserAvatar);
    }
}