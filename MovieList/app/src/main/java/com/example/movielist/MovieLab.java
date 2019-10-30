package com.example.movielist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieLab {
    private static MovieLab sMovieLab;
    private List<Movie> mMovies;

    public static MovieLab get(Context context) {
        if (sMovieLab == null){
            sMovieLab = new MovieLab(context);
        }
        return sMovieLab;
    }
    private MovieLab(Context context){
        mMovies = new ArrayList<>();
        for (int i = 1; i<21; i++){
            Movie movie = new Movie();
            movie.setTitle("Movie #"+i);
            movie.setSolved(i%2 == 0);
            mMovies.add(movie);
        }
    }
    public List<Movie> getMovies(){
        return mMovies;
    }
    public Movie getMovie(UUID id){
        for (Movie movie : mMovies){
            if (movie.getId().equals(id)){
                return movie;
            }
        }
        return null;
    }
}
