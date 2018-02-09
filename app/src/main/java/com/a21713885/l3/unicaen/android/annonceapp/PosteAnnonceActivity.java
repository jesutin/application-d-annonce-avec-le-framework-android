package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                    saveAnnonce(titre_chaine, desc_chaine, prix_chaine);
                }
            }
        });

    }

    /*public void sendAnnoncePost(String titre, String desc, String prix){
        String apikey = "21713885", method= "save",pseudo="alpha",emailContact="alpha@gmail.com", telContact="0682136682",
        ville="Herouville",cp="14200";
        apiInterface.createAnnonce(apikey, method, titre, desc, prix, pseudo, emailContact,telContact,ville,cp).enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(Call<Annonce> call, Response<Annonce> response) {

                //showResponse(response.toString());
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


    }*/

    public void saveAnnonce(final String titre, final String desc, final String prix) {
        String url = "https://ensweb.users.info.unicaen.fr/android-api/";
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //*******
                        //test.setText(response.toString());
                        Intent intent = new Intent(PosteAnnonceActivity.this,VoirAnnonceActivity.class);
                        try {
                            JSONObject resp = new JSONObject(response);
                            JSONObject o = (JSONObject)resp.get("response");
                            JSONArray img = (JSONArray)o.get("images");
                            ArrayList<String> image = new ArrayList<>();
                            for (int k = 0; k < img.length(); k++)
                                image.add(img.get(k).toString());
                            Annonce annonce = new Annonce(o.get("id").toString(), o.get("titre").toString(), o.get("description").toString(),
                                    o.get("prix").toString(), o.get("pseudo").toString(), o.get("emailContact").toString(),
                                    o.get("telContact").toString(), o.get("ville").toString(), o.get("cp").toString(),
                                    image, o.get("date").toString());
                            intent.putExtra("Annonce",annonce);
                            startActivity(intent);
                        } catch (Exception e){
                            System.out.println("Erreur "+ e.getMessage());
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
    }) {
            @Override
            protected Map<String, String> getParams() {
                /*final String apikey="", method= "save",pseudo="alpha",emailContact="alpha@gmail.com", telContact="0682136682",
                        ville="Herouville",cp="14200";*/
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey","21713885");
                params.put("method","save");
                params.put("titre",titre);
                params.put("description",desc);
                params.put("prix",prix);
                params.put("pseudo","alpha");
                params.put("emailContact","alpha@gmail.com");
                params.put("telContact","0682136682");
                params.put("ville","Caen");
                params.put("cp","14200");

            return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}