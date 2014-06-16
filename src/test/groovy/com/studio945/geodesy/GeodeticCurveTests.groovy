package com.studio945.geodesy;

public class GeodeticCurveTests extends spock.lang.Specification {

  def "default constructor should construct proper object"() {
    when: "instantiate object"
    def g = new GeodeticCurve(100.0, 1000.0, 0.0)

    then: "expect proper values"
    0.0 == g.reverseAzimuth
    100.0 == g.ellipsoidalDistance
    1000.0 == g.azimuth
  }

  def "toString should return proper value"() {
    when: "instantiate object"
    def g = new GeodeticCurve(100.0, 1000.0, 0.0)

    then: "expect proper toString value"
    "s=100.0;a12=1000.0;a21=0.0;" == g.toString()
  }
}
