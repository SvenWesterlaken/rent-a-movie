package com.example.svenwesterlaken.rentamovie.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sven Westerlaken on 13-6-2017.
 * Worked on by Devon Marsham 16-6-2017.
 */

public class Movie implements Parcelable {

    @SerializedName("film_id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("release_year")
    private String releaseYear;
    @SerializedName("rental_rate")
    private int rentalRate;
    @SerializedName("length")
    private int length;
    @SerializedName("replacement_cost")
    private double replacementCost;
    @SerializedName("rating")
    private String rating;
    @SerializedName("special_features")
    private String specialFeatures;


    public Movie(int id, String title, String description, String releaseYear, int rentalRate, int length, double replacementCost, String rating, String specialFeatures) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(int rentalRate) {
        this.rentalRate = rentalRate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(double replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.releaseYear);
        dest.writeInt(this.rentalRate);
        dest.writeInt(this.length);
        dest.writeDouble(this.replacementCost);
        dest.writeString(this.rating);
        dest.writeString(this.specialFeatures);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.releaseYear = in.readString();
        this.rentalRate = in.readInt();
        this.length = in.readInt();
        this.replacementCost = in.readDouble();
        this.rating = in.readString();
        this.specialFeatures = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
