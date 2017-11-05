package pasu.vadivasal.view;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.v7.widget.SnapHelper;
import android.view.View;


public class GravitySnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1.0f;
    private int mGravity;
    private OrientationHelper mHorizontalHelper;
    private boolean mIsRtlHorizontal;
    private OnScrollListener mScrollListener;
    private boolean mSnapLastItemEnabled;
    SnapListener mSnapListener;
    boolean mSnapping;
    private OrientationHelper mVerticalHelper;

    class C05651 extends OnScrollListener {
        C05651() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 2) {
                GravitySnapHelper.this.mSnapping = false;
            }
            if (newState == 0 && GravitySnapHelper.this.mSnapping && GravitySnapHelper.this.mSnapListener != null) {
                int position = GravitySnapHelper.this.getSnappedPosition(recyclerView);
                if (position != -1) {
                    GravitySnapHelper.this.mSnapListener.onSnap(position);
                }
                GravitySnapHelper.this.mSnapping = false;
            }
        }
    }

    public interface SnapListener {
        void onSnap(int i);
    }

    public GravitySnapHelper(int gravity) {
        this(gravity, false, null);
    }

    public GravitySnapHelper(int gravity, boolean enableSnapLastItem) {
        this(gravity, enableSnapLastItem, null);
    }

    public GravitySnapHelper(int gravity, boolean enableSnapLastItem, SnapListener snapListener) {
        this.mScrollListener = new C05651();
        if (gravity == GravityCompat.START || gravity == GravityCompat.END || gravity == 80 || gravity == 48) {
            this.mSnapListener = snapListener;
            this.mGravity = gravity;
            this.mSnapLastItemEnabled = enableSnapLastItem;
            return;
        }
        throw new IllegalArgumentException("Invalid gravity value. Use START | END | BOTTOM | TOP constants");
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        if (recyclerView != null) {
            if (this.mGravity == GravityCompat.START || this.mGravity == GravityCompat.END) {
                this.mIsRtlHorizontal = true;
            }
            if (this.mSnapListener != null) {
                recyclerView.addOnScrollListener(this.mScrollListener);
            }
        }
        super.attachToRecyclerView(recyclerView);
    }

    public int findTargetSnapPosition(LayoutManager layoutManager, int velocityX, int velocityY) {
        if (!(layoutManager instanceof ScrollVectorProvider)) {
            return -1;
        }
        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return -1;
        }
        View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return -1;
        }
        int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == -1) {
            return -1;
        }
        PointF vectorForEnd = ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            return -1;
        }
        int hDeltaJump;
        int vDeltaJump;
        int deltaJump;
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager, getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0.0f) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager, getVerticalHelper(layoutManager), 0, velocityY);
            if (vectorForEnd.y < 0.0f) {
                vDeltaJump = -vDeltaJump;
            }
        } else {
            vDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            deltaJump = vDeltaJump;
        } else {
            deltaJump = hDeltaJump;
        }
        if (deltaJump == 0) {
            return -1;
        }
        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            return itemCount - 1;
        }
        return targetPos;
    }

    public int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (!layoutManager.canScrollHorizontally()) {
            out[0] = 0;
        } else if (this.mGravity == GravityCompat.START) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager), false);
        } else {
            out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager), false);
        }
        if (!layoutManager.canScrollVertically()) {
            out[1] = 0;
        } else if (this.mGravity == 48) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager), false);
        } else {
            out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager), false);
        }
        return out;
    }

    public View findSnapView(LayoutManager layoutManager) {
        View snapView = null;
        if (layoutManager instanceof LinearLayoutManager) {
            switch (this.mGravity) {
                case 48:
                    snapView = findStartView(layoutManager, getVerticalHelper(layoutManager));
                    break;
                case 80:
                    snapView = findEndView(layoutManager, getVerticalHelper(layoutManager));
                    break;
                case GravityCompat.START /*8388611*/:
                    snapView = findStartView(layoutManager, getHorizontalHelper(layoutManager));
                    break;
                case GravityCompat.END /*8388613*/:
                    snapView = findEndView(layoutManager, getHorizontalHelper(layoutManager));
                    break;
            }
        }
        this.mSnapping = snapView != null;
        return snapView;
    }

    public void enableLastItemSnap(boolean snap) {
        this.mSnapLastItemEnabled = snap;
    }

    private int distanceToStart(View targetView, OrientationHelper helper, boolean fromEnd) {
        if (!this.mIsRtlHorizontal || fromEnd) {
            return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
        }
        return distanceToEnd(targetView, helper, true);
    }

    private int distanceToEnd(View targetView, OrientationHelper helper, boolean fromStart) {
        if (!this.mIsRtlHorizontal || fromStart) {
            return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
        }
        return distanceToStart(targetView, helper, true);
    }

    private View findStartView(LayoutManager layoutManager, OrientationHelper helper) {
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }
        int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        if (firstChild == -1) {
            return null;
        }
        float visibleWidth;
        View child = layoutManager.findViewByPosition(firstChild);
        if (this.mIsRtlHorizontal) {
            visibleWidth = ((float) (helper.getTotalSpace() - helper.getDecoratedStart(child))) / ((float) helper.getDecoratedMeasurement(child));
        } else {
            visibleWidth = ((float) helper.getDecoratedEnd(child)) / ((float) helper.getDecoratedMeasurement(child));
        }
        boolean endOfList = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() + -1;
        if (visibleWidth > 0.5f && !endOfList) {
            return child;
        }
        if (this.mSnapLastItemEnabled && endOfList) {
            return child;
        }
        if (endOfList) {
            return null;
        }
        return layoutManager.findViewByPosition(firstChild + 1);
    }

    private View findEndView(LayoutManager layoutManager, OrientationHelper helper) {
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }
        int lastChild = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        if (lastChild == -1) {
            return null;
        }
        float visibleWidth;
        View child = layoutManager.findViewByPosition(lastChild);
        if (this.mIsRtlHorizontal) {
            visibleWidth = ((float) helper.getDecoratedEnd(child)) / ((float) helper.getDecoratedMeasurement(child));
        } else {
            visibleWidth = ((float) (helper.getTotalSpace() - helper.getDecoratedStart(child))) / ((float) helper.getDecoratedMeasurement(child));
        }
        boolean startOfList = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0;
        if (visibleWidth > 0.5f && !startOfList) {
            return child;
        }
        if (this.mSnapLastItemEnabled && startOfList) {
            return child;
        }
        if (startOfList) {
            return null;
        }
        return layoutManager.findViewByPosition(lastChild - 1);
    }

    private int estimateNextPositionDiffForFling(LayoutManager layoutManager, OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0.0f) {
            return 0;
        }
        int distance = Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
        if (((float) Math.abs(distance)) >= distancePerChild / 2.0f) {
            return (int) Math.floor((double) (((float) distance) / distancePerChild));
        }
        return 0;
    }

    private float computeDistancePerChild(LayoutManager layoutManager, OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return 1.0f;
        }
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int pos = layoutManager.getPosition(child);
            if (pos != -1) {
                if (pos < minPos) {
                    minPos = pos;
                    minPosView = child;
                }
                if (pos > maxPos) {
                    maxPos = pos;
                    maxPosView = child;
                }
            }
        }
        if (minPosView == null || maxPosView == null) {
            return 1.0f;
        }
        int distance = Math.max(helper.getDecoratedEnd(minPosView), helper.getDecoratedEnd(maxPosView)) - Math.min(helper.getDecoratedStart(minPosView), helper.getDecoratedStart(maxPosView));
        if (distance == 0) {
            return 1.0f;
        }
        return (1.0f * ((float) distance)) / ((float) ((maxPos - minPos) + 1));
    }

    int getSnappedPosition(RecyclerView recyclerView) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (this.mGravity == GravityCompat.START || this.mGravity == 48) {
                return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            }
            if (this.mGravity == GravityCompat.END || this.mGravity == 80) {
                return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
        }
        return -1;
    }

    private OrientationHelper getVerticalHelper(LayoutManager layoutManager) {
        if (this.mVerticalHelper == null) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    private OrientationHelper getHorizontalHelper(LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }
}
