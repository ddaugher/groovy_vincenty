package com.studio945.geodesy;

public class GlobalPositionEqualsTests extends spock.lang.Specification {
  def "should return true when global positions equal"() {
    when: "a valid global position is created from global coordinates"
    def gp = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0)

    then: "global position should be equal to itself"
    true == gp.equals(gp)
  }

  def "should return false when global positions are NOT equal by elevation"() {
    when: "a valid global position is created from global coordinates"
    def gp1 = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0)
    def gp2 = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 200.0)

    then: "global position should be equal to itself"
    false == gp1.equals(gp2)
  }

  def "should return false when global positions are NOT equal by latitude"() {
    when: "a valid global position is created from global coordinates"
    def gp1 = new GlobalPosition(new GlobalCoordinates(200.0, 1000.0), 100.0)
    def gp2 = new GlobalPosition(new GlobalCoordinates(100.0, 1000.0), 100.0)

    then: "global position should be equal to itself"
    false == gp1.equals(gp2)
  }

  def "should return false when global positions are NOT equal by longitude"() {
    when: "a valid global position is created from global coordinates"
    def gp1 = new GlobalPosition(new GlobalCoordinates(100.0, 200.0), 100.0)
    def gp2 = new GlobalPosition(new GlobalCoordinates(100.0, 300.0), 100.0)

    then: "global position should be equal to itself"
    false == gp1.equals(gp2)
  }
}
