package com.nudle.rentplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nudle.rentplace.model.Cart;
import com.nudle.rentplace.model.Users;
import com.nudle.rentplace.viewHolder.CartViewHolder;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView productList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID= getIntent().getStringExtra("uid");

        productList = findViewById(R.id.products_list);
        productList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List")
                .child("Admin View")
                .child(userID)
                .child("Products");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef, Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.txtSname.setText(model.getSname());
                holder.txtSphone.setText(model.getSsurname());
                holder.txtSphone.setText(model.getSphone());

                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price = " + model.getPrice());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductTime.setText(model.getPtime());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout, viewGroup, false);
                CartViewHolder holder = new CartViewHolder(view);
                return  holder;

            }
        };

        productList.setAdapter(adapter);
        adapter.startListening();

    }
}