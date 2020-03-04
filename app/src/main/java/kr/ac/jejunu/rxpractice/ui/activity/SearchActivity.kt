package kr.ac.jejunu.rxpractice.ui.activity

import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseActivity
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.databinding.SearchActivityBinding
import kr.ac.jejunu.rxpractice.ui.adapter.SearchAdapter
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.SearchViewModel
import java.util.*

class SearchActivity : BaseActivity<SearchActivityBinding,SearchViewModel>(R.layout.search_activity),SearchView.OnQueryTextListener {
    private lateinit var searchAdapter : SearchAdapter
    private val repository : RoomRepository by lazy {
        RoomRepository(this.application)
    }
    override fun getViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.searchViewModel
    }

    override fun initView() {
        setSupportActionBar(binding.secondToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.searchView.requestFocus()
        searchAdapter = SearchAdapter(repository.getAllUsers())
        binding.userRecycler.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(this@SearchActivity,LinearLayoutManager.VERTICAL))
        }
        binding.searchView.onActionViewExpanded()
        binding.searchView.isIconified = false
        binding.searchView.setOnQueryTextListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        search(newText)
        return true
    }
    private fun search(query: String?) {
        searchAdapter.search(query) {
            Toast.makeText(this,"존재 하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}