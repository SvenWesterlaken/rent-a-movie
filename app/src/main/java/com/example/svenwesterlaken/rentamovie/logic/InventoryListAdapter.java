package com.example.svenwesterlaken.rentamovie.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.domain.Inventory;
import com.example.svenwesterlaken.rentamovie.domain.Movie;
import com.example.svenwesterlaken.rentamovie.domain.Rental;

import java.util.List;

/**
 * Created by Dajakah on 19-6-2017.
 */

public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.InventoryViewHolder> {
    private List<Inventory> inventories;


    public InventoryListAdapter(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View invenView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detailedmovie_list, parent, false);
        return new InventoryViewHolder(invenView);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        final Inventory inventory = inventories.get(position);
        holder.id.setText(String.valueOf(inventory.getInventoryID()));
        holder.user.setText(String.valueOf(inventory.getFilmID()));
//        holder.status.setText(rentals.get);

    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView status;
        private TextView user;
        private View view;
        public InventoryViewHolder(View v) {
            super(v);
            this.view = v;
            this.id = (TextView) v.findViewById(R.id.DMI_TV_title);
            this.user = (TextView) v.findViewById(R.id.DMI_TV_statusUser);
        }
    }

}
