package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.adapter.SearchAdapter

class SearchViewModel(application: Application) : BaseViewModel<User>(application) {
//    val adapter = SearchAdapter()
//    private val repository : RoomRepository by lazy {
//        RoomRepository(application)
//    }
//    init {
//        adapter.setUsers(repository.getAllUsers())
//    }
}