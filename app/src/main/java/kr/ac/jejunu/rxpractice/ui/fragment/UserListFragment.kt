package kr.ac.jejunu.rxpractice.ui.fragment

import android.util.Log
import androidx.databinding.library.baseAdapters.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.SearchUserFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.UserListViewModel

class UserListFragment : BaseFragment<SearchUserFragmentBinding, UserListViewModel>(R.layout.search_user_fragment){
    override fun getViewModel(): Class<UserListViewModel> {
        return UserListViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.viewmodel
    }

    override fun initView() {
        binding.test.text = arguments?.getString("userName")
    }
}