package kr.ac.jejunu.rxpractice.ui.sales.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.DayOfWeekItemBinding

class WeekAdapter : RecyclerView.Adapter<WeekHolder>() {
    companion object {
        const val TAG = "WeekAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekHolder {
        val binding: DayOfWeekItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.day_of_week_item, parent, false
        )
        return WeekHolder(binding)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: WeekHolder, position: Int) {
        holder.childView.apply {
            layoutManager = LinearLayoutManager(holder.childView.context,LinearLayoutManager.VERTICAL,false)
        }
    }
}