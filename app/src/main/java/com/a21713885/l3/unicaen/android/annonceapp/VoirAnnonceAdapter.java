package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by djelo bah on 01-02-18.
 */

public class VoirAnnonceAdapter extends PagerAdapter{

    private List<Annonce> list = new ArrayList<>();
    private Context context;
    LayoutInflater layoutInflater;
    Annonce annonce;
    HashMap<String,String> HashMapForURL;

    public VoirAnnonceAdapter(List<Annonce> liste, Context c,Annonce annonce){
        this.list = liste;
        System.out.println("------------------------>"+list.toString());
        context = c;
        this.annonce=annonce;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View voirannnonce = layoutInflater.inflate(R.layout.layout_voir_annonce, container, false);
        container.addView(voirannnonce);
        TextView desc = (TextView) voirannnonce.findViewById(R.id.description),
                titre = (TextView) voirannnonce.findViewById(R.id.Titre),
                pri = (TextView) voirannnonce.findViewById(R.id.prix),
                code_p = (TextView) voirannnonce.findViewById(R.id.codePostal),
                ville = (TextView) voirannnonce.findViewById(R.id.ville),
                datepub = (TextView) voirannnonce.findViewById(R.id.date_pub),
                name = (TextView) voirannnonce.findViewById(R.id.nom),
                mail = (TextView) voirannnonce.findViewById(R.id.mail),
                tel = (TextView) voirannnonce.findViewById(R.id.telephone);
        SliderLayout sliderLayout = (SliderLayout) voirannnonce.findViewById(R.id.image_annonce);

        titre.setText(annonce.getTitre().toString());
        pri.setText(annonce.getPrice().toString());
        code_p.setText(annonce.getCodePostal().toString());
        ville.setText(annonce.getVille().toString());
        desc.setText(annonce.getDescription().toString());
        datepub.setText(annonce.getDate().toString());
        mail.setText(annonce.getEmailContact().toString());
        name.setText(annonce.getPseudo().toString());
        tel.setText(annonce.getTelContact().toString());
        ArrayList image = annonce.getImages();
        AddImages(image);
        if (HashMapForURL.size()==0){
            HashMapForURL.put("Aucune Image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQSjuSta9ozBoZMNUsh3TObWQA_ZsIvS4BGJy5FEzXvWWa8B1TK");
        }
        for(String name2 : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(voirannnonce.getContext());

            textSliderView
                    .description(name2)
                    .image(HashMapForURL.get(name2))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });

            textSliderView.bundle(new Bundle());
            //Toast.makeText(VoirAnnonceActivity.this, image.get(0).toString(), Toast.LENGTH_SHORT).show();

            textSliderView.getBundle()
                    .putString("extra",name2);

            sliderLayout.addSlider(textSliderView);
        }


        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(5000);

        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ((ViewPager)container).addView(voirannnonce,list.indexOf(annonce));
        return voirannnonce;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


    public void AddImages(ArrayList<String> listeImages) {

        this.HashMapForURL = new HashMap<String, String>();
        if (listeImages.size() != 0) {
            for (int i = 0; i < listeImages.size(); i++) {
                this.HashMapForURL.put("images "+(i+1), listeImages.get(i));
            }
        }

    }


}
