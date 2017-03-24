package org.ramferno.ramfernoscouting2017.sql;

import android.provider.BaseColumns;

// Start of DatabaseContract
@SuppressWarnings("WeakerAccess")
public class DatabaseContract {
    // Prevent this class from being called
    private DatabaseContract() {} // End of constructor

    // Define Match Table
    public static class ScoutTable implements BaseColumns {
        // Prevent this class from being called
        public static final String TABLE_NAME = "scoutdb";

        // Define Team elements
        public static final String TEAM_NAME =  "TeamName";
        public static final String TEAM_NUMBER = "TeamNumber";

        // Define Autonomous Scouting elements
        public static final String AUTO_FUEL_LOW = "AutoFuelLow";
        public static final String AUTO_FUEL_HIGH = "AutoFuelHigh";
        public static final String AUTO_FUEL_POINTS = "AutoFuelPoints";
        public static final String AUTO_ROTOR_ENGAGED = "AutoRotorEngaged";

        // Define Teleoperated Scouting elements
        public static final String TELE_FUEL_LOW = "TeleFuelLow";
        public static final String TELE_FUEL_HIGH = "TeleFuelHigh";
        public static final String TELE_FUEL_POINTS = "TeleFuelPoints";
        public static final String TELE_ROTOR_ENGAGED = "TeleRotorEngaged";

        // Define Endgame Scouting elements
        public static final String ENDGAME_HANG = "Hang";

        // Define play style element
        public static final String PLAY_STYLE = "PlayStyle";
    } // End of class

    // Define Match Table elements
    public static class MatchTable implements BaseColumns {
        // Define the name of the table
        public static final String TABLE_NAME = "matchdb";

        // Define match number and type elements
        public static String MATCH_NUMBER = "MatchNumber";
        public static String MATCH_TYPE = "MatchType";

        // Define blue alliance teams
        public static String BLUE_TEAM_ONE = "BlueTeam1";
        public static String BLUE_TEAM_TWO = "BlueTeam2";
        public static String BLUE_TEAM_THREE = "BlueTeam3";

        // Define blue score breakdown elements
        public static String BLUE_AUTO_FUEL_LOW = "BlueAutoFuelLow";
        public static String BLUE_AUTO_FUEL_HIGH = "BlueAutoFuelHigh";
        public static String BLUE_ROTOR_1_AUTO = "BlueRotor1Auto";
        public static String BLUE_ROTOR_2_AUTO = "BlueRotor2Auto";
        public static String BLUE_ROTOR_1_TELE = "BlueRotor1Tele";
        public static String BLUE_ROTOR_2_TELE = "BlueRotor2Tele";
        public static String BLUE_ROTOR_3_TELE = "BlueRotor3Tele";
        public static String BLUE_ROTOR_4_TELE = "BlueRotor4Tele";
        public static String BLUE_TELE_FUEL_LOW = "BlueTeleFuelLow";
        public static String BLUE_TELE_FUEL_HIGH = "BlueTeleFuelHigh";
        public static String BLUE_AUTO_POINTS = "BlueAutoPoints";
        public static String BLUE_AUTO_MOBILITY_POINTS = "BlueAutoMobilityPoints";
        public static String BLUE_AUTO_ROTOR_POINTS = "BlueAutoRotorPoints";
        public static String BLUE_AUTO_FUEL_POINTS = "BlueAutoFuelPoints";
        public static String BLUE_TELE_POINTS = "BlueTelePoints";
        public static String BLUE_TELE_FUEL_POINTS = "BlueTeleFuelPoints";
        public static String BLUE_TELE_ROTOR_POINTS = "BlueTeleRotorPoints";
        public static String BLUE_TELE_TAKEOFF_POINTS = "BlueTeleTakeoffPoints";
        public static String BLUE_ADJUST_POINTS = "BlueAdjustPoints";
        public static String BLUE_FOUL_POINTS = "BlueFoulPoints";
        public static String BLUE_TOTAL_POINTS = "BlueTotalPoints";

        // Define red alliance teams
        public static String RED_TEAM_ONE = "RedTeam1";
        public static String RED_TEAM_TWO = "RedTeam2";
        public static String RED_TEAM_THREE = "RedTeam3";

        // Define red score breakdown elements
        public static String RED_AUTO_FUEL_LOW = "RedAutoFuelLow";
        public static String RED_AUTO_FUEL_HIGH = "RedAutoFuelHigh";
        public static String RED_ROTOR_1_AUTO = "RedRotor1Auto";
        public static String RED_ROTOR_2_AUTO = "RedRotor2Auto";
        public static String RED_ROTOR_1_TELE = "RedRotor1Tele";
        public static String RED_ROTOR_2_TELE = "RedRotor2Tele";
        public static String RED_ROTOR_3_TELE = "RedRotor3Tele";
        public static String RED_ROTOR_4_TELE = "RedRotor4Tele";
        public static String RED_TELE_FUEL_LOW = "RedTeleFuelLow";
        public static String RED_TELE_FUEL_HIGH = "RedTeleFuelHigh";
        public static String RED_AUTO_POINTS = "RedAutoPoints";
        public static String RED_AUTO_MOBILITY_POINTS = "RedAutoMobilityPoints";
        public static String RED_AUTO_ROTOR_POINTS = "RedAutoRotorPoints";
        public static String RED_AUTO_FUEL_POINTS = "RedAutoFuelPoints";
        public static String RED_TELE_POINTS = "RedTelePoints";
        public static String RED_TELE_FUEL_POINTS = "RedTeleFuelPoints";
        public static String RED_TELE_ROTOR_POINTS = "RedTeleRotorPoints";
        public static String RED_TELE_TAKEOFF_POINTS = "RedTeleTakeoffPoints";
        public static String RED_ADJUST_POINTS = "RedAdjustPoints";
        public static String RED_FOUL_POINTS = "RedFoulPoints";
        public static String RED_TOTAL_POINTS = "RedTotalPoints";
    } // End of class
} // End of class