package kr.ac.jejunu.rxpractice.ui.schedule.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TimeScheduleEmptyDialogLayoutBinding

class ScheduleBottomEmpty : BottomSheetDialogFragment() {
    private lateinit var binding: TimeScheduleEmptyDialogLayoutBinding

    companion object {
        fun newInstance(): ScheduleBottomEmpty {
            return ScheduleBottomEmpty()
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.time_schedule_empty_dialog_layout,
            container,
            false
        )
        binding.addSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_addScheduleFragment)
        }
        binding.cancel.setOnClickListener { dismiss() }
        return binding.root
    }
}