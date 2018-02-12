package com.example.mandelacomp3.mypnp;

import android.content.Context;
import android.net.http.RequestQueue;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Singleton {

    public String UserEmail = null;
    public String Usercell = null ;
    public String UserDestination = null ;
    public String OrderDate = null ;
    public Double OrderPrice = null ;



    public Cart cart  = null;

    private static Singleton firstInstance = null ;

    private Singleton(){}

    public static Singleton getInstance(){

        if(firstInstance == null){

            firstInstance = new Singleton() ;

        }

        return firstInstance ;
    }
}
