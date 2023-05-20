package com.quocmanh.appproject.fragment

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.quocmanh.appproject.R
import com.quocmanh.appproject.Util.RealPathUtil
import com.quocmanh.appproject.activity.LoginActivity
import com.quocmanh.appproject.model.*
import com.quocmanh.appproject.myinterface.ApiInterface
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import com.quocmanh.appproject.myinterface.ServiceBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class AccountFragment : Fragment(), View.OnClickListener {
    private lateinit var txtInforAccount: TextView
    private lateinit var txtManagerAccount: TextView
    private lateinit var txtChangePass: TextView
    private lateinit var mView: View
    private lateinit var txtLogout: TextView
    private lateinit var imvChangeAvatar: ImageView
    private lateinit var file: File
    val MY_REQUEST_CODE = 10
    private lateinit var mUri: Uri
    private lateinit var mProgressionDialog: ProgressDialog
    private lateinit var sharedPreferences: SharedPreferences
    var idUser: Int? = null
    var nameUser: String = ""
    private lateinit var callApi: CallApi
    private lateinit var listAvatar: MutableList<Avatar>
    private lateinit var txtName: TextView
    private lateinit var txtListOrder: TextView
    private lateinit var txtListPostFavorite: TextView

    private var mActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult> {
            override fun onActivityResult(result: ActivityResult?) {
                Log.e("EditProfileFragment", "onActivityResult")
                if (result?.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data == null) {
                        return
                    }
                    val uri: Uri = data.data!!
                    mUri = uri
                    try {
                        val bitmap: Bitmap =
                            MediaStore.Images.Media.getBitmap(context!!.contentResolver, uri)
                        imvChangeAvatar.setImageBitmap(bitmap)

                        val requestId: RequestBody = RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            idUser.toString()
                        )

                        val strRealPath: String = RealPathUtil.getRealPath(requireContext(), mUri)
                        Log.e("EditProfile", strRealPath)
                        val file: File = File(strRealPath)
                        val requestBodyImage: RequestBody = RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            file
                        )
                        val multipartBodyImage: MultipartBody.Part =
                            MultipartBody.Part.createFormData("images", file.name, requestBodyImage)

                        val retrofit = ServiceBuilder.buildService1(ApiInterface::class.java)
                        retrofit.updateAvatar(multipartBodyImage, requestId)
                            .enqueue(object : Callback<ResponseChangeAvatar> {
                                override fun onResponse(
                                    call: Call<ResponseChangeAvatar>,
                                    response: Response<ResponseChangeAvatar>
                                ) {
                                    Toast.makeText(context, "Thay đổi avatar!", Toast.LENGTH_LONG)
                                        .show()
                                }

                                override fun onFailure(
                                    call: Call<ResponseChangeAvatar>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(context, "Thay đổi avatar!", Toast.LENGTH_LONG)
                                        .show()
                                    return
                                }

                            })
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_account, container, false)
        init(mView)
        getAvatar()
        return mView
    }

    private fun init(view: View) {
        txtInforAccount = view.findViewById(R.id.txt_infor_accou)
        txtManagerAccount = view.findViewById(R.id.txt_manager_accou)
        txtChangePass = view.findViewById(R.id.txt_change_pass1)
        txtInforAccount.setOnClickListener(this)
        txtManagerAccount.setOnClickListener(this)
        txtChangePass.setOnClickListener(this)
        txtLogout = view.findViewById(R.id.txt_logout)
        txtLogout.setOnClickListener(this)
        imvChangeAvatar = view.findViewById(R.id.imv_change_avatar)
        imvChangeAvatar.setOnClickListener({
            onClickRequestPermission()
        })
        mProgressionDialog = ProgressDialog(requireContext())
        mProgressionDialog.setMessage("Please wait...")

        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("id_user", "")!!.toInt()
        nameUser = sharedPreferences.getString("name_user", "").toString()
        callApi = RetrofitFactor.createRetrofit()
        listAvatar = mutableListOf()
        txtName = view.findViewById(R.id.txt_name_account)

        txtListOrder = view.findViewById(R.id.txt_list_order)
        txtListPostFavorite = view.findViewById(R.id.txt_list_post_favorite)
        txtListOrder.setOnClickListener(this)
        txtListPostFavorite.setOnClickListener(this)
    }

    private fun getAvatar() {
        txtName.setText(nameUser)
        callApi.getAvatarFromId(idUser!!.toInt())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listAvatar = it.data
                Glide.with(mView)
                    .load(listAvatar[0].avatar)
                    .into(imvChangeAvatar)
            }, {

            })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_infor_accou -> {
                val inforAccountFragment = InforAccountFragment()
                getFragment(inforAccountFragment)
            }
            R.id.txt_manager_accou -> {
                val managerAccountFragment = ManagerAccountFragment()
                getFragment(managerAccountFragment)
            }
            R.id.txt_change_pass1 -> {
                val changePassFragment = ChangePassFragment()
                getFragment(changePassFragment)
            }
            R.id.txt_logout -> {
                val intent = Intent()
                intent.setClass(requireContext(), LoginActivity::class.java)
                val preferences: SharedPreferences =
                    requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.clear()
                editor.apply()
                startActivity(intent)
                requireActivity().finish()
            }
            R.id.imv_change_avatar -> {
                onClickRequestPermission()
            }
            R.id.txt_list_order -> {
                val listOrderFragment = ListOrderFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, listOrderFragment)
                fg.commit()
            }
            R.id.txt_list_post_favorite -> {
                val favoriteNewsFragment = FavoriteNewsFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, favoriteNewsFragment)
                fg.commit()
            }
        }
    }

    private fun onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery()
            return
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            val permission = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, MY_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent: Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private fun getFragment(fragment: Fragment) {
        val fm = fragmentManager
        val fg = fm!!.beginTransaction()
        fg.replace(R.id.fr_layout_main, fragment)
//        fg.addToBackStack(null)
        fg.commit()
    }

}