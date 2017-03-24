package org.ramferno.ramfernoscouting2017.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Start of DatabaseHelper
public class DatabaseHelper extends SQLiteOpenHelper {
    // Declare main database constants
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ramferno.db";

    // Declare scout table constants
    private static final String SCOUT_TABLE_ENTRIES = "CREATE TABLE " +
            DatabaseContract.ScoutTable.TABLE_NAME + " (" +
            DatabaseContract.ScoutTable._ID + " INTEGER PRIMARY KEY," +
            DatabaseContract.ScoutTable.TEAM_NAME + " TEXT," +
            DatabaseContract.ScoutTable.TEAM_NUMBER + " INTEGER," +
            DatabaseContract.ScoutTable.AUTO_FUEL_LOW + " TEXT," +
            DatabaseContract.ScoutTable.AUTO_FUEL_HIGH + " TEXT," +
            DatabaseContract.ScoutTable.AUTO_FUEL_POINTS + " INTEGER," +
            DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED + " TEXT," +
            DatabaseContract.ScoutTable.TELE_FUEL_LOW + " TEXT," +
            DatabaseContract.ScoutTable.TELE_FUEL_HIGH + " TEXT," +
            DatabaseContract.ScoutTable.TELE_FUEL_POINTS + " INTEGER," +
            DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED + " TEXT," +
            DatabaseContract.ScoutTable.ENDGAME_HANG + " TEXT," +
            DatabaseContract.ScoutTable.PLAY_STYLE + " TEXT)";
    private static final String DELETE_SCOUT_TABLE = "DROP TABLE IF EXISTS " +
            DatabaseContract.ScoutTable.TABLE_NAME;

    // Declare match table constants
    private static final String MATCH_TABLE_ENTRIES = "CREATE TABLE " +
            DatabaseContract.MatchTable.TABLE_NAME + " (" +
            DatabaseContract.MatchTable._ID + " INTEGER PRIMARY KEY," +
            DatabaseContract.MatchTable.MATCH_NUMBER + " INTEGER," +
            DatabaseContract.MatchTable.MATCH_TYPE + " TEXT," +
            DatabaseContract.MatchTable.BLUE_TEAM_ONE + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TEAM_TWO + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TEAM_THREE + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_AUTO_FUEL_LOW + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_AUTO_FUEL_HIGH + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_ROTOR_1_AUTO + " TEXT," +
            DatabaseContract.MatchTable.BLUE_ROTOR_2_AUTO + " TEXT," +
            DatabaseContract.MatchTable.BLUE_ROTOR_1_TELE + " TEXT," +
            DatabaseContract.MatchTable.BLUE_ROTOR_2_TELE + " TEXT," +
            DatabaseContract.MatchTable.BLUE_ROTOR_3_TELE + " TEXT," +
            DatabaseContract.MatchTable.BLUE_ROTOR_4_TELE + " TEXT," +
            DatabaseContract.MatchTable.BLUE_TELE_FUEL_LOW + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TELE_FUEL_HIGH + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_AUTO_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_AUTO_MOBILITY_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_AUTO_ROTOR_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_AUTO_FUEL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TELE_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TELE_FUEL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TELE_ROTOR_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TELE_TAKEOFF_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_ADJUST_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_FOUL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.BLUE_TOTAL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_TEAM_ONE + " INTEGER," +
            DatabaseContract.MatchTable.RED_TEAM_TWO + " INTEGER," +
            DatabaseContract.MatchTable.RED_TEAM_THREE + " INTEGER," +
            DatabaseContract.MatchTable.RED_AUTO_FUEL_LOW + " INTEGER," +
            DatabaseContract.MatchTable.RED_AUTO_FUEL_HIGH + " INTEGER," +
            DatabaseContract.MatchTable.RED_ROTOR_1_AUTO + " TEXT," +
            DatabaseContract.MatchTable.RED_ROTOR_2_AUTO + " TEXT," +
            DatabaseContract.MatchTable.RED_ROTOR_1_TELE + " TEXT," +
            DatabaseContract.MatchTable.RED_ROTOR_2_TELE + " TEXT," +
            DatabaseContract.MatchTable.RED_ROTOR_3_TELE + " TEXT," +
            DatabaseContract.MatchTable.RED_ROTOR_4_TELE + " TEXT," +
            DatabaseContract.MatchTable.RED_TELE_FUEL_LOW + " INTEGER," +
            DatabaseContract.MatchTable.RED_TELE_FUEL_HIGH + " INTEGER," +
            DatabaseContract.MatchTable.RED_AUTO_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_AUTO_MOBILITY_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_AUTO_ROTOR_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_AUTO_FUEL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_TELE_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_TELE_FUEL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_TELE_ROTOR_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_TELE_TAKEOFF_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_ADJUST_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_FOUL_POINTS + " INTEGER," +
            DatabaseContract.MatchTable.RED_TOTAL_POINTS + " INTEGER)";
    private static final String DELETE_MATCH_TABLE = "DROP TABLE IF EXISTS " +
            DatabaseContract.MatchTable.TABLE_NAME;

