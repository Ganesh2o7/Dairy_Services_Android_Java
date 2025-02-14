package com.example.dairyservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DairyDetails extends AppCompatActivity {

    private String img;
    private String name;
    private String address;
    private String description;
    private Intent intent;

    private ImageView image;
    private TextView nam;
    private TextView add;
    private TextView des;
    private Button select;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        image=findViewById(R.id.seller_image_details);
        nam=findViewById(R.id.seller_name_details);
        add=findViewById(R.id.seller_addres_details);
        des=findViewById(R.id.dairy_description_details);
        select=findViewById(R.id.seller_add_button);

        name=getIntent().getStringExtra("name");
        img=getIntent().getStringExtra("image");
        description=getIntent().getStringExtra("descrip");
        address=getIntent().getStringExtra("address");


        Picasso.get().load(img).into(image);
        nam.setText(name);
        des.setText(description);
        add.setText(address);

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getIntent().getStringExtra("price") != null) {
                    Intent intent = new Intent(DairyDetails.this, ConfirmFinalOrderActivity.class);
                    intent.putExtra("dairyname", name);
                    intent.putExtra("phonee",getIntent().getStringExtra("phonee"));
                    intent.putExtra("Total Price", getIntent().getStringExtra("price"));
                    startActivity(intent);
                    }
                    else {
                        Toast.makeText(DairyDetails.this,"Please Select a Product First",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
}
