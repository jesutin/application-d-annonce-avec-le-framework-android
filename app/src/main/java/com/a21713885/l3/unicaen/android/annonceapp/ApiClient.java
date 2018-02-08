package com.a21713885.l3.unicaen.android.annonceapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by djelo bah on 08-02-18.
 */

public class ApiClient {
    private static final String BASE_URL= "https://ensweb.users.info.unicaen.fr/";
    private static Retrofit retrofit=null;

    public static Retrofit getApiClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
