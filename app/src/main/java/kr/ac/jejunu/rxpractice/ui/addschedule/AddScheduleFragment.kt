package kr.ac.jejunu.rxpractice.ui.addschedule

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.provider.ContactsContract
import android.util.DisplayMetrics
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.databinding.FragmentAddScheduleBinding
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import kr.ac.jejunu.rxpractice.ui.addschedule.viewmodel.AddScheduleViewModel
import org.koin.android.ext.android.inject
import java.lang.Boolean.parseBoolean
import java.text.SimpleDateFormat
import java.util.*

class AddScheduleFragment :
    BaseFragment<FragmentAddScheduleBinding>(R.layout.fragment_add_schedule) {
    private val viewModel: AddScheduleViewModel by inject()
    private var userName = ""
    private var phoneNum = ""
    private var selectDate =""
    private val calendar = Calendar.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scheduleAddViewModel = viewModel
        initView()
        observe()
    }

    private fun initView() {
        arguments?.let {
            val schedule = it.getParcelable<TimeSchedule>("update")
            schedule?.let {s ->
                binding.userNameText.setText(s.schedule?.name)
                binding.userNumInputText.setText(s.schedule?.phoneNum)
            }
        }
        setSaveBtnWidth()
    }

    private fun setSaveBtnWidth() {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        binding.saveBtn.width = (width / 3) * 2
    }

    private fun observe() {
        with(viewModel) {
            findUser.observe(viewLifecycleOwner, Observer {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent, 1)
            })
            cancel.observe(viewLifecycleOwner, Observer {
                findNavController().popBackStack()
            })
            selectDate.observe(viewLifecycleOwner, Observer {
                callDateDialog()
            })
            save.observe(viewLifecycleOwner, Observer {
                findNavController().popBackStack()
            })
        }
    }

    private fun callTimeDialog() {
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
    private fun callDateDialog() {
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
                callTimeDialog()
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