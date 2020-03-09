package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel

import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseItemViewModel
import kr.ac.jejunu.rxpractice.model.Schedule

class TodoTableItemViewModel : BaseItemViewModel<Schedule>() {
    val schedule = MutableLiveData<Schedule>()
    val name = MutableLiveData<String>()
    val num = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    override fun bind(data: Schedule) {
        schedule.value = data
        name.value = data.name
//        description.value = data.description
    }
}