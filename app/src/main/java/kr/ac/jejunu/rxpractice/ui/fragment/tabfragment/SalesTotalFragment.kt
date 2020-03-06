package kr.ac.jejunu.rxpractice.ui.fragment.tabfragment

import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.SalesTotalFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.tabviewmodel.SalesTotalViewModel

class SalesTotalFragment : BaseFragment<SalesTotalFragmentBinding,SalesTotalViewModel>(R.layout.sales_total_fragment) {
    override fun getViewModel(): Class<SalesTotalViewModel> = SalesTotalViewModel::class.java

    override fun getBindingVariable(): Int = BR.totalViewModel

    override fun initView() {
    }
}