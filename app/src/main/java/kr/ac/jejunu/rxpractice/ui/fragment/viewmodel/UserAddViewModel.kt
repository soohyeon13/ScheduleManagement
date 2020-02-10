package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent

class UserAddViewModel(application: Application):BaseViewModel<User>(application) {
    private val repository: RoomRepository by lazy {
        RoomRepository(application)
    }
    val saveUser = SingleLiveEvent<Unit>()
    val getUser = SingleLiveEvent<Unit>()
    val cancelUser = SingleLiveEvent<Unit>()
    fun onSaveUser() {
        Log.d("test on save user","start")
        saveUser.call()
    }
    fun saveUser(user: User) {
        Log.d("test","saveUser")
        CompositeDisposable().add(repository.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    fun cancelUser() {
        cancelUser.call()
    }

    fun getUserFromContacts() {
        getUser.call()
    }
}