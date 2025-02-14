package com.example.drawer1.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drawer1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.ViewHolder> {

    private ArrayList<String> mnames=new ArrayList<>();
    private ArrayList<String> maddress=new ArrayList<>();
    private ArrayList<Integer> mimages=new ArrayList<>();
    private ArrayList<Double> mrating=new ArrayList<>();
    private Fragment mContext;
    FirebaseAuth mAuth;

    public ProgrammingAdapter(Fragment context, ArrayList<String> names, ArrayList<Integer> images, ArrayList<String> address, ArrayList<Double> ratings){
        mnames=names;
        mimages=images;
        mContext=context;
        maddress=address;
        mrating=ratings;
        mAuth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(mContext)
        .asBitmap()
        .load(mimages.get(position))
        .into(holder.imgicon);

        holder.txtTitle.setText(mnames.get(position));
        //holder.imgicon.setImageResource(images[position]);
        holder.cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        try {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user.getEmail()!=null) {
                        Toast.makeText(mContext.getContext(), mnames.get(position), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContext.getContext(), seller.class);
                        intent.putExtra("rating",mrating.get(position));
                        intent.putExtra("address",maddress.get(position));
                        intent.putExtra("image_url", mimages.get(position));
                        intent.putExtra("name", mnames.get(position));
                        mContext.startActivity(intent);
            }
            else
            {
                Toast.makeText(mContext.getContext(), "Please Login or register Frist",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (NullPointerException a){
            Toast.makeText(mContext.getContext(), "Please Login or register Frist",
                    Toast.LENGTH_LONG).show();
        }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mnames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgicon;
        TextView txtTitle;
        LinearLayout cards;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgicon = (ImageView) itemView.findViewById(R.id.imgicon);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            cards = (LinearLayout) itemView.findViewById(R.id.cards);
        }
    }
}
