package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ListeAnnonceActivity extends AppCompatActivity{
    private  RecyclerView recyclerView;
    private List<Annonce> annonceList;
    private final String url = "https://ensweb.users.info.unicaen.fr/android-api/?apikey=21712875&method=listAll";
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_annonce);

        //ajout le toolbar à l'activity ListAnnonceActivity
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_liste));

        this.recyclerView = (RecyclerView) findViewById(R.id.liste_annonces);
        this.recyclerView.setHasFixedSize(true);

        //modifie le layout manager du recycler en lui donnant un linearLayoutManager
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.annonceList = new ArrayList<>();

        chargerListeAnnonces();
    }

    private void chargerListeAnnonces(){
        // On créé un loader qui va s'executer jusqu'à ce la requete http ce termine
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Chargement des données...");
        //Afficher le loader
        progressDialog.show();

        // On utilise volley pour recuperer les annonces sur l'api
        //on declare un objet StringRequest pour construire la requete
        StringRequest sRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                //Dès la fin d'execution de la requete , on arrete le loader
                progressDialog.dismiss();
                try {
                        //creation d'un json object a partir de la reponse de l'api
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("response");

                        //parcour du json array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);
                            JSONArray img = o.getJSONArray("images");
                            ArrayList<String> image = new ArrayList<>();
                            //creation de l'arraylist d'images
                            for (int k = 0; k < img.length(); k++)
                                image.add(img.get(k).toString());

                            //formatage de la date
                            String date = getDate(o.get("date").toString());

                            //creation d'un annonce et son ajout dans la liste des annonces
                            Annonce annonce = new Annonce(o.get("id").toString(), o.get("titre").toString(), o.get("description").toString(),
                                    o.get("prix").toString(), o.get("pseudo").toString(), o.get("emailContact").toString(),
                                    o.get("telContact").toString(), o.get("ville").toString(), o.get("cp").toString(),
                                    image, date);
                            annonceList.add(annonce);

                        }
                        adapter = new AnnonceAdapter(annonceList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                    catch (Exception e)
                    {
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

    //methode pour formater le timestamp en format date dd-MM-yyyy
    public static String getDate(String time_stamp){

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(Long.parseLong(time_stamp));
        String date = android.text.format.DateFormat.format("dd-MM-yyyy",cal).toString();
        return date;
    }

    // ajout du menu à la toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ajout des evenements au items du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Creation d' une instance de MenuListener et appel de sa methode action
        MenuListner menuListner = new MenuListner(item);
        menuListner.action(new View(ListeAnnonceActivity.this));
        return super.onOptionsItemSelected(item);
    }


}
