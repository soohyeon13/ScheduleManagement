package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseItemViewModel
import kr.ac.jejunu.rxpractice.model.Description
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class TodoListItemViewModel : BaseItemViewModel<Schedule>() {
    val schedule = MutableLiveData<Schedule>()
    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name
    private var _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date
    private var _descriptions = MutableLiveData<String>()
    val descriptions: LiveData<String>
        get() = _descriptions

    override fun bind(data: Schedule) {
        val day = convertDay(data.cal)
        val des = convertDescription(data.descriptions)
        schedule.value = data
        _name.value = data.name
        _date.value = day
        _descriptions.value = des
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDay(cal: Calendar?): String {
        val dateFormat = "hh:mm"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        return simpleDateFormat.format(cal!!.time)
    }

    private fun convertDescription(list: List<Description>): String {
        var preStr = ""
        for (str in list) {
            preStr += "${str.description} "
        }
        return preStr.trim().replace(' ', ',')
    }
}