package ny.gelato.extessera.common

import android.content.Context
import android.support.v7.widget.StaggeredGridLayoutManager
import android.graphics.PointF
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView

/**
 * Created by jord.goldberg on 6/21/17.
 */

class SmoothStaggeredLayoutManager(spanCount: Int, orientation: Int)
    : StaggeredGridLayoutManager(spanCount, orientation) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@SmoothStaggeredLayoutManager.computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int = SNAP_TO_START
    }
}