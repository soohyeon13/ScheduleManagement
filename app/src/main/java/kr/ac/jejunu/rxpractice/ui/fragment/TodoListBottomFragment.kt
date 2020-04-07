package kr.ac.jejunu.rxpractice.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.money_dialog_layout.view.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.TodoListBottomFragmentBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.fragment.dialog.SalesDialogFragment
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoListBottomViewModel

class TodoListBottomFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: TodoListBottomViewModel
    private lateinit var binding: TodoListBottomFragmentBinding
    private lateinit var controller: LayoutAnimationController
    private val DATA = "DATA"
    private val loadingDuration: Long
        get() = (600 / animationPlaybackSpeed).toLong()
    private var animationPlaybackSpeed: Double = 0.8

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
            runAnimation(this)
        }
        updateRecyclerViewAnimDuration()
        with(viewModel) {
            this.getScheduleData(arguments?.get(DATA).toString())
            clickEvent.observe(this@TodoListBottomFragment, Observer {
                val bundle = Bundle()
                bundle.putSerializable("schedule", it)
                findNavController().navigate(
                    R.id.action_todoFragment_to_scheduleAddFragment,
                    bundle
                )
            })
            cancelClickEvent.observe(this@TodoListBottomFragment, Observer {
                this@TodoListBottomFragment.dismiss()
            })
            clickMoneyEvent.observe(this@TodoListBottomFragment, Observer {
                val dialogFragment = SalesDialogFragment.newInstance()
                dialogFragment.show(childFragmentManager,"dialog")
            })
        }
    }

    private fun updateRecyclerViewAnimDuration() = binding.todoRecycler.itemAnimator?.run {
        removeDuration = loadingDuration * 60 / 100
        addDuration = loadingDuration
    }

    private fun runAnimation(recyclerview: RecyclerView) {
        controller = AnimationUtils.loadLayoutAnimation(
            this@TodoListBottomFragment.requireContext(),
            R.anim.layout_animation
        )
        recyclerview.layoutAnimation = controller
        recyclerview.scheduleLayoutAnimation()
    }
}