package com.example.drawer1;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.drawer1.ui.home.HomeFragment;
import com.example.drawer1.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile,
                R.id.nav_room, R.id.nav_setting, R.id.nav_business, R.id.nav_about, R.id.nav_login)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Toast.makeText(MainActivity.this, "Please Turn on Internet and Location for better expirence",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView t11;
        mAuth=FirebaseAuth.getInstance();
        int i = item.getItemId();

        if (i == R.id.action_Login) {

            LoginFragment fm = new LoginFragment();
            final AlertDialog.Builder alert1 = new AlertDialog.Builder(MainActivity.this);
            View mm = getLayoutInflater().inflate(R.layout.dialog_login, null);
            Button b1 = mm.findViewById(R.id.login);
            Button b2=mm.findViewById(R.id.forgot2);
            final TextView no = (TextView) mm.findViewById(R.id.phoneno);
            final TextView pass = (TextView) mm.findViewById(R.id.password);

            alert1.setView(mm);
            final AlertDialog alertDialog1=alert1.create();
            alertDialog1.setCanceledOnTouchOutside(true);
            alertDialog1.show();
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                    String email3 = no.getText().toString();
                    String pass3 = pass.getText().toString();
                    if (networkInfo!=null && networkInfo.isConnected()) {
                        if (!TextUtils.isEmpty(email3) && !TextUtils.isEmpty(pass3)) {
                            mAuth.signInWithEmailAndPassword(email3, pass3)
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Login Successful",
                                                        Toast.LENGTH_LONG).show();
                                                alertDialog1.dismiss();

                                            } else {
                                                Toast.makeText(MainActivity.this, "Login failed Invalid Email or password",
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill the details",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Please Turn On The Internet Connection First",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                    try {
                        if(networkInfo!=null && networkInfo.isConnected()) {
                            if (no.getText().toString() != null) {
                                mAuth.sendPasswordResetEmail(no.getText().toString());
                                Toast.makeText(MainActivity.this, "Please Check your Email to Reset Your Password ",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Please Enter Your Email First",
                                        Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Please Turn On The Internet Connection First",
                                    Toast.LENGTH_LONG).show();
                        }
                    }catch (IllegalArgumentException ignored){
                        Toast.makeText(MainActivity.this, "Please Enter Your Email First",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        if (i == R.id.action_sign) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        if (i==R.id.mapsss) {
            Toast.makeText(MainActivity.this, "Please Turn on Internet and Location for better experience",
                    Toast.LENGTH_LONG).show();
            Intent intent=getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
            startActivity(intent);
        }
        if (i==R.id.logut){
            try {
                if(mAuth.getCurrentUser()!=null){
                    mAuth.signOut();
                    Toast.makeText(MainActivity.this, "Logout Successful",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Not Signed In Yet",
                            Toast.LENGTH_LONG).show();
                }
            }catch (NullPointerException ignored){
                Toast.makeText(MainActivity.this, "Not Signed In Yet",
                        Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }

    public void dialog(MenuItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View m = getLayoutInflater().inflate(R.layout.dialog_box, null);
        alert.setView(m);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Button b1 = (Button) m.findViewById(R.id.d_cancel);
        Button b2 = (Button) m.findViewById(R.id.d_submit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thankx For Feedback", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

    }






}
