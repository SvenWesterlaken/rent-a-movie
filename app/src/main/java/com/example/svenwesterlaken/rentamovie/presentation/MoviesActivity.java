package com.example.svenwesterlaken.rentamovie.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.svenwesterlaken.rentamovie.Adapter.MovieListAdapter;
import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.MoviesRequest;
import com.example.svenwesterlaken.rentamovie.domain.Movies;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements MoviesRequest.MoviesRequestListener {
    private ListView movieList;
    private MovieListAdapter mMovieListAdapter;
    private ArrayList<Movies> movieArrayList = new ArrayList<Movies>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieList = (ListView) findViewById(R.id.activityMovie_LV_movieList);
        mMovieListAdapter = new MovieListAdapter(getApplicationContext(),movieArrayList);
        movieList.setAdapter(mMovieListAdapter);
    }

    @Override
    public void onMoviesAvailable(ArrayList<Movies> film) {

    }

    @Override
    public void onMovieAvailable(Movies movie) {
        movieArrayList.add(movie);
        mMovieListAdapter.notifyDataSetChanged();


    }

    @Override
    public void onMovieErrors(String message) {

    }
}
