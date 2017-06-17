package com.example.svenwesterlaken.rentamovie.domain;

import java.io.Serializable;

/**
 * Created by Sven Westerlaken on 13-6-2017.
 * Worked on by Devon Marsham 16-6-2017.
 */

public class Movie implements Serializable{
    private int film_id;
    private String title;
    private String description;
    private String release_year;
    private int Language_id;
    private int Original_Language_id;
    private int rental_duration;
    private int rental_rate;
    private int length;
    private double replacement_cost;
    private String rating;
    private String special_features;
    private String last_update;

    public Movie(int film_id, int rental_duration, int rental_rate, int length, int language_id,
                 int original_Language_id, String title, String description, String release_year,
                 String rating, String special_features, String last_update, double replacement_cost){
        this.film_id = film_id;
        this.title = title;
        this.description = description;
        this.release_year = release_year;
        this.Language_id = language_id;
        this.Original_Language_id = original_Language_id;
        this.rental_duration = rental_duration;
        this.rental_rate = rental_rate;
        this.length = length;
        this.replacement_cost = replacement_cost;
        this.rating = rating;
        this.special_features = special_features;
        this.last_update = last_update;
    }


    //================================================================================
    // Accessors
    //================================================================================
    public int getFilm_id() {
        return film_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRelease_year() {
        return release_year;
    }

    public int getLanguage_id() {
        return Language_id;
    }

    public int getOriginal_Language_id() {
        return Original_Language_id;
    }

    public int getRental_duration() {
        return rental_duration;
    }

    public int getRental_rate() {
        return rental_rate;
    }

    public int getLength() {
        return length;
    }

    public double getReplacement_cost() {
        return replacement_cost;
    }

    public String getRating() {
        return rating;
    }

    public String getSpecial_features() {
        return special_features;
    }

    public String getLast_update() {
        return last_update;
    }


    //================================================================================
    // Mutators
    //================================================================================

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public void setLanguage_id(int language_id) {
        Language_id = language_id;
    }

    public void setOriginal_Language_id(int original_Language_id) {
        Original_Language_id = original_Language_id;
    }

    public void setRental_duration(int rental_duration) {
        this.rental_duration = rental_duration;
    }

    public void setRental_rate(int rental_rate) {
        this.rental_rate = rental_rate;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setReplacement_cost(double replacement_cost) {
        this.replacement_cost = replacement_cost;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setSpecial_features(String special_features) {
        this.special_features = special_features;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
