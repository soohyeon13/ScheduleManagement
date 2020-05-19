package kr.ac.jejunu.rxpractice.base

import androidx.lifecycle.ViewModel

abstract class BaseItemViewModel<T> : ViewModel() {
    abstract fun bind(data:T)
}