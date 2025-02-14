package com.example.drawer1.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.drawer1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private TextView t1,t2,t3,t4;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String snapshot;
    private ProgressBar progressBar;
    @SuppressLint("ShowToast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        t1 = view.findViewById(R.id.user_name);
        t2 = view.findViewById(R.id.phone_);
        t4 = view.findViewById(R.id.email_);
        progressBar=view.findViewById(R.id.progress);
        user = mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference();
        progressBar.setVisibility(View.VISIBLE);

        try {
            if (mAuth.getCurrentUser()==null){
                Toast.makeText(this.getContext(),"Please Login or Register First To See Your Profile",
                        Toast.LENGTH_LONG).show();
            }
            else {
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.child("user_info").getChildren();
                        ArrayList<String> child=new ArrayList<>();
                        for (DataSnapshot c:children){
                            String cc=c.getKey();
                            child.add(cc);
                        }
                        for (int i = 0; i < (child.size() - 1); i++){
                            snapshot=child.get(i);
                            String s = dataSnapshot.child("user_info").child(snapshot).child("email").getValue(String.class);
                            if(s.equals(user.getEmail())){
                                break;
                            }
                        }
                        String s1=dataSnapshot.child("user_info").child(snapshot).child("name").getValue(String.class);
                        String s2=dataSnapshot.child("user_info").child(snapshot).child("phone").getValue(String.class);
                        t1.setText(s1);
                        t2.setText(s2);
                        t4.setText(user.getEmail());
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }
        catch (NullPointerException ignored){

        }
        return view;
    }
}