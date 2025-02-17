package com.example.flexdaily.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.flexdaily.fragment.MuscleTypeFragment;
import com.example.flexdaily.fragment.WorkoutTypeFragment;

public class HomePageAdapter extends FragmentPagerAdapter {
    public HomePageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WorkoutTypeFragment();
            case 1:
                return new MuscleTypeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Workout";
            case 1:
                return "Muscle";
            default:
                return null;
        }
    }
}
