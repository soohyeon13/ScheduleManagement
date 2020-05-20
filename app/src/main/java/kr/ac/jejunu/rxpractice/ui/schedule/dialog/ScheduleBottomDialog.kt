package kr.ac.jejunu.rxpractice.ui.schedule.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TimeScheduleDialogLayoutBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule

class ScheduleBottomDialog : BottomSheetDialogFragment() {
    private lateinit var binding: TimeScheduleDialogLayoutBinding

    companion object {
        fun newInstance(data: TimeSchedule): ScheduleBottomDialog {
            val bundle = Bundle()
            bundle.putParcelable("data", data)
            val fragment = ScheduleBottomDialog()
            fragment.arguments = bundle
            return fragment
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
            R.layout.time_schedule_dialog_layout,
            container,
            false
        )
        arguments?.getParcelable<TimeSchedule>("data")?.let { item ->
            binding.updateSchedule.setOnClickListener {
                val arg = Bundle()
                arg.putParcelable("update", item)
                findNavController().navigate(R.id.action_scheduleFragment_to_addScheduleFragment, arg)
            }
        }
        binding.deleteSchedule.setOnClickListener {
            Toast.makeText(requireContext(), "delete click", Toast.LENGTH_SHORT).show()
        }
        binding.cancel.setOnClickListener { dismiss() }
        binding.goSales.setOnClickListener {
            Toast.makeText(requireContext(), "go sales", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}