package kr.ac.jejunu.rxpractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.TodoListTableBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoTableViewModel

class TodoTableFragment :BottomSheetDialogFragment() {
    private lateinit var binding:TodoListTableBinding
    private lateinit var viewModel: TodoTableViewModel
    private val DATA = "DATA"
    companion object {
        fun newInstance(data:String) : TodoTableFragment =
            TodoTableFragment().apply {
                val bundle = Bundle()
                bundle.putString(DATA,data)
                this.arguments = bundle
            }
    }
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.todo_list_table,container,false)
        viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProviders.of(this).get(TodoTableViewModel::class.java)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel,viewModel)
        initViews()
        return binding.root
    }

    private fun initViews() {

    }
}