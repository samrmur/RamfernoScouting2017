package org.ramferno.ramfernoscouting2017.helpers;

import android.support.v4.app.Fragment;

// Start of FragmentHelper
public class InstanceHelper {
    // Declare references
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;

    /**
     * Saves an instance of of multiple fragments
     * @param fragment1 is a fragment object
     * @param fragment2 is a fragment object
     * @param fragment3 is a fragment object
     */
    public InstanceHelper(Fragment fragment1, Fragment fragment2, Fragment fragment3) {
        // Instantiates objects
        this.fragment1 = fragment1;
        this.fragment2 = fragment2;
        this.fragment3 = fragment3;
    } // End of constructor

    /**
     * Returns the instance of the first fragment
     * @return a fragment
     */
    public Fragment getFirstFragment() {
        return fragment1;
    } // End of getter method

    /**
     * Returns the instance of the second fragment
     * @return a fragment
     */
    public Fragment getSecondFragment() {
        return fragment2;
    } // End of getter method

    /**
     * Returns the instance of the third fragment
     * @return a fragment
     */
    public Fragment getThirdFragment() {
        return fragment3;
    } // End of getter method
} // End of class
