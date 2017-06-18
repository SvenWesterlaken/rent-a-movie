package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.svenwesterlaken.rentamovie.domain.Movie;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.TokenUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MoviesRequest implements Response.Listener<JSONArray>, Response.ErrorListener{
    private Context context;
    private MoviesRequestListener listener;
    public final String TAG = this.getClass().getSimpleName();
    public TokenUtil util = new TokenUtil();

    public MoviesRequest(Context context, MoviesRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetAllMovies() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Config.URL_FILMS, null, this, this);
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.toString());
        listener.onMovieErrors("Something went wrong");
    }

    @Override
    public void onResponse(JSONArray response) {
        JsonArray result = (JsonArray) new JsonParser().parse(response.toString());
        List<Movie> movies = new ArrayList<>(Arrays.asList(new Gson().fromJson(result, Movie[].class)));
        listener.onMoviesAvailable(movies);
    }


    public interface MoviesRequestListener {
            void onMoviesAvailable(List<Movie> movies);
            void onMovieErrors(String message);
        }
    }

