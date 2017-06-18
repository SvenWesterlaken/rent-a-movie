package com.example.svenwesterlaken.rentamovie.logic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Movie;
import com.example.svenwesterlaken.rentamovie.presentation.DetailedMovieActivity;

import java.util.List;

/**
 * Created by Sven Westerlaken on 18-6-2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MovieListAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context=  context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_list, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailedMovieActivity.class);
                i.putExtra("movie", movie);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private View view;

        public MovieViewHolder(View v) {
            super(v);
            this.view = v;
            this.title = (TextView) v.findViewById(R.id.movies_TV_title);
        }
    }
}


