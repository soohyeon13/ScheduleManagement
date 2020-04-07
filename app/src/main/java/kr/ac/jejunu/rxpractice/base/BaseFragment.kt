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

abstract class BaseFragment<T : ViewDataBinding>(
    private val layoutId: Int
) : Fragment() {
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
    companion object {
        fun <T : Fragment> newInstance(fragment : T) : T {
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }
}