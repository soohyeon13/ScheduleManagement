package kr.ac.jejunu.rxpractice.ui.schedule.viewholder

import kr.ac.jejunu.rxpractice.base.BaseViewHolder
import kr.ac.jejunu.rxpractice.databinding.TimeScheduleItemBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import java.text.SimpleDateFormat

class TimeViewHolder(val binding : TimeScheduleItemBinding): BaseViewHolder<TimeSchedule>(binding.root) {
    private val dateFormat = SimpleDateFormat("HH:mm")
    override fun bind(item: TimeSchedule) {
        println(item.toString())
        binding.time.text = item.time
        binding.name.text = item.name
        item.date?.let {
            binding.reservationTime.text = dateFormat.format(it)
        }
        binding.content.text = item.content
    }
}