package pasu.vadivasal.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;

import java.util.List;

import pasu.vadivasal.Profile.UserProfileActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.ShareAct;
import pasu.vadivasal.adapter.base.BaseMultiItemQuickAdapter;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.globalModle.Media;
import pasu.vadivasal.matchList.MatchListMainFragment;
import pasu.vadivasal.model.PlayerDash;
import pasu.vadivasal.videopackage.VideoActivityMain;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class DashboardAdapter extends BaseMultiItemQuickAdapter<BaseDashboardMultiItem, BaseViewHolder> {
    Context context;
    private LinearLayoutManager mLayoutManager;

    public DashboardAdapter(Context context, List data) {
        super(data);
        this.context = context;
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(11, R.layout.raw_dashboard_match);
        addItemType(20, R.layout.raw_dashboard_other);
        addItemType(99, R.layout.raw_dashboard_media);
        addItemType(MultipleItem.IMG, R.layout.raw_dashboard_other);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDashboardMultiItem item) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mContext, 0, false);
        BaseQuickAdapter adapter;
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv1, item.getDescription());
                break;
            case MultipleItem.IMG:
                // helper.setImageResource(R.id.iv, R.mipmap.ic_launcher_round);
                RecyclerView vpMatchPhotoViewer = (RecyclerView) helper.getView(R.id.dataViewer);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvDescription, item.getDescription());
//                if (vpMatchPhotoViewer != null) {
//                    adapter = item.getAdapter();
//                    System.out.println("photoAdapter" + vpMatchPhotoViewer);
//                    if (adapter != null) {
//                        vpMatchPhotoViewer.setAdapter(adapter);
//                        return;
//                    }
//                    return;
//                }


                vpMatchPhotoViewer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                vpMatchPhotoViewer.setHasFixedSize(true);
//                rv.setNestedScrollingEnabled(false);
                vpMatchPhotoViewer.setItemAnimator(new DefaultItemAnimator());
                DashboardPhotoAdapter adapters = new DashboardPhotoAdapter(context, (List<Media>) item.getModelList(), true);
                vpMatchPhotoViewer.setAdapter(adapters);
                new GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(vpMatchPhotoViewer);
                break;
//            case MultipleItem.IMG_TEXT:
//                switch (helper.getLayoutPosition() % 2) {
////                    case MultipleItem.IMG:
////                        // helper.setImageResource(R.id.iv, R.mipmap.ic_launcher_round);
////                        RecyclerView vpMatchPhotoViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
////                        helper.setText(R.id.tvTitle, item.getTitle());
////                        helper.setText(R.id.tvDescription, item.getDescription());
////                        if (vpMatchPhotoViewer != null) {
////                            adapter = item.getAdapter();
////                            if (adapter != null) {
////                                vpMatchPhotoViewer.setAdapter(adapter);
////                                return;
////                            }
////                            return;
////                        }
////                        break;
//                    case 20:
////                        helper.setImageResource(R.id.iv, R.mipmap.ic_launcher_round);
//                        RecyclerView vpMatchDataViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
//                        helper.setText(R.id.tvTitle, item.getTitle());
//                        helper.setText(R.id.tvDescription, item.getDescription());
//                        if (vpMatchDataViewer != null) {
//                            adapter = item.getAdapter();
//                            if (adapter != null) {
//                                vpMatchDataViewer.setAdapter(adapter);
//                                return;
//                            }
//                            return;
//
//             }
//                        break;
//
//                }
//                break;
            case 11:

                RecyclerView vpMatchDataViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvDescription, item.getDescription());


                mLayoutManager =
                        new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                vpMatchDataViewer.setLayoutManager(mLayoutManager);
                vpMatchDataViewer.setHasFixedSize(true);
//                rv.setNestedScrollingEnabled(false);
                vpMatchDataViewer.setItemAnimator(new DefaultItemAnimator());
                HorizontalAdapter adaptersss = new HorizontalAdapter(context, item.getModelList(), 0);
                vpMatchDataViewer.setAdapter(adaptersss);
                GravityPagerSnapHelper gravitySnapHelpers = new GravityPagerSnapHelper(Gravity.START);
                gravitySnapHelpers.attachToRecyclerView(vpMatchDataViewer);
                vpMatchDataViewer.setLayoutManager(mLayoutManager);

