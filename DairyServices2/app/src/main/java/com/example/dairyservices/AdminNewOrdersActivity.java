package com.example.dairyservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dairyservices.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    private String name1,name2,phone;
    int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);


        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
        name1=getIntent().getStringExtra("dairy");
        phone=getIntent().getStringExtra("phone");
    }


    @Override
    protected void onStart() {
        super.onStart();
        flag=0;


            FirebaseRecyclerOptions<AdminOrders> options =
                    new FirebaseRecyclerOptions.Builder<AdminOrders>()
                            .setQuery(ordersRef, AdminOrders.class)
                            .build();

            final FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                    new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {
                            if(name1.equals(model.getDairy())) {
                                flag=1;
                                holder.userName.setText("Name: " + model.getName());
                                holder.userPhoneNumber.setText("Phone: " + model.getPhone());
                                holder.userTotalPrice.setText("Total Amount =  ₹ " + model.getTotalAmount());
                                holder.userDateTime.setText("Order at: " + model.getDate() + "  " + model.getTime());
                                holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + ", " + model.getCity());

                                holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String uID = getRef(position).getKey();

                                        Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                                        intent.putExtra("uid", uID);
                                        startActivity(intent);
                                    }

                                });

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        CharSequence options[] = new CharSequence[]
                                                {
                                                        "Yes",
                                                        "No"
                                                };

                                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                        builder.setTitle("Have you shipped this order products ?");

                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (i == 0) {
                                                    String uID = getRef(position).getKey();

                                                    RemoverOrder(uID);
                                                } else {
                                                    finish();
                                                }
                                            }
                                        });
                                        builder.show();
                                        }
                                });
                            }
                            else {
                                holder.relativeLayout.setVisibility(View.INVISIBLE);
                                if(flag==0) {
                                    Toast.makeText(AdminNewOrdersActivity.this, "No Received Orders Yet", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @NonNull
                        @Override
                        public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                            return new AdminOrdersViewHolder(view);
                        }
                    };

            ordersList.setAdapter(adapter);
            adapter.startListening();
    }




    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button ShowOrdersBtn;
        public RelativeLayout relativeLayout;

        public AdminOrdersViewHolder(View itemView)
        {
            super(itemView);


            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            ShowOrdersBtn = itemView.findViewById(R.id.show_all_products_btn);
            relativeLayout=itemView.findViewById(R.id.order_layout);
        }
    }




    private void RemoverOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }

}
