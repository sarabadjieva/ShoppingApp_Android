package com.example.androidapp_citb704_F102014;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidapp_citb704_F102014.Fragments.CategoriesFragment;
import com.example.androidapp_citb704_F102014.Fragments.HomepageFragment;

public class ScreenSliderPageAdapter extends FragmentStateAdapter {

    public ScreenSliderPageAdapter(Fragment f) {
        super(f);
    }

    private static final int NUM_PAGES = 2;

    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HomepageFragment();
        } else {
            return new CategoriesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
