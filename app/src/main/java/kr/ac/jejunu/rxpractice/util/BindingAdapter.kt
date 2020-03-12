package kr.ac.jejunu.rxpractice.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("backgroundVisibleOrGone")
fun setBackgroundVisibleOrGone(btn:Button,visible:Boolean) {
    if (visible) {
        btn.setBackgroundResource(R.drawable.check_box)
    }else {
        btn.setBackgroundResource(R.drawable.content_btn)
    }
}

@BindingAdapter("visibleContent")
fun setVisibleContent(view:View,visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}