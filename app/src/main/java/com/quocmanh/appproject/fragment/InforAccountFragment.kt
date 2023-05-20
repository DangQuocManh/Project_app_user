package com.quocmanh.appproject.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.DataLogin
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class InforAccountFragment : Fragment() {
    private lateinit var txtName : EditText
    private lateinit var txtEmail : EditText
    private lateinit var txtPhone : EditText
    private lateinit var txtAddress : EditText
    private lateinit var mView : View
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var callApi: CallApi
    private lateinit var dataUser : MutableList<DataLogin>
    private lateinit var txtTypeAccount : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_infor_account, container, false)
        init(mView)
        getDataUser()
        return mView
    }
    private fun init(view : View) {
        txtName = view.findViewById(R.id.et_name)
        txtEmail = view.findViewById(R.id.et_email)
        txtPhone = view.findViewById(R.id.et_phone)
        txtAddress = view.findViewById(R.id.et_address)
        sharedPreferences = requireContext().getSharedPreferences("user", MODE_PRIVATE)
        callApi = RetrofitFactor.createRetrofit()
        dataUser = mutableListOf()
        txtTypeAccount = view.findViewById(R.id.et_type_account)
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
                    txtName.setText(t[0].userName)
                    txtEmail.setText(t[0].email)
                    txtAddress.setText(t[0]?.address)
                    txtPhone.setText(t[0].phone)
                    if(t[0].type == "client") {
                        txtTypeAccount.setText("Tài khoản khách hàng")
                    }
                }
            },{

            })
    }

}