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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.quocmanh.appproject.R
import com.quocmanh.appproject.Util.RealPathUtil
import com.quocmanh.appproject.model.ResponseChangeAvatar
import com.quocmanh.appproject.myinterface.ApiInterface
import com.quocmanh.appproject.myinterface.ServiceBuilder
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddPostFragment : Fragment(), View.OnClickListener {
    private lateinit var imvBack : ImageView
    private lateinit var imvAdd : ImageView
    private lateinit var imvImage : ImageView
    private lateinit var edtTitle : EditText
    private lateinit var edtContent : EditText
    private lateinit var mView : View

    private lateinit var file : File
    val MY_REQUEST_CODE = 10
    private lateinit var mUri: Uri
    private lateinit var mProgressionDialog : ProgressDialog
    private lateinit var sharedPreferences: SharedPreferences
    var idUser : Int? = null
    var nameUser : String = ""
    var title : String = ""
    var content : String = ""
    var time : String = ""

    private var mActivityResultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(
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
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, uri)
                        imvImage.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_post, container, false)
        init(mView)
        return mView
    }
    fun init(view : View) {
        imvBack = view.findViewById(R.id.imv_back_post_news)
        imvAdd = view.findViewById(R.id.imv_add_post_new1)
        imvImage = view.findViewById(R.id.imv_add_image_new_post)
        edtTitle = view.findViewById(R.id.edt_add_new_title)
        edtContent = view.findViewById(R.id.edt_add_new_content)
        imvBack.setOnClickListener(this)
        imvAdd.setOnClickListener(this)
        imvImage.setOnClickListener({
            onClickRequestPermission()
        })
        mProgressionDialog = ProgressDialog(requireContext())
        mProgressionDialog.setMessage("Please wait...")

        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("id_user", "")!!.toInt()
        nameUser = sharedPreferences.getString("name_user", "").toString()

        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MMMM-yy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val current = LocalDateTime.now().format(formatter)
        time = current.toString().trim()
    }

    private fun onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery()
            return
        }
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery()
        }else {
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
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery()
            }
        }
    }
    private fun openGallery() {
        val intent : Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imv_back_post_news -> {
                val newsFragment = NewsFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, newsFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.imv_add_image_new_post -> {
                onClickRequestPermission()
            }
            R.id.imv_add_post_new1 -> {
                try {
                    title = edtTitle.text.toString()
                    content = edtContent.text.toString()
                    val requestId : RequestBody = RequestBody.create(
                        "multipart/form-data".toMediaTypeOrNull(),
                        idUser.toString()
                    )
                    val requestTitle : RequestBody = RequestBody.create(
                        "multipart/form-data".toMediaTypeOrNull(),
                        title
                    )
                    val requestContent : RequestBody = RequestBody.create(
                        "multipart/form-data".toMediaTypeOrNull(),
                        content.toString()
                    )
                    val requestTime : RequestBody = RequestBody.create(
                        "multipart/form-data".toMediaTypeOrNull(),
                        time.toString()
                    )

                    val strRealPath : String = RealPathUtil.getRealPath(requireContext(), mUri)
                    Log.e("EditProfile", strRealPath)
                    val file : File = File(strRealPath)
                    val requestBodyImage : RequestBody = RequestBody.create(
                        "multipart/form-data".toMediaTypeOrNull(),
                        file
                    )
                    val multipartBodyImage : MultipartBody.Part = MultipartBody.Part.createFormData("images", file.name, requestBodyImage)

                    val retrofit = ServiceBuilder.buildService1(ApiInterface::class.java)
                    retrofit.addPost(multipartBodyImage, requestTitle, requestContent, requestTime, requestId).enqueue(object :
                        Callback<ResponseChangeAvatar> {
                        override fun onResponse(
                            call: Call<ResponseChangeAvatar>,
                            response: Response<ResponseChangeAvatar>
                        ) {
                            Toast.makeText(context, "Nhận ảnh!", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<ResponseChangeAvatar>, t: Throwable) {
                            Toast.makeText(context, "!!!", Toast.LENGTH_LONG).show()
                            return
                        }

                    })
                    Toast.makeText(context, "Thêm bài đăng!", Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}