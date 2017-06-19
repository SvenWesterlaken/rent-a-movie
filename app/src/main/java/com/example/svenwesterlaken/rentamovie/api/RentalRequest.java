package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.svenwesterlaken.rentamovie.domain.Rental;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RentalRequest implements Response.Listener<JSONArray>, Response.ErrorListener{
    private Context context;
    private RentalRequestListener listener;
    public final String TAG = this.getClass().getSimpleName();

    public RentalRequest(Context context, RentalRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetAllRentals() {
        String url = Config.URL_RENTAL + LoginUtil.getUserID();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, this, this);
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.toString());
        listener.onRentalsErrors("Something went wrong");
    }

    @Override
    public void onResponse(JSONArray response) {
        JsonArray result = (JsonArray) new JsonParser().parse(response.toString());
        List<Rental> rentals = new ArrayList<>(Arrays.asList(new Gson().fromJson(result, Rental[].class)));
        listener.onRentalsAvailable(rentals);
    }


    public interface RentalRequestListener {
            void onRentalsAvailable(List<Rental> rentals);
            void onRentalsErrors(String message);
        }
    }

