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
import com.example.svenwesterlaken.rentamovie.api.RentalRequest;
import com.example.svenwesterlaken.rentamovie.domain.Rental;
import com.example.svenwesterlaken.rentamovie.logic.RentalListAdapter;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;
import com.example.svenwesterlaken.rentamovie.util.Message;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class RentalFragment extends Fragment implements RentalRequest.RentalRequestListener {
    private List<Rental> rentals;
    private RentalListAdapter rentalAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rental_list, container, false);

        if(LoginUtil.isGuest()) {
            onRentalsErrors("Not available for guests");
        } else {
            Context context = view.getContext();
            RecyclerView rentalList = (RecyclerView) view.findViewById(R.id.rentals_RV_content);
            rentalList.setItemAnimator(new SlideInRightAnimator());
            rentalList.getItemAnimator().setAddDuration(300);
            rentalList.getItemAnimator().setRemoveDuration(200);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rentalList.setLayoutManager(layoutManager);

            rentals = new ArrayList<>();
            getRentals();

            rentalAdapter = new RentalListAdapter(rentals, context);
            rentalList.setAdapter(rentalAdapter);
        }

        return view;
    }

    public void getRentals(){
        if(!rentals.isEmpty()) {
            int size = rentals.size();
            rentals.clear();
            rentalAdapter.notifyItemRangeRemoved(0, size);
        }

        RentalRequest request = new RentalRequest(getContext(), this);
        request.handleGetAllRentals();
    }

    @Override
    public void onRentalsAvailable(List<Rental> rentals) {
        this.rentals.addAll(this.rentals.size(), rentals);
        rentalAdapter.notifyItemRangeInserted(0, rentals.size());
    }

    @Override
    public void onRentalsErrors(String message) {
        Message.display(getContext(), message);
    }
}
