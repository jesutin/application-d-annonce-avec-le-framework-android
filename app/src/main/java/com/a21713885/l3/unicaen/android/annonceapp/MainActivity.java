package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private  Button voirAnnonce;
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
    }


    private void voirAnnonceListner(View view)
    {
        Intent intent = new Intent(this,VoirAnnonceActivity.class);
        startActivity(intent);
    }
}
