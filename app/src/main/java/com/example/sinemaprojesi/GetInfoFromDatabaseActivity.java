package com.example.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.sinemaprojesi.Models.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.sinemaprojesi.Adapters.FilmsAdapter.selected_film_id;
import static com.example.sinemaprojesi.FirstLoginActivity.state;

public class GetInfoFromDatabaseActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    public static List<Film> action_movies;
    public static int seat_integers[] = new int[200];
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_from_database);

        database = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        action_movies = new ArrayList<>();

        if(user == null){
            Intent intent = new Intent(GetInfoFromDatabaseActivity.this, FirstLoginActivity.class);

            startActivity(intent);
        }

        /*
        reference = database.getReference("Films/"+selected_film_id+"/Tickets");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        String seat_information = childDataSnapshot.getValue().toString();

                        String seat_info_array[] = seat_information.split("/");

                            int session = Integer.valueOf(seat_info_array[0]);
                            int saloon = Integer.valueOf(seat_info_array[1]);
                            int row = Integer.valueOf(seat_info_array[2]);
                            int column = Integer.valueOf(seat_info_array[3]);

                            seat_integers[i++] = row*20 + column;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
        reference = database.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                state = Integer.valueOf(dataSnapshot.child(user.getUid()).child("state").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reference = database.getReference("Films");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*if(dataSnapshot.exists()){
                    Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                    List<Object> values = new ArrayList<>(td.values());

                    Log.i("bakım",values.get(0).toString());


                    //notifyDataSetChanged();
                } */

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if(childDataSnapshot.child("name").getValue() != null && childDataSnapshot.child("director").getValue() != null
                    && childDataSnapshot.child("image").getValue() != null){
                        String film_id = childDataSnapshot.getKey();

                        String name = childDataSnapshot.child("name").getValue().toString();
                        Log.i("bakis","name: "+name);
                        String director = childDataSnapshot.child("director").getValue().toString(); //Yeni eklenenin director'ını okuyamıyor?
                        Log.i("bakis","director: "+director);
                        String image = childDataSnapshot.child("image").getValue().toString();

                        Film film = new Film(film_id, image, name, director,"A","5/5/2020",new String[]{"A","B"});

                        //Log.i("bakis",""+ childDataSnapshot.getKey()); //displays the key for the node
                        //Log.i("bakis",""+ childDataSnapshot.child("Films").getValue());   //gives the value for given keyname

                        int search = 0;
                        for(Film a_film : action_movies){
                            if(a_film.film_name.equals(name) && a_film.director_name.equals(director)){
                                search = 1;
                                break;
                            }
                        }

                        if(search == 0) action_movies.add(film);
                    }

                }

                //for(int i = 0; i  < 3; i++)
                //Log.i("bakis", action_movies.get(i).getFilm_name());

                Intent intent = new Intent(GetInfoFromDatabaseActivity.this, MainActivity.class);

                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }
}
