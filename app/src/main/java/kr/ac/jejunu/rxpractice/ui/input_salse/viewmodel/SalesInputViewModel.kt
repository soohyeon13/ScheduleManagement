package kr.ac.jejunu.rxpractice.ui.input_salse.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import java.util.*

class SalesInputViewModel : BaseViewModel(){
    companion object {
        private val memberPriceList = intArrayOf(15000,25000,40000,50000,20000,30000,150000)
        private val nonMemberPriceList = intArrayOf(20000,30000,50000,60000,25000,35000,150000)
        private val contentList =  arrayOf("손 케어", "발 케어","젤 네일","젤 페디","맨즈 손","맨즈 발","홀드 핀칭")
    }
    private val won = Currency.getInstance(Locale.KOREA).symbol
    private var _memberCheck = MutableLiveData<String>()
    val memberCheck : LiveData<String>
        get() = _memberCheck
    private var _price = MutableLiveData<String>()
    val price = _price

    init {
        _memberCheck.value = "회원"
        _price.value = "$won 0"
    }

    fun onMemberCheck(view: View) {
        _memberCheck.value = view.tag.toString()
    }

}