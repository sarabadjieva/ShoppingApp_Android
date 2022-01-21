package com.example.androidapp_citb704_F102014;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp_citb704_F102014.Data.ItemData;
import com.example.androidapp_citb704_F102014.Helpers.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    private List<ItemData> mDataSet;
    private int viewType = 0;

    //viewType 0 -> category list cell
    //viewType 1 -> favorites cell
    //viewType 2 -> cart cell
    public ItemsAdapter(List<ItemData> data, int viewType)
    {
        this.viewType = viewType;
        mDataSet = new ArrayList<>();

        if (data != null)
        {
            mDataSet = data;
        }
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        viewType = this.viewType;
        if (viewType == 0)
        {
            //items in category view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
            return new ItemsAdapter.ViewHolder(v, viewType);
        }
        else
        {
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
        if (holder.getNameTextView() != null)
        {
            holder.getNameTextView().setText(mDataSet.get(position).getName());
        }
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

        //long cells
        private TextView nameTextView;

        public ViewHolder(View v, int viewType) {
            super(v);

            priceTextView = (TextView) v.findViewById(R.id.item_price);
            imageView = (ImageView) v.findViewById(R.id.item_image);

            nameTextView = null;
            if ((TextView) v.findViewById(R.id.item_name) != null)
            {
                nameTextView = (TextView) v.findViewById(R.id.item_name);
            }

            if ((ImageButton) v.findViewById(R.id.item_btn_fav) != null){
                v.findViewById(R.id.item_btn_fav).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemData != null)
                        {
                            DataBaseHelper dbHelper = new DataBaseHelper(v.getContext());
                            dbHelper.addToFavoritesDB(itemData);
                            dbHelper.close();
                        }
                    }
                });
            }

            if ((ImageButton) v.findViewById(R.id.item_btn_cart) != null){
                v.findViewById(R.id.item_btn_cart).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemData != null)
                        {
                            DataBaseHelper dbHelper = new DataBaseHelper(v.getContext());
                            dbHelper.addToCartDB(itemData);
                            dbHelper.close();
                        }
                    }
                });
            }

            if ((ImageButton) v.findViewById(R.id.item_btn_remove) != null){
                v.findViewById(R.id.item_btn_remove).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemData != null)
                        {
                            removeAt(getAdapterPosition());
                            DataBaseHelper dbHelper = new DataBaseHelper(v.getContext());
                            if (viewType == 1){
                                dbHelper.removeFromFavoritesDB(itemData);
                            }
                            else if (viewType == 2) {
                                dbHelper.removeFromCartDB(itemData);
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
        public ImageView getImageView() { return imageView; }
        public TextView getNameTextView() {return nameTextView;}
        public void setItemData(ItemData itemData) {this.itemData = itemData;}
    }
}
