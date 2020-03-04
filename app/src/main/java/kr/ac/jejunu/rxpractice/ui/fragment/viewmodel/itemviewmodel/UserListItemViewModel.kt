package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel

import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseItemViewModel
import kr.ac.jejunu.rxpractice.model.User

class UserListItemViewModel :BaseItemViewModel<User>() {
    val user = MutableLiveData<User>()
    val name = MutableLiveData<String>()
    val num = MutableLiveData<String>()
    override fun bind(data: User) {
        user.value = data
        name.value = data.userName
        num.value = data.userNum
    }
}