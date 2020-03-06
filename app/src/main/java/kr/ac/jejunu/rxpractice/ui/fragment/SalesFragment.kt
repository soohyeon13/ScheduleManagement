package kr.ac.jejunu.rxpractice.ui.fragment

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.add_schedule_fragment.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.SalesFragmentBinding
import kr.ac.jejunu.rxpractice.ui.adapter.PagerAdapter
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.SalesViewModel

class SalesFragment : BaseFragment<SalesFragmentBinding,SalesViewModel>(R.layout.sales_fragment) {
    private val PAGES : Int = 2
    override fun getViewModel(): Class<SalesViewModel> = SalesViewModel::class.java

    override fun getBindingVariable(): Int = BR.salesViewModel

    override fun initView() {
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val pagerAdapter = PagerAdapter(childFragmentManager)
        binding.viewPagerContainer.offscreenPageLimit = PAGES
        binding.viewPagerContainer.adapter = pagerAdapter
        setViewPagerListener()
        setTabLayoutListener()
    }

    private fun setTabLayoutListener() {
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPagerContainer.currentItem = tab!!.position
            }
        })
    }

    private fun setViewPagerListener() {
//        binding.viewPagerContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                when(position) {
//                    0 -> binding.tab.addTab()
//                }
//            }
//        })
        binding.viewPagerContainer.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tab))
    }
}