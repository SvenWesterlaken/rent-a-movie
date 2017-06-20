package com.example.svenwesterlaken.rentamovie.util;

/**
 * Created by Dajakah on 17-6-2017.
 */

public class Config {
    private static final String BASIC_URL = "https://programmeren-tent-4.herokuapp.com/api/v1";

    public static final String URL_LOGIN = BASIC_URL + "/login";
    public static final String URL_REGISTER = BASIC_URL + "/register";
    public static final String URL_FILMS = BASIC_URL + "/films?count=20&offset=0";
    public static final String URL_RENTAL = BASIC_URL + "/rentals/";
    public static final String URL_INVENTORY = BASIC_URL + "/inventory/";

    public static final String JWT_key = "SecretKeyForProgramExam-01928738-";

    public static final int REGISTER_REQUEST = 1;

}

