package kr.ac.jejunu.rxpractice.util

import androidx.recyclerview.widget.DiffUtil
import kr.ac.jejunu.rxpractice.model.Schedule

class TodoDiffUtil(
    private val oldList : List<Schedule>,
    private val newList : List<Schedule>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] ==  newList[newItemPosition]
}