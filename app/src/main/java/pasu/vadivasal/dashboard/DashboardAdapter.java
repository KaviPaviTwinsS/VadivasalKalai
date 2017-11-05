package pasu.vadivasal.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pasu.vadivasal.Profile.MaterialUpConceptActivity;
import pasu.vadivasal.R;
import pasu.vadivasal.adapter.base.BaseMultiItemQuickAdapter;
import pasu.vadivasal.adapter.base.BaseQuickAdapter;
import pasu.vadivasal.adapter.base.BaseViewHolder;
import pasu.vadivasal.model.PlayerDash;
import pasu.vadivasal.videopackage.VideoActivityMain;
import pasu.vadivasal.view.GravitySnapHelper;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class DashboardAdapter extends BaseMultiItemQuickAdapter<BaseDashboardMultiItem, BaseViewHolder> {
    Context context;

    public DashboardAdapter(Context context, List data) {
        super(data);
        this.context = context;
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(11, R.layout.raw_dashboard_match);
        addItemType(20, R.layout.raw_dashboard_other);
        addItemType(99, R.layout.raw_dashboard_media);
        addItemType(MultipleItem.IMG_TEXT, R.layout.raw_player_leaderboard);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDashboardMultiItem item) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mContext, 0, false);
        BaseQuickAdapter adapter;
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv1, item.getDescription());
                break;
            case MultipleItem.IMG_TEXT:
                switch (helper.getLayoutPosition() % 2) {
                    case 0:
                        // helper.setImageResource(R.id.iv, R.mipmap.ic_launcher_round);
                        break;
                    case 20:
//                        helper.setImageResource(R.id.iv, R.mipmap.ic_launcher_round);
                        RecyclerView vpMatchDataViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
                        helper.setText(R.id.tvTitle, item.getTitle());
                        helper.setText(R.id.tvDescription, item.getDescription());
                        if (vpMatchDataViewer != null) {
                            adapter = item.getAdapter();
                            if (adapter != null) {
                                vpMatchDataViewer.setAdapter(adapter);
                                return;
                            }
                            return;
                        }
                        break;

                }
                break;
            case 11:

                RecyclerView vpMatchDataViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvDescription, item.getDescription());
                if (vpMatchDataViewer != null) {
                    adapter = item.getAdapter();
                    if (adapter != null) {
                        vpMatchDataViewer.setAdapter(adapter);
                        return;
                    }
                    return;
                }
//                RecyclerView rvMatchDataViewer = (RecyclerView) helper.getView(R.id.matchDataViewer);
//                rvMatchDataViewer.setLayoutManager(layoutManager);
//                rvMatchDataViewer.setOnFlingListener(null);
//                new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvMatchDataViewer);
//                rvMatchDataViewer.setAdapter(((DashboardMatchItem)helper).g);
//                View btnMore = viewHolder.getView(R.id.btnMore);
//                View tvTitle = viewHolder.getView(R.id.rel_desc);
//                btnMore.setOnClickListener(new C07552());
//                tvTitle.setOnClickListener(new C07563());
//                rvMatchDataViewer.addOnItemTouchListener(new C07574());
                break;


            case 20:
                RecyclerView rvDataViewer = (RecyclerView) helper.getView(R.id.dataViewer);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvDescription, item.getDescription());
                if (rvDataViewer != null) {
                    adapter = item.getAdapter();
                    if (adapter != null) {
                        rvDataViewer.setAdapter(adapter);
                        return;
                    }
                    return;
                }
                break;
            case 99:
                RecyclerView vpVideoDataViewer = (RecyclerView) helper.getView(R.id.mediaDataViewer);
                helper.setGone(R.id.tvTitle, false);
                helper.setGone(R.id.tvDescription, false);
                if (vpVideoDataViewer != null) {
                    adapter = item.getAdapter();
                    if (adapter != null) {
                        vpVideoDataViewer.setAdapter(adapter);
                        return;
                    }
                    return;
                }
                return;

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

            case 20:
                RecyclerView rvSuperPlayerDataViewer = (RecyclerView) viewHolder.getView(R.id.dataViewer);
                rvSuperPlayerDataViewer.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
                rvSuperPlayerDataViewer.setOnFlingListener(null);
                //   new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvSuperPlayerDataViewer);
                baseViewHolder = viewHolder;
                rvSuperPlayerDataViewer.addOnItemTouchListener(new pasu.vadivasal.adapter.base.listener.OnItemClickListener() {
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
//                        if (DashboardAdapter.this.dashboardItemClickListener != null) {
//                            DashboardAdapter.this.dashboardItemClickListener.onSuperPlayerClick((Player) ((DashboardSuperPlayerAdapter) adapter).getItem(i), baseViewHolder.itemView);
//                        }
                        System.out.println("PlayerId"+((PlayerDash) adapter.getItem(i)).getpID());
                        Intent id = new Intent(mContext, MaterialUpConceptActivity.class);
                        id.putExtra("isprofile", false);
                        id.putExtra("id", ((PlayerDash) adapter.getItem(i)).getpID());
                        mContext.startActivity(id);
                        // mContext.startActivity(new Intent(mContext, MaterialUpConceptActivity.class));
                    }
                });
                break;
            case 99:
                RecyclerView rvVideoDataViewer = (RecyclerView) viewHolder.getView(R.id.mediaDataViewer);
                rvVideoDataViewer.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
                rvVideoDataViewer.setOnFlingListener(null);
                viewHolder.getView(R.id.btnMore).setVisibility(8);
                new GravitySnapHelper(GravityCompat.START, false).attachToRecyclerView(rvVideoDataViewer);

                rvVideoDataViewer.addOnItemTouchListener(new pasu.vadivasal.adapter.base.listener.OnItemClickListener() {
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int i) {
//                        if (DashboardAdapter.this.dashboardItemClickListener != null) {
//                            DashboardAdapter.this.dashboardItemClickListener.onSuperPlayerClick((Player) ((DashboardSuperPlayerAdapter) adapter).getItem(i), baseViewHolder.itemView);
//                        }
                        mContext.startActivity(new Intent(mContext, VideoActivityMain.class));
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
}
