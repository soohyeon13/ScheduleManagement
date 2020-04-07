package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.tabviewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.model.Sales

class SalesSubmitVIewModel(application: Application) : BaseViewModel<Sales>(application) {
    private var _isCheckDay = MutableLiveData<Boolean>()
    val isCheckDay : LiveData<Boolean>
        get() = _isCheckDay

    init {
        _isCheckDay.value = false
    }
    fun onDayClick() {
        _isCheckDay.value = !_isCheckDay.value!!
    }
}