package com.example.svenwesterlaken.rentamovie.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.svenwesterlaken.rentamovie.R;

/**
 * Created by Dajakah on 17-6-2017.
 */

public class TokenUtil {

    public static boolean tokenAvailable(Context context) {
        boolean result = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(context.getString(R.string.saved_token), null);

        if (!token.equals("default token")) {
            result = true;
        }

        return result;
    }

    public static boolean tokenValid(Context context) {
        boolean result = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long timestamp = sharedPreferences.getLong(context.getString(R.string.token_expiration_timestamp), 0);

        if (timestamp != 0) {
            long currentTimestamp = System.currentTimeMillis() / 1000L;

            if (currentTimestamp < timestamp) {
                result = true;
            }
        }

        return result;
    }

    public static String getUserMail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.token_user_mail), " ");
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.saved_token), token);
//        editor.putLong(context.getString(R.string.token_expiration_timestamp), expirationTime);
//        editor.putString(context.getString(R.string.token_user_mail), " ");

        editor.apply();
    }

    public static void voidToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.saved_token), "default token");
        editor.putLong(context.getString(R.string.token_expiration_timestamp), 1497517801L);
//        editor.putString(context.getString(R.string.token_user_mail), "");
        editor.apply();
    }


}
