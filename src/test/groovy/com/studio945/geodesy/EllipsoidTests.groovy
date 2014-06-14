package com.studio945.geodesy;

public class EllipsoidTests extends spock.lang.Specification {

  def "should be able to construct proper ellipsoid"() {
    when: "create new ellipsoid"
    def e = new Ellipsoid(
      semiMajorAxis: 100.0d,
      semiMinorAxis: 1000.0d,
      flattening: 0.0d,
      inverseFlattening: -1.0
    )

    then:
    100.0d == e.semiMajorAxis
    1000.0d == e.semiMinorAxis
    0.0d == e.flattening
    -1.0d == e.inverseFlattening
  }
}
