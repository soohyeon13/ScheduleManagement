package kr.ac.jejunu.rxpractice.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ShareActionProvider
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.databinding.TodoItemBinding
import kr.ac.jejunu.rxpractice.databinding.UserItemBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    private var schedules :List<Schedule> = listOf()
    class TodoViewHolder(private val binding : TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(schedule: Schedule) {
            binding.schedule = schedule
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    fun setSchedules(schedule: List<Schedule>) {
        this.schedules = schedules
    }

    override fun getItemCount(): Int =schedules.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(schedules[position])
    }


}