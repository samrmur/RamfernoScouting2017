package org.ramferno.ramfernoscouting2017.fragments;

import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.adapters.MatchListViewAdapter;
import org.ramferno.ramfernoscouting2017.parsers.MatchParser;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;

// Start of MatchFragment
@SuppressWarnings({"FieldCanBeLocal"})
public class MatchFragment extends Fragment {
    // Declare constants and statics
    public static final int TABLE = 1;
    public static int teamNumberStatic;
    public static String eventCodeStatic;

    // Declare objects
    private static SQLiteDatabase database;
    private MergeCursor cursor;
    private MatchListViewAdapter adapter;
    private ListView matchItems;
    private Button refreshButton;

    /**
     * Constructor for the class, no use besides instantiating
     */
    public MatchFragment() {
        // Required empty public constructor
    } // End of constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create View object
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        // Get database
        database = LaunchActivity.dbHelper.getReadableDatabase();

        // Populate list view
        matchItems = (ListView) view.findViewById(R.id.list_match);
        cursor = mergeCursorObjects();
        adapter = new MatchListViewAdapter(getContext(), cursor);
        matchItems.setAdapter(adapter);

        // Switch fragments on item
        matchItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView,
                                    int itemPosition, long itemId) {
                // Move the cursor the clicked item position
                adapter.getCursor();
                cursor.moveToPosition(itemPosition);

                // Get match type and number
                String matchType = cursor.getString
                        (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.MATCH_TYPE));
                int matchNumber = cursor.getInt
                        (cursor.getColumnIndexOrThrow(DatabaseContract.MatchTable.MATCH_NUMBER));

                // Create bundle of arguments
                Bundle bundle = new Bundle();
                bundle.putString("matchType", matchType);
                bundle.putInt("matchNumber", matchNumber);

                // Create new fragment with arguments
                ViewMatchDataFragment fragment =  new ViewMatchDataFragment();
                fragment.setArguments(bundle);

                // Switch fragments and show match data
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                fTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fTransaction.add(R.id.match_fragment, fragment, "viewDataTag");
                fTransaction.commit();
            } // End of method
        }); // End of method

        // Refresh list view with new data
        // Get and parse data
        refreshButton = (Button) view.findViewById(R.id.match_refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get and parse data
                MatchParser parser = new MatchParser("samer", "scouting-system",
                        "v1", "https://www.thebluealliance.com/api/v2/team/frc" +
                        teamNumberStatic + "/event/2017" + eventCodeStatic + "/matches",
                        LaunchActivity.dbHelper, LaunchActivity.dbHelper.getWritableDatabase(),
                        MatchFragment.TABLE, getContext(), LaunchActivity.instanceHelper
                        .getThirdFragment());

                // Attempt to parse data
                parser.execute();
            } // End of method
        }); // End of method

        // Inflate the layout for this fragment
        return view;
    } // End of method

    /**
     * Groups up four cursor objects based on their data
     * @return a MergeCursor object
     */
    public MergeCursor mergeCursorObjects() {
        // Declare and initialize array of cursor objects
        Cursor[] cursorArray = new Cursor[4];

        // Separate data by match type
        Cursor cursor1 = LaunchActivity.dbHelper.getRowData(database, "*", TABLE,
                "WHERE " + DatabaseContract.MatchTable.MATCH_TYPE + "='f' ORDER BY "
                        + DatabaseContract.MatchTable.MATCH_NUMBER);
        cursorArray[0] = cursor1;
        Cursor cursor2 = LaunchActivity.dbHelper.getRowData(database, "*", TABLE,
                "WHERE " + DatabaseContract.MatchTable.MATCH_TYPE + "='sf' ORDER BY "
                        + DatabaseContract.MatchTable.MATCH_NUMBER);
        cursorArray[1] = cursor2;
        Cursor cursor3 = LaunchActivity.dbHelper.getRowData(database, "*", TABLE,
                "WHERE " + DatabaseContract.MatchTable.MATCH_TYPE + "='qf' ORDER BY "
                        + DatabaseContract.MatchTable.MATCH_NUMBER);
        cursorArray[2] = cursor3;
        Cursor cursor4 = LaunchActivity.dbHelper.getRowData(database, "*", TABLE,
                "WHERE " + DatabaseContract.MatchTable.MATCH_TYPE + "='qm' ORDER BY "
                        + DatabaseContract.MatchTable.MATCH_NUMBER);
        cursorArray[3] = cursor4;

        // Return a MergeCursor with the array
        return new MergeCursor(cursorArray);
    } // End of method

    /**
     * Refreshes the list view in this fragment
     */
    public void refreshListView() {
        cursor.requery();
        adapter.notifyDataSetChanged();
    } // End of method

    /**
     * Sets the static variables of the class
     * @param teamNumber is a new team number value
     * @param eventCode is a new event code value
     */
    public static void setVariables(int teamNumber, String eventCode) {
        teamNumberStatic = teamNumber;
        eventCodeStatic = eventCode;
    } // End of method
} // End of class