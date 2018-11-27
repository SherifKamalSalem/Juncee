package com.example.sherif.androiddrinkshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherif.androiddrinkshop.Adapter.DrinkAdapter;
import com.example.sherif.androiddrinkshop.Model.Drink;
import com.example.sherif.androiddrinkshop.Retrofit.IDrinkShopAPI;
import com.example.sherif.androiddrinkshop.Utils.Common;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DrinkActivity extends AppCompatActivity {

    IDrinkShopAPI mService;
    RecyclerView first_drink;

    TextView txt_banner_name;
    //Rxjava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        mService = Common.getAPI();

        first_drink = (RecyclerView) findViewById(R.id.recycler_drink);
        first_drink.setLayoutManager(new GridLayoutManager(this, 2));
        first_drink.setHasFixedSize(true);


        txt_banner_name = (TextView) findViewById(R.id.txt_menu_name);
        txt_banner_name.setText(Common.currentCategory.Name);

        loadListDrink(Common.currentCategory.ID);
    }

    private void loadListDrink(String menuId) {
        compositeDisposable.add(mService.getDrink(menuId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Drink>>() {
            @Override
            public void accept(List<Drink> drinks) throws Exception {
                displayDrinkList(drinks);
            }
        }));
    }

    private void displayDrinkList(List<Drink> drinks) {
        DrinkAdapter drinkAdapter = new DrinkAdapter(this, drinks);
        first_drink.setAdapter(drinkAdapter);
    }

    //Exit App when click Back button
    boolean isBackButtonPressed = false;

    @Override
    public void onBackPressed() {
        if (isBackButtonPressed) {
            super.onBackPressed();
            return;
        }
        this.isBackButtonPressed = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isBackButtonPressed = false;
    }
}
