package com.example.dairyservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private CardView tShirts, sportsTShirts, femaleDresses;
    private CardView glasses, hatsCaps, walletsBagsPurses;
    private CardView headPhonesHandFree, Laptops, watches;

    private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn,addNewDaityBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        maintainProductsBtn = (Button) findViewById(R.id.maintain_btn);
        addNewDaityBtn = (Button) findViewById(R.id.add_new_dairy_btn);


        addNewDaityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AddNewDairyActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });


        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                intent.putExtra("dairy",getIntent().getStringExtra("dairy"));
                intent.putExtra("phone",getIntent().getStringExtra("phone"));
                startActivity(intent);
            }
        });


        tShirts = (CardView) findViewById(R.id.t_shirts);
        sportsTShirts = (CardView) findViewById(R.id.sports_t_shirts);
        femaleDresses = (CardView) findViewById(R.id.female_dresses);


        glasses = (CardView) findViewById(R.id.glasses);
        hatsCaps = (CardView) findViewById(R.id.hats_caps);
        walletsBagsPurses = (CardView) findViewById(R.id.purses_bags_wallets);


        headPhonesHandFree = (CardView) findViewById(R.id.headphones_handfree);
        Laptops = (CardView) findViewById(R.id.laptop_pc);
        watches = (CardView) findViewById(R.id.watches);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Milk");
                startActivity(intent);
            }
        });


        sportsTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Yogurt");
                startActivity(intent);
            }
        });


        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Fermented milk");
                startActivity(intent);
            }
        });


        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Milk Cream");
                startActivity(intent);
            }
        });


        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Milk Powder");
                startActivity(intent);
            }
        });



        walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });

        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Ice Cream");
                startActivity(intent);
            }
        });


        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Badam Milk");
                startActivity(intent);
            }
        });


        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductsActivity.class);
                intent.putExtra("category", "Cheese and Butter");
                startActivity(intent);
            }
        });

    }

}
