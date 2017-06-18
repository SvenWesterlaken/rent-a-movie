package com.example.svenwesterlaken.rentamovie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Movie;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by BlackWolf on 06/17/17.
 */

public class MovieListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    ArrayList movies;

    public MovieListAdapter(Context context, LayoutInflater inflater, ArrayList<Movie>movies) {
        this.context = context;
        this.inflater = inflater;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_movie_list, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.activityMovie_TV_title);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.activityMovie_TV_rating);
            viewHolder.releaseYear = (TextView) convertView.findViewById(R.id.activityMovie_TV_releaseYear);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = (Movie) movies.get(position);

        viewHolder.title.setText(movie.getTitle());
        viewHolder.rating.setText(movie.getRating());
        viewHolder.releaseYear.setText(movie.getRelease_year());

        return convertView;
    }

    private static class ViewHolder{
        TextView title, rating, releaseYear;
    }
}
