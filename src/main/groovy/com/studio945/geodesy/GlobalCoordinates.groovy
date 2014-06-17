package com.studio945.geodesy

class GlobalCoordinates implements Comparable<GlobalCoordinates>, Serializable {
  /** Latitude in degrees. Negative latitude is southern hemisphere. */
  def double mLatitude

  /** Longitude in degrees. Negative longitude is western hemisphere. */
  def double mLongitude

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

  public void setLatitude(double latitude) {
    mLatitude = latitude;
    normalize();
  }

  public double getLongitude() {
    mLongitude;
  }

  public void setLongitude(double longitude) {
    mLongitude = longitude;
    normalize();
  }

  public int compareTo(GlobalCoordinates other) {
    if (mLongitude < other.mLongitude) return -1
    if (mLongitude > other.mLongitude) return 1
    if (mLatitude < other.mLatitude) return -1
    if (mLatitude > other.mLatitude) return 1

    0
  }

  @Override
  public int hashCode() {
    ((int) (mLongitude * mLatitude * 1000000 + 1021)) * 1000033;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GlobalCoordinates)) return false;

    GlobalCoordinates other = (GlobalCoordinates) obj;

    (mLongitude == other.mLongitude) && (mLatitude == other.mLatitude);
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append(Math.abs(mLatitude));
    buffer.append((mLatitude >= 0) ? 'N' : 'S');
    buffer.append(';');
    buffer.append(Math.abs(mLongitude));
    buffer.append((mLongitude >= 0) ? 'E' : 'W');
    buffer.append(';');

    buffer.toString();
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
