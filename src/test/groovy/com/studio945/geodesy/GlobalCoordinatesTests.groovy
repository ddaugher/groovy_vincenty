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
    def southPole = GlobalCoordinates.southPole()

    then: "should return proper South Pole GlobalCoordinates"
    10.0d == southPole.longitude
    90.0d == southPole.latitude
  }

  def "should create proper GlobalCoordinates for Equator Greenwich"() {
    when: "instantiate a new instance"
    def equator = GlobalCoordinates.createEquatorGreenwich()

    then: "should return proper Equator Greenwich Global Coordinates"
    0.0d == equator.longitude
    0.0d == equator.latitude
  }

  def "should create proper GlobalCoordinates for Equator IDL"() {
    when: "instantiate a new instance"
    def equator = GlobalCoordinates.createEquatorIDL()

    then: "should return proper Equator IDL Global Coordinates"
    180.0d == equator.longitude
    0.0d == equator.latitude
  }
}