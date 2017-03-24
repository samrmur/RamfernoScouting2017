package org.ramferno.ramfernoscouting2017.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.adapters.ScoutListViewAdapter;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;
import org.ramferno.ramfernoscouting2017.sql.DatabaseTransfer;

// Start of ScoutFragment
@SuppressWarnings({"FieldCanBeLocal", "deprecation"})
public class ScoutFragment extends Fragment {
    // Declare constants
    private final int TABLE = 0;

    // Declare instance objects
    private ListView scoutItems;
    private Cursor cursor;
    private SQLiteDatabase database;
    private ScoutListViewAdapter adapter;

    public ScoutFragment() {
        // Required empty public constructor
    } // End of constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scout, container, false);

        // Get database
        database = LaunchActivity.dbHelper.getReadableDatabase();

        // Populate list view
        scoutItems = (ListView) view.findViewById(R.id.list_scout);
        cursor = getScoutCursor();
        adapter = new ScoutListViewAdapter(getContext(), cursor);
        scoutItems.setAdapter(adapter);

        // Create event on button click
        Button addButton = (Button) view.findViewById(R.id.scout_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragmentByArgument(0);
            } // End of method
        }); // End of method

        // Create event on list view item click
        scoutItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Move the cursor the clicked item position
                cursor.moveToPosition(i);

                // Get team number
                int teamNumber = cursor.getInt
                        (cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable.TEAM_NUMBER));

                // Add to bundle
                Bundle bundle = new Bundle();
                bundle.putInt("teamNumber", teamNumber);

                // Switch fragments with arguments
                switchFragmentByArgument(1, bundle);
            } // End of method
        }); // End of method

        // Create event on list view item hold
        scoutItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Move the cursor the clicked item position
                cursor.moveToPosition(i);

                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ScoutTable.TEAM_NUMBER));

                // Create dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setCancelable(true);
                dialog.setTitle("Are you sure you want to delete this item?");
                dialog.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Close dialog
                                dialog.cancel();
                            } // End of onClick
                        }); // End of DialogInterface
                dialog.setPositiveButton("DELETE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Delete row and refresh list view
                                LaunchActivity.dbHelper.deleteScoutRow(LaunchActivity
                                        .dbHelper.getWritableDatabase(),
                                        Integer.toString(cursor.getInt(cursor
                                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable
                                        .TEAM_NUMBER))), TABLE );
                                refreshListView();
                            } // End of onClick
                        }); // End of DialogInterface
                dialog.show();

                return true;
            } // End of method
        }); // End of method

        // Return view
        return view;
    } // End of method

    /**
     * Gets scouting data from the SQLite database
     * @return a Cursor object
     */
    public Cursor getScoutCursor() {
        return LaunchActivity.dbHelper.getData(database, "*", TABLE);
    } // End of method

    /**
     * Switch fragments with one argument
     * @param argument is an integer which will define what behaviours the fragment has
     */
    private void switchFragmentByArgument(int argument) {
        // Set arguments for fragment
        ScoutDataEditorFragment fragment = new ScoutDataEditorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("AddOrEdit", argument);
        fragment.setArguments(bundle);

        // Switch fragments
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fTransaction.add(R.id.scout_fragment, fragment);
        fTransaction.commit();
    } // End of method

    /**
     * Switch fragments with bundle of arguments
     * @param argument is an integer which will define what behaviours the fragment has
     * @param bundle is a Bundle object with arguments
     */
    private void switchFragmentByArgument(int argument, Bundle bundle) {
        // Set arguments for fragment
        ScoutDataEditorFragment fragment = new ScoutDataEditorFragment();
        bundle.putInt("AddOrEdit", argument);
        fragment.setArguments(bundle);

        // Switch fragments
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fTransaction.add(R.id.scout_fragment, fragment);
        fTransaction.commit();
    } // End of method

    /**
     * Refreshes the list view in this fragment
     */
    public void refreshListView() {
        cursor.requery();
        adapter.notifyDataSetChanged();
    } // End of method
} // End of class