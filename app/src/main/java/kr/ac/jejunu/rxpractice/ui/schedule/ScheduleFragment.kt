package kr.ac.jejunu.rxpractice.ui.schedule

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.FragmentScheduleBinding
import kr.ac.jejunu.rxpractice.ui.schedule.viewmodel.ScheduleViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class ScheduleFragment
    : BaseFragment<FragmentScheduleBinding>(R.layout.fragment_schedule){
    companion object {
        private val TAG = "ScheduleFragment"
    }
    private var isOpen = false
    private val viewModel: ScheduleViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scheduleViewModel = viewModel
        initView()
        observe()
    }

    private fun initView() {
        with(binding.calendar) {
            setOnDayClickListener(object :OnDayClickListener {
                @SuppressLint("SetTextI18n", "SimpleDateFormat")
                override fun onDayClick(eventDay: EventDay) {
                    binding.scrollView.apply {
                        requestChildFocus(binding.childView,binding.timeTable.timeTableContainer)
                        smoothScrollToView(binding.timeTable.timeTableContainer)
                    }
                    val simple = SimpleDateFormat("yyyy-MM-dd")
                    val str = "${eventDay.calendar.get(Calendar.YEAR)}-" +
                            "${eventDay.calendar.get(Calendar.MONTH)+1}-" +
                            "${eventDay.calendar.get(Calendar.DAY_OF_MONTH)}"
                    val date : Date = simple.parse(str)
                    viewModel.getDaySchedule(date)
                }
            })
        }
    }

    private fun observe() {
        with(viewModel) {
            fabClick.observe(viewLifecycleOwner, Observer {
                animation()
            })
            fabSchedule.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.action_scheduleFragment_to_addScheduleFragment)
            })
            scheduleLiveData.observe(viewLifecycleOwner, Observer {cal ->
                val eventDays = cal.map { EventDay(toCalendar(it),R.drawable.ic_icon) }
                binding.calendar.setEvents(eventDays)
            })
            dayScheduleLiveData.observe(viewLifecycleOwner, Observer {schedule ->
                schedule.forEach {
                    Log.d(TAG,"time ${it.time}")
                    Log.d(TAG,"date ${it.date}")
                }
            })
        }
    }

    private fun animation() {
        val fabOpen =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        val fabClose =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)
        val fabRClockwise =
            AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        val fabRAntiClockwise =
            AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anticlockwise)
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
    }
    private fun ScrollView.computeDistanceToView(view:View) : Int {
        return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }
    private fun calculateRectOnScreen(view:View) :Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(location[0],location[1],location[0] + view.measuredWidth,location[1]+view.measuredHeight)
    }
    private fun ScrollView.smoothScrollToView(view: View) {
        val y = computeDistanceToView(view)
        ObjectAnimator.ofInt(this,"scrollY",y).apply {
            duration = 1000L
        }.start()
    }
    private fun toCalendar(date : Date) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }
}