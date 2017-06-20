package com.example.svenwesterlaken.rentamovie.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.api.RentalExtendRequest;
import com.example.svenwesterlaken.rentamovie.api.RentalRemoveRequest;
import com.example.svenwesterlaken.rentamovie.domain.Rental;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;
import com.example.svenwesterlaken.rentamovie.util.Message;

public class RentalDetailedActivity extends AppCompatActivity implements View.OnClickListener, RentalExtendRequest.RentalExtendListener, RentalRemoveRequest.RentalRemoveListener {
    private Button extendBTN;
    private Button returnBTN;

    private TextView title;
    private TextView itemid;
    private TextView rentaldate;
    private TextView returndate;

    private Rental rental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_rental);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        rental = extras.getParcelable("rental");

        extendBTN = (Button) findViewById(R.id.rental_BTN_extend);
        returnBTN = (Button) findViewById(R.id.rental_BTN_return);

        extendBTN.setOnClickListener(this);
        returnBTN.setOnClickListener(this);

        title = (TextView) findViewById(R.id.rental_TV_title);
        itemid = (TextView) findViewById(R.id.rental_TV_inventoryid);
        rentaldate = (TextView) findViewById(R.id.rental_TV_rentdate);
        returndate = (TextView) findViewById(R.id.rental_TV_returndate);

        if(rental != null) {
            title.setText(rental.getTitle());
            itemid.setText("" + rental.getInventoryID());
            rentaldate.setText(rental.getRentalDateAPI());
            returndate.setText(rental.getReturnDateAPI());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rental_BTN_extend) {
            extendRental();
        } else if (v.getId() == R.id.rental_BTN_return) {
            removeRental();
        }
    }

    public void extendRental() {
        switchButtons(false);

        if(rental != null) {
            RentalExtendRequest request = new RentalExtendRequest(this, this);
            request.handleExtendRental(LoginUtil.getUserID(), rental.getInventoryID());
        }
    }

    public void removeRental() {
        switchButtons(false);

        if(rental != null) {
            RentalRemoveRequest request = new RentalRemoveRequest(this, this);
            request.handleRemoveRental(LoginUtil.getUserID(), rental.getInventoryID());
        }
    }

    public void switchButtons(boolean value) {
        extendBTN.setEnabled(value);
        returnBTN.setEnabled(value);
    }

    @Override
    public void onRentalExtended() {
        Message.display(this, "Rental successfully extended by 3 weeks");
    }

    @Override
    public void onErrors(String message) {
        Message.display(this, message);
    }

    @Override
    public void onRentalRemoved() {
        Message.display(this, "Copy successfully returned");
    }
}
