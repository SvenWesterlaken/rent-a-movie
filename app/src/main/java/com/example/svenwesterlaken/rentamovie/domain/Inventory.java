package com.example.svenwesterlaken.rentamovie.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dajakah on 19-6-2017.
 */

public class Inventory implements Parcelable {
    @SerializedName("inventory_id")
    private int inventoryID;
    @SerializedName("film_id")
    private int filmID;
    @SerializedName("store_id")
    private int StoreID;

    public Inventory(int inventoryID, int filmID, int storeID) {
        this.inventoryID = inventoryID;
        this.filmID = filmID;
        StoreID = storeID;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }


    protected Inventory(Parcel in) {
        inventoryID = in.readInt();
        filmID = in.readInt();
        StoreID = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(inventoryID);
        dest.writeInt(filmID);
        dest.writeInt(StoreID);
    }

    public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel in) {

            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };

}
