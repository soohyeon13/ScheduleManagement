package kr.ac.jejunu.rxpractice.ui.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.TodoFragmentBinding
import kr.ac.jejunu.rxpractice.ui.fragment.TodoFragmentDirections
import kr.ac.jejunu.rxpractice.ui.fragment.TodoListBottomFragment
import kr.ac.jejunu.rxpractice.ui.todo.viewmodel.TodoViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment<TodoFragmentBinding>(R.layout.todo_fragment) {
    private val viewModel : TodoViewModel by inject()
    private var isOpen = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun observe() {
        val fabOpen =
            AnimationUtils.loadAnimation(requireContext(),R.anim.fab_open)
        val fabClose =
            AnimationUtils.loadAnimation(requireContext(),R.anim.fab_close)
        val fabRClockwise =
            AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
        val fabRAntiClockwise =
            AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_anticlockwise)
        with(viewModel) {
            clickFab.observe(viewLifecycleOwner, Observer {
                if(isOpen) {
                    binding.addPerson.startAnimation(fabClose)
                    binding.addSchedule.startAnimation(fabClose)
                    binding.addFab.startAnimation(fabRClockwise)
                    isOpen = false
                } else  {
                    binding.addPerson.startAnimation(fabOpen)
                    binding.addSchedule.startAnimation(fabOpen)
                    binding.addFab.startAnimation(fabRAntiClockwise)
                    binding.addPerson.isClickable
                    binding.addSchedule.isClickable
                    isOpen = true
                }
                fabClickAction()
            })
        }
    }

    private fun fabClickAction() {
        binding.addPerson.setOnClickListener {
            val action = TodoFragmentDirections.actionTodoFragmentToUserAddFragment()
            val extras =
                FragmentNavigatorExtras(binding.addPerson to binding.addPerson.transitionName)
            findNavController().navigate(action,extras)
        }
        binding.addSchedule.setOnClickListener {
            val action = TodoFragmentDirections.actionTodoFragmentToScheduleAddFragment()
            findNavController().navigate(action)
        }
    }

    private fun initView() {
        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDayClick(eventDay: EventDay) {
                val cal: Calendar = eventDay.calendar
                if (event.contains(cal)) {
                    val dateFormat = "yyyy-MM-dd"
                    val simpleDateFormat = SimpleDateFormat(dateFormat)
                    val selectDay = simpleDateFormat.format(cal.time)
                    val fragment = TodoListBottomFragment.newInstance(selectDay)
                    fragment.show(childFragmentManager, "todo_list_fragment")
                } else {
                    Toast.makeText(
                        this@TodoFragment.requireContext(),
                        "일정이 등록되어 있지 않습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


//    override fun initView() {
//        val event: List<Calendar> = repository.getAllSchedules()
//        val list: MutableList<EventDay> = mutableListOf()
//        for (i in event.indices) {
//            list.add(EventDay(event[i], R.drawable.ic_icon))
//        }
//        binding.calendar.setEvents(list)

//        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
//            @SuppressLint("SimpleDateFormat")
//            override fun onDayClick(eventDay: EventDay) {
//                val cal: Calendar = eventDay.calendar
//                if (event.contains(cal)) {
//                    val dateFormat = "yyyy-MM-dd"
//                    val simpleDateFormat = SimpleDateFormat(dateFormat)
//                    val selectDay = simpleDateFormat.format(cal.time)
//                    val fragment =
//                        TodoListBottomFragment.newInstance(
//                            selectDay
//                        )
//                    fragment.show(childFragmentManager, "todo_list_fragment")
//                } else {
//                    Toast.makeText(
//                        this@TodoFragment.requireContext(),
//                        "일정이 등록되어 있지 않습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        })
//        with(viewModel) {
//            clickFab.observe(this@TodoFragment, Observer {
//                if (isOpen) {
//                    binding.addPerson.startAnimation(fabClose)
//                    binding.addSchedule.startAnimation(fabClose)
//                    binding.addFab.startAnimation(fabRClockwise)
//
//                    isOpen = false
//                } else {
//                    binding.addPerson.startAnimation(fabOpen)
//                    binding.addSchedule.startAnimation(fabOpen)
//                    binding.addFab.startAnimation(fabRAntiClockwise)
//
//                    binding.addPerson.isClickable
//                    binding.addSchedule.isClickable
//
//                    isOpen = true
//                }
//
//                binding.addPerson.setOnClickListener {
//                    val action =
//                        TodoFragmentDirections.actionTodoFragmentToUserAddFragment()
//                    val extras =
//                        FragmentNavigatorExtras(binding.addPerson to binding.addPerson.transitionName)
//                    findNavController().navigate(action, extras)
//                }
//                binding.addSchedule.setOnClickListener {
//                    val action =
//                        TodoFragmentDirections.actionTodoFragmentToScheduleAddFragment()
//                    findNavController().navigate(action)
//                }
//            })
//
//        }
//    }
}