package com.example.drawer1.ui.login;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.drawer1.MainActivity;
import com.example.drawer1.R;
import com.example.drawer1.RegisterActivity;
import com.example.drawer1.User;
import com.example.drawer1.ui.ProgrammingAdapter;
import com.example.drawer1.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginFragment extends Fragment{
    private Button btn,btn2,btn3;
    private EditText pass1,email1,login;
    private ClipData.Item item;
    private FirebaseAuth mAuth;
    private MenuItem menuItem;
    public TextView flag;
    public  FirebaseUser user;
    private Fragment context=this;
    private AppCompatActivity appCompatActivity;
    private NetworkInfo networkInfo;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint({"SetTextI18n", "ResourceType", "ClickableViewAccessibility"})
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ConnectivityManager connectivityManager= (ConnectivityManager)this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) ;
        networkInfo=connectivityManager.getActiveNetworkInfo();
        mAuth = FirebaseAuth.getInstance();
        btn = v.findViewById(R.id.login);
        btn2=v.findViewById(R.id.signup);
        btn3=v.findViewById(R.id.forgot);
        pass1 = (EditText) v.findViewById(R.id.login_pass);
        email1 = (EditText) v.findViewById(R.id.login_email);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn.setBackgroundColor(255);
                return false;
            }
        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn2.setBackgroundColor(0);
                return false;
            }
        });

        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn3.setBackgroundColor(0);
                return false;
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackgroundColor(255);
                Intent intent=new Intent(container.getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setBackgroundColor(255);
                try {
                    if (networkInfo!=null && networkInfo.isConnected()) {
                        if (email1.getText().toString() != null) {
                            mAuth.sendPasswordResetEmail(email1.getText().toString());
                            Toast.makeText(context.getContext(), "Please Check your Email to Reset Your Password ",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context.getContext(), "Please Enter Your Email First",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(context.getContext(), "Please Turn On The Internet Connection First",
                                Toast.LENGTH_LONG).show();
                    }
                }catch (IllegalArgumentException ignored){
                    Toast.makeText(context.getContext(), "Please Enter Your Email First",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(final View v) {
                btn.setBackgroundResource(R.drawable.shape);
                String email3 = email1.getText().toString();
                String pass3 = pass1.getText().toString();
                if (networkInfo!=null&&networkInfo.isConnected()) {
                    if (!TextUtils.isEmpty(email3) && !TextUtils.isEmpty(pass3)) {
                        mAuth.signInWithEmailAndPassword(email3, pass3)
                                .addOnCompleteListener((Activity) container.getContext(), new OnCompleteListener<AuthResult>() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context.getContext(), "Login Successful",
                                                    Toast.LENGTH_LONG).show();
                                            LoginFragment.this.getActivity().setTitle("");
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                           /* fragmentTransaction.add(container.getId(), new HomeFragment());
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();*/
                                            fragmentTransaction.remove(LoginFragment.this);
                                            Intent intent=new Intent(container.getContext(), MainActivity.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(context.getContext(), "Login failed Invalid Email or password",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });
                    } else {
                        Toast.makeText(context.getContext(), "Please fill the details",
                                Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context.getContext(), "Please Turn On The Internet Connection First",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        return v;
    }

}