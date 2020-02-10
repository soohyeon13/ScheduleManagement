package kr.ac.jejunu.rxpractice.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kr.ac.jejunu.rxpractice.database.RoomRepository

abstract class BaseViewModel<D> protected constructor(application: Application)  : AndroidViewModel(application){
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val roomRepository: RoomRepository = RoomRepository(application)

    val onErrorEvent : MutableLiveData<Throwable> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun addDisposable(single:Single<*>,observer:DisposableSingleObserver<*>) {
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer as SingleObserver<Any>)as Disposable)
    }

    val baseObserver : DisposableSingleObserver<String>
        get() = object : DisposableSingleObserver<String>() {
            override fun onSuccess(t: String) {
                onRetrieveBaseSuccess(t)
                isLoading.value = false
            }

            override fun onError(e: Throwable) {
                onErrorEvent.value = e
                isLoading.value = false
            }

        }

    val dataObserver : DisposableSingleObserver<D>
        get() = object : DisposableSingleObserver<D>() {
            override fun onSuccess(t: D) {
                onRetrieveDataSuccess(t)
                isLoading.value = false
            }

            override fun onError(e: Throwable) {
                onErrorEvent.value = e
                isLoading.value = false
            }
        }

    protected open fun onRetrieveDataSuccess(t: D) {
    }

    protected open fun onRetrieveBaseSuccess(t: String) {
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}