package com.a21713885.l3.unicaen.android.annonceapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/*
*
* Activiter pour afficher les images d'une annonce
*
 */
public class ImageViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        //ajout le toolbar à l'activity ListAnnonceActivity

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_imgV));
        ViewPager viewPager = (ViewPager)findViewById(R.id.affichage_image);

        //Recuperation de l'annonce depuis voirAnnonceActivity
        Annonce annonce = getIntent().getExtras().getParcelable("annonce");

        //instanciation de l'adapteur d'image pour afficher et son ajout dans le viewPager
        ImageAdapter imageAdapter = new ImageAdapter(this,annonce.getImages());
        viewPager.setAdapter(imageAdapter);
    }

    // creer le menu et l'ajouter au toolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //ecouter les evenements sur les items du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Creation d' une instance de MenuListener et appel de sa methode action
        MenuListner menuListner = new MenuListner(item);
        menuListner.action(new View(ImageViewActivity.this));
        return super.onOptionsItemSelected(item);
    }
}
