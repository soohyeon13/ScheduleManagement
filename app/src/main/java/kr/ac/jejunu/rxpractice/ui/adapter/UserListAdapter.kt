package kr.ac.jejunu.rxpractice.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.databinding.UserItemBinding
import kr.ac.jejunu.rxpractice.model.User

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var users : List<User> = listOf()
    class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onUserBind(user : User) {
            binding.user = user
        }
    }

    fun setUsers(users: List<User>) {
        this.users = users
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onUserBind(users[position])
    }
}