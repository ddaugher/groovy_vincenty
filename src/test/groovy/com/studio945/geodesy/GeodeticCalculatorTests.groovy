package com.studio945.geodesy;

public class GeodeticCalculatorTests extends spock.lang.Specification {
  def "should calculate Geodetic curve using Global Coordinates for start and end position"() {
    given: "a valid geodetic calculator"
    def geocalc = new GeodeticCalculator()

    and: "we use the WGS84 ellipsoid"
    Ellipsoid reference = Ellipsoid.WGS84

    and: "set Lincoln Memorial coordinates"
    GlobalCoordinates lincolnMemorial = new GlobalCoordinates(38.88922, -77.04978)

    and: "set Eiffel Tower coordinates"
    GlobalCoordinates eiffelTower = new GlobalCoordinates(48.85889, 2.29583);

    when: "calculate the geodetic curve"
    GeodeticCurve geoCurve = geocalc.calculateGeodeticCurve(reference, lincolnMemorial, eiffelTower)

    then: "the values should be correct"
    6179016.136 == geoCurve.getEllipsoidalDistance().round(3) //, 0.001
    51.76792142 == geoCurve.getAzimuth().round(8) //, 0.0000001
    291.75529334 == geoCurve.getReverseAzimuth().round(8) //, 0.0000001
  }

  def "should calculate geodetic measurement using GlobalPosition for start and end position"() {
    given: "a valid geodetic calculator"
    def geocalc = new GeodeticCalculator()

    and: "set Pike's Peak position"
    GlobalPosition pikesPeak = new GlobalPosition(38.840511, -105.0445896, 4301.0)

    and: "set Alcatraz Island coordinates"
    GlobalPosition alcatrazIsland = new GlobalPosition(37.826389, -122.4225, 0.0)

    when: "calculate the geodetic measurement"
    GeodeticMeasurement geoMeasurement = geocalc.calculateGeodeticMeasurement(Ellipsoid.WGS84, pikesPeak, alcatrazIsland)

    then: "the elevation change should correct"
    -4301.0 == geoMeasurement.getElevationChange().round(8)

    then: "the point to point distance should be correct"
    1521788.826 == geoMeasurement.getPointToPointDistance().round(3)

    then: "the ellipsoidal distance should be correct"
    1521782.748 == geoMeasurement.getEllipsoidalDistance().round(3)

    then: "the asimuth value should be correct"
    271.21039153 == geoMeasurement.getAzimuth().round(8)

    then: "the reverse asimuth should be correct"
    80.38029386 == geoMeasurement.getReverseAzimuth().round(8)
  }

  def "test anti podal 1"() {
    given: "a valid geodetic calculator"
    def geocalc = new GeodeticCalculator()

    and: "set global coordinate position 1"
    GlobalCoordinates p1 = new GlobalCoordinates(10, 80.6);

    and: "set global coordinate position 2"
    GlobalCoordinates p2 = new GlobalCoordinates(-10, -100);

    when: "calculate the geodetic measurement"
    GeodeticCurve geoCurve = geocalc.calculateGeodeticCurve(Ellipsoid.WGS84, p1, p2);

    then: "the ellipsoidal distance should be correct"
    19970718.422432076 == geoCurve.getEllipsoidalDistance().round(9)

    then: "the asimuth value should be correct"
    90.0004877491174 == geoCurve.getAzimuth().round(13)

    then: "the reverse asimuth should be correct"
    270.0004877491174 == geoCurve.getReverseAzimuth().round(13)
  }

//   public void testAntiPodal2()
//   {
//      // instantiate the calculator
//      GeodeticCalculator geoCalc = new GeodeticCalculator();
//
//      // select a reference elllipsoid
//      Ellipsoid reference = Ellipsoid.WGS84;
//
//      // set position 1
//      GlobalCoordinates p1;
//      p1 = new GlobalCoordinates(11, 80);
//
//      // set position 2
//      GlobalCoordinates p2;
//      p2 = new GlobalCoordinates(-10, -100);
//
//      // calculate the geodetic measurement
//      GeodeticCurve geoCurve;
//
//      geoCurve = geoCalc.calculateGeodeticCurve(reference, p1, p2);
//
//      assertEquals( 19893320.272061437, geoCurve.getEllipsoidalDistance(), 0.001);
//      assertEquals(360.0, geoCurve.getAzimuth(), 0.0000001);
//      assertEquals(0.0, geoCurve.getReverseAzimuth(), 0.0000001);
//   }
//
//   public void testInverseWithDirect()
//   {
//      // instantiate the calculator
//      GeodeticCalculator geoCalc = new GeodeticCalculator();
//
//      // select a reference elllipsoid
//      Ellipsoid reference = Ellipsoid.WGS84;
//
//      // set Lincoln Memorial coordinates
//      GlobalCoordinates lincolnMemorial;
//      lincolnMemorial = new GlobalCoordinates(38.88922, -77.04978);
//
//      // set Eiffel Tower coordinates
//      GlobalCoordinates eiffelTower;
//      eiffelTower = new GlobalCoordinates(48.85889, 2.29583);
//
//      // calculate the geodetic curve
//      GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference, lincolnMemorial, eiffelTower);
//
//      // now, plug the result into to direct solution
//      GlobalCoordinates dest;
//      double[] endBearing = new double[1];
//
//      dest = geoCalc.calculateEndingGlobalCoordinates(reference, lincolnMemorial, geoCurve.getAzimuth(), geoCurve.getEllipsoidalDistance(), endBearing);
//
//      assertEquals( eiffelTower.getLatitude(), dest.getLatitude(), 0.0000001 );
//      assertEquals( eiffelTower.getLongitude(), dest.getLongitude(), 0.0000001 );
//   }
//
//   public void testPoleCrossing()
//   {
//     // instantiate the calculator
//     GeodeticCalculator geoCalc = new GeodeticCalculator();
//
//     // select a reference elllipsoid
//     Ellipsoid reference = Ellipsoid.WGS84;
//
//     // set Lincoln Memorial coordinates
//     GlobalCoordinates lincolnMemorial;
//     lincolnMemorial = new GlobalCoordinates(38.88922, -77.04978);
//
//     // set a bearing of 1.0deg (almost straight up) and a distance
//     double startBearing = 1.0;
//     double distance = 6179016.13586;
//
//     // set the expected destination
//     GlobalCoordinates expected;
//     expected = new GlobalCoordinates(85.60006433, 92.17243943);
//
//     // calculate the ending global coordinates
//     GlobalCoordinates dest = geoCalc.calculateEndingGlobalCoordinates(reference, lincolnMemorial, startBearing, distance );
//
//     assertEquals(expected.getLatitude(), dest.getLatitude(), 0.0000001);
//     assertEquals(expected.getLongitude(), dest.getLongitude(), 0.0000001);
//   }
}
