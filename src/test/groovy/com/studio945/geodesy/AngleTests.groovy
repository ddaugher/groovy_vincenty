package com.studio945.geodesy;

public class AngleTests extends spock.lang.Specification {

  def "should not be allowed to instantiate"() {
    when: "try to instantiate a new instance"
    new Angle()

    then: "should throw RunTimeException"
    thrown(RuntimeException)
  }

  def "should convert degrees to radians"() {
    expect: "proper radians are returned"
    0.34906585039887 == Angle.instance.toRadians(20).round(14)
  }

  def "should convert 0 degrees to 0 radians"() {
    expect:
    0.0 == Angle.toRadians(0.0)
  }

  def "should convert 0 radians to 0 degrees"() {
    expect:
    0.0 == Angle.toDegrees(0.0)
  }

  def "should convert radians to degrees"() {
    expect: "proper degrees are returned"
    57.295779513082 == Angle.instance.toDegrees(1).round(12)
  }

  def "conversion constant should be returned properly"() {
    expect: "proper value returned"
    0.017453292519943295 == Angle.piOver180
  }


//  public void testToRadians() throws Throwable {
//    double result = Angle.toRadians(100.0);
//    assertEquals("result", 1.7453292519943295, result, 1.0E-6);
//  }
//
//  public void testToRadians1() throws Throwable {
//    double result = Angle.toRadians(0.0);
//    assertEquals("result", 0.0, result, 1.0E-6);
//  }

}
