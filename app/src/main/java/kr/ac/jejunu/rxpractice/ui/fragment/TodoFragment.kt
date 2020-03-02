package kr.ac.jejunu.rxpractice.ui.fragment

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.databinding.TodoFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment<TodoFragmentBinding, TodoViewModel>(R.layout.todo_fragment) {
    private var isOpen = false
    private val repository: RoomRepository by lazy {
        RoomRepository(activity!!.application)
    }

    override fun getViewModel(): Class<TodoViewModel> {
        return TodoViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.todoViewModel
    }

    override fun initView() {
        val event: List<Calendar> = repository.getAllSchedules()
        val list: MutableList<EventDay> = mutableListOf()
        for (i in event.indices) {
            list.add(EventDay(event[i], R.drawable.ic_add))
        }
        binding.calendar.setEvents(list)
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
        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDayClick(eventDay: EventDay) {
                val cal: Calendar = eventDay.calendar
                val dateFormat = "yyyy-MM-dd"
                val simpleDateFormat = SimpleDateFormat(dateFormat)
                val selectDay = simpleDateFormat.format(cal.time)
                val fragment = TodoListBottomFragment.newInstance(selectDay)
                fragment.show(childFragmentManager, "todo_list_fragment")
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
                    val extras =
                        FragmentNavigatorExtras(binding.addPerson to binding.addPerson.transitionName)
                    findNavController().navigate(action, extras)
                }
                binding.addSchedule.setOnClickListener {
                    val action = TodoFragmentDirections.actionTodoFragmentToScheduleAddFragment()
                    findNavController().navigate(action)
                }
            })

        }
    }
}