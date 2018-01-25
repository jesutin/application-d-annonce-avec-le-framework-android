package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VoirAnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_annonce);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connection(cm)){
            RequestQueue rq = Volley.newRequestQueue(this);
            String url  = "https://ensweb.users.info.unicaen.fr/android-api/mock-api/completeAd.json";
            //TextView desc = (TextView)findViewById(R.id.description);
            StringRequest sRequest=new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response.toString();
                            TextView desc = (TextView)findViewById(R.id.description);
                            //desc.setText(response.toString());
                            try {
                                JSONObject jo = new JSONObject(response);
                                //desc.setText(jo.get("success").toString());
                                JSONObject resp = new JSONObject(jo.get("response").toString());
                                desc.setText(resp.get("description").toString());
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

    public void getData(){

    }
}
