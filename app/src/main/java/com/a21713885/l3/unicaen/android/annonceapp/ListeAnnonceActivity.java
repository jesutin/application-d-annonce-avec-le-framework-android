package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ListeAnnonceActivity extends AppCompatActivity{

    private  RecyclerView recyclerView;
    private List<Annonce> annonceList;
    private String url = "https://ensweb.users.info.unicaen.fr/android-api/?apikey=21713885&method=listAll";
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_annonce);
        this.recyclerView = (RecyclerView) findViewById(R.id.liste_annonces);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.annonceList = new ArrayList<>();

        chargerListeAnnonces();
    }

    private void chargerListeAnnonces(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Chargement des données...");
        progressDialog.show();

        StringRequest sRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("response");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject o = jsonArray.getJSONObject(i);
                                JSONArray img = o.getJSONArray("images");
                                ArrayList<String> image = new ArrayList<>();
                                //creation de l'arraylist d'images
                                for (int k = 0; k < img.length(); k++)
                                 image.add(img.get(k).toString());
                                String date = getDate(o.get("date").toString());
                                Annonce annonce = new Annonce(o.get("id").toString(), o.get("titre").toString(), o.get("description").toString(),
                                        o.get("prix").toString(), o.get("pseudo").toString(), o.get("emailContact").toString(),
                                        o.get("telContact").toString(), o.get("ville").toString(), o.get("cp").toString(),
                                        image, date);
                                annonceList.add(annonce);

                                Log.d("debug liste get postal",annonceList.get(0).getCodePostal());
                            }
                            Log.d("debug liste",annonceList.toString());
                            adapter = new AnnonceAdapter(annonceList, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (Exception e) {
                            Toast.makeText(ListeAnnonceActivity.this, "EXCEPTION", Toast.LENGTH_SHORT).show();
                            Log.d("Exception de liste","Exception");
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ListeAnnonceActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sRequest);
    }

    public static String getDate(String time_stamp){

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(Long.parseLong(time_stamp));
        String date = android.text.format.DateFormat.format("dd-MM-yyyy",cal).toString();
        return date;
    }


}
