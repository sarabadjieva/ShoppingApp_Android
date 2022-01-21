package com.example.androidapp_citb704_F102014.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidapp_citb704_F102014.Data.ItemData;
import com.example.androidapp_citb704_F102014.Helpers.DataBaseHelper;
import com.example.androidapp_citb704_F102014.ItemsAdapter;
import com.example.androidapp_citb704_F102014.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public ItemsAdapter mAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        DataBaseHelper dbHelper = new DataBaseHelper(getContext());
        ArrayList<ItemData> items = dbHelper.getItemsInFavDB();
        dbHelper.close();

        mRecyclerView = view.findViewById(R.id.recycler_view_fav_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemsAdapter(items, 1);
        mRecyclerView.setAdapter(mAdapter);

        //add drawables
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        return view;
    }
}