package kr.ac.jejunu.rxpractice.ui.fragment.tabfragment

import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.SalesSubmitFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.tabviewmodel.SalesSubmitVIewModel

class SalesSubmitFragment : BaseFragment<SalesSubmitFragmentBinding,SalesSubmitVIewModel>(R.layout.sales_submit_fragment) {
    override fun getViewModel(): Class<SalesSubmitVIewModel> = SalesSubmitVIewModel::class.java

    override fun getBindingVariable(): Int = BR.submitViewModel

    override fun initView() {
    }
}