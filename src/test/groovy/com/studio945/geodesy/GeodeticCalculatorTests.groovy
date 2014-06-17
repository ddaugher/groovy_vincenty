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
    6179016.136 == geoCurve.ellipsoidalDistance.round(3) //, 0.001
    51.76792142 == geoCurve.azimuth.round(8) //, 0.0000001
    291.75529334 == geoCurve.reverseAzimuth.round(8) //, 0.0000001
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
    1521788.826 == geoMeasurement.pointToPoint.round(3)

    then: "the ellipsoidal distance should be correct"
    1521782.748 == geoMeasurement.ellipsoidalDistance.round(3)

    then: "the asimuth value should be correct"
    271.21039153 == geoMeasurement.azimuth.round(8)

    then: "the reverse asimuth should be correct"
    80.38029386 == geoMeasurement.reverseAzimuth.round(8)
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
    19970718.422432076 == geoCurve.ellipsoidalDistance.round(9)

    then: "the asimuth value should be correct"
    90.0004877491174 == geoCurve.azimuth.round(13)

    then: "the reverse asimuth should be correct"
    270.0004877491174 == geoCurve.reverseAzimuth.round(13)
  }

  def "test anti podal 2"() {
    given: "a valid geodetic calculator"
    def geocalc = new GeodeticCalculator()

    and: "set global coordinate position 1"
    GlobalCoordinates p1 = new GlobalCoordinates(11, 80);

    and: "set global coordinate position 2"
    GlobalCoordinates p2 = new GlobalCoordinates(-10, -100);

    when: "calculate the geodetic measurement"
    GeodeticCurve geoCurve = geocalc.calculateGeodeticCurve(Ellipsoid.WGS84, p1, p2);

    then: "the ellipsoidal distance should be correct"
    19893320.272061437 == geoCurve.ellipsoidalDistance.round(9)

    then: "the asimuth value should be correct"
    360.0 == geoCurve.azimuth.round(0)

    then: "the reverse asimuth should be correct"
    0.0 == geoCurve.reverseAzimuth.round(0)
  }

  def "test inverse with direct"() {
    given: "a valid geodetic calculator"
    def geocalc = new GeodeticCalculator()

    and: "set Lincoln Memorial coordinates"
    GlobalCoordinates lincolnMemorial = new GlobalCoordinates(38.88922, -77.04978);

    and: "set Eiffel Tower coordinates"
    GlobalCoordinates eiffelTower = new GlobalCoordinates(48.85889, 2.29583);

    when: "calculate the geodetic measurement"
    GeodeticCurve geoCurve = geocalc.calculateGeodeticCurve(Ellipsoid.WGS84, lincolnMemorial, eiffelTower);

    and: "plug the result into the direct solution"
    double[] endBearing = new double[1];
    GlobalCoordinates dest = geocalc.calculateEndingGlobalCoordinates(Ellipsoid.WGS84, lincolnMemorial, geoCurve.azimuth, geoCurve.ellipsoidalDistance, endBearing);

    then: "the destination latitude will be"
    eiffelTower.getLatitude().round(8) == dest.getLatitude().round(8)

    then: "the destination longitude will be"
    eiffelTower.getLongitude().round(8) == dest.getLongitude().round(8)
  }

  def "test pole crossing"() {
    given: "a valid geodetic calculator"
    def geocalc = new GeodeticCalculator()

    when: "set Lincoln Memorial coordinates"
    GlobalCoordinates lincolnMemorial = new GlobalCoordinates(38.88922, -77.04978);

    and: "create the expected destination"
    GlobalCoordinates expected = new GlobalCoordinates(85.60006433, 92.17243943);

    and: "calculate the ending global coordinates"
    double startBearing = 1.0;
    double distance = 6179016.13586;
    GlobalCoordinates dest = geocalc.calculateEndingGlobalCoordinates(Ellipsoid.WGS84, lincolnMemorial, startBearing, distance);

    then: "the destination latitude will be"
    dest.getLatitude().round(8) == expected.getLatitude().round(8)

    then: "the destination longitude will be"
    dest.getLongitude().round(8) == expected.getLongitude().round(8)
  }

