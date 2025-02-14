package com.example.dairyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dairyservices.Model.Admins;
import com.example.dairyservices.Model.Products;
import com.example.dairyservices.ViewHolder.CartViewHolder;
import com.example.dairyservices.ViewHolder.ProductViewHolder;
import com.example.dairyservices.ViewHolder.SellerViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OurSellers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference AdminsRef;
    private String dis;

    String type ="";
    String amount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_sellers);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            try {
                type = getIntent().getExtras().get("Admins").toString();
            }
            catch (NullPointerException ignored){

            }
        }
        amount=getIntent().getStringExtra("amount");
        AdminsRef = FirebaseDatabase.getInstance().getReference().child("Admins");

        recyclerView = findViewById(R.id.recycler_seller);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Admins> options =
                new FirebaseRecyclerOptions.Builder<Admins>()
                        .setQuery(AdminsRef, Admins.class)
                        .build();

        FirebaseRecyclerAdapter<Admins, SellerViewHolder> adapter =
                new FirebaseRecyclerAdapter<Admins, SellerViewHolder>(options) {
                    protected void onBindViewHolder(@NonNull SellerViewHolder holder, int position, @NonNull final Admins model)
                    {
                        holder.txtDairyName.setText(model.getName());
                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (type.equals("Admins")) {
                                    Intent intent = new Intent(OurSellers.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("name",model.getName());
                                    startActivity(intent);
                                } else {

                                    DatabaseReference r=FirebaseDatabase.getInstance().getReference().child("Admins");
                                    r.child(model.getPhone()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            dis=dataSnapshot.child("description").getValue().toString();
                                            Intent intent = new Intent(OurSellers.this, DairyDetails.class);
                                            intent.putExtra("name",model.getName());
                                            intent.putExtra("image",model.getImage());
                                            intent.putExtra("phonee",model.getPhone());
                                            intent.putExtra("descrip",dis);
                                            intent.putExtra("address",model.getAddress());
                                            intent.putExtra("price",amount);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        });
                    }
                    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_items_layout, parent, false);
                        SellerViewHolder holder = new SellerViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
