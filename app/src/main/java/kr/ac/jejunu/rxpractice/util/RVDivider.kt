package kr.ac.jejunu.rxpractice.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R

class RVDivider(context:Context) : RecyclerView.ItemDecoration() {
    private var mDivider : Drawable? = ContextCompat.getDrawable(context,R.drawable.dash_line)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.childCount
        for (i in 0 until count) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            mDivider?.draw(c)
        }
    }
}