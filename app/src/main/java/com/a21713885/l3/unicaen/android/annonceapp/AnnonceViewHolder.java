package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

/**
 * Created by djelo bah on 01-02-18.
 */

public class AnnonceViewHolder extends RecyclerView.ViewHolder {
    private TextView titre;
    private TextView price;

    public AnnonceViewHolder(View itemView) {
        super(itemView);
        this.titre = (TextView) itemView.findViewById(R.id.Titre);
        this.price = (TextView) itemView.findViewById(R.id.prix);
    }

    public void bind(Annonce annonce){
        titre.setText(annonce.getTitre());
        price.setText(annonce.getPrice());
    }
}
