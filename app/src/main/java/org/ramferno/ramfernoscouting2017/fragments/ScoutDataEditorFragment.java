package org.ramferno.ramfernoscouting2017.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;

// Start of ScoutDataEditorFragment
public class ScoutDataEditorFragment extends Fragment {
    // Declare constants
    private Fragment removeFragment = this;
    private final int TABLE = 0;

    public ScoutDataEditorFragment() {
        // Required empty public constructor
    } // End of constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scout_data_editor, container, false);

        // Create Button Event
        changeButtonByArgument(getArguments().getInt("AddOrEdit"), view);

        // Create event on button click
        Button backButton = (Button) view.findViewById(R.id.team_data_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch fragments
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fTransaction.remove(removeFragment);
                fTransaction.commit();
            } // End of method
        }); // End of method

        // Return view
        return view;
    } // End of method

    /**
     * Turns a check value into a string, allowing it to be stored in a database
     * @param checkBox is a CheckBox object
     * @return a string
     */
    private String checkToString(CheckBox checkBox) {
        if(checkBox.isChecked()) {
            return "yes";
        }
        else {
            return "no";
        } // End of if statement
    } // End of method

    /**
     * Turns a string into a boolean value, allowing it to be set onto a CheckBox
     * @param stringValue is a string object
     * @return a boolean
     */
    private boolean stringToCheck(String stringValue) {
        // Check if the box has been checked and set value based on the answer
        return stringValue.equals("yes");
    } // End of method

    /**
     * Changes the button behaviour
     * @param argument is the argument switch decides button behaviour
     */
    @SuppressLint("SetTextI18n")
    private void changeButtonByArgument(int argument, final View viewMain) {
        if (argument == 0) {
            Button addButton = (Button) viewMain.findViewById(R.id.team_data_add_button);

            // Set text for the button
            addButton.setText(R.string.add_button);

            // Create event on button click
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Declare and initialize variables
                    boolean dataAlreadyExists = false;
                    boolean allSlotsFilled = true;

                    // Get team numbers from database
                    Cursor cursor = LaunchActivity.dbHelper.getData(LaunchActivity.dbHelper
                            .getReadableDatabase(), DatabaseContract.ScoutTable.TEAM_NUMBER,
                            TABLE);

                    // If data does not exist and EditText objects are not empty, insert it into
                    // database and exit fragment
                    if (((EditText) viewMain.findViewById(R.id.team_number_scout))
                            .getText().toString().isEmpty() ||
                            ((EditText) viewMain.findViewById(R.id.team_name_scout))
                            .getText().toString().isEmpty() ||
                            ((EditText) viewMain.findViewById(R.id.scout_auto_fuel_points))
                            .getText().toString().isEmpty() ||
                            ((EditText) viewMain.findViewById(R.id.scout_tele_fuel_points))
                            .getText().toString().isEmpty() ||
                            ((EditText) viewMain.findViewById(R.id.scout_play_style))
                            .getText().toString().isEmpty()) {

                        // Do allow data to be added since all slots ae not filled
                        allSlotsFilled = false;

                        // Make toast indicating all EditText objects need to be filled
                        Toast.makeText(getContext(), "ERROR: Please enter data in all data " +
                                "slots", Toast.LENGTH_LONG).show();
                    }
                    else {
                        // Iterate through cursor
                        while (cursor.moveToNext()) {
                            // Check if data already exits in the database, if so, print toast, else
                            // add data to the database
                            if (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract
                                    .ScoutTable.TEAM_NUMBER)) ==
                                    Integer.valueOf(((EditText) viewMain.findViewById(R.id
                                            .team_number_scout)).getText().toString())) {
                                Toast.makeText(getContext(), "ERROR: There is data for this " +
                                        "already in the database, please enter a different team " +
                                        "or go back to the main list and edit the team there",
                                        Toast.LENGTH_SHORT).show();

                                // Mark data as already existing
                                dataAlreadyExists = true;
                            } // End of if statement
                        } // End of while statement
                    } // End of if statement

                    if (!dataAlreadyExists && allSlotsFilled) {
                        ContentValues cv = new ContentValues();
                        cv.put(DatabaseContract.ScoutTable.TEAM_NUMBER,
                                Integer.valueOf(((EditText) viewMain.findViewById(R.id
                                        .team_number_scout)).getText().toString()));
                        cv.put(DatabaseContract.ScoutTable.TEAM_NAME,
                                ((EditText) viewMain.findViewById(R.id.team_name_scout))
                                        .getText().toString());
                        cv.put(DatabaseContract.ScoutTable.AUTO_FUEL_LOW,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_auto_fuel_low)));
                        cv.put(DatabaseContract.ScoutTable.AUTO_FUEL_HIGH,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_auto_fuel_high)));
                        cv.put(DatabaseContract.ScoutTable.AUTO_FUEL_POINTS,
                                Integer.valueOf(((EditText) viewMain.findViewById(R.id
                                        .scout_auto_fuel_points)).getText().toString()));
                        cv.put(DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_auto_rotor_engaged)));
                        cv.put(DatabaseContract.ScoutTable.TELE_FUEL_LOW,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_tele_fuel_low)));
                        cv.put(DatabaseContract.ScoutTable.TELE_FUEL_HIGH,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_tele_fuel_high)));
                        cv.put(DatabaseContract.ScoutTable.TELE_FUEL_POINTS,
                                Integer.valueOf(((EditText) viewMain.findViewById(R.id
                                        .scout_tele_fuel_points)).getText().toString()));
                        cv.put(DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_tele_rotor_engaged)));
                        cv.put(DatabaseContract.ScoutTable.ENDGAME_HANG,
                                checkToString((CheckBox) viewMain.findViewById
                                        (R.id.scout_tele_endgame)));
                        cv.put(DatabaseContract.ScoutTable.PLAY_STYLE,
                                ((EditText) viewMain.findViewById(R.id.scout_play_style))
                                        .getText().toString());

                        // Insert data into database
                        LaunchActivity.dbHelper.insert(LaunchActivity.dbHelper
                                .getWritableDatabase(), cv, TABLE);

                        // Make toast indicating successful insert
                        Toast.makeText(getContext(), "Data successfully inserted!", Toast
                                .LENGTH_LONG).show();

                        // Refresh the list view
                        ScoutFragment fragment = (ScoutFragment) LaunchActivity.instanceHelper
                                .getSecondFragment();
                        fragment.refreshListView();

                        // Switch fragments
                        FragmentManager fManager = getFragmentManager();
                        FragmentTransaction fTransaction = fManager.beginTransaction();
                        fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fTransaction.remove(removeFragment);
                        fTransaction.commit();
                    } // End of if statement
                } // End of method
            }); // End of method
        }
        else {
            // Get data from database
            Cursor cursor = LaunchActivity.dbHelper.getRowData(LaunchActivity.dbHelper
                    .getReadableDatabase(), "*", TABLE, "WHERE " + DatabaseContract.ScoutTable
                    .TEAM_NUMBER + "=" + getArguments().getInt("teamNumber"));
            cursor.moveToFirst();

            // Set Values for all views
            ((EditText) viewMain.findViewById(R.id.team_number_scout)).setText(Integer.toString
                    (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable
                            .TEAM_NUMBER))));
            ((EditText) viewMain.findViewById(R.id.team_name_scout)).setText(cursor.getString
                    (cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable.TEAM_NAME)));
            ((CheckBox) viewMain.findViewById(R.id.scout_auto_fuel_low)).setChecked(stringToCheck
                    (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable
                            .AUTO_FUEL_LOW))));
            ((CheckBox) viewMain.findViewById(R.id.scout_auto_fuel_high)).setChecked(stringToCheck
                    (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable
                            .AUTO_FUEL_HIGH))));
            ((EditText) viewMain.findViewById(R.id.scout_auto_fuel_points)).setText(Integer
                    .toString(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract
                            .ScoutTable.AUTO_FUEL_POINTS))));
            ((CheckBox) viewMain.findViewById(R.id.scout_auto_rotor_engaged)).setChecked
                    (stringToCheck(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract
                            .ScoutTable.AUTO_ROTOR_ENGAGED))));
            ((CheckBox) viewMain.findViewById(R.id.scout_tele_fuel_low)).setChecked(stringToCheck
                    (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable
                            .TELE_FUEL_LOW))));
            ((CheckBox) viewMain.findViewById(R.id.scout_tele_fuel_high)).setChecked(stringToCheck
                    (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable
                            .TELE_FUEL_HIGH))));
            ((EditText) viewMain.findViewById(R.id.scout_tele_fuel_points)).setText(Integer
                    .toString(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract
                            .ScoutTable.TELE_FUEL_POINTS))));
            ((CheckBox) viewMain.findViewById(R.id.scout_tele_rotor_engaged)).setChecked
                    (stringToCheck(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract
                            .ScoutTable.TELE_ROTOR_ENGAGED))));
            ((CheckBox) viewMain.findViewById(R.id.scout_tele_endgame)).setChecked
                    (stringToCheck(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract
                            .ScoutTable.ENDGAME_HANG))));
            ((EditText) viewMain.findViewById(R.id.scout_play_style)).setText(cursor.getString
                    (cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable.PLAY_STYLE)));

            // Make all views uneditable
            (viewMain.findViewById(R.id.team_number_scout)).setEnabled(false);
            (viewMain.findViewById(R.id.team_name_scout)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_auto_fuel_low)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_auto_fuel_high)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_auto_fuel_points)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_auto_rotor_engaged)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_tele_fuel_low)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_tele_fuel_high)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_tele_fuel_points)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_tele_rotor_engaged)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_tele_endgame)).setEnabled(false);
            (viewMain.findViewById(R.id.scout_play_style)).setEnabled(false);

            // Set text for the button
            final Button editButton = (Button) viewMain.findViewById(R.id.team_data_add_button);
            editButton.setText(R.string.scout_edit_button);
            editButton.setTag(0);

            // Create event on button click
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create action based on tag
                    if ((int) view.getTag() == 0) {
                        view.setTag(1);
                        editButton.setText(R.string.scout_save_button);

                        // Make all views editable
                        (viewMain.findViewById(R.id.team_name_scout)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_auto_fuel_low)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_auto_fuel_high)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_auto_fuel_points)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_auto_rotor_engaged)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_tele_fuel_low)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_tele_fuel_high)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_tele_fuel_points)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_tele_rotor_engaged)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_tele_endgame)).setEnabled(true);
                        (viewMain.findViewById(R.id.scout_play_style)).setEnabled(true);
                    }
                    else {
                        // If EditText objects are not empty, update data in
                        // database and exit fragment, else print toast
                        if (((EditText) viewMain.findViewById(R.id.team_number_scout))
                                .getText().toString().isEmpty() ||
                                ((EditText) viewMain.findViewById(R.id.team_name_scout))
                                        .getText().toString().isEmpty() ||
                                ((EditText) viewMain.findViewById(R.id.scout_auto_fuel_points))
                                        .getText().toString().isEmpty() ||
                                ((EditText) viewMain.findViewById(R.id.scout_tele_fuel_points))
                                        .getText().toString().isEmpty() ||
                                ((EditText) viewMain.findViewById(R.id.scout_play_style))
                                        .getText().toString().isEmpty()) {
                            // Make toast indicating all EditText objects need to be filled
                            Toast.makeText(getContext(), "ERROR: Please enter data in all data " +
                                    "slots", Toast.LENGTH_LONG).show();

                        }
                        else {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseContract.ScoutTable.TEAM_NAME,
                                    ((EditText) viewMain.findViewById(R.id.team_name_scout))
                                            .getText().toString());
                            cv.put(DatabaseContract.ScoutTable.AUTO_FUEL_LOW,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_auto_fuel_low)));
                            cv.put(DatabaseContract.ScoutTable.AUTO_FUEL_HIGH,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_auto_fuel_high)));
                            cv.put(DatabaseContract.ScoutTable.AUTO_FUEL_POINTS,
                                    Integer.valueOf(((EditText) viewMain.findViewById(R.id
                                            .scout_auto_fuel_points)).getText().toString()));
                            cv.put(DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_auto_rotor_engaged)));
                            cv.put(DatabaseContract.ScoutTable.TELE_FUEL_LOW,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_tele_fuel_low)));
                            cv.put(DatabaseContract.ScoutTable.TELE_FUEL_HIGH,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_tele_fuel_high)));
                            cv.put(DatabaseContract.ScoutTable.TELE_FUEL_POINTS,
                                    Integer.valueOf(((EditText) viewMain.findViewById(R.id
                                            .scout_tele_fuel_points)).getText().toString()));
                            cv.put(DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_tele_rotor_engaged)));
                            cv.put(DatabaseContract.ScoutTable.ENDGAME_HANG,
                                    checkToString((CheckBox) viewMain.findViewById
                                            (R.id.scout_tele_endgame)));
                            cv.put(DatabaseContract.ScoutTable.PLAY_STYLE,
                                    ((EditText) viewMain.findViewById(R.id.scout_play_style))
                                            .getText().toString());

                            // Upgrade data in the database
                            LaunchActivity.dbHelper.upgradeData(LaunchActivity.dbHelper
                                    .getWritableDatabase(), cv, DatabaseContract.ScoutTable
                                    .TEAM_NUMBER + "=" + ((EditText) viewMain.findViewById
                                    (R.id.team_number_scout)).getText().toString(), TABLE);

                            // Make toast indicating successful update
                            Toast.makeText(getContext(), "Data successfully updated!", Toast
                                    .LENGTH_LONG).show();

                            // Refresh the list view
                            ScoutFragment fragment = (ScoutFragment) LaunchActivity.instanceHelper
                                    .getSecondFragment();
                            fragment.refreshListView();

                            // Switch fragments
                            FragmentManager fManager = getFragmentManager();
                            FragmentTransaction fTransaction = fManager.beginTransaction();
                            fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                            fTransaction.remove(removeFragment);
                            fTransaction.commit();
                        } // End of if statement
                    } // End of if statement
                } // End of method
            }); // End of method
        } // End of if statement
    } // End of method
} // End of class