package kr.ac.jejunu.rxpractice.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import kr.ac.jejunu.rxpractice.util.DBHandler

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel<*>>(
    private val layoutId: Int
) : Fragment() {
    protected lateinit var binding: T
    protected lateinit var viewModel: VM
    protected lateinit var handler : DBHandler
    private val compositeDisposable = CompositeDisposable()
//    private val adapter = TodoListAdapter()

    protected abstract fun getViewModel() : Class<VM>
    protected abstract fun getBindingVariable() : Int
    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProviders.of(this).get(getViewModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        initView()
    }

    private fun setUp() {
        binding.setVariable(getBindingVariable(),viewModel)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

//    protected fun showSchedule(schedule:List<Schedule>, context:Context) {
//        if (schedule.isEmpty()) {
//            Toast.makeText(context,"일정이 없습니다.",Toast.LENGTH_LONG).show()
//        }
//        adapter.setSchedules(schedule = schedule)
//    }

//    protected fun showProgress() {
//        progress.visibility = VISIBLE
//    }
//
//    protected fun hideProgress() {
//        progress.visibility = GONE
//    }

    companion object {
        fun <T : Fragment> newInstance(fragment : T) : T {
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }
}