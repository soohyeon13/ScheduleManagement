package kr.ac.jejunu.rxpractice.ui.schedule.viewmodel

import android.view.View
import kotlinx.android.synthetic.main.time_table_layout.view.*
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent

class TimeTableViewModel : BaseViewModel() {
    val timeRowClick = SingleLiveEvent<Schedule>()

    fun onTimeRowClick(view : View) {
        when(view.id) {
            R.id.container09 -> {

            }
            R.id.container10 -> {

            }
            R.id.container11 -> {

            }
            R.id.container12 -> {

            }
            R.id.container13 -> {

            }
            R.id.container14 -> {

            }
            R.id.container15 -> {

            }
            R.id.container16 -> {

            }
            R.id.container17 -> {

            }
            R.id.container18 -> {

            }
            R.id.container19 -> {

            }
            R.id.container20 -> {

            }
        }
    }
}