package com.example.svenwesterlaken.rentamovie.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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


public class LoginRequest implements Response.Listener<JSONObject>, Response.ErrorListener{
    private Context context;
    private LoginRequestListener listener;
    public final String TAG = this.getClass().getSimpleName();

    public LoginRequest(Context context, LoginRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleLogin(JSONObject body) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.URL_LOGIN, body, this, this);
        request.setRetryPolicy(new DefaultRetryPolicy(1500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        NetworkResponse nr = error.networkResponse;

        if(nr.statusCode == 401) {
            listener.onLoginErrors("Wrong credentials");
        } else {
            listener.onLoginErrors("Something went wrong");
        }


    }

    @Override
    public void onResponse(JSONObject response) {
        JsonObject result = (JsonObject) new JsonParser().parse(response.toString());
        Customer customer = new Gson().fromJson(result, Customer.class);
        Paper.book().write("user", customer);
        listener.onLoginApproved();
    }


    public interface LoginRequestListener {
            void onLoginApproved();
            void onLoginErrors(String message);
        }
    }

