package com.example.svenwesterlaken.rentamovie.api;

import android.util.Log;
import com.example.svenwesterlaken.rentamovie.domain.Movies;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dajakah on 17-6-2017.
 */

class MoviesMapper {
    public static final String FILM_ID = "film_id";
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    public static final String FILM_RELEASE_YEAR = "release_year";
    public static final String FILM_LANGUAGE_ID = "language_id";
    public static final String FILM_ORIGINAL_LANGUAGE_ID = "original_language_id";
    public static final String FILM_RENTAL_DURATION = "rental_duration";
    public static final String FILM_RENTAL_RATE = "rental_rate";
    public static final String FILM_LENGTH = "length";
    public static final String FILM_REPLACEMENT_COST = "replacement_cost";
    public static final String FILM_RATING = "rating";
    public static final String FILM_SPECIAL_FEATURES = "special_features";
    public static final String FILM_LAST_UPDATE = "last_update";

    public static ArrayList<Movies> mapMovieList(JSONArray response) {
        ArrayList<Movies> result = new ArrayList<>();

        try {

            for (int i = 0; i < response.length(); i++) {
                JSONObject object = response.getJSONObject(i);

                int id = object.getInt(FILM_ID);
                String title = object.getString(FILM_TITLE);
                String description = object.getString(FILM_DESCRIPTION);
                int releaseYear = object.getInt(FILM_RELEASE_YEAR);
                int languageID = object.getInt(FILM_LANGUAGE_ID);
                int originalLanguageID = 0;
                if (object.isNull(FILM_ORIGINAL_LANGUAGE_ID)) {
                    originalLanguageID = 0;
                } else {
                    originalLanguageID = object.getInt(FILM_ORIGINAL_LANGUAGE_ID);
                }
                int rentalDuration = object.getInt(FILM_RENTAL_DURATION);
                double rentalRate = object.getDouble(FILM_RENTAL_RATE);
                int length = object.getInt(FILM_LENGTH);
                double replacementCost = object.getDouble(FILM_REPLACEMENT_COST);
                String rating = object.getString(FILM_RATING);
                String specialFeatures = object.getString(FILM_SPECIAL_FEATURES);
                String lastUpdate = object.getString(FILM_LAST_UPDATE);

                Log.i("LastUpdate", lastUpdate);

                Movies movie = new Movies(id, title, description, releaseYear, languageID, originalLanguageID, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures, lastUpdate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
