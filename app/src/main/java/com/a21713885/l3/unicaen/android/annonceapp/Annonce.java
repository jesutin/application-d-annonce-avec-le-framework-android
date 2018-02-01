package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
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
    private ArrayList<String>  images = new ArrayList<>();
    private String date;
    private Activity activity;
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
    public Annonce(){

    }

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


    public static ArrayList<Annonce> createListeAnnonce(JSONArray ja) throws JSONException {
        ArrayList<Annonce> liste = new ArrayList<>();

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonPers = ja.getJSONObject(i);
            String titre = jsonPers.get("titre").toString(),
            id = jsonPers.get("id").toString(),
            price = jsonPers.get("prix").toString(),
            code_p =jsonPers.get("cp").toString(),
            ville=jsonPers.get("ville").toString(),
            description=jsonPers.get("description").toString(),
            datepub=jsonPers.get("date").toString(),
            mail=jsonPers.get("emailContact").toString(),
            pseudo=jsonPers.get("pseudo").toString(),
            tel=jsonPers.get("telContact").toString();
            JSONArray img =(JSONArray) jsonPers.get("images");
            ArrayList<String>image=new ArrayList<>();
            //creation de l'arraylist d'images
            for(int k=0; k<img.length();k++)
                image.add(img.get(k).toString());
            liste.add(new Annonce(id, titre, description,Integer.valueOf(price),pseudo, mail,tel,ville, code_p,image, datepub));
        }
        return liste;
    }


}
