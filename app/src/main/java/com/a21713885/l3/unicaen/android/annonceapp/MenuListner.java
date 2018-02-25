package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by amadou on 23/02/18.
 */

public class MenuListner {

    private MenuItem item;
    public MenuListner(MenuItem item){
        this.item=item;
    }
    public void action(View view) {
        if(item.getItemId()==R.id.liste_annonces_menu){
            listeAnnoncesListner(view);
        }

        if(item.getItemId()==R.id.post_annonce_menu){
            posteAnnonce(view);
         }

        if(item.getItemId()==R.id.pref_menu){
            Intent intent = new Intent(view.getContext(),PreferencesActivity.class);
            view.getContext().startActivity(intent);
        }


        if(item.getItemId()==R.id.voir_annonce_menu){
            try {
                voirAnnonceListner(view);
            }catch (Exception e){}

        }

        if(item.getItemId() == R.id.quitter_menu){
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("EXIT", true);
            view.getContext().startActivity(intent);

        }



    }



    private void voirAnnonceListner(View view) throws IOException
    {
        getRequest("https://ensweb.users.info.unicaen.fr/android-api/?apikey=21713885&method=randomAd",view);
    }
    private void listeAnnoncesListner(View view)
    {
        Intent intent = new Intent(view.getContext(),ListeAnnonceActivity.class);
        view.getContext().startActivity(intent);
    }

    protected void getRequest(String url, final View view) throws IOException{
        StringRequest sRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Intent intent = new Intent(view.getContext(),VoirAnnonceActivity.class);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject o = (JSONObject)jsonObject.get("response");
                            System.out.println(response);
                            ArrayList<String> image = new ArrayList<>();
                            //creation de l'arraylist d'images
                            JSONArray img = (JSONArray)o.get("images") ;
                            System.out.println("images"+img);
                            if (img.length()!=0)
                            {
                                for (int k = 0; k < img.length(); k++)
                                    image.add(img.get(k).toString());
                            }
                            String date = ListeAnnonceActivity.getDate(o.get("date").toString());
                            Annonce annonce = new Annonce(o.get("id").toString(), o.get("titre").toString(), o.get("description").toString(),
                                    o.get("prix").toString(), o.get("pseudo").toString(), o.get("emailContact").toString(),
                                    o.get("telContact").toString(), o.get("ville").toString(), o.get("cp").toString(),
                                    image, date);
                            intent.putExtra("Annonce",annonce);
                            view.getContext().startActivity(intent);
                        }
                        catch (Exception e) {

                            Log.d("Exception voirAnnonce","Exception");
                        }

                    }

                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(sRequest);
    }

    private  void posteAnnonce (View view)
    {
        Intent intent = new Intent(view.getContext(), PosteAnnonceActivity.class);
        view.getContext().startActivity(intent);
    }


}
