package com.a21713885.l3.unicaen.android.annonceapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Manifest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploaderActivity extends AppCompatActivity {
    private  static final MediaType MEDIA_TYPE_IMG = MediaType.parse("image/jpeg");
    private static final int RESULT_LOAD_IMG=1;
    private Button camera, gallery, ajouter, terminer;
    private ImageView image;
    private  File fichier;
    private Annonce annonce;
    private Uri fichierUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarU));
        image = (ImageView)findViewById(R.id.uploaded_img);
        gallery=(Button)findViewById(R.id.upload_gal);
        terminer = (Button) findViewById(R.id.finish_upload);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });
        annonce = getIntent().getExtras().getParcelable("Annonce");
        ajouter = (Button) findViewById(R.id.add_image);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {

                    if(fichier != null)
                    {
                        uploaderImage(fichier,"image.jpg", annonce.getId());
                    }
                    else{
                        fichier = new File(fichierUri.getPath());
                        uploaderImage(fichier,"image.jpg", annonce.getId());
                    }

                }catch (Exception e){
                    System.out.println("------------- ");e.printStackTrace();
                }
            }
        });
        camera = (Button) findViewById(R.id.upload_cam);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            camera.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    try {
                        // creation d'un fichier avec nom unique
                        fichier = recupererFichierImage();
                    }catch (IOException e){
                        System.out.println("Erreur lors de la recuperation de l'image "+e.getMessage());
                    }
                }

                if (fichier != null){
                    fichierUri = Uri.fromFile(fichier);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,fichierUri);
                    startActivityForResult(intent, 100);
                }
            }
        });

        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToVoirAnnonce();
            }
        });
    }

    void goToVoirAnnonce(){
        Intent intent = new Intent(this, VoirAnnonceActivity.class);

        intent.putExtra("Annonce",annonce);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                camera.setEnabled(true);
            }
        }
    }

    //Recuperation de l'images
    public File recupererFichierImage() throws IOException{
        File repertoire = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AnnoncesPhoto");

        if (!repertoire.exists()){
            if (!repertoire.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return new File(repertoire.getPath() + File.separator + "IMG_" + timeStamp + ".jpeg");
    }

    //ouverture de la gallery
    public void openGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }


    // callback declanché par la methode startActivityForResult pour renvoyer l'image créée ou chargée
    protected void onActivityResult(int resquestCode, int resultCode, Intent data){
        super.onActivityResult(resquestCode,resultCode,data);
        //si l'image est recupérée
        if(resquestCode == RESULT_LOAD_IMG && resultCode==RESULT_OK && data!=null){
            try{

                //recuperer l'image a partir de data
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                //recuperer le curseur
                Cursor cursor = getContentResolver().query(selectedImage,
                filePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);

                String imgDecodableString = cursor.getString(columnIndex);
                fichier = new File(imgDecodableString);
                cursor.close();
                image.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            }
            catch (Exception e)
            {
                System.out.println("ERREUR de recuperation d'image");
                Log.d("------Image--------", "Erreur de chargement de fichier"+fichier.getPath());
                Toast.makeText(this,"ERREUR de recuperation d'image",Toast.LENGTH_SHORT).show();
            }
        }
        else if (resquestCode == 100){
            if(resultCode == RESULT_OK){
                image.setImageURI(fichierUri);
            }
        }
        else {
            System.out.println("aucune image choisie");
            Toast.makeText(this,"aucune image choisie",Toast.LENGTH_SHORT).show();
        }

    }

    // methode pour envoyer une image à l'API
    public void uploaderImage(File image, String name, String id) throws IOException{
        // Instanciation de l'objet OkHttp pour l'envoie
        OkHttpClient client = new OkHttpClient();

        //construction du corps de la requete à partir de l'objet RequestBody
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("apikey","21712875")
                .addFormDataPart("method","addImage")
                .addFormDataPart("id",id)
                .addFormDataPart("photo",name,RequestBody.create(MEDIA_TYPE_IMG,image))
                .build();

        //construction de la requete à partir de l'objet Request
        Request  request = new Request.Builder()
                .url("https://ensweb.users.info.unicaen.fr/android-api/")
                .post(requestBody)
                .build();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Ajout de l'image en cours...");
        //Afficher le loader
        progressDialog.show();
        // callback de la requete
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println( "Erreur ajout imag"+e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();

                //System.out.println( "valeur de response " +response.body().string());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    JSONObject o = (JSONObject)jsonObject.get("response");
                    System.out.println(o.toString());
                    ArrayList<String> image = new ArrayList<>();
                    //creation de l'arraylist d'images
                    JSONArray img = (JSONArray)o.get("images") ;
                    System.out.println("images"+img);
                    if (img.length()!=0)
                    {
                        for (int k = 0; k < img.length(); k++)
                            image.add(img.get(k).toString());
                    }
                    String date = ListeAnnonceActivity.getDate(o.get("date").toString());
                    annonce = new Annonce(o.get("id").toString(), o.get("titre").toString(), o.get("description").toString(),
                            o.get("prix").toString(), o.get("pseudo").toString(), o.get("emailContact").toString(),
                            o.get("telContact").toString(), o.get("ville").toString(), o.get("cp").toString(),
                            image, date);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuListner menuListner = new MenuListner(item);
        menuListner.action(new View(UploaderActivity.this));
        return super.onOptionsItemSelected(item);
    }
}
