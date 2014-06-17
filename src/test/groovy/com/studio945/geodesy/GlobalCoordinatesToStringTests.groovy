package com.studio945.geodesy;

public class GlobalCoordinatesToStringTests extends spock.lang.Specification {

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

  def "should throw NullPointerException when compared with null global coordinates"() {
    when: "valid global coordinate compared to null global coordinate"
    new GlobalCoordinates(100.0, 1000.0).compareTo(null)

    then: "should throw NullPointerException"
    thrown(NullPointerException)
  }
}
