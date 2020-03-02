package kr.ac.jejunu.rxpractice.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import kr.ac.jejunu.rxpractice.BR
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import kr.ac.jejunu.rxpractice.R
import kr.ac.jejunu.rxpractice.base.BaseFragment
import kr.ac.jejunu.rxpractice.databinding.AddUserFragmentBinding
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.UserAddViewModel

class UserAddFragment :BaseFragment<AddUserFragmentBinding,UserAddViewModel>(R.layout.add_user_fragment) {
    override fun getViewModel(): Class<UserAddViewModel> {
        return UserAddViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.userAddViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(this@UserAddFragment.requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun initView() {
        with(viewModel) {
            saveUser.observe(this@UserAddFragment, Observer {
                Log.d("fragment test","test")
                val name = binding.inputName.text
                val num = binding.inputNumber.text
                if (name.isNullOrBlank() || num.isNullOrBlank()) {
                    Toast.makeText(this@UserAddFragment.requireContext(),"이름 or 전화번호 입력해 주세요",Toast.LENGTH_SHORT).show()
                    return@Observer
                }
                val user = User(userName = name.toString(),userNum = num.toString())
                saveUser(user)
                Toast.makeText(this@UserAddFragment.requireContext(),"$name 등록 완료",Toast.LENGTH_SHORT).show()
//                val action = UserAddFragmentDirections.actionUserAddFragmentToTodoFragment()
                findNavController().popBackStack()
            })
            getUser.observe(this@UserAddFragment, Observer {
                val intent = Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent,1)
            })
            cancelUser.observe(this@UserAddFragment, Observer {
                val action = UserAddFragmentDirections.actionUserAddFragmentToTodoFragment()
                findNavController().navigate(action)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val contactData = data?.data
            val cursor = activity!!.contentResolver.query(contactData!!,null,null,null,null)
            if (cursor!!.moveToFirst()) {
                val userName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var phoneNum = ""
                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                var hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                hasPhone = if (hasPhone.equals("1",ignoreCase = true)) {
                    "true"
                }else {
                    "false"
                }

                if(java.lang.Boolean.parseBoolean(hasPhone)) {
                    val phones = activity!!.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null)
                    while (phones!!.moveToNext()) {
                        phoneNum = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    phones.close()
                }
                binding.inputName.setText(userName)
                binding.inputNumber.setText(phoneNum)
            }
            cursor.close()
        }
    }
}