package kr.ac.jejunu.rxpractice.ui.fragment

import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.AddScheduleFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.ScheduleAddViewModel

class ScheduleAddFragment : BaseFragment<AddScheduleFragmentBinding, ScheduleAddViewModel>(R.layout.add_schedule_fragment) {
    override fun getViewModel(): Class<ScheduleAddViewModel> {
        return ScheduleAddViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.viewmodel
    }

    override fun initView() {
    }
}