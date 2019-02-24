package com.nudle.rentplace;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UserCategoryActivity extends AppCompatActivity
{
    private ImageView books, children, clothes, shoes;
    private ImageView home_equipment, transport, garden, instruments;
    private ImageView gamesAndConsoles, electronics, computersAndLaptops, phonesAndTablets;

    private Button LogoutBtn, CheckOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });

        books = (ImageView) findViewById(R.id.books);
        children = (ImageView) findViewById(R.id.children);
        clothes = (ImageView) findViewById(R.id.clothes);
        shoes = (ImageView) findViewById(R.id.shoes);

        home_equipment = (ImageView) findViewById(R.id.home_equipment);
        transport = (ImageView) findViewById(R.id.transport);
        garden = (ImageView) findViewById(R.id.garden);
        instruments = (ImageView) findViewById(R.id.instruments);

        gamesAndConsoles = (ImageView) findViewById(R.id.games_and_consoles);
        electronics = (ImageView) findViewById(R.id.electronics);
        computersAndLaptops = (ImageView) findViewById(R.id.computers_and_laptops);
        phonesAndTablets = (ImageView) findViewById(R.id.phones_and_tablets);


        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Books and comics");
                startActivity(intent);
            }
        });


        children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Children things");
                startActivity(intent);
            }
        });


        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Clothes");
                startActivity(intent);
            }
        });


        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });


        home_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Home equipment");
                startActivity(intent);
            }
        });


        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Transport");
                startActivity(intent);
            }
        });



        garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Garden");
                startActivity(intent);
            }
        });


        instruments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Instruments");
                startActivity(intent);
            }
        });



        gamesAndConsoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Games and consoles");
                startActivity(intent);
            }
        });


        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Electronics");
                startActivity(intent);
            }
        });


        computersAndLaptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Computers and laptops");
                startActivity(intent);
            }
        });


        phonesAndTablets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Phones and tablets");
                startActivity(intent);
            }
        });
    }
}