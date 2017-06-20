package com.example.svenwesterlaken.rentamovie.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.InventoryRequest;
import com.example.svenwesterlaken.rentamovie.api.RentalAddRequest;
import com.example.svenwesterlaken.rentamovie.domain.Inventory;
import com.example.svenwesterlaken.rentamovie.domain.Movie;
import com.example.svenwesterlaken.rentamovie.logic.InventoryListAdapter;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;
import com.example.svenwesterlaken.rentamovie.util.Message;

import java.util.ArrayList;
import java.util.List;

public class DetailedMovieActivity extends AppCompatActivity implements InventoryRequest.InventoryRequestListener, RentalAddRequest.RentalAddListener {
    private List<Inventory> inventories;
    private InventoryListAdapter inventoryAdapter;
    private Movie movie;
    private RecyclerView inventoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        Bundle extras = getIntent().getExtras();
        movie = extras.getParcelable("movie");
        TextView title = (TextView) findViewById(R.id.movie_TV_title);
        TextView rating = (TextView) findViewById(R.id.ActivityDetailedMovie_TV_rating);
        TextView release = (TextView) findViewById(R.id.ActivityDetailedMovie_TV_release);

        if(movie != null) {
            title.setText(movie.getTitle());
            rating.setText(movie.getRating());
            release.setText(movie.getReleaseYear());
        }

        if(LoginUtil.isGuest()) {
            onInventoryErrors("Inventory not available for guests");
        } else {
            inventoryList = (RecyclerView) findViewById(R.id.movie_RV_content);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            inventoryList.setLayoutManager(layoutManager);
            inventories = new ArrayList<>();
            assert movie != null;
            getCopies(movie.getId());

            inventoryAdapter = new InventoryListAdapter(inventories, getApplicationContext(), this, this);
            inventoryList.setAdapter(inventoryAdapter);
        }
    }

    private void getCopies(int movieID) {
        InventoryRequest request = new InventoryRequest(getApplicationContext(), this);
        request.handleGetAllInventories(movieID);
    }


    @Override
    public void onInventoriesAvailable(List<Inventory> inventories) {
        this.inventories.addAll(this.inventories.size(), inventories);
        inventoryAdapter.notifyItemRangeInserted(this.inventories.size(), inventories.size());
    }

    @Override
    public void onInventoryErrors(String message) {
        Message.display(getApplicationContext(), message);
    }

    @Override
    public void onRentalAdded() {
        getCopies(movie.getId());
        inventoryList.setEnabled(true);
        Message.display(this, "Rental added");
    }

    @Override
    public void onErrors(String message) {
        Message.display(this, message);
    }
}
