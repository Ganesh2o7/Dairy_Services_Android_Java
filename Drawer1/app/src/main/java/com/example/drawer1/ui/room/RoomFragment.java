package com.example.drawer1.ui.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer1.R;
import com.example.drawer1.ui.ProgrammingAdapter;

import java.util.ArrayList;

public class RoomFragment extends Fragment {

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mAddress=new ArrayList<>();
    private ArrayList<Integer> mimageurl=new ArrayList<>();
    private ArrayList<Double> mratings=new ArrayList<>();

    Integer[] images = new Integer[]{R.drawable.amaraledairy,R.drawable.ananyadairy,R.drawable.ganeshgairy,
            R.drawable.gangadairy,R.drawable.jagdambajairy,R.drawable.jasbirdairy,R.drawable.krushnadairy,
    R.drawable.milkhub,R.drawable.milkpalace,R.drawable.mordendairy,R.drawable.naikdairy,R.drawable.omkardairy,
    R.drawable.pateldairy,R.drawable.pradeepdairy,R.drawable.shubhashdairy,R.drawable.yashodadairy};

    String[] title = {"Amarale Dairy","Ananya Dairy","Ganesh Dairy","Ganga Dairy","Jagdamba Dairy","Jasbir Dairy",
            "Krushna Dairy","Milk Hub","Milk Palace","Modern Dairy","Naik Dairy","Omkar Dairy","Patel Dairy","Pradeep Dairy",
    "Shubhash Dairy","Yashoda Dairy"};

    Double[] rating= new Double[]{3.0,4.0,5.0,3.5,2.5,1.0,2.0,4.5,1.5,1.0,2.0,4.0,5.0,3.5,2.0,2.5};

    String[] address = {"Beed by pass","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        View view = inflater.inflate(R.layout.fragment_room, container, false);
        RecyclerView programminglist = (RecyclerView) view.findViewById(R.id.recyclerview);
        programminglist.setLayoutManager(new LinearLayoutManager(this.getContext()));
        programminglist.setAdapter(new ProgrammingAdapter(this,mNames,mimageurl,mAddress,mratings));
        return view;
    }

    private void init(){
        for(int i=0;i<title.length;i++){
            mNames.add(title[i]);
            mimageurl.add(images[i]);
            mAddress.add(address[i]);
            mratings.add(rating[i]);
        }
    }

 /*   private void initSet(){
        RecyclerView recyclerView=R.id.recyclerview;
        ProgrammingAdapter programmingAdapter=new ProgrammingAdapter(this,mNames,mimageurl);
        recyclerView.setAdapter(programmingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }*/


}