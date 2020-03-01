package kr.ac.jejunu.rxpractice.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseActivity
import kr.ac.jejunu.rxpractice.databinding.ActivityMainBinding
import kr.ac.jejunu.rxpractice.ui.fragment.TodoFragment
import kr.ac.jejunu.rxpractice.ui.fragment.UserListFragment
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(
    R.layout.activity_main
) {

    override fun getViewModel(): Class<MainViewModel> { return MainViewModel::class.java }
    override fun getBindingVariable(): Int { return BR.mainViewModel }

    override fun initView() {
        binding.nameSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val bundle = Bundle()
                bundle.putString("userName", binding.nameSearch.text.toString())
                val controller = findNavController(R.id.fragment)
//                controller.navigate(R.id.action_todoFragment_to_userListFragment, bundle)
                return@OnKeyListener true
            }
            false
        })

//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabReselected(p0: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when(tab?.position) {
//                    0 -> TodoFragment()
//                    1-> UserListFragment()
//                    else -> {}
//                }
//            }
//
//        })

        with(viewModel) {
            calendarEvent.observe(this@MainActivity, Observer {
                TodoFragment()
            })
            userEvent.observe(this@MainActivity, Observer {
                UserListFragment()
            })
        }
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(fragment).navigateUp()
}
