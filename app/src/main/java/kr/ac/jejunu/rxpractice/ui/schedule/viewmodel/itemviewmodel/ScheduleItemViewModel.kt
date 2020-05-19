package kr.ac.jejunu.rxpractice.ui.schedule.viewmodel.itemviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseItemViewModel
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import java.text.SimpleDateFormat

class ScheduleItemViewModel : BaseItemViewModel<TimeSchedule>() {
    private val format = SimpleDateFormat("HH:mm")
    private var _time = MutableLiveData<String>()
    val time : LiveData<String>
        get() = _time
    private var _name = MutableLiveData<String>()
    val name : LiveData<String>
        get() = _name
    private var _content = MutableLiveData<String>()
    val content : LiveData<String>
        get() = _content
    private var _date = MutableLiveData<String>()
    val date : LiveData<String>
        get() = _date
    override fun bind(item: TimeSchedule) {
        item.schedule?.let {
            val convertDate = format.format(it.date)
            _name.value = it.name
            _content.value = it.reservationContent
            _date.value = convertDate
        }
        _time.value = item.time
    }
}