package kr.ac.jejunu.rxpractice.ui.schedule

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TimeScheduleDialogLayoutBinding

class ScheduleBottomDialog : BottomSheetDialogFragment() {
    private lateinit var binding: TimeScheduleDialogLayoutBinding
    companion object {
        fun newInstance() : ScheduleBottomDialog {
            return ScheduleBottomDialog()

        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(),theme     )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.time_schedule_dialog_layout,
            container,
            false
        )
        return binding.root
    }
}