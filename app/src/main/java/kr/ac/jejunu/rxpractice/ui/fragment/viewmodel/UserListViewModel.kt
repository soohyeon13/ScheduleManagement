package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.observers.DisposableSingleObserver
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.adapter.UserListAdapter

class UserListViewModel(application: Application) : BaseViewModel<List<User>>(application) {
    private val _user = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _user
    private val repository : RoomRepository by lazy {
        RoomRepository(application)
    }
    private val adapter = UserListAdapter()

    fun getUsers(name : String) {
        addDisposable(repository.getUser(name),object :DisposableSingleObserver<List<User>>(){
            override fun onSuccess(t: List<User>) {
                Log.d("test",t.toString())
                _user.value = t
            }
            override fun onError(e: Throwable) {
                Log.d("UserListViewModel",e.message)
            }
        })
    }
}