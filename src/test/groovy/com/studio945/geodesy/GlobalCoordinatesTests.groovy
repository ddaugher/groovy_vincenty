package com.studio945.geodesy;

public class GlobalCoordinatesTests extends spock.lang.Specification {

  def "should create proper GlobalCoordinates for North Pole"() {
    when: "instantiate a new instance"
    def northPole = GlobalCoordinates.northPole()

    then: "should return proper North Pole GlobalCoordinates"
    90.0d == northPole.latitude
    10.0d == northPole.longitude
  }

  def "should create proper GlobalCoordinates for South Pole"() {
    when: "instantiate a new instance"
    def southPole = GlobalCoordinates.southPole()

    then: "should return proper South Pole GlobalCoordinates"
    90.0d == southPole.latitude
    10.0d == southPole.longitude
  }

  def "should create proper GlobalCoordinates for Equator Greenwich"() {
    when: "instantiate a new instance"
    def equator = GlobalCoordinates.createEquatorGreenwich()

    then: "should return proper Equator Greenwich Global Coordinates"
    0.0d == equator.latitude
    0.0d == equator.longitude
  }

  def "should create proper GlobalCoordinates for Equator IDL"() {
    when: "instantiate a new instance"
    def equator = GlobalCoordinates.createEquatorIDL()

    then: "should return proper Equator IDL Global Coordinates"
    0.0d == equator.latitude
    180.0d == equator.longitude
  }

  def "should normalize longitude/latitude values when location is on opposite hemisphere"() {
    when: "instantiate a new instance"
    def lat = -90.000000009d
    def longitude = 0.0d
    def e = new GlobalCoordinates(lat, longitude)

    then: "should return proper latitude and longitude"
    -89.999999991d == e.latitude
    180.0d == e.longitude
  }

  def "should normalize longitude value when location is on opposite hemisphere"() {
    when: "instantiate a new instance"
    def lat = 45.0d
    def longitude = -180.001d
    def e = new GlobalCoordinates(lat, longitude)

    then: "should return proper latitude and longitude"
    45.0d == e.latitude
    179.999 == e.longitude.round(3)
  }

  def "should NOT normalize values when values are already within range"() {
    when: "instantiate a new instance"
    def lat = 45.0d
    def longitude = -179.999d
    def e = new GlobalCoordinates(lat, longitude)

    then: "should return proper latitude and longitude"
    45.0d == e.latitude
    -179.999d == e.longitude
  }

  def "should wrap properly when values are outside the accepted range"() {
    when: "instantiate a new instance"
    def latitude = -450.009d
    def longitude = 360.01
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    -89.991 == e.latitude.round(3)
    -179.99d == e.longitude
  }

  def "another example of wrapping"() {
    when: "instantiate a new instance"
    def latitude = -1169.9991d
    def longitude = -179.999d
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    -89.9991 == e.latitude
    -179.999d == e.longitude
  }

