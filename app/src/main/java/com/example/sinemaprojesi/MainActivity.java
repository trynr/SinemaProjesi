package com.example.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.sinemaprojesi.Adapters.FilmsAdapter;
import com.example.sinemaprojesi.Models.Film;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout filmGenresLayout;
    ScrollView films_scroll;
    RecyclerView actionMoviesRecView, kidsMoviesRecView, loveMoviesRecView;
    List<List<Film>> filmGenresList;
    List<Film> action_movies;
    List<Film> kids_movies;
    List<Film> love_movies;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        define();

        check();


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

        filmGenresLayout = findViewById(R.id.filmGenresLayout);
        actionMoviesRecView = findViewById(R.id.actionMoviesRecView);
        kidsMoviesRecView = findViewById(R.id.kidsMoviesRecView);
        loveMoviesRecView = findViewById(R.id.loveMoviesRecView);
        films_scroll = findViewById(R.id.films_scroll);

        filmGenresList = new ArrayList<>();
        FilmContent fc = new FilmContent();

        action_movies = fc.getAction_movies();
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

    }

    public void check(){
        if(user == null){
            Intent intent = new Intent(MainActivity.this, AdminOrUserActivity.class);

            startActivity(intent);

            finish();

        } else{

        }
    }



}
