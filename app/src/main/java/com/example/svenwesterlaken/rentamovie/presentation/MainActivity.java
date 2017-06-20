package com.example.svenwesterlaken.rentamovie.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.svenwesterlaken.rentamovie.R;
import com.example.svenwesterlaken.rentamovie.util.LoginUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MenuItem.OnMenuItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        setFragment(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_logout).setOnMenuItemClickListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_movies:
                setFragment(0);
                return true;
            case R.id.navigation_myrentals:
                setFragment(1);
                return true;
            default:
                return false;
        }
    }

    private void setFragment(int position) {
        Fragment newFragment;

        if(position == 0) {
            newFragment = new MovieFragment();
        } else if (position == 1) {
            newFragment = new RentalFragment();
        } else {
            newFragment = null;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_FL_content, newFragment).commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.action_logout) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            LoginUtil.removeUser();
            finish();
            return true;
        } else {
            return false;
        }
    }
}
