package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Class that implements FlightLoader using methods to turn a .dat file containing Flight data into a
 * an Flight object to use for the record
 */
public class FlightLoader {
    /**
     * Loads a flight in from a particular file and appends the: statuses, locations, altitudes, latitudes
     * and longitudes to arraylists, which are returned in the Flight object.
     *
     * @param path The path at which the flight file is contained.
     * @return The particular flight being loaded in, which may contain some null values.
     * @throws IOException If the flight file loaded is invalid.
     */
    public Flight loadFlightFile(String path) throws IOException {

        ArrayList<String> statuses = new ArrayList<String>();
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<Integer> altitudes = new ArrayList<Integer>();
        ArrayList<Double> latitudes = new ArrayList<Double>();
        ArrayList<Double> longitudes = new ArrayList<Double>();
        int risk = 0; // Placeholder

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");

                String status;
                try {
                    status = data[0];
                } catch (Exception e) {
                    status = null;
                }

                String location;
                try {
                    location = data[1];
                } catch (Exception e) {
                    location = null;
                }

                int altitude;
                try {
                    altitude = Integer.parseInt(data[2]);
                } catch (Exception e) {
                    altitude = -1;
                }

                double latitude;
                try {
                    latitude = Double.parseDouble(data[3]);
                } catch (Exception e) {
                    latitude = 360; // Error case, latitude can't be 360.
                }

                double longitude;
                try {
                    longitude = Double.parseDouble(data[4]);
                } catch (Exception e) {
                    longitude = 360; // Error case, longitude can't be 360.
                }

                statuses.add(status);
                locations.add(location);
                altitudes.add(altitude);
                latitudes.add(latitude);
                longitudes.add(longitude);
            }
        }
        dataReader.close();

        return new Flight(latitudes, longitudes, altitudes, locations, statuses, risk);
    }

    /**
     * Checks to make sure that the flight has the exact number of attributes to be a
     * valid flight.
     *
     * @param path The path at which the flight file is contained.
     * @return true if the flight has exactly 5 attributes, false otherwise.
     * @throws IOException If the flight loaded is invalid.
     */
    public boolean loadFlightErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length == 5) {
                return true;
            }
        }

        dataReader.close();
        return false;
    }
}
