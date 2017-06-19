package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.svenwesterlaken.rentamovie.domain.Customer;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import io.paperdb.Paper;


public class RegisterRequest implements Response.Listener<JSONObject>, Response.ErrorListener{
    private Context context;
    private RegisterRequestListener listener;
    public final String TAG = this.getClass().getSimpleName();

    public RegisterRequest(Context context, RegisterRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleRegister(JSONObject body) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.URL_REGISTER, body, this, this);
        request.setRetryPolicy(new DefaultRetryPolicy(1500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.toString());
        listener.onRegisterErrors("Something went wrong");
    }

    @Override
    public void onResponse(JSONObject response) {
        JsonObject result = (JsonObject) new JsonParser().parse(response.toString());
        Customer customer = new Gson().fromJson(result, Customer.class);
        Paper.book().write("user", customer);
        listener.onRegisterApproved();
    }


    public interface RegisterRequestListener {
            void onRegisterApproved();
            void onRegisterErrors(String message);
        }
    }

