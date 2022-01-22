package com.example.androidapp_citb704_F102014.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidapp_citb704_F102014.Data.ItemData;
import com.example.androidapp_citb704_F102014.Helpers.DataBaseHelper;
import com.example.androidapp_citb704_F102014.ItemsAdapter;
import com.example.androidapp_citb704_F102014.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public ItemsAdapter mAdapter;

    private static float priceAll = 0f;
    private static TextView txtPrice;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        DataBaseHelper dbHelper = new DataBaseHelper(getContext());
        ArrayList<ItemData> items = dbHelper.getItemsInCartDB();
        dbHelper.close();

        priceAll = 0f;
        for (ItemData item: items
             ) {
            priceAll += item.getPriceDouble();
        }

        txtPrice = ((TextView)view.findViewById(R.id.cart_price_label));
        txtPrice.setText(NumberFormat.getCurrencyInstance().format(priceAll));

        mRecyclerView = view.findViewById(R.id.recycler_view_cart_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemsAdapter(items, 2);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public static void RemoveFromCart(ItemData itemData){
        priceAll -= itemData.getPriceDouble();
        txtPrice.setText(NumberFormat.getCurrencyInstance().format(priceAll));
    }
}