package com.a21713885.l3.unicaen.android.annonceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.jar.JarEntry;

public class ListeAnnonceActivity extends AppCompatActivity {

    private  String annonceJsonArray ;
    private List<Annonce> annonceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_annonce);
        // Recuperer la liste des annonces ici
        Annonce annonce = new Annonce();
        annonceList = annonce.createListAnnonce(this);
        if(annonceList.isEmpty())
            Toast.makeText(ListeAnnonceActivity.this, "la liste est vide", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(ListeAnnonceActivity.this, "la liste est pas du tout vide"+annonceList.size(), Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.liste_annonces);
        //Toast.makeText(ListeAnnonceActivity.this, annonceList.get(0).toString(), Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AnnonceAdapter annonceAdapter = new AnnonceAdapter(annonceList, this);
        recyclerView.setAdapter(annonceAdapter);


    }

}
