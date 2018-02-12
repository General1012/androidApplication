package com.example.mandelacomp3.mypnp;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MANDELACOMP3 on 2017/11/27.
 */
public class Item implements Parcelable {
    private String name;
    private String description;
    private double price;
    private String image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return getName() + " " + getPrice();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Item() {

    }

    public Item(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        //dest.writeValue(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}