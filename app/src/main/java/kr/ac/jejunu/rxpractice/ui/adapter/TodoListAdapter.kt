package kr.ac.jejunu.rxpractice.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TodoItemBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel.TodoListItemViewModel
import kr.ac.jejunu.rxpractice.util.OnItemClickListener

class TodoListAdapter(val itemClickListener : OnItemClickListener<Schedule>) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    private lateinit var schedules: List<Schedule>
    private lateinit var binding : TodoItemBinding

    class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TodoListItemViewModel()

        fun onBind(schedule: Schedule,itemClickListener:OnItemClickListener<Schedule>) {
            viewModel.bind(schedule)
            binding.viewModel = viewModel
            itemView.setOnClickListener {
                itemClickListener.onItemClick(schedule)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.todo_item,parent,false)
        return TodoViewHolder(binding)
    }

    fun setSchedules(schedule: List<Schedule>?) {
        this.schedules = schedule!!
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(::schedules.isInitialized){
            schedules.size
        } else{
            0
        }
    }


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(schedules[position],itemClickListener)
    }
}