//                 if (vpMatchDataViewer != null) {
                //   adapter = item.getAdapter();
//                if (adapter != null) {
//                    vpMatchDataViewer.setAdapter(adapter);
//                    return;
//                }
//                return;
//        }
//                RecyclerView rvMatchDataViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
//                rvMatchDataViewer.setLayoutManager(layoutManager);
//                rvMatchDataViewer.setOnFlingListener(null);
//                new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvMatchDataViewer);
//                rvMatchDataViewer.setAdapter(((DashboardMatchItem)helper).g);
                View btnMore = helper.getView(R.id.btnMore);
                View tvTitle = helper.getView(R.id.rel_desc);
                btnMore.setOnClickListener(new ClickListner());
//                tvTitle.setOnClickListener(new C07563());
//                rvMatchDataViewer.addOnItemTouchListener(new C07574());
                break;


            case 20:
                RecyclerView rvDataViewer = (RecyclerView) helper.getView(R.id.dataViewer);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvDescription, item.getDescription());
//                if (rvDataViewer != null) {
//                    adapter = item.getAdapter();
//                    if (adapter != null) {
//                        rvDataViewer.setAdapter(adapter);
//                        return;
//                    }
//                    return;
//                }

                rvDataViewer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                rvDataViewer.setHasFixedSize(true);
//                rv.setNestedScrollingEnabled(false);
                rvDataViewer.setItemAnimator(new DefaultItemAnimator());
                DashboardPlayerAdapter adapterss = new DashboardPlayerAdapter(context, (List<PlayerDash>) item.getModelList());
                rvDataViewer.setAdapter(adapterss);
                rvDataViewer.setOnFlingListener(null);
                new GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(rvDataViewer);

