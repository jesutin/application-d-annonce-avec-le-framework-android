package com.a21713885.l3.unicaen.android.annonceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosteAnnonceActivity extends AppCompatActivity {

    private EditText titre;
    private EditText desc;
    private EditText prix;
    private EditText test;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste_annonce);

        this.titre = (EditText) findViewById(R.id.titre_an);
        this.desc = (EditText) findViewById(R.id.desc_an);
        this.prix = (EditText) findViewById(R.id.prix_an);
        this.test = (EditText) findViewById(R.id.test);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Button poster = (Button) findViewById(R.id.post);

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titre_chaine = titre.getText().toString().trim();
                String desc_chaine = desc.getText().toString().trim();
                String prix_chaine = prix.getText().toString().trim();
                if (!TextUtils.isEmpty(titre_chaine) && !TextUtils.isEmpty(desc_chaine) && !TextUtils.isEmpty(prix_chaine)){
                    sendAnnoncePost(titre_chaine, desc_chaine, prix_chaine);
                }
            }
        });

    }

    public void sendAnnoncePost(String titre, String desc, String prix){
        String apikey = "21713885", method= "save",pseudo="alpha",emailContact="alpha@gmail.com", telContact="0682136682",
        ville="Herouville",cp="14200";
        apiInterface.createAnnonce(apikey, method, titre, desc, prix, pseudo, emailContact,telContact,ville,cp).enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(Call<Annonce> call, Response<Annonce> response) {
                showResponse(response.toString());
                System.out.println("annonce sauvegardée avec succès"+response.body().toString());
            }

            @Override
            public void onFailure(Call<Annonce> call, Throwable t) {
                System.out.println("Impossible de sauvegarder l'annonce");
                Log.e("Erreur post Annonnce", "Impossible de sauvegarder l'annonce");
            }
        });
    }

    public void showResponse(String response){

        test.setText(response);
    }
}
