package com.example.mandelacomp3.mypnp.activity;
/**
 * Created by MANDELACOMP3 on 2017/11/27.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mandelacomp3.mypnp.Cart;
import com.example.mandelacomp3.mypnp.Item;
import com.example.mandelacomp3.mypnp.R;
import com.example.mandelacomp3.mypnp.Singleton;
import com.example.mandelacomp3.mypnp.activity.ThankYouActivity;
import com.example.mandelacomp3.mypnp.fragment.CartListFragment;
import com.example.mandelacomp3.mypnp.fragment.ProductDetailsFragment;
import com.example.mandelacomp3.mypnp.fragment.ProductListFragment;
import com.example.mandelacomp3.mypnp.manager.AlertDialogManager;
import com.example.mandelacomp3.mypnp.manager.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ProductListFragment.ProductListControllerCallback<Item>,
        ProductDetailsFragment.ProductDetailsControllerCallback {

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    private double checkOutPrice ;

    private Cart cart;
    private Button cartButton ,checkoutButton ,totalpriceButton; //TextView in toolbar that displays number of items in cart.
    private ProductListFragment productListFragment;
   private PlaceOrderActivity placeOrderActivity ;
    private ProductDetailsFragment productDetailsFragment;
    private CartListFragment cartListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);

        //   Toast.makeText(getApplicationContext(),Singleton.getInstance().session, Toast.LENGTH_LONG).show();

        // Session class instance
        session = new SessionManager(getApplicationContext());

        cart = new Cart();
        Singleton.getInstance().cart = new Cart() ;

        //get the list of saved items, if any.
        final   List<Item> items = (List<Item>) getLastCustomNonConfigurationInstance();

        if (items != null) {
            cart.setItems(items);
            Singleton.getInstance().cart.setItems(items);
        } else {
            cart.setItems(new ArrayList<Item>());
            Singleton.getInstance().cart.setItems(new ArrayList<Item>());
        }

        FragmentManager manager = getSupportFragmentManager();

        if (manager.findFragmentByTag("pjf") != null) {
            productListFragment = (ProductListFragment) manager.findFragmentByTag("plf");
        } else {
            productListFragment = new ProductListFragment();
        }

        if (manager.findFragmentByTag("pdf") != null) {
            productDetailsFragment = (ProductDetailsFragment) manager.findFragmentByTag("pdf");
        } else {
            productDetailsFragment = new ProductDetailsFragment();
        }

        if (manager.findFragmentByTag("clf") != null) {
            cartListFragment = (CartListFragment) manager.findFragmentByTag("clf");
        } else {
            cartListFragment = new CartListFragment();
        }

        if (manager.findFragmentByTag("cdf") != null) {
        //   placeOrderActivity = (PlaceOrderActivity) manager.findFragmentByTag("cdf");
        } else {
            placeOrderActivity = new PlaceOrderActivity();
        }

        cartButton = (Button) findViewById(R.id.cart_button);


        cartListFragment.setCart(cart);

        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.main_content, productListFragment, "plf").commit();
        }


        totalpriceButton = (Button) findViewById(R.id.checkout_priceBTN);

        checkoutButton = (Button) findViewById ( R.id.checkoutBTN );

        //Session
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn() + "User email: " + session.getUserDetails(), Toast.LENGTH_LONG).show();

        session.checkLogin();

        // get user data from session
        final  HashMap<String, String> user = session.getUserDetails();




        //Buttons
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartButtonClicked();


            }
        });
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Snackbar.make(checkoutButton, "Check out functionality not implemented yet!", Snackbar.LENGTH_LONG).show();

                // name


                checkoutButtonClicked() ;




                // Intent intent = new Intent (MainActivity.this, ThankYouActivity.class);
                // startActivity(intent) ;

            }
        });

    }

    private void checkoutButtonClicked() {


        Singleton.getInstance().OrderPrice = checkOutPrice ;
        Intent intent = new Intent (MainActivity.this, PlaceOrderActivity.class);
         startActivity(intent) ;


        }


    //User has clicked the button to shot the contents of the shopping cart.
    private void cartButtonClicked() {
        if (!cartListFragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("clf")
                    .replace(R.id.main_content, cartListFragment, "clf")
                    .commit();

            totalpriceButton.setText("Total:R" + checkOutPrice) ;


        }
    }

    //Callback notifying this activity that an item has been clicked in the ProductListFragment.
    @Override
    public void onItemClicked(Item item) {
        //Need to swap fragments when this happens, passing the data from this item to it.
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);

        productDetailsFragment.setArguments(bundle);

        //Swap out the listFragment for the detials fragment, saving the transaction to the back stack.
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("pdf")
                .replace(R.id.main_content, productDetailsFragment, "pdf")
                .commit();
    }

    //Callback to add an item to the cart.
    @Override
    public void addToCart(Item item) {
        cart.addItem(item);

        checkOutPrice += item.getPrice() ;

        Singleton.getInstance().cart.addItem(item);

        //update the cart button text.
        cartButton.setText("Items: " + cart.getSize());
    }

    //used to persist the list of items in the shopping cart across configuration changes.
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return cart.getAllItems();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //update the cart button text.
        cartButton.setText("Items: " + cart.getSize());
    }
}