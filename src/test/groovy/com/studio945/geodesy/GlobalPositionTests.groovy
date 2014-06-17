package com.studio945.geodesy

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

  def "should create global position"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(180.0, 0.0, 100.0)

    then: "longitude should return proper value"
    180.0 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    0.0 == gp.latitude
  }

  def "should create global position and deal with wrapping"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(89.9991, 180.0, 100.0)

    then: "longitude should return proper value"
    180.0 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    89.9991 == gp.latitude
  }

  def "should create global position from an existing global position"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalPosition(90.0, 100.0, 1000.0), 100.0)

    then: "longitude should return proper value"
    100.0 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    90.0 == gp.latitude
  }

  def "should create global position from an existing global coordinates"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(100.0, 0.0), 100.0)

    then: "longitude should return proper value"
    180.0 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    80.0 == gp.latitude
  }

  def "should create global position from an existing global coordinates and deal with wrapping"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(-1169.9991, -1260.0), 100.0)

    then: "longitude should return proper value"
    180.0 == gp.longitude

    then: "elevation should return proper value"
    100.0 == gp.elevation

    then: "latitude should return proper value"
    -89.9991 == gp.latitude
  }

  def "should be able to set elevation on an already existing global position"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(100.0, 1000.0, 0.0)

    then: "elevation on constructed object should be set to constructor value"
    0.0 == gp.elevation

    when: "set the elevation to a new value"
    gp.elevation = 100.0

    then: "elevation should return proper value"
    100.0 == gp.elevation
  }

  def "should return proper toString value 1"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(1000.0, 359.999), 100.0)

    then: "proper toString should be returned"
    "80.0S;9.999999999763531E-4W;elevation=100.0m" == gp.toString()
  }

  def "should return proper toString value 2"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(0.0, 100.0), 100.0)

    then: "proper toString should be returned"
    "0.0N;100.0E;elevation=100.0m" == gp.toString()
  }

  def "should return proper toString value 3"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(0.0, 360.0), 100.0)

    then: "proper toString should be returned"
    "0.0N;0.0E;elevation=100.0m" == gp.toString()
  }

  def "should return proper toString value 4"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(360.001, 100.0), 100.0)

    then: "proper toString should be returned"
    "9.999999999763531E-4N;100.0E;elevation=100.0m" == gp.toString()
  }

  def "should return proper toString value 5"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(1000.0, 0.0010), 100.0)

    then: "proper toString should be returned"
    "80.0S;0.0010000000000047748E;elevation=100.0m" == gp.toString()
  }

  def "should not be allowed to instantiate without latitude value"() {
    when: "try to instantiate a new instance"
    new GlobalPosition(null, 100.0);

    then: "should throw RunTimeException"
    thrown(NullPointerException)
  }

  def "should throw NullPointerException when trying to compare global postion with nul global position"() {
    when: "try to instantiate a new instance"
    new GlobalPosition(100.0, 100.0, 0.0).compareTo(null)

    then: "should throw RunTimeException"
    thrown(NullPointerException)
  }
}
