package com.example.svenwesterlaken.rentamovie.util;

/**
 * Created by Dajakah on 17-6-2017.
 */

public class Config {
    private static final String BASIC_URL = "https://programmeren-tent-4.herokuapp.com";

    public static final String URL_LOGIN = BASIC_URL + "/api/v1/login";
    public static final String URL_REGISTER = BASIC_URL + "/api/v1/register";
    public static final String URL_FILMS = BASIC_URL + "/api/v1/films?count=20&offset=0";
    public static final String URL_RENTAL = BASIC_URL + "/api/v1/rentals";

}

