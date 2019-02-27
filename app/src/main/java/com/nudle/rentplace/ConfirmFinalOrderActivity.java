package com.nudle.rentplace;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nudle.rentplace.model.Cart;
import com.nudle.rentplace.model.Users;
import com.nudle.rentplace.prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private EditText snameEditText, ssurnameEditText, sphoneEditText;
    private Button confirmOrderBtn;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toasty.info(this,"Total Price = " + totalAmount + Toast.LENGTH_SHORT).show();

        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shippment_name);
        phoneEditText = (EditText) findViewById(R.id.shippment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shippment_address);
        cityEditText = (EditText) findViewById(R.id.shippment_city);


        snameEditText = (EditText) findViewById(R.id.sname_c);
        ssurnameEditText = (EditText) findViewById(R.id.ssurname_c);
        sphoneEditText = (EditText) findViewById(R.id.sphone_c);


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });

    }

    private void Check(){

        if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toasty.error(this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toasty.error(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(addressEditText.getText().toString())){
            Toasty.error(this, "Please provide your address.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(cityEditText.getText().toString())){
            Toasty.error(this, "Please provide your city name.", Toast.LENGTH_SHORT).show();
        }else {
            ConfirmOrder();
        }

    }

    private void ConfirmOrder(){

        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount",totalAmount);
        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("city",cityEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("state", "not shipped");

        orderMap.put("sname", snameEditText.getText().toString());
        orderMap.put("ssurname", ssurnameEditText.getText().toString());
        orderMap.put("sphone", sphoneEditText.getText().toString());
        orderMap.put("sname", Cart.sname);
        orderMap.put("ssurname",Cart.ssurname);
        orderMap.put("sphone",Cart.sphone);
        /*holder.txtSname.setText(model.getSname());
        holder.txtSphone.setText(model.getSsurname());
        holder.txtSphone.setText(model.getSphone());*/

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        Toasty.success(ConfirmFinalOrderActivity.this, "Your final order has been placed successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    }

                                }
                            });

                }

            }
        });

    }

}
