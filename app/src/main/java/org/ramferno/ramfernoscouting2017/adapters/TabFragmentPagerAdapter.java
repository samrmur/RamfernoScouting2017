package org.ramferno.ramfernoscouting2017.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.ramferno.ramfernoscouting2017.helpers.InstanceHelper;

// Start of TabFragmentPagerAdapter
@SuppressWarnings("FieldCanBeLocal")
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private InstanceHelper helper;

    public TabFragmentPagerAdapter(FragmentManager fm, InstanceHelper helper) {
        super(fm);
        this.helper = helper;
    } // End of constructor

    @Override
    public int getCount() {
        return PAGE_COUNT;
    } // End of getter method

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return helper.getFirstFragment();
            case 1:
                return helper.getSecondFragment();
            case 2:
                return helper.getThirdFragment();
            default:
                return null;
        } // End of switch statement
    } // End of getter method
} // End of class
