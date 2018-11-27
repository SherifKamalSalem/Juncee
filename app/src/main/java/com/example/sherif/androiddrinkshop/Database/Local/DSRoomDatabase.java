package com.example.sherif.androiddrinkshop.Database.Local;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.sherif.androiddrinkshop.Database.ModelDB.Cart;
import com.example.sherif.androiddrinkshop.Database.ModelDB.Favorite;

@Database(entities = {Cart.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class DSRoomDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    public abstract FavoriteDAO favoriteDAO();
    private static DSRoomDatabase instance;

    public static DSRoomDatabase getInstance(Context context) {

        if (instance == null)
            instance = Room.databaseBuilder(context, DSRoomDatabase.class, "DrinkShopDB")
            .allowMainThreadQueries()
            .build();

        return instance;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
