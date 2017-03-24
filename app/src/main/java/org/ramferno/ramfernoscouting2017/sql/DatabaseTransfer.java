package org.ramferno.ramfernoscouting2017.sql;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ramferno.ramfernoscouting2017.LaunchActivity;
import org.ramferno.ramfernoscouting2017.fragments.ScoutFragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

// Start of DatabaseTransfer
@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class DatabaseTransfer extends AsyncTask<String, String, String> {
    // Declare object references
    private Context context;
    private ProgressDialog pDialog;
    private Fragment fragment;

    // Declare variables
    private String urlAddress;

    /**
     * Constructor for the class
     * @param context is the application context
     * @param urlAddress is a url address with an accessible MySQL database
     */
    public DatabaseTransfer(Context context, String urlAddress, Fragment fragment) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.fragment = fragment;
    } //End of constructor

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Display dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Syncing Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    } // End of method

    @Override
    protected String doInBackground(String... args) {
        sendData();
        return getAndStoreData();
    } // End of method

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ScoutFragment scoutFragment = (ScoutFragment) fragment;
        scoutFragment.refreshListView();

        // Make toast which indicates if response was successful
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();

        // Dismiss dialog
        pDialog.dismiss();
    } // End of method

    /**
     * Attempts a GET connection to a database server
     * @return a connection object
     */
    private HttpURLConnection attemptGetConnection() {
        try {
            // Declare and instantiate URL object
            URL url = new URL("http://" + urlAddress + "/ramfernoscout/database/retrieve.php");

            // Open connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set request properties
            con.setRequestMethod("GET");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);

            // Return connection
            return con;
        }
        catch (SocketTimeoutException errTO) {
            Log.e("ERROR TO", errTO.getMessage());
            return null;
        }
        catch (MalformedURLException errURL) {
            Log.e("ERROR URL", errURL.getMessage());
            return null;
        }
        catch (IOException errIO) {
            errIO.printStackTrace();
            return null;
        } // End of try statement
    } // End of method

    /**
     * Attempts a POST connection to a database server
     * @return a connection object
     */
    private HttpURLConnection attemptPostConnection() {
        try {
            URL url = new URL("http://" + urlAddress + "/ramfernoscout/database/updateadd.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set request properties
            con.setRequestMethod("POST");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);
            con.setDoOutput(true);

            // Return connection
            return con;
        }
        catch (SocketTimeoutException errTO) {
            Log.e("ERROR TO", errTO.getMessage());
            return null;
        }
        catch (MalformedURLException errURL) {
            Log.e("ERROR URL", errURL.getMessage());
            return null;
        }
        catch (IOException errIO) {
            errIO.printStackTrace();
            return null;
        } // End of try statement
    } // End of method

    /**
     * Sends all data inside local database to database server
     * @return a response code as a string
     */
    @SuppressWarnings("static-access")
    private String sendData() {
        // Declare object reference
        HttpURLConnection con = null;

        // Attempt to insert data into the database
        try {
            // Declare and instantiate objects
            final Cursor cursor = LaunchActivity.dbHelper.getData(LaunchActivity.dbHelper
                    .getReadableDatabase(), "*", 0);

            // Attempt to get a connection
            con = attemptPostConnection();

            //Check if connection was successful
            if (con == null) {
                return null;
            } //End of if statement

            // Declare and instantiate objects
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            // Write data to database server
            bw.write(packData(cursor));
            cursor.moveToNext();

            // Close stream, writer and connection
            bw.close();
            os.close();

            // Store response code in integer
            int responseCode = con.getResponseCode();

            // Check if data was successfully enter into the database
            if (responseCode == con.HTTP_OK) {
                // Declare and instantiate objects
                BufferedReader br = new BufferedReader(new InputStreamReader
                        (con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Get full response
                while ((line=br.readLine()) != null) {
                    response.append(line);
                } // End of while statement

                // Close reader and return response
                br.close();
                return response.toString();
            } // End of if statement
        }
        catch (IOException errIO) {
            Log.e("ERROR IO", errIO.getMessage());
            errIO.printStackTrace();
        } //End of try statement

        // If nothing is returned originally, return null
        return null;
    } // End of method

    /**
     * Packs database information into a string which can be sent to the database server
     * @param cursor is a Cursor object containing data
     * @return a string
     */
    private String packData(Cursor cursor) {
        //Declare and instantiate objects
        JSONArray ja = new JSONArray();

        // Attempt to send data to the server via JSON
        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                JSONObject jo = new JSONObject();

                // Put all data in a json object
                jo.put(DatabaseContract.ScoutTable.TEAM_NUMBER, cursor.getInt(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.TEAM_NUMBER)));
                jo.put(DatabaseContract.ScoutTable.TEAM_NAME, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.TEAM_NAME)));
                jo.put(DatabaseContract.ScoutTable.AUTO_FUEL_LOW, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.AUTO_FUEL_LOW)));
                jo.put(DatabaseContract.ScoutTable.AUTO_FUEL_HIGH, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.AUTO_FUEL_HIGH)));
                jo.put(DatabaseContract.ScoutTable.AUTO_FUEL_POINTS, cursor.getInt(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.AUTO_FUEL_POINTS)));
                jo.put(DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED)));
                jo.put(DatabaseContract.ScoutTable.TELE_FUEL_LOW, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.TELE_FUEL_LOW)));
                jo.put(DatabaseContract.ScoutTable.TELE_FUEL_HIGH, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.TELE_FUEL_HIGH)));
                jo.put(DatabaseContract.ScoutTable.TELE_FUEL_POINTS, cursor.getInt(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.TELE_FUEL_POINTS)));
                jo.put(DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED)));
                jo.put(DatabaseContract.ScoutTable.ENDGAME_HANG, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.ENDGAME_HANG)));
                jo.put(DatabaseContract.ScoutTable.PLAY_STYLE, cursor.getString(cursor
                        .getColumnIndexOrThrow(DatabaseContract.ScoutTable.PLAY_STYLE)));

                Log.e("Print JSON Object", jo.toString());

                // Add JSON Object to array
                ja.put(jo);

                // Move to next position in array
                cursor.moveToNext();
            } // End of while loop

            // Return packed data in string format
            return ja.toString();
        }
        catch (JSONException errJSON) {
            // If occurs, log error
            Log.e("ERROR JSON", errJSON.getMessage());
        } // End of try statement

        // Return nothing
        return null;
    } // End of packData

    /**
     * Downloads data from the database server and stores it in local database
     */
    private String getAndStoreData() {
        // Attempt connection to server
        HttpURLConnection con = attemptGetConnection();

        // If no connection, return nothing
        if(con == null) {
            return "ERROR: Connection unsuccessful, please make sure you are properly connected " +
                    "to the right IP address which has access to the database server";
        } // End of if statement

        // Declare InputStream
        InputStream is = null;

        // Attempt to read the file of data
        try {
            // Declare and instantiate objects
            is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            // Declare object references
            String line;
            String responseString = "";

            // Read all lines of data until the end of the file is reached
            if(br != null) {
                while ((line = br.readLine()) != null) {
                    responseString += line;
                } //End of while statement

                // Add string to string builder
                response.append(responseString);

                // Close reader
                br.close();
            }
            else {
                throw new IOException();
            } //End of if statement

            // Declare and instantiate objects
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject jsonObject;

            // Reset scout table
            LaunchActivity.dbHelper.onUpgrade(LaunchActivity.dbHelper.getWritableDatabase(), 0);

            // Iterate through JSON array and add each set to database
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                // Store data in Content values
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.ScoutTable.TEAM_NUMBER,
                        jsonObject.getInt(DatabaseContract.ScoutTable.TEAM_NUMBER));
                values.put(DatabaseContract.ScoutTable.TEAM_NAME,
                        jsonObject.getString(DatabaseContract.ScoutTable.TEAM_NAME));
                values.put(DatabaseContract.ScoutTable.AUTO_FUEL_LOW,
                        jsonObject.getString(DatabaseContract.ScoutTable.AUTO_FUEL_LOW));
                values.put(DatabaseContract.ScoutTable.AUTO_FUEL_HIGH,
                        jsonObject.getString(DatabaseContract.ScoutTable.AUTO_FUEL_HIGH));
                values.put(DatabaseContract.ScoutTable.AUTO_FUEL_POINTS,
                        jsonObject.getInt(DatabaseContract.ScoutTable.AUTO_FUEL_POINTS));
                values.put(DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED,
                        jsonObject.getString(DatabaseContract.ScoutTable.AUTO_ROTOR_ENGAGED));
                values.put(DatabaseContract.ScoutTable.TELE_FUEL_LOW,
                        jsonObject.getString(DatabaseContract.ScoutTable.TELE_FUEL_LOW));
                values.put(DatabaseContract.ScoutTable.TELE_FUEL_HIGH,
                        jsonObject.getString(DatabaseContract.ScoutTable.TELE_FUEL_HIGH));
                values.put(DatabaseContract.ScoutTable.TELE_FUEL_POINTS,
                        jsonObject.getInt(DatabaseContract.ScoutTable.TELE_FUEL_POINTS));
                values.put(DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED,
                        jsonObject.getString(DatabaseContract.ScoutTable.TELE_ROTOR_ENGAGED));
                values.put(DatabaseContract.ScoutTable.ENDGAME_HANG,
                        jsonObject.getString(DatabaseContract.ScoutTable.ENDGAME_HANG));
                values.put(DatabaseContract.ScoutTable.PLAY_STYLE,
                        jsonObject.getString(DatabaseContract.ScoutTable.PLAY_STYLE));

                // Add data to database
                LaunchActivity.dbHelper.insert(LaunchActivity.dbHelper.getWritableDatabase(),
                        values, 0);
            } // End of for loop
        }
        catch (IOException errIO) {
            // If occurs, log error and return false
            Log.e("ERROR IO", errIO.getMessage());
            return "ERROR";
        }
        catch (JSONException errJSON) {
            // If occurs, log error and return false
            Log.e("ERROR JSON", errJSON.getMessage());
            return "ERROR";
        }
        finally {
            // Close input stream
            if(is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } // End of try statement
            } // End of if statement
        } // End of try statement

        // Return success message if no error occurs
        return "Sync successful!";
    } // End of method
} // End of class