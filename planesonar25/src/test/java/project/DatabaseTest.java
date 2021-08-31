package project;

import org.junit.Before;
import org.junit.Test;
import project.controller.Database;
import project.model.*;

import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class DatabaseTest {

    private Airline testAirline1 = new Airline(100, "Test1", true, "New Zealand", "Test1", "Test1", "Test1", "Test1");
    private Airline testAirline2 = new Airline(101, "Test2", false, "New Zealand", "Test2", "Test2", "Test2", "Test2");
    private Airline testAirline3 = new Airline(102, "Test3", true, "Australia", "Test3", "Test3", "Test3", "Test3");
    private Airline testAirline4 = new Airline(103, "Test4", false, "New Zealand", "Test4", "Test4", "Test4", "Test4");
    private Airline testAirline5 = new Airline(104, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

    private Route testRoute1 = new Route("Air NZ", 500, "NZWN", 411, "NZCH", 511, 0, "DXa134", false);
    private Route testRoute2 = new Route("Air NZ", 501, "NZCH", 411, "WLG", 511, 0, "DXa34", false);
    private Route testRoute3 = new Route("Air NZ", 502, "NZAA", 411, "SYD", 511, 2, "DXa34", false);
    private Route testRoute4 = new Route("Air NZ", 503, "NZCH", 411, "SYD", 511, 1, "DXa34", false);
    private Route testRoute5 = new Route("Air NZ", 504, "NZWN", 411, "SYD", 511, 1, "DXa34", false);
    private Route testRoute6 = new Route("Air NZ", 505, "CHC", 411, "YSSY", 511, 4, "DXa34", false);

    private Airport testAirport1 = new Airport(101, 500, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", "Test1", "Openflights", 1, 1);
    private Airport testAirport2 = new Airport(102, 500, "Test2", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2", "Test2", "Openflights", 4, 4);
    private Airport testAirport3 = new Airport(103, 500, "Test3", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", "Test3", "Openflights", 10, 10);
    private Airport testAirport4 = new Airport(104, 500, "Test4", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", "Test4", "Openflights", 2, 2);
    private Airport testAirport5 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);

    @Before
    public void setUp() {
        //Drop each of the current tables in the database to ensure a clear testing database
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            String dropStatement = "DROP TABLE airports";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE airlines";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE routes";
            stmt.executeUpdate(dropStatement);
            System.out.println("Tables dropped");
        } catch (SQLException e) {}
        Database.setupDatabase();
    }


    @Test
    public void getAllAirportsTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport5);

        ArrayList<Airport> airports = new ArrayList<Airport>();
        airports.add(testAirport1);
        airports.add(testAirport2);
        airports.add(testAirport5);

        ArrayList<Airport> retrievedAirports = Database.getAllAirports();
        assertEquals(retrievedAirports, airports);

        Database.addNewAirport(testAirport3);
        Database.addNewAirport(testAirport4);
        airports.add(2, testAirport3);
        airports.add(3, testAirport4);

        retrievedAirports = Database.getAllAirports();
        assertEquals(retrievedAirports, airports);
    }

    @Test
    public void getAllAirlinesTest() {
        Database.addNewAirline(testAirline4);
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline3);

        //Create an arrayList in order of ID's, as SQL will order the data points in this way
        ArrayList<Airline> airlines = new ArrayList<Airline>();
        airlines.add(testAirline1);
        airlines.add(testAirline3);
        airlines.add(testAirline4);

        ArrayList<Airline> retrievedAirlines = Database.getAllAirlines();
        assertEquals(retrievedAirlines, airlines);

        Database.addNewAirline(testAirline5);
        Database.addNewAirline(testAirline2);
        airlines.add(1, testAirline2);
        airlines.add(testAirline5);

        retrievedAirlines = Database.getAllAirlines();
        assertEquals(retrievedAirlines, airlines);
    }

    @Test
    public void getAllRoutesTest() {
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute3);
        Database.addNewRoute(testRoute4);

        ArrayList<Route> routes = new ArrayList<Route>();
        routes.add(testRoute2);
        routes.add(testRoute3);
        routes.add(testRoute4);

        ArrayList<Route> retrievedRoutes = Database.getAllRoutes();
        assertEquals(retrievedRoutes, routes);

        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute1);
        Database.addNewRoute(testRoute6);
        routes.add(testRoute5);
        routes.add(0, testRoute1);
        routes.add(testRoute6);

        retrievedRoutes = Database.getAllRoutes();
        assertEquals(retrievedRoutes, routes);
    }

    @Test
    public void removeAirportTest() {
        Database.populateAirportTableColumns();
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport3);
        Database.addNewAirport(testAirport4);
        Database.addNewAirport(testAirport5);

        try {
            //Remove Airline 2
            Database.removeAirport("id", "101");
            Database.removeAirport("airportName", "Test4");
            Database.removeAirport("city", "Christchurch");
            Database.removeAirport("city", "New Zealand");
        } catch (NoSuchFieldException e) {
            fail("Exception thrown when not appropriate");
        }

        try {
            Database.removeAirport("Not an appropriate column", "Not an appropriate value");
            fail("Exception not thrown with inappropriate data value");
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Airport> resultingAirports = Database.getAllAirports();
        assertTrue(resultingAirports.size() == 1);
        assertEquals(resultingAirports.get(0), testAirport3);
    }

    @Test
    public void removeAirlineTest() {
        Database.populateAirlineTableColumns();
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline2);
        Database.addNewAirline(testAirline3);
        Database.addNewAirline(testAirline4);
        Database.addNewAirline(testAirline5);

        try {
            Database.removeAirline("id", "101");
            Database.removeAirline("airlineName", "Test3");
            Database.removeAirline("active", "1");
            //Remove none, but don't throw an error
            Database.removeAirline("id", "New Zealand");
        } catch (NoSuchFieldException e) {
            fail("Exception thrown when not appropriate");
        }

        try {
            Database.removeAirline("No such column", "bad value");
            fail("Exception not thrown with inappropriate data value");
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Airline> resultingAirlines = Database.getAllAirlines();
        assertTrue(resultingAirlines.size() == 1);
        assertEquals(resultingAirlines.get(0), testAirline4);
    }

    @Test
    public void removeRouteTest() {
        Database.populateRouteTableColumns();
        Database.addNewRoute(testRoute1);
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute3);
        Database.addNewRoute(testRoute4);
        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute6);

        try {
            Database.removeRoute("id", "502");
            Database.removeRoute("numStops", "0");
            Database.removeRoute("destAirport", "SYD");
            Database.removeRoute("sourceAirport", "Totally Real Airport");
        } catch (NoSuchFieldException e) {
            fail("Exception caught when not expected");
        }

        try {
            Database.removeRoute("routeName", "This is a route name");
            fail("Exception not caught when expected.");
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Route> resultingRoutes = Database.getAllRoutes();
        assertTrue(resultingRoutes.size() == 1);
        assertEquals(resultingRoutes.get(0), testRoute6);

    }

    @Test
    public void addNewAirportTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport4);

        ArrayList<Integer> airportIDs = new ArrayList<Integer>();
        airportIDs.add(testAirport1.getId());
        airportIDs.add(testAirport2.getId());
        airportIDs.add(testAirport4.getId());

        String testQuery = "SELECT id FROM airports";
        ArrayList<Integer> retrievedIDs = new ArrayList<Integer>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(testQuery)) {
            while (rs.next()) {
                retrievedIDs.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            fail("SQLException occurred");
            System.err.println(e.getMessage());
        }

        assertEquals(retrievedIDs, airportIDs);

    }

    @Test
    public void addNewAirlineTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport4);

        ArrayList<Integer> airportIDs = new ArrayList<Integer>();
        airportIDs.add(testAirport1.getId());
        airportIDs.add(testAirport2.getId());
        airportIDs.add(testAirport4.getId());

        String testQuery = "SELECT id FROM airports";
        ArrayList<Integer> retrievedIDs = new ArrayList<Integer>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(testQuery)) {
            while (rs.next()) {
                retrievedIDs.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            fail("SQLException occurred");
            System.err.println(e.getMessage());
        }

        assertEquals(retrievedIDs, airportIDs);

    }

    @Test
    public void addNewRouteTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport4);

        ArrayList<Integer> airportIDs = new ArrayList<Integer>();
        airportIDs.add(testAirport1.getId());
        airportIDs.add(testAirport2.getId());
        airportIDs.add(testAirport4.getId());

        String testQuery = "SELECT id FROM airports";
        ArrayList<Integer> retrievedIDs = new ArrayList<Integer>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(testQuery)) {
            while (rs.next()) {
                retrievedIDs.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            fail("SQLException occurred");
            System.err.println(e.getMessage());
        }

        assertEquals(retrievedIDs, airportIDs);
    }

    @Test
    public void generateRecordTest() {
        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute4);
        ArrayList<Route> routes = new ArrayList<Route>();
        routes.add(testRoute2);
        routes.add(testRoute4);
        routes.add(testRoute5);

        Database.addNewAirline(testAirline3);
        Database.addNewAirline(testAirline1);
        ArrayList<Airline> airlines = new ArrayList<Airline>();
        airlines.add(testAirline1);
        airlines.add(testAirline3);

        Database.addNewAirport(testAirport4);
        Database.addNewAirport(testAirport5);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport1);
        ArrayList<Airport> airports = new ArrayList<Airport>();
        airports.add(testAirport1);
        airports.add(testAirport2);
        airports.add(testAirport4);
        airports.add(testAirport5);

        Record testRecord = Database.generateRecord();
        ArrayList<Airline> recordAirlines = testRecord.getAirlineList();
        ArrayList<Airport> recordAirports = testRecord.getAirportList();
        ArrayList<Route> recordRoutes = testRecord.getRouteList();

        assertEquals(recordAirlines, airlines);
        assertEquals(recordAirports, airports);
        assertEquals(recordRoutes, routes);

    }
}