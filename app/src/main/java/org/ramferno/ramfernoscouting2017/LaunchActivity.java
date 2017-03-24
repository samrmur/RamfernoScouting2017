package org.ramferno.ramfernoscouting2017;

import android.app.ActivityManager;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.ramferno.ramfernoscouting2017.adapters.TabFragmentPagerAdapter;
import org.ramferno.ramfernoscouting2017.fragments.SyncDialogFragment;
import org.ramferno.ramfernoscouting2017.fragments.HomeFragment;
import org.ramferno.ramfernoscouting2017.fragments.MatchDataDialogFragment;
import org.ramferno.ramfernoscouting2017.fragments.ScoutFragment;
import org.ramferno.ramfernoscouting2017.fragments.MatchFragment;
import org.ramferno.ramfernoscouting2017.helpers.InstanceHelper;
import org.ramferno.ramfernoscouting2017.sql.DatabaseHelper;

// Start of LaunchActivity
@SuppressWarnings({"FieldCanBeLocal", "deprecation"})
public class LaunchActivity extends AppCompatActivity {
    // Declare instance objects
    private Toolbar mainToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentPagerAdapter pagerAdapter;
    public static InstanceHelper instanceHelper;

    // Declare all accessible fragments
    public HomeFragment homeFragment;
    public ScoutFragment scoutFragment;
    public MatchFragment matchFragment;

    // Declare database helper object
    public static DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //Disables Orientation throughout the entire application
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Set Application Overview Description
        ActivityManager.TaskDescription taskDescription = new ActivityManager.
                TaskDescription("Team 3756 Scouting Application", BitmapFactory
                .decodeResource(getResources(),R.mipmap.ic_launcher), ContextCompat
                .getColor(getApplicationContext(), R.color.colorPrimaryOpaque));
        this.setTaskDescription(taskDescription);

        // Make toolbar act as an action bar
        mainToolbar = (Toolbar) findViewById(R.id.main_app_bar);
        mainToolbar.setTitle(""); // Create dummy title
        setSupportActionBar(mainToolbar);
        mainToolbar.setTitle(R.string.toolbar_name);
        mainToolbar.setTitleTextColor(Color.WHITE);

        // Create database
        dbHelper = new DatabaseHelper(this);

        // Instantiate all fragments
        homeFragment = new HomeFragment();
        scoutFragment = new ScoutFragment();
        matchFragment = new MatchFragment();

        // Pass instances of fragment to helper
        instanceHelper = new InstanceHelper(homeFragment, scoutFragment, matchFragment);

        // Create tab layout
        createTabLayout();
    } // End of method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    } //End of onCreateOptionMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // Execute on option click
        switch (item.getItemId()) {
            case R.id.ip_item:
                SyncDialogFragment syncDialogFragment = new SyncDialogFragment();
                syncDialogFragment.show(fm, "IPFrag");
                return true;
            case R.id.match_item:
                MatchDataDialogFragment matchDialogFragment = new MatchDataDialogFragment();
                matchDialogFragment.show(fm, "MatchFrag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // End of switch statement
    } // End of method

    // Creates a tab layout
    public void createTabLayout() {
        // Instantiate objects
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), instanceHelper);

        // Add tabs to the layout
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_one));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_two));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_three));

        // Set color of tab text (deprecated method, required for lower APIs)
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.colorOptions));

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            } // End of method

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            } // End of method

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            } // End of method
        }); // End of method
    } // End of method
} // End of class
