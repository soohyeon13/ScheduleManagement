package kr.ac.jejunu.rxpractice.ui.fragment

import android.app.DatePickerDialog
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.TodoFragmentBinding
import kr.ac.jejunu.rxpractice.ui.adapter.TodoListAdapter
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController

class TodoFragment : BaseFragment<TodoFragmentBinding, TodoViewModel>(R.layout.todo_fragment) {
    private val todoAdapter = TodoListAdapter()
    private val today = LocalDate.now()
    private val formatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd")
    private val formatted = today
        .format(formatter)
    private val cal = Calendar.getInstance()
    private var isOpen = false
    override fun getViewModel(): Class<TodoViewModel> {
        return TodoViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.viewmodel
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

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@TodoFragment.requireContext(), RecyclerView.VERTICAL, true)
            adapter = todoAdapter
        }
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { v, y, m, d ->
                cal.set(Calendar.YEAR, y)
                cal.set(Calendar.MONTH, m)
                cal.set(Calendar.DAY_OF_MONTH, d)
                updateDateView()
            }
        with(viewModel) {
            clickDate.observe(this@TodoFragment, Observer {
                Log.d("test", "call observer")
                DatePickerDialog(
                    this@TodoFragment.requireContext(),
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
                getSchedule("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}=${cal.get(Calendar.DAY_OF_MONTH)}")
            })
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

    private fun updateDateView() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
        binding.todayDate.text = sdf.format(cal.time)
    }
}


//    override fun initView() {
//        binding.recyclerView.apply {
//            layoutManager =
//                LinearLayoutManager(this@TodoFragment.requireContext(), RecyclerView.VERTICAL, true)
//            adapter = todoAdapter
//        }
//        binding.todayDate.text = formatted
//
//        val dateSetListener =
//            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                cal.set(Calendar.YEAR,year)
//                cal.set(Calendar.MONTH,month)
//                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
//                updateDateInView()
//            }
//
//        binding.todayDate.setOnClickListener {
//            DatePickerDialog(this.requireContext(),
//                dateSetListener,
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH)).show()
//        }
//    }

//    private fun updateDateInView() {
//        val myFormat = "yyyy-MM-dd"
//        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
//        binding.todayDate.text = sdf.format(cal.time)
//
//    }


//    @SuppressLint("NewApi")
//    override fun onStart() {
//        super.onStart()
//        val dateChangeStream = dateChangeObservable().toFlowable(BackpressureStrategy.BUFFER)
//        disposable = dateChangeStream.observeOn(AndroidSchedulers.mainThread())
//            .doOnNext{showProgress()}
//            .observeOn(Schedulers.io())
//            .map { handler.getSchedule(todayDate.toString())}
//            .doOnError { Log.d("aaa",it.message) }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                hideProgress()
//                showSchedule(it,this.requireContext())
//            }
//
//
//    }
//
//    private fun dateChangeObservable(): Observable<String> {
//        return Observable.create { emitter ->
//            val textWatcher = object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) = Unit
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) = Unit
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    s?.toString()?.let { emitter.onNext(it)}
//                }
//            }
//            todayDate.addTextChangedListener(textWatcher)
//        }
//    }
//
//
//    override fun onStop() {
//        super.onStop()
//    }