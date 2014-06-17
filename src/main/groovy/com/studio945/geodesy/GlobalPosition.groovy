package com.studio945.geodesy

class GlobalPosition extends GlobalCoordinates {
  def double elevation;

  public GlobalPosition(double latitude, double longitude, double elevation) {
    super(latitude, longitude);
    this.elevation = elevation;
  }

  public GlobalPosition(GlobalCoordinates coords, double elevation) {
    this(coords.getLatitude(), coords.getLongitude(), elevation);
  }

  public int compareTo(GlobalPosition other) {
    int retval = super.compareTo(other);

    if (retval == 0) {
      if (elevation < other.elevation) retval = -1;
      else if (elevation > other.elevation) retval = +1;
    }

    retval;
  }

  @Override
  public int hashCode() {
    int hash = super.hashCode();

    if (elevation != 0.0) hash *= (int) elevation;

    hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GlobalPosition)) return false;

    GlobalPosition other = (GlobalPosition) obj;

    (elevation == other.elevation) && (mLatitude == other.mLatitude) && (mLongitude == other.mLongitude);
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append(super.toString());
    buffer.append("elevation=");
    buffer.append(Double.toString(elevation));
    buffer.append("m");

    buffer.toString();
  }
}
