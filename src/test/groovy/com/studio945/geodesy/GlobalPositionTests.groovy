package com.studio945.geodesy;

public class GlobalPositionTests extends spock.lang.Specification {
  def "should create global position from existing global coordinate and altitude"() {
    when: "a valid global coordinates exists"
    def gc = new GlobalCoordinates(-90.0, 100.0)

    and: "a valid global position is created from global coordinates"
    def gp = new GlobalPosition(gc, 100.0)

    then: "longitude should return proper value"
    100.0 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    -90.0 == gp.latitude
  }

  def "should create global position from existing global coordinate and altitude and deal with wrapping"() {
    when: "a valid global coordinates exists"
    def gc = new GlobalCoordinates(100.0, 0.0010)

    and: "a valid global position is created from global coordinates"
    def gp = new GlobalPosition(gc, 100.0)

    then: "longitude should return proper value"
    -179.99900000000002 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    80.0 == gp.latitude
  }
//  public void testConstructor1() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 0.0010), 100.0);
//    assertEquals("globalPosition.getLongitude()", -179.99900000000002, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 80.0, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor2() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(180.0, 0.0, 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 0.0, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor3() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(89.9991, 180.0, 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 89.9991, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor4() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalPosition(90.0, 100.0, 1000.0), 100.0);
//    assertEquals("globalPosition.getLongitude()", 100.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 90.0, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor5() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 0.0), 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 80.0, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor6() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(-1169.9991, -1260.0), 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", -89.9991, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor7() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(-990.0009, 100.0), 100.0);
//    assertEquals("globalPosition.getLongitude()", 100.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 89.9991, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor8() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(90.0, 180.0, 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", 90.0, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor9() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(-90.0009, 100.0, 1000.0);
//    assertEquals("globalPosition.getLongitude()", -80.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 1000.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", -89.9991, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor10() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(-1169.9991, 180.0, 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", -89.9991, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testConstructor11() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(-90.0, 180.0, 100.0);
//    assertEquals("globalPosition.getLongitude()", 180.0, globalPosition.getLongitude(), 1.0E-6);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//    assertEquals("globalPosition.getLatitude()", -90.0, globalPosition.getLatitude(), 1.0E-6);
//  }
//
//  public void testCompareTo() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(1000.0, 0.0), 100.0);
//    int result = globalPosition.compareTo(new GlobalPosition(0.0, 0.0, 100.0));
//    assertEquals("result", -1, result);
//  }
//
//  public void testCompareTo1() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(100.0, 1000.0, 0.0);
//    int result = globalPosition.compareTo(new GlobalPosition(new GlobalCoordinates(100.0, 1.0), 100.0));
//    assertEquals("result", 1, result);
//  }
//
//  public void testCompareTo2() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(-100.0, 0.0, 100.0);
//    int result = globalPosition.compareTo(new GlobalPosition(-80.0, 180.0, 0.0));
//    assertEquals("result", 1, result);
//  }
//
//  public void testCompareTo3() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(100.0, 1000.0, 0.0);
//    int result = globalPosition.compareTo(globalPosition);
//    assertEquals("result", 0, result);
//  }
//
//  public void testCompareTo4() throws Throwable {
//    int result = new GlobalPosition(-89.98740000900011, -0.021600000000034925, 0.0).compareTo(new GlobalPosition(new GlobalCoordinates(-1169.987400009, -2160.0216), 100.0));
//    assertEquals("result", -1, result);
//  }
//
//  public void testEquals() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0);
//    boolean result = globalPosition.equals(globalPosition);
//    assertTrue("result", result);
//  }
//
//  public void testEquals1() throws Throwable {
//    boolean result = new GlobalPosition(100.0, 1000.0, 0.0).equals(new Integer(-1));
//    assertFalse("result", result);
//  }
//
//  public void testEquals2() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 0.0);
//    boolean result = globalPosition.equals(new GlobalPosition(new GlobalCoordinates(1000.0, 0.0), 0.0));
//    assertFalse("result", result);
//  }
//
//  public void testEquals3() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0);
//    boolean result = globalPosition.equals(new GlobalPosition(100.0, 1000.0, 0.0));
//    assertFalse("result", result);
//  }
//
//  public void testHashCode() throws Throwable {
//    int result = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 0.0).hashCode();
//    assertEquals("result", 2146483615, result);
//  }
//
//  public void testHashCode1() throws Throwable {
//    int result = new GlobalPosition(100.0, 1000.0, -1.0).hashCode();
//    assertEquals("result", -2146483615, result);
//  }
//
//  public void testHashCode2() throws Throwable {
//    int result = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 2.0E-5).hashCode();
//    assertEquals("result", 0, result);
//  }
//
//  public void testSetElevation() throws Throwable {
//    GlobalPosition globalPosition = new GlobalPosition(100.0, 1000.0, 0.0);
//    globalPosition.setElevation(100.0);
//    assertEquals("globalPosition.getElevation()", 100.0, globalPosition.getElevation(), 1.0E-6);
//  }
//
//  public void testToString() throws Throwable {
//    String result = new GlobalPosition(new GlobalCoordinates(359.999, 100.0), 100.0).toString();
//    assertEquals("result", "9.999999999763531E-4S;100.0E;elevation=100.0m", result);
//  }
//
//  public void testToString1() throws Throwable {
//    String result = new GlobalPosition(new GlobalCoordinates(1000.0, 359.999), 100.0).toString();
//    assertEquals("result", "80.0S;9.999999999763531E-4W;elevation=100.0m", result);
//  }
//
//  public void testToString2() throws Throwable {
//    String result = new GlobalPosition(new GlobalCoordinates(0.0, 100.0), 100.0).toString();
//    assertEquals("result", "0.0N;100.0E;elevation=100.0m", result);
//  }
//
//  public void testToString3() throws Throwable {
//    String result = new GlobalPosition(new GlobalCoordinates(0.0, 360.0), 100.0).toString();
//    assertEquals("result", "0.0N;0.0E;elevation=100.0m", result);
//  }
//
//  public void testToString4() throws Throwable {
//    String result = new GlobalPosition(new GlobalCoordinates(360.001, 100.0), 100.0).toString();
//    assertEquals("result", "9.999999999763531E-4N;100.0E;elevation=100.0m", result);
//  }
//
//  public void testToString5() throws Throwable {
//    String result = new GlobalPosition(new GlobalCoordinates(1000.0, 0.0010), 100.0).toString();
//    assertEquals("result", "80.0S;0.0010000000000047748E;elevation=100.0m", result);
//  }
//
//  public void testConstructorThrowsNullPointerException() throws Throwable {
//    try {
//      new GlobalPosition(null, 100.0);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GlobalPosition.class, ex);
//    }
//  }
//
//  public void testCompareToThrowsNullPointerException() throws Throwable {
//    try {
//      new GlobalPosition(100.0, 1000.0, 0.0).compareTo((GlobalPosition) null);
//      fail("Expected NullPointerException to be thrown");
//    } catch (NullPointerException ex) {
//      assertNull("ex.getMessage()", ex.getMessage());
//      assertThrownBy(GlobalCoordinates.class, ex);
//    }
//  }
//
}
