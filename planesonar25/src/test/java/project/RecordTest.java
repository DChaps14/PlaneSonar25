package project;

import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RecordTest {

    Airline testAirline1 = new Airline(100, "Test1", true, "New Zealand", "Test1", "Test1", "Test1", "Test1");
    Airline testAirline2 = new Airline(101, "Test2", false, "New Zealand", "Test2", "Test2", "Test2", "Test2");
    Airline testAirline3 = new Airline(102, "Test3", true, "Australia", "Test3", "Test3", "Test3", "Test3");
    Airline testAirline4 = new Airline(103, "Test4", false, "New Zealand", "Test4", "Test4", "Test4", "Test4");
    Airline testAirline5 = new Airline(104, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

    Route testRoute1 = new Route("Air NZ", 500, "NZWN", 411, "NZCH", 511, 0, "DXa134", false);
    Route testRoute2 = new Route("Air NZ", 501, "NZCH", 411, "WLG", 511, 0, "DXa34", false);
    Route testRoute3 = new Route("Air NZ", 502, "NZAA", 411, "SYD", 511, 2, "DXa34", false);
    Route testRoute4 = new Route("Air NZ", 503, "NZCH", 411, "SYD", 511, 1, "DXa34", false);
    Route testRoute5 = new Route("Air NZ", 504, "NZWN", 411, "SYD", 511, 1, "DXa34", false);
    Route testRoute6 = new Route("Air NZ", 505, "CHC", 411, "YSSY", 511, 4, "DXa34", false);


    Airport testAirport1 = new Airport(101, 500, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", "Test1", "Openflights", 1, 1);
    Airport testAirport2 = new Airport(102, 500, "Test2", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2", "Test2", "Openflights", 4, 4);
    Airport testAirport3 = new Airport(103, 500, "Test3", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", "Test3", "Openflights", 10, 10);
    Airport testAirport4 = new Airport(104, 500, "Test4", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", "Test4", "Openflights", 2, 2);
    Airport testAirport5 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);

    private Loader loader = new Loader();
    private FlightLoader flightLoad = new FlightLoader();
    private Record testRecord;


    @Before
    public void setUp() throws IOException {

        Flight testFlight1 = flightLoad.loadFlightFile("data/flight.csv");
        Flight testFlight2 = flightLoad.loadFlightFile("data/flighttest.csv");

        ArrayList<Flight> testFlightList = new ArrayList<>();
        testFlightList.add(testFlight1);
        testFlightList.add(testFlight1);
        testFlightList.add(testFlight2);
        testFlightList.add(testFlight1);
        testFlightList.add(testFlight2);

        ArrayList<Route> testRouteList = new ArrayList<>();
        testRouteList.add(testRoute1);
        testRouteList.add(testRoute2);
        testRouteList.add(testRoute3);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);
        testRouteList.add(testRoute6);

        ArrayList<Airport> testAirportList = new ArrayList<>();
        testAirportList.add(testAirport1);
        testAirportList.add(testAirport2);
        testAirportList.add(testAirport3);
        testAirportList.add(testAirport4);
        testAirportList.add(testAirport5);

        ArrayList<Airline> testAirlineList = new ArrayList<>();
        testAirlineList.add(testAirline1);
        testAirlineList.add(testAirline2);
        testAirlineList.add(testAirline3);
        testAirlineList.add(testAirline4);
        testAirlineList.add(testAirline5);

        testRecord = new Record(testFlightList, testRouteList, testAirportList, testAirlineList);
    }

    @Test
    public void filterAirlinesTest() {
        ArrayList<Airline> filteredTestAirlineList = testRecord.filterAirlines(true, testRecord.getAirlineList());

        ArrayList<Airline> comparisonAirlineList = new ArrayList<>();
        comparisonAirlineList.add(testAirline1);
        comparisonAirlineList.add(testAirline3);
        comparisonAirlineList.add(testAirline5);

        assertEquals(comparisonAirlineList, filteredTestAirlineList);

        filteredTestAirlineList = testRecord.filterAirlines(false, testRecord.getAirlineList());

        comparisonAirlineList = new ArrayList<>();
        comparisonAirlineList.add(testAirline2);
        comparisonAirlineList.add(testAirline4);

        assertEquals(comparisonAirlineList, filteredTestAirlineList);
    }

    @Test
    public void filterRoutesStopsTest() throws IOException {

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesStops(true, testRecord.getRouteList());

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute1);
        comparisonRouteList.add(testRoute2);

        assertEquals(comparisonRouteList, filteredRouteList);

        filteredRouteList = testRecord.filterRoutesStops(false, testRecord.getRouteList());
        comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);
        comparisonRouteList.add(testRoute6);

        assertEquals(comparisonRouteList, filteredRouteList);

    }

    @Test
    public void rankAirportsTest() throws IOException {

        ArrayList<Airport> testAirports = testRecord.getAirportList();

        List<Airport> rankedAirports = testRecord.rankAirports(false, testAirports);

        ArrayList<Airport> comparisonAirportList = new ArrayList<Airport>();

        comparisonAirportList.add(testAirport5);
        comparisonAirportList.add(testAirport1);
        comparisonAirportList.add(testAirport4);
        comparisonAirportList.add(testAirport2);
        comparisonAirportList.add(testAirport3);

        assertEquals(comparisonAirportList, rankedAirports);

        rankedAirports = testRecord.rankAirports(true, testAirports);

        comparisonAirportList = new ArrayList<Airport>();

        comparisonAirportList.add(testAirport3);
        comparisonAirportList.add(testAirport2);
        comparisonAirportList.add(testAirport4);
        comparisonAirportList.add(testAirport1);
        comparisonAirportList.add(testAirport5);

        assertEquals(comparisonAirportList, rankedAirports);

    }

    @Test
    public void searchAirlinesTest() {
        List<Airline> airlineList = testRecord.searchAirlines("New Zealand", "country");

        ArrayList<Airline> expectedList = new ArrayList<>();
        expectedList.add(testAirline1);
        expectedList.add(testAirline2);
        expectedList.add(testAirline4);

        assertEquals(expectedList, airlineList);

        airlineList = testRecord.searchAirlines("Mongolia", "country");

        assertEquals(airlineList, new ArrayList<>());

        airlineList = testRecord.searchAirlines("test", "name");
        List<Airline> secondAirlineList = testRecord.searchAirlines("Test1", "name");
        List<Airline> invalidList = testRecord.searchAirlines("Good Name", "name");

        expectedList.add(2, testAirline3);
        expectedList.add(testAirline5);
        ArrayList<Airline> secondExpectedList = new ArrayList<>();
        secondExpectedList.add(testAirline1);

        assertEquals(expectedList, airlineList);
        assertEquals(secondExpectedList, secondAirlineList);
        assertEquals(new ArrayList<>(), invalidList);

    }

    @Test
    public void searchAirportsTest() throws IOException {
        ArrayList<Airport> searchResults = (ArrayList<Airport>) testRecord.searchAirports("est", "name");
        assertEquals(testRecord.getAirportList(), searchResults);

        ArrayList<Airport> testAirport = new ArrayList<>();
        ArrayList<Airport> comparisonList = new ArrayList<>();
        List<Airport> cityComparisonList = new ArrayList<>();

        Airport testAirport6 = new Airport(101, 500, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", "Test1", "Openflights", 0, 0);
        Airport testAirport7 = new Airport(102, 500, "Ranla ndom", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2", "Test2", "Openflights", 0, 0);
        Airport testAirport8 = new Airport(103, 500, "England", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", "Test3", "Openflights", 0, 0);
        Airport testAirport9 = new Airport(104, 500, "New Zealand", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", "Test4", "Openflights", 0, 0);
        Airport testAirport10 = new Airport(105, 500, "Orlando", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);
        Airport testAirport11 = new Airport(105, 500, "Test6", "Christchurch", "New Zealand", "YEET", "YEET", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);

        testAirport.add(testAirport6);
        testAirport.add(testAirport7);
        testAirport.add(testAirport8);
        testAirport.add(testAirport9);
        testAirport.add(testAirport10);
        testAirport.add(testAirport11);

        testRecord = new Record(null, null, testAirport, null);

        comparisonList.add(testAirport8);
        comparisonList.add(testAirport9);
        comparisonList.add(testAirport10);

        cityComparisonList.add(testAirport8);
        cityComparisonList.add(testAirport9);

        searchResults = (ArrayList<Airport>) testRecord.searchAirports("land", "name");
        assertEquals(comparisonList, searchResults);

        searchResults = (ArrayList<Airport>) testRecord.searchAirports("auckland", "name");
        assertEquals(0, searchResults.size());

        searchResults = (ArrayList<Airport>) testRecord.searchAirports("Sydney", "city");
        assertEquals(cityComparisonList, searchResults);
    }

    @Test
    public void searchRoutesTest() throws IOException {
        ArrayList<Route> searchResults = testRecord.searchRoutes("NZ", "airline");
        assertEquals(testRecord.getRouteList(), searchResults);

        ArrayList<Route> testRouteList = new ArrayList<>();
        ArrayList<Route> comparisonList = new ArrayList<>();

        Route testRoute7 = new Route("Air NZL", 500, "NZWN", 411, "NZCH", 511, 0, "DXaMIDDLE34", false);
        Route testRoute8 = new Route("AIR NZL", 501, "NZCH", 411, "WLG", 511, 0, "DXa34", false);
        Route testRoute9 = new Route("ubereats", 502, "NZAA", 411, "LAX", 511, 2, "DXa34ter", false);
        Route testRoute10 = new Route("frubereats", 503, "NZCH", 411, "ENG", 511, 1, "FRONTDXa34BACK", false);
        Route testRoute11 = new Route("frubercheats", 504, "NZWN", 411, "FAP", 511, 1, "Big PlaNE", false);

        testRouteList.add(testRoute1);
        testRouteList.add(testRoute2);
        testRouteList.add(testRoute3);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);
        testRouteList.add(testRoute6);
        testRouteList.add(testRoute7);
        testRouteList.add(testRoute8);
        testRouteList.add(testRoute9);
        testRouteList.add(testRoute10);
        testRouteList.add(testRoute11);

        testRecord = new Record(null, testRouteList, null, null);

        comparisonList.add(testRoute9);
        comparisonList.add(testRoute10);
        comparisonList.add(testRoute11);

        searchResults = testRecord.searchRoutes("uber", "airline");
        assertEquals(comparisonList, searchResults);

        searchResults = testRecord.searchRoutes("auckland", "airline");
        assertEquals(0, searchResults.size());

        searchResults = testRecord.searchRoutes("NZCH", "source airport");
        assertEquals(4, searchResults.size());

        searchResults = testRecord.searchRoutes("air nz", "airline");
        assertEquals(8, searchResults.size());

        searchResults = testRecord.searchRoutes("big plane", "equipment");
        assertEquals(1, searchResults.size());
        assertEquals(testRoute11, searchResults.get(0));

        ArrayList<Route> filteredRouteList = testRecord.searchRoutes("DXa34", "equipment");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute2);
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);
        comparisonRouteList.add(testRoute6);
        comparisonRouteList.add(testRoute8);
        comparisonRouteList.add(testRoute9);
        comparisonRouteList.add(testRoute10);

        assertEquals(comparisonRouteList, filteredRouteList);

        filteredRouteList = testRecord.searchRoutes("SYD", "destination airport");
        comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void searchCovidTest() {
        Covid searchResult = null;
        try {
            searchResult = Record.searchCovid("Mongolia");
            assertEquals("Mongolia", searchResult.getCountry());
            assertEquals(0.01, searchResult.getRiskDouble(), 0);

            searchResult = Record.searchCovid("Austria");
            assertEquals("Austria", searchResult.getCountry());
            assertEquals(0.4, searchResult.getRiskDouble(), 0);
        } catch (NoSuchFieldException e) {
            fail("Exception caught when inappropriate");
        }

        try {
            searchResult = Record.searchCovid("Australasia");
            fail("No exception caught when needed");
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

    }

    // Commented out as we won't be using this numRoutes function
    /*
    public void setNumRoutesTest() {

        ArrayList<Airline> testAirlineList = new ArrayList<Airline>();
        ArrayList<Flight> testFlightList = new ArrayList<Flight>();

        ArrayList<Airport> testAirportList = new ArrayList<Airport>();
        Airport testAirport6 = new Airport(101, 500, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", "Test1", "Openflights", 0, 0);
        Airport testAirport7 = new Airport(102, 500, "Test2", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2", "Test2", "Openflights", 0, 0);
        Airport testAirport8 = new Airport(103, 500, "Test3", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", "Test3", "Openflights", 0, 0);
        Airport testAirport9 = new Airport(104, 500, "Test4", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", "Test4", "Openflights", 0, 0);
        Airport testAirport10 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);
        Airport testAirport11 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "YEET", "YEET", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);


        ArrayList<Route> testRouteList = new ArrayList<Route>();
        Route testRoute1 = new Route("Air NZ", 500, "YSSY", 411, "NZCH", 511, 0, "DXa134", false);
        Route testRoute2 = new Route("Air NZ", 501, "NZCH", 411, "WLG", 511, 0, "DXa34", false);
        Route testRoute3 = new Route("Air NZ", 502, "NZAA", 411, "SYD", 511, 2, "DXa34", false);
        Route testRoute4 = new Route("Air NZ", 503, "NZCH", 411, "SYD", 511, 1, "DXa34", false);
        Route testRoute5 = new Route("Air NZ", 504, "SYD", 411, "SYD", 511, 1, "DXa34", false);
        Route testRoute6 = new Route("Air NZ", 505, "CHC", 411, "YSSY", 511, 4, "DXa34", false);

        testAirportList.add(testAirport6);
        testAirportList.add(testAirport7);
        testAirportList.add(testAirport8);
        testAirportList.add(testAirport9);
        testAirportList.add(testAirport10);
        testAirportList.add(testAirport11);

        testRouteList.add(testRoute1);
        testRouteList.add(testRoute2);
        testRouteList.add(testRoute3);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);
        testRouteList.add(testRoute6);

        Record testRecord = new Record(testFlightList, testRouteList, testAirportList, testAirlineList);
        List<Airport> testAirports = testRecord.getAirportList();

        //TODO: Phase 3, implement faster route calculation
        //testRecord.setNumRoutesDest();
        //testRecord.setNumRoutesSource();

        //assertEquals(6, testRecord.rankAirports(true, testAirports).get(0).getTotalRoutes());
        //assertEquals(4, testRecord.rankAirports(true, testAirports).get(4).getTotalRoutes());
        //assertEquals(0, testRecord.rankAirports(true, testAirports).get(5).getTotalRoutes());

    }
     */

    /*
    public void searchFlightsTest() throws IOException {

        ArrayList<Flight> flightList = testRecord.searchFlights(true, "Test1");
        assertEquals(3, flightList.size());

        flightList = testRecord.searchFlights(false, "Test4");
        assertEquals(2, flightList.size());

        flightList = testRecord.searchFlights(false, "???");
        assertEquals(0, flightList.size());

    }
     */

    @Test
    public void addAirportsTest() throws IOException {

        assertEquals(testRecord.getAirportList().size(), 5);

        ArrayList<Airport> testAirportList = new ArrayList<>();
        testAirportList.add(testAirport1);
        testAirportList.add(testAirport2);
        testAirportList.add(testAirport5);

        Airport testAirport6 = new Airport(119, 500, "Test5", "Christchurch", "New Zealand", "YEET", "YEET", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);

        testAirportList.add(testAirport6);

        testRecord.addAirports(testAirportList);
        assertEquals(testRecord.getAirportList().size(), 6);
    }

    @Test
    public void addAirlinesTest() throws IOException {

        assertEquals(testRecord.getAirlineList().size(), 5);

        ArrayList<Airline> testAirlineList = new ArrayList<>();
        testAirlineList.add(testAirline1);
        testAirlineList.add(testAirline2);
        testAirlineList.add(testAirline3);

        Airline testAirline6 = new Airline(114, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

        testAirlineList.add(testAirline6);

        testRecord.addAirlines(testAirlineList);
        assertEquals(testRecord.getAirlineList().size(), 6);
    }

    @Test
    public void addRoutesTest() throws IOException {

        assertEquals(testRecord.getRouteList().size(), 6);

        ArrayList<Route> testRouteList = new ArrayList<>();
        testRouteList.add(testRoute1);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);
        testRouteList.add(testRoute1);

        Route testRoute7 = new Route("Air NZL", 505, "CHC", 411, "YSSY", 511, 4, "DXa34", false);

        testRouteList.add(testRoute7);

        testRecord.addRoutes(testRouteList);
        assertEquals(testRecord.getRouteList().size(), 7);
    }

    @Test
    public void addFlightsTest() throws IOException {

        assertEquals(testRecord.getFlightList().size(), 5);

        ArrayList<Flight> testFlightList = new ArrayList<>();

        testRecord.addFlights(flightLoad.loadFlightFile("data/flight.csv"));

        assertEquals(2, testRecord.getFlightList().size());
    }
}
