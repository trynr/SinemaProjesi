package com.example.sinemaprojesi;

import com.example.sinemaprojesi.Models.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmContent {

    Film a_film = new Film(R.drawable.asd, "A","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film b_film = new Film(R.drawable.prayers_background, "B","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film q_film = new Film(R.drawable.prayers_background, "B","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film w_film = new Film(R.drawable.prayers_background, "B","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film r_film = new Film(R.drawable.prayers_background, "B","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film s_film = new Film(R.drawable.prayers_background, "B","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});

    Film c_film = new Film(R.drawable.asd,"C","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film d_film = new Film(R.drawable.asd,"D","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});

    Film e_film = new Film(R.drawable.asd,"E","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});
    Film f_film = new Film(R.drawable.asd,"F","BC","DE","10/10/2020", new String[]{"BP","AG","JJ","FR"});

    List<Film> action_movies = new ArrayList<>();
    List<Film> kids_movies = new ArrayList<>();
    List<Film> love_movies = new ArrayList<>();

    public FilmContent(){
        action_movies.add(a_film);
        action_movies.add(b_film);
        action_movies.add(q_film);
        action_movies.add(w_film);
        action_movies.add(r_film);
        action_movies.add(s_film);

        kids_movies.add(c_film);
        kids_movies.add(d_film);

        love_movies.add(e_film);
        love_movies.add(f_film);
    }


    public List<Film> getAction_movies() {
        return action_movies;
    }

    public void setAction_movies(List<Film> action_movies) {
        this.action_movies = action_movies;
    }

    public List<Film> getKids_movies() {
        return kids_movies;
    }

    public void setKids_movies(List<Film> kids_movies) {
        this.kids_movies = kids_movies;
    }

    public List<Film> getLove_movies() {
        return love_movies;
    }

    public void setLove_movies(List<Film> love_movies) {
        this.love_movies = love_movies;
    }
}
