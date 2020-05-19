package kr.ac.jejunu.rxpractice.ui.schedule

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.databinding.FragmentScheduleBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import kr.ac.jejunu.rxpractice.ui.schedule.adapter.TimeAdapter
import kr.ac.jejunu.rxpractice.ui.schedule.listener.OnItemClickListener
import kr.ac.jejunu.rxpractice.ui.schedule.viewmodel.ScheduleViewModel
import kr.ac.jejunu.rxpractice.util.RVDivider
import org.koin.android.ext.android.inject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class ScheduleFragment
    : BaseFragment<FragmentScheduleBinding>(R.layout.fragment_schedule) {
    companion object {
        private val TAG = "ScheduleFragment"
    }

    private val timeAdapter: TimeAdapter by inject()
    private val timeArr = ArrayList<TimeSchedule>()
    private val todayEndFormat = arrayOf("년", "월", "일")
    private val baseFormat = SimpleDateFormat("yyyy-MM-dd")
    private var isOpen = false
    private var isStartCheck = false
    private val viewModel: ScheduleViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scheduleViewModel = viewModel
        initView()
        observe()
    }

    private fun initView() {
        binding.dayRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = timeAdapter
            setHasFixedSize(true)
        }
        binding.todayText.text = getToday(Calendar.getInstance().time)
        with(binding.calendar) {
            setOnDayClickListener(object : OnDayClickListener {
                override fun onDayClick(eventDay: EventDay) {
                    viewModel.getDaySchedule(selectDay(eventDay.calendar)!!)
                }
            })
        }
        timeAdapter.setOnItemClickListener(object : OnItemClickListener<TimeSchedule> {
            override fun onItemClick(item: TimeSchedule?, position: Int) {
                showDialog(item, position)
            }
        })
    }

    private fun showDialog(item: TimeSchedule?, position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("목록")
        if (item?.schedule == null) {
            builder.setItems(R.array.empty_time_schedule_content) { _, pos ->
                findNavController().navigate(R.id.action_scheduleFragment_to_addScheduleFragment)
            }
        } else {
            builder.setItems(R.array.time_schedule_content_list) { dialog, pos ->
                val bundle = Bundle()
                bundle.putParcelable("schedule", item)
                when (pos) {
                    0 -> {
                        //Todo pass data Object using bundle or argument
                        findNavController()
                            .navigate(R.id.action_scheduleFragment_to_addScheduleFragment, bundle)
                    }
                    1 -> {
                        checkRemove(item, position)
                        dialog.dismiss()
                    }
                    2 -> {
                        Toast.makeText(requireContext(), "정산 click", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun checkRemove(item: TimeSchedule, position: Int) {
        val time = item.time
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("정말 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { dialog, _ ->
            item.schedule?.let { viewModel.removeSchedule(it) }
            timeArr[position] = TimeSchedule(time)
            timeAdapter.setSchedules(timeArr)
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create()
        builder.show()
    }

    private fun selectDay(select: Calendar): Date? {
        val year = select.get(Calendar.YEAR)
        val month = select.get(Calendar.MONTH) + 1
        val day = select.get(Calendar.DAY_OF_MONTH)
        val convertDay = "$year-$month-$day"
        return baseFormat.parse(convertDay)
    }

    private fun observe() {
        viewModel.getDaySchedule(selectDay(Calendar.getInstance())!!)
        viewModel.getMonthSchedule()
        with(viewModel) {
            fabClick.observe(viewLifecycleOwner, Observer {
                animation()
            })
            fabSchedule.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.action_scheduleFragment_to_addScheduleFragment)
            })
            //load month item
            scheduleLiveData.observe(viewLifecycleOwner, Observer { cal ->
                val eventDays = cal.map { EventDay(toCalendar(it), R.drawable.ic_icon) }
                binding.calendar.setEvents(eventDays)
            })
            //load day item
            dayScheduleLiveData.observe(viewLifecycleOwner, Observer { schedules ->
                getTodayItems(schedules)
            })
        }
    }

    private fun getTodayItems(schedules: List<Schedule>) {
        timeArr.clear()
        val sortSchedules = schedules.sortedBy { it.time }
        val cal = Calendar.getInstance()
        for (i in 10..21) {
            val time = StringBuilder()
            var check = true
            var currentTime = 0
            if (i in 10..11) time.append("AM").append(" ")
            else time.append("PM").append(" ")
            if (i % 12 == 0) currentTime = 12
            else currentTime = i % 12
            time.append(currentTime)
            for (schedule in sortSchedules) {
                cal.time = schedule.time
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                if (hour == i) {
                    timeArr.add(
                        TimeSchedule(
                            time.toString(), schedule
                        )
                    )
                    check = false
                    break
                }
            }
            if (check) timeArr.add(TimeSchedule(time.toString()))
        }
        timeAdapter.setSchedules(timeArr)
        if (schedules.isNotEmpty()) {
            binding.todayText.text = schedules.first().date?.let { getToday(it) }
        }
        if (isStartCheck) {
            binding.scrollView.apply {
                requestChildFocus(binding.childView, binding.dayRecyclerView)
                smoothScrollToView(binding.dayRecyclerView)
            }
        }
        isStartCheck = true
    }

    private fun getToday(date: Date): String {
        val todayFrontFormat = baseFormat.format(date).split("-")
        val today = StringBuilder()
        for (i in todayFrontFormat.indices) {
            today.append("${todayFrontFormat[i]} ${todayEndFormat[i]}")
        }
        return today.toString()
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

    private fun ScrollView.computeDistanceToView(view: View): Int {
        return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }

    private fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }

    private fun ScrollView.smoothScrollToView(view: View) {
        val y = computeDistanceToView(view)
        ObjectAnimator.ofInt(this, "scrollY", y).apply {
            duration = 1000L
        }.start()
    }

    private fun toCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }
}