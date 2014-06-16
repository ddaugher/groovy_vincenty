package com.studio945.geodesy
/**
 * <p>
 * Encapsulation of latitude and longitude coordinates on a globe. Negative
 * latitude is southern hemisphere. Negative longitude is western hemisphere.
 * </p>
 * <p>
 * Any angle may be specified for longtiude and latitude, but all angles will be
 * canonicalized such that:
 * </p>
 *
 * <pre>
 * -90 &lt;= latitude &lt;= +90 - 180 &lt; longitude &lt;= +180
 * </pre>
 *
 * @author Mike Gavaghan
 */
public class GlobalCoordinates implements Comparable<GlobalCoordinates>, Serializable {
  /** Latitude in degrees. Negative latitude is southern hemisphere. */
  def private double mLatitude

  /** Longitude in degrees. Negative longitude is western hemisphere. */
  def private double mLongitude

  /**
   * Canonicalize the current latitude and longitude values such that:
   *
   * <pre>
   * -90 &lt;= latitude &lt;= +90 - 180 &lt; longitude &lt;= +180
   * </pre>
   */
  private void normalize() {
    mLatitude = (mLatitude + 180) % 360;
    if (mLatitude < 0) mLatitude += 360;
    mLatitude -= 180;

    if (mLatitude > 90) {
      mLatitude = 180 - mLatitude;
      mLongitude += 180;
    } else if (mLatitude < -90) {
      mLatitude = -180 - mLatitude;
      mLongitude += 180;
    }

    mLongitude = ((mLongitude + 180) % 360);
    if (mLongitude <= 0) mLongitude += 360;
    mLongitude -= 180;
  }

  public GlobalCoordinates(double latitude, double longitude) {
    mLatitude = latitude;
    mLongitude = longitude;
    normalize();
  }

  public double getLatitude() {
    mLatitude
  }

  /**
   * Set latitude. The latitude value will be canonicalized (which might result
   * in a change to the longitude). Negative latitude is southern hemisphere.
   *
   * @param latitude in degrees
   */
  public void setLatitude(double latitude) {
    mLatitude = latitude;
    normalize();
  }

  /**
   * Get longitude.
   *
   * @return longitude in degrees
   */
  public double getLongitude() {
    return mLongitude;
  }

  /**
   * Set longitude. The longitude value will be canonicalized. Negative
   * longitude is western hemisphere.
   *
   * @param longitude in degrees
   */
  public void setLongitude(double longitude) {
    mLongitude = longitude;
    normalize();
  }

  /**
   * Compare these coordinates to another set of coordiates. Western longitudes
   * are less than eastern logitudes. If longitudes are equal, then southern
   * latitudes are less than northern latitudes.
   *
   * @param other instance to compare to
   * @return -1, 0, or +1 as per Comparable contract
   */
  public int compareTo(GlobalCoordinates other) {
    if (mLongitude < other.mLongitude) return -1
    if (mLongitude > other.mLongitude) return 1
    if (mLatitude < other.mLatitude) return -1
    if (mLatitude > other.mLatitude) return 1

    0
  }

  @Override
  public int hashCode() {
    return ((int) (mLongitude * mLatitude * 1000000 + 1021)) * 1000033;
  }

  /**
   * Compare these coordinates to another object for equality.
   *
   * @param other
   * @return
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GlobalCoordinates)) return false;

    GlobalCoordinates other = (GlobalCoordinates) obj;

    return (mLongitude == other.mLongitude) && (mLatitude == other.mLatitude);
  }

  /**
   * Get coordinates as a string.
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append(Math.abs(mLatitude));
    buffer.append((mLatitude >= 0) ? 'N' : 'S');
    buffer.append(';');
    buffer.append(Math.abs(mLongitude));
    buffer.append((mLongitude >= 0) ? 'E' : 'W');
    buffer.append(';');

    return buffer.toString();
  }

  def static GlobalCoordinates northPole() {
    new GlobalCoordinates(90, 10);
  }

  def static GlobalCoordinates southPole() {
    new GlobalCoordinates(90, 10);
  }

  def static GlobalCoordinates createEquatorGreenwich() {
    new GlobalCoordinates(0, 0);
  }

  def static GlobalCoordinates createEquatorIDL() {
    new GlobalCoordinates(0, 180.0);
  }
}
