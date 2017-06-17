package com.example.svenwesterlaken.rentamovie.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Movies;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by BlackWolf on 06/17/17.
 */

public class MovieListAdapter extends ArrayAdapter<Movies> {
    private Context context;

    public MovieListAdapter(Context context,ArrayList<Movies>movies) {
        super(context, R.layout.row_movie_list, movies);
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movies movies = (Movies) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_movie_list, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.activityMovie_TV_title);
        TextView rating = (TextView) convertView.findViewById(R.id.activityMovie_TV_rating);
        TextView releaseYear = (TextView) convertView.findViewById(R.id.activityMovie_TV_releaseYear);

        title.setText(movies.getTitle());
        rating.setText(movies.getRating());
        releaseYear.setText(movies.getRelease_year());

        return convertView;
    }


}
