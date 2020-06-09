package kr.ac.jejunu.rxpractice.ui.sales

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.FragmentSalesBinding
import kr.ac.jejunu.rxpractice.ui.sales.adapter.PageAdapter

class SalesFragment :BaseFragment<FragmentSalesBinding>(R.layout.fragment_sales){
    companion object{
        private val PAGE : Int = 2
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        listener()
        observe()
    }

    private fun initView() {
        binding.viewPagerContainer.apply {
            offscreenPageLimit = PAGE
            adapter = PageAdapter(childFragmentManager)
        }
    }

    private fun listener() {
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPagerContainer.currentItem = tab!!.position
            }
        })
        binding.viewPagerContainer.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tab))
    }

    private fun observe() {

    }
}