package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

    //여기 부분 너무 하드코딩 느낌...
    fun onHandClick() {
        _isCheckHand.value = !_isCheckHand.value!!
        isCheck(_isCheckHand.value!!, "손케어")
    }

    fun onFootClick() {
        _isCheckFoot.value = !_isCheckFoot.value!!
        isCheck(isCheckFoot.value!!, "발케어")
    }

    fun onGNClick() {
        _isCheckGN.value = !_isCheckGN.value!!
        isCheck(_isCheckGN.value!!, "젤네일")
    }

    fun onGFClick() {
        _isCheckGF.value = !_isCheckGF.value!!
        isCheck(_isCheckGF.value!!, "젤페디")
    }

    fun onRGClick() {
        _isCheckRG.value = !_isCheckRG.value!!
        isCheck(_isCheckRG.value!!, "젤 제거")
    }

    fun onRClick() {
        _isCheckR.value = !_isCheckR.value!!
        isCheck(_isCheckR.value!!, "보수")
    }

    fun onHClick() {
        _isCheckH.value = !_isCheckH.value!!
        isCheck(_isCheckH.value!!, "홀드핀칭")
    }

    fun onEtcClick() {
        _isCheckEtc.value = !_isCheckEtc.value!!
        isCheck(_isCheckEtc.value!!, "기타")
    }

    private fun isCheck(check: Boolean, title: String) {
        if (check) descriptions.add(Description(description = title)) else descriptions.remove(
            Description(description = title)
        )
        Log.d("test", descriptions.toString())
    }

    //리펙토링 하고싶다... 방법을 잘 모르겠음 공부 필요...
    fun onSelectPerson() {
        clickPersonEvent.call()
    }

    fun onSelectDate() {
        clickDateEvent.call()
    }

    fun onSave(
        userName: String,
        number: String,
        date: String
    ) {
        if (userName.isEmpty() || date.isEmpty()) {
            toastShow.call()
        } else {
            val saveDate = date.split(" ".toRegex())
            userId = checkUser(userName, number)
            val c = dateToCalendar(date)
            val schedule = Schedule(
                userId = userId,
                name = userName,
                descriptions = descriptions,
                cal = c,
                date = saveDate[0]
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

    fun onCancel() {
        Log.d("test", "test cancel")
        clickCancel.call()
    }
}