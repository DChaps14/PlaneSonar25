package project.model;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;

/**
 * This is a Class that implements the Covid data.
 * Calculates covid risk and determines the countries risk e.g. Extreme Low, Low, Medium, High, Extreme
 */
public class Covid {
    private String location;
    private String date;
    private double total_cases_per_million;
    private double total_deaths_per_million;
    private int population;
    public static ArrayList<String> missingCountries = new ArrayList<>();

    /**
     *
     * @param location A String that represents the Country that has the virus
     * @param date A String that represents the date of when the data is taken
     * @param total_cases_per_million A double that represents the total cases per million in a Country
     * @param total_deaths_per_million A double that represents the total deaths per million in a Country
     * @param population An int that represents the total population in a country
     */
    public Covid(String location, String date, double total_cases_per_million, double total_deaths_per_million,
                 int population) {
        setLocation(location);
        setDate(date);
        setTotal_cases_per_million(total_cases_per_million);
        setTotal_deaths_per_million(total_deaths_per_million);
        setPopulation(population);
    }

    /**
     * Uses the getRiskDouble method to calculate the Covid Risk and
     * returns it as a string representing the level of Risk.
     *
     * @return A String that represents the Covid Risk
     */
    public String getRiskString() {
        double percentage = getRiskDouble();
        if (percentage <= 0.1){
            return "Extremely Low";
        }
        if (percentage <= 0.5){
            return "Low";
        }
        else if(percentage <= 1){
            return "Medium";
        }
        else if (percentage <= 5){
            return "High";
        }
        else{
            return "Extreme";
        }
    }

    /**
     * Gets the percentage of the population that have cases of COVID-19
     *
     * @return The percentage of one million citizens that have cases of COVID-19
     */
    public double getRiskDouble() {
        if(this.total_cases_per_million != 0) {
            double percentWithCases = (this.total_cases_per_million/1000000)*100;
            return Precision.round(percentWithCases, 2);
        }else {
            return 0;
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal_cases_per_million(double total_cases_per_million) {
        this.total_cases_per_million = total_cases_per_million;
    }

    public void setTotal_deaths_per_million(double total_deaths_per_million) {
        this.total_deaths_per_million = total_deaths_per_million;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountry() {
        return this.location;
    }
}
