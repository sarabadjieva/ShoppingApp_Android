package com.example.androidapp_citb704_F102014.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidapp_citb704_F102014.R;
import com.example.androidapp_citb704_F102014.ScreenSliderPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    private int lastTab = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance( ) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //page slider and tab layout logic
        ViewPager2 viewPager2 = view.findViewById(R.id.pager);

        ScreenSliderPageAdapter adapter = new ScreenSliderPageAdapter(this);
        viewPager2.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText(getString(R.string.app_name));
                        } else  {
                            tab.setText(getString(R.string.categories));
                        }
                    }
                }).attach();

        // Inflate the layout for this fragment
        return view;
    }
}