package com.studio945.geodesy

class GeodeticMeasurement extends GeodeticCurve {
  def double elevationChange
  def double pointToPoint

  public GeodeticMeasurement(double ellipsoidalDistance, double azimuth, double reverseAzimuth, double elevationChange) {
    super(ellipsoidalDistance, azimuth, reverseAzimuth)
    this.elevationChange = elevationChange
    pointToPoint = Math.sqrt(ellipsoidalDistance * ellipsoidalDistance + this.elevationChange * this.elevationChange)
  }

 public GeodeticMeasurement(GeodeticCurve averageCurve, double elevationChange) {
    this(averageCurve.getEllipsoidalDistance(), averageCurve.azimuth, averageCurve.reverseAzimuth, elevationChange)
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer()

    buffer.append(super.toString())
    buffer.append("elev12=")
    buffer.append(elevationChange)
    buffer.append(";p2p=")
    buffer.append(pointToPoint)
    buffer.append(";")

    buffer.toString()
  }
}
