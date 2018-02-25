package com.a21713885.l3.unicaen.android.annonceapp;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadou on 19/01/18.
 */

public class Annonce implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("titre")
    @Expose
    private String titre;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("prix")
    @Expose
    private String price;

    @SerializedName("pseudo")
    @Expose
    private String pseudo;

    @SerializedName("emailContact")
    @Expose
    private String emailContact;

    @SerializedName("telContact")
    @Expose
    private String telContact;

    @SerializedName("ville")
    @Expose
    private String ville;

    @SerializedName("cp")
    @Expose
    private String codePostal;

    @SerializedName("images")
    @Expose
    private ArrayList<String> images = new ArrayList<>();

    @SerializedName("date")
    @Expose
    private String date;

    public Annonce(String id, String titre, String description, String price,
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

    public Annonce(String titre, String description, String price,
                   String pseudo, String emailContact, String telContact,
                   String ville, String codePostal, ArrayList<String> images) {
        this.titre = titre;
        this.description = description;
        this.price = price;
        this.pseudo = pseudo;
        this.emailContact = emailContact;
        this.telContact = telContact;
        this.ville = ville;
        this.codePostal = codePostal;
        this.images = images;

    }

    public Annonce(Parcel parcel){
        this.id = parcel.readString();
        this.titre = parcel.readString();
        this.description = parcel.readString();
        this.price = parcel.readString();
        this.pseudo = parcel.readString();
        this.emailContact = parcel.readString();
        this.telContact = parcel.readString();
        this.ville = parcel.readString();
        this.codePostal = parcel.readString();
        this.images = parcel.readArrayList(String.class.getClassLoader());
        this.date = parcel.readString();
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

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(titre);
        parcel.writeString(description);
        parcel.writeString(price);
        parcel.writeString(pseudo);
        parcel.writeString(emailContact);
        parcel.writeString(telContact);
        parcel.writeString(ville);
        parcel.writeString(codePostal);
        parcel.writeList(images);
        parcel.writeString(date);
    }
    public static final Parcelable.Creator<Annonce> CREATOR = new Creator<Annonce>() {
        @Override
        public Annonce createFromParcel(Parcel parcel) {
            return new Annonce(parcel);
        }

        @Override
        public Annonce[] newArray(int i) {
            return new Annonce[i];
        }
    };

}