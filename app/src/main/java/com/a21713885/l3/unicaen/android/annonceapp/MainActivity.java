package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

/*
*Activity d'entré de la fonction cette activity ne se contente d'appeler directement la liste d'annonce
* une fois l'application lancé.
* elle est également chargé de tuer tous les processus de l'application quand on click sur l'item quitter
* à partire de n'importe quelle activity
*
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        listeAnnoncesListner();
    }

    //Methode appelant l'intent des liste d'annonces
    private void listeAnnoncesListner()
    {
        Intent intent = new Intent(this,ListeAnnonceActivity.class);
        startActivity(intent);
    }


}
