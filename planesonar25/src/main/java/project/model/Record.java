package project.model;

import java.io.*;
import java.util.*;

/**
 * This is a Class implements a Record.
 * The record can be used to filter Airports, Routes, Airlines and Flights by criteria,
 * it can also be used to search for Airports, Routes, Airlines and Flights given a criteria.
 * It is also used to add, remove and modify certain types of data.
 */
public class Record {
    private ArrayList<Flight> flightList;
    private ArrayList<Route> routeList;
    private ArrayList<Airport> airportList;
    private ArrayList<Airline> airlineList;
    private static Hashtable<String, Covid> covidDict = setCovid();
    private String name;

    /**
     * Record constructor that takes a String and initialize ArrayLists as null lists
     * @param name A String containing the name of the record
     */
    public Record(String name) {
        this.flightList = new ArrayList<Flight>();
        this.routeList = new ArrayList<Route>();
        this.airportList = new ArrayList<Airport>();
        this.airlineList = new ArrayList<Airline>();
        this.name = name;
    }

    /**
     * Record constructor
     * @param flightList An ArrayList containing Flight objects
     * @param routeList An ArrayList containing Route objects
     * @param airportList An ArrayList containing Airport objects
     * @param airlineList An Arraylist containing Airline objects
     */
    public Record(ArrayList<Flight> flightList, ArrayList<Route> routeList, ArrayList<Airport> airportList, ArrayList<Airline> airlineList) {
        this.flightList = flightList;
        this.routeList = routeList;
        this.airportList = airportList;
        this.airlineList = airlineList;
    }

    /**
     * Filters airlines by whether they're active or not. Returns a new list of airlines meeting this criteria.
     * Filters airlines by whether they're active or not.
     * @param active A boolean taken from the GUI whether they're after active or not
     * @param airlineList A list of Airlines to be filtered
     * @return a new list of airlines meeting this criteria (doesn't override original airlineList).
     */
    public ArrayList<Airline> filterAirlines(Boolean active, List<Airline> airlineList) {
        ArrayList<Airline> filteredAirlines = new ArrayList<Airline>();
        for (Airline airline: airlineList) {
            if (airline.isActive() == active) {
                filteredAirlines.add(airline);
            }
        }
        return filteredAirlines;
    }

