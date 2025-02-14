package com.example.dairyservices;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dairyservices.Prevalent.Prevalent;
import com.example.dairyservices.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity
{
    private String check = "";
    private TextView pageTitle,titleQuestions;
    private EditText phoneNumber,question1,question2;
    private Button verify;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        check = getIntent().getStringExtra("check");
        pageTitle=findViewById(R.id.page_title);
        verify=findViewById(R.id.verify_btn);
        titleQuestions=findViewById(R.id.title_questions);
        phoneNumber=findViewById(R.id.find_phone_number);
        question1=findViewById(R.id.question_1);
        question2=findViewById(R.id.question_2);
    }



    @Override
    protected void onStart()
    {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);
        if (check.equals("settings"))
        {
            displayAnswers();
        pageTitle.setText("Set Questions");
        titleQuestions.setText("Please Set Following Security Questions ");
        verify.setText("Set Questions");
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q1=question1.getText().toString().toLowerCase();
                String q2=question2.getText().toString().toLowerCase();
                if (q1.equals("")&&q2.equals("")){
                    Toast.makeText(ResetPasswordActivity.this,"Please Answer Both Questions",Toast.LENGTH_LONG).show();
                }
                else {
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(Prevalent.currentOnlineUser.getPhone());
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("answer1", q1);
                    userdataMap.put("answer2", q2);
                    ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this,"Security Question Has Been Set Successfully",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String phone = phoneNumber.getText().toString();
                    final String q1 = question1.getText().toString().toLowerCase();
                    final String q2 = question2.getText().toString().toLowerCase();

                    if (!phone.equals("") && !q1.equals("") && !q2.equals("")) {


                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(phone);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String m_phone = dataSnapshot.child("phone").getValue().toString();

                                    if (dataSnapshot.hasChild("Security Questions")) {
                                        String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                                        String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                                        if (!ans1.equals(q1)) {
                                            Toast.makeText(ResetPasswordActivity.this, "Answer 1 is Incorrect",
                                                    Toast.LENGTH_LONG).show();
                                        } else if (!ans2.equals(q2)) {
                                            Toast.makeText(ResetPasswordActivity.this, "Answer 2 is Incorrect",
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                            builder.setTitle("New Password");
                                            final EditText newpass = new EditText(ResetPasswordActivity.this);
                                            newpass.setHint("Write New Password..");
                                            builder.setView(newpass);
                                            builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(final DialogInterface dialog, int which) {
                                                    if (!newpass.getText().toString().equals("")) {
                                                        ref.child("password").setValue(newpass.getText()
                                                                .toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(ResetPasswordActivity.this,
                                                                            "Password Has Changed Successfully",
                                                                            Toast.LENGTH_LONG).show();
                                                                    dialog.cancel();
                                                                    Intent intent=new Intent(ResetPasswordActivity.this,
                                                                            LoginActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.show();
                                        }
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, "You Have Not Set The Security Questions Yet",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(ResetPasswordActivity.this, "Phone Number Is Incorrect",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        Toast.makeText(ResetPasswordActivity.this, "Please Enter All Details",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    private void displayAnswers(){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                .child("Users").child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String ans1=dataSnapshot.child("answer1").getValue().toString();
                    String ans2=dataSnapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
