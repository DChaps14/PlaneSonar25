package project;
import org.junit.Test;
import project.model.Covid;

import static org.junit.Assert.assertEquals;

public class CovidTest {
    @Test
    public void riskTest(){

        Covid country1 = new Covid("Bolivia","15/08/2020",826.408,332.733,11673029);
        Covid country2 = new Covid("Bolivia","15/08/2020",4263.408,332.733,100000);
        Covid country3 = new Covid("Bolivia","15/08/2020",9123.412,332.733,100000);
        Covid country4 = new Covid("Bolivia","15/08/2020",25232.412,332.733,100000);
        Covid country5 = new Covid("Bolivia","15/08/2020",1000000,332.733,100000);

        Covid country6 = new Covid("Test below Extremely Low","15/08/2020",900,332.733,100000);
        Covid country7 = new Covid("Test Extremely Low","15/08/2020",1000,332.733,100000);
        Covid country8 = new Covid("Test just over Extremely Low","15/08/2020",1100,332.733,100000);
        Covid country9 = new Covid("Test below Low","15/08/2020",4900,332.733,100000);
        Covid country10 = new Covid("Test Low","15/08/2020",5000,332.733,100000);
        Covid country11 = new Covid("Test just over Low","15/08/2020",5100,332.733,100000);
        Covid country12 = new Covid("Test below Medium","15/08/2020",9900,332.733,100000);
        Covid country13 = new Covid("Test Medium","15/08/2020",10000,332.733,100000);
        Covid country14 = new Covid("Test just over Medium","15/08/2020",11000,332.733,100000);
        Covid country15 = new Covid("Test below High","15/08/2020",49000,332.733,100000);
        Covid country16 = new Covid("Test High","15/08/2020",50000,332.733,100000);
        Covid country17 = new Covid("Test just over High (Extreme)","15/08/2020",51000,332.733,100000);


        assertEquals("Extremely Low", country1.getRiskString());
        assertEquals("Low", country2.getRiskString());
        assertEquals("Medium", country3.getRiskString());
        assertEquals("High", country4.getRiskString());
        assertEquals("Extreme", country5.getRiskString());


        //boundary testing for each level of risk
        assertEquals("Extremely Low", country6.getRiskString());
        assertEquals("Extremely Low", country7.getRiskString());
        assertEquals("Low", country8.getRiskString());

        assertEquals("Low", country9.getRiskString());
        assertEquals("Low", country10.getRiskString());
        assertEquals("Medium", country11.getRiskString());

        assertEquals("Medium", country12.getRiskString());
        assertEquals("Medium", country13.getRiskString());
        assertEquals("High", country14.getRiskString());

        assertEquals("High", country15.getRiskString());
        assertEquals("High", country16.getRiskString());
        assertEquals("Extreme", country17.getRiskString());



    }

    @Test
    public void getRiskDoubleTest() {
        //testing the risk function is working as expected
        Covid country1 = new Covid("Bolivia","15/08/2020",8263.408,332.733,11673029);
        Covid country2 = new Covid("Bolivia","15/08/2020",900000.12,332.733,11673029);
        Covid country3 = new Covid("Bolivia","15/08/2020",10.0,332.733,11673029);

        assertEquals(00.83, country1.getRiskDouble(), 0);
        assertEquals(90, country2.getRiskDouble(), 0);
        //Rounded to 2 d.p., so expected risk will be 0
        assertEquals(00.00, country3.getRiskDouble(), 0);
    }
}
