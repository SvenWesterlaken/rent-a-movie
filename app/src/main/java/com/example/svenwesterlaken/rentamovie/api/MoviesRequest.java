package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Movies;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.TokenUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dajakah on 17-6-2017.
 */

public class MoviesRequest {
    private Context context;
    private MoviesRequestListener listener;
    public final String TAG = this.getClass().getSimpleName();
    public TokenUtil util = new TokenUtil();

    public MoviesRequest(Context context, MoviesRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetAllMovies() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(
                context.getString(R.string.saved_token), "default token");
        if (token != null && !token.equals("default token")) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Config.URL_FILMS,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ArrayList<Movies> result = MoviesMapper.mapMovieList(response);
                            listener.onMoviesAvailable(result);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null) {
                                Log.e(TAG, error.toString());
                            }
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("W-Access-Token", token);
                    return headers;
                }
            };
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonArrayRequest);
        }
    }


        public interface MoviesRequestListener {
            void onMoviesAvailable(ArrayList<Movies> moviesArrayList);

            void onMovieAvailable(Movies movie);

            void onMovieErrors(String message);
        }
    }

