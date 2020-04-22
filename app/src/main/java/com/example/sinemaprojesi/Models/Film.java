package com.example.sinemaprojesi.Models;

import android.media.Image;

public class Film {

    public String filmImage_id;
    public String film_name;
    public String director_name;
    public String producer_name;
    public String releaseDate;
    public String[] movie_cast;
    String film_id;

    public Film(String film_id, String filmImage_id, String film_name, String director_name, String producer_name, String releaseDate, String[] movie_cast) {
        this.film_id = film_id;
        this.filmImage_id = filmImage_id;
        this.film_name = film_name;
        this.director_name = director_name;
        this.producer_name = producer_name;
        this.releaseDate = releaseDate;
        this.movie_cast = movie_cast;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public String getFilmImage_id() {
        return filmImage_id;
    }

    public void setFilmImage_id(String filmImage_id) {
        this.filmImage_id = filmImage_id;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public String getProducer_name() {
        return producer_name;
    }

    public void setProducer_name(String producer_name) {
        this.producer_name = producer_name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String[] getMovie_cast() {
        return movie_cast;
    }

    public void setMovie_cast(String[] movie_cast) {
        this.movie_cast = movie_cast;
    }
}
