package kr.ac.jejunu.rxpractice.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TodoListBottomFragmentBinding
import kr.ac.jejunu.rxpractice.ui.adapter.TodoListAdapter
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoListBottomViewModel

class TodoListBottomFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel : TodoListBottomViewModel
    private lateinit var binding : TodoListBottomFragmentBinding
    private val todoAdapter = TodoListAdapter()
    private val DATA = "DATA"
    companion object {
        fun newInstance(date : String): TodoListBottomFragment =
            TodoListBottomFragment().apply {
                val bundle = Bundle()
                bundle.putString(DATA,date)
                this.arguments = bundle
            }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProviders.of(this).get(TodoListBottomViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.todo_list_bottom_fragment, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.todoRecycler.apply {
            layoutManager = LinearLayoutManager(this@TodoListBottomFragment.requireContext())
            adapter = todoAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }



}