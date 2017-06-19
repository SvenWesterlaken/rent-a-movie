package com.example.svenwesterlaken.rentamovie.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.InventoryRequest;
import com.example.svenwesterlaken.rentamovie.domain.Inventory;
import com.example.svenwesterlaken.rentamovie.domain.Movie;
import com.example.svenwesterlaken.rentamovie.domain.Rental;
import com.example.svenwesterlaken.rentamovie.logic.InventoryListAdapter;
import com.example.svenwesterlaken.rentamovie.logic.MovieListAdapter;
import com.example.svenwesterlaken.rentamovie.util.Message;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class DetailedMovieActivity extends AppCompatActivity implements InventoryRequest.InventoryRequestListener {
    private List<Inventory> inventories;
    private InventoryListAdapter inventoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        Bundle extras = getIntent().getExtras();
        Movie movie = extras.getParcelable("movie");
        RecyclerView inventoryList = (RecyclerView) findViewById(R.id.Inventory_RV_content);

        TextView title = (TextView) findViewById(R.id.movie_TV_title);
        TextView rating = (TextView) findViewById(R.id.ActivityDetailedMovie_TV_rating);
        TextView release = (TextView) findViewById(R.id.ActivityDetailedMovie_TV_release);

        assert movie != null;
        title.setText(movie.getTitle());
        rating.setText(movie.getRating());
        release.setText(movie.getReleaseYear());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        inventoryList.setLayoutManager(layoutManager);
        inventories = new ArrayList<>();
        getInventories();
        inventoryAdapter = new InventoryListAdapter(inventories);
        inventoryList.setAdapter(inventoryAdapter);






    }

    private void getInventories() {

        InventoryRequest request = new InventoryRequest(getApplicationContext(), this);
        request.handleGetAllInventories();
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

}
