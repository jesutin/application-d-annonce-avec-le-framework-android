package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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
public class PosteAnnonceActivity extends AppCompatActivity {

    private EditText titre;
    private EditText desc;
    private EditText prix;
    public static  final String MY_PREF_NAME = "preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poste_annonce);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_activity));
        this.titre = (EditText) findViewById(R.id.titre_an);
        this.desc = (EditText) findViewById(R.id.desc_an);
        this.prix = (EditText) findViewById(R.id.prix_an);


        Button poster = (Button) findViewById(R.id.post);

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titre_chaine = titre.getText().toString().trim();
                String desc_chaine = desc.getText().toString().trim();
                String prix_chaine = prix.getText().toString().trim();
                if (!TextUtils.isEmpty(titre_chaine) && !TextUtils.isEmpty(desc_chaine) && !TextUtils.isEmpty(prix_chaine)){
                    saveAnnonce(titre_chaine, desc_chaine, prix_chaine);
                    Toast.makeText(PosteAnnonceActivity.this, "Annonce Ajoutée", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void saveAnnonce(final String titre, final String desc, final String prix) {
        String url = "https://ensweb.users.info.unicaen.fr/android-api/";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(PosteAnnonceActivity.this,UploaderActivity.class);
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
                SharedPreferences prefs  = getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE);
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey","21712875");
                params.put("method","save");
                params.put("titre",titre);
                params.put("description",desc);
                params.put("prix",prix);
                params.put("pseudo",prefs.getString("pseudo","Amadou"));
                params.put("emailContact",prefs.getString("emailContact","test@test.com"));
                params.put("telContact",prefs.getString("telContact","22222220225"));
                params.put("ville",prefs.getString("ville","paris"));
                params.put("cp",prefs.getString("cp","75000"));

            return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuListner menuListner = new MenuListner(item);
        menuListner.action(new View(PosteAnnonceActivity.this));
        return super.onOptionsItemSelected(item);
    }

}