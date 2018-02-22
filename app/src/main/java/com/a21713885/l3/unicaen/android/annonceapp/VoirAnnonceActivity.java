package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VoirAnnonceActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener
{
    private  JSONObject jo;
    private Annonce annonce;
    HashMap<String, String> HashMapForURL ;
    SliderLayout sliderLayout ;
    ArrayList<Annonce> listeAnnonces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_annonce);
        //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        annonce = getIntent().getExtras().getParcelable("Annonce");

        TextView desc = (TextView) findViewById(R.id.description),
                titre = (TextView) findViewById(R.id.Titre),
                pri = (TextView) findViewById(R.id.prix),
                code_p = (TextView) findViewById(R.id.codePostal),
                ville = (TextView) findViewById(R.id.ville),
                datepub = (TextView) findViewById(R.id.date_pub),
                name = (TextView) findViewById(R.id.nom),
                mail = (TextView) findViewById(R.id.mail),
                tel = (TextView) findViewById(R.id.telephone);
        SliderLayout sliderLayout = (SliderLayout) findViewById(R.id.image_annonce);

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

        TextSliderView textSliderView = new TextSliderView(this);

        textSliderView
                .description(name2)
                .image(HashMapForURL.get(name2))
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);

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

        sliderLayout.addOnPageChangeListener(this);

        //Toast.makeText(VoirAnnonceActivity.this, image.get(0).toString(), Toast.LENGTH_SHORT).show();
        /*if (image.size() == 0) {
            Picasso.with(VoirAnnonceActivity.this).
                    load(R.drawable.feu).
                    into(iv);
        } else {
            Picasso.with(VoirAnnonceActivity.this).
                    load(image.get(0).toString()).
                    into(iv);
        }*/

        /*SliderLayout listeImage=(SliderLayout) findViewById(R.id.image_annonce);
        listeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageListner(view,annonce);
            }
        });*/
        final String numero = tel.getText().toString();
        tel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               getNumeroListener(view,numero);
            }
        });
        final String e_mail =mail.getText().toString(),
               titre_mail =titre.getText().toString()  ;
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMailListener(view,e_mail,titre_mail);
            }
        });
    }
    private void getImageListner(Annonce annonce)
    {
        Intent intent = new Intent(this,ImageViewActivity.class);
        intent.putExtra("images", annonce);
        startActivity(intent);
    }

    private void getNumeroListener(View view,String numero){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+numero));
        startActivity(intent);
    }
    private void getMailListener(View view, String mail, String titre){
       Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        intent.putExtra(Intent.EXTRA_SUBJECT, titre);
        startActivity(intent);
    }

    public void AddImages(ArrayList<String> listeImages) {

        this.HashMapForURL = new HashMap<String, String>();
        if (listeImages.size() != 0) {
            for (int i = 0; i < listeImages.size(); i++) {
                this.HashMapForURL.put("images "+(i+1), listeImages.get(i));
            }
        }

    }

   /* public void fillSlider(ArrayList<String> listeImages){
        this.sliderLayout = (SliderLayout)findViewById(R.id.image_annonce);
        AddImages(listeImages);
        for(String name : HashMapForURL.keySet()){
            TextSliderView textSliderView = new TextSliderView(VoirAnnonceActivity.this);

            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    ;

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(3000);

        sliderLayout.addOnPageChangeListener(VoirAnnonceActivity.this);
    }*/

    @Override
    public void onSliderClick(BaseSliderView slider) {
        getImageListner(this.annonce);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}


