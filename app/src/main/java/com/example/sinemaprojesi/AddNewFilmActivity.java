package com.example.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.example.sinemaprojesi.GetInfoFromDatabaseActivity.action_movies;


public class AddNewFilmActivity extends AppCompatActivity {

    Button image_upload_button;
    private static final int galleryPick = 1;
    ImageView new_film_image_view;
    private StorageReference filmImageRef;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button add_film_button;
    EditText film_name_et, film_director_et;
    String downloadedUrl;
    String film_id = "";
    String name, director, image_id;
    TextView title_add_or_edit_tv;
    String edit_or_add, empty_film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_film);

        image_upload_button = findViewById(R.id.image_upload_button);
        new_film_image_view = findViewById(R.id.new_film_image_view);
        filmImageRef = FirebaseStorage.getInstance().getReference().child("Film Images");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        add_film_button = findViewById(R.id.add_film_button);
        film_name_et = findViewById(R.id.film_name_et);
        film_director_et = findViewById(R.id.film_director_et);
        title_add_or_edit_tv = findViewById(R.id.title_add_or_edit_tv);

        empty_film = "https://firebasestorage.googleapis.com/v0/b/sinemaprojesi-8681b.appspot.com/o/Film%20Images%2Fempty_film.jpg?alt=media&token=ba9d832a-241b-422c-89b6-ef9f01dcf073";

        Bundle bundle = getIntent().getExtras();
        if(getIntent() != null && bundle != null && bundle.getString("name") != null && bundle.getString("edit_or_add") != null){
            edit_or_add = bundle.getString("edit_or_add");
            title_add_or_edit_tv.setText("Film Düzenleme Ekranı");
            add_film_button.setText("Filmi Düzenle");

            name = bundle.getString("name");
            director = bundle.getString("director");
            image_id = bundle.getString("imageid");
            film_id = bundle.getString("film_id");

            film_name_et.setText(name);
            film_director_et.setText(director);
            Picasso.with(AddNewFilmActivity.this).load(image_id).into(new_film_image_view);

        } else{
            for(int i = 0; i < 10; i++){
                int a = (int)(Math.random()*10);
                film_id += String.valueOf(a);
            }

            Picasso.with(AddNewFilmActivity.this).load(empty_film).into(new_film_image_view);
        }


        image_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();

                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, galleryPick);
            }
        });



        add_film_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!film_name_et.getText().toString().isEmpty() &&
                        !film_director_et.getText().toString().isEmpty()){

                        reference = database.getReference("Films/"+film_id+"/name");
                        reference.setValue(film_name_et.getText().toString());
                        reference = database.getReference("Films/"+film_id+"/director");
                        reference.setValue(film_director_et.getText().toString());
                        reference = database.getReference("Films/"+film_id+"/image");

                        if(!edit_or_add.equals("1") && downloadedUrl == null) reference.setValue(empty_film);
                        else{
                            if(edit_or_add.equals("1")) reference.setValue(image_id);
                            else reference.setValue(downloadedUrl);
                        }

                        action_movies.clear();

                        Intent intent = new Intent(AddNewFilmActivity.this, MainActivity.class);

                        startActivity(intent);

                }
                else{
                    Toast.makeText(AddNewFilmActivity.this, "Tüm alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();

            final StorageReference filePath = filmImageRef.child(film_id + ".jpg");

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadedUrl = uri.toString();

                            Picasso.with(AddNewFilmActivity.this).load(downloadedUrl).into(new_film_image_view);
                        }
                    });

                    Toast.makeText(getApplicationContext(), "Resim başarıyla yüklendi!", Toast.LENGTH_SHORT).show();
                }
            });





/*

            filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Resim başarıyla yüklendi!", Toast.LENGTH_SHORT).show();

                        downloadedUrl = task.getResult().getStorage().getDownloadUrl().toString();
                        Log.i("bakis",downloadedUrl);
                        //downloadedUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString(); //YANLIŞ, getStorage olacak.

                        Picasso.with(AddNewFilmActivity.this).load(downloadedUrl).into(new_film_image_view);

                    } else{
                        String message = task.getException().toString();
                        Toast.makeText(getApplicationContext(), "Hata! : " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
*/


        }


    }
}
