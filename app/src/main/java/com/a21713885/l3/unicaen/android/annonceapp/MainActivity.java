package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private  Button voirAnnonce,listeAnnonce;
    private Button posteAnnonce;
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
        posteAnnonce = (Button)findViewById(R.id.post);
        posteAnnonce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                posteAnnonce(view);
            }
        });


    }

   private  void posteAnnonce (View view)
   {
       Intent intent = new Intent(this, PosteAnnonceActivity.class);
       startActivity(intent);


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
