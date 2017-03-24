package org.ramferno.ramfernoscouting2017.parsers;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ramferno.ramfernoscouting2017.fragments.MatchFragment;
import org.ramferno.ramfernoscouting2017.sql.DatabaseContract;
import org.ramferno.ramfernoscouting2017.sql.DatabaseHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// Start of MatchParser
@SuppressWarnings("FieldCanBeLocal")
public class MatchParser extends AsyncTask<String, String, JSONArray> {
    // Declare instance object references
    private String targetURL;
    private String headerTBA;
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;
    private Fragment fragment;
    private HttpURLConnection connection;
    private URL url;
    private ProgressDialog pDialog;


    // Declare instance variables
    private int tableNumber;
    private boolean flagURL = true;
    private boolean flagConnection = true;


    /**
     * Parses data from the TBA API
     * @param id is the personal id for the request header
     * @param description is the name of the project using TBA data
     * @param version is the version of the project
     * @param targetURL is a TBA URL where data will be retrieved from
     */
    public MatchParser(String id, String description, String version,
                       String targetURL, DatabaseHelper helper, SQLiteDatabase database,
                       int tableNumber, Context context, Fragment fragment) {
        // Instantiate objects and initialize variables
        this.targetURL = targetURL;
        this.headerTBA = id + ":" + description + ":" + version;
        this.helper = helper;
        this.database = database;
        this.tableNumber = tableNumber;
        this.context = context;
        this.fragment = fragment;
    } // End of constructor

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Create Progress Dialog box
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Getting Data from TBA ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    } // End of method

    @Override
    protected JSONArray doInBackground(String... args) {
        // Attempt to open URL link
        try {
            // Open connection
            connection = null;
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            // Set request attributes
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "TBA-API");
            connection.setRequestProperty("X-TBA-App-Id", headerTBA);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);

            // Parse retrieved data to BufferedReader
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;

            // Read each line of the BufferedReader and store in a string
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append("\r");
            } // End of while statement
            br.close();

            // Convert data to JSON Array then return
            return new JSONArray(response.toString());
        }
        catch (MalformedURLException errFNF) {
            // Create toast
            Toast.makeText(context, "ERROR: Request Unsuccessful, please make " +
                    "sure you are entering valid team numbers and event codes",
                    Toast.LENGTH_LONG).show();

            // Close Dialog box
            pDialog.cancel();

            // Make flag false
            flagURL = false;

            // Return nothing
            return null;
        }
        catch(Exception err) {
            // Log error and print out its stack tree
            Log.e("ERROR", "Data request failed. Possible connection timeout or no " +
                    "connection at all");
            err.printStackTrace();

            // Close Dialog box
            pDialog.cancel();

            // Make flag false
            flagConnection = false;

            // Return nothing
            return null;
        } // End of try statement
    } // End of method

    @Override
    protected void onPostExecute(JSONArray array) {
        // Dismiss the dialog box
        pDialog.dismiss();

        // If error occurred, print toast, else add JSON data to database
        if (!flagURL) {
            Toast.makeText(context, "ERROR: Request Unsuccessful, please make " +
                    "sure you are entering valid team numbers and event codes",
                    Toast.LENGTH_LONG).show();
        }
        else if (!flagConnection) {
            Toast.makeText(context, "ERROR: Data request failed. Possible " +
                    "connection timeout or no connection at all", Toast.LENGTH_LONG).show();
        }
        else if (array.length() == 0) {
            Toast.makeText(context, "ERROR: The URL contains no data to " +
                    "add to the database, please make sure you are entering valid team " +
                    "numbers and event codes ", Toast.LENGTH_LONG).show();
        }
        else {
            // Get result
            boolean result = jsonToDatabase(array, helper, database, tableNumber);

            // Check if error occurred and make toast if it has
            if (!result) {
                Toast.makeText(context, "ERROR: Some or all data could not be added to " +
                        "the database, data is either null or not compatible with JSON",
                        Toast.LENGTH_LONG).show();
            }
            else {
                // Make toast, indicate to boolean successful and refresh list view
                Toast.makeText(context, "Data successfully retrieved",
                        Toast.LENGTH_LONG).show();

                MatchFragment matchFragment = (MatchFragment) fragment;
                matchFragment.refreshListView();
            } // End of if statement
        } // End of if statement
    } // End of method

    /**
     * Puts JSON data into a database
     * @param helper is a database helper object
     * @param database is a SQLiteDatabase object
     * @param tableNumber is an integer which defines which table is being changed
     */
    private boolean jsonToDatabase(JSONArray array, DatabaseHelper helper,
                                SQLiteDatabase database, int tableNumber) {
        // Reset database
        helper.onUpgrade(database, tableNumber);

        // Attempt to insert JSON Data
        try {
            // Go through every JSONObject in the array
            for (int i = 0; i < array.length(); i++) {
                // Get JSONObject
                JSONObject object = array.getJSONObject(i);

                // Create new content values
                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.MatchTable.MATCH_NUMBER,
                        object.getInt("match_number"));
                cv.put(DatabaseContract.MatchTable.MATCH_TYPE, object.getString("comp_level"));

                // Check if object can be cast
                if((object.get("score_breakdown")) instanceof JSONObject) {
                    // Get score breakdown object
                    JSONObject score_breakdown = (JSONObject) object.get("score_breakdown");

                    // Get alliance information
                    JSONObject alliances = (JSONObject) object.get("alliances");

                    // Get blue teams participating in the match
                    JSONObject blueAllianceInfo = (JSONObject) alliances.get("blue");
                    JSONArray blueTeams = (JSONArray) blueAllianceInfo.get("teams");
                    cv.put(DatabaseContract.MatchTable.BLUE_TEAM_ONE,
                            teamToInteger(blueTeams.get(0).toString()));
                    cv.put(DatabaseContract.MatchTable.BLUE_TEAM_TWO,
                            teamToInteger(blueTeams.get(1).toString()));
                    cv.put(DatabaseContract.MatchTable.BLUE_TEAM_THREE,
                            teamToInteger(blueTeams.get(2).toString()));

                    // Get blue teams participating in the match
                    JSONObject redAllianceInfo = (JSONObject) alliances.get("red");
                    JSONArray redTeams = (JSONArray) redAllianceInfo.get("teams");
                    cv.put(DatabaseContract.MatchTable.RED_TEAM_ONE,
                            teamToInteger(redTeams.get(0).toString()));
                    cv.put(DatabaseContract.MatchTable.RED_TEAM_TWO,
                            teamToInteger(redTeams.get(1).toString()));
                    cv.put(DatabaseContract.MatchTable.RED_TEAM_THREE,
                            teamToInteger(redTeams.get(2).toString()));

                    // Get blue team match data and store in content values
                    JSONObject blue = (JSONObject) score_breakdown.get("blue");
                    cv.put(DatabaseContract.MatchTable.BLUE_AUTO_FUEL_HIGH,
                            blue.getInt("autoFuelHigh"));
                    cv.put(DatabaseContract.MatchTable.BLUE_AUTO_FUEL_LOW,
                            blue.getInt("autoFuelLow"));
                    cv.put(DatabaseContract.MatchTable.BLUE_AUTO_FUEL_POINTS,
                            blue.getInt("autoFuelPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ROTOR_1_AUTO,
                            blue.getString("rotor1Auto"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ROTOR_2_AUTO,
                            blue.getString("rotor2Auto"));
                    cv.put(DatabaseContract.MatchTable.BLUE_AUTO_ROTOR_POINTS,
                            blue.getInt("autoRotorPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_AUTO_MOBILITY_POINTS,
                            blue.getInt("autoMobilityPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_AUTO_POINTS,
                            blue.getInt("autoPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TELE_FUEL_HIGH,
                            blue.getInt("teleopFuelHigh"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TELE_FUEL_LOW,
                            blue.getInt("teleopFuelLow"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TELE_FUEL_POINTS,
                            blue.getInt("teleopFuelPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ROTOR_1_TELE,
                            blue.getString("rotor1Engaged"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ROTOR_2_TELE,
                            blue.getString("rotor2Engaged"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ROTOR_3_TELE,
                            blue.getString("rotor3Engaged"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ROTOR_4_TELE,
                            blue.getString("rotor4Engaged"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TELE_ROTOR_POINTS,
                            blue.getInt("teleopRotorPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TELE_TAKEOFF_POINTS,
                            blue.getInt("teleopTakeoffPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TELE_POINTS,
                            blue.getInt("teleopPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_FOUL_POINTS,
                            blue.getInt("foulPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_ADJUST_POINTS,
                            blue.getInt("adjustPoints"));
                    cv.put(DatabaseContract.MatchTable.BLUE_TOTAL_POINTS,
                            blue.getInt("totalPoints"));

                    // Get red team match data and store in content values
                    JSONObject red = (JSONObject) score_breakdown.get("red");
                    cv.put(DatabaseContract.MatchTable.RED_AUTO_FUEL_HIGH,
                            red.getInt("autoFuelHigh"));
                    cv.put(DatabaseContract.MatchTable.RED_AUTO_FUEL_LOW,
                            red.getInt("autoFuelLow"));
                    cv.put(DatabaseContract.MatchTable.RED_AUTO_FUEL_POINTS,
                            red.getInt("autoFuelPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_ROTOR_1_AUTO,
                            red.getString("rotor1Auto"));
                    cv.put(DatabaseContract.MatchTable.RED_ROTOR_2_AUTO,
                            red.getString("rotor2Auto"));
                    cv.put(DatabaseContract.MatchTable.RED_AUTO_ROTOR_POINTS,
                            red.getInt("autoRotorPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_AUTO_MOBILITY_POINTS,
                            red.getInt("autoMobilityPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_AUTO_POINTS,
                            red.getInt("autoPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_TELE_FUEL_HIGH,
                            red.getInt("teleopFuelHigh"));
                    cv.put(DatabaseContract.MatchTable.RED_TELE_FUEL_LOW,
                            red.getInt("teleopFuelLow"));
                    cv.put(DatabaseContract.MatchTable.RED_TELE_FUEL_POINTS,
                            red.getInt("teleopFuelPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_ROTOR_1_TELE,
                            red.getString("rotor1Engaged"));
                    cv.put(DatabaseContract.MatchTable.RED_ROTOR_2_TELE,
                            red.getString("rotor2Engaged"));
                    cv.put(DatabaseContract.MatchTable.RED_ROTOR_3_TELE,
                            red.getString("rotor3Engaged"));
                    cv.put(DatabaseContract.MatchTable.RED_ROTOR_4_TELE,
                            red.getString("rotor4Engaged"));
                    cv.put(DatabaseContract.MatchTable.RED_TELE_ROTOR_POINTS,
                            red.getInt("teleopRotorPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_TELE_TAKEOFF_POINTS,
                            red.getInt("teleopTakeoffPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_TELE_POINTS,
                            red.getInt("teleopPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_FOUL_POINTS,
                            red.getInt("foulPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_ADJUST_POINTS,
                            red.getInt("adjustPoints"));
                    cv.put(DatabaseContract.MatchTable.RED_TOTAL_POINTS,
                            red.getInt("totalPoints"));

                    // Insert data into the database
                    helper.insert(database, cv, tableNumber);
                } // End of if statement
            } // End of for loop

            // Return true if no error occurs
            return true;
        }
        catch (NullPointerException errNull) {
            // Log error and and make flag false
            Log.e("ERROR NULL", errNull.getMessage());
            return false;
        }
        catch(JSONException errJSON) {
            // Log error and and make flag false
            Log.e("ERROR JSON", errJSON.getMessage());
            return false;
        } // End of try statement
    } // End of method

    /**
     * Turns an FRC team value from API to integer
     * @param team is the team in a string
     * @return an integer
     */
    private int teamToInteger(String team) {
        team = team.replace("frc", "");
        return Integer.valueOf(team);
    } // End of method
} // End of class