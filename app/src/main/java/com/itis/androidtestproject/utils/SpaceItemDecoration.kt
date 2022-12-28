package com.itis.androidtestproject.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    context: Context,
    spacingDp: Float
) : RecyclerView.ItemDecoration() {

    private val spacingPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacingDp,
        context.resources.displayMetrics
    ).toInt() / 2

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = spacingPx
        outRect.bottom = spacingPx
        outRect.left = spacingPx
        outRect.right = spacingPx
    }
}