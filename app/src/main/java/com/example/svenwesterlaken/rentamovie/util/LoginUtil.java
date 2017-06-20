package com.example.svenwesterlaken.rentamovie.util;

import android.util.Log;

import com.example.svenwesterlaken.rentamovie.domain.Customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.paperdb.Paper;

public class LoginUtil {

    public static boolean isSuccesfullyLoggedIn() {
        boolean loggedIn = false;

        Customer user = Paper.book().read("user");

        if(user != null) {
            loggedIn = isValidToken(user.getToken());
        }

        return loggedIn;
    }

    public static boolean isValidToken(String token) {
        boolean isValid = false;

        try {
            isValid = Jwts.parser().setSigningKey(Config.JWT_key.getBytes()).parseClaimsJws(token).getBody().getExpiration().after(new Date());
        } catch (SignatureException e) {
            Log.e("JWT", e.getMessage());
        }

        return isValid;
    }

    public static boolean isGuest() {
        return Paper.book().read("user") == null;
    }

    public static int getUserID() {
        return ((Customer) Paper.book().read("user")).getId();
    }

    public static String getToken() {
        return ((Customer) Paper.book().read("user")).getToken();
    }

    public static void removeUser() {
        Paper.book().destroy();
    }

    public static Map<String, String> getAuthHeaders() {
        Map<String, String>  headers = new HashMap<>();
        headers.put("Cache-Control", "no-cache");
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("W-Access-Token", getToken());
        return headers;
    }




}
