package kr.ac.jejunu.rxpractice.util

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule

class DiffCallback (
    private val oldList : ArrayList<TimeSchedule?>,
    private val newList: ArrayList<TimeSchedule>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.time == newList[newItemPosition].time
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.time == newList[newItemPosition].time &&
                oldList[oldItemPosition]?.schedule == newList[newItemPosition].schedule
    }
}