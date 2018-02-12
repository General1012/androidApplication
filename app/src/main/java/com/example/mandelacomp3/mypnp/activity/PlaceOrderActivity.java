package com.example.mandelacomp3.mypnp.activity;
/**
 * Created by MANDELACOMP3 on 2017/11/27.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.mandelacomp3.mypnp.Cart;
import com.example.mandelacomp3.mypnp.Item;
import com.example.mandelacomp3.mypnp.JsonArrayRequest;
import com.example.mandelacomp3.mypnp.R;
import com.example.mandelacomp3.mypnp.Singleton;
import com.example.mandelacomp3.mypnp.manager.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class PlaceOrderActivity extends AppCompatActivity {

    String sessionKeeper = null;        // use this to keep session
    private static final String TAG = "event";
    EditText userID,username, clientOrderDate, usercellphone ,destination ;
    ImageButton placeorderBTN;

    String str_userID;
    String str_username;
    String str_clientOrderDate;
    String str_usercellphone ;
    String str_destination ;

    SessionManager session;




    //MainActivity mainActivity  ;
    //Cart cart = mainActivity.getCart() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);


        Toast.makeText(getApplicationContext(),Singleton.getInstance().cart.getAllItems().toString(), Toast.LENGTH_LONG).show();
        String json = new Gson().toJson(Singleton.getInstance().cart.getAllItems() );
        Toast.makeText(PlaceOrderActivity.this,json, Toast.LENGTH_SHORT);

        username = (EditText) findViewById(R.id.nameText);
        clientOrderDate = (EditText) findViewById(R.id.dateText);
        usercellphone = (EditText) findViewById(R.id.cellphonenumberText);
        destination = (EditText) findViewById(R.id.addressText);

        placeorderBTN = (ImageButton) findViewById(R.id.placeorderBTN);

        session = new SessionManager(getApplicationContext());

        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final String orderDate = cal.get(Calendar.DAY_OF_MONTH) + ":" + (cal.get(Calendar.MONTH) + 1) + ":"
                + cal.get(Calendar.YEAR) + " <<>> " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);


        clientOrderDate.setText("Date:" + orderDate);
        str_clientOrderDate = clientOrderDate.getText().toString();


        username.setText(Singleton.getInstance().UserEmail);

        str_username = username.getText().toString();


        // Toast.makeText(PlaceOrderActivity.this, cart.getAllItems().toString(), Toast.LENGTH_SHORT).show();

        Button logout ;

        logout = (Button) findViewById ( R.id.logoutBTN );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (PlaceOrderActivity.this, LoginActivity.class);

                session.logoutUser();



                startActivity(intent);
            }
        });



        placeorderBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                str_usercellphone = usercellphone.getText().toString();
                Singleton.getInstance().Usercell =   str_usercellphone ;

                str_destination = destination.getText().toString();
                Singleton.getInstance().UserDestination =  destination.getText().toString();
                String id = "" ;

                //     session.createLoginSession(str_username, str_usercellphone);

                //L cart = new Cart() ;

             //  Singleton.getInstance().cell =  usercellphone.getText().toString() ;











///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                if (  str_username.equals("") || str_clientOrderDate.equals("") || str_usercellphone.equals("") || str_destination.equals("") ) {

                    Toast.makeText(PlaceOrderActivity.this, "Please Enter All The Fields", Toast.LENGTH_SHORT);
                } else {
                    // volleyCall( str_name, str_surname, str_email, password );
                    Singleton.getInstance().OrderDate = orderDate ;
                    volleyCall(Singleton.getInstance().UserEmail,Singleton.getInstance().Usercell,orderDate ,
                            str_destination ,Singleton.getInstance().OrderPrice);
                }

            }
        });
    }

    private void volleyCall(String username, String usercellphone, String date, String destination , Double total) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://192.168.2.197:8080/checkout";

        ArrayList<Object> myCartFormatted = new ArrayList<>();
        JsonArray formatHttpCart;

        Cart<Item> overrideCart = Singleton.getInstance().cart;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObject = null;


        String prodName[] = new String[overrideCart.getSize()];
        String prodDesc[] = new String[overrideCart.getSize()];
        Double prodPrice[] = new Double[overrideCart.getSize()];
        String prodImage[] = new String[overrideCart.getSize()];
        Integer prodId[] = new Integer[overrideCart.getSize()];

        for (int i = 0; i < overrideCart.getSize(); i++) {

            Item product = overrideCart.getItem(i);

            prodName[i] = product.getName();
            prodDesc[i] = product.getDescription();
            prodPrice[i] = product.getPrice();
            prodImage[i] = product.getImage();
           // prodId[i] = product.getProductId();

            jsonObject = Json.createObjectBuilder()
                    .add("name", prodName[i])
                    .add("description", prodDesc[i])
                    .add("price", prodPrice[i])
                    .add("image", prodImage[i]);


            arrayBuilder.add(jsonObject);
        }

        JsonObject jsonParams = Json.createObjectBuilder()
                .add("id", 12)
                .add("userID", 12)
                .add("username", username)
                .add("clientOrderDate", date)
                .add("usercellphone", usercellphone)
                .add("destination", destination)
                .add("total", total)
                .add("lineProducts", arrayBuilder )
                .build();

        Log.d(TAG, "Json:" + jsonParams);
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, URL, jsonParams.toString(),
                new Response.Listener<JsonObject>() {
                    @Override
                    public void onResponse(JsonObject response) {
                        Intent intent = new Intent (PlaceOrderActivity.this, ThankYouActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        Log.d(TAG, "Error" + error + "\nmessage" + error.getMessage());
                    }
                });
        queue.add(postRequest);

    }
}