package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadou on 19/01/18.
 */

public class Annonce {
    private String id;
    private String titre;
    private String description;
    private int price;
    private String pseudo;
    private String emailContact;
    private String telContact;
    private String ville;
    private String codePostal;
    private ArrayList<String> images = new ArrayList<>();
    private String date;
    //private Activity activity;
    private List<Annonce> liste=new ArrayList<>();
    public void setListe(Annonce a){this.liste.add(a);}
    public List<Annonce> getListe(){return this.liste;}

    public Annonce(String id, String titre, String description, int price,
                   String pseudo, String emailContact, String telContact,
                   String ville, String codePostal, ArrayList<String> images, String date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.price = price;
        this.pseudo = pseudo;
        this.emailContact = emailContact;
        this.telContact = telContact;
        this.ville = ville;
        this.codePostal = codePostal;
        this.images = images;
        this.date = date;

    }

    public  Annonce(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getTelContact() {
        return telContact;
    }

    public void setTelContact(String telContact) {
        this.telContact = telContact;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    //public static  List getListe(){ArrayList<Annonce> l; l=this.liste; return l;}
    //public static  void Addinliste(Annonce a){this.liste.add(a);}

    public   List<Annonce> createListAnnonce(Activity a) {
        RequestQueue rq = Volley.newRequestQueue(a);
        String url = "https://ensweb.users.info.unicaen.fr/android-api/mock-api/liste.json";
        StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //List<Annonce> liste= new ArrayList<>();
                            JSONObject jo = new JSONObject(response);
                            if (!jo.get("success").toString().equals("null")) {
                                Log.d("====Success",jo.get("response").toString());

                                JSONArray ja = (JSONArray) jo.get("response");
                                Log.d("====Success",jo.get("response").toString());
                               // Toast.makeText(, ja.get(0).toString(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jsonPers = ja.getJSONObject(i);
                                    Log.d("====Object dans boucle",jsonPers.toString()+" et "+ja.length());
                                    String titre = jsonPers.get("titre").toString();
                                    Log.d("====titre",titre);



                                    String       id = jsonPers.get("id").toString();
                                    Log.d("====id",id);
                                    String    price = jsonPers.get("prix").toString();
                                    Log.d("====prix",price);
                                    String      code_p = jsonPers.get("cp").toString();
                                    Log.d("====code post ",code_p);
                                    String     ville = jsonPers.get("ville").toString();
                                    Log.d("====ville",ville);
                                    String      description = jsonPers.get("description").toString();
                                    String      datepub = jsonPers.get("date").toString();
                                    String      mail = jsonPers.get("emailContact").toString();
                                    String      pseudo = jsonPers.get("pseudo").toString();

                                    String       tel = jsonPers.get("telContact").toString();
                                    JSONArray img = (JSONArray) jsonPers.get("images");
                                    ArrayList<String> image = new ArrayList<>();
                                    //creation de l'arraylist d'images
                                    //for (int k = 0; k < img.length(); k++)
                                       // image.add(img.get(k).toString());
                                    //Toast.makeText(null, liste.size(), Toast.LENGTH_SHORT).show();
                                    Annonce.this.setListe(new Annonce(id, titre, description, Integer.valueOf(price), pseudo, mail, tel, ville, code_p, image, datepub));

                                        Log.d("debug liste",Annonce.this.getListe().get(0).getCodePostal());
                                }

                            }
                            else
                                Log.d("On en est dans le else","ELSE");
                            //Toast.makeText(ListeAnnonceActivity, "je suis dans le else", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            //.makeText(ListeAnnonceActivity, "EXCEPTION", Toast.LENGTH_SHORT).show();
                            Log.d("Exception de liste","Exception");
                        }

                    }

                }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
            }

        });
        rq.add(sRequest);
        try {

        }
        catch (Exception e){Toast.makeText(a,"EXCEPTION", Toast.LENGTH_SHORT).show();}
        return Annonce.this.getListe();
    }
}