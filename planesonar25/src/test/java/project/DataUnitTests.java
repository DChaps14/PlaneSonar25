package project;

import org.junit.Test;
import project.model.*;

import java.io.IOException;

import static org.junit.Assert.*;

public class DataUnitTests {

    @Test
    public void airportEquals() {
        Airport testAirport1 = new Airport(101, 9001, "Christchurch International Airport", "Christchurch", "New Zealand", "BX45", "C9845", 40.0, 40.0, 40, 5, "Y", "NZST", "Default", "Openflights", 46, 45);
        Airport testAirport2 = new Airport(101, 9001, "Christchurch International Airport", "Christchurch", "New Zealand", "BX45", "C9845", 40.0, 40.0, 40, 5, "Y", "NZST", "Default", "Openflights", 46, 45);

        assertTrue(testAirport1.equals(testAirport2));

        testAirport1 = new Airport(100, 9001, "Christchurch International Airport", "Christchurch", "New Zealand", "BX45", "C9845", 40.0, 40.0, 40, 5, "Y", "NZST", "Default", "Openflights", 46, 45);

        assertFalse(testAirport1.equals(testAirport2));
    }

    @Test
    public void airlineEquals() {
        Airline testAirline1 = new Airline(500, "Air NZ", true, "New Zealand", "AirNZ", "BD45X", "678234A", "34345B");
        Airline testAirline2 = new Airline(500, "Air NZ", true, "New Zealand", "AirNZ", "BD45X", "678234A", "34345B");

        assertTrue(testAirline1.equals(testAirline2));

        testAirline1 = new Airline(501, "Air NZ", true, "New Zealand", "AirNZ", "BD45X", "678234A", "34345B");

        assertFalse(testAirline1.equals(testAirline2));
    }

    @Test
    public void routeEquals() {
        Route testRoute1 = new Route("Air NZ", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);
        Route testRoute2 = new Route("Air NZ", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);

        assertTrue(testRoute1.equals(testRoute2));

        testRoute1 = new Route("Air NZL", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);

        assertFalse(testRoute1.equals(testRoute2));
    }

    @Test
    public void flightEquals() throws IOException {

        FlightLoader flightLoad = new FlightLoader();
        Flight testFlight1 = flightLoad.loadFlightFile("data/flight.csv");
        Flight testFlight2 = flightLoad.loadFlightFile("data/flight.csv");

        assertTrue(testFlight1.equals(testFlight2));

        testFlight2 = flightLoad.loadFlightFile("data/flighttest.csv");

        assertFalse(testFlight2.equals(testFlight1));
    }
}
