package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_schedule_fragment.view.*
import kotlinx.android.synthetic.main.content_layout.view.*
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.Description
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAddViewModel(application: Application) : BaseViewModel<List<Schedule>>(application) {
    val clickPersonEvent = SingleLiveEvent<Unit>()
    val clickDateEvent = SingleLiveEvent<Unit>()
    val clickSave = SingleLiveEvent<Unit>()
    val clickCancel = SingleLiveEvent<Unit>()
    val toastShow = SingleLiveEvent<Unit>()

    private var descriptions = mutableListOf<Description>()
    private var _isCheckHand = MutableLiveData<Boolean>()
    val isCheckHand: LiveData<Boolean>
        get() = _isCheckHand
    private var _isCheckFoot = MutableLiveData<Boolean>()
    val isCheckFoot: LiveData<Boolean>
        get() = _isCheckFoot
    private var _isCheckGN = MutableLiveData<Boolean>()
    val isCheckGN: LiveData<Boolean>
        get() = _isCheckGN
    private var _isCheckGF = MutableLiveData<Boolean>()
    val isCheckGF: LiveData<Boolean>
        get() = _isCheckGF
    private var _isCheckRG = MutableLiveData<Boolean>()
    val isCheckRG: LiveData<Boolean>
        get() = _isCheckRG
    private var _isCheckR = MutableLiveData<Boolean>()
    val isCheckR: LiveData<Boolean>
        get() = _isCheckR
    private var _isCheckH = MutableLiveData<Boolean>()
    val isCheckH: LiveData<Boolean>
        get() = _isCheckH
    private var _isCheckEtc = MutableLiveData<Boolean>()
    val isCheckEtc: LiveData<Boolean>
        get() = _isCheckEtc
    private var userId = 0
    private val calendar = Calendar.getInstance()
    private val repository: RoomRepository by lazy {
        RoomRepository(application)
    }

    init {
        _isCheckHand.value = false
        _isCheckFoot.value = false
        _isCheckGN.value = false
        _isCheckGF.value = false
        _isCheckRG.value = false
        _isCheckR.value = false
        _isCheckH.value = false
        _isCheckEtc.value = false
    }

    private fun isCheck(check: Boolean, title: String) {
        if (check) descriptions.add(Description(description = title)) else descriptions.remove(
            Description(description = title)
        )
    }

    fun onSelectPerson() {
        clickPersonEvent.call()
    }

    fun onSelectDate() {
        clickDateEvent.call()
    }

    fun onSave(
        userName: String,
        number: String,
        date: String,
        etcDes : String?
    ) {
        if (userName.isEmpty() || date.isEmpty()) {
            toastShow.call()
        } else {
            val saveDate = date.split(" ".toRegex())
            var member ="비회원"
            userId = checkUser(userName, number)
            if (userId != 0) {
               member = "회원"
            }
            val c = dateToCalendar(date)
            val schedule = Schedule(
                userId = userId,
                userMember = member,
                name = userName,
                descriptions = descriptions,
                cal = c,
                date = saveDate[0],
                etcDes = etcDes
            )
            CompositeDisposable().add(
                repository.insertSchedule(schedule)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Log.d("ViewModel", it.message.toString())
                    }
                    .subscribe()
            )
            clickSave.call()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateToCalendar(dateStr: String): Calendar {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val date = simpleDateFormat.parse(dateStr)
        calendar.time = date
        return calendar
    }

    private fun checkUser(userName: String, number: String): Int {
        userId = repository.getUserId(userName, number)
        if (userId == 0) {
            val user = User(userName = userName, userNum = number)
            CompositeDisposable()
                .add(
                    repository.insertUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                )
        }
        return userId
    }

    fun isCheckBtn(view:View) {
        when(view.id) {
            R.id.handCare -> {
                _isCheckHand.value = !_isCheckHand.value!!
                isCheck(_isCheckHand.value!!, view.handCare.text.toString())
            }
            R.id.footCare -> {
                _isCheckFoot.value = !_isCheckFoot.value!!
                isCheck(_isCheckFoot.value!!, view.footCare.text.toString())
            }
            R.id.gelNail -> {
                _isCheckGN.value = !_isCheckGN.value!!
                isCheck(_isCheckGN.value!!, view.gelNail.text.toString())
            }
            R.id.gelFoot -> {
                _isCheckGF.value = !_isCheckGF.value!!
                isCheck(_isCheckGF.value!!, view.gelFoot.text.toString())
            }
            R.id.removeGel -> {
                _isCheckRG.value = !_isCheckRG.value!!
                isCheck(_isCheckRG.value!!, view.removeGel.text.toString())
            }
            R.id.repair -> {
                _isCheckR.value = !_isCheckR.value!!
                isCheck(_isCheckR.value!!, view.repair.text.toString())
            }
            R.id.hold -> {
                _isCheckH.value = !_isCheckH.value!!
                isCheck(_isCheckH.value!!, view.hold.text.toString())
            }
            R.id.etc -> {
                _isCheckEtc.value = !_isCheckEtc.value!!
                isCheck(_isCheckEtc.value!!, view.etc.text.toString())
            }
        }
    }

    fun onCancel() {
        clickCancel.call()
    }
}