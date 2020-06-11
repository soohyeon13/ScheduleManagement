package kr.ac.jejunu.rxpractice.ui.input_salse.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.ui.input_salse.data.Salse
import java.text.DecimalFormat

class SalesInputViewModel : BaseViewModel() {
    private val memberPriceList = intArrayOf(15000, 25000, 40000, 50000, 20000, 30000, 150000)
    private val nonMemberPriceList = intArrayOf(20000, 30000, 50000, 60000, 25000, 35000, 150000)
    private val contentList =
        arrayOf("handCare", "footCare", "GelNail", "GelPedi", "GelRemove", "Repair", "HoldPunching")

    companion object {
        const val HANDCARE = "handCare"
        const val FOOTCARE = "footCare"
        const val GELNAIL = "GelNail"
        const val GELPEDI = "GelPedi"
        const val GELREMOVE = "GelRemove"
        const val REPAIR = "Repair"
        const val HOLDPUNCHING = "HoldPunching"
    }

    val checkContents: Salse
    private val emptyContents: Salse
        get() {
            val content = mutableMapOf(
                HANDCARE to false,
                FOOTCARE to false,
                GELNAIL to false,
                GELPEDI to false,
                GELREMOVE to false,
                REPAIR to false,
                HOLDPUNCHING to false
            )
            return Salse(content)
        }
    private var _memberCheck = MutableLiveData<String>()
    val memberCheck: LiveData<String>
        get() = _memberCheck
    private var _price = MutableLiveData<String>()
    val price = _price

    init {
        _memberCheck.value = "회원"
        _price.value = "0 원"
        checkContents = emptyContents
    }

    fun onMemberCheck(view: View) {
        _memberCheck.value = view.tag.toString()
    }

    fun isApply() {
        val finalPrice = StringBuilder()
        val format = DecimalFormat("#,###")
        val priceConvert: Double
        if (memberCheck.value == "회원") {
            priceConvert = priceMeasure(memberPriceList).toDouble()
        } else {
            priceConvert = priceMeasure(nonMemberPriceList).toDouble()
        }
        _price.value = finalPrice.append(format.format(priceConvert)).append(" 원").toString()
    }

    private fun priceMeasure(priceList: IntArray): Int {
        var p = 0
        for (content in contentList) {
            if (checkContents[content]) {
                p += priceList[contentList.indexOf(content)]
            }
        }
        return p
    }
}