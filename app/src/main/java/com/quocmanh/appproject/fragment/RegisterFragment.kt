package com.quocmanh.appproject.fragment

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.RequestRegister
import com.quocmanh.appproject.model.ResponseRegister
import com.quocmanh.appproject.myinterface.ApiInterface
import com.quocmanh.appproject.myinterface.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterFragment : Fragment() {
    private lateinit var edtUsername : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPhone : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtPassAgain : EditText
    private lateinit var mView: View
    private lateinit var btnRegister : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_register, container, false)
        init(mView)
        btnRegister.setOnClickListener {
            register()
        }
        return mView
    }

    @SuppressLint("NewApi")
    private fun register(){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val current = LocalDateTime.now().format(formatter)
        val timeCurrent = current.toString().trim()
        val userName : String = edtUsername.text.toString().trim()
        val password : String = edtPassword.text.toString().trim()
        val email : String = edtEmail.text.toString().trim()
        val phone : String = edtPhone.text.toString().trim()
        val t1 = userName
        val t2 = password
        val t3 = email
        val t4 = phone
        val obj = RequestRegister("", t1, t2, t3, t4, "", timeCurrent, "client")
        val retrofit = ServiceBuilder.buildService1(ApiInterface::class.java)
        retrofit.register(obj).enqueue(
            object : Callback<ResponseRegister> {
                override fun onResponse(
                    call: Call<ResponseRegister>,
                    response: Response<ResponseRegister>
                ) {
                    val message : String = response.body()?.message.toString()
                    if(message == "ok"){
                        Toast.makeText(context, "Đăng kí thành công!", Toast.LENGTH_LONG).show()
                        val handler = Handler()
                        handler.postDelayed({
                            loginFragment()
                        }, 700)
                    } else {
                        Toast.makeText(context, "Đăng kí thất bại!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
    private fun loginFragment(){
        val loginFragment = LoginFragment()
        val fm = parentFragmentManager
        val fg = fm.beginTransaction()
        fg.replace(R.id.fr_login_register, loginFragment)
//        fg.addToBackStack(null)
        fg.commit()
    }
    private fun init(view : View){
        edtEmail = view.findViewById(R.id.editTextEmail)
        edtUsername = view.findViewById(R.id.editTextUsername)
        edtPhone = view.findViewById(R.id.editTextPhone)
        edtPassword = view.findViewById(R.id.editTextPassword)
        edtPassAgain = view.findViewById(R.id.editTextPasswordAgain)
        btnRegister = view.findViewById(R.id.btn_register)
    }

}