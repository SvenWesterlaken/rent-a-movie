package com.example.svenwesterlaken.rentamovie.util;

/**
 * Created by Dajakah on 17-6-2017.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class Message {

    public static void display(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void error(Context context, VolleyError error) {
        if (error instanceof AuthFailureError) {
            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null) {
                    json = "Error " + response.statusCode + ": " + json;
                    display(context, json);
                }
            } else {
                display(context, "Internal server error.");
            }
        } else if (error instanceof NoConnectionError) {
            display(context, "No network available.");
        }
    }

    private static String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return trimmedString;
    }
}
