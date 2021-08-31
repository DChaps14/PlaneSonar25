package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Class that implements AirlineLoader using methods to turn a .dat file containing Airlines into an
 * Arraylist of Airlines to use for the record
 */
public class AirlineLoader {
    /**
     * Loads each airline from the airlineData and makes sure that the airline has enough valid data attributes
     * to be a valid airline.
     *
     * @param airlineData a string containing the data attributes for an airline.
     * @return The loaded airline if the number of unknown data attributes is less than 5, null otherwise.
     */
    public Airline loadAirline(String[] airlineData) {

        int numUnknown = 0;

        int id;
        try {
            id = Integer.parseInt(airlineData[0]);
        } catch (Exception e) {
            id = -1;
            numUnknown += 1;
        }

        String name;
        try {
            name = airlineData[1].replace("\"", "").replace("\\\\", "");
            if (name.equals("\\N") || name.equals("")) {
                name = "Unknown";
                numUnknown += 3;
            }
        } catch (Exception e) {
            name = "Unknown";
            numUnknown += 3;
        }

        String alias;
        try {
            alias = airlineData[2].replaceAll("\"", "").replace("\\\\", "");
            if (alias.equals("\\N") || alias.equals("")) {
                alias = "None";
                numUnknown += 1;
            }
        } catch (Exception e) {
            alias = "None";
            numUnknown += 1;
        }

        String iata;
        try {
            iata = airlineData[3].replace("\"", "").replace("\\\\", "");
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
            icao = airlineData[4].replace("\"", "").replace("\\\\", "");
            if (icao.equals("\\N") || icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
                icao = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            icao = "Unknown";
            numUnknown += 1;
        }

        String callSign;
        try {
            callSign = airlineData[5].replace("\"", "").replace("\\\\", "");
            if (callSign.equals("\\N") || callSign.equals("")) {
                callSign = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            callSign = "Unknown";
            numUnknown += 1;
        }

        String country;
        try {
            country = airlineData[6].replace("\"", "").replace("\\\\", "");
            if (country.equals("\\N") || country.equals("")) {
                country = "Unknown";
                numUnknown += 1;
            }
        } catch (Exception e) {
            country = "Unknown";
            numUnknown += 1;
        }

        String activeString;
        try {
            activeString = airlineData[7].replace("\"", "").replace("\\\\", "");
        } catch (Exception e) {
            activeString = "N";
        }

        boolean active;
        active = activeString.equals("Y");

        if (numUnknown < 5) {
            return new Airline(id, name, active, country, alias, callSign, iata, icao);
        } else {
            return null;
        }

    }

    /**
     * Checks to make sure that the airline has the exact number of attributes to be a
     * valid airline.
     *
     * @param path The path at which the airline file is contained.
     * @return true if the airline has exactly 8 attributes, false otherwise.
     * @throws IOException If the airline loaded is invalid.
     */
    public boolean loadAirlineErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length == 8) {
                return true;
            }
        }

        dataReader.close();
        return false;
    }

    /**
     * Loads all the airlines in a particular file into an array to be checked further.
     * Appropriate error handling is done if the airline file is invalid.
     *
     * @param path The path at which the airline file is contained.
     * @return An arraylist containing all the airlines in the file.
     * @throws IOException If the airline file loaded is invalid.
     */
    public ArrayList<Airline> loadAirlineFile(String path) throws IOException {

        ArrayList<Airline> airlineList = new ArrayList<Airline>();

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                Airline airline = loadAirline(data);
                if (airline != null) {
                    airlineList.add(airline);
                }
            }
        }
        dataReader.close();
        return airlineList;
    }
}
