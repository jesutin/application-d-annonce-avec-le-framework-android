package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VoirAnnonceActivity extends AppCompatActivity {
    private  JSONObject jo;
    private Annonce annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_annonce);
        //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        annonce = getIntent().getExtras().getParcelable("Annonce");

        //Toast.makeText(this, "Inside VoirAnnonceActivity "+annonce.getId(), Toast.LENGTH_SHORT).show();

        TextView desc = (TextView) findViewById(R.id.description),
                titre = (TextView) findViewById(R.id.Titre),
                pri = (TextView) findViewById(R.id.prix),
                code_p = (TextView) findViewById(R.id.codePostal),
                ville = (TextView) findViewById(R.id.ville),
                datepub = (TextView) findViewById(R.id.date_pub),
                name = (TextView) findViewById(R.id.nom),
                mail = (TextView) findViewById(R.id.mail),
                tel = (TextView) findViewById(R.id.telephone);
        ImageView iv = (ImageView) findViewById(R.id.image_annonce);

        titre.setText(annonce.getTitre().toString());
        pri.setText(annonce.getPrice().toString());
        code_p.setText(annonce.getCodePostal().toString());
        ville.setText(annonce.getVille().toString());
        desc.setText(annonce.getDescription().toString());
        datepub.setText(annonce.getDate().toString());
        mail.setText(annonce.getEmailContact().toString());
        name.setText(annonce.getPseudo().toString());
        tel.setText(annonce.getTelContact().toString());
        ArrayList image = annonce.getImages();
        //Toast.makeText(VoirAnnonceActivity.this, image.get(0).toString(), Toast.LENGTH_SHORT).show();
        if (image.size() == 0) {
            Picasso.with(VoirAnnonceActivity.this).
                    load(R.drawable.feu).
                    into(iv);
        } else {
            Picasso.with(VoirAnnonceActivity.this).
                    load(image.get(0).toString()).
                    into(iv);
        }
        ImageView listeImage=(ImageView) findViewById(R.id.image_annonce);
        listeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageListner(view,annonce);
            }
        });
    }
    private void getImageListner(View view,Annonce annonce)
    {
        Intent intent = new Intent(this,ImageViewActivity.class);
        intent.putExtra("images", annonce);
        startActivity(intent);
    }
}


