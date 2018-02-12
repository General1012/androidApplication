package com.example.mandelacomp3.mypnp.adapter;
/**
 * Created by MANDELACOMP3 on 2017/11/27.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mandelacomp3.mypnp.Item;
import com.example.mandelacomp3.mypnp.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProductListAdapter extends ArrayAdapter<Item> {
    private List<Item> items;

    public ProductListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        items = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_row, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name_text_view_row);

            holder.price = (TextView) convertView.findViewById(R.id.price_text_view_row);




            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //bind data to views.
      //  holder.icon.setImageDrawable(getItem(position).getImage());
        holder.name.setText(getItem(position).getName());

        holder.price.setText(String.valueOf(getItem(position).getPrice()));

        holder.icon = (ImageView) convertView.findViewById(R.id.icon_image_view_row);


        final String pureBase64Encoded = getItem(position).getImage().substring(getItem(position).getImage().indexOf(",") + 1);
        decodeImageString(pureBase64Encoded, holder.icon);



       // ImageDownloader imageDownloader = new ImageDownloader();
      //  imageDownloader.download(getItem(position).getImage(),holder.icon);







        return convertView;
    }

    private static class ViewHolder {
        TextView name, price;
        ImageView icon;
    }
    public class ImageRequestAsk extends AsyncTask<String , Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                InputStream inputStream = new java.net.URL(params[0]).openStream() ;
                return BitmapFactory.decodeStream(inputStream) ;

            }catch (Exception e){
                return null ;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){super.onPostExecute(bitmap);}

    }

    private void decodeImageString(String imageString, ImageView imageView){

        byte[] decodeString = Base64.decode (imageString, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray (decodeString, 0, decodeString.length);
        imageView.setImageBitmap(decoded);

    }



}
