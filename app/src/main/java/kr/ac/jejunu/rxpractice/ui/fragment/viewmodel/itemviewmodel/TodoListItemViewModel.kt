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
    private var _etcDes = MutableLiveData<String>()
    val etcDes : LiveData<String>
        get() = _etcDes
    private var _checkEtcContent = MutableLiveData<Boolean>()
    val isCheckEtcContent : LiveData<Boolean>
        get() = _checkEtcContent

    init {
        _checkEtcContent.value = false
    }

    override fun bind(data: Schedule) {
        if (!data.etcDes.isNullOrEmpty()) {
            _checkEtcContent.value =true
        }
        val day = convertDay(data.cal)
        val des = convertDescription(data.descriptions)
        schedule.value = data
        _name.value = data.name
        _date.value = day
        _descriptions.value = des
        if (_checkEtcContent.value!!) _etcDes.value = data.etcDes
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