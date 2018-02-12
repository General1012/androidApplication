package com.example.mandelacomp3.mypnp;

import java.util.List;

/**
 * Created by MANDELACOMP3 on 2017/11/27.
 */

public class Cart<T> {
    private List<T> items;

    public Cart() {

    }

    public Cart(List<T> items) {
        this.items = items;
    }

    public List<T> getAllItems() {
        return items;
    }

    public T getItem( int i ){
        return items.get(i);
    }

    public void addItem(T item) {
        items.add(item);
    }

   // public double total(){
     //   double s  = 0 ;
       // for(List<T> items :items){
        //    s+= items(i).

        //}


    //}

    public void removeItem(T item) {
        items.remove(item);
    }

    public int getSize() {
        return items.size();
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}