package com.example.androidapp_citb704_F102014.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ItemData implements Parcelable {
    private String id;
    private String name;
    private double price;
    private ArrayList<String> colors = new ArrayList<>();
    private String photoURl;

    protected ItemData(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readDouble();
        colors = in.createStringArrayList();
        photoURl = in.readString();
    }

    public static final Creator<ItemData> CREATOR = new Creator<ItemData>() {
        @Override
        public ItemData createFromParcel(Parcel in) {
            return new ItemData(in);
        }

        @Override
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };

    public String getId() {return id; }
    public String getName()
    {
        return name;
    }
    public String getPhotoURL() { return photoURl; }

    public String getPriceFormattedString(){
        return NumberFormat
        .getCurrencyInstance()
        .format(price);
    }

    public String getPriceString() {return String.valueOf(price);}
    public double getPriceDouble() {return price;};

    public String getColors(){
        String toReturn = "";
        for (int i = 0 ; i < colors.size(); i++) {
            toReturn += colors.get(i);

            if ( i < colors.size()-1){
                toReturn = toReturn+',';
            }
        }
        return toReturn;
    }

    public ItemData() { }

    public ItemData(String id, String name, String price, String[] colors, String photoUrl)
    {
        this.id = id;
        this.name = name;
        this.price = Double.parseDouble(price);
        this.photoURl = photoUrl;

        for (int i = 0; i < colors.length; i++)
        {
            this.colors.add(colors[i]);
        }
    }

    public ItemData(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.price = jsonObject.getDouble("price");
        this.photoURl = jsonObject.getString("photo");

        JSONArray colorsRaw = jsonObject.getJSONArray("colors");
        for (int i = 0; i < colorsRaw.length(); i++){
            this.colors.add(String.valueOf(colorsRaw.get(i)));
        }
    }

    @Override
    public  boolean equals(Object obj){
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ItemData)) {
            return false;
        }

        ItemData item = (ItemData) obj;

        return this.id == item.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeStringList(colors);
        parcel.writeString(photoURl);
    }
}
