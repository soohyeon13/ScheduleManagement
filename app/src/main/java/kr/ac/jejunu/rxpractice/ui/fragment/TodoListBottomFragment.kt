package kr.ac.jejunu.rxpractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TodoListBottomFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoListBottomViewModel

class TodoListBottomFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: TodoListBottomViewModel
    private lateinit var binding: TodoListBottomFragmentBinding
    private val DATA = "DATA"

    companion object {
        fun newInstance(date: String): TodoListBottomFragment =
            TodoListBottomFragment().apply {
                val bundle = Bundle()
                bundle.putString(DATA, date)
                this.arguments = bundle
            }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.todo_list_bottom_fragment, container, false)
        viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProviders.of(this).get(
            TodoListBottomViewModel::class.java
        )
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.todoRecycler.apply {
            layoutManager = LinearLayoutManager(this@TodoListBottomFragment.requireContext())
        }
        viewModel.getScheduleData(arguments?.get(DATA).toString())
    }
}