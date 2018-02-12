package com.example.mandelacomp3.mypnp.activity;

/**
 * Created by MANDELACOMP3 on 2017/11/20.
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
import com.example.mandelacomp3.mypnp.Singleton;
import com.example.mandelacomp3.mypnp.manager.AlertDialogManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    AlertDialogManager.SessionManager session;




    private static final String TAG = "event";
    EditText email, password;
    Button login;
    Button gotoregister ;

    String str_email ;
    String str_password ;

    public String getStr_email() {
        return str_email;
    }

    public void setStr_email(String str_email) {
        this.str_email = str_email;
    }

    public String getStr_password() {
        return str_password;
    }

    public void setStr_password(String str_password) {
        this.str_password = str_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new AlertDialogManager.SessionManager(getApplicationContext());

        email = (EditText) findViewById( R.id.emailText);
        password = (EditText) findViewById( R.id.passwordText);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        login = (Button) findViewById ( R.id.loginbtn );
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_email = email.getText().toString();
                str_password = password.getText().toString();

                Singleton.getInstance().UserEmail = str_email ;

                if ( str_email.equals("") || str_password.equals("") ){

                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }
                else{
                    volleyCall( str_email, str_password );
                    session.createLoginSession("", email.getText().toString());
                }
            }
        });

        gotoregister = (Button) findViewById ( R.id.gotoRegisterbtn );
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    public void next(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void volleyCall(final String email, String password ){

        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://192.168.2.197:8080/login";
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("email", email);
        jsonParams.put("password", password);

        Log.d( TAG, "Json:" + new JSONObject(jsonParams));
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message= null;

                        try {
                            message = (String) response.get("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // String status = response.toString
                        if (message.equals("You have successfully logged in")){


                            alert.showAlertDialog(LoginActivity.this, "Login succesfull..", "Username and Password are correct", true);

                            Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            Singleton.getInstance().UserEmail = email;

                        }
                        else
                            alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);

                        Log.d(TAG, "Json" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        Log.d(TAG, "Error" + error + "\nmessage" + error.getMessage() );
                    }
                });
        // postRequest.setsholdcache(false);
        queue.add(postRequest);
    }
}