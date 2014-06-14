package com.studio945.geodesy;

public class GlobalCoordinatesTests extends spock.lang.Specification {

  def "should create proper GlobalCoordinates for North Pole"() {
    when: "instantiate a new instance"
    def northPole = GlobalCoordinates.northPole()

    then: "should return proper North Pole GlobalCoordinates"
    10.0d == northPole.longitude
    90.0d == northPole.latitude
  }

  def "should create proper GlobalCoordinates for South Pole"() {
    when: "instantiate a new instance"
    def southPole = GlobalCoordinates.createSouthPole()

    then: "should return proper South Pole GlobalCoordinates"
    10.0d == southPole.longitude
    90.0d == southPole.latitude
  }
}
