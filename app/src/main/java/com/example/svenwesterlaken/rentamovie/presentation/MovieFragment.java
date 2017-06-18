package com.example.svenwesterlaken.rentamovie.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.MoviesRequest;
import com.example.svenwesterlaken.rentamovie.domain.Movie;
import com.example.svenwesterlaken.rentamovie.logic.MovieListAdapter;
import com.example.svenwesterlaken.rentamovie.util.Message;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MovieFragment extends Fragment implements MoviesRequest.MoviesRequestListener {
    private List<Movie> movies;
    private MovieListAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        Context context = view.getContext();
        RecyclerView movieList = (RecyclerView) view.findViewById(R.id.movies_RV_content);
        movieList.setItemAnimator(new SlideInLeftAnimator());
        movieList.getItemAnimator().setAddDuration(300);
        movieList.getItemAnimator().setRemoveDuration(200);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieList.setLayoutManager(layoutManager);

        movies = new ArrayList<>();
        getMovies();

        movieAdapter = new MovieListAdapter(movies, context);
        movieList.setAdapter(movieAdapter);

        return view;
    }

    public void getMovies(){
        if(!movies.isEmpty()) {
            int size = movies.size();
            movies.clear();
            movieAdapter.notifyItemRangeRemoved(0, size);
        }

        MoviesRequest request = new MoviesRequest(getContext(), this);
        request.handleGetAllMovies();
    }

    @Override
    public void onMoviesAvailable(List<Movie> movies) {
        this.movies.addAll(this.movies.size(), movies);
        movieAdapter.notifyItemRangeInserted(this.movies.size(), movies.size());
    }

    @Override
    public void onMovieErrors(String message) {
        Message.display(getContext(), message);
    }
}
