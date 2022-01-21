package com.example.androidapp_citb704_F102014.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryData implements Parcelable {

    String name = "";
    ArrayList<ItemData> items = new ArrayList<>();

    public String getName() {return name;}
    public ArrayList<ItemData> getItems() {return items;}

    public CategoryData() {}

    public CategoryData(JSONObject json) {
        name = json.keys().next();

        try {
            JSONArray itemsRawData = json.getJSONArray(name);

            items = new ArrayList<>();
            for (int i = 0; i < itemsRawData.length(); i++){
                JSONObject itemRawData = itemsRawData.getJSONObject(i);
                items.add( new ItemData(itemRawData));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected CategoryData(Parcel in) {
        name = in.readString();
        in.readTypedList(items, ItemData.CREATOR);
    }

    public static final Creator<CategoryData> CREATOR = new Creator<CategoryData>() {
        @Override
        public CategoryData createFromParcel(Parcel in) {
            return new CategoryData(in);
        }

        @Override
        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(items);
    }
}
