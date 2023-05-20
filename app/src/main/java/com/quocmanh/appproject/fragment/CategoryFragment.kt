package com.quocmanh.appproject.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.MainActivity
import de.hdodenhof.circleimageview.CircleImageView

class CategoryFragment : Fragment(), View.OnClickListener {
    private lateinit var civLaptop : CircleImageView
    private lateinit var civPhone : CircleImageView
    private lateinit var civTablet : CircleImageView
    private lateinit var civHeadphone : CircleImageView
    private lateinit var civScreen : CircleImageView
    private lateinit var civMicro : CircleImageView
    private lateinit var mView : View
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var imvBack : ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_category, container, false)
        init(mView)
        return mView
    }
    private fun init(view: View) {
        civLaptop = view.findViewById(R.id.civ_laptop)
        civPhone = view.findViewById(R.id.civ_phone)
        civTablet = view.findViewById(R.id.civ_tablet)
        civHeadphone = view.findViewById(R.id.civ_headphone)
        civScreen = view.findViewById(R.id.civ_screen)
        civMicro = view.findViewById(R.id.civ_micro)
        civLaptop.setOnClickListener(this)
        civPhone.setOnClickListener(this)
        civTablet.setOnClickListener(this)
        civHeadphone.setOnClickListener(this)
        civScreen.setOnClickListener(this)
        civMicro.setOnClickListener(this)
        sharedPreferences = requireContext().getSharedPreferences("type_categories", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        imvBack = view.findViewById(R.id.imv_back_categories)
        imvBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.civ_laptop -> {
                editor.putString("type", "LT1")
                editor.commit()
                val categoriesProductFragment = CategoriesProductFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, categoriesProductFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.civ_phone -> {
                editor.putString("type", "PH1")
                editor.commit()
                val categoriesProductFragment = CategoriesProductFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, categoriesProductFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.civ_tablet -> {
                editor.putString("type", "PH1")
                editor.commit()
                val categoriesProductFragment = CategoriesProductFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, categoriesProductFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.civ_headphone -> {
                editor.putString("type", "PH1")
                editor.commit()
                val categoriesProductFragment = CategoriesProductFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, categoriesProductFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.civ_screen -> {
                editor.putString("type", "PH1")
                editor.commit()
                val categoriesProductFragment = CategoriesProductFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, categoriesProductFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.civ_micro -> {
                editor.putString("type", "PH1")
                editor.commit()
                val categoriesProductFragment = CategoriesProductFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, categoriesProductFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
            R.id.imv_back_categories -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}