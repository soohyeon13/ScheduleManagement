package kr.ac.jejunu.rxpractice.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*
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

@BindingAdapter("backDrawable")
fun setBackDrawable(view: View,visible: Boolean) {
    view.setBackgroundResource(if (visible) R.drawable.member_box else R.drawable.non_member_box)
}