package ny.gelato.extessera.common

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView

/**
 * Created by jord.goldberg on 6/21/17.
 */

class SmoothGridLayoutManager(context: Context, spanCount: Int)
    : GridLayoutManager(context, spanCount) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@SmoothGridLayoutManager.computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int = SNAP_TO_START
    }
}