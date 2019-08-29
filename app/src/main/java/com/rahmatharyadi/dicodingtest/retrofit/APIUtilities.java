package com.rahmatharyadi.dicodingtest.retrofit;

public class APIUtilities {
    public static String BASE_URL = "https://rahmatharyadii.github.io/";

    public static RequestAPIServices getAPIServices() {
        return RetrofitClient.getClient(BASE_URL).create(RequestAPIServices.class);
    }
}