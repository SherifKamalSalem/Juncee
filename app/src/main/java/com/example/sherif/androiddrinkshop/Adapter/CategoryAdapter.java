package com.example.sherif.androiddrinkshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherif.androiddrinkshop.DrinkActivity;
import com.example.sherif.androiddrinkshop.Interface.ItemClickListener;
import com.example.sherif.androiddrinkshop.Model.Category;
import com.example.sherif.androiddrinkshop.R;
import com.example.sherif.androiddrinkshop.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position) {

        //load image
        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(categoryViewHolder.image_porduct);

        categoryViewHolder.txt_menu_name.setText(categories.get(position).Name);

        //implement ItemClickListener onclick method
        categoryViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory = categories.get(position);
                //Start new activity
                context.startActivity(new Intent(context, DrinkActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
