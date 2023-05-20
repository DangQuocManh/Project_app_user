package com.quocmanh.appproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R

class FavoriteConcernFragment : Fragment() {
    private lateinit var rcFavoriteConcern : RecyclerView
    private lateinit var mView : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_concern, container, false)
        init(mView)
        return mView
    }
    private fun init(view : View) {
        rcFavoriteConcern = view.findViewById(R.id.rc_favorite_concern)
    }

}