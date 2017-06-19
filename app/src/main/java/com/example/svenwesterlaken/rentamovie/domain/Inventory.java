package com.example.svenwesterlaken.rentamovie.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Dajakah on 19-6-2017.
 */

public class Inventory implements Parcelable {
    @SerializedName("film_id")
    private int filmID;
    @SerializedName("title")
    private String title;
    @SerializedName("inventory_id")
    private int id;
    @SerializedName("rental_id")
    private int rentalID;
    @SerializedName("rental_date")
    private String rentalDateAPI;
    private Date rentalDate;
    @SerializedName("return_date")
    private String returnDateAPI;
    private Date returnDate;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("customer_id")
    private int customerID;
    @SerializedName("available")
    private String available;

    public Inventory(int filmID, String title, int rentalID, int id, String rentalDateAPI, Date rentalDate, String returnDateAPI, Date returnDate, String firstName, String lastName, int customerID) {
        this.filmID = filmID;
        this.title = title;
        this.rentalID = rentalID;
        this.id = id;
        this.rentalDateAPI = rentalDateAPI;
        this.rentalDate = rentalDate;
        this.returnDateAPI = returnDateAPI;
        this.returnDate = returnDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerID = customerID;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getrentalID() {
        return rentalID;
    }

    public void setrentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRentalDateAPI() {
        return rentalDateAPI;
    }

    public void setRentalDateAPI(String rentalDateAPI) {
        this.rentalDateAPI = rentalDateAPI;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDateAPI() {
        return returnDateAPI;
    }

    public void setReturnDateAPI(String returnDateAPI) {
        this.returnDateAPI = returnDateAPI;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    private String getAvailable() {
        return available;
    }

    public void setIsAvailable(String available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return getAvailable() == null || Integer.parseInt(getAvailable()) == 1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.filmID);
        dest.writeString(this.title);
        dest.writeInt(this.rentalID);
        dest.writeInt(this.id);
        dest.writeString(this.rentalDateAPI);
        dest.writeLong(this.rentalDate != null ? this.rentalDate.getTime() : -1);
        dest.writeString(this.returnDateAPI);
        dest.writeLong(this.returnDate != null ? this.returnDate.getTime() : -1);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeInt(this.customerID);
        dest.writeString(this.available);
    }

    protected Inventory(Parcel in) {
        this.filmID = in.readInt();
        this.title = in.readString();
        this.rentalID = in.readInt();
        this.id = in.readInt();
        this.rentalDateAPI = in.readString();
        long tmpRentalDate = in.readLong();
        this.rentalDate = tmpRentalDate == -1 ? null : new Date(tmpRentalDate);
        this.returnDateAPI = in.readString();
        long tmpReturnDate = in.readLong();
        this.returnDate = tmpReturnDate == -1 ? null : new Date(tmpReturnDate);
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.customerID = in.readInt();
        this.available = in.readString();
    }

    public static final Parcelable.Creator<Inventory> CREATOR = new Parcelable.Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel source) {
            return new Inventory(source);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };
}
