package com.quocmanh.appproject.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.contentValuesOf
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.LoginActivity
import com.quocmanh.appproject.activity.UserActivity

class ViewDialog {
    fun showDialog(activity: Activity?, context: Context) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_layout)
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mDialogNo: FrameLayout = dialog.findViewById(R.id.frmNo)
        mDialogNo.setOnClickListener(View.OnClickListener {
                dialog!!.dismiss()
        })

        val mDialogOk: FrameLayout = dialog.findViewById(R.id.frmOk)
        mDialogOk.setOnClickListener(View.OnClickListener() {
            val intent : Intent = Intent()
            intent.setClass(activity as UserActivity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            dialog!!.cancel()
            startActivity(context, intent, null)
        })
        dialog.show()
    }

}