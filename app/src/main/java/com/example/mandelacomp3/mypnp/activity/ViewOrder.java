package com.example.mandelacomp3.mypnp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mandelacomp3.mypnp.R;
import com.example.mandelacomp3.mypnp.Singleton;

/**
 * Created by MANDELACOMP3 on 2017/12/12.
 */

public class ViewOrder  extends AppCompatActivity {



    String UserEmail;
    String Usercell;
    Double UserDestination;
    String OrderDate;
    String OrderPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworder);


        final TextView userDetails;
        final TextView products;


        userDetails = (TextView) findViewById(R.id.user_text_view_row);
        products = (TextView) findViewById(R.id.products_text_view_row);






        // Empty the TextView
        userDetails.setText("");
        products.setText("");
        // Initialize a new RequestQueue instance


                            //    final String pureBase64Encoded = prodImageString.substring(prodImageString.indexOf(",") + 1);

                                // Display the formatted json data in text view
                               userDetails.append("User email: " + Singleton.getInstance().UserEmail + "\nUser cell: " + Singleton.getInstance().Usercell + "\nUser address: " + Singleton.getInstance().UserDestination  + "\nOrder date: " + Singleton.getInstance().OrderDate+ "\nOrder total price: " + Singleton.getInstance().OrderPrice);
                               userDetails.append("\n\n");

        products.append("Ordered products : " + Singleton.getInstance().cart.getAllItems() );
        userDetails.append("\n\n");

                                // decode base64 string to image
                              //  decodeImageString(pureBase64Encoded, picView);


  //  private void decodeImageString(String imageString, ImageView imageView){

      //  byte[] decodeString = Base64.decode (imageString, Base64.DEFAULT);
      //  Bitmap decoded = BitmapFactory.decodeByteArray (decodeString, 0, decodeString.length);
      //  imageView.setImageBitmap(decoded);

    }


}
