package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by  Jonas , Morteza, Alpha, Amadou on 03/02/18.
 */

//Adapter des images afin de pouvoir les faire slider

public class ImageAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String>listeImage;

    //Constructeur
    public  ImageAdapter(Context context, ArrayList<String> listeImage){
        this.listeImage=listeImage;
        this.context=context;
    }


    //redéfinissions des methodes de PageAdapter
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).
                load(listeImage.get(position)).
                into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }
    @Override
    public int getCount() {
        return listeImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}

