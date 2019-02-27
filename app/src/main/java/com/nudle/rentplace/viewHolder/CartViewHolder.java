package com.nudle.rentplace.viewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nudle.rentplace.interfaceListener.ItemClickListner;
import com.nudle.rentplace.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity, txtProductTime;
    public TextView txtSname, txtSsurname, txtSphone;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        txtProductTime = itemView.findViewById(R.id.cart_product_time);

        txtSname = itemView.findViewById(R.id.cart_product_sname);
        txtSsurname = itemView.findViewById(R.id.cart_product_ssurname);
        txtSphone = itemView.findViewById(R.id.cart_product_sphone);

    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
