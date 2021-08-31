package project;

import org.junit.Before;
import org.junit.Test;
import project.model.Airport;
import project.model.Covid;
import project.model.Record;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AirportTest {

    @Test
    public void distanceTest() {
        Airport testAuckAirport1 = new Airport(1, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 37.0082, 174.7850, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testChchAirport2 = new Airport(2, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 43.4876, 172.5374, 37, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        double dist1 = testAuckAirport1.distance(testChchAirport2);
        Airport testErrorAirport1 = new Airport(3, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 360, 360, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testErrorAirport2 = new Airport(4, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 360, 360, 37, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        double dist2 = testErrorAirport1.distance(testErrorAirport2);
        Airport testErrorAirport3 = new Airport(3, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 25, 360, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testErrorAirport4 = new Airport(4, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 360, 34, 37, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        double dist3 = testErrorAirport3.distance(testErrorAirport4);
        Airport testErrorAirport5 = new Airport(3, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 360, 36, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testErrorAirport6= new Airport(4, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 25, 360, 37, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        double dist4 = testErrorAirport5.distance(testErrorAirport6);
        Airport testBoundaryAirport1 = new Airport(3, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 90, -180, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testBoundaryAirport2= new Airport(4, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 0, 110, 37, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        double dist5 = testBoundaryAirport1.distance(testBoundaryAirport2);
        Airport testBoundaryAirport3 = new Airport(3, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", -90, -180, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testBoundaryAirport4= new Airport(4, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 90, 180, 37, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        double dist6 = testBoundaryAirport3.distance(testBoundaryAirport4);
        assertEquals(dist1, 745.692, 5);
        assertEquals(dist2, -1, 0);
        assertEquals(dist3, -1, 0);
        assertEquals(dist4, -1, 0);
        assertEquals(dist5, 10010, 5);
        assertEquals(dist6, 20020, 5);
    }

    @Test
    public void getCovidRiskTest() {
        Airport testAirport1 = new Airport(1, 0, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 37.0082, 174.7850, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testAirport2 = new Airport(1, 0, "LAX", "LA", "United States", "LAX", "SOME", 37.0082, 174.7850, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);
        Airport testAirport3 = new Airport(1, 0, "Gold Coast Airport", "Coolangatta", "Australia", "OOL", "SOME", 37.0082, 174.7850, 7, 12, "dunno", "NZST", "Airport", "Source", 0, 0);

        assertNotEquals(testAirport1.getRisk(), 0);
        assertNotEquals(testAirport2.getRisk(), 0);
        assertNotEquals(testAirport3.getRisk(), 0);

        assertEquals(0.03, testAirport1.getRisk(), 0);
        assertEquals(2.02, testAirport2.getRisk(), 0);
        assertEquals(0.11, testAirport3.getRisk(), 0);
    }
}
