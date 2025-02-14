package com.example.dairyservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dairyservices.Model.Products;
import com.example.dairyservices.Prevalent.Prevalent;
import com.example.dairyservices.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView =(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        if (!type.equals("Admin"))
        {
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }

        ImageSlider img1 = findViewById(R.id.slider1);
        List<SlideModel> slideModels1 = new ArrayList<>();
        slideModels1.add(new SlideModel("https://ithinklogistics.com/blog/wp-content/uploads/2019/07/cropped-shipping-products-from-home.jpg","Easy Delivery Of Products"));
        slideModels1.add(new SlideModel("https://www.verywellfit.com/thmb/bux2qKNIKWiwPLU3d5GpPjcWbdE=/1500x1000/filters:fill(FFDB5D,1)/coconut-milk_annotated-8b864d23e7ef4c238cb956eaf5462f22.jpg","Quality Products"));
        slideModels1.add(new SlideModel("https://image.freepik.com/free-photo/30-percent-off-promotion_2227-146.jpg","Only For 5 days"));
        slideModels1.add(new SlideModel("https://www.brundamilkcart.com/uploads/pages/WhatsApp_Image_2020-05-16_at_9_15_39_AM.jpeg","Offer Until 31st May"));
        slideModels1.add(new SlideModel("https://www.verywellfit.com/thmb/hZw-djVaza-dDkyLKLseV_rd53Y=/1500x1000/filters:fill(FFDB5D,1)/cottage-cheese_annotated2-c5d7204446c14744b9fb1e277e06768c.jpg","Cottage Cheese Available"));
        slideModels1.add(new SlideModel("https://i2.wp.com/www.eatthis.com/wp-content/uploads/2020/01/how-to-make-a-milkshake-5.jpg?fit=1200%2C879&ssl=1","Milk Shakes Available"));
        slideModels1.add(new SlideModel("https://fiverrtutorial.com/wp-content/uploads/2013/12/top-seller-stamp.jpg","Trusted Sellers"));
        slideModels1.add(new SlideModel("https://www.printvenue.com/static/f50-1st-user-mob","Offers For New Customers"));
        slideModels1.add(new SlideModel("https://blog.insideview.com/wp-content/uploads/2012/07/increase-sales.jpeg","Increase Productivity"));
        slideModels1.add(new SlideModel("https://lh3.googleusercontent.com/proxy/s7BmnbGZr0Zltxab4jELTtoUW_DSzdHgFbQCPAtgzr8WJjIwId8SHbZBTKs4vAkASOgoDJSZQ2UrvhKLpcFOnE3AIOgZCfYdEngrcGK2eYjxMjqSsXA",""));

        img1.setImageList(slideModels1,true);

        recyclerView = findViewById(R.id.recycler_menu);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(ProductsRef, Products.class)
                            .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                        {
                            holder.txtProductName.setText(model.getPname());
                            //holder.txtProductDescription.setText(model.getDescription());
                            holder.txtProductPrice.setText("Price = " +" ₹"+ model.getPrice());
                            Picasso.get().load(model.getImage()).into(holder.imageView);


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    if (type.equals("Admin"))
                                    {
                                        Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                                        intent.putExtra("pid", model.getPid());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                        intent.putExtra("pid", model.getPid());
                                        startActivity(intent);
                                    }
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                        {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }

    @Override
    protected void onStart()
    {
        super.onStart();


    }
   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.product_view);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_search)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
                startActivity(intent);
            }
        }

        else if (id==R.id.oursellers)
        {
            Intent intent = new Intent(HomeActivity.this, OurSellers.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_settings)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_logout)
        {
            if (!type.equals("Admin"))
            {
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
        else if ((id==R.id.maps))
        {
            if(!type.equals("Admin")) {
                Toast.makeText(HomeActivity.this, "Please Turn on Internet and Location for better experience",
                        Toast.LENGTH_LONG).show();
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
            else {
                Toast.makeText(this,"This Option is Only User Not for Admins",Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
