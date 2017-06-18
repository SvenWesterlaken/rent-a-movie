package com.example.svenwesterlaken.rentamovie.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.svenwesterlaken.rentamovie.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        setFragment(1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                setFragment(0);
                return true;
            case R.id.navigation_dashboard:
                setFragment(1);
                return true;
            case R.id.navigation_notifications:
                setFragment(2);
                return true;
            default:
                return false;
        }
    }

    private void setFragment(int position) {
        Fragment newFragment;

        if(position == 0) {
            newFragment = new RentalFragment();
        } else if (position == 1) {
            newFragment = new MovieFragment();
        } else if (position == 2) {
            newFragment = new MovieFragment();
        } else {
            newFragment = null;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_FL_content, newFragment).commit();
    }
}
