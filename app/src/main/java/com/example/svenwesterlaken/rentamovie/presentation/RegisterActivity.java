package com.example.svenwesterlaken.rentamovie.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.RegisterRequest;
import com.example.svenwesterlaken.rentamovie.util.Message;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterRequest.RegisterRequestListener {
    private EditText emailET;
    private EditText passwordET;
    private EditText firstnameET;
    private EditText lastnameET;
    private Button registerBTN;

    private String email;
    private String password;
    private String firstname;
    private String lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailET = (EditText) findViewById(R.id.register_ET_email);
        passwordET = (EditText) findViewById(R.id.register_ET_password);
        firstnameET = (EditText) findViewById(R.id.register_ET_firstname);
        lastnameET = (EditText) findViewById(R.id.register_ET_lastname);

        registerBTN = (Button) findViewById(R.id.register_BTN_register);
        registerBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.register_BTN_register) {
            email = emailET.getText().toString();
            password = passwordET.getText().toString();
            firstname = firstnameET.getText().toString();
            lastname = lastnameET.getText().toString();

            if(!email.isEmpty() && !password.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty()) {
                handleRegister(email, password, firstname, lastname);
            }
        }
    }

    public void handleRegister(String email, String password, String firstname, String lastname) {
        registerBTN.setEnabled(false);
        String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"firstname\":\"" + firstname + "\",\"lastname\":\"" + lastname + "\"}";

        try {
            JSONObject jsonBody = new JSONObject(body);
            RegisterRequest request = new RegisterRequest(getApplicationContext(), this);
            request.handleRegister(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRegisterApproved() {
        Intent intent = new Intent();
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRegisterErrors(String message) {
        registerBTN.setEnabled(true);
        Message.display(getApplicationContext(), message);
    }
}
