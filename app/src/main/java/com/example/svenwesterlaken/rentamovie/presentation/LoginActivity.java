package com.example.svenwesterlaken.rentamovie.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.VolleyRequestQueue;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.Message;
import com.example.svenwesterlaken.rentamovie.util.TokenUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String Emailtext;
    private String Passwordtext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (TokenUtil.tokenAvailable(getApplicationContext())) {
            if (TokenUtil.tokenValid(getApplicationContext())) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }

        editTextEmail = (EditText) findViewById(R.id.login_ET_email);
        editTextPassword = (EditText) findViewById(R.id.login_ET_password);
        Button btnLogin = (Button) findViewById(R.id.login_BTN_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emailtext = editTextEmail.getText().toString();
                Passwordtext = editTextPassword.getText().toString();

                if (!Emailtext.isEmpty() || !Passwordtext.isEmpty()) {
                    handleLogin(Emailtext, Passwordtext);
                }
            }
        });
    }

    private void handleLogin(String email, String password) {
        String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        Log.i("handleLogin ", body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String token = response.getString("token");
//                                long expirationTime = response.getLong("expire");
//                                String userMail = response.getString("mail");
                        if (token == null) {
                            throw new Exception("Token not found.");
                        }

//                                if (Long.valueOf(expirationTime) == null) {
//                                    throw new Exception("Expiration not found");
//                                }

//                                if (String.valueOf(userMail) == null) {
//                                    throw new Exception("User Mail not found");
//                                }
                        TokenUtil.saveToken(getApplicationContext(), token);
                        Message.display(getApplicationContext(), "Met succes ingelogd.");
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(main);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Message.error(getApplicationContext(), error);
                }
            });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(1500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
