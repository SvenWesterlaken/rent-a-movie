package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Dajakah on 19-6-2017.
 */

public class RentalAddRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private RentalAddListener listener;
    public final String TAG = this.getClass().getSimpleName();

    public RentalAddRequest(Context context, RentalAddListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleAddRental(int userid, int inventoryid) {
        String url = Config.URL_RENTAL + userid + "/" + inventoryid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return LoginUtil.getAuthHeaders();
            }
        };;
        VolleyRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.toString());
        listener.onErrors("Something went wrong");

    }

    @Override
    public void onResponse(JSONObject response) {
        listener.onRentalAdded();
    }

    public interface RentalAddListener {
        void onRentalAdded();
        void onErrors(String message);

    }
}
