package kr.ac.jejunu.rxpractice.ui.sales.tabui

import android.os.Bundle
import android.view.View
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.SalesViewerLayoutBinding

class SalesViewerFragment : BaseFragment<SalesViewerLayoutBinding>(R.layout.sales_viewer_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {

    }
}