package org.ramferno.ramfernoscouting2017.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.sql.DatabaseTransfer;

// Start pf SyncDialogFragment
public class SyncDialogFragment extends DialogFragment {
    // Declare and instantiate objects
    EditText enterIP;

    // Declare and initialize variables
    String ipAddress;

    @Override
    @NonNull
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Declare and initialize objects
        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.fragment_dialog_address, null);
        enterIP = (EditText) v.findViewById(R.id.enter_ip);

        // Create AlertDialog
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                //Set title
                .setTitle("Sync Data with MySQL database")

                // Set negative button
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Close dialog
                                dialog.cancel();
                            } // End of onClick
                        }) // End of DialogInterface

                // Set positive button
                .setPositiveButton("SYNC",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Set IP address to the one entered by the user in the EditText
                                // object
                                ipAddress = enterIP.getText().toString();

                                // Sync with database
                                DatabaseTransfer transfer = new DatabaseTransfer(getContext(),
                                        ipAddress, LaunchActivity.instanceHelper
                                        .getSecondFragment());
                                transfer.execute();
                            } // End of onClick
                        } // End of DialogInterface
                ); // End of AlertDialog

        //Set view and return the created view
        b.setView(v);
        return b.create();
    } //End of onCreateDialog
} //End of class