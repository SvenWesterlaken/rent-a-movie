package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.svenwesterlaken.rentamovie.domain.Inventory;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Dajakah on 19-6-2017.
 */

public class InventoryRequest implements Response.Listener<JSONArray>, Response.ErrorListener {
    private Context context;
    private InventoryRequestListener listener;
    public final String TAG = this.getClass().getSimpleName();

    public InventoryRequest(Context context, InventoryRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetAllInventories(int id) {
        String url = Config.URL_INVENTORY + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return LoginUtil.getAuthHeaders();
            }
        };;
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.toString());
        listener.onInventoryErrors("Something went wrong");

    }

    @Override
    public void onResponse(JSONArray response) {
        JsonArray result = (JsonArray) new JsonParser().parse(response.toString());
        List<Inventory> inventories = new ArrayList<>(Arrays.asList(new Gson().fromJson(result, Inventory[].class)));
        listener.onInventoriesAvailable(inventories);

    }

    public interface InventoryRequestListener {
        void onInventoriesAvailable(List<Inventory> inventories);
        void onInventoryErrors(String message);

    }
}
