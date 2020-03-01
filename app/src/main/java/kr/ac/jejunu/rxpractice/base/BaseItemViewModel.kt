package kr.ac.jejunu.rxpractice.base

import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import java.lang.ref.WeakReference

abstract class BaseItemViewModel<T,N> :ViewModel() {
    private lateinit var navigator : WeakReference<N>

    fun getNavigator() : N {
        return navigator.get()!!
    }

    fun setNavigator(navigator:N) {
        this.navigator = WeakReference(navigator)
    }

    abstract fun bind(data:T)
}