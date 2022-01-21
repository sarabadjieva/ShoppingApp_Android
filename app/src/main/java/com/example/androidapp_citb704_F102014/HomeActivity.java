package com.example.androidapp_citb704_F102014;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.androidapp_citb704_F102014.Data.CategoryData;
import com.example.androidapp_citb704_F102014.Fragments.CartFragment;
import com.example.androidapp_citb704_F102014.Fragments.FavoritesFragment;
import com.example.androidapp_citb704_F102014.Fragments.HomeFragment;
import com.example.androidapp_citb704_F102014.Fragments.SingleCategoryFragment;
import com.example.androidapp_citb704_F102014.Helpers.BitmapCacheHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static final String CATEGORY = "CATEGORY";

    public static BitmapCacheHelper bitmapCacheHelper;
    public ArrayList<CategoryData> categoriesData;

    private int lastClickedMenuButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bitmapCacheHelper = new BitmapCacheHelper();

        //process data
        categoriesData = getIntent().getParcelableArrayListExtra(MainActivity.CATEGORIES_DATA);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNav.setSelectedItemId(R.id.btn_home);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null){
            int id = savedInstanceState.getInt("lastClickedMenuButtonId");
            ((BottomNavigationView) findViewById(R.id.nav_view)).setSelectedItemId(id);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("lastClickedMenuButtonId", lastClickedMenuButtonId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        item.setChecked(true);
        int itemId = item.getItemId();
        lastClickedMenuButtonId = itemId;
        Fragment fragment;

        switch (itemId){
            case R.id.btn_home:
                fragment = new HomeFragment();
                break;
            case  R.id.btn_cart:
                fragment = new CartFragment();
                break;
            case R.id.btn_favorites:
                fragment = new FavoritesFragment();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        if (fragment != null)
        {
            loadFragment(fragment);
        }

        return true;
    }

    public void goToCategory(View view)
    {
        CategoryData selectedCategory = new CategoryData();
        int viewId = view.getId();

        switch (viewId) {
            case R.id.button_tops:
                selectedCategory = categoriesData.get(0);
                break;
            case R.id.button_pants:
                selectedCategory = categoriesData.get(1);
                break;
            case R.id.button_skirts:
                //selectedCategory = categoriesData.get(2);
                break;
            default:
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY, selectedCategory);
        SingleCategoryFragment singleCategoryFragment = new SingleCategoryFragment();
        singleCategoryFragment.setArguments(bundle);

        loadFragment(singleCategoryFragment);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void payBill(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to continue to payment?")
                .setTitle("PAYMENT")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomeActivity.this, BillActivity.class);
                        startActivity(intent);
                    }
                })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}