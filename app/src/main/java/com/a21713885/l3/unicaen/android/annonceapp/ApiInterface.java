package com.a21713885.l3.unicaen.android.annonceapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by djelo bah on 08-02-18.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("android-api")
    Call<Annonce> createAnnonce(@Field("apikey") String apikey, @Field("method") String method,
                                @Field("titre") String titre, @Field("description") String desc,
                                @Field("prix") String prix, @Field("pseudo") String pseudo,
                                @Field("emailContact") String emailContact, @Field("telContact") String telContact,
                                @Field("ville") String ville,@Field("cp") String cp);
}
