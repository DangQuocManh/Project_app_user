package com.quocmanh.appproject.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.SearchAdvancedActivity
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchAdvancedFragment : Fragment(), View.OnClickListener, AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var imbBack : ImageButton
    private lateinit var mView : View
    lateinit var selectedType : String
    lateinit var selectedBrand : String
    lateinit var selectedPrice : String

    lateinit var spinnerType : Spinner
    lateinit var spinnerBrand : Spinner
    lateinit var spinnerPrice : Spinner

    lateinit var typeAdapter : ArrayAdapter<CharSequence>
    lateinit var brandAdapter : ArrayAdapter<CharSequence>
    lateinit var priceAdapter : ArrayAdapter<CharSequence>

    lateinit var txtType : TextView
    lateinit var txtBrand : TextView
    lateinit var txtPrice : TextView
    lateinit var btnSearch : Button

    lateinit var strType : String
    lateinit var strBrand : String
    lateinit var strPrice : String

    lateinit var callApi: CallApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search_advanced, container, false)
        init(mView)
        return mView
    }

    private fun init(view : View) {
        callApi = RetrofitFactor.createRetrofit()
        imbBack = view.findViewById(R.id.imb_back_search)
        imbBack.setOnClickListener(this)
        selectedBrand = null.toString()
        selectedType = null.toString()
        selectedPrice = null.toString()

        strBrand = null.toString()
        strPrice = null.toString()
        strType = null.toString()

        spinnerType = view.findViewById(R.id.spin_type_product)
        typeAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_type_product, R.layout.spinner_layout)
        typeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinnerType.adapter = typeAdapter
        spinnerType.onItemSelectedListener = this

        txtType = view.findViewById(R.id.txt_type_product)
        txtBrand = view.findViewById(R.id.txt_brand_product)
        txtPrice = view.findViewById(R.id.txt_price_product_search)
        btnSearch = view.findViewById(R.id.btn_search_advanced)
        btnSearch.setOnClickListener {
            if(selectedType.equals("Chọn loại sản phẩm")) {
                txtType.setError("Loại sản phẩm được yêu cầu")
                txtType.requestFocus()
            } else if (selectedBrand.equals("Chọn hãng sản phẩm")) {
                txtBrand.setError("Hãng sản phẩm được yêu cầu")
                txtBrand.requestFocus()
                txtType.setError(null)
            } else if (selectedPrice.equals("Chọn giá")) {
                txtPrice.setError("Giá sản phẩm được yêu cầu")
                txtPrice.requestFocus()
                txtBrand.setError(null)
            } else {
                Log.d("strType: ", strType)
                Log.d("strBrand: ", strBrand)
                Log.d("strPrice: ", strPrice)
//                callApi.getDataSearchAdvanced(
//                    strType, strBrand, strPrice
//                ).subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        val test = it.message
//                        Log.d("search advanced: ", test.toString())
//                        val dataList = it.data
//
//                    },{
//
//                    })
                val intent = Intent()
                intent.setClass(requireContext(), SearchAdvancedActivity::class.java)
                val bundle = Bundle()
                bundle.putString("type", strType)
                bundle.putString("brand", strBrand)
                bundle.putString("price", strPrice)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imb_back_search -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerBrand = requireView().findViewById(R.id.spin_brand_product)
        spinnerPrice = requireView().findViewById(R.id.spin_price_product)
        //Chon item spinner type
        selectedType = spinnerType.selectedItem.toString()
        val parentId = parent!!.id
        if(parentId == R.id.spin_type_product) {
            when(selectedType) {
                "Chọn loại sản phẩm" -> {
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_phone, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
                "Điện thoại" -> {
                    strType = "PH1"
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_phone, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
                "Laptop" -> {
                    strType = "LT1"
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_laptop, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
                "Tablet" -> {
                    strType = ""
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_tablet, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
                "Tai nghe" -> {
                    strType = ""
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_headphone, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
                "Màn hình" -> {
                    strType = ""
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_screen, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
                "Microphone" -> {
                    strType = ""
                    Log.d("String type: ", selectedType)
                    brandAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_brand_microphone, R.layout.spinner_layout)
                    brandAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerBrand.adapter = brandAdapter
                    spinnerBrand.onItemSelectedListener = this

                    priceAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_price_product, R.layout.spinner_layout)
                    priceAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                    spinnerPrice.adapter = priceAdapter
                    spinnerPrice.onItemSelectedListener = this

                    Log.d("String brand: ", selectedBrand)
                }
            }
        }
        if(parentId == R.id.spin_brand_product) {
            Log.d("Parent Id: ", "brand_spinner")
            selectedBrand = spinnerBrand.selectedItem.toString()
            if(selectedType == "Điện thoại") {
                when(selectedBrand) {
                    "Chọn hãng sản phẩm" -> {
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "IPhone" -> {
                        strType = "PH1"
                        strBrand = "Iphone"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Samsung" -> {
                        strType = "PH1"
                        strBrand = "Samsung"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Oppo" -> {
                        strType = "PH1"
                        strBrand = "Oppo"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Xiaomi" -> {
                        strType = "PH1"
                        strBrand = "Xiaomi"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Asus" -> {
                        strType = "PH1"
                        strBrand = "Asus"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Vivo" -> {
                        strType = "PH1"
                        strBrand = "Vivo"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Realme" -> {
                        strType = "PH1"
                        strBrand = "Realme"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                }
            } else if (selectedType == "Laptop") {
                when(selectedBrand) {
                    "Chọn hãng sản phẩm" -> {
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Mac" -> {
                        strType = "LT1"
                        strBrand = "Mac"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Dell" -> {
                        strType = "LT1"
                        strBrand = "Dell"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Asus" -> {
                        strType = "LT1"
                        strBrand = "Asus"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Acer" -> {
                        strType = "LT1"
                        strBrand = "Acer"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Lenovo" -> {
                        strType = "LT1"
                        strBrand = "Lenovo"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "HP" -> {
                        strType = "LT1"
                        strBrand = "HP"
//                        if(selectedPrice == "Dưới 1 triệu") {
//                            strPrice = "option1"
//                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                            strPrice = "option2"
//                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                            strPrice = "option3"
//                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                            strPrice = "option4"
//                        } else if (selectedPrice == "Trên 20 triệu") {
//                            strPrice = "option5"
//                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                }
            } else if (selectedType == "Tablet") {
               when(selectedBrand) {
                   "IPad" -> {
                       strType = ""
                       strBrand = ""
//                       if(selectedPrice == "Dưới 1 triệu") {
//                           strPrice = "option1"
//                       } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                           strPrice = "option2"
//                       } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                           strPrice = "option3"
//                       } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                           strPrice = "option4"
//                       } else if (selectedPrice == "Trên 20 triệu") {
//                           strPrice = "option5"
//                       }
                       Log.d("type: ", selectedType)
                       Log.d("brand: ", selectedBrand)
                       Log.d("price: ", selectedPrice)
                   }
                   "Samsung" -> {
                       strType = ""
                       strBrand = ""
//                       if(selectedPrice == "Dưới 1 triệu") {
//                           strPrice = "option1"
//                       } else if (selectedPrice == "Từ 1 - 5 triệu") {
//                           strPrice = "option2"
//                       } else if (selectedPrice == "Từ 5 - 10 triệu") {
//                           strPrice = "option3"
//                       } else if (selectedPrice == "Từ 10 - 20 triệu") {
//                           strPrice = "option4"
//                       } else if (selectedPrice == "Trên 20 triệu") {
//                           strPrice = "option5"
//                       }
                       Log.d("type: ", selectedType)
                       Log.d("brand: ", selectedBrand)
                       Log.d("price: ", selectedPrice)
                   }
                   "Xiaomi" -> {
                       strType = ""
                       strBrand = ""
                       if(selectedPrice == "Dưới 1 triệu") {
                           strPrice = "option1"
                       } else if (selectedPrice == "Từ 1 - 5 triệu") {
                           strPrice = "option2"
                       } else if (selectedPrice == "Từ 5 - 10 triệu") {
                           strPrice = "option3"
                       } else if (selectedPrice == "Từ 10 - 20 triệu") {
                           strPrice = "option4"
                       } else if (selectedPrice == "Trên 20 triệu") {
                           strPrice = "option5"
                       }
                       Log.d("type: ", selectedType)
                       Log.d("brand: ", selectedBrand)
                       Log.d("price: ", selectedPrice)
                   }
                   "Huawei" -> {
                       strType = ""
                       strBrand = ""
                       if(selectedPrice == "Dưới 1 triệu") {
                           strPrice = "option1"
                       } else if (selectedPrice == "Từ 1 - 5 triệu") {
                           strPrice = "option2"
                       } else if (selectedPrice == "Từ 5 - 10 triệu") {
                           strPrice = "option3"
                       } else if (selectedPrice == "Từ 10 - 20 triệu") {
                           strPrice = "option4"
                       } else if (selectedPrice == "Trên 20 triệu") {
                           strPrice = "option5"
                       }
                       Log.d("type: ", selectedType)
                       Log.d("brand: ", selectedBrand)
                       Log.d("price: ", selectedPrice)
                   }
                   "Lenovo" -> {
                       strType = ""
                       strBrand = ""
                       if(selectedPrice == "Dưới 1 triệu") {
                           strPrice = "option1"
                       } else if (selectedPrice == "Từ 1 - 5 triệu") {
                           strPrice = "option2"
                       } else if (selectedPrice == "Từ 5 - 10 triệu") {
                           strPrice = "option3"
                       } else if (selectedPrice == "Từ 10 - 20 triệu") {
                           strPrice = "option4"
                       } else if (selectedPrice == "Trên 20 triệu") {
                           strPrice = "option5"
                       }
                       Log.d("type: ", selectedType)
                       Log.d("brand: ", selectedBrand)
                       Log.d("price: ", selectedPrice)
                   }
               }
            } else if (selectedType == "Tai nghe") {
                when(selectedBrand) {
                    "Apple" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Xiaomi" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Samsung" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Sony" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Huawei" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                }
            } else if (selectedType == "Màn hình") {
                when(selectedBrand) {
                    "Acer" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Asus" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Dell" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Samsung" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                }
            } else if (selectedType == "Microphone") {
                when(selectedBrand) {
                    "Kingstom" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Krom" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Razer" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                    "Salar" -> {
                        strType = ""
                        strBrand = ""
                        if(selectedPrice == "Dưới 1 triệu") {
                            strPrice = "option1"
                        } else if (selectedPrice == "Từ 1 - 5 triệu") {
                            strPrice = "option2"
                        } else if (selectedPrice == "Từ 5 - 10 triệu") {
                            strPrice = "option3"
                        } else if (selectedPrice == "Từ 10 - 20 triệu") {
                            strPrice = "option4"
                        } else if (selectedPrice == "Trên 20 triệu") {
                            strPrice = "option5"
                        }
                        Log.d("type: ", selectedType)
                        Log.d("brand: ", selectedBrand)
                        Log.d("price: ", selectedPrice)
                    }
                }
            }
        }
        if(parentId == R.id.spin_price_product) {
            selectedPrice = spinnerPrice.selectedItem.toString()
            Log.d("type: ", selectedType)
            Log.d("brand: ", selectedBrand)
            Log.d("price: ", selectedPrice)
            if(selectedPrice == "Dưới 1 triệu") {
                strPrice = "option1"
            } else if (selectedPrice == "Từ 1 - 5 triệu") {
                strPrice = "option2"
            } else if (selectedPrice == "Từ 5 - 10 triệu") {
                strPrice = "option3"
            } else if (selectedPrice == "Từ 10 - 20 triệu") {
                strPrice = "option4"
            } else if (selectedPrice == "Trên 20 triệu") {
                strPrice = "option5"
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

}