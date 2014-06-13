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

  def "shoudl convert radians to degrees"() {
    expect: "proper degrees are returned"
    57.295779513082 == Angle.instance.toDegrees(1).round(12)
  }
}
