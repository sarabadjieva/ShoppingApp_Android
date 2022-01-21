package com.example.androidapp_citb704_F102014.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidapp_citb704_F102014.Data.CategoryData;
import com.example.androidapp_citb704_F102014.HomeActivity;
import com.example.androidapp_citb704_F102014.ItemsAdapter;
import com.example.androidapp_citb704_F102014.R;

public class SingleCategoryFragment extends Fragment {

    CategoryData categoryData = new CategoryData();
    public RecyclerView mRecyclerView;
    public ItemsAdapter mAdapter;

    public SingleCategoryFragment() {
        // Required empty public constructor
    }

    public static SingleCategoryFragment newInstance() {
        SingleCategoryFragment fragment = new SingleCategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryData = getArguments().getParcelable(HomeActivity.CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_category, container, false);

        TextView categoryLabel = view.findViewById(R.id.category_name_label);
        categoryLabel.setText(categoryData.getName());

        mRecyclerView = view.findViewById(R.id.recycler_view_items);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mAdapter = new ItemsAdapter(categoryData.getItems(), 0);
        mRecyclerView.setAdapter(mAdapter);

        //add drawables
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        return view;
    }
}