package kr.ac.jejunu.rxpractice.ui.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.MoneyDialogLayoutBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.dialogviewmodel.SalesDialogViewModel

class SalesDialogFragment : DialogFragment() {
    private lateinit var viewModel: SalesDialogViewModel
    private lateinit var binding: MoneyDialogLayoutBinding

    companion object {
        fun newInstance(): SalesDialogFragment =
            SalesDialogFragment().apply {
            }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.money_dialog_layout, container, false)
        viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProviders.of(this)
            .get(SalesDialogViewModel::class.java)
        binding.lifecycleOwner = this
        binding.setVariable(BR.dialogViewModel, viewModel)
        isCancelable = false
        dialogView()
        initView()
        return binding.root
    }

    private fun initView() {
        with(viewModel) {
            clickCancelEvent.observe(this@SalesDialogFragment, Observer {
                dismiss()
            })
            clickCheckEvent.observe(this@SalesDialogFragment, Observer {
                Toast.makeText(requireContext(), "click check", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun dialogView() {

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.member_list,
            android.R.layout.simple_dropdown_item_1line
        ).also {
            binding.memberList.adapter = it
        }
        binding.memberList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.member_sales_list,
                            android.R.layout.simple_dropdown_item_1line
                        ).also {
                            binding.salesList.adapter = it
                        }
                    }
                    1 -> {
                        ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.non_member_sales_list,
                            android.R.layout.simple_dropdown_item_1line
                        ).also {
                            binding.salesList.adapter = it
                        }
                    }
                }
            }

        }
        binding.discount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0, 1 -> {
                        binding.discountInputText.visibility = View.VISIBLE
                    }
                }
            }

        }
    }
}