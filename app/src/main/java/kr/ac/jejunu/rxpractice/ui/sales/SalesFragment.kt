package kr.ac.jejunu.rxpractice.ui.sales

import android.os.Bundle
import android.view.View
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.FragmentSalesBinding

class SalesFragment :BaseFragment<FragmentSalesBinding>(R.layout.fragment_sales){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {

    }
    private fun observe() {

    }
}