//  public void testConstructor() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    assertEquals("geodeticCalculator.TwoPi", 6.283185307179586, ((Number) getPrivateField(geodeticCalculator, "TwoPi")).doubleValue(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createSphere();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.0);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(0.0), new Double(0.9984971498638638)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "abs", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GlobalCoordinates result = geodeticCalculator.calculateEndingGlobalCoordinates(ellipsoid, start, 100.0, 1000.0, (double[]) null);
//    assertEquals("result.getLatitude()", 0.008993216059195674, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates1() throws Throwable {
//    GlobalCoordinates result = new GeodeticCalculator().calculateEndingGlobalCoordinates(EllipsoidTestHelper.createGRS80(), GlobalCoordinatesTestHelper.createEquatorGreenwich(), 100.0, 1000.0, (double[]) null);
//    assertEquals("result.getLatitude()", -0.0015704211100455723, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates2() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createGRS80();
//    double[] endBearing = new double[4];
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.0);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(0.0), new Double(-0.6156614753256583)}, new Double(3.141592653589793), 1);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GlobalCoordinates result = geodeticCalculator.calculateEndingGlobalCoordinates(ellipsoid, start, 100.0, 1000.0, endBearing);
//    assertEquals("result.getLatitude()", 0.00904369477004252, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates3() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    double[] endBearing = new double[0];
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createSphere();
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.02085003381585247);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.02085003381585247)}, new Double(0.02085305567739928), 1);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GlobalCoordinates result = geodeticCalculator.calculateEndingGlobalCoordinates(ellipsoid, start, 100.0, 1000.0, endBearing);
//    assertEquals("result.getLatitude()", 0.008991261346949386, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates4() throws Throwable {
//    double[] doubles = new double[1];
//    GlobalCoordinates result = new GeodeticCalculator().calculateEndingGlobalCoordinates(EllipsoidTestHelper.createGRS80(), GlobalCoordinatesTestHelper.createEquatorGreenwich(), 100.0, 1000.0, doubles);
//    assertEquals("doubles[0]", 99.99999987876062, doubles[0], 1.0E-6);
//    assertEquals("result.getLatitude()", -0.0015704211100455723, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates5() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    double[] endBearing = new double[0];
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createNorthPole();
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createGRS80();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 1.5707963267948966);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(1.633123935319537E16), 1);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GlobalCoordinates result = geodeticCalculator.calculateEndingGlobalCoordinates(ellipsoid, start, 100.0, -1.0, endBearing);
//    assertEquals("result.getLatitude()", 89.99999104696599, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinates6() throws Throwable {
//    Ellipsoid ellipsoid = (Ellipsoid) callPrivateMethod("org.gavaghan.geodesy.Ellipsoid", "<init>", new Class[] {double.class, double.class, double.class, double.class}, null, new Object[] {new Double(1.9795342683792114), new Double(100.0), new Double(0.0), new Double(1000.0)});
//    GlobalCoordinates result = new GeodeticCalculator().calculateEndingGlobalCoordinates(ellipsoid, GlobalCoordinatesTestHelper.createNorthPole(), 100.0, 1000.0);
//    assertEquals("result.getLatitude()", -29.814301498789007, result.getLatitude(), 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 100.0);
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), new GlobalCoordinates(0.0, 100.0), globalCoordinates);
//    assertEquals("result.azimuth", 0.0, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve1() throws Throwable {
//    Ellipsoid ellipsoid = (Ellipsoid) callPrivateMethod("org.gavaghan.geodesy.Ellipsoid", "<init>", new Class[] {double.class, double.class, double.class, double.class}, null, new Object[] {new Double(100.0), new Double(1000.0), new Double(1.9641906023025513), new Double(0.0)});
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, new GlobalCoordinates(1.0, 0.0), new GlobalCoordinates(0.6347497701644897, 2.227550983428955));
//    assertEquals("result.azimuth", 180.0, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve2() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createGRS80();
//    GlobalCoordinates end = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(ellipsoid.getFlattening(), 0.7503077387809753);
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(1000.0), 0.0);
//    Mockingbird.setReturnValue(end.getLatitude(), 0.9072631001472473);
//    Mockingbird.setReturnValue(Angle.toRadians(-1.0), 0.015834728279420517);
//    Mockingbird.setReturnValue(end.getLongitude(), 6.235450267791748);
//    Mockingbird.setReturnValue(Angle.toRadians(1.0), 0.10882913751732813);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(0.0)}, new Double(1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.015834728279420517)}, new Double(0.01583605187147488), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(0.10882913751732813)}, new Double(0.10861443994999569), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(0.10882913751732813)}, new Double(0.994083951904641), 1);
//    GeodeticCurve geodeticCurve = (GeodeticCurve) Mockingbird.getProxyObject(GeodeticCurve.class);
//    Mockingbird.replaceObjectForRecording(GeodeticCurve.class, "<init>(double,double,double)", geodeticCurve);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GeodeticCurve result = geodeticCalculator.calculateGeodeticCurve(ellipsoid, start, end);
//    assertSame("result", geodeticCurve, result);
//  }
//
//  public void testCalculateGeodeticCurve3() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(1.2332849502563477, 100.0);
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), new GlobalCoordinates(1.2948811054229736, 100.0), globalCoordinates);
//    assertEquals("result.azimuth", 180.0, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve4() throws Throwable {
//    new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), GlobalCoordinatesTestHelper.createEquatorGreenwich(), GlobalCoordinatesTestHelper.createEquatorIDL());
//    assertTrue("Test call resulted in expected outcome", true);
//  }
//
//  public void testCalculateGeodeticCurve5() throws Throwable {
//    GlobalCoordinates equatorGreenwich = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), equatorGreenwich, equatorGreenwich);
//    assertTrue("Test call resulted in expected outcome", true);
//  }
//
//  public void testCalculateGeodeticCurve6() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(0.0, 1.0);
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createSphere(), globalCoordinates, new GlobalCoordinates(1.0, 1.001));
//    assertEquals("result.azimuth", 0.057289942535358455, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve7() throws Throwable {
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), GlobalCoordinatesTestHelper.createNorthPole(), GlobalCoordinatesTestHelper.createEquatorIDL());
//    assertEquals("result.azimuth", 9.999999999999998, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve8() throws Throwable {
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), new GlobalCoordinates(90.001, 100.0), GlobalCoordinatesTestHelper.createEquatorGreenwich());
//    assertEquals("result.azimuth", 99.99999479885369, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve9() throws Throwable {
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createSphere(), GlobalCoordinatesTestHelper.createEquatorGreenwich(), new GlobalCoordinates(0.0, 1000.0));
//    assertEquals("result.azimuth", 270.0, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve10() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    GlobalCoordinates end = GlobalCoordinatesTestHelper.createEquatorIDL();
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createSphere();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(1000.0), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(0.0), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(-1.0), 3.141592653589793);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(0.0)}, new Double(1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(0.0)}, new Double(1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(1.2246467991473532E-16), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(-1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(1.2246467991473532E-16), new Double(-1.0)}, new Double(3.141592653589793), 1);
//    Mockingbird.setReturnValue(true, Math.class, "asin", "(double)double", new Object[] {new Double(1.0)}, new Double(1.5707963267948966), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(6.123233995736766E-17), 1);
//    Mockingbird.setReturnValue(true, Math.class, "abs", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(1.2246467991473532E-16), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(-1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(1.2246467991473532E-16), new Double(-1.0)}, new Double(3.141592653589793), 1);
//    Mockingbird.setReturnValue(true, Math.class, "asin", "(double)double", new Object[] {new Double(1.0)}, new Double(1.5707963267948966), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(6.123233995736766E-17), 1);
//    Mockingbird.setReturnValue(true, Math.class, "abs", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(1.2246467991473532E-16), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(-1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(1.2246467991473532E-16), new Double(-1.0)}, new Double(3.141592653589793), 1);
//    Mockingbird.setReturnValue(true, Math.class, "asin", "(double)double", new Object[] {new Double(1.0)}, new Double(1.5707963267948966), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(6.123233995736766E-17), 1);
//    Mockingbird.setReturnValue(true, Math.class, "abs", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(1.2246467991473532E-16), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(-1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(1.2246467991473532E-16), new Double(0.0)}, new Double(1.5707963267948966), 1);
//    Mockingbird.setReturnValue(Angle.toDegrees(100.0), 90.0);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(1.2246467991473532E-16), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(3.141592653589793)}, new Double(-1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "atan2", "(double,double)double", new Object[] {new Double(1.2246467991473532E-16), new Double(-0.0)}, new Double(1.5707963267948966), 1);
//    GeodeticCurve geodeticCurve = (GeodeticCurve) Mockingbird.getProxyObject(GeodeticCurve.class);
//    Mockingbird.replaceObjectForRecording(GeodeticCurve.class, "<init>(double,double,double)", geodeticCurve);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GeodeticCurve result = geodeticCalculator.calculateGeodeticCurve(ellipsoid, start, end);
//    assertSame("result", geodeticCurve, result);
//  }
//
//  public void testCalculateGeodeticCurve11() throws Throwable {
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), new GlobalCoordinates(90.001, 1.0), new GlobalCoordinates(11.0, 1.001));
//    assertEquals("result.azimuth", 359.9990000034733, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve12() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(1000.0, 0.0);
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, globalCoordinates, new GlobalCoordinates(0.0, 1.0));
//    assertEquals("result.azimuth", 1.0163673464214555, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve13() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createGRS80();
//    GlobalCoordinates end = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(ellipsoid.getFlattening(), 0.21668410301208496);
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.003515077454545489);
//    Mockingbird.setReturnValue(Angle.toRadians(1000.0), 0.8377580409572782);
//    Mockingbird.setReturnValue(Angle.toRadians(0.0), 0.8519608122087252);
//    Mockingbird.setReturnValue(end.getLongitude(), -0.20985156297683716);
//    Mockingbird.setReturnValue(Angle.toRadians(-1.0), -0.0036626007144020412);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.003515077454545489)}, new Double(0.003515091931779321), 1);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.8519608122087252)}, new Double(1.1428444247537557), 1);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GeodeticCurve result = geodeticCalculator.calculateGeodeticCurve(ellipsoid, start, end);
//    assertEquals("result.azimuth", 180.38051146461564, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve14() throws Throwable {
//    Ellipsoid ellipsoid = (Ellipsoid) callPrivateMethod("org.gavaghan.geodesy.Ellipsoid", "<init>", new Class[] {double.class, double.class, double.class, double.class}, null, new Object[] {new Double(100.0), new Double(1000.0), new Double(-1.0), new Double(0.0)});
//    new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, GlobalCoordinatesTestHelper.createEquatorGreenwich(), GlobalCoordinatesTestHelper.createEquatorIDL());
//    assertTrue("Test call resulted in expected outcome", true);
//  }
//
//  public void testCalculateGeodeticCurve15() throws Throwable {
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), new GlobalCoordinates(-990.010800009, 1.0), new GlobalCoordinates(-10.0, 1.001));
//    assertEquals("result.azimuth", 179.99900003211255, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticCurve16() throws Throwable {
//    GeodeticCurve result = new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), new GlobalCoordinates(100.0, 2.227550983428955), new GlobalCoordinates(213.3948211669922, 1.8377326726913452));
//    assertEquals("result.azimuth", 180.35501962740037, result.azimuth, 1.0E-6);
//  }
//
//  public void testCalculateGeodeticMeasurement() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(GlobalCoordinatesTestHelper.createEquatorGreenwich(), 1000.0);
//    GeodeticMeasurement result = new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), new GlobalPosition(GlobalCoordinatesTestHelper.createEquatorIDL(), 100.0), globalPosition);
//    assertEquals("result.getElevationChange()", 900.0, result.getElevationChange(), 1.0E-6);
//  }
//
//  public void testCalculateGeodeticMeasurement1() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(GlobalCoordinatesTestHelper.createEquatorGreenwich(), 100.0);
//    GeodeticMeasurement result = new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), globalPosition, new GlobalPosition(0.0, 100.0, 1000.0));
//    assertEquals("result.getElevationChange()", 900.0, result.getElevationChange(), 1.0E-6);
//  }
//
//  public void testCalculateGeodeticMeasurement2() throws Throwable {
//    GlobalPosition start = new GlobalPosition(1000.0, 0.0, 100.0);
//    GeodeticMeasurement result = new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), start, new GlobalPosition(GlobalCoordinatesTestHelper.createNorthPole(), 100.0));
//    assertEquals("result.getElevationChange()", 0.0, result.getElevationChange(), 1.0E-6);
//  }
//
//  public void testCalculateGeodeticMeasurement3() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    GlobalPosition end = (GlobalPosition) Mockingbird.getProxyObject(GlobalPosition.class);
//    GlobalPosition start = (GlobalPosition) Mockingbird.getProxyObject(GlobalPosition.class);
//    Ellipsoid refEllipsoid = EllipsoidTestHelper.createGRS80();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(start.getElevation(), 91.0);
//    Mockingbird.setReturnValue(end.getElevation(), -3.7560677528381348);
//    Mockingbird.setReturnValue(start.getLatitude(), 0.10000000149011612);
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 0.0017453292780017621);
//    Mockingbird.setReturnValue(end.getLatitude(), 0.8358408808708191);
//    Mockingbird.setReturnValue(Angle.toRadians(1000.0), 0.014588175393965482);
//    Ellipsoid gRS80 = EllipsoidTestHelper.createGRS80();
//    Mockingbird.setReturnValue(Ellipsoid.createFromSemiMajorAxisAndFlattening(100.0, 1000.0), gRS80);
//    Mockingbird.setReturnValue(gRS80.getFlattening(), 0.0033528106647474805);
//    Mockingbird.setReturnValue(start.getLatitude(), 0.10000000149011612);
//    Mockingbird.setReturnValue(Angle.toRadians(0.0), 0.0017453292780017621);
//    Mockingbird.setReturnValue(start.getLongitude(), 179.0);
//    Mockingbird.setReturnValue(Angle.toRadians(-1.0), 3.12413936106985);
//    Mockingbird.setReturnValue(end.getLatitude(), 0.8358408808708191);
//    Mockingbird.setReturnValue(Angle.toRadians(1.0), 0.014588175393965482);
//    Mockingbird.setReturnValue(end.getLongitude(), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(10.0), 0.0);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0017453292780017621)}, new Double(0.001745331050196312), 1);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.014588175393965482)}, new Double(0.014589210342241005), 1);
//    Mockingbird.replaceObjectForRecording(GeodeticCurve.class, "<init>(double,double,double)", Mockingbird.getProxyObject(GeodeticCurve.class));
//    GeodeticMeasurement geodeticMeasurement = (GeodeticMeasurement) Mockingbird.getProxyObject(GeodeticMeasurement.class);
//    Mockingbird.replaceObjectForRecording(GeodeticMeasurement.class, "<init>(org.gavaghan.geodesy.GeodeticCurve,double)", geodeticMeasurement);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GeodeticMeasurement result = geodeticCalculator.calculateGeodeticMeasurement(refEllipsoid, start, end);
//    assertSame("result", geodeticMeasurement, result);
//  }
//
//  public void testCalculateGeodeticMeasurement4() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    Ellipsoid refEllipsoid = EllipsoidTestHelper.createGRS80();
//    GlobalPosition start = (GlobalPosition) Mockingbird.getProxyObject(GlobalPosition.class);
//    GlobalPosition end = (GlobalPosition) Mockingbird.getProxyObject(GlobalPosition.class);
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(start.getElevation(), 0.6431910991668701);
//    Mockingbird.setReturnValue(end.getElevation(), 0.5309370756149292);
//    Mockingbird.setReturnValue(start.getLatitude(), -51.998999999999995);
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), -0.9075537577445313);
//    Mockingbird.setReturnValue(end.getLatitude(), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(1000.0), 0.0);
//    Ellipsoid gRS80 = EllipsoidTestHelper.createGRS80();
//    Mockingbird.setReturnValue(Ellipsoid.createFromSemiMajorAxisAndFlattening(100.0, 1000.0), gRS80);
//    Mockingbird.setReturnValue(gRS80.getFlattening(), 2.909834146499634);
//    Mockingbird.setReturnValue(start.getLatitude(), -51.998999999999995);
//    Mockingbird.setReturnValue(Angle.toRadians(0.0), -0.9075537577445313);
//    Mockingbird.setReturnValue(start.getLongitude(), -178.7009313106537);
//    Mockingbird.setReturnValue(Angle.toRadians(-1.0), -3.118919627751133);
//    Mockingbird.setReturnValue(end.getLatitude(), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(1.0), 0.0);
//    Mockingbird.setReturnValue(end.getLongitude(), 0.0);
//    Mockingbird.setReturnValue(Angle.toRadians(10.0), 0.0);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(-0.9075537577445313)}, new Double(-1.2798955870625395), 1);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.0)}, new Double(0.0), 1);
//    Mockingbird.replaceObjectForRecording(GeodeticCurve.class, "<init>(double,double,double)", Mockingbird.getProxyObject(GeodeticCurve.class));
//    GeodeticMeasurement geodeticMeasurement = (GeodeticMeasurement) Mockingbird.getProxyObject(GeodeticMeasurement.class);
//    Mockingbird.replaceObjectForRecording(GeodeticMeasurement.class, "<init>(org.gavaghan.geodesy.GeodeticCurve,double)", geodeticMeasurement);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GeodeticMeasurement result = geodeticCalculator.calculateGeodeticMeasurement(refEllipsoid, start, end);
//    assertSame("result", geodeticMeasurement, result);
//  }
//
//  public void testCalculateGeodeticMeasurement5() throws Throwable {
//    GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
//    GlobalPosition end = (GlobalPosition) Mockingbird.getProxyObject(GlobalPosition.class);
//    GlobalPosition start = (GlobalPosition) Mockingbird.getProxyObject(GlobalPosition.class);
//    Ellipsoid refEllipsoid = EllipsoidTestHelper.createGRS80();
//    Mockingbird.enterRecordingMode();
//    Mockingbird.setReturnValue(start.getElevation(), 0.0);
//    Mockingbird.setReturnValue(end.getElevation(), 1.0);
//    Mockingbird.setReturnValue(start.getLatitude(), 90.0);
//    Mockingbird.setReturnValue(Angle.toRadians(100.0), 1.5707963267948966);
//    Mockingbird.setReturnValue(end.getLatitude(), 2.219294548034668);
//    Mockingbird.setReturnValue(Angle.toRadians(1000.0), 0.038733996934764404);
//    Ellipsoid gRS80 = EllipsoidTestHelper.createGRS80();
//    Mockingbird.setReturnValue(Ellipsoid.createFromSemiMajorAxisAndFlattening(100.0, 1000.0), gRS80);
//    Mockingbird.setReturnValue(gRS80.getFlattening(), 0.0033528106647474805);
//    Mockingbird.setReturnValue(start.getLatitude(), 90.0);
//    Mockingbird.setReturnValue(Angle.toRadians(0.0), 1.5707963267948966);
//    Mockingbird.setReturnValue(start.getLongitude(), 10.0);
//    Mockingbird.setReturnValue(Angle.toRadians(-1.0), 0.17453292519943295);
//    Mockingbird.setReturnValue(end.getLatitude(), 2.219294548034668);
//    Mockingbird.setReturnValue(Angle.toRadians(1.0), 0.038733996934764404);
//    Mockingbird.setReturnValue(end.getLongitude(), -179.0);
//    Mockingbird.setReturnValue(Angle.toRadians(10000.0), -3.12413936106985);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(1.633123935319537E16), 1);
//    Mockingbird.setReturnValue(true, Math.class, "sin", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(1.0), 1);
//    Mockingbird.setReturnValue(true, Math.class, "cos", "(double)double", new Object[] {new Double(1.5707963267948966)}, new Double(6.123233995736766E-17), 1);
//    Mockingbird.setReturnValue(true, Math.class, "tan", "(double)double", new Object[] {new Double(0.038733996934764404)}, new Double(0.03875337972963642), 1);
//    Mockingbird.replaceObjectForRecording(GeodeticCurve.class, "<init>(double,double,double)", Mockingbird.getProxyObject(GeodeticCurve.class));
//    GeodeticMeasurement geodeticMeasurement = (GeodeticMeasurement) Mockingbird.getProxyObject(GeodeticMeasurement.class);
//    Mockingbird.replaceObjectForRecording(GeodeticMeasurement.class, "<init>(org.gavaghan.geodesy.GeodeticCurve,double)", geodeticMeasurement);
//    Mockingbird.enterTestMode(GeodeticCalculator.class);
//    GeodeticMeasurement result = geodeticCalculator.calculateGeodeticMeasurement(refEllipsoid, start, end);
//    assertSame("result", geodeticMeasurement, result);
//  }
//
//  public void testCalculateGeodeticMeasurement6() throws Throwable {
//    GeodeticMeasurement result = new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), new GlobalPosition(GlobalCoordinatesTestHelper.createEquatorIDL(), 100.0), new GlobalPosition(GlobalCoordinatesTestHelper.createNorthPole(), 1000.0));
//    assertEquals("result.getElevationChange()", 900.0, result.getElevationChange(), 1.0E-6);
//  }
//
//  public void testCalculateGeodeticMeasurement7() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(GlobalCoordinatesTestHelper.createNorthPole(), 100.0);
//    GeodeticMeasurement result = new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), globalPosition, new GlobalPosition(1000.0, 0.0, 100.0));
//    assertEquals("result.getElevationChange()", 0.0, result.getElevationChange(), 1.0E-6);
//  }
//
//  public void testCalculateGeodeticMeasurement8() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(GlobalCoordinatesTestHelper.createEquatorGreenwich(), 100.0);
//    GeodeticMeasurement result = new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), globalPosition, new GlobalPosition(1.468879222869873, 100.0, 1000.0));
//    assertEquals("result.getElevationChange()", 900.0, result.getElevationChange(), 1.0E-6);
//  }
//
//  public void testCalculateEndingGlobalCoordinatesThrowsNullPointerException() throws Throwable {
//    GlobalCoordinates start = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    double[] endBearing = new double[3];
//    try {
//      new GeodeticCalculator().calculateEndingGlobalCoordinates(null, start, 100.0, 1000.0, endBearing);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//      assertEquals("start.getLatitude()", 0.0, start.getLatitude(), 1.0E-6);
//      assertEquals("endBearing.length", 3, endBearing.length);
//    }
//  }
//
//  public void testCalculateEndingGlobalCoordinatesThrowsNullPointerException1() throws Throwable {
//    double[] doubles = new double[3];
//    try {
//      new GeodeticCalculator().calculateEndingGlobalCoordinates(EllipsoidTestHelper.createGRS80(), null, 100.0, 1000.0, doubles);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }
//
//  public void testCalculateEndingGlobalCoordinatesThrowsNullPointerException2() throws Throwable {
//    Ellipsoid ellipsoid = EllipsoidTestHelper.createGRS80();
//    try {
//      new GeodeticCalculator().calculateEndingGlobalCoordinates(ellipsoid, null, 100.0, 1000.0);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//      assertEquals("ellipsoid.getFlattening()", 0.003352810681182319, ellipsoid.getFlattening(), 1.0E-6);
//    }
//  }
//
//  public void testCalculateGeodeticCurveThrowsNullPointerException() throws Throwable {
//    try {
//      new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), GlobalCoordinatesTestHelper.createEquatorGreenwich(), null);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }
//
//  public void testCalculateGeodeticCurveThrowsNullPointerException1() throws Throwable {
//    GlobalCoordinates equatorGreenwich = GlobalCoordinatesTestHelper.createEquatorGreenwich();
//    try {
//      new GeodeticCalculator().calculateGeodeticCurve(null, equatorGreenwich, equatorGreenwich);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }
//
//  public void testCalculateGeodeticCurveThrowsNullPointerException2() throws Throwable {
//    try {
//      new GeodeticCalculator().calculateGeodeticCurve(EllipsoidTestHelper.createGRS80(), null, GlobalCoordinatesTestHelper.createEquatorGreenwich());
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }
//
//  public void testCalculateGeodeticMeasurementThrowsNullPointerException() throws Throwable {
//    try {
//      new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), null, new GlobalPosition(100.0, 1000.0, 0.0));
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }
//
//  public void testCalculateGeodeticMeasurementThrowsNullPointerException1() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(100.0, 1000.0, 0.0);
//    try {
//      new GeodeticCalculator().calculateGeodeticMeasurement(null, globalPosition, globalPosition);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }
//
//  public void testCalculateGeodeticMeasurementThrowsNullPointerException2() throws Throwable {
//    try {
//      new GeodeticCalculator().calculateGeodeticMeasurement(EllipsoidTestHelper.createGRS80(), new GlobalPosition(100.0, 1000.0, 0.0), null);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GeodeticCalculator.class, ex);
//    }
//  }

}
