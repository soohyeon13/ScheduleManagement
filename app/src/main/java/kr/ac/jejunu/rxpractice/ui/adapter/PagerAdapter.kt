package kr.ac.jejunu.rxpractice.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kr.ac.jejunu.rxpractice.ui.fragment.tabfragment.SalesSubmitFragment
import kr.ac.jejunu.rxpractice.ui.fragment.tabfragment.SalesTotalFragment

class PagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SalesSubmitFragment()
            else -> SalesTotalFragment()
        }
    }

    override fun getCount(): Int = 2
}