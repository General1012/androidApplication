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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mandelacomp3.mypnp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    String sessionKeeper = null;        // use this to keep session
    private static final String TAG = "event";
    EditText firstname,lastname, email, password ,address , cellphonNumber;
    Button register ,gotoLogin;

    String str_firstname ;
    String str_lastname;
    String str_email;
    String str_address ;
    String str_cellphonNumber ;
    String str_password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = (EditText) findViewById(R.id.nameText);
        lastname = (EditText) findViewById(R.id.surnameText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        address = (EditText) findViewById(R.id.addressText);
        cellphonNumber = (EditText) findViewById(R.id.cellphonenumberText);
        register = (Button) findViewById(R.id.regbtn);
        gotoLogin = (Button) findViewById(R.id.gotoLoginbtn);


        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_firstname = firstname.getText().toString();
                str_lastname = lastname.getText().toString();
                str_email = email.getText().toString();
                str_address = address.getText().toString();
                str_cellphonNumber = cellphonNumber.getText().toString();
                //****************PASSWORD*****************//
                str_password = password.getText().toString();
                int passwordLength = password.length();
                char[] pd = new char[passwordLength];
                password.getText().getChars(0, passwordLength, pd, 0);

                if (str_firstname.equals("") || str_lastname.equals("") || str_email.equals("") || str_password.equals("") || str_address.equals("") || str_cellphonNumber.equals("") ) {

                    Toast.makeText(RegisterActivity.this, "Please Enter All The Fields", Toast.LENGTH_SHORT);
                } else {
                    // volleyCall( str_name, str_surname, str_email, password );
                    volleyCall(str_firstname, str_lastname, str_email, str_password ,str_address ,str_cellphonNumber);
                }

            }
        });
    }

    private void volleyCall(String firstname, String lastname, String email, String password ,String address ,String cellphonNumber) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://192.168.2.95:8080/register";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("firstname", firstname);
        jsonParams.put("lastname", lastname);
        jsonParams.put("email", email);
        jsonParams.put("password", password);
        jsonParams.put("address", address);

        jsonParams.put("cellphonNumber", cellphonNumber);

        Log.d(TAG, "Json:" + new JSONObject(jsonParams));
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = "";
                        try {
                            status = (String) response.get("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // String status = response.toString
                        if (status.equals("You are successfully registered")) {
                            Toast.makeText(RegisterActivity.this, "You have succesfully registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
                            startActivity(intent) ;
                        } else
                            Toast.makeText(RegisterActivity.this, "Error in registering", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Json" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        Log.d(TAG, "Error" + error + "\nmessage" + error.getMessage());
                    }
                });
        // postRequest.setsholdcache(false);
        queue.add(postRequest);
    }
}