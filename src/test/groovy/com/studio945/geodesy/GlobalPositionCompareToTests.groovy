package com.studio945.geodesy;

public class GlobalPositionCompareToTests extends spock.lang.Specification {
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

  def "compare 1"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(new GlobalCoordinates(1000.0, 0.0), 100.0);

    then: "compare"
    -1 == gp.compareTo(new GlobalPosition(0.0, 0.0, 100.0))
  }

  def "compare 2"() {
    when: "a valid global position is created"
    def globalPosition = new GlobalPosition(100.0, 1000.0, 0.0);

    then: "compare"
    1 == globalPosition.compareTo(new GlobalPosition(new GlobalCoordinates(100.0, 1.0), 100.0));
  }

  def "compare 3"() {
    when: "a valid global position is created"
    def globalPosition = new GlobalPosition(-100.0, 0.0, 100.0);

    then: "compare"
    1 == globalPosition.compareTo(new GlobalPosition(-80.0, 180.0, 0.0));
  }

  def "compare 4"() {
    when: "a valid global position is created"
    def globalPosition = new GlobalPosition(100.0, 1000.0, 0.0);

    then: "compare"
    0 == globalPosition.compareTo(globalPosition);
  }

  def "compare 5"() {
    expect: "a valid global position is created"
    -1 == new GlobalPosition(-89.98740000900011, -0.021600000000034925, 0.0).compareTo(new GlobalPosition(new GlobalCoordinates(-1169.987400009, -2160.0216), 100.0));
  }

  def "should return proper hascode"() {
    when: "a valid global position is created"
    def gp = new GlobalPosition(100.0, 1000.0, -1.0).hashCode();

    then: "return proper hashcode"
    -2146483615 == gp.hashCode()

  }
}
