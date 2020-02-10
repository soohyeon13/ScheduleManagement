package kr.ac.jejunu.rxpractice.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewDataBinding>
    (
    private val layoutId : Int
) : AppCompatActivity() {
    lateinit var binding : T
    private val compositeDisposable = CompositeDisposable()

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,layoutId)
        binding.setLifecycleOwner { this.lifecycle }
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