    /**
     * Creates a database
     * @param context is the context of the application
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    } // End of constructor

    /**
     * Required abstract method, do not implement
     */
    public void onCreate(SQLiteDatabase db) {
        // Create both tables
        db.execSQL(SCOUT_TABLE_ENTRIES);
        db.execSQL(MATCH_TABLE_ENTRIES);
    } // End of method

    /**
     * Creates a database tables
     * @param db is a SQLiteDatabase object
     * @param table is an integer (0 for scout table, 1 for match table, anything else
     *                         for both)
     */
    private void onCreate(SQLiteDatabase db, int table) {
        // Check which table is being created
        if (table == 0) {
            // Create match table
            db.execSQL(SCOUT_TABLE_ENTRIES);
        }
        else if (table == 1) {
            // Create scout table
            db.execSQL(MATCH_TABLE_ENTRIES);
        } // End of if statement
        else {
            // Create both tables
            db.execSQL(SCOUT_TABLE_ENTRIES);
            db.execSQL(MATCH_TABLE_ENTRIES);
        } // End of if statement
    } // End of method

    /**
     * Required abstract method, do not implement
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Required abstract method, do not implement
    } // End of method

    /**
     * Upgrades database tables
     * @param db is a SQLiteDatabase object
     * @param table is an integer (0 for scout table, 1 for match table)
     */
    public void onUpgrade(SQLiteDatabase db, int table) {
        // Check which table is being dropping
        if (table == 0) {
            // Drop table and create new one
            db.execSQL(DELETE_SCOUT_TABLE);
            onCreate(db, table);
        }
        else if (table == 1) {
            db.execSQL(DELETE_MATCH_TABLE);
            onCreate(db, table);
        } // End of if statement
    } // End of method

    /**
     * Inserts data into the database
     * @param db is a SQLiteDatabase object
     * @param cv is a ContentValues object
     * @param table is an integer (0 for scout table, 1 for match table)
     */
    public void insert(SQLiteDatabase db, ContentValues cv, int table) {
        // Check which table is having data inserted into it
        if (table == 0) {
            db.insert(DatabaseContract.ScoutTable.TABLE_NAME, null, cv);
        }
        else if (table == 1) {
            db.insert(DatabaseContract.MatchTable.TABLE_NAME, null, cv);
        } // End of if statement
    } // End of method

    /**
     * Upgrades data in the database
     * @param db is a SQLiteDatabase object
     * @param cv is a ContentValues object
     * @param whichRow is the row being updated
     * @param table is an integer (0 for scout table, 1 for match table)
     */
    public void upgradeData(SQLiteDatabase db, ContentValues cv, String whichRow, int table) {
        // Check which table is having data upgrading in it
        if (table == 0) {
            db.update(DatabaseContract.ScoutTable.TABLE_NAME, cv, whichRow, null);
        }
        else if (table == 1) {
            db.update(DatabaseContract.MatchTable.TABLE_NAME, cv, whichRow, null);
        } // End of if statement
    } // End of method

    /**
     * Gets data from a database
     * @param db is a SQLiteDatabase object
     * @param selectRow is a string which defines what row to select data from
     * @param table is an integer (0 for scout table, 1 for match table)
     * @return Cursor object containing data
     */
    public Cursor getData(SQLiteDatabase db, String selectRow, int table) {
        // Defines Cursor object
        Cursor cursor = null;

        // Check which table to get data from
        if (table == 0) {
            cursor = db.rawQuery("SELECT " + selectRow + " FROM " +
                    DatabaseContract.ScoutTable.TABLE_NAME, null);
        }
        else if (table == 1) {
            cursor = db.rawQuery("SELECT " + selectRow + " FROM " +
                    DatabaseContract.MatchTable.TABLE_NAME, null);
        } // End of if statement

        // Return cursor
        return cursor;
    } // End of getter method

    /**
     * Gets data from a database with a where clause
     * @param db is a SQLiteDatabase object
     * @param selectRow is a string which defines what row to select data from
     * @param table is an integer (0 for scout table, 1 for match table)
     * @param extra is extra inserted sql code
     * @return Cursor object containing data
     */
    public Cursor getRowData(SQLiteDatabase db, String selectRow, int table, String extra) {
        // Defines Cursor object
        Cursor cursor = null;

        // Check which table to get data from
        if (table == 0) {
            cursor = db.rawQuery("SELECT " + selectRow + " FROM " +
                    DatabaseContract.ScoutTable.TABLE_NAME + " " + extra, null);
        }
        else if (table == 1) {
            cursor = db.rawQuery("SELECT " + selectRow + " FROM " +
                    DatabaseContract.MatchTable.TABLE_NAME + " " + extra, null);
        } // End of if statement

        // Return cursor
        return cursor;
    } // End of getter method

    /**
     * Deletes a row of data from the scout table in the local database
     * @param db is a SQLiteDatabase object
     * @param teamNumber is the number of an FRC team in the local database
     * @param table is an integer (0 for scout table, 1 for match table)
     */
    public void deleteScoutRow(SQLiteDatabase db, String teamNumber, int table) {
        db.delete(DatabaseContract.ScoutTable.TABLE_NAME, DatabaseContract.ScoutTable.TEAM_NUMBER
                + "=" + teamNumber, null);
    } // End of method
} // End of class