    /**
     * Filters routes by whether they have stops or not. Returns a new list of routes meeting this criteria.
     * Filters routes by whether they have stops or not.
     * @param direct A boolean taken from the GUI whether they are after direct flights or not
     * @param routeList A list of Routes to be filtered
     * @return a new list of routes meeting this criteria (doesn't override original routeList).
     */
    public ArrayList<Route> filterRoutesStops(boolean direct, List<Route> routeList) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();
        if (direct) {
            for (Route route: routeList) {
                if (route.getNumStops() == 0) {
                    filteredRoutes.add(route);
                }
            }
        } else {
            for (Route route: routeList) {
                if (route.getNumStops() != 0) {
                    filteredRoutes.add(route);
                }
            }
        }
        return filteredRoutes;
    }

    /**
     * Ranks airports based on the number of routes they have.
     * Can be either ascending or descending order depending on the reverse parameter.
     * @param reverse A boolean taken from the GUI whether they are after ascending or descending
     * @param airports A list of Airports to be sorted
     * @return new, sorted list of airports (doesn't override original airportList).
     */
    public List<Airport> rankAirports(boolean reverse, List<Airport> airports) {
        List<Airport> rankedAirports = airports;
        if (reverse) {
            rankedAirports.sort(Comparator.comparing(Airport::getTotalRoutes).reversed());
        } else {
            rankedAirports.sort(Comparator.comparing(Airport::getTotalRoutes));
        }
        return rankedAirports;
    }


    /**
     * Finds airports where the value of the airport's 'attribute' matches 'keyword'
     * @param keyword The value of the airport's attributes that we are searching for
     * @param attribute The attribute of the airports to be compared
     * @return A list of airports which have a value of attribute that matches the keyword
     */
    public List<Airport> searchAirports(String keyword, String attribute) {
        List<Airport> searchResult = new ArrayList<Airport>();
        keyword = keyword.trim();
        for (Airport airport: airportList) {
            boolean airportMatches = false;
            if (attribute.equals("country")) {
                airportMatches = airport.getCountry().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("name")) {
                airportMatches = airport.getName().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("city")) {
                airportMatches = airport.getCity().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("total # routes")) {
                airportMatches = (Integer.toString(airport.getTotalRoutes()).equals(keyword));
            } else if (attribute.equals("iata")) {
                airportMatches = airport.getIata().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("icao")) {
                airportMatches = airport.getIcao().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("timezone")) {
                airportMatches = airport.getTimezoneString().equals(keyword);
            }

            if (airportMatches) {
                searchResult.add(airport);
            }
        }
        return searchResult;
    }

    /**
     * Finds airlines where the value of the airline's 'attribute' matches 'keyword'
     * @param keyword The value of the airline's attributes that we are searching for
     * @param attribute The attribute of the airlines to be compared
     * @return A list of airlines which have a value of 'attribute' that matches the keyword
     */
    public List<Airline> searchAirlines(String keyword, String attribute) {
       List<Airline> searchResult = new ArrayList<Airline>();
       keyword = keyword.trim();
       for (Airline airline: airlineList) {
           boolean airlineMatches = false;
           if (attribute.equals("name")) {
               airlineMatches = airline.getName().toLowerCase().contains(keyword.toLowerCase());
           } else if (attribute.equals("alias")) {
               airlineMatches = airline.getAlias().toLowerCase().contains(keyword.toLowerCase());
           } else if (attribute.equals("iata")) {
               airlineMatches = airline.getIata().toLowerCase().contains(keyword);
           } else if (attribute.equals("icao")) {
               airlineMatches = airline.getIcao().toLowerCase().contains(keyword);
           } else if (attribute.equals("callsign")) {
               airlineMatches = airline.getCallSign().toLowerCase().contains(keyword);
           } else if (attribute.equals("country")) {
               airlineMatches = airline.getCountry().toLowerCase().contains(keyword.toLowerCase());
           }

           if (airlineMatches) {
               searchResult.add(airline);
           }
       }
       return searchResult;
    }


    /**
     * Finds routes where the value of the route's 'attribute' matches 'keyword'
     * @param keyWord The value of the route's attributes that we are searching for
     * @param attribute The attribute of the routes to be compared
     * @return A list of routes which have a value of 'attribute' that matches the keyword
     */
    public ArrayList<Route> searchRoutes(String keyWord, String attribute) {
        ArrayList<Route> searchResult = new ArrayList<Route>();
        keyWord = keyWord.trim();
        for (Route route: routeList) {
            boolean match = false;
            if (attribute.equals("airline")) {
                match = route.getAirline().toLowerCase().contains(keyWord.toLowerCase());
            } else if (attribute.equals("total # stops")) {
                match = Integer.toString(route.getNumStops()).equals(keyWord);
            } else if (attribute.equals("source id")) {
                match = Integer.toString(route.getSourceID()).equals(keyWord);
            } else if (attribute.equals("destination id")) {
                match = Integer.toString(route.getDestID()).equals(keyWord);
            } else if (attribute.equals("source airport")) {
                match = route.getSourceAirport().toLowerCase().contains(keyWord.toLowerCase());
            } else if (attribute.equals("destination airport")) {
                match = route.getDestAirport().toLowerCase().contains(keyWord.toLowerCase());
            } else if (attribute.equals("equipment")) {
                match = route.getEquipment().toLowerCase().contains(keyWord.toLowerCase());
            }

            if (match) {
                searchResult.add(route);
            }
        }
        return searchResult;
    }

    /**
     * Requires special implementation in GUI.
     * To search flights, check either the destination or source checkbox (can't be both).
     * Then enter the name of the source/destination airport. If this is on record, will identify the
     * ICAO code from that airport, and search the flightlist for flights matching this source/destination
     * ICAO. If none can be found, returns empty list. Print exception when this occurs.

    public ArrayList<Flight> searchFlights(boolean source, String keyword) {

        ArrayList<Flight> searchResult = new ArrayList<Flight>();

        for (Airport airport: airportList) {
            if (airport.getName().toUpperCase().contains(keyword.toUpperCase())) {
                String icao = airport.getIcao();
                for (Flight flight : flightList) {
                    if (source) { // Searching source
                        if (flight.getSource().equals(icao)) {
                            searchResult.add(flight);
                        }
                    } else {  // Searching destination
                        if (flight.getDest().equals(icao)) {
                            searchResult.add(flight);
                        }
                    }
                }
            }
        }
        return searchResult;
    }
     */

    /**
     * Searches the current COVID list for a country, and returns the the COVID object that is of that country
     * @param country The country who's COVID stats we are looking for
     * @return The COVID object that contains the stats of the requested country
     */
    public static Covid searchCovid(String country) throws NoSuchFieldException {
        if (country.toLowerCase().equals("unknown")) {
            return new Covid(null, null, 0, 0, 0 );
        }
        if (covidDict.get(country.toLowerCase()) != null) {
            return covidDict.get(country.toLowerCase());
        } else {
            throw new NoSuchFieldException("No such country exists in our records");
        }
    }

    // Region commented out because these will be replaced soon.
    /**
    /*
     * Iterates through the list of airports and list of routes and adds 1 to the numRoutesSource attribute
     * of each airport based on how many routes begin at that airport.

    public void setNumRoutesSource() {
        for (Airport airport : airportList) {
            String icao = airport.getIcao();
            String iata = airport.getIata();
            for (Route route : routeList) {
                if (route.getSourceAirport().equals(icao) || route.getSourceAirport().equals(iata)) {
                    airport.setNumRoutesSource(airport.getNumRoutesSource() + 1);
                }
            }
        }
    }

    /*
     * Iterates through the list of airports and list of routes and adds 1 to the numRoutesDest attribute
     * of each airport based on how many routes end at that airport.
    public void setNumRoutesDest() {
        for (Airport airport : airportList) {
            String icao = airport.getIcao();
            String iata = airport.getIata();
            for (Route route : routeList) {
                if (route.getDestAirport().equals(icao) || route.getDestAirport().equals(iata)) {
                    airport.setNumRoutesDest(airport.getNumRoutesDest() + 1);
                }
            }
        }
    }
     */

    /**
     * Adds new Routes to the Record's list of Routes.
     * @param newRouteList An ArrayList containing the new Routes to be added
     */
    public void addRoutes(ArrayList<Route> newRouteList) {
        this.routeList.addAll(newRouteList);
        Set<Route> uniqueElements = new HashSet<>(this.routeList);
        this.routeList.clear();
        this.routeList.addAll(uniqueElements);
        this.routeList.sort(Comparator.comparing(Route::getId));
    }

    /**
     * Adds new Airports to the Record's list of Airports.
     * @param newAirportList An ArrayList containing the new Airports to be added
     */
    public void addAirports(ArrayList<Airport> newAirportList) {
        this.airportList.addAll(newAirportList);
        Set<Airport> uniqueElements = new HashSet<>(this.airportList);
        this.airportList.clear();
        this.airportList.addAll(uniqueElements);
        this.airportList.sort(Comparator.comparing(Airport::getId));
    }

    /**
     * Adds new Airlines to the Record's list of Airlines.
     * @param newAirlineList An ArrayList containing the new Airlines to be added
     */
    public void addAirlines(ArrayList<Airline> newAirlineList) {
        this.airlineList.addAll(newAirlineList);
        Set<Airline> uniqueElements = new HashSet<>(this.airlineList);
        this.airlineList.clear();
        this.airlineList.addAll(uniqueElements);
        this.airlineList.sort(Comparator.comparing(Airline::getId));
    }

    /**
     * Add a single Flight to the Record's list of Flight.
     * @param newFlight A Flight object containing the new object Flight to be added
     */
    public void addFlights(Flight newFlight) {
        this.flightList.add(newFlight);
        Set<Flight> uniqueElements = new HashSet<>(this.flightList);
        this.flightList.clear();
        this.flightList.addAll(uniqueElements);
    }

    /**
     * Modifies an Airport from the Record's list of Airport
     * by setting the oldAirport index to the new Airport
     * @param oldAirport An Airport object to be modified
     * @param newAirport An Airport object that is modified
     */
    public void modifyAirport(Airport oldAirport, Airport newAirport) {
        if (this.airportList.contains(oldAirport)) {
            this.airportList.set(airportList.indexOf(oldAirport), newAirport);
        }
    }

    /**
     * Modifies an Airline from the Record's list of Airline
     * by setting the oldAirline index to the new Airline
     * @param oldAirline An Airline object to be modified
     * @param newAirline An Airline object that is modified
     */
    public void modifyAirline(Airline oldAirline, Airline newAirline) {
        if (this.airlineList.contains(oldAirline)) {
            this.airlineList.set(airlineList.indexOf(oldAirline), newAirline);
        }
    }

    /**
     * Modifies a Route from the Record's list of Route
     * by setting the oldRoute index to the new Route
     * @param oldRoute A Route object to be modified
     * @param newRoute A Route object that is modified
     */
    public void modifyRoute(Route oldRoute, Route newRoute) {
        if (this.routeList.contains(oldRoute)) {
            this.routeList.set(routeList.indexOf(oldRoute), newRoute);
        }
    }

    /**
     * Removes an Airport from the Record's list of Airport
     * ensuring the Airport exists inside the Record
     * @param airport An Airport object to be removed
     */
    public void removeAirports(Airport airport) {
        if (this.airportList.contains(airport)) {
            this.airportList.remove(airport);
        }
    }

    /**
     * Removes an Airline from the Record's list of Airline
     * ensuring the Airline exists inside the Record
     * @param airlines An Airline object to be removed
     */
    public void removeAirlines(Airline airlines) {
        if (this.airlineList.contains(airlines)) {
            this.airlineList.remove(airlines);
        }
    }

    /**
     * Removes an Route from the Record's list of Route
     * ensuring the Route exists inside the Record
     * @param route A route object to be removed
     */
    public void removeRoutes(Route route) {
        if (this.routeList.contains(route)) {
            this.routeList.remove(route);
        }
    }

    /**
     * Prompts the loading of a pre-installed covid file
     * using the loadCovidFile from the CovidLoader method
     * @return a Hashtable containing the covid data
     */
    public static Hashtable<String, Covid> setCovid() {
        Hashtable<String, Covid> covidDict = null;

        try {
//            ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource())

            CovidLoader covidLoad = new CovidLoader();
            covidDict = covidLoad.loadCovidFile("/covid.dat");

        } catch (IOException e) {
            System.err.println("Could not load file");
        }
        return covidDict;
    }

    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    public ArrayList<Airline> getAirlineList() {
        return airlineList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
