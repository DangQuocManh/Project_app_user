package com.quocmanh.appproject.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.quocmanh.appproject.R
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ChangePassFragment : Fragment(), View.OnClickListener {
    private lateinit var edtPassOld : EditText
    private lateinit var edtPassNew : EditText
    private lateinit var edtPassAgain : EditText
    private lateinit var callApi: CallApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mView : View
    private lateinit var btnChangePass : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_change_pass, container, false)
        init(mView)
        return mView
    }
    private fun init(view : View) {
        edtPassAgain = view.findViewById(R.id.et_pass_new_again)
        edtPassOld = view.findViewById(R.id.et_pass_old)
        edtPassNew = view.findViewById(R.id.et_pass_new)
        btnChangePass = view.findViewById(R.id.btn_change_pass)
        btnChangePass.setOnClickListener(this)
        callApi = RetrofitFactor.createRetrofit()
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_change_pass -> {
                changePassword()
            }
        }
    }
    private fun changePassword() {
        val idUser : Int = sharedPreferences.getString("id_user", "")!!.toInt()
        val passOld : String = edtPassOld.text.toString().trim()
        val passNew : String = edtPassNew.text.toString().trim()
        callApi.updatePassword(
            idUser, passOld, passNew
        ).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if( edtPassOld.text.toString().trim() == null || edtPassAgain.text.toString().trim() == null
                    ||edtPassNew.text.toString().trim() == null) {
                    Toast.makeText(context, "Nhập đầy đủ các trường!", Toast.LENGTH_LONG).show()
                } else {
                    if (edtPassAgain.text.toString().trim() != edtPassNew.text.toString().trim()) {
                        Toast.makeText(context, "Mật khẩu mới không trùng khớp!", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        if (it.message == "not_pass") {
                            Toast.makeText(context, "Mật khẩu không đúng!", Toast.LENGTH_LONG)
                                .show()
                        } else if (it.message == "ok") {
                            Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            },{

            })
    }
}