package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//gestion du profil de l'annonceur

public class PreferencesActivity extends AppCompatActivity {
    public static  final String MY_PREF_NAME = "preferences";
    TextView pseudo, mail,tel,cp,ville;
    Button valider,modifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_pref));
        pseudo = (TextView) findViewById(R.id.pref_pseudo);
        mail = (TextView) findViewById(R.id.pref_email);
        tel = (TextView) findViewById(R.id.pref_tel);
        cp = (TextView) findViewById(R.id.pref_cp);
        ville = (TextView) findViewById(R.id.pref_ville);
        valider = (Button) findViewById(R.id.pref_valider);
        modifier = (Button) findViewById(R.id.pref_edit);
        fillchamps();
        griser();
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPreferences();
                griser();
            }
        });
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activer();
            }
        });
    }
    //Création des preferences de l'annonceur
    protected  void myPreferences(){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE).edit();
        editor.putString("pseudo",pseudo.getText().toString());
        editor.putString("emailContact",mail.getText().toString());
        editor.putString("telContact",tel.getText().toString());
        editor.putString("ville",ville.getText().toString());
        editor.putString("cp",cp.getText().toString());
        editor.apply();
    }

    //grise les gens des preferences quand on lance l'intent preferences ou quand l'annonceur valide ces modification
    public void griser(){
        pseudo.setEnabled(false);
        mail.setEnabled(false);
        tel.setEnabled(false);
        cp.setEnabled(false);
        ville.setEnabled(false);
    }

    //active les champs des preferences quand l'annonceur veux les modifier
    public void activer(){
        pseudo.setEnabled(true);
        mail.setEnabled(true);
        tel.setEnabled(true);
        cp.setEnabled(true);
        ville.setEnabled(true);
    }
    // remplir les champs des preferences par les preferences de l'annonceur ou avec des valeurs par défaut s'ils ne sont pas renseigné
    public void fillchamps(){
        SharedPreferences prefs  = getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE);
        pseudo.setText(prefs.getString("pseudo","nom"));
        mail.setText(prefs.getString("emailContact","xyz@mail.com"));
        tel.setText(prefs.getString("telContact","00-00-00-00-00"));
        ville.setText(prefs.getString("ville","ville"));
        cp.setText(prefs.getString("cp","000000"));
    }


    //ajout du menu au toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuListner menuListner = new MenuListner(item);
        menuListner.action(new View(PreferencesActivity.this));
        return super.onOptionsItemSelected(item);
    }
}
