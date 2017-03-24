package org.ramferno.ramfernoscouting2017.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;

// Start of MatchListViewAdapter
public class MatchListViewAdapter extends CursorAdapter {
    /**
     * Custom adapter for displaying match data
     * @param context is the application context
     * @param cursor is a Cursor object containing data
     */
    public MatchListViewAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    } // End of constructor

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.match_row_layout, parent, false);
    } // End of method

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView matchType = (TextView) view.findViewById(R.id.match_type_main);
        TextView matchNumber = (TextView) view.findViewById(R.id.match_number_main);

        // Extract properties from cursor
        String matchTypeDB = cursor.getString(cursor.getColumnIndexOrThrow
                (DatabaseContract.MatchTable.MATCH_TYPE));
        int matchNumberDB = cursor.getInt(cursor.getColumnIndexOrThrow
                (DatabaseContract.MatchTable.MATCH_NUMBER));

        // Populate fields with extracted properties
        matchType.setText(getFullMatchType(matchTypeDB));
        matchNumber.setText(String.valueOf(matchNumberDB));
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
