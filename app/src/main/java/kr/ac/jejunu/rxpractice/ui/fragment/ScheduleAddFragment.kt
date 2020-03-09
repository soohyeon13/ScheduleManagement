package kr.ac.jejunu.rxpractice.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import kotlinx.android.synthetic.main.add_schedule_fragment.view.*
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.database.AppDatabase
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.databinding.AddScheduleFragmentBinding
import kr.ac.jejunu.rxpractice.model.Description
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.ScheduleAddViewModel
import java.lang.Boolean.parseBoolean
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAddFragment :
    BaseFragment<AddScheduleFragmentBinding, ScheduleAddViewModel>(R.layout.add_schedule_fragment) {
    private val calendar = Calendar.getInstance()
    private var userName = ""
    private var phoneNum = ""
    private var selectDate = ""
    private var list = mutableListOf<Description>()
    private val DATA = "DATA"

    override fun getViewModel(): Class<ScheduleAddViewModel> = ScheduleAddViewModel::class.java
    override fun getBindingVariable(): Int = BR.scheduleAddViewModel

    override fun initView() {
        setBtnWidth()
//        if (arguments?.getSerializable("schedule") != null) {
//            val schedule = arguments?.getSerializable("schedule") as Schedule
//            val hour = schedule.cal?.get(Calendar.HOUR_OF_DAY)
//            val minute = schedule.cal?.get(Calendar.MINUTE)
//            val time = "$hour:$minute"
//            binding.userNameText.setText(schedule.name)
//            binding.titleText.setText(schedule.title)
//            binding.dateText.setText(schedule.date)
//            binding.timeText.setText(time)
//            binding.descriptionText.setText(schedule.description)
//        }
        with(viewModel) {
            clickPersonEvent.observe(this@ScheduleAddFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent, 1)
            })
            clickDateEvent.observe(this@ScheduleAddFragment, Observer {
                getDateDialog()
            })
            clickSave.observe(this@ScheduleAddFragment, Observer {
                findNavController().popBackStack()
            })
            clickCancel.observe(this@ScheduleAddFragment, Observer {
                findNavController().popBackStack()
            })
            toastShow.observe(this@ScheduleAddFragment, Observer {
                Toast.makeText(requireContext(),"정보를 입력해주세요.",Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setBtnWidth() {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        binding.saveBtn.width = (width / 3) * 2
    }

    private fun getTimeDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeDialog = TimePickerDialog(
            this.requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val time = "$hourOfDay:$minute"
                selectDate = selectDate.plus(" ").plus(time)
                binding.dateInputText.setText(selectDate)
            },
            hour,
            minute,
            false
        )
        timeDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateDialog = DatePickerDialog(
            this.requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = "yyyy-MM-dd"
                val simpleDateFormat = SimpleDateFormat(dateFormat)
                selectDate = simpleDateFormat.format(calendar.time)
                getTimeDialog()
            },
            year,
            month,
            day
        )
        dateDialog.show()
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val contactData = data?.data
            val cursor = activity!!.contentResolver.query(contactData!!, null, null, null, null)
            if (cursor!!.moveToFirst()) {
                userName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                var hasPhone =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                hasPhone = if (hasPhone.equals("1", ignoreCase = true)) "true" else "false"

                phoneNum = ""
                if (parseBoolean(hasPhone)) {
                    val phones = activity!!.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null
                    )
                    while (phones!!.moveToNext()) {
                        phoneNum =
                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    phones.close()
                }
                binding.userNameText.setText(userName)
                binding.userNumInputText.setText(phoneNum)
            }
            cursor.close()
        }
    }
}