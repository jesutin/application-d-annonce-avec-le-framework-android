package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
private  Button voirAnnonce,listeAnnonce;
    private Button posteAnnonce, monProfil;
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






    private void listeAnnoncesListner()
    {
        Intent intent = new Intent(this,ListeAnnonceActivity.class);
        startActivity(intent);
    }


}
