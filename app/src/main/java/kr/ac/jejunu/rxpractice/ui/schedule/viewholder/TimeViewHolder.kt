package kr.ac.jejunu.rxpractice.ui.schedule.viewholder

import android.view.View
import kr.ac.jejunu.rxpractice.base.BaseViewHolder
import kr.ac.jejunu.rxpractice.databinding.TimeScheduleItemBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import kr.ac.jejunu.rxpractice.ui.schedule.viewmodel.itemviewmodel.ScheduleItemViewModel

class TimeViewHolder(val binding : TimeScheduleItemBinding): BaseViewHolder<TimeSchedule>(binding.root) {
    private val viewModel = ScheduleItemViewModel()
    override fun bind(item: TimeSchedule) {
        viewModel.bind(item)
        binding.itemViewModel = viewModel
    }

    fun setListener(itemClick : View.OnClickListener) {
        itemView.setOnClickListener(itemClick)
    }
}