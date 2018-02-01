package com.a21713885.l3.unicaen.android.annonceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ListeAnnonceActivity extends AppCompatActivity {

    List<Annonce> annonceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_annonce);
        // Recuperer la liste des annonces ici

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.liste_annonces);
        //définition de l'agencement des elements sous forme de liste
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AnnonceAdapter annonceAdapter = new AnnonceAdapter(annonceList, this);
        recyclerView.setAdapter(annonceAdapter);
    }


    public void liste(){


        RequestQueue rq = Volley.newRequestQueue(this);
        String url  = "https://ensweb.users.info.unicaen.fr/android-api/mock-api/completeAdWithImages.json";
        StringRequest sRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            if (!jo.get("success").toString().equals("null")) {
                                JSONArray ja = new JSONArray(jo.get("response").toString());
                            }
                        }
                        catch (Exception e)
                        {

                        }

                    }
                    },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    TextView desc = (TextView)findViewById(R.id.description);
                    desc.setText("Erreur page non recuperer");

                    }

                 });
    }


}
