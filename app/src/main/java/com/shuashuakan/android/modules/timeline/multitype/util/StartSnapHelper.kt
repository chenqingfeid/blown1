package com.shuashuakan.android.modules.timeline.multitype.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * https://github.com/MindorksOpenSource/SnapHelperExample
 */
class StartSnapHelper : LinearSnapHelper() {

  private lateinit var mVerticalHelper: OrientationHelper
  private lateinit var mHorizontalHelper: OrientationHelper

  override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
    val out = IntArray(2)
    if (layoutManager.canScrollHorizontally()) {
      out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
    } else {
      out[0] = 0
    }

    if (layoutManager.canScrollVertically()) {
      out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
    } else {
      out[1] = 0
    }
    return out
  }

  override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
    if (layoutManager is LinearLayoutManager) {
      return if (layoutManager.canScrollHorizontally()) {
        getStartView(layoutManager, getHorizontalHelper(layoutManager))
      } else {
        getStartView(layoutManager, getVerticalHelper(layoutManager))
      }
    }
    return super.findSnapView(layoutManager)
  }

  private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
    return helper.getDecoratedStart(targetView) - helper.startAfterPadding
  }

  private fun getStartView(layoutManager: RecyclerView.LayoutManager,
                           helper: OrientationHelper): View? {
    if (layoutManager is LinearLayoutManager) {
      val firstChild = layoutManager.findFirstVisibleItemPosition()

      val isLastItem = layoutManager
          .findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1

      if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
        return null
      }

      val child = layoutManager.findViewByPosition(firstChild)

      return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 && helper.getDecoratedEnd(child) > 0) {
        child
      } else {
        if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
          null
        } else {
          layoutManager.findViewByPosition(firstChild + 1)
        }
      }
    }

    return super.findSnapView(layoutManager)
  }

  private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
    if (!::mVerticalHelper.isInitialized) {
      mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
    }
    return mVerticalHelper
  }

  private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
    if (!::mHorizontalHelper.isInitialized) {
      mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
    }
    return mHorizontalHelper
  }
}