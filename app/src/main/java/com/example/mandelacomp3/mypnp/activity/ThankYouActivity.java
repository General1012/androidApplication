package com.example.mandelacomp3.mypnp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mandelacomp3.mypnp.R;
import com.example.mandelacomp3.mypnp.manager.AlertDialogManager;

/**
 * Created by MANDELACOMP3 on 2017/11/28.
 */

public class ThankYouActivity  extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

       final AlertDialogManager.SessionManager session;

        session = new AlertDialogManager.SessionManager(getApplicationContext());

        Button logout ,viewOrder ;

        logout = (Button) findViewById ( R.id.logoutBTN );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ThankYouActivity.this, LoginActivity.class);

                session.logoutUser();



                startActivity(intent);
            }
        });

        viewOrder = (Button) findViewById ( R.id. viewOrderBTN);
        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ThankYouActivity.this, ViewOrder.class);

                session.logoutUser();



                startActivity(intent);
            }
        });




    }




}
