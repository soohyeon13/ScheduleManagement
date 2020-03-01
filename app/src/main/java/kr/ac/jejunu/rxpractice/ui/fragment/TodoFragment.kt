package kr.ac.jejunu.rxpractice.ui.fragment

import android.util.Log
import android.view.animation.AnimationUtils
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import io.reactivex.disposables.CompositeDisposable
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.databinding.TodoFragmentBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoViewModel
import java.util.*

class TodoFragment : BaseFragment<TodoFragmentBinding, TodoViewModel>(R.layout.todo_fragment) {
    private var isOpen = false
    private val event : LiveData<List<Schedule>> = null

    override fun getViewModel(): Class<TodoViewModel> {
        return TodoViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.todoViewModel
    }

    override fun initView() {
        val fabOpen =
            AnimationUtils.loadAnimation(this@TodoFragment.requireContext(), R.anim.fab_open)
        val fabClose =
            AnimationUtils.loadAnimation(this@TodoFragment.requireContext(), R.anim.fab_close)
        val fabRClockwise = AnimationUtils.loadAnimation(
            this@TodoFragment.requireContext(),
            R.anim.rotate_clockwise
        )
        val fabRAntiClockwise = AnimationUtils.loadAnimation(
            this@TodoFragment.requireContext(),
            R.anim.rotate_anticlockwise
        )
        val calendar = Calendar.getInstance()
        Log.d("teste",calendar.toString())

        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val fragment = TodoListBottomFragment.newInstance(eventDay.toString())
                fragment.show(childFragmentManager,"todo_list_fragment")
                Log.d("test",eventDay.calendar.time.toString())
//                val event: List<EventDay> = arrayListOf(EventDay(eventDay.calendar,R.drawable.favorite_selector))
//                var select : Calendar = binding.calendar.firstSelectedDate
//                Log.d("test",select.toString())
            }
        })
        with(viewModel) {
            clickFab.observe(this@TodoFragment, Observer {
                if (isOpen) {
                    binding.addPerson.startAnimation(fabClose)
                    binding.addSchedule.startAnimation(fabClose)
                    binding.addFab.startAnimation(fabRClockwise)

                    isOpen = false
                } else {
                    binding.addPerson.startAnimation(fabOpen)
                    binding.addSchedule.startAnimation(fabOpen)
                    binding.addFab.startAnimation(fabRAntiClockwise)

                    binding.addPerson.isClickable
                    binding.addSchedule.isClickable

                    isOpen = true
                }

                binding.addPerson.setOnClickListener {
                    val action = TodoFragmentDirections.actionTodoFragmentToUserAddFragment()
                    val extras = FragmentNavigatorExtras(binding.addPerson to binding.addPerson.transitionName)
                    findNavController().navigate(action,extras)
                }
                binding.addSchedule.setOnClickListener {
                    val action = TodoFragmentDirections.actionTodoFragmentToScheduleAddFragment()
                    findNavController().navigate(action)
                }
            })

        }
    }
}