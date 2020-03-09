package kr.ac.jejunu.rxpractice.util

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("backgroundVisibleOrGone")
fun setBackgroundVisibleOrGone(view:View,visible:Boolean) {
    if (visible) {
        Log.d("test1","test")
        view.setBackgroundResource(R.drawable.check_box)
    }else {
        Log.d("test2","test")
        view.setBackgroundResource(R.drawable.content_btn)
    }
}