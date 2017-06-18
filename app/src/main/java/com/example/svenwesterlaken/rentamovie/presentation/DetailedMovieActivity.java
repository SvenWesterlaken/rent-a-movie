package com.example.svenwesterlaken.rentamovie.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Movie;

public class DetailedMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        Bundle extras = getIntent().getExtras();
        Movie movie = extras.getParcelable("movie");

        TextView title = (TextView) findViewById(R.id.movie_TV_title);
        assert movie != null;
        title.setText(movie.getTitle());
    }
}
