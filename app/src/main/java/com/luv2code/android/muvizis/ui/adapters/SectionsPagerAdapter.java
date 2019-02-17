package com.luv2code.android.muvizis.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luv2code.android.muvizis.ui.fragments.SearchMovieFragment;
import com.luv2code.android.muvizis.ui.fragments.MyMovieListFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SearchMovieFragment.newInstance();
        } else if (position == 1) {
            return MyMovieListFragment.newInstance();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Search movie";
            case 1:
                return "My movie list";
        }
        return null;
    }
}