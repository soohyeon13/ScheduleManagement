package kr.ac.jejunu.rxpractice.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.adapter.UserListAdapter


//@BindingAdapter("bind_items")
//fun setBindItems(view: RecyclerView, items: List<Schedule>?) {
//    items?.let {
//        val adapter = view.adapter as TodoListAdapter
//        adapter.setSchedules(items)
//        adapter.notifyDataSetChanged()
//    }
//}

@BindingAdapter("bind_users")
fun setBindUsers(view: RecyclerView, users: List<User>?) {
    users?.let {
        val adapter = view.adapter as UserListAdapter
        adapter.setUsers(users)
        adapter.notifyDataSetChanged()
    }
}