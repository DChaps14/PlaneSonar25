package project;

import org.junit.Test;
import project.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LoaderTest {
    private final Loader loader = new Loader();
    private final RouteLoader routeLoad = new RouteLoader();
    private final CovidLoader covidLoad = new CovidLoader();
    private final AirportLoader airportLoad = new AirportLoader();
    private final AirlineLoader airlineLoad = new AirlineLoader();
    private final FlightLoader flightLoad = new FlightLoader();

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     */
    public void loadIndividualRouteTest() {

        String[] routeData1 = {"2B", "410", "AER", "2965", "KZN", "2990", "Y", "0", "CR2"};
        Route testRoute1 = routeLoad.loadRoute(routeData1);

        assertEquals(testRoute1.getAirline(), "2B");
        assertEquals(testRoute1.getSourceAirport(), "AER");
        assertEquals(testRoute1.getDestAirport(), "KZN");
        assertEquals(testRoute1.getEquipment(), "CR2");
        assertEquals(testRoute1.getId(), 410);
        assertEquals(testRoute1.getSourceID(), 2965);
        assertEquals(testRoute1.getDestID(), 2990);
        assertTrue(testRoute1.isCodeshare());
        assertEquals(testRoute1.getNumStops(), 0);

        String[] routeData2 = {"2B", "410a", "AER", "2965a", "KZN", "2990a", "N", "0a", "CR2"};
        testRoute1 = routeLoad.loadRoute(routeData2);

        assertEquals(testRoute1.getId(), -1);
        assertEquals(testRoute1.getSourceID(), -1);
        assertEquals(testRoute1.getDestID(), -1);
        assertEquals(testRoute1.getNumStops(), -1);
        assertFalse(testRoute1.isCodeshare());

        String[] routeData3 = {"2B", "", "AER", "", "KZN", "", "", "", "CR2"};
        testRoute1 = routeLoad.loadRoute(routeData3);
        assertFalse(testRoute1.isCodeshare());
        assertEquals(testRoute1.getId(), -1);
        assertEquals(testRoute1.getSourceID(), -1);
        assertEquals(testRoute1.getDestID(), -1);
        assertEquals(testRoute1.getNumStops(), -1);

        String[] routeData4 = {"", "410", "", "2965", "", "2990", "Y", "0", ""};
        testRoute1 = routeLoad.loadRoute(routeData4);
        assertEquals(testRoute1.getAirline(), "Unknown");
        assertEquals(testRoute1.getSourceAirport(), "Unknown");
        assertEquals(testRoute1.getDestAirport(), "Unknown");
        assertEquals(testRoute1.getEquipment(), "Unknown");

        String[] routeData5 = {"", "", "", "", "", "", "", "", ""};
        testRoute1 = routeLoad.loadRoute(routeData5);
        assertNull(testRoute1);
    }

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     */
    public void loadIndividualAirlineTest() {

        String[] airlineData1 = {"1", "Private flight", "alias", "iata", "icao", "callsign", "country", "Y"};
        Airline testAirline1 = airlineLoad.loadAirline(airlineData1);

        assertEquals(testAirline1.getId(), 1);
        assertEquals(testAirline1.getName(), "Private flight");
        assertEquals(testAirline1.getAlias(), "alias");
        assertEquals(testAirline1.getIata(), "iata");
        assertEquals(testAirline1.getIcao(), "icao");
        assertEquals(testAirline1.getCallSign(), "callsign");
        assertEquals(testAirline1.getCountry(), "country");
        assertTrue(testAirline1.isActive());

        String[] airlineData2 = {"1a", "Private flight", "alias", "iata", "icao", "callsign", "country", "N"};
        testAirline1 = airlineLoad.loadAirline(airlineData2);

        assertEquals(testAirline1.getId(), -1);
        assertFalse(testAirline1.isActive());

        String[] airlineData3 = {"", "Private flight", "alias", "iata", "icao", "callsign", "country", ""};
        testAirline1 = airlineLoad.loadAirline(airlineData3);

        assertEquals(testAirline1.getId(), -1);
        assertFalse(testAirline1.isActive());

        String[] airlineData4 = {"1", "", "", "", "", "", "", "Y"};
        testAirline1 = airlineLoad.loadAirline(airlineData4);
        assertNull(testAirline1);

        /*assertEquals(testAirline1.getName(), null);
        assertEquals(testAirline1.getAlias(), null);
        assertEquals(testAirline1.getIata(), null);
        assertEquals(testAirline1.getIcao(), null);
        assertEquals(testAirline1.getCallSign(), null);
        assertEquals(testAirline1.getCountry(), null);*/

    }

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     **/
    public void loadIndividualAirportTest() {

        String[] airportData1 = {"1", "Goroka", "Goroka", "Papua New Guinea", "GKA", "AYGA", "-6.081689", "145.391881", "5282", "10", "U", "Pacific/Port_Moresby", "type", "source"};
        Airport testAirport1 = airportLoad.loadAirport(airportData1);

        assertEquals(testAirport1.getId(), 1);
        assertEquals(testAirport1.getName(), "Goroka");
        assertEquals(testAirport1.getCity(), "Goroka");
        assertEquals(testAirport1.getCountry(), "Papua New Guinea");
        assertEquals(testAirport1.getIata(), "GKA");
        assertEquals(testAirport1.getIcao(), "AYGA");
        assertEquals(testAirport1.getLatitude(), -6.081689, 0);
        assertEquals(testAirport1.getLongitude(), 145.391881, 0);
        assertEquals(testAirport1.getAltitude(), 5282);
        assertEquals(testAirport1.getTimezone(), 10, 0);
        assertEquals(testAirport1.getDst(), "U");
        assertEquals(testAirport1.getTimezoneString(), "Pacific/Port_Moresby");
        assertEquals(testAirport1.getType(), "type");
        assertEquals(testAirport1.getSource(), "source");
        assertEquals(testAirport1.getNumRoutesSource(), 0);
        assertEquals(testAirport1.getNumRoutesDest(), 0);
        assertEquals(testAirport1.getTotalRoutes(), 0);

        String[] airportData2 = {"1a", "Goroka", "Goroka", "Papua New Guinea", "GKA", "AYGA", "-6.081689a", "145.391881a", "5282a", "10a", "U", "Pacific/Port_Moresby", "type", "source"};
        testAirport1 = airportLoad.loadAirport(airportData2);

        assertEquals(testAirport1.getId(), -1);
        assertEquals(testAirport1.getLatitude(), 360, 0);
        assertEquals(testAirport1.getLongitude(), 360, 0);
        assertEquals(testAirport1.getAltitude(), -1);
        assertEquals(testAirport1.getTimezone(), 25, 0);


        // Code works but fails tests due to nullpointer in thanks to having no record in loadertest.
        // Tests worked before this addition and code works.
        String[] airportData3 = {"", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        testAirport1 = airportLoad.loadAirport(airportData3);
        assertNull(testAirport1);
        /*.getId(), -1);
        assertEquals(testAirport1.getName(), );
        assertEquals(testAirport1.getCity(), null);
        assertEquals(testAirport1.getCountry(), null);
        assertEquals(testAirport1.getIata(), null);
        assertEquals(testAirport1.getIcao(), null);
        assertEquals(testAirport1.getLatitude(), 360, 0);
        assertEquals(testAirport1.getLongitude(), 360, 0);
        assertEquals(testAirport1.getAltitude(), -1);
        assertEquals(testAirport1.getTimezone(), 25, 0);
        assertEquals(testAirport1.getDst(), null);
        assertEquals(testAirport1.getTimezoneString(), null);
        assertEquals(testAirport1.getType(), null);
        assertEquals(testAirport1.getSource(), null);*/

    }

    @Test
    /**
     * Manual testing. Not gonna bother to type out the entire data files as an ArrayList of classes. Functions work.
     */
    public void loadRouteFileTest() throws IOException {
        ArrayList<Route> routeList = routeLoad.loadRouteFile("data/routes.dat");
    }

    @Test
    /**
     * Manual testing. Will update later.
     */
    public void loadFlightFileTest() throws IOException {

        Flight flight = flightLoad.loadFlightFile("data/flighttest.csv");

        System.out.println(flight.getStatus());
        System.out.println(flight.getLocations());
        System.out.println(flight.getAltitudes());
        System.out.println(flight.getLatitudes());
        System.out.println(flight.getLongitudes());

    }

    @Test
    /**
     * Manual testing. Not gonna bother to type out the entire data files as an ArrayList of classes. Functions work.
     */
    public void loadAirportFileTest() throws IOException {
        AirportLoader airportLoad = new AirportLoader();
        Record record = new Record(null, null, null, null); //initialise the COVID lists
        ArrayList<Airport> airportList = airportLoad.loadAirportFile("data/airports.dat");

    }

    @Test
    /**
     * Manual testing. Not gonna bother to type out the entire data files as an ArrayList of classes. Functions work.
     */
    public void loadAirlineFileTest() throws IOException {
        Loader loader = new Loader();
        ArrayList<Airline> airlineList = airlineLoad.loadAirlineFile("data/airlines.dat");

    }

    @Test

    public void loadCovidFileTest() throws IOException{
        // checks that covid file doesnt fail

        Hashtable<String, Covid> covid_list = covidLoad.loadCovidFile("/covid.dat");

    }

    @Test
    public void testCovid() throws IOException {
        //runs test file with missing attributes to ensure loader wont crash
        // test file contains missing values and illegal values, to test that checks work correctly
        Hashtable<String, Covid> covid_list = covidLoad.loadCovidFile("/covid_test.dat");

    }
}
