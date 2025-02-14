package com.example.drawer1;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {
    private Button btn;
    private EditText tv, username, phone, email, pass1,pass2;
    DatabaseReference dbUser;
    private FirebaseAuth mAuth;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = (Button) findViewById(R.id.submit);
        username =(EditText) findViewById(R.id.username1);
        phone =(EditText) findViewById(R.id.mobileno1);
        pass1 =(EditText) findViewById(R.id.pass1);
        email =(EditText) findViewById(R.id.email1);
        pass2=(EditText) findViewById(R.id.pass2);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(this);
        btn.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        btn.setBackgroundColor(256);
        String user = username.getText().toString();
        String pass3 = pass1.getText().toString();
        String email3 = email.getText().toString();
        String phone3 = phone.getText().toString();
        String pass4 = pass2.getText().toString();
        dbUser= FirebaseDatabase.getInstance().getReference().child("user_info");
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()) {
            if (!TextUtils.isEmpty(user) || !TextUtils.isEmpty(pass3) || !TextUtils.isEmpty(email3) ||
                    !TextUtils.isEmpty(phone3)) {
                if (phone3.length()==10) {
                    if (pass3.equals(pass4)) {
                        if (email3.contains("@gmail")) {
                            if (pass3.length()>=8) {
                                String id = dbUser.push().getKey();
                                User u1 = new User(id, user, phone3, email3, pass3);
                                dbUser.child(id).setValue(u1);
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully",
                                        Toast.LENGTH_LONG).show();
                                mAuth.createUserWithEmailAndPassword(email3, pass3);
                                pass1.setText("");
                                username.setText("");
                                email.setText("");
                                phone.setText("");
                                pass2.setText("");
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Password Must Be Greater Than 8 Characters",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Email Is Not Correct",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pass2.setText("");
                        Toast.makeText(RegisterActivity.this, "Password Does Not Match",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Phone Number Is Not Valid",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please Fill all the details",
                        Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(RegisterActivity.this, "Please Turn On The Internet Connection First",
                    Toast.LENGTH_LONG).show();
        }
        btn.setBackgroundResource(R.drawable.shape);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        btn.setBackgroundColor(255);
        return false;
    }
}