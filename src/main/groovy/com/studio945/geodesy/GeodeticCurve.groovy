package com.studio945.geodesy

class GeodeticCurve implements Serializable {
  /** Ellipsoidal distance (in meters). */
  def double ellipsoidalDistance;

  /** Azimuth (degrees from north). */
  def double azimuth;

  /** Reverse azimuth (degrees from north). */
  def double reverseAzimuth;

  def GeodeticCurve(double ellipsoidalDistance, double azimuth, double reverseAzimuth) {
    this.ellipsoidalDistance = ellipsoidalDistance;
    this.azimuth = azimuth;
    this.reverseAzimuth = reverseAzimuth;
  }

//   public double getEllipsoidalDistance()
//   {
//      return ellipsoidalDistance;
//   }

  /**
   * Get the azimuth.
   * @return azimuth in degrees
   */
//   public double azimuth
//   {
//      return azimuth;
//   }

  /**
   * Get the reverse azimuth.
   * @return reverse azimuth in degrees
   */
//  public double reverseAzimuth {
//    return reverseAzimuth;
//  }

  /**
   * Get curve as a string.
   * @return
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("s=");
    buffer.append(ellipsoidalDistance);
    buffer.append(";a12=");
    buffer.append(azimuth);
    buffer.append(";a21=");
    buffer.append(reverseAzimuth);
    buffer.append(";");

    return buffer.toString();
  }
}
