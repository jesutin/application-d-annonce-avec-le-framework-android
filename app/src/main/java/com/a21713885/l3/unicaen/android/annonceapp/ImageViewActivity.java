package com.a21713885.l3.unicaen.android.annonceapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ViewPager viewPager = (ViewPager)findViewById(R.id.affichage_image);
        Annonce annonce = getIntent().getExtras().getParcelable("images");
        ImageAdapter imageAdapter = new ImageAdapter(this,annonce.getImages());
        viewPager.setAdapter(imageAdapter);
    }
}
