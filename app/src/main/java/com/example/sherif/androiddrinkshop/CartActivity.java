package com.example.sherif.androiddrinkshop;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sherif.androiddrinkshop.Adapter.CartAdapter;
import com.example.sherif.androiddrinkshop.Adapter.FavoriteAdapter;
import com.example.sherif.androiddrinkshop.Database.ModelDB.Cart;
import com.example.sherif.androiddrinkshop.Database.ModelDB.Favorite;
import com.example.sherif.androiddrinkshop.Retrofit.IDrinkShopAPI;
import com.example.sherif.androiddrinkshop.Utils.Common;
import com.example.sherif.androiddrinkshop.Utils.RecyclerItemTouchHelper;
import com.example.sherif.androiddrinkshop.Utils.RecyclerItemTouchHelperListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    RecyclerView recycler_cart;
    Button btn_place_order;

    List<Cart> cartList = new ArrayList<>();

    IDrinkShopAPI mService;

    CompositeDisposable compositeDisposable;

    CartAdapter cartAdapter;
    RelativeLayout cartRootLayout;
    private EditText edt_comment, edt_other_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();

        recycler_cart = (RecyclerView) findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        mService = Common.getAPI();

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);

        btn_place_order = (Button) findViewById(R.id.btn_place_order);
        cartRootLayout = (RelativeLayout) findViewById(R.id.cart_rootLayout);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                placeOrder();
            }
        });

        loadCartItems();
    }

    private void placeOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit Order");

        View submit_order = LayoutInflater.from(this).inflate(R.layout.submit_order_layout, null);

        edt_comment = (EditText) submit_order.findViewById(R.id.edt_comment);
        edt_other_address = (EditText) submit_order.findViewById(R.id.edt_other_address);

        final RadioButton rdi_user_address = (RadioButton) submit_order.findViewById(R.id.rdi_user_address);
        final RadioButton rdi_other_address = (RadioButton) submit_order.findViewById(R.id.rdi_other_address);

        rdi_user_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    rdi_user_address.setEnabled(false);
            }
        });

        rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    rdi_user_address.setEnabled(true);
            }
        });

        builder.setView(submit_order);

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String orderComment = edt_comment.getText().toString();
                final String orderAddress;
                if (rdi_user_address.isChecked())
                    orderAddress = Common.currentUser.getAddress();
                else if (rdi_other_address.isChecked())
                    orderAddress = edt_other_address.getText().toString();
                else
                    orderAddress = "";

                //Submit Order
                compositeDisposable.add(
                        Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                            @Override
                            public void accept(List<Cart> carts) throws Exception {
                                if (!TextUtils.isEmpty(orderAddress))
                                    sendOrderToServer(Common.cartRepository.sumPrice(),
                                            carts, orderComment, orderAddress);
                                else
                                    Toast.makeText(CartActivity.this, "Order Address can't be null", Toast.LENGTH_SHORT).show();
                            }
                        })
                );
            }
        });

        builder.show();

    }

    private void sendOrderToServer(float sumPrice, List<Cart> carts, String orderComment, String orderAddress) {
        if (carts.size() > 0) {
            String orderDetail = new Gson().toJson(carts);

            mService.submitOrder(sumPrice, orderDetail, orderComment, orderAddress, Common.currentUser.getPhone())
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(CartActivity.this, "Order has been submitted", Toast.LENGTH_SHORT).show();
                            //Clear Cart
                            Common.cartRepository.emptyCart();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("Error", t.getMessage());
                        }
                    });
        }
    }

    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItem(carts);
                    }
                })
        );
    }

    private void displayCartItem(List<Cart> carts) {
        cartList = carts;
        cartAdapter = new CartAdapter(this, carts);
        recycler_cart.setAdapter(cartAdapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewHolder) {
            String name = cartList.get(viewHolder.getAdapterPosition()).name;

            final Cart deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            cartAdapter.removeItem(deletedIndex);
            //delete item from database
            Common.cartRepository.deleteCartItem(deletedItem);

            Snackbar snackbar = Snackbar.make(cartRootLayout, new StringBuilder(name).append(" removed from Favorite List").toString(), Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartAdapter.restoreItem(deletedItem, deletedIndex);
                    Common.cartRepository.insertToCart(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
