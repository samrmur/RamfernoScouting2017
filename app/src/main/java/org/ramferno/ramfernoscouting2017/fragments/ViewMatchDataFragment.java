package org.ramferno.ramfernoscouting2017.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;

// Start of ViewMatchDataFragment
public class ViewMatchDataFragment extends Fragment {
    // Declare instance objects
    private Fragment removeFragment = this;

    public ViewMatchDataFragment() {
        // Required empty public constructor
    } // End of constructor

    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_match_data, container, false);

        // Declare constant
        final int TABLE_NUMBER = 1;

        // Get all relevant match data
        Bundle bundle = getArguments();
        Cursor cursor = LaunchActivity.dbHelper.getRowData(LaunchActivity.dbHelper
                .getWritableDatabase(), "*", TABLE_NUMBER, "WHERE " + DatabaseContract
                .MatchTable.MATCH_TYPE + "= '" + bundle.getString("matchType") + "' AND " +
                DatabaseContract.MatchTable.MATCH_NUMBER + "=" + Integer.toString
                (bundle.getInt("matchNumber")));
        cursor.moveToFirst();

        // Set the text values for every text view
        ((TextView) view.findViewById(R.id.match_type_view_data)).setText(getFullMatchType
                (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .MATCH_TYPE))));

        ((TextView) view.findViewById(R.id.match_number_view_data)).setText(Integer.toString(cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.MATCH_NUMBER))));

        ((TextView) view.findViewById(R.id.blue_team_one)).setText(Integer.toString(cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_TEAM_ONE))));

        ((TextView) view.findViewById(R.id.blue_team_two)).setText(Integer.toString(cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_TEAM_TWO))));

        ((TextView) view.findViewById(R.id.blue_team_three)).setText(Integer.toString(cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TEAM_THREE))));

        ((TextView) view.findViewById(R.id.blue_auto_fuel_count_high)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_AUTO_FUEL_HIGH))));

        ((TextView) view.findViewById(R.id.blue_auto_fuel_count_low)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_AUTO_FUEL_LOW))));

        ((TextView) view.findViewById(R.id.blue_auto_fuel_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_AUTO_FUEL_POINTS))));

        ((TextView) view.findViewById(R.id.blue_auto_rotor_1_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_ROTOR_1_AUTO)));

        ((TextView) view.findViewById(R.id.blue_auto_rotor_2_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_ROTOR_2_AUTO)));

        ((TextView) view.findViewById(R.id.blue_auto_rotor_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_AUTO_ROTOR_POINTS))));

        ((TextView) view.findViewById(R.id.blue_auto_mobility_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_AUTO_MOBILITY_POINTS))));

        ((TextView) view.findViewById(R.id.blue_auto_total_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_AUTO_POINTS))));

        ((TextView) view.findViewById(R.id.blue_tele_fuel_count_high)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TELE_FUEL_HIGH))));

        ((TextView) view.findViewById(R.id.blue_tele_fuel_count_low)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TELE_FUEL_LOW))));

        ((TextView) view.findViewById(R.id.blue_tele_fuel_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TELE_FUEL_POINTS))));

        ((TextView) view.findViewById(R.id.blue_tele_rotor_1_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_ROTOR_1_TELE)));

        ((TextView) view.findViewById(R.id.blue_tele_rotor_2_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_ROTOR_2_TELE)));

        ((TextView) view.findViewById(R.id.blue_tele_rotor_3_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_ROTOR_3_TELE)));

        ((TextView) view.findViewById(R.id.blue_tele_rotor_4_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.BLUE_ROTOR_4_TELE)));

        ((TextView) view.findViewById(R.id.blue_tele_rotor_points)).setText(Integer
                .toString(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TELE_ROTOR_POINTS))));

        ((TextView) view.findViewById(R.id.blue_tele_takeoff_points)).setText(Integer
                .toString(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TELE_TAKEOFF_POINTS))));

        ((TextView) view.findViewById(R.id.blue_tele_total_points)).setText(Integer
                .toString(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TELE_POINTS))));

        ((TextView) view.findViewById(R.id.blue_foul_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_FOUL_POINTS))));

        ((TextView) view.findViewById(R.id.blue_adjust_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_ADJUST_POINTS))));

        ((TextView) view.findViewById(R.id.blue_final_score)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .BLUE_TOTAL_POINTS))));

        ((TextView) view.findViewById(R.id.red_team_one)).setText(Integer.toString(cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_TEAM_ONE))));

        ((TextView) view.findViewById(R.id.red_team_two)).setText(Integer.toString(cursor
                .getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_TEAM_TWO))));

        ((TextView) view.findViewById(R.id.red_team_three)).setText(Integer.toString(cursor.getInt
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_TEAM_THREE))));

        ((TextView) view.findViewById(R.id.red_auto_fuel_count_high)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_AUTO_FUEL_HIGH))));

        ((TextView) view.findViewById(R.id.red_auto_fuel_count_low)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_AUTO_FUEL_LOW))));

        ((TextView) view.findViewById(R.id.red_auto_fuel_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_AUTO_FUEL_POINTS))));

        ((TextView) view.findViewById(R.id.red_auto_rotor_1_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_ROTOR_1_AUTO)));

        ((TextView) view.findViewById(R.id.red_auto_rotor_2_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_ROTOR_2_AUTO)));

        ((TextView) view.findViewById(R.id.red_auto_rotor_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_AUTO_ROTOR_POINTS))));

        ((TextView) view.findViewById(R.id.red_auto_mobility_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_AUTO_MOBILITY_POINTS))));

        ((TextView) view.findViewById(R.id.red_auto_total_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_AUTO_POINTS))));

        ((TextView) view.findViewById(R.id.red_tele_fuel_count_high)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TELE_FUEL_HIGH))));

        ((TextView) view.findViewById(R.id.red_tele_fuel_count_low)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TELE_FUEL_LOW))));

        ((TextView) view.findViewById(R.id.red_tele_fuel_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TELE_FUEL_POINTS))));

        ((TextView) view.findViewById(R.id.red_tele_rotor_1_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_ROTOR_1_TELE)));

        ((TextView) view.findViewById(R.id.red_tele_rotor_2_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_ROTOR_2_TELE)));

        ((TextView) view.findViewById(R.id.red_tele_rotor_3_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_ROTOR_3_TELE)));

        ((TextView) view.findViewById(R.id.red_tele_rotor_4_engaged)).setText(cursor.getString
                (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.RED_ROTOR_4_TELE)));

        ((TextView) view.findViewById(R.id.red_tele_rotor_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TELE_ROTOR_POINTS))));

        ((TextView) view.findViewById(R.id.red_tele_takeoff_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TELE_TAKEOFF_POINTS))));

        ((TextView) view.findViewById(R.id.red_tele_total_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TELE_POINTS))));

        ((TextView) view.findViewById(R.id.red_foul_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_FOUL_POINTS))));

        ((TextView) view.findViewById(R.id.red_adjust_points)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_ADJUST_POINTS))));

        ((TextView) view.findViewById(R.id.red_final_score)).setText(Integer.toString
                (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable
                        .RED_TOTAL_POINTS))));

        // Create back button
        Button backButton = (Button) view.findViewById(R.id.view_data_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to original fragment
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fTransaction.remove(removeFragment);
                fTransaction.commit();
            } // End of method
        }); // End of method

        //Return view
        return view;
    } // End of method

    /**
     * Gets a full name for the match type
     * @param dataFromDB is data from a database
     * @return a string
     */
    private String getFullMatchType(String dataFromDB) {
        // Check match type is in the database
        switch (dataFromDB) {
            case "qm":
                return "Qualification Match";
            case "qf":
                return "Quarter Finals";
            case "sf":
                return "Semi Finals";
            case "f":
                return "Finals";
            default:
                return "Unknown";
        } // End of switch statement
    } // End of method
} // End of class