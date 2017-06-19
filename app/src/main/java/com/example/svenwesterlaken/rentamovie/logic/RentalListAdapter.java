package com.example.svenwesterlaken.rentamovie.logic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Rental;
import com.example.svenwesterlaken.rentamovie.presentation.RentalDetailedActivity;

import java.util.List;

/**
 * Created by Sven Westerlaken on 18-6-2017.
 */

public class RentalListAdapter extends RecyclerView.Adapter<RentalListAdapter.RentalViewHolder> {

    private List<Rental> rentals;
    private Context context;

    public RentalListAdapter(List<Rental> rentals, Context context) {
        this.rentals = rentals;
        this.context=  context;
    }

    @Override
    public RentalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rental_list, parent, false);
        return new RentalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RentalViewHolder holder, int position) {
        final Rental rental = rentals.get(position);
        holder.title.setText(rental.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RentalDetailedActivity.class);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return rentals.size();
    }

    public static class RentalViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView available;
        private View view;

        public RentalViewHolder(View v) {
            super(v);
            this.view = v;
            this.title = (TextView) v.findViewById(R.id.rentals_TV_title);
            this.available = (TextView) v.findViewById(R.id.rentals_TV_available);
        }
    }
}


