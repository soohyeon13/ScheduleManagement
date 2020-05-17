package kr.ac.jejunu.rxpractice.ui.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.databinding.TimeScheduleItemBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import kr.ac.jejunu.rxpractice.ui.schedule.viewholder.TimeViewHolder

class TimeAdapter : RecyclerView.Adapter<TimeViewHolder>() {
    private var timeSchedule = ArrayList<TimeSchedule>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding : TimeScheduleItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.time_schedule_item,parent,false)
        return TimeViewHolder(binding)
    }

    override fun getItemCount(): Int =timeSchedule.size

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(timeSchedule[position])
    }

    fun setSchedules(schedule: ArrayList<TimeSchedule>) {
        this.timeSchedule = schedule
        notifyDataSetChanged()
    }
}