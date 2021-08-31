package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Class that implements AirportLoader using methods to turn a .dat file containing Airports into an
 * Arraylist of Airports to use for the record
 */
public class AirportLoader {
    /**
     * Loads each airport from the airportData and makes sure that the airport has enough valid data attributes
     * to be a valid airport.
     * (Most) invalid ints are displayed as -1, invalid strings displayed as null
     * Invalid latitudes and longitudes are displayed as 360. This is because these are impossible latitude/longitude values, and correspond to easy error handling.
     * Invalid timezones displayed as 25. Can't have a +25 hour timezone after all.
     *
     * @param airportData a string containing the data attributes for an airport.
     * @return The loaded airport if the number of unknown data attributes is less than 9, null otherwise.
     */
    public Airport loadAirport(String[] airportData) {

        int numUnknown = 0;

        int id;
        try {
            id = Integer.parseInt(airportData[0]);
        } catch (Exception e) {
            id = -1;
            numUnknown += 1;
        }

        String name;
        try {
            name = airportData[1].replaceAll("\"", "").replace("\\\\", "");
            if (name.equals("\\N") || name.equals("")) {
                name = "Unknown";
                numUnknown += 3;
            }
        } catch (Exception e) {
            name = "Unknown";
            numUnknown += 3;
        }

        String city;
        try {
            city = airportData[2].replaceAll("\"", "").replace("\\\\", "");
            if (city.equals("\\N") || city.equals("")) {
                city = "Unknown";
                numUnknown += 3;
            }
        } catch (Exception e) {
            city = "Unknown";
            numUnknown += 3;
        }

        String country;
        try {
            country = airportData[3].replaceAll("\"", "").replace("\\\\", "");
            if (country.equals("\\N") || country.equals("")) {
                country = "Unknown";
                numUnknown += 3;
            }
        } catch (Exception e) {
            country = "Unknown";
            numUnknown += 3;
        }

        String iata;
        try {
            iata = airportData[4].replaceAll("\"", "").replace("\\\\", "");
            if (iata.equals("\\N") || iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
                iata = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            iata = "Unknown";
            numUnknown += 1;
        }

        String icao;
        try {
            icao = airportData[5].replaceAll("\"", "").replace("\\\\", "");
            if (icao.equals("\\N") || icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
                icao = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            icao = "Unknown";
            numUnknown += 1;
        }

        double latitude;
        try {
            latitude = Double.parseDouble(airportData[6]);
        } catch (Exception e) {
            latitude = 360; // Latitudes can't be this big, used for error catching when calculating distances.
            numUnknown += 1;
        }

        double longitude;
        try {
            longitude = Double.parseDouble(airportData[7]);
        } catch (Exception e) {
            longitude = 360; // Longitudes can't be this big, used for error catching when calculating distances.
            numUnknown += 1;
        }

        int altitude;
        try {
            altitude = Integer.parseInt(airportData[8]);
        } catch (Exception e) {
            altitude = -1;
            numUnknown += 1;
        }

        double timezone;
        try {
            timezone = Double.parseDouble(airportData[9]);
        } catch (Exception e) {
            timezone = 25; // Timezones can't be this far ahead, used for error catching.
            numUnknown += 1;
        }

        String dst;
        try {
            dst = airportData[10].replaceAll("\"", "").replace("\\\\", "");
            if (dst.equals("\\N") || dst.equals("")) {
                dst = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            dst = "Unknown";
            numUnknown += 1;
        }

        String timezoneString;
        try {
            timezoneString = airportData[11].replaceAll("\"", "").replace("\\\\", "");
            if (timezoneString.equals("\\N") || timezoneString.equals("")) {
                timezoneString = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            timezoneString = "Unknown";
            numUnknown += 1;
        }

        String type;
        try {
            type = airportData[12].replaceAll("\"", "").replace("\\\\", "");
            if (type.equals("\\N") || type.equals("")) {
                type = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            type = "Unknown";
            numUnknown += 1;
        }

        String source;
        try {
            source = airportData[13].replaceAll("\"", "").replace("\\\\", "");
            if (source.equals("\\N") || source.equals("")) {
                source = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            source = "Unknown";
            numUnknown += 1;
        }

        int numRoutesSource = 0; // Placeholder, is altered through another function in Record (needs list routes to work).
        int numRoutesDest = 0; // Same as above.
        int risk = 0; //Generated within the airport class itself, using COVID statistics

        //If there are too many errors, don't add the airport to the file
        if (numUnknown < 9) {
            return new Airport(id, risk, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString, type, source, numRoutesSource, numRoutesDest);
        } else {
            return null;
        }
    }

    /**
     * Checks to make sure that the number of attributes an airport has is in the correct range
     * to be a valid airport.
     *
     * @param path The path at which the airport file is contained.
     * @return true if the airline has between 12 and 14 attributes, false otherwise.
     * @throws IOException If the airport loaded is invalid.
     */
    public boolean loadAirportErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length >= 12  && data.length <= 14) {
                return true;
            }
        }

        dataReader.close();
        return false;
    }

    /**
     * Loads all the airports in a particular file into an array to be checked further.
     * Appropriate error handling is done if the airport file is invalid.
     *
     * @param path The path at which the airport file is contained.
     * @return An arraylist containing all the airports in the file.
     * @throws IOException If the airport file loaded is invalid.
     */
    public ArrayList<Airport> loadAirportFile(String path) throws IOException {

        ArrayList<Airport> airportList = new ArrayList<Airport>();

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",(?! )");
                AirportLoader airportLoad = new AirportLoader();
                Airport airport = airportLoad.loadAirport(data);
                //Only add the airport if enough attributes are present
                if (airport != null) {
                    airportList.add(airport);
                }
            }
        }
        dataReader.close();
        return airportList;
    }
}