//                rvDataViewer.setLayoutManager(layoutManager);
//                rvDataViewer.setOnFlingListener(null);
//                new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvDataViewer);
//                rvDataViewer.setAdapter(((DashboardMatchItem)helper).getModelList());
//                btnMore.setOnClickListener(new C07552());
//                tvTitle.setOnClickListener(new C07563());
//                rvDataViewer.addOnItemTouchListener(new C07574());

                break;
            case 99:
                RecyclerView vpVideoDataViewer = (RecyclerView) helper.getView(R.id.mediaDataViewer);
                helper.setGone(R.id.tvTitle, false);
                helper.setGone(R.id.tvDescription, false);
                vpVideoDataViewer.setOnFlingListener(null);
                vpVideoDataViewer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                vpVideoDataViewer.setHasFixedSize(true);
                vpVideoDataViewer.setItemAnimator(new DefaultItemAnimator());
                vpVideoDataViewer.setAdapter(new DashboardVideoAdapter(context, (List<Media>) item.getModelList(), true));
                new GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(vpVideoDataViewer);

        }
    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mContext, 0, false);
        final BaseViewHolder baseViewHolder;
        switch (viewType) {

            case 11:
                RecyclerView rvMatchDataViewer = (RecyclerView) viewHolder.getView(R.id.matchDataViewer);
                rvMatchDataViewer.setLayoutManager(layoutManager);
                rvMatchDataViewer.setOnFlingListener(null);
                // new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvMatchDataViewer);
                View btnMore = viewHolder.getView(R.id.btnMore);
                View tvTitle = viewHolder.getView(R.id.rel_desc);
//                btnMore.setOnClickListener(new C07552());
//                tvTitle.setOnClickListener(new C07563());
//                rvMatchDataViewer.addOnItemTouchListener(new C07574());
                break;
            case MultipleItem.IMG:
                RecyclerView rvPhotoDataViewer = (RecyclerView) viewHolder.getView(R.id.dataViewer);
                rvPhotoDataViewer.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
                rvPhotoDataViewer.setOnFlingListener(null);
                rvPhotoDataViewer.addOnItemTouchListener(new pasu.vadivasal.adapter.base.listener.OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        String stockArr = pasu.vadivasal.android.Utils.toString(adapter.getData());
                        Intent intent = new Intent(context, ShareAct.class);
                        intent.putExtra("image_data", stockArr);
                        intent.putExtra("pos", position);
                        context.startActivity(intent);

                    }
                });
                break;
            case 20:
                RecyclerView rvSuperPlayerDataViewer = (RecyclerView) viewHolder.getView(R.id.dataViewer);
                rvSuperPlayerDataViewer.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
                rvSuperPlayerDataViewer.setOnFlingListener(null);
                //   new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvSuperPlayerDataViewer);
                baseViewHolder = viewHolder;
                rvSuperPlayerDataViewer.addOnItemTouchListener(new pasu.vadivasal.adapter.base.listener.OnItemClickListener() {
                    public void onSimpleItemClick(BaseQuickAdapter adapter, final View view, int i) {
//                        if (DashboardAdapter.this.dashboardItemClickListener != null) {
//                            DashboardAdapter.this.dashboardItemClickListener.onSuperPlayerClick((Player) ((DashboardSuperPlayerAdapter) adapter).getItem(i), baseViewHolder.itemView);
//                        }
//                        System.out.println("PlayerId" + ((PlayerDash) adapter.getItem(i)).getpID());
//                        Intent id = new Intent(mContext, MaterialUpConceptActivity.class);
//                        id.putExtra("isprofile", false);
//                        id.putExtra("id", ((PlayerDash) adapter.getItem(i)).getpID());
//                        mContext.startActivity(id);
                        final String profile_id = ((PlayerDash) adapter.getItem(i)).getpID();

                        final String profile_name=((PlayerDash) adapter.getItem(i)).getName();
                        final String profile_url=((PlayerDash) adapter.getItem(i)).getImageUrl();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int[] startingLocation = new int[2];
                                view.getLocationOnScreen(startingLocation);
                                startingLocation[0] += view.getWidth() / 2;
                                //  startingLocation[1]=500;
                                Bundle b = new Bundle();


                                UserProfileActivity.startUserProfileFromLocation(startingLocation, (AppCompatActivity) context, false, profile_id,profile_name,profile_url);
                                ((AppCompatActivity) context).overridePendingTransition(0, 0);
                            }
                        }, 100);


                        // mContext.startActivity(new Intent(mContext, MaterialUpConceptActivity.class));
                    }
                });
                break;

            case 99:
                RecyclerView rvVideoDataViewer = (RecyclerView) viewHolder.getView(R.id.mediaDataViewer);
                rvVideoDataViewer.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
                rvVideoDataViewer.setOnFlingListener(null);
                viewHolder.getView(R.id.btnMore).setVisibility(View.GONE);
//                new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvVideoDataViewer);

                rvVideoDataViewer.addOnItemTouchListener(new pasu.vadivasal.adapter.base.listener.OnItemClickListener() {
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
//                        if (DashboardAdapter.this.dashboardItemClickListener != null) {
//                            DashboardAdapter.this.dashboardItemClickListener.onSuperPlayerClick((Player) ((DashboardSuperPlayerAdapter) adapter).getItem(i), baseViewHolder.itemView);
//                        }

                        System.out.println("Video__"+adapter.getData());

                        Media[] jsonArray = new Media[adapter.getData().size()];
                        for (int v = 0; v < adapter.getData().size(); v++) {
                            Media media = (Media) adapter.getData().get(v);
//                            media.setMediaUrl((Media) adapter.getData().get(v));
                            jsonArray[v] = media;
                        }
                        String s = pasu.vadivasal.android.Utils.toString(jsonArray);
                        Intent ss = new Intent(mContext, VideoActivityMain.class);
                        ss.putExtra("videos", s);
                        ss.putExtra("toplay", i);
                        mContext.startActivity(ss);
                    }
                });

//                rvVideoDataViewer.addOnItemTouchListener(new OnItemClickListener() {
//                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
//                        if (DashboardAdapter.this.dashboardItemClickListener != null) {
//                            DashboardAdapter.this.dashboardItemClickListener.onVideoClick(i);
//                        }
//                    }
//
//                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                        DashboardAdapter.this.dashboardItemClickListener.onVideoShareClick(position);
//                    }
//                });
                break;

        }
        return viewHolder;
    }

    private class ClickListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction().add(R.id.main_frag, new MatchListMainFragment()).addToBackStack(null).commit();
        }
    }
}
