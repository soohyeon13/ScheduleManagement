package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.dialogviewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.money_dialog_layout.view.*
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent

class SalesDialogViewModel(application:Application) : AndroidViewModel(application) {
    val clickCheckEvent = SingleLiveEvent<Unit>()
    val clickCancelEvent = SingleLiveEvent<Unit>()
    private var _totalMoney = MutableLiveData<String>()
    val totalMoney : LiveData<String>
        get() =_totalMoney


    fun onCheckClick() {
        clickCheckEvent.call()
    }

    fun onCancelClick() {
        clickCancelEvent.call()
    }
}