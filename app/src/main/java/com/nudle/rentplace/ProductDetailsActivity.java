package com.nudle.rentplace;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nudle.rentplace.model.Products;
import com.nudle.rentplace.prevalent.Prevalent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName, productTime;
    private TextView sname, ssurname, sphone;
    private String productID = "", state = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        addToCartButton = (Button) findViewById(R.id.pd_add_to_cart_button);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productTime = (TextView) findViewById(R.id.product_time_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);

        sname = (TextView) findViewById(R.id.product_sname_details);
        ssurname = (TextView) findViewById(R.id.product_ssurname_details);
        sphone = (TextView) findViewById(R.id.product_sphone_details);

        getProductDetails(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();

                if (state.equals("Order Placed") || state.equals("Order Shipped")){

                    Toasty.info(ProductDetailsActivity.this, "You can purchase more products, once order is shipped or confirmed.", Toast.LENGTH_LONG).show();

                }else {

                    addingToCartList();

                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();

    }

    private void addingToCartList(){
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("ptime",productTime.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");

        cartMap.put("sname",sname.getText().toString());
        cartMap.put("ssurname",ssurname.getText().toString());
        cartMap.put("sphone",sphone.getText().toString());

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products")
                .child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products")
                            .child(productID).updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toasty.info(ProductDetailsActivity.this, "Added to cart list", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void getProductDetails(String productID){
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productTime.setText(products.getPtime() + " days");
                    productPrice.setText(products.getPrice() + "₽");
                    productDescription.setText(products.getDescription());

                    sname.setText(products.getSname());
                    ssurname.setText(products.getSsurname());
                    sphone.setText(products.getSphone());

                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CheckOrderState(){

        final DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String shippingState = dataSnapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped")){

                        state = "Order Shipped";

                    }else if (shippingState.equals("not shipped")){

                       state = "Order Placed";

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
