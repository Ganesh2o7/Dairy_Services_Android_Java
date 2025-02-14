package com.example.dairyservices.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dairyservices.Interface.ItemClickListner;
import com.example.dairyservices.R;

public class SellerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtDairyName;
    public ImageView imageView;
    public ItemClickListner listner;

    public SellerViewHolder(View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imgicon);
        txtDairyName = (TextView) itemView.findViewById(R.id.txtTitle);
    }
    public void setItemClickListner(ItemClickListner listner)
    {

        this.listner = listner;
    }
    public void onClick(View view)
    {

        listner.onClick(view, getAdapterPosition(), false);
    }

}
