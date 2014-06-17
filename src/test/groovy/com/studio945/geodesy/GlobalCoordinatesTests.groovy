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

  def "latitude pushed over the pole causes the longitude to flip to opposite hemisphere"() {
    when: "instantiate a new instance"
    def latitude = -450.0009d
    def longitude = 0.0d
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    -89.9991 == e.latitude
    180.0d == e.longitude
  }

  def "latitude pushes coordinate into southern hemisphere and longitude starts on opposite side of earth"() {
    when: "instantiate a new instance"
    def latitude = 180.001d
    def longitude = 360.001d
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    -9.999999999763531E-4 == e.latitude
    -179.99900000000002d == e.longitude
  }

  def "end up on the other side of the earth, just stay on the equator"() {
    when: "instantiate a new instance"
    def latitude = 180.0d
    def longitude = 0.0d
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    0.0 == e.latitude
    180.0 == e.longitude
  }

  def "dj end up on the other side of the earth, just stay on the equator"() {
    when: "instantiate a new instance"
    def latitude = 90.0009d
    def longitude = 360.001d
    def e = new GlobalCoordinates(latitude, longitude)

    then: "should return proper latitude and longitude"
    89.9991 == e.latitude
    -179.999 == e.longitude.round(3)
  }

  def "should return -1 when left longitude is less than right"() {
    when: "instantiate two global coordinates"
    def left = new GlobalCoordinates(0.0, 0.0)
    def right = new GlobalCoordinates(0.0, 80.0)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "the left to be less"
    -1 == result
  }

  def "should return +1 when left longitude is greater than right"() {
    when: "instantiate two global coordinates"
    def left = new GlobalCoordinates(0.0, 80.0)
    def right = new GlobalCoordinates(0.0, 0.0)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "the left to be greater"
    1 == result
  }

  def "should return -1 when left latitude is less than right"() {
    when: "instantiate two global coordinates"
    def left = new GlobalCoordinates(0.0, 0.0)
    def right = new GlobalCoordinates(80.0, 0.0)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "the left to be less"
    -1 == result
  }

  def "should return 1 when left latitude is greater than right"() {
    when: "instantiate two global coordinates"
    def left = new GlobalCoordinates(80.0, 0.0)
    def right = new GlobalCoordinates(0.0, 0.0)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "the left to be greater"
    1 == result
  }

  def "should return 0 when left latitude is equal than right"() {
    when: "instantiate two global coordinates"
    def left = new GlobalCoordinates(80.0, 0.0)
    def right = new GlobalCoordinates(80.0, 0.0)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "both global coordinates to be equal"
    0 == result
  }

  def "comparing two coordinates when latitude wrapping occurs"() {
    when: "instantiate two different global coordinates"
    def left = new GlobalCoordinates(1000.0, 0.0)
    def right = new GlobalCoordinates(0.0, 0.0)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "the left to be less"
    -1 == result
  }

  def "dj comparing two coordinates when latitude wrapping occurs"() {
    when: "instantiate two different global coordinates"
    def left = new GlobalCoordinates(-54.12682626557432, -43.319025743636864)
    def right = new GlobalCoordinates(-54.12682626557432, -43.31902574363687)

    then: "compare the right to the left"
    def result = left.compareTo(right)

    expect: "the left to be less"
    0 == result
  }

  def "comparing two coordinates after setting new longitude and latitude"() {
    when: "instantiate global coordinates"
    def left = new GlobalCoordinates(100.0, 1000.0)

    and: "set longitude"
    left.longitude = 359.99

    and: "set latitude"
    left.latitude = 89.999

    then: "other should be greater"
    1 == new GlobalCoordinates(-270.0, -9.999999999763531E-4).compareTo(left)
  }

  def "should return proper hashcode"() {
    when: "instantiate two different global coordinates"
    def left = new GlobalCoordinates(100.0, 1000.0)

    then: "should return proper hashcode"
    2146483615 == left.hashCode()
  }

  def "should return false when global coordinates are not equal"() {
    when: "instantiate two different global coordinates"
    def globalCoordinates = new GlobalCoordinates(1000.0, 0.0);

    then: "should not equal"
    false == globalCoordinates.equals(new GlobalCoordinates(0.0, 360.001));
  }

  def "should return true when global coordinates are equal"() {
    when: "instantiate two different global coordinates"
    def globalCoordinates = new GlobalCoordinates(1000.0, 0.0);

    then: "should not equal"
    true == globalCoordinates.equals(globalCoordinates);
  }
//  public void testHashCode() throws Throwable {
//    int result = new GlobalCoordinates(100.0, 1000.0).hashCode();
//    assertEquals("result", 2146483615, result);
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

  def "should throw NullPointerException when compared with null global coordinates"() {
    when: "valid global coordinate compared to null global coordinate"
    new GlobalCoordinates(100.0, 1000.0).compareTo(null)

    then: "should throw NullPointerException"
    thrown(NullPointerException)
  }
}
