package com.example.sherif.androiddrinkshop.Utils;

import com.example.sherif.androiddrinkshop.Database.DataSource.CartRepository;
import com.example.sherif.androiddrinkshop.Database.DataSource.FavoriteRepository;
import com.example.sherif.androiddrinkshop.Database.Local.DSRoomDatabase;
import com.example.sherif.androiddrinkshop.Model.Category;
import com.example.sherif.androiddrinkshop.Model.Drink;
import com.example.sherif.androiddrinkshop.Model.User;
import com.example.sherif.androiddrinkshop.Retrofit.IDrinkShopAPI;
import com.example.sherif.androiddrinkshop.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {

    //Please check your device ip address and be sure it's the same as here
    private static final String BASE_URL = "http://192.168.1.105/drinkshop/";
    public static final String TOPPING_MENU_ID = "7";

    public static User currentUser = null;
    public static Category currentCategory = null;

    public static List<Drink> toppingList = new ArrayList<>();

    public static double toppingPrice = 0.0;
    public static List<String> toppingAdded = new ArrayList<>();
    //Hold field for using it in calculating price of confirm dialog
    public static int sizeOfCup = -1; //-1 no choice (error), 0 : M, 1 : L
    public static int sugar = -1;
    public static int ice = -1;

    //Room database system
    public static DSRoomDatabase cartDatabase;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;


    public static IDrinkShopAPI getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
}
