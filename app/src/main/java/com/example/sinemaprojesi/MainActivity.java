package com.example.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.sinemaprojesi.Adapters.FilmsAdapter;
import com.example.sinemaprojesi.Models.Film;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sinemaprojesi.FirstLoginActivity.state;
import  static com.example.sinemaprojesi.GetInfoFromDatabaseActivity.action_movies;

public class MainActivity extends AppCompatActivity {

    LinearLayout filmGenresLayout;
    ScrollView films_scroll;
    RecyclerView actionMoviesRecView, kidsMoviesRecView, loveMoviesRecView;
    List<List<Film>> filmGenresList;
    //List<Film> action_movies;
    List<Film> kids_movies;
    List<Film> love_movies;
    Button add_new_film_button;
    Button budget_button;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference reference;
    FirebaseDatabase database;
    LinearLayout admin_panel_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Films");

        //for(int i = 0; i  < 3; i++)
          //  Log.i("bakis", "main "+action_movies.get(i).getFilm_name());

       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*if(dataSnapshot.exists()){
                    Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                    List<Object> values = new ArrayList<>(td.values());

                    Log.i("bakım",values.get(0).toString());


                    //notifyDataSetChanged();
                }



                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String name = childDataSnapshot.child("name").getValue().toString();
                    String director = childDataSnapshot.child("director").getValue().toString();
                    String image = childDataSnapshot.child("image").getValue().toString();

                    Film film = new Film(image, name, director,"A","5/5/2020",new String[]{"A","B"});

                    //Log.i("bakis",""+ childDataSnapshot.getKey()); //displays the key for the node
                    //Log.i("bakis",""+ childDataSnapshot.child("Films").getValue());   //gives the value for given keyname

                    action_movies.add(film);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        }); */


        //check();

        define();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m1 = getMenuInflater();
        m1.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.logout_button);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, FirstLoginActivity.class);
                startActivity(intent);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public void define(){
        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        /*
        database = FirebaseDatabase.getInstance();
        Log.i("bakım", "auth uid : "+auth.getUid());
        reference = database.getReference(auth.getUid() + "/state");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null && dataSnapshot.getValue() != null){
                    state = Integer.parseInt(dataSnapshot.getValue().toString());
                    Log.i("bakım","state2: "+state);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("bakım","Bilgi alınamadı!");
            }
        });
        Log.i("bakım","state: "+state); */

        admin_panel_layout = findViewById(R.id.admin_panel_layout);
        add_new_film_button = findViewById(R.id.add_new_film_button);
        budget_button = findViewById(R.id.budget_button);

        if(state == 0) admin_panel_layout.setVisibility(View.INVISIBLE);
        //TODO if(state == 0)

        add_new_film_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewFilmActivity.class);

                startActivity(intent);
            }
        });

        budget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetBudgetInfoFromDatabase.class);

                startActivity(intent);
            }
        });



        filmGenresLayout = findViewById(R.id.filmGenresLayout);
        actionMoviesRecView = findViewById(R.id.actionMoviesRecView);
        kidsMoviesRecView = findViewById(R.id.kidsMoviesRecView);
        loveMoviesRecView = findViewById(R.id.loveMoviesRecView);
        films_scroll = findViewById(R.id.films_scroll);

        filmGenresList = new ArrayList<>();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this); layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        actionMoviesRecView.setLayoutManager(layoutManager1);
        filmGenresList.add(action_movies);
        FilmsAdapter f1_adapter = new FilmsAdapter(action_movies, getApplicationContext(), this);
        actionMoviesRecView.setAdapter(f1_adapter);

/*
        filmGenresList = new ArrayList<>();
        FilmContent fc = new FilmContent();

        action_movies = fc.getAction_movies(); //TODO burayı açmalı
        kids_movies = fc.getKids_movies();
        love_movies = fc.getLove_movies();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this); layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this); layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this); layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);

        actionMoviesRecView.setLayoutManager(layoutManager1);
        kidsMoviesRecView.setLayoutManager(layoutManager2);
        loveMoviesRecView.setLayoutManager(layoutManager3);

        //Log.i("firstlog",action_movies.get(0).getFilm_name());  DONE

        filmGenresList.add(action_movies);
        filmGenresList.add(kids_movies);
        filmGenresList.add(love_movies);

        FilmsAdapter f1_adapter = new FilmsAdapter(action_movies, getApplicationContext(), this);
        FilmsAdapter f2_adapter = new FilmsAdapter(kids_movies, getApplicationContext(), this);
        FilmsAdapter f3_adapter = new FilmsAdapter(love_movies, getApplicationContext(), this);

        actionMoviesRecView.setAdapter(f1_adapter);
        kidsMoviesRecView.setAdapter(f2_adapter);
        loveMoviesRecView.setAdapter(f3_adapter);
*/
    }

    public void check(){
        if(user == null){

            Intent intent = new Intent(MainActivity.this, FirstLoginActivity.class);

            startActivity(intent);

            finish();

        } else{

        }
    }



}
