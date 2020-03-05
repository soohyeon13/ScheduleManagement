package kr.ac.jejunu.rxpractice.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TodoListBottomFragmentBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoListBottomViewModel
import kr.ac.jejunu.rxpractice.util.OnItemClickListener

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
        with(viewModel) {
            this.getScheduleData(arguments?.get(DATA).toString())
            clickEvent.observe(this@TodoListBottomFragment, Observer {
                val bundle = Bundle()
                bundle.putSerializable("schedule",it)
                findNavController().navigate(R.id.action_todoFragment_to_scheduleAddFragment,bundle)
                Log.d("singleLiveEvent test",it.name)
            })
            cancelClickEvent.observe(this@TodoListBottomFragment, Observer {
                this@TodoListBottomFragment.dismiss()
            })
        }
    }
}