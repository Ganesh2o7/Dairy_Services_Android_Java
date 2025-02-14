package com.example.drawer1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.drawer1.MainActivity;
import com.example.drawer1.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider img1 = view.findViewById(R.id.slider1);
        List<SlideModel> slideModels1 = new ArrayList<>();
        slideModels1.add(new SlideModel("https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940","Nature"));
        slideModels1.add(new SlideModel("https://www.incimages.com/uploaded_files/image/970x450/getty_854296630_2000133320009280254_410436.jpg","Milk"));
        img1.setImageList(slideModels1,true);

        return view;
    }
}