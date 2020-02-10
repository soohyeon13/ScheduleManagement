package kr.ac.jejunu.rxpractice.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseActivity
import kr.ac.jejunu.rxpractice.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    override fun initView() {
        binding.nameSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val bundle = Bundle()
                bundle.putString("userName", binding.nameSearch.text.toString())
                val controller = findNavController(R.id.fragment)
                controller.navigate(R.id.action_todoFragment_to_userListFragment, bundle)
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(fragment).navigateUp()
}
