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
import com.quocmanh.appproject.model.DataLogin
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ManagerAccountFragment : Fragment(), View.OnClickListener {
    private lateinit var edtName : EditText
    private lateinit var edtPhone : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtAddress : EditText
    private lateinit var btnChange : Button
    private lateinit var mView : View
    private lateinit var callApi: CallApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataUser : MutableList<DataLogin>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_manager_account, container, false)
        init(mView)
        getDataUser()
        return mView
    }
    private fun init(view : View) {
        edtAddress = view.findViewById(R.id.et_address_change)
        edtEmail = view.findViewById(R.id.et_email_change)
        edtPhone = view.findViewById(R.id.et_phone_change)
        edtName = view.findViewById(R.id.et_name_change)
        btnChange = view.findViewById(R.id.btn_change)
        btnChange.setOnClickListener(this)
        callApi = RetrofitFactor.createRetrofit()
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        dataUser = mutableListOf()
    }
    private fun getDataUser() {
        val idUser = sharedPreferences.getString("id_user", "")!!.toInt()
        callApi.getDataUser(idUser)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.message == "ok") {
                    dataUser = it.dataLogin!!
                    val t = dataUser
                    edtName.setText(t[0].userName)
                    edtEmail.setText(t[0].email)
                    edtAddress.setText(t[0]?.address)
                    edtPhone.setText(t[0].phone)
                }
            },{

            })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btn_change -> {
                updateInforUser()
            }
        }
    }
    fun updateInforUser() {
        val idUser = sharedPreferences.getString("id_user", "")!!.toInt()
        val name : String = edtName.text.toString().trim()
        val email : String = edtEmail.text.toString().trim()
        val phone : String = edtPhone.text.toString().trim()
        val address : String = edtAddress.text.toString().trim()
        callApi.updateInforUser(
            name, email, phone, address, idUser
        ).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.message == "ok") {
                    Toast.makeText(context, "Cập nhập thành công!", Toast.LENGTH_LONG).show()
                }
            },{

            })
    }
}