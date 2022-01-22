package com.example.androidapp_citb704_F102014.Helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidapp_citb704_F102014.Data.ItemData;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public final static String TABLE_NAME_FAVORITES = "favorites";
    public final static String TABLE_NAME_CART = "cart";

    public final static String IID = "_ID";
    public final static String COLUMN_ITEM_ID = "id";
    public final static String COLUMN_ITEM_NAME = "name";
    public final static String COLUMN_ITEM_PRICE = "price";
    public final static String COLUMN_ITEM_COLORS = "colors";
    public final static String COLUMN_ITEM_PHOTO_URL = "photoUrl";

    private static ArrayList<ItemData> cachedFav = new ArrayList<>();
    private static ArrayList<ItemData> cachedCart = new ArrayList<>();

    public ArrayList<ItemData> getItemsInFavDB() {return cachedFav;}
    public ArrayList<ItemData> getItemsInCartDB() {return cachedCart;}

    public boolean hasItemInFav(ItemData itemData){

        for (ItemData addedData : cachedFav
        ) {
            if (addedData.getId().equalsIgnoreCase(itemData.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItemInCart(ItemData itemData){
        for (ItemData addedData : cachedCart
        ) {
            if (addedData.getId().equals(itemData.getId())) {
                return true;
            }
        }
        return false;
    }

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE "
                + TABLE_NAME_FAVORITES + " ("
                + IID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEM_ID + " TEXT NOT NULL, "
                + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + COLUMN_ITEM_PRICE + " TEXT NOT NULL, "
                + COLUMN_ITEM_COLORS + " TEXT NOT NULL, "
                + COLUMN_ITEM_PHOTO_URL + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);

        SQL_CREATE_ITEMS_TABLE = "CREATE TABLE "
                + TABLE_NAME_CART + " ("
                + IID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEM_ID + " TEXT NOT NULL, "
                + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + COLUMN_ITEM_PRICE + " TEXT NOT NULL, "
                + COLUMN_ITEM_COLORS + " TEXT NOT NULL, "
                + COLUMN_ITEM_PHOTO_URL + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAVORITES);
        onCreate(sqLiteDatabase);

    }

    public boolean addToFavoritesDB(ItemData itemData) {
        if (hasItemInFav(itemData)) return false;
        cachedFav.add(itemData);

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_ID, itemData.getId());
        values.put(COLUMN_ITEM_NAME, itemData.getName());
        values.put(COLUMN_ITEM_PRICE, itemData.getPriceString());
        values.put(COLUMN_ITEM_COLORS, itemData.getColors());
        values.put(COLUMN_ITEM_PHOTO_URL, itemData.getPhotoURL());
        database.insert(TABLE_NAME_FAVORITES, null, values);

        database.close();

        return true;
    }

    public void removeFromFavoritesDB(ItemData itemData) {
        for (ItemData addedData : cachedFav
        ) {
            if (addedData.getId().equals(itemData.getId())) {
                cachedFav.remove(addedData);
                break;
            }
        }

        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_NAME_FAVORITES, COLUMN_ITEM_ID + "=" + itemData.getId() , null);
        database.close();
    }

    public boolean addToCartDB(ItemData itemData) {
        if (hasItemInCart(itemData)) return false;
        cachedCart.add(itemData);

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_ID, itemData.getId());
        values.put(COLUMN_ITEM_NAME, itemData.getName());
        values.put(COLUMN_ITEM_PRICE, itemData.getPriceString());
        values.put(COLUMN_ITEM_COLORS, itemData.getColors());
        values.put(COLUMN_ITEM_PHOTO_URL, itemData.getPhotoURL());

        database.insert(TABLE_NAME_CART, null, values);

        database.close();
        return true;
    }

    public void removeFromCartDB(ItemData itemData) {

        for (ItemData addedData : cachedCart
        ) {
            if (addedData.getId().equals(itemData.getId())) {
                cachedCart.remove(addedData);
                break;
            }
        }

        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_NAME_CART, COLUMN_ITEM_ID + "=" + itemData.getId() , null);
        database.close();
    }

    public void readFavoritesDB() {
        ArrayList<ItemData> itemsData = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String query = " SELECT " + IID + " ,  "
                + COLUMN_ITEM_ID + " ,  "
                + COLUMN_ITEM_NAME + " ,  "
                + COLUMN_ITEM_PRICE + " ,  "
                + COLUMN_ITEM_COLORS + " ,  "
                + COLUMN_ITEM_PHOTO_URL +
                " FROM " + TABLE_NAME_FAVORITES;

        Cursor cursor2 = database.rawQuery(query, null);

        while (cursor2.moveToNext()) {
            // получаване на индексите и стойностите на колоните
            @SuppressLint("Range") int id = cursor2.getInt(cursor2.getColumnIndex(IID));
            @SuppressLint("Range") String myId = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_ID));
            @SuppressLint("Range") String name = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_NAME));
            @SuppressLint("Range") String price = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_PRICE));
            @SuppressLint("Range") String colors = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_COLORS));
            @SuppressLint("Range") String photoUrl = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_PHOTO_URL));

            itemsData.add(new ItemData(myId, name, price, colors.split("-"), photoUrl));
        }

        cursor2.close();
        database.close();

        cachedFav = itemsData;
    }

    public void readCartDB() {
        ArrayList<ItemData> itemsData = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        String query = " SELECT " + IID + " ,  "
                + COLUMN_ITEM_ID + " ,  "
                + COLUMN_ITEM_NAME + " ,  "
                + COLUMN_ITEM_PRICE + " ,  "
                + COLUMN_ITEM_COLORS + " ,  "
                + COLUMN_ITEM_PHOTO_URL +
                " FROM " + TABLE_NAME_CART;

        Cursor cursor2 = database.rawQuery(query, null);

        while (cursor2.moveToNext()) {
            // получаване на индексите и стойностите на колоните
            @SuppressLint("Range") int id = cursor2.getInt(cursor2.getColumnIndex(IID));
            @SuppressLint("Range") String myId = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_ID));
            @SuppressLint("Range") String name = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_NAME));
            @SuppressLint("Range") String price = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_PRICE));
            @SuppressLint("Range") String colors = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_COLORS));
            @SuppressLint("Range") String photoUrl = cursor2.getString(cursor2.getColumnIndex(COLUMN_ITEM_PHOTO_URL));

            itemsData.add(new ItemData(myId, name, price, colors.split("-"), photoUrl));
        }

        cursor2.close();
        database.close();

        cachedCart = itemsData;
    }
}
