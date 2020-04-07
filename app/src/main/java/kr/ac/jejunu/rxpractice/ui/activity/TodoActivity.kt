package kr.ac.jejunu.rxpractice.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_todo.*
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.ActivityTodoBinding

class TodoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_todo)
        binding.lifecycleOwner = this
        initView()
    }
    fun initView() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment? ?: return
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(host.navController)
        supportActionBar!!.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        }
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
    }
    override fun onSupportNavigateUp() = NavHostFragment.findNavController(fragment).navigateUp()
}
