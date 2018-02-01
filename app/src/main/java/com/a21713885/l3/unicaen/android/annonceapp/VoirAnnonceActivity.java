package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class VoirAnnonceActivity extends AppCompatActivity {
    private  JSONObject jo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_annonce);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connection(cm)){

            RequestQueue rq = Volley.newRequestQueue(this);
            String url  = "https://ensweb.users.info.unicaen.fr/android-api/mock-api/completeAdWithImages.json";
            //TextView desc = (TextView)findViewById(R.id.description);
            StringRequest sRequest=new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response.toString();

                            TextView desc = (TextView)findViewById(R.id.description),
                                    titre = (TextView)findViewById(R.id.Titre),
                                    pri = (TextView)findViewById(R.id.prix),
                                    code_p=(TextView)findViewById(R.id.codePostal),
                                    ville = (TextView)findViewById(R.id.ville),
                                    datepub= (TextView)findViewById(R.id.date_pub),
                                    name = (TextView)findViewById(R.id.nom),
                                    mail = (TextView)findViewById(R.id.mail),
                                    tel = (TextView)findViewById(R.id.telephone);
                                    ImageView iv = (ImageView)findViewById(R.id.image_annonce);

                            //desc.setText(response.toString());
                            try {
                                JSONObject jo = new JSONObject(response);
                                if (!jo.get("success").toString().equals("null"))
                                {
                                    Annonce a = new Annonce();

                                    JSONObject resp = new JSONObject(jo.get("response").toString());
                                    
                                    titre.setText(resp.get("titre").toString());
                                    pri.setText(resp.get("prix").toString());
                                    code_p.setText(resp.get("cp").toString());
                                    ville.setText(resp.get("ville").toString());
                                    desc.setText(resp.get("description").toString());
                                    datepub.setText(resp.get("date").toString());
                                    mail.setText(resp.get("emailContact").toString());
                                    name.setText(resp.get("pseudo").toString());
                                    tel.setText(resp.get("telContact").toString());
                                    JSONArray image =(JSONArray) resp.get("images");
                                    //Toast.makeText(VoirAnnonceActivity.this, image.get(0).toString(), Toast.LENGTH_SHORT).show();
                                    if(image.length()==0)
                                    {
                                        Picasso.with(VoirAnnonceActivity.this).
                                                load(R.drawable.feu).
                                                into(iv);
                                    }
                                    else
                                    {
                                        Picasso.with(VoirAnnonceActivity.this).
                                                load(image.get(1).toString()).
                                                into(iv);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(VoirAnnonceActivity.this, "echec de chargement des donnees", Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    TextView desc = (TextView)findViewById(R.id.description);
                    desc.setText("Erreur page non recuperer");
                }
            });
            rq.add(sRequest);

        }
        else
        {
            Toast.makeText(this, "ECHOUE!!!!", Toast.LENGTH_SHORT).show();
        }
    }


    public Boolean connection(ConnectivityManager cm){
        NetworkInfo actNet = cm.getActiveNetworkInfo();
        if (actNet!=null && actNet.isConnected()){
            Toast.makeText(this, actNet.getTypeName(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}