package org.ramferno.ramfernoscouting2017.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ramferno.ramfernoscouting2017.R;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;

// Start of ScoutListViewAdapter
public class ScoutListViewAdapter extends CursorAdapter {
    /**
     * Custom adapter for displaying scout data
     * @param context is the application context
     * @param cursor  is a Cursor object containing data
     */
    public ScoutListViewAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    } // End of constructor

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.scout_row_layout, parent, false);
    } // End of method

    @Override
    @SuppressLint("SetTextI18n")
    public void bindView(View view, Context context, Cursor cursor) {
        TextView teamNumber = (TextView) view.findViewById(R.id.scout_team_number);
        TextView teamName = (TextView) view.findViewById(R.id.scout_team_name);

        // Extract properties from cursor
        int teamNumberDB = cursor.getInt(cursor.getColumnIndexOrThrow
                (DatabaseContract.ScoutTable.TEAM_NUMBER));
        String teamNameDB = cursor.getString(cursor.getColumnIndexOrThrow
                (DatabaseContract.ScoutTable.TEAM_NAME));

        // Populate fields with extracted properties
        teamNumber.setText(Integer.toString(teamNumberDB));
        teamName.setText(teamNameDB);
    } // End of method
} // End of class