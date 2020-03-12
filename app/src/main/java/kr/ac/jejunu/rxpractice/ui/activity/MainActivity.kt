package kr.ac.jejunu.rxpractice.ui.activity

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseActivity
import kr.ac.jejunu.rxpractice.databinding.ActivityMainBinding
import kr.ac.jejunu.rxpractice.ui.fragment.TodoFragmentDirections
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.mainViewModel
    }

    override fun initView() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment? ?: return
//        binding.toolbar.setupWithNavController(host.navController)
//        appBarConfiguration= AppBarConfiguration(
//            setOf(R.id.action_money)
//        )
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(host.navController)
        supportActionBar!!.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        }
//        setupActionBarWithNavController(host.navController,appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_money -> {
                NavigationUI.onNavDestinationSelected(item,NavHostFragment.findNavController(fragment))
            }
            R.id.action_search -> {
                val intent = Intent(this,SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return true
//        return NavigationUI.onNavDestinationSelected(
//            item,
//            NavHostFragment.findNavController(fragment)
//        ) || super.onOptionsItemSelected(item)
    }

    //    override fun onSupportNavigateUp() = findNavController(R.id.fragment).navigateUp(appBarConfiguration)
    override fun onSupportNavigateUp() = NavHostFragment.findNavController(fragment).navigateUp()
}
