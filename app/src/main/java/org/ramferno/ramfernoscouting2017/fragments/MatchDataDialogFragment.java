package org.ramferno.ramfernoscouting2017.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.parsers.MatchParser;

// Start of MatchDataDialogFragment
public class MatchDataDialogFragment extends DialogFragment {
    // Declare objects
    private EditText enterTeam;
    private EditText enterCode;

    // Declare variables
    private int teamNumber;
    private String eventCode;

    @NonNull
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Declare and initialize objects
        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.fragment_dialog_match, null);
        enterTeam = (EditText) v.findViewById(R.id.enter_team);
        enterCode = (EditText) v.findViewById(R.id.enter_code);

        // Create AlertDialog
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                // Set title
                .setTitle("Enter Team & Event")

                // Set negative button
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Close dialog
                                dialog.cancel();
                            } //End of method
                        }) //End of method

                // Set positive button
                .setPositiveButton("Retrieve",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Initialize team and year variables
                                teamNumber = Integer.valueOf(enterTeam.getText().toString());
                                eventCode = enterCode.getText().toString();

                                // Check if view match data fragment currently exists
                                if (getFragmentManager().findFragmentByTag("viewDataTag") != null) {
                                    // Switch to original fragment
                                    FragmentManager fManager = getFragmentManager();
                                    FragmentTransaction fTransaction = fManager.beginTransaction();
                                    fTransaction.setCustomAnimations(R.anim.fade_in,
                                            R.anim.fade_out);
                                    fTransaction.remove(getFragmentManager()
                                            .findFragmentByTag("viewDataTag"));
                                    fTransaction.commit();
                                } // End of method

                                // Get and parse data
                                MatchParser parser = new MatchParser("samer", "scouting-system",
                                        "v1", "https://www.thebluealliance.com/api/v2/team/frc" +
                                        teamNumber + "/event/2017" + eventCode + "/matches",
                                        LaunchActivity.dbHelper, LaunchActivity.dbHelper
                                        .getWritableDatabase(), MatchFragment.TABLE, getContext(),
                                        LaunchActivity.instanceHelper.getThirdFragment());

                                // Attempt to parse data
                                parser.execute();

                                // Set static variables for the class
                                MatchFragment.setVariables(teamNumber, eventCode);
                            } //End of method
                        }); //End of method

        //Set view and return the created view
        b.setView(v);
        return b.create();
    } //End of method
} // End of class