  def "dj another example of wrapping"() {
    when: "instantiate a new instance"
    def latitude = -450.0009d
    def longitude = 0.0d
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    -89.9991 == e.latitude
    180.0d == e.longitude
  }
//  public void testConstructor5() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-450.0009, 0.0);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor6() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(180.001, 360.001);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -9.999999999763531E-4, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor7() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(180.0, 0.0);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 0.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor8() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(90.0009, 360.001);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor9() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-90.0009, 100.0);
//    assertEquals("globalCoordinates.getLongitude()", -80.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor10() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-1169.9991, -180.001);
//    assertEquals("globalCoordinates.getLongitude()", 179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor11() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 0.0);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 80.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor12() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-90.000000009, 360.001);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -89.999999991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor13() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-180.001, -360.0036);
//    assertEquals("globalCoordinates.getLongitude()", 179.9964, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 9.999999999763531E-4, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor14() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-181.0, 360.001);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 1.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor15() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-269.9991, 360.001);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor16() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(89.999, -180.001);
//    assertEquals("globalCoordinates.getLongitude()", 179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 89.99900000000002, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCanonicalize() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(90.0009, 100.0);
//    callPrivateMethod("org.gavaghan.geodesy.GlobalCoordinates", "canonicalize", new Class[] {}, globalCoordinates, new Object[] {});
//    assertEquals("globalCoordinates.getLongitude()", -80.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCanonicalize1() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(90.0, 100.0);
//    callPrivateMethod("org.gavaghan.geodesy.GlobalCoordinates", "canonicalize", new Class[] {}, globalCoordinates, new Object[] {});
//    assertEquals("globalCoordinates.getLongitude()", 100.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 90.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCanonicalize2() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 360.001);
//    callPrivateMethod("org.gavaghan.geodesy.GlobalCoordinates", "canonicalize", new Class[] {}, globalCoordinates, new Object[] {});
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 80.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCanonicalize3() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-450.0009, 100.0);
//    callPrivateMethod("org.gavaghan.geodesy.GlobalCoordinates", "canonicalize", new Class[] {}, globalCoordinates, new Object[] {});
//    assertEquals("globalCoordinates.getLongitude()", -80.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCanonicalize4() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 0.0);
//    callPrivateMethod("org.gavaghan.geodesy.GlobalCoordinates", "canonicalize", new Class[] {}, globalCoordinates, new Object[] {});
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 80.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCanonicalize5() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-90.0, 100.0);
//    callPrivateMethod("org.gavaghan.geodesy.GlobalCoordinates", "canonicalize", new Class[] {}, globalCoordinates, new Object[] {});
//    assertEquals("globalCoordinates.getLongitude()", 100.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -90.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testCompareTo() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(1000.0, 0.0);
//    int result = globalCoordinates.compareTo(new GlobalCoordinates(0.0, 0.0));
//    assertEquals("result", -1, result);
//  }
//
//  public void testCompareTo1() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-54.12682626557432, -43.319025743636864);
//    int result = new GlobalCoordinates(-54.12682626557432, -43.31902574363687).compareTo(globalCoordinates);
//    assertEquals("result", 0, result);
//  }
//
//  public void testCompareTo2() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(0.0, -69.0);
//    int result = globalCoordinates.compareTo(new GlobalCoordinates(-1.0, -69.0));
//    assertEquals("result", 1, result);
//  }
//
//  public void testCompareTo3() throws Throwable {
//    int result = new GlobalCoordinates(0.0, -28.367958936972176).compareTo(new GlobalCoordinates(1000.0, -28.36739158630371));
//    assertEquals("result", -1, result);
//  }
//
//  public void testCompareTo4() throws Throwable {
//    GlobalCoordinates other = new GlobalCoordinates(100.0, 1000.0);
//    other.setLongitude(359.999);
//    int result = new GlobalCoordinates(1000.0, 0.0).compareTo(other);
//    assertEquals("result", 1, result);
//  }
//
//  public void testCompareTo5() throws Throwable {
//    GlobalCoordinates other = new GlobalCoordinates(100.0, 1000.0);
//    other.setLongitude(359.999);
//    other.setLatitude(89.999);
//    int result = new GlobalCoordinates(-270.0, -9.999999999763531E-4).compareTo(other);
//    assertEquals("result", 1, result);
//  }
//
//  public void testEquals() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(1000.0, 0.0);
//    boolean result = globalCoordinates.equals(new GlobalCoordinates(0.0, 360.001));
//    assertFalse("result", result);
//  }
//
//  public void testEquals1() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(1000.0, 0.0);
//    boolean result = globalCoordinates.equals(new GlobalCoordinates(0.0, 0.0));
//    assertFalse("result", result);
//  }
//
//  public void testEquals2() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    boolean result = globalCoordinates.equals(globalCoordinates);
//    assertTrue("result", result);
//  }
//
//  public void testEquals3() throws Throwable {
//    boolean result = new GlobalCoordinates(100.0, 1000.0).equals("testString");
//    assertFalse("result", result);
//  }
//
//  public void testHashCode() throws Throwable {
//    int result = new GlobalCoordinates(100.0, 1000.0).hashCode();
//    assertEquals("result", 2146483615, result);
//  }
//
//  public void testSetLatitude() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(-180.0);
//    globalCoordinates.setLatitude(1000.0);
//    assertEquals("globalCoordinates.getLatitude()", -80.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude1() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(360.001);
//    globalCoordinates.setLatitude(100.0);
//    globalCoordinates.setLatitude(-90.0);
//    assertEquals("globalCoordinates.getLatitude()", -90.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude2() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(360.001);
//    globalCoordinates.setLatitude(100.0);
//    globalCoordinates.setLatitude(-360.0);
//    assertEquals("globalCoordinates.getLatitude()", 0.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude3() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(1000.0, 180.0);
//    globalCoordinates.setLatitude(-360.0);
//    assertEquals("globalCoordinates.getLatitude()", 0.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude4() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLatitude(-89.999);
//    assertEquals("globalCoordinates.getLatitude()", -89.999, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", 100.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude5() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLatitude(-181.0);
//    assertEquals("globalCoordinates.getLatitude()", 1.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", -80.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude6() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(360.001);
//    globalCoordinates.setLatitude(100.0);
//    globalCoordinates.setLatitude(89.999);
//    assertEquals("globalCoordinates.getLatitude()", 89.99900000000002, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude7() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(0.0);
//    globalCoordinates.setLatitude(100.0);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 80.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude8() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(360.001);
//    globalCoordinates.setLatitude(100.0);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 80.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude9() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLatitude(90.0);
//    assertEquals("globalCoordinates.getLatitude()", 90.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", 100.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude10() throws Throwable {
//    GlobalCoordinates globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0);
//    globalPosition.setLatitude(-90.0009);
//    assertEquals("(GlobalPosition) globalPosition.getLongitude()", -80.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("(GlobalPosition) globalPosition.getLatitude()", -89.9991, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude11() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLatitude(90.001);
//    assertEquals("globalCoordinates.getLatitude()", 89.99900000000002, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", -80.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude12() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(0.0);
//    globalCoordinates.setLatitude(180.0);
//    assertEquals("globalCoordinates.getLatitude()", 0.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLatitude13() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLongitude(0.0);
//    globalCoordinates.setLatitude(-181.0);
//    assertEquals("globalCoordinates.getLatitude()", 1.0, globalCoordinates.getLatitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//  }
//
//  public void testSetLongitude() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-90.0, 100.0);
//    globalCoordinates.setLongitude(-2519.9748);
//    assertEquals("globalCoordinates.getLongitude()", 0.025200000000040745, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -90.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLongitude1() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-270.0, 100.0);
//    globalCoordinates.setLongitude(-2519.9748);
//    assertEquals("globalCoordinates.getLongitude()", 0.025200000000040745, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 90.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLongitude2() throws Throwable {
//    GlobalCoordinates globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0);
//    globalPosition.setLatitude(89.999);
//    globalPosition.setLongitude(100.0);
//    assertEquals("(GlobalPosition) globalPosition.getLongitude()", 100.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("(GlobalPosition) globalPosition.getLatitude()", 89.99900000000002, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLongitude3() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLatitude(-90.0);
//    globalCoordinates.setLongitude(-180.0);
//    assertEquals("globalCoordinates.getLongitude()", 180.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -90.0, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLongitude4() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(100.0, 1000.0);
//    globalCoordinates.setLatitude(89.999);
//    globalCoordinates.setLongitude(180.001);
//    assertEquals("globalCoordinates.getLongitude()", -179.99900000000002, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", 89.99900000000002, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testSetLongitude5() throws Throwable {
//    GlobalCoordinates globalCoordinates = new GlobalCoordinates(-450.0009, 100.0);
//    globalCoordinates.setLongitude(100.0);
//    assertEquals("globalCoordinates.getLongitude()", 100.0, globalCoordinates.getLongitude(), 1.0E-6);
//    assertEquals("globalCoordinates.getLatitude()", -89.9991, globalCoordinates.getLatitude(), 1.0E-6);
//  }
//
//  public void testToString() throws Throwable {
//    String result = new GlobalCoordinates(0.0, 100.0).toString();
//    assertEquals("result", "0.0N;100.0E;", result);
//  }
//
//  public void testToString1() throws Throwable {
//    String result = new GlobalCoordinates(-0.0010, 100.0).toString();
//    assertEquals("result", "0.0010000000000047748S;100.0E;", result);
//  }
//
//  public void testToString2() throws Throwable {
//    String result = new GlobalCoordinates(100.0, -179.999).toString();
//    assertEquals("result", "80.0N;0.0010000000000047748E;", result);
//  }
//
//  public void testToString3() throws Throwable {
//    String result = new GlobalCoordinates(1000.0, 0.0).toString();
//    assertEquals("result", "80.0S;0.0E;", result);
//  }
//
//  public void testToString4() throws Throwable {
//    String result = new GlobalCoordinates(-180.001, -179.999).toString();
//    assertEquals("result", "9.999999999763531E-4N;0.0010000000000047748E;", result);
//  }
//
//  public void testToString5() throws Throwable {
//    String result = new GlobalCoordinates(1.0, -9.999999999763531E-4).toString();
//    assertEquals("result", "1.0N;9.999999999763531E-4W;", result);
//  }
//
//  public void testToString6() throws Throwable {
//    String result = new GlobalCoordinates(1000.0, -9.999999999763531E-4).toString();
//    assertEquals("result", "80.0S;9.999999999763531E-4W;", result);
//  }
//
//  public void testCompareToThrowsNullPointerException() throws Throwable {
//    try {
//      new GlobalCoordinates(100.0, 1000.0).compareTo((GlobalCoordinates) null);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GlobalCoordinates.class, ex);
//    }
//  }
//
}
