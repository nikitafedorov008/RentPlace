package com.polka.rentplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.polka.rentplace.model.Cart;
import com.polka.rentplace.prevalent.Prevalent;
import com.polka.rentplace.viewHolder.CartViewHolder;

public class AdminUserProductActivity extends AppCompatActivity {

   private RecyclerView productsList;
   RecyclerView.LayoutManager layoutManager;
   private DatabaseReference cartListRef;

   private String userID = "";
   private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_product);


        userID = getIntent().getStringExtra("uid");


        phone = Prevalent.currentOnlineUser.getPhone();

        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(userID).child("Products");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(reference.orderByChild("phone").equalTo(phone), Cart.class)
                .build();



        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                    holder.txtProductQuantiny.setText(model.getQuantity());
                    holder.txtProductPrice.setText("Price = " + model.getPrice());
                    holder.txtProductName.setText("Product = " + model.getPname());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;


            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();

    }
}
