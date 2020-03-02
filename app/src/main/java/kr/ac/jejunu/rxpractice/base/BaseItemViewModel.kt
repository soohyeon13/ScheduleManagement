package kr.ac.jejunu.rxpractice.base

import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import java.lang.ref.WeakReference

abstract class BaseItemViewModel<T> : ViewModel() {
    abstract fun bind(data: T)
}