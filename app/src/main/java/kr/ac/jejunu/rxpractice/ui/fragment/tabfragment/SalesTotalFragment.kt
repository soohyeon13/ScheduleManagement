package kr.ac.jejunu.rxpractice.ui.fragment.tabfragment

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.sales_total_fragment.view.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.databinding.SalesTotalFragmentBinding
import kr.ac.jejunu.rxpractice.model.Sales
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.tabviewmodel.SalesTotalViewModel
import java.util.*
import kotlin.collections.ArrayList

class SalesTotalFragment :
    BaseFragment<SalesTotalFragmentBinding, SalesTotalViewModel>(R.layout.sales_total_fragment) {
    private var xAxisData = ArrayList<Entry>()
    private val xAxisValue =
        arrayListOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
    private val repository: RoomRepository by lazy {
        RoomRepository(activity!!.application)
    }

    override fun getViewModel(): Class<SalesTotalViewModel> = SalesTotalViewModel::class.java

    override fun getBindingVariable(): Int = BR.totalViewModel

    override fun initView() {
        setChart()
    }

    private fun setChart() {
        getData()
        val lineDataSet = LineDataSet(xAxisData,"총 금액")
        lineDataSet.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = resources.getColor(R.color.colorBasic)
            setCircleColor(resources.getColor(R.color.colorAccent))
            valueTextSize = 10f
            lineWidth = 2f
            circleRadius = 3f
            fillAlpha =0
            fillColor = resources.getColor(R.color.colorBasic)
            setDrawValues(false)
        }
        val xAxis = binding.lineChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 10f
            setDrawGridLines(false)
            valueFormatter = IndexAxisValueFormatter(xAxisValue)
            setLabelCount(12,true)
            granularity =1f
        }
        binding.lineChart.apply {
            description =null
            axisRight.isEnabled = false
            axisLeft.axisMaximum = 50000f
            legend.apply {
                textSize = 15f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
        }
        val lineData = LineData(lineDataSet)
        binding.lineChart.data = lineData
    }

    private fun getData() {
        for (i in 0 until xAxisValue.size) {
            var money: Int = 0
            val index = i.toFloat()
            Log.d("test",index.toString())
            val monthData = repository.getMoney(xAxisValue[i])
            if (monthData.isNotEmpty()) {
                for (j in monthData.indices) {
                    money += monthData[j].money
                }

                xAxisData.add(Entry(index,money.toFloat()))
            } else {
                xAxisData.add(Entry(index,0f))
            }
        }
        Collections.sort(xAxisData,EntryXComparator())
    }
}





























