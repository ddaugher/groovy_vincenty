package com.studio945.geodesy;

public class GeodeticMeasurementTests extends spock.lang.Specification {

  def "should construct object from default constructor"() {
    when: "instantiate geodeticMeasurement"
    def geodeticMeasurement = new GeodeticMeasurement(new GeodeticCurve(100.0, 1000.0, 0.0), 100.0)

    then:
    0.0 == geodeticMeasurement.reverseAzimuth
    141.4213562373095 == geodeticMeasurement.getPointToPointDistance()
    100.0 == geodeticMeasurement.getEllipsoidalDistance()
    1000.0 == geodeticMeasurement.azimuth
    100.0 == geodeticMeasurement.getElevationChange()
  }

  def "should return proper toString"() {
    when: "instantiate geodeticMeasurement"
    def geodeticMeasurement = new GeodeticMeasurement(new GeodeticCurve(100.0, 1000.0, 0.0), 100.0)

    then:
    "s=100.0;a12=1000.0;a21=0.0;elev12=100.0;p2p=141.4213562373095" == geodeticMeasurement.toString()
  }
}
