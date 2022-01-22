package com.example.androidapp_citb704_F102014;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp_citb704_F102014.Data.ItemData;
import com.example.androidapp_citb704_F102014.Fragments.CartFragment;
import com.example.androidapp_citb704_F102014.Helpers.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<ItemData> mDataSet;
    private int viewType = 0;

    //viewType 0 -> category list cell
    //viewType 1 -> favorites cell
    //viewType 2 -> cart cell
    public ItemsAdapter(List<ItemData> data, int viewType) {
        this.viewType = viewType;
        mDataSet = new ArrayList<>();

        if (data != null) {
            mDataSet = data;
        }
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        viewType = this.viewType;
        if (viewType == 0) {
            //items in category view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
            return new ItemsAdapter.ViewHolder(v, viewType);
        } else {
            //items in favorites and cart
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell_long, parent, false);
            return new ItemsAdapter.ViewHolder(v, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        //set price
        holder.getPriceTextView().setText(mDataSet.get(position).getPriceFormattedString());

        //set image
        HomeActivity.bitmapCacheHelper.getBitmapFromMemCache(mDataSet.get(position).getPhotoURL(),
                holder.getImageView());
        holder.setItemData(mDataSet.get(position));

        //set name when in favorites and cart
        if (holder.getNameTextView() != null) {
            holder.getNameTextView().setText(mDataSet.get(position).getName());
        }

        DataBaseHelper dbHelper = new DataBaseHelper(holder.getImageView().getContext());

        if (holder.getImgButtonFav() != null) {

            if (dbHelper.hasItemInFav(holder.itemData)) {
                holder.getImgButtonFav().setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                holder.getImgButtonFav().setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        }

        if (holder.getImgButtonCart() != null)
        {
            if (dbHelper.hasItemInCart(holder.itemData)) {
                holder.getImgButtonCart().setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
            } else {
                holder.getImgButtonCart().setImageResource(R.drawable.ic_baseline_shopping_cart_24);
            }
        }

        dbHelper.close();
    }

    public void removeAt(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataSet.size());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemData itemData = new ItemData();

        private final TextView priceTextView;
        private final ImageView imageView;

        private final ImageButton imgButtonFav;
        private final ImageButton imgButtonCart;

        //long cells
        private TextView nameTextView;

        public ViewHolder(View v, int viewType) {
            super(v);

            priceTextView = (TextView) v.findViewById(R.id.item_price);
            imageView = (ImageView) v.findViewById(R.id.item_image);

            nameTextView = null;
            if ((TextView) v.findViewById(R.id.item_name) != null) {
                nameTextView = (TextView) v.findViewById(R.id.item_name);
            }

            DataBaseHelper dbHelper = new DataBaseHelper(v.getContext());

            imgButtonFav = (ImageButton) v.findViewById(R.id.item_btn_fav);
            if (imgButtonFav != null) {

                imgButtonFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemData != null) {

                            if (dbHelper.hasItemInFav(itemData)) {
                                dbHelper.removeFromFavoritesDB(itemData);
                                imgButtonFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            } else {
                                dbHelper.addToFavoritesDB(itemData);
                                imgButtonFav.setImageResource(R.drawable.ic_baseline_favorite_24);
                            }
                        }
                    }
                });
            }

            imgButtonCart = (ImageButton) v.findViewById(R.id.item_btn_cart);
            if (imgButtonCart != null) {

                imgButtonCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemData != null) {
                            if (dbHelper.hasItemInCart(itemData)) {
                                dbHelper.removeFromCartDB(itemData);
                                imgButtonCart.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
                            } else {
                                dbHelper.addToCartDB(itemData);
                                imgButtonCart.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
                            }
                        }
                    }
                });
            }

            dbHelper.close();

            if ((ImageButton) v.findViewById(R.id.item_btn_remove) != null) {
                v.findViewById(R.id.item_btn_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemData != null) {
                            removeAt(getAdapterPosition());
                            if (viewType == 1) {
                                dbHelper.removeFromFavoritesDB(itemData);
                            } else if (viewType == 2) {
                                dbHelper.removeFromCartDB(itemData);
                                CartFragment.RemoveFromCart(itemData);
                            }
                            dbHelper.close();
                        }
                    }
                });
            }
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public ImageButton getImgButtonFav(){
            return imgButtonFav;
        }

        public ImageButton getImgButtonCart(){
            return imgButtonCart;
        }

        public void setItemData(ItemData itemData) {
            this.itemData = itemData;
        }
    }
}
