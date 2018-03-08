package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  Jonas , Morteza, Alpha, Amadou on 19/01/18.
 */

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.ViewHolder> {

    private List<Annonce> list;
    private Context context;

    public AnnonceAdapter(List<Annonce> liste, Context c){
        this.list = liste;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnnonceListner(view);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Annonce annonce = list.get(position);
        Log.d("debug dans Adapter ",annonce.getTitre());
        holder.id.setText(String.valueOf(position));
        holder.titre.setText(annonce.getTitre());
        holder.price.setText(annonce.getPrice() + "€");
        holder.pseudo.setText(annonce.getPseudo());
        holder.email.setText(annonce.getEmailContact());
        if (annonce.getImages().size()!=0)
        {
            Picasso.with(holder.imageAnnonce.getContext()).
                    load(annonce.getImages().get(0).toString()).
                    into(holder.imageAnnonce);
        }
        else
        {
            Picasso.with(holder.imageAnnonce.getContext()).
                    load(R.drawable.feu).
                    into(holder.imageAnnonce);
        }
    }

    private void getAnnonceListner(View view)
    {
        Intent intent = new Intent(view.getContext(),VoirAnnonceActivity.class);

        TextView id = (TextView) view.findViewById(R.id.id_item);
        Annonce annonce = list.get(Integer.valueOf(id.getText().toString()));

        intent.putExtra("Annonce",annonce);
        view.getContext().startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView titre;
        public TextView pseudo;
        public TextView email;
        public TextView price;
        public ImageView imageAnnonce;

        
        public ViewHolder(View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.id_item);
            this.titre = (TextView) itemView.findViewById(R.id.titre_item);
            this.price = (TextView) itemView.findViewById(R.id.price_item);
            this.pseudo = (TextView) itemView.findViewById(R.id.pseudo_item);
            this.email = (TextView) itemView.findViewById(R.id.email_contact_item);
            this.imageAnnonce= (ImageView) itemView.findViewById(R.id.image_annonce_item);
        }

    }

}
