package project.model;

import java.io.*;
import java.util.Hashtable;

/**
 * A Class that implements CovidLoader using methods to turn a .dat file containing data about COVID into an
 * a hashtable containing all the Covid Data to use for the record
 */
public class CovidLoader {

    /**
     * Iterates through the COVID file given, appending each line of COVID data into a hashtasble.
     * Appropriate error handling is done for invalid files.
     *
     * @param path The path at which the COVID file is contained.
     * @return A hashtable containing all the individual covidData attributes in the file, each will be for a different country.
     * @throws IOException If the COVID file loaded is invalid.
     */
    public Hashtable<String, Covid> loadCovidFile(String path) throws IOException {

        Hashtable<String, Covid> covidDict = new Hashtable<>();
        Covid currentCovid;

        BufferedReader dataReader = new BufferedReader(new InputStreamReader(this.getClass().getResource(path).openStream()));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                currentCovid = loadCovid(data);
                covidDict.put(currentCovid.getCountry().toLowerCase(), currentCovid);
            }
        }
        dataReader.close();
        return covidDict;
    }

    /**
     * Reads covid data from file and assigns the values to new element
     *
     * This function has checks for each input to try ensure data is entered correctly
     * For Example:
     * For numeric values, if they are not valid (Non-Numeric) they are set to a default of 0.
     * For string values, if they contain illegal characters are set to null.
     *
     * @return country covid-19 data entry
     */
    public Covid loadCovid(String[] covidData) {

        String location;
        try {
            if (covidData[2].matches("[A-Za-z '-]+")){
                location = covidData[2];
            }
            else{
                location = "Unknown";
            }
        }catch (Exception e){
            location = "Unknown";
        }

        String date;
        try {
            //checks date doesn't contain invalid Characters
            if (covidData[3].matches("[0-9/]+")){
                date = covidData[3];
            }
            else{
                date = null;
            }
        }catch (Exception e){
            date = null;
        }

        float total_cases_per_million;
        try {
            total_cases_per_million = Float.parseFloat(covidData[8]);
        }catch (Exception e){
            total_cases_per_million = 0;
        }

        float total_deaths_per_million;
        try {
            total_deaths_per_million = Float.parseFloat(covidData[9]);
        }catch (Exception e){
            total_deaths_per_million = 0;
        }

        int population;
        try {
            population = Integer.parseInt(covidData[10]);
        }catch (Exception e){
            population = 0;
        }

        Covid newCovid = new Covid(location, date, total_cases_per_million, total_deaths_per_million, population);
        return newCovid;

    }
}
