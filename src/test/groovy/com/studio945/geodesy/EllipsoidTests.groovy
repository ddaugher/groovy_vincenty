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
    def e = Ellipsoid.createFromSemiMajorAxisAndFlattening(semiMajorAxis, flattening)

    then:
    1000.0d == e.flattening
  }

  def "when creating Ellipsoid from semiMajorAxis and inverseFlattening, should return original flattening value"() {
    when: "create new ellipsoid"
    def semiMajorAxis = 100.0d
    def inverseFlattening = 1000.0d
    def e = Ellipsoid.createFromSemiMajorAxisAndFlattening(semiMajorAxis, inverseFlattening)

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

  def "should properly create GRS80 ellipsoid"() {
    when: "create GRS80 ellipsoid"
    def e = Ellipsoid.GRS80

    then:
    6378137.0 == e.semiMajorAxis
    6356752.314140356 == e.semiMinorAxis
    0.003352810681182319 == e.flattening
    298.257222101 == e.inverseFlattening
  }

  def "should properly create GRS67 ellipsoid"() {
    when: "create GRS67 ellipsoid"
    def e = Ellipsoid.GRS67

    then:
    6378160.0 == e.semiMajorAxis
    6356774.719195305 == e.semiMinorAxis
    0.003352891869237217 == e.flattening
    298.25 == e.inverseFlattening
  }

  def "should properly create ANS ellipsoid"() {
    when: "create ANS ellipsoid"
    def e = Ellipsoid.ANS

    then:
    6378160.0 == e.semiMajorAxis
    6356774.719195305 == e.semiMinorAxis
    0.003352891869237217 == e.flattening
    298.25 == e.inverseFlattening
  }

  def "should properly create WGS72 ellipsoid"() {
    when: "create WGS72 ellipsoid"
    def e = Ellipsoid.WGS72

    then:
    6378135.0 == e.semiMajorAxis
    6356750.520016094 == e.semiMinorAxis
    0.003352779454167505 == e.flattening
    298.26 == e.inverseFlattening
  }

  def "should properly create Clarke1858 ellipsoid"() {
    when: "create Clarke1858 ellipsoid"
    def e = Ellipsoid.Clarke1858

    then:
    6378293.645 == e.semiMajorAxis
    6356617.937649357 == e.semiMinorAxis
    0.003398355196085095 == e.flattening
    294.26 == e.inverseFlattening
  }

  def "should properly create Clarke1880 ellipsoid"() {
    when: "create Clarke1880 ellipsoid"
    def e = Ellipsoid.Clarke1880

    then:
    6378249.145 == e.semiMajorAxis
    6356514.8695497755 == e.semiMinorAxis
    0.003407561378699334 == e.flattening
    293.465 == e.inverseFlattening
  }

  def "should properly create Sphere ellipsoid"() {
    when: "create Sphere ellipsoid"
    def e = Ellipsoid.Sphere

    then:
    6371000.0 == e.semiMajorAxis
    6371000.0 == e.semiMinorAxis
    0.0 == e.flattening
    Double.POSITIVE_INFINITY == e.inverseFlattening
  }
}
