package kr.ac.jejunu.rxpractice.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import kr.ac.jejunu.rxpractice.BR
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.database.AppDatabase
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.databinding.AddScheduleFragmentBinding
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.ScheduleAddViewModel
import java.lang.Boolean.parseBoolean
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAddFragment :
    BaseFragment<AddScheduleFragmentBinding, ScheduleAddViewModel>(R.layout.add_schedule_fragment) {
    private val calendar = Calendar.getInstance()
    private var userName =""
    private var phoneNum = ""
    private val repository : RoomRepository by lazy {
        RoomRepository(activity!!.application)
    }
    override fun getViewModel(): Class<ScheduleAddViewModel> {
        return ScheduleAddViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.scheduleAddViewModel
    }

    override fun initView() {
        with(viewModel) {
            clickPersonEvent.observe(this@ScheduleAddFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent, 1)
            })
            clickDateEvent.observe(this@ScheduleAddFragment, Observer {
                getDateDialog()
            })
            clickTimeEvent.observe(this@ScheduleAddFragment, Observer {
                getTimeDialog()
            })
            clickSave.observe(this@ScheduleAddFragment, Observer {
                findNavController().popBackStack()
            })
            clickCancel.observe(this@ScheduleAddFragment, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun getTimeDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeDialog = TimePickerDialog(
            this.requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val time = "$hourOfDay:$minute"
                binding.timeText.setText(time)
            },
            hour,
            minute,
            false
        )
        timeDialog.show()
    }

    private fun getDateDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateDialog = DatePickerDialog(
            this.requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val date = "$dayOfMonth-$month-$year"
                binding.dateText.setText(date)
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
                binding.numText.text = phoneNum
            }
            cursor.close()
        }
    }
}