package kr.ac.jejunu.rxpractice.ui.sales.week

import kr.ac.jejunu.rxpractice.base.BaseViewHolder
import kr.ac.jejunu.rxpractice.databinding.DayOfWeekItemBinding
import java.util.*

class WeekHolder(val binding: DayOfWeekItemBinding) : BaseViewHolder<Date>(binding.root){
    val childView = binding.childRecyclerview
    override fun bind(item: Date) {

    }
}