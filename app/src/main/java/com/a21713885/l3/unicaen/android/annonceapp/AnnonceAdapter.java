package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by djelo bah on 01-02-18.
 */

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceViewHolder> {

    private List<Annonce> list;
    private Activity activity;

    public AnnonceAdapter(List<Annonce> liste, Activity activity){
        this.list = liste;
        this.activity = activity;
    }

    @Override
    public AnnonceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new AnnonceViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(AnnonceViewHolder holder, int position) {
        Annonce annonce = list.get(position);
        holder.bind(annonce);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
