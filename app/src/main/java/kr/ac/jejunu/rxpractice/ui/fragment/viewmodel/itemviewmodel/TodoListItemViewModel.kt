package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseItemViewModel
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.text.SimpleDateFormat

class TodoListItemViewModel : BaseItemViewModel<Schedule>() {
    val schedule = MutableLiveData<Schedule>()
    val name = ObservableField<String>()
    val title = ObservableField<String>()
    val date = ObservableField<String>()
    val description = ObservableField<String>()

    @SuppressLint("SimpleDateFormat")
    override fun bind(data: Schedule) {
        val cal = data.cal
        val dateFormat = "yyyy-MM-dd hh:mm"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        val day = simpleDateFormat.format(cal!!.time)
        schedule.value = data
        name.set(data.name)
        title.set(data.title)
        date.set(day)
        description.set(data.description)
    }
}