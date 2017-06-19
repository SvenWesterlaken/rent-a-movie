package com.example.svenwesterlaken.rentamovie.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.LoginRequest;
import com.example.svenwesterlaken.rentamovie.util.Config;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;
import com.example.svenwesterlaken.rentamovie.util.Message;

import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginRequest.LoginRequestListener {
    private EditText emailET;
    private EditText passwordET;
    private Button loginBTN;
    private Button registerBTN;
    private Button guestBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(getApplicationContext());
        if(LoginUtil.isSuccesfullyLoggedIn()) {
            onLoginApproved();
        } else {
            setContentView(R.layout.activity_login);

            emailET = (EditText) findViewById(R.id.login_ET_email);
            passwordET = (EditText) findViewById(R.id.login_ET_password);
            loginBTN = (Button) findViewById(R.id.login_BTN_login);
            registerBTN = (Button) findViewById(R.id.login_BTN_register);
            guestBTN = (Button) findViewById(R.id.login_BTN_guest);

            loginBTN.setOnClickListener(this);
            registerBTN.setOnClickListener(this);
            guestBTN.setOnClickListener(this);
        }
    }

    private void handleLogin(String email, String password) {
        switchButtons(false);
        String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

        try {
            JSONObject jsonBody = new JSONObject(body);
            LoginRequest request = new LoginRequest(getApplicationContext(), this);
            request.handleLogin(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_BTN_login) {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();

            if (!email.isEmpty() || !password.isEmpty()) {
                handleLogin(email, password);
            }
        } else if (v.getId() == R.id.login_BTN_register) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivityForResult(i, Config.REGISTER_REQUEST);
        } else if (v.getId() == R.id.login_BTN_guest) {
            onLoginApproved();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.REGISTER_REQUEST && resultCode == RESULT_OK) {
            String password = data.getExtras().getString("password");
            String email = data.getExtras().getString("email");

            if(password != null && email != null) {
                handleLogin(email, password);
            }

        }
    }

    private void switchButtons(boolean value) {
        loginBTN.setEnabled(value);
        registerBTN.setEnabled(value);
        guestBTN.setEnabled(value);
    }

    @Override
    public void onLoginApproved() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onLoginErrors(String message) {
        Message.display(this, message);
        switchButtons(true);
    }
}
