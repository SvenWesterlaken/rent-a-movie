package com.example.svenwesterlaken.rentamovie.logic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.svenwesterlaken.rentamovie.presentation.MovieFragment;
import com.example.svenwesterlaken.rentamovie.presentation.RentalFragment;

/**
 * Created by Sven Westerlaken on 18-6-2017.
 */

public class MoviePagerAdapter extends FragmentPagerAdapter{

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MovieFragment();
            case 1:
                return new RentalFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return "Movies";
            case 1:
                return "Rentals";
            default:
                return null;
        }
    }



}
