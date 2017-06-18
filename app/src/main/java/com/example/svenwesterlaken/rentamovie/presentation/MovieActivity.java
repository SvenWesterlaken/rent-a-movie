package com.example.svenwesterlaken.rentamovie.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.svenwesterlaken.rentamovie.Adapter.MovieListAdapter;
import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Movie;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {
    private ListView movieList;
    private MovieListAdapter mMovieListAdapter;
    private ArrayList<Movie> movieArrayListList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieList = (ListView) findViewById(R.id.activityMovie_LV_movieList);
        mMovieListAdapter = new MovieListAdapter(this,getLayoutInflater(),movieArrayListList);
        movieList.setAdapter(mMovieListAdapter);
    }
}
