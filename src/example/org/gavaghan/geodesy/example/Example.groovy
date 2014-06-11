/* Geodesy by Mike Gavaghan
 * 
 * http://www.gavaghan.org/blog/free-source-code/geodesy-library-vincentys-formula-java/
 * 
 * This code may be freely used and modified on any personal or professional
 * project.  It comes with no warranty.
 *
 * BitCoin tips graciously accepted at 1FB63FYQMy7hpC2ANVhZ5mSgAZEtY1aVLf
 */
package org.gavaghan.geodesy.example;

import com.studio945.geodesy.Ellipsoid;
import com.studio945.geodesy.GeodeticCalculator;
import com.studio945.geodesy.GeodeticCurve;
import com.studio945.geodesy.GeodeticMeasurement;
import com.studio945.geodesy.GlobalCoordinates;
import com.studio945.geodesy.GlobalPosition;

public class Example
{
   /**
    * Calculate the destination if we start at:
    *    Lincoln Memorial in Washington, D.C --> 38.8892N, 77.04978W
    *         and travel at
    *    51.7679 degrees for 6179.016136 kilometers
    * 
    *    WGS84 reference ellipsoid
    */
   static void TwoDimensionalDirectCalculation()
   {
     // instantiate the calculator
     GeodeticCalculator geoCalc = new GeodeticCalculator();

     // select a reference elllipsoid
     Ellipsoid reference = Ellipsoid.WGS84;

     // set Lincoln Memorial coordinates
     GlobalCoordinates lincolnMemorial;
     lincolnMemorial = new GlobalCoordinates( 38.88922, -77.04978 );

     // set the direction and distance
     double startBearing = 51.7679;
     double distance = 6179016.13586;

     // find the destination
     double[] endBearing = new double[1];
     GlobalCoordinates dest = geoCalc.calculateEndingGlobalCoordinates(reference, lincolnMemorial, startBearing, distance, endBearing);

     System.out.println("Travel from Lincoln Memorial at 51.767921 deg for 6179.016 km");
     System.out.printf("   Destination: %1.4f%s", dest.getLatitude(), (dest.getLatitude() > 0) ? "N" : "S" );
     System.out.printf(", %1.4f%s\n", dest.getLongitude(), (dest.getLongitude() > 0) ? "E" : "W");
     System.out.printf("   End Bearing: %1.2f degrees\n", endBearing[0]);
   }

	/**
	 * Calculate the two-dimensional path from 
	 * 
	 * 	 Lincoln Memorial in Washington, D.C --> 38.8892N, 77.04978W 
	 * 
	 *     to 
	 *     
	 *   Eiffel Tower in Paris --> 48.85889N, 2.29583E
	 *   
	 * using WGS84 reference ellipsoid
	 */
	static void TwoDimensionalCalculation()
	{
		// instantiate the calculator
		GeodeticCalculator geoCalc = new GeodeticCalculator();

		// select a reference elllipsoid
		Ellipsoid reference = Ellipsoid.WGS84;

		// set Lincoln Memorial coordinates
		GlobalCoordinates lincolnMemorial;
		lincolnMemorial = new GlobalCoordinates(38.88922, -77.04978);

		// set Eiffel Tower coordinates
		GlobalCoordinates eiffelTower;
		eiffelTower = new GlobalCoordinates(48.85889, 2.29583);

		// calculate the geodetic curve
		GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference, lincolnMemorial, eiffelTower);
		double ellipseKilometers = geoCurve.getEllipsoidalDistance() / 1000.0;
		double ellipseMiles = ellipseKilometers * 0.621371192;

		System.out.println("2-D path from Lincoln Memorial to Eiffel Tower using WGS84");
		System.out.printf(
		    "   Ellipsoidal Distance: %1.2f kilometers (%1.2f miles)\n",
		    ellipseKilometers, ellipseMiles);
		System.out.printf("   Azimuth:              %1.2f degrees\n", geoCurve.getAzimuth());
		System.out.printf("   Reverse Azimuth:      %1.2f degrees\n", geoCurve.getReverseAzimuth());
	}

	/**
	 * Calculate the three-dimensional path from 
	 * 
	 *   Pike's Peak in Colorado --> 38.840511N, 105.0445896W, 4301 meters
	 *   
	 *     to 
	 *     
	 *   Alcatraz Island --> 37.826389N, 122.4225W, sea level 
	 *   
	 * using WGS84 reference ellipsoid
	 */
	static void ThreeDimensionalCalculation()
	{
		// instantiate the calculator
		GeodeticCalculator geoCalc = new GeodeticCalculator();

		// select a reference elllipsoid
		Ellipsoid reference = Ellipsoid.WGS84;

		// set Pike's Peak position
		GlobalPosition pikesPeak;
		pikesPeak = new GlobalPosition(38.840511, -105.0445896, 4301.0);

		// set Alcatraz Island coordinates
		GlobalPosition alcatrazIsland;
		alcatrazIsland = new GlobalPosition(37.826389, -122.4225, 0.0);

		// calculate the geodetic measurement
		GeodeticMeasurement geoMeasurement;
		double p2pKilometers;
		double p2pMiles;
		double elevChangeMeters;
		double elevChangeFeet;

		geoMeasurement = geoCalc.calculateGeodeticMeasurement(reference, pikesPeak, alcatrazIsland);
		p2pKilometers = geoMeasurement.getPointToPointDistance() / 1000.0;
		p2pMiles = p2pKilometers * 0.621371192;
		elevChangeMeters = geoMeasurement.getElevationChange();
		elevChangeFeet = elevChangeMeters * 3.2808399;

		System.out.println("3-D path from Pike's Peak to Alcatraz Island using WGS84");
		System.out.printf(
		    "   Point-to-Point Distance: %1.2f kilometers (%1.2f miles)\n",
		    p2pKilometers, p2pMiles);
		System.out.printf(
		    "   Elevation change:        %1.1f meters (%1.1f} feet)\n",
		    elevChangeMeters, elevChangeFeet);
		System.out.printf("   Azimuth:                 %1.2f degrees\n",
		    geoMeasurement.getAzimuth());
		System.out.printf("   Reverse Azimuth:         %1.2f degrees\n",
		    geoMeasurement.getReverseAzimuth());
	}

	static public void main(String[] args)
	{
	    TwoDimensionalDirectCalculation();

	    System.out.println();

		TwoDimensionalCalculation();

		System.out.println();

		ThreeDimensionalCalculation();

		System.out.println();
	}
}
