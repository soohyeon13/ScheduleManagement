package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent

class MainViewModel(application: Application) : BaseViewModel<Any>(application) {
    val calendarEvent = SingleLiveEvent<Unit>()
    val userEvent = SingleLiveEvent<Unit>()
    fun onCalendarClick() {
        calendarEvent.call()
    }

    fun onUserClick() {
        userEvent.call()
    }
}