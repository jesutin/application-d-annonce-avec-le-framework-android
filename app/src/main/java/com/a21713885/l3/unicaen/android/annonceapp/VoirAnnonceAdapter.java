package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadou on 22/02/18.
 */

public class VoirAnnonceAdapter extends PagerAdapter{
    private Context context;
    private Annonce[] listeAnnonces;
    Annonce current;
    public  VoirAnnonceAdapter(Annonce[] listeAnnonces, Annonce current){
        this.listeAnnonces=listeAnnonces;
        //this.context=context;
        this.current=current;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        getAnnonceListner(container,listeAnnonces,current);
        return new VoirAnnonceActivity();
    }
    @Override
    public int getCount() {
        return  listeAnnonces.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    private void getAnnonceListner(View view,Annonce[] listeAnnonces,Annonce current)
    {
        Intent intent = new Intent(view.getContext(),VoirAnnonceActivity.class);
        TextView id = (TextView) view.findViewById(R.id.id_item);
        intent.putExtra("Annonce",current);
        view.getContext().startActivity(intent);
    }

}
