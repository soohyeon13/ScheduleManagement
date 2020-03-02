package kr.ac.jejunu.rxpractice.ui.fragment

import android.util.Log
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.UserListFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.UserListViewModel

class UserListFragment : BaseFragment<UserListFragmentBinding, UserListViewModel>(R.layout.user_list_fragment){
    override fun getViewModel(): Class<UserListViewModel> {
        return UserListViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.userListViewModel
    }

    override fun initView() {
        viewModel.getUsers(arguments?.getString("userName").toString())
    }
}