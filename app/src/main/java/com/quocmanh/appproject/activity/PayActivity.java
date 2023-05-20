package com.quocmanh.appproject.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.quocmanh.appproject.R;
import com.quocmanh.appproject.model.Cart;
import com.quocmanh.appproject.myinterface.CallApi;
import com.quocmanh.appproject.myinterface.RetrofitFactor;
import com.quocmanh.appproject.zalopay.CreateOrder;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PayActivity extends AppCompatActivity {
    ImageView imvBackOrder;
    TextView txtName;
    TextView txtEmail;
    TextView txtPhone;
    TextView txtDes;
    TextView txtTotalPrice;
    EditText edtAddress;
    Button btnPay;
    Button btnPayZalo;
    CallApi callApi;
    SharedPreferences sharedPreferences;
    String idUser;
    String nameUser;
    String emailUser;
    String phoneUser;
    String addressUser;
    List<Cart> listCart = null;
    String detailOrder = "";
    String detailOrderFinal;
    String detail;
    String count;
    String price;
    int totalPrice = 0;
    String time;
    String token;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        init();
        idUser = sharedPreferences.getString("id_user", "");
        nameUser = sharedPreferences.getString("name_user", "");
        emailUser = sharedPreferences.getString("email_user", "");
        phoneUser = sharedPreferences.getString("phone_user", "");
        addressUser = sharedPreferences.getString("address_user", "");
        txtName.setText(nameUser);
        txtEmail.setText(emailUser);
        txtPhone.setText(phoneUser);
        edtAddress.setText(addressUser);
        getDataCart1();

        imvBackOrder.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, CartActivity.class);
            startActivity(intent);
        });
        btnPay.setOnClickListener(v -> {
            saveDetailOrder();
        });
        btnPayZalo.setOnClickListener(v -> {
            payZalo();
        });
    }

    public void payZalo() {
        String address = edtAddress.getText().toString().trim();
        String status = "pending";
        if (edtAddress == null || edtAddress.equals("")) {
            Toast.makeText(this, "Bạn chưa nhập địa chỉ!", Toast.LENGTH_LONG).show();
        } else {
            callApi.addDetailOrderZalo(
                            nameUser, emailUser, phoneUser, detailOrderFinal, String.valueOf(totalPrice), address, time, status
                    ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responsePayZalo -> {
                        Toast.makeText(PayActivity.this, "Thêm đơn hàng thành công!", Toast.LENGTH_LONG).show();
                        int idOrder = responsePayZalo.getData().getInsertId();
                        requestZalo(idOrder);
                    });
        }
    }

    public void requestZalo(int idOrder) {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder("100000");
            String code = data.getString("return_code");

            if (code.equals("1")) {
//                txtToken.setText(data.getString("zp_trans_token"));
                token = data.getString("zp_trans_token");
//                callApi.updateTokenZalo(String.valueOf(idOrder), token)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(responseInsertCart -> {
//                            Toast.makeText(PayActivity.this, "Thêm đơn hàng thành công!", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent();
//                            intent.setClass(this, MainActivity.class);
//                            startActivity(intent);
//                        });
                ZaloPaySDK.getInstance().payOrder(PayActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                callApi.updateTokenZalo(String.valueOf(idOrder), token)
//                                        .subscribeOn(Schedulers.newThread())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(responseInsertCart -> {
//                                            Toast.makeText(PayActivity.this, "Thêm token thành công!!!", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent();
//                                            intent.setClass(getApplicationContext(), MainActivity.class);
//                                            startActivity(intent);
//                                        });
                            }

                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDetailOrder() {
        String address = edtAddress.getText().toString().trim();
        String status = "pending";
        callApi.addDetailOrder(
                        nameUser, emailUser, phoneUser, detailOrderFinal, String.valueOf(totalPrice), address, time, status
                ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseInsertCart -> {
                    Toast.makeText(PayActivity.this, "Thêm đơn hàng thành công!", Toast.LENGTH_LONG).show();
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDataCart1() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        time = dtf.format(now);
        callApi.getDataCart(Integer.parseInt(idUser))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseCart -> {
                    listCart = responseCart.getDataCart();
                    if (listCart.size() > 0 || !listCart.isEmpty()) {
                        for (int i = 0; i < listCart.size(); i++) {
                            detail = listCart.get(i).getName_product();
                            count = String.valueOf(listCart.get(i).getCount_product());
                            price = listCart.get(i).getPrice();
                            totalPrice = totalPrice + Integer.parseInt(listCart.get(i).getPrice());
                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            price = decimalFormat.format(Long.parseLong(price.toString())).toString() + " Đ";
                            detailOrder = detailOrder + "\n" + "- Tên sản phẩm: " + detail + "\n" + "- Số lượng: " + count + "\n"
                                    + "- Giá: " + price + "\n";
                        }
                        DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                        detailOrderFinal = detailOrder;
                        txtDes.setText(detailOrderFinal);
                        txtTotalPrice.setText(decimalFormat1.format(totalPrice) + " Đ");
                    } else {
                        Toast.makeText(this, "Giỏ hàng trống!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void init() {
        imvBackOrder = findViewById(R.id.imb_back_order);
        txtName = findViewById(R.id.txt_name_order);
        txtEmail = findViewById(R.id.txt_email_order);
        txtPhone = findViewById(R.id.txt_phone_order);
        txtDes = findViewById(R.id.txt_detail_order);
        txtTotalPrice = findViewById(R.id.txt_total_price);
        edtAddress = findViewById(R.id.edt_address_order);
        btnPayZalo = findViewById(R.id.btn_checkout_order_zalopay);
        btnPay = findViewById(R.id.btn_checkout_order);
        callApi = RetrofitFactor.INSTANCE.createRetrofit();
        sharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
