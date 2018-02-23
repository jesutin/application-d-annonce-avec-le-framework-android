package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
private  Button voirAnnonce,listeAnnonce;
    private Button posteAnnonce, monProfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voirAnnonce=(Button)findViewById(R.id.voir_annonce);

        voirAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                try{voirAnnonceListner(view);}catch (Exception e){e.printStackTrace();}
            }
        });
        listeAnnonce = (Button)findViewById(R.id.lister_annones);
        listeAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listeAnnoncesListner(view);
            }
        });
        posteAnnonce = (Button)findViewById(R.id.deposer_annonce);
        posteAnnonce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                posteAnnonce(view);
            }
        });

        monProfil=(Button) findViewById(R.id.profile);
        monProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PreferencesActivity.class);
                startActivity(intent);
            }
        });

    }

   private  void posteAnnonce (View view)
   {
       Intent intent = new Intent(this, PosteAnnonceActivity.class);
       startActivity(intent);


   }
    private void voirAnnonceListner(View view) throws IOException
    {
        getRequest("https://ensweb.users.info.unicaen.fr/android-api/?apikey=21713885&method=randomAd");
    }
    private void listeAnnoncesListner(View view)
    {
        Intent intent = new Intent(this,ListeAnnonceActivity.class);
        startActivity(intent);
    }
    protected void getRequest(String url) throws IOException{
        StringRequest sRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      try {
                          Intent intent = new Intent(MainActivity.this,VoirAnnonceActivity.class);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject o = (JSONObject)jsonObject.get("response");
                            System.out.println(response);
                                ArrayList<String> image = new ArrayList<>();
                                //creation de l'arraylist d'images
                            JSONArray img = (JSONArray)o.get("images") ;
                          System.out.println("images"+img);
                                if (img.length()!=0)
                                {
                                    for (int k = 0; k < img.length(); k++)
                                        image.add(img.get(k).toString());
                                }
                                String date = ListeAnnonceActivity.getDate(o.get("date").toString());
                                Annonce annonce = new Annonce(o.get("id").toString(), o.get("titre").toString(), o.get("description").toString(),
                                        o.get("prix").toString(), o.get("pseudo").toString(), o.get("emailContact").toString(),
                                        o.get("telContact").toString(), o.get("ville").toString(), o.get("cp").toString(),
                                        image, date);
                          intent.putExtra("Annonce",annonce);
                          startActivity(intent);
                            }
                   catch (Exception e) {
                            Toast.makeText(MainActivity.this, "EXCEPTION", Toast.LENGTH_SHORT).show();
                            Log.d("Exception voirAnnonce","Exception");
                        }

                    }

                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sRequest);
    }
}
