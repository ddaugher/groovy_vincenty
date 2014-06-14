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

  def "when creating Ellipsoid from semiMajorAxis and flattening, should return original flattening value"() {
    when: "create new ellipsoid"
    def semiMajorAxis = 100.0d
    def flattening = 1000.0d
    def e = Ellipsoid.fromAAndF(semiMajorAxis, flattening)

    then:
    1000.0d == e.flattening
  }

  def "when creating Ellipsoid from semiMajorAxis and inverseFlattening, should return original flattening value"() {
    when: "create new ellipsoid"
    def semiMajorAxis = 100.0d
    def inverseFlattening = 1000.0d
    def e = Ellipsoid.fromAAndF(semiMajorAxis, inverseFlattening)

    then:
    0.0010d == e.inverseFlattening
  }

  def "should properly create WGS84 ellipsoid"() {
    when: "create WGS84 ellipsoid"
    def e = Ellipsoid.WGS84

    then:
    6378137.0 == e.semiMajorAxis
    6356752.314245179 == e.semiMinorAxis
    0.0033528106647474805 == e.flattening
    298.257223563 == e.inverseFlattening
  }
}
