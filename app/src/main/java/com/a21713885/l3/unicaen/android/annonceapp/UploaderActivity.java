package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import static android.R.string.ok;

public class UploaderActivity extends AppCompatActivity {
    private  static final  int RESULT_LOAD_IMG=1;
    private Button camera, gallery, ajouter;
    private ImageView image;
    private  static final MediaType MEDIA_TYPE_IMG = MediaType.parse("image/jpeg");
    private  File fichier;
    private Annonce annonce;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private Uri fichierUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);
        image = (ImageView)findViewById(R.id.uploaded_img);
        gallery=(Button)findViewById(R.id.upload_gal);
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

                    Toast.makeText(UploaderActivity.this,"dans le try",Toast.LENGTH_SHORT).show();
                    Toast.makeText(UploaderActivity.this,"fichier "+ fichier.getPath(),Toast.LENGTH_SHORT).show();
                    if(fichier != null)
                        uploaderImage(fichier,"image.jpg", annonce.getId());
                    else{
                        fichier = new File(fichierUri.getPath());
                        uploaderImage(fichier,"image.jpg", annonce.getId());
                    }

                }catch (Exception e){
                    System.out.println("------------- ");e.printStackTrace();
                    Toast.makeText(UploaderActivity.this,"dans le catch",Toast.LENGTH_SHORT).show();
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

    public void openGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpeg");
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }
    protected void onActivityResult(int resquestCode, int resultCode, Intent data){
        super.onActivityResult(resquestCode,resultCode,data);
        //si l'image est recupérée
        if(resquestCode == RESULT_LOAD_IMG && resultCode==RESULT_OK && data!=null){
            try{

                //recuperer l'image a partir de data
                Uri selectedImage = data.getData();
                Toast.makeText(this,"URI"+selectedImage.toString(),Toast.LENGTH_SHORT).show();
                Log.d("------Image--------", "URI"+selectedImage.toString());
                System.out.println("URI"+selectedImage.toString());
                String[] filePath = {MediaStore.Images.Media.DATA};
                //recuperer le curseur
                Cursor cursor = getContentResolver().query(selectedImage,
                filePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);

                String imgDecodableString = cursor.getString(columnIndex);
                Toast.makeText(this,"imageDecodable"+imgDecodableString,Toast.LENGTH_SHORT).show();
                Log.d("------Image--------", "imageDecodable"+imgDecodableString);
                System.out.println("imageDecodable"+imgDecodableString);
                fichier = new File(imgDecodableString);
                cursor.close();
                Log.d("------Image--------", "Chemin fichier"+fichier.getPath());
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
            Log.d("------Image--------", "Erreur de chargement de fichier"+fichier.getPath());
            Toast.makeText(this,"aucune image choisie",Toast.LENGTH_SHORT).show();
        }

    }

    public void uploaderImage(File image, String name, String id) throws IOException{
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("apikey","21713885")
                .addFormDataPart("method","addImage")
                .addFormDataPart("id",id)
                .addFormDataPart("photo",name,RequestBody.create(MEDIA_TYPE_IMG,image))
                .build();
        Toast.makeText(UploaderActivity.this, id, Toast.LENGTH_SHORT).show();
        Request  request = new Request.Builder()
                .url("https://ensweb.users.info.unicaen.fr/android-api/")
            .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println( "Erreur ajout imag"+e.getMessage());
                    e.printStackTrace();

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println( "valeur de response " +response.body().string());
                    //test.setText(response.toString());
                }
        });

    }
}
