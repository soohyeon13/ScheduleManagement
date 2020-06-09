package kr.ac.jejunu.rxpractice.ui.input_salse

import android.os.Bundle
import android.util.Log
import android.view.View
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.SalesInputLayoutBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import kr.ac.jejunu.rxpractice.ui.input_salse.viewmodel.SalesInputViewModel
import org.koin.android.ext.android.inject

class SalesInputFragment : BaseFragment<SalesInputLayoutBinding>(R.layout.sales_input_layout) {
    private val viewModel : SalesInputViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputViewModel = viewModel
        initView()
    }
    private fun initView() {
        arguments?.getParcelable<TimeSchedule>("schedule")?.let { schedule ->
            Log.d("aaaas",schedule.toString())
        }
    }
}