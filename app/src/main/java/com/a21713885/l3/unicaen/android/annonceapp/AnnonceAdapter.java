package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by djelo bah on 01-02-18.
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Annonce annonce = list.get(position);
        Log.d("debug dans Adapter ",annonce.getTitre());
        //holder.bind(annonce);
        holder.titre.setText(annonce.getTitre());
        //holder.price.setText(annonce.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titre;
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titre = (TextView) itemView.findViewById(R.id.titre_item);
            this.price = (TextView) itemView.findViewById(R.id.pri_item);
        }

        public void bind(Annonce annonce){
            titre.setText(annonce.getTitre());
            price.setText(annonce.getPrice());
        }
    }

}
