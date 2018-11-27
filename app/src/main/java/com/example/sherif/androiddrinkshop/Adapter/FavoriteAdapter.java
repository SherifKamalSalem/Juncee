package com.example.sherif.androiddrinkshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sherif.androiddrinkshop.Database.ModelDB.Cart;
import com.example.sherif.androiddrinkshop.Database.ModelDB.Favorite;
import com.example.sherif.androiddrinkshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    Context context;
    List<Favorite> favoriteList;

    public FavoriteAdapter(Context context, List<Favorite> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fav_item_layout, viewGroup, false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int position) {

        Picasso.with(context).load(favoriteList.get(position).link).into(favoriteViewHolder.img_product);
        favoriteViewHolder.txt_price.setText(new StringBuilder("$").append(favoriteList.get(position).price).toString());
        favoriteViewHolder.txt_product_name.setText(favoriteList.get(position).name);

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_product_name, txt_price;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = (ImageView) itemView.findViewById(R.id.img_product);
            txt_product_name = (TextView) itemView.findViewById(R.id.txt_product_name);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);

            view_background = (RelativeLayout) itemView.findViewById(R.id.view_background);
            view_foreground = (LinearLayout) itemView.findViewById(R.id.view_foreground);
        }
    }

    public void removeItem(int position) {
        favoriteList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Favorite item, int position) {
        favoriteList.add(item);
        notifyItemInserted(position);
    }
}
