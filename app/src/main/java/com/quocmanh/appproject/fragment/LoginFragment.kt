package com.quocmanh.appproject.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.LoginResponse
import com.quocmanh.appproject.model.LoginResquest
import com.quocmanh.appproject.myinterface.ApiInterface
import com.quocmanh.appproject.myinterface.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var txtSignUp : TextView
    private lateinit var mView : View
    private lateinit var edtUsername : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         mView = inflater.inflate(R.layout.fragment_login, container, false)
         init(mView)
         return mView
    }

    private fun init(view : View){
        txtSignUp = view.findViewById(R.id.txt_sign_up)
        txtSignUp.setOnClickListener(this)
        edtUsername = view.findViewById(R.id.editTextUsernameLogin)
        edtPassword = view.findViewById(R.id.editTextPasswordLogin)
        btnLogin = view.findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)
        sharedPreferences = requireContext().getSharedPreferences("user", MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
    private fun login(){
        val userName : String = edtUsername.text.toString().trim()
        val password : String = edtPassword.text.toString().trim()
        val t1 = userName
        val t2 = password
        val obj = LoginResquest(t1, t2)
        val retrofit = ServiceBuilder.buildService1(ApiInterface::class.java)
        retrofit.loginUser(obj).enqueue(
            object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val message = response.body()?.message
                    val data = response.body()?.dataLogin
                    val idUser = data!![0].id
                    val username = data!![0].userName
                    val email = data!![0].email
                    val phone = data!![0].phone
                    val address = data[0]?.address
                    if(message == "ok"){
                        editor.putString("id_user", idUser.toString())
                        editor.putString("name_user", userName)
                        editor.putString("email_user", email)
                        editor.putString("phone_user", phone)
                        editor.putString("address_user", address)
                        editor.commit()
                        Log.d("idUser >>>> ", idUser.toString())
                        Log.d("nameUser >>>> ", userName.toString())
                        Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_LONG).show()
                        val intent = Intent()
                        intent.setClass(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else if (message == "not_account") {
                        Toast.makeText(context, "Tài khoản không tồn tại!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show()
                }

            })
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.txt_sign_up -> {
                val registerFragment = RegisterFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_login_register, registerFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.btn_login -> {
                login()
            }
        }
    }

}