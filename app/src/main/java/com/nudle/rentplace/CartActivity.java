package com.nudle.rentplace;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nudle.rentplace.model.Cart;
import com.nudle.rentplace.prevalent.Prevalent;
import com.nudle.rentplace.viewHolder.CartViewHolder;

import es.dmoral.toasty.Toasty;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;

    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
        txtMsg1 = (TextView) findViewById(R.id.msg1);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Prevalent.currentOnlineUser.getImage() == null) {
                    onClickDialog(view);
                } else if (Prevalent.currentOnlineUser.getName() == null) {
                    onClickDialog(view);
                } else if (Prevalent.currentOnlineUser.getName() == null) {
                    onClickDialog(view);
                } else if (Prevalent.currentOnlineUser.getSurname() == null) {
                    onClickDialog(view);
                } else if (Prevalent.currentOnlineUser.getAddress() == null) {
                    onClickDialog(view);
                } else {

                    txtTotalAmount.setText("Total Price = ₽" + String.valueOf(overTotalPrice));

                    Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone()).child("Products"), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

                holder.txtSname.setText(model.getSname());
                holder.txtSphone.setText(model.getSsurname());
                holder.txtSphone.setText(model.getSphone());

                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price = " + model.getPrice());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductTime.setText(model.getPtime());

                int oneTyprProductTPrice = ((Integer.valueOf(model.getPrice().replace("₽","")))) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTyprProductTPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "EDIT",
                                "REMOVE"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options: ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i == 0) {
                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }else if (i == 1){

                                        cartListRef.child("User View")
                                                .child(Prevalent.currentOnlineUser.getPhone())
                                                .child("Products")
                                                .child(model.getPid())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){

                                                            Toasty.success(CartActivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                            startActivity(intent);

                                                        }
                                                    }
                                                });
                                        }
                                }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout, viewGroup, false);
                CartViewHolder holder = new CartViewHolder(view);
                return  holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

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
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped")){

                        txtTotalAmount.setText("Dear " + userName + "\n order is shipped successfully.");
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Congratulations, your final order has been placed successfully. Soon you will received your order at your door step.");
                        NextProcessBtn.setVisibility(View.GONE);

                        Toasty.info(CartActivity.this, "you can purchase more products, once you received your first final order", Toast.LENGTH_SHORT).show();

                    }else if (shippingState.equals("not shipped")){

                        txtTotalAmount.setText("Shipping state = Not Shipped");
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        NextProcessBtn.setVisibility(View.GONE);

                        Toasty.info(CartActivity.this, "you can purchase more products, once you received your first final order", Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onClickDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Sorry")
                .setMessage("Please write more personal information")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(CartActivity.this, SettingsActivity.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("LATER",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}