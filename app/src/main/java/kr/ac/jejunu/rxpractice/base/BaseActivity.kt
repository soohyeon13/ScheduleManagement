package kr.ac.jejunu.rxpractice.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewDataBinding,VM:BaseViewModel<*>>
    (
    private val layoutId : Int
) : AppCompatActivity() {
    protected lateinit var binding : T
    protected lateinit var viewModel : VM

    private val compositeDisposable = CompositeDisposable()

    protected abstract fun getViewModel() : Class<VM>
    protected abstract fun getBindingVariable() : Int
    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,layoutId)
        this.viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProviders.of(this).get(getViewModel())
        binding.setVariable(getBindingVariable(),viewModel)
        binding.lifecycleOwner = this
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


//    protected fun showProgress() {
//        progress.visibility = VISIBLE
//    }
//
//    protected fun hideProgress() {
//        progress.visibility = GONE
//    }


}