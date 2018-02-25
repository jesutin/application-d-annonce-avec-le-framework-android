package com.a21713885.l3.unicaen.android.annonceapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ImageViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_imgV));
        ViewPager viewPager = (ViewPager)findViewById(R.id.affichage_image);
        Annonce annonce = getIntent().getExtras().getParcelable("images");
        ImageAdapter imageAdapter = new ImageAdapter(this,annonce.getImages());
        viewPager.setAdapter(imageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuListner menuListner = new MenuListner(item);
        menuListner.action(new View(ImageViewActivity.this));
        return super.onOptionsItemSelected(item);
    }
}
