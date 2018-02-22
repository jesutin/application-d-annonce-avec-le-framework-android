package com.a21713885.l3.unicaen.android.annonceapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploaderActivity extends AppCompatActivity {
    private  static final  int RESULT_LOAD_IMG=1;
    private Button camera, gallery, ajouter;
    private  static final MediaType MEDIA_TYPE_IMG = MediaType.parse("image/jpg");
    private  File fichier;
    private Annonce annonce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);
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
                    Toast.makeText(UploaderActivity.this,"dans le try "+annonce.getId(),Toast.LENGTH_SHORT).show();

                    uploaderImage(fichier,"image.jpg", annonce.getId());

                }catch (Exception e){
                    System.out.println("------------- ");e.printStackTrace();
                    Toast.makeText(UploaderActivity.this,"dans le catch",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images
        .Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }
    protected void onActivityResult(int resquestCode, int resultCode, Intent data){
        super.onActivityResult(resquestCode,resultCode,data);
        try{
            //si l'image est recupérée
            if(resquestCode == RESULT_LOAD_IMG && resultCode==RESULT_OK && data!=null){
                //recuperer l'image a partir de data
                Uri selectedImage = data.getData();
                Toast.makeText(this,"URI"+selectedImage.toString(),Toast.LENGTH_SHORT).show();
                System.out.println("URI"+selectedImage.toString());
                String[] filePath = {MediaStore.Images.Media.DATA};
            //recuperer le curseur
                Cursor cursor = getContentResolver().query(selectedImage,
                filePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);

                String imgDecodableString = cursor.getString(columnIndex);
                Toast.makeText(this,"imageDecodable"+imgDecodableString,Toast.LENGTH_SHORT).show();
                System.out.println("imageDecodable"+imgDecodableString);
                fichier = new File(imgDecodableString);
                cursor.close();
                ImageView imageView = (ImageView)findViewById(R.id.uploaded_img);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            }
            else{
                System.out.println("aucune image choisie");
                Toast.makeText(this,"aucune image choisie",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            System.out.println("ERREUR de recuperation d'image");
            Toast.makeText(this,"ERREUR de recuperation d'image",Toast.LENGTH_SHORT).show();
        }
    }

    public void uploaderImage(File image, String name, String id) throws IOException{
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("apikey","21713885")
                .addFormDataPart("method","addImage")
                .addFormDataPart("id",id)
                .addFormDataPart("file",name,RequestBody.create(MEDIA_TYPE_IMG,image))
                .build();
        Toast.makeText(UploaderActivity.this, id, Toast.LENGTH_SHORT).show();
        Request  request = new Request.Builder()
                .url("https://ensweb.users.info.unicaen.fr/android-api/")
                .post(requestBody).build();
         client.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 e.printStackTrace();
             }

             @Override
             public void onResponse(Call call, Response response){
                //Toast.makeText(UploaderActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
             System.out.println(response.body().toString());
             }
         });
        /*if(!response.isSuccessful()){
            System.out.println( "Erreur ajout imag");
            Toast.makeText(this,"Erreur ajout image",Toast.LENGTH_SHORT).show();
        }
        else
        {
            System.out.println( "valeur de response" +response.toString());
        }*/

    }
}
