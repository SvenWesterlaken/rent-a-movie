package com.example.svenwesterlaken.rentamovie.logic;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.RentalAddRequest;
import com.example.svenwesterlaken.rentamovie.domain.Inventory;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;

import java.util.List;

/**
 * Created by Sven Westerlaken on 19-6-2017.
 */

public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.InventoryViewHolder> {
    private List<Inventory> inventories;
    private Context context;
    private RentalAddRequest.RentalAddListener listener;
    private Activity a;


    public InventoryListAdapter(List<Inventory> inventories, Context context, RentalAddRequest.RentalAddListener listener, Activity a) {
        this.inventories = inventories;
        this.context = context;
        this.listener = listener;
        this.a = a;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View invenView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detailedmovie_list, parent, false);
        return new InventoryViewHolder(invenView);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        final Inventory inventory = inventories.get(position);
        holder.id.setText(String.valueOf(inventory.getId()));
        holder.user.setText(String.valueOf(inventory.getFilmID()));
        if(inventory.isAvailable()) {
            holder.user.setText(R.string.inventory_available);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.findViewById(R.id.movie_RV_content).setEnabled(false);
                    RentalAddRequest request = new RentalAddRequest(context, listener);
                    request.handleAddRental(LoginUtil.getUserID(), inventory.getId());
                }
            });
        } else {
            holder.user.setText(R.string.inventory_unavailable);
        }

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
