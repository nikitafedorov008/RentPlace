package com.polka.rentplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.polka.rentplace.utility.Font;

public class AboutUsActivity extends AppCompatActivity {

    private TextView logo;

    private TextView companyName, companyDescription;
    private TextView ittv1, ittv2, ittv3, ittv4, ittv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        companyName = (TextView) findViewById(R.id.company_name);
        companyDescription = (TextView) findViewById(R.id.company_description);
        ittv1 = (TextView) findViewById(R.id.ittv1);
        ittv2 = (TextView) findViewById(R.id.ittv2);
        ittv3 = (TextView) findViewById(R.id.ittv3);
        ittv4 = (TextView) findViewById(R.id.ittv4);
        ittv5 = (TextView) findViewById(R.id.ittv5);

        /*Font font = new Font();
        font.setFont(getApplicationContext(),companyName);
        font.setFont(getApplicationContext(),companyDescription);
        font.setFont(getApplicationContext(),ittv1);
        font.setFont(getApplicationContext(),ittv2);
        font.setFont(getApplicationContext(),ittv3);
        font.setFont(getApplicationContext(),ittv4);
        font.setFont(getApplicationContext(),ittv5);*/

        /*vkBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(openVKontakte(getApplicationContext()));
            }
        });

        facebookBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(openFacebook(getApplicationContext()));
            }
        });*/

        ittv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(openSite(getApplicationContext()));
            }
        });

        ittv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(openVKontakte(getApplicationContext()));
            }
        });

        ittv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(openFacebook(getApplicationContext()));
            }
        });

        ittv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(openInstagram(getApplicationContext()));
            }
        });
    }

    public static Intent openVKontakte(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.vk.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/polka"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://vk.com/polka"));
        }
    }

    public static Intent openFacebook(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/polka"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/polka"));
        }
    }

    public static Intent openSite(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.polka.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("http://polka.com/"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("http://polka.com/"));
        }
    }

    public static Intent openInstagram(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.instagram.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/polka"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://intagram.com/polka"));
        }
    }

    /*public static Intent buy500px(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.500px.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://marketplace.500px.com/dkomov"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://marketplace.500px.com/dkomov"));
        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AboutUsActivity.this,HomeActivity.class));
        finish();
    }

}
