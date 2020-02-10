package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.model.User

class UserListViewModel(application: Application) : BaseViewModel<List<User>>(application) {
    private val _user = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _user

    init {
        getUsers()
    }

    private fun getUsers() {

    }

}