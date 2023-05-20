package com.quocmanh.appproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.Cart
import com.quocmanh.appproject.myinterface.ApiInterface
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.ImageClick
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private var context : Context
    private var callApi: CallApi = RetrofitFactor.createRetrofit()
    private var itemCarts : MutableList<Cart> = mutableListOf()

    constructor(context: Context, itemCart: MutableList<Cart>) : super() {
        this.context = context
        this.itemCarts = itemCart
    }

    override fun getItemCount(): Int {
        return itemCarts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val itemCart = itemCarts[position]
        holder.nameCart.setText(itemCart.name_product)
        holder.countCart.setText(itemCart.count_product.toString())
        val decimal: DecimalFormat = DecimalFormat("###,###,###")
        holder.txtPrice.setText(decimal.format(itemCart.price.toDouble()).toString() + " Đ")
        Glide.with(holder.itemView.context)
            .load(itemCart.image)
            .into(holder.imgCart)
        holder.setImageClick( object : ImageClick{
            override fun onImageClick(view: View, position: Int, value: Int) {
                if(value == 1) {
                    try {
                        //Tang so luong
                        if(itemCarts[position].count_product < 10) {
                            var count: Int = itemCarts[position].count_product + 1
                            itemCarts[position].count_product = count
                            var countNew = count
                            //Gia mot san pham
                            val price: Int = itemCarts[position].price.toInt() / (countNew - 1)
                            //Gia moi
                            val newPrice: Int = price * countNew
                            itemCarts[position].price = newPrice.toString()

                            holder.countCart.setText(countNew.toString())
                            holder.txtPrice.setText(
                                decimal.format(itemCarts[position].price.toDouble()).toString() + " Đ"
                            )
                            //Update cart
                            callApi.updateCart(
                                itemCarts[position].price,
                                itemCarts[position].count_product,
                                itemCarts[position].id_user!!.toInt(),
                                itemCarts[position].id_product!!.toInt()
                            )
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if (it.message == "ok") {
                                        Log.d("Update add count: ", "ok")
                                    }
                                }, {

                                })
                        } else {
                            return
                        }
                    } catch (e : IndexOutOfBoundsException){
                        e.printStackTrace()
                    }

                } else if(value == 2) {
                    try {
                        //Giam so luong
                        if(itemCarts[position].count_product > 1) {
                            var count : Int = itemCarts[position].count_product - 1
                            itemCarts[position].count_product = count
                            var countNew = count
                            var price : Int = itemCarts[position].price.toInt() / (countNew + 1)
                            var newPrice : Int = price * countNew
                            itemCarts[position].price = newPrice.toString()

                            holder.countCart.setText(countNew.toString())
                            holder.txtPrice.setText(decimal.format(itemCarts[position].price.toDouble()).toString() + " Đ")

                            callApi.updateCart( itemCarts[position].price,
                                itemCarts[position].count_product, itemCarts[position].id_user!!.toInt(),
                                itemCarts[position].id_product!!.toInt())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if(it.message == "ok") {
                                        Log.d("Update remove count: ", "ok")
                                    }
                                },{

                                })
                        } else {
                            return
                        }
                    }catch (e : IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                } else if(value == 3) {
                    try {
                        val t = itemCarts[position].id_user!!.toInt()
                        val t1 = itemCarts[position].id_product!!.toInt()
                        callApi.deleteItemCart(
                            itemCarts[position].id_user!!.toInt(),
                            itemCarts[position].id_product!!.toInt()
                        ).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                if(it.message == "ok") {
                                    Toast.makeText(context, "Đã xóa khỏi giỏ hàng!", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Chưa xóa khỏi giỏ hàng!", Toast.LENGTH_LONG).show()
                                }
                            },{

                            })
                        itemCarts.removeAt(position)
                        notifyDataSetChanged()
                    } catch (e : IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }

                }
            }
        })
    }
    class CartViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        val imgCart : ImageView
        val nameCart : TextView
        val removeCart : ImageView
        val countCart : TextView
        val addCart : ImageView
        val txtPrice : TextView
        val imbDelete : ImageButton
        lateinit var imageClick : ImageClick

        constructor(
            itemView: View
        ) : super(itemView) {
            imgCart = itemView.findViewById(R.id.imv_imagecart)
            nameCart = itemView.findViewById(R.id.txt_namecart)
            removeCart = itemView.findViewById(R.id.img_removecart)
            countCart = itemView.findViewById(R.id.txt_countcart)
            addCart = itemView.findViewById(R.id.img_addcart)
            txtPrice = itemView.findViewById(R.id.txt_price)
            imbDelete = itemView.findViewById(R.id.imb_delete)
            addCart.setOnClickListener(this)
            removeCart.setOnClickListener(this)
            imbDelete.setOnClickListener(this)
        }

        @JvmName("setImageClick1")
        fun setImageClick(imageClick : ImageClick){
            this.imageClick = imageClick
        }

        override fun onClick(view: View?) {
            if(view == addCart){
                //Cong gia tri 1
                imageClick.onImageClick(view, adapterPosition, 1)
            }else if(view == removeCart){
                //Tru gia tri 2
                imageClick.onImageClick(view, adapterPosition, 2)
            }else if(view == imbDelete){
                //Xoa item cart
                imageClick.onImageClick(view, adapterPosition, 3)
            }
        }

    }

}