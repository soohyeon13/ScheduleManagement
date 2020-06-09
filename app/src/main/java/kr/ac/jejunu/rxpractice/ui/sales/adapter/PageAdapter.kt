package kr.ac.jejunu.rxpractice.ui.sales.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kr.ac.jejunu.rxpractice.ui.sales.tabui.SalesViewerFragment

class PageAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position) {
            else-> SalesViewerFragment()
        }
    }

    override fun getCount(): Int = 2


}