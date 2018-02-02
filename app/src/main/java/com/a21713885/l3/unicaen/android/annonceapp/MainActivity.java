package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private  Button voirAnnonce,listeAnnonce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voirAnnonce=(Button)findViewById(R.id.voir_annonce);
        voirAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voirAnnonceListner(view);
            }
        });
        listeAnnonce = (Button)findViewById(R.id.lister_annones);
        listeAnnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listeAnnoncesListner(view);
            }
        });


    }


    private void voirAnnonceListner(View view)
    {
        Intent intent = new Intent(this,VoirAnnonceActivity.class);
        startActivity(intent);
    }
    private void listeAnnoncesListner(View view)
    {
        Intent intent = new Intent(this,ListeAnnonceActivity.class);
        startActivity(intent);
    }
}
