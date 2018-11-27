package com.example.sherif.androiddrinkshop.Database.DataSource;

import com.example.sherif.androiddrinkshop.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public interface IFavoriteDataSource {

    Flowable<List<Favorite>> getFavItems();
    int isFavorite(int itemId);
    void insertFavorite(Favorite...favorites);
    void delete(Favorite favorite);
}
