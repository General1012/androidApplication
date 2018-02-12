package com.example.mandelacomp3.mypnp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.example.mandelacomp3.mypnp.Cart;
import com.example.mandelacomp3.mypnp.R;
import com.example.mandelacomp3.mypnp.adapter.ProductListAdapter;

import java.util.List;

/**
 * Created by MANDELACOMP3 on 2017/11/27.
 */
public class CartListFragment extends android.support.v4.app.Fragment {
    private CartDetailsFragmentCallback callback;
    private Cart cart;

    /*Decided to not implement this. */
    public interface CartDetailsFragmentCallback<T> {
        List<T> getCartIetms();
    }

    public void setCart(Cart cart)
    {
        this.cart = cart;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_list_layout, container, false);

        TextView empty = (TextView) view.findViewById(R.id.empty);

        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(new ProductListAdapter(getActivity(), R.layout.product_list_row, cart.getAllItems()));
        list.setEmptyView(empty);

        return view;
    }
}