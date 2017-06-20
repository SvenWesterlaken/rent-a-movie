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
 * Created by Sven Westerlaken on 19-6-2017.
 */

public class RentalExtendRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private RentalExtendListener listener;

    public RentalExtendRequest(Context context, RentalExtendListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleExtendRental(int userid, int inventoryid) {
        String url = Config.URL_RENTAL + userid + "/" + inventoryid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return LoginUtil.getAuthHeaders();
            }
        };;
        VolleyRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        listener.onErrors("Something went wrong");
    }

    @Override
    public void onResponse(JSONObject response) {
        listener.onRentalExtended();
    }

    public interface RentalExtendListener {
        void onRentalExtended();
        void onErrors(String message);

    }
}
