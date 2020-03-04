package kr.ac.jejunu.rxpractice.ui.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseActivity
import kr.ac.jejunu.rxpractice.databinding.ActivityMainBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(
    R.layout.activity_main
) {
    override fun getViewModel(): Class<MainViewModel> { return MainViewModel::class.java }
    override fun getBindingVariable(): Int { return BR.mainViewModel }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this,SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.toolbar_menu,menu)
//        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchItem = menu?.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
//        searchView.queryHint = "이름검색"
//        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                searchItem.collapseActionView()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//        return true
//    }



    override fun onSupportNavigateUp() = NavHostFragment.findNavController(fragment).navigateUp()
}
