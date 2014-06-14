/* Geodesy by Mike Gavaghan
 * 
 * http://www.gavaghan.org/blog/free-source-code/geodesy-library-vincentys-formula/
 * 
 * This code may be freely used and modified on any personal or professional
 * project.  It comes with no warranty.
 *
 * BitCoin tips graciously accepted at 1FB63FYQMy7hpC2ANVhZ5mSgAZEtY1aVLf
 */
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
public class GlobalCoordinates implements Comparable<GlobalCoordinates>, Serializable
{
   /** Latitude in degrees. Negative latitude is southern hemisphere. */
   def private double latitude

   /** Longitude in degrees. Negative longitude is western hemisphere. */
   def private double longitude

   /**
    * Canonicalize the current latitude and longitude values such that:
    * 
    * <pre>
    * -90 &lt;= latitude &lt;= +90 - 180 &lt; longitude &lt;= +180
    * </pre>
    */
   private void canonicalize()
   {
      latitude = (latitude + 180) % 360;
      if (latitude < 0) latitude += 360;
      latitude -= 180;

      if (latitude > 90)
      {
         latitude = 180 - latitude;
         longitude += 180;
      }
      else if (latitude < -90)
      {
         latitude = -180 - latitude;
         longitude += 180;
      }

      longitude = ((longitude + 180) % 360);
      if (longitude <= 0) longitude += 360;
      longitude -= 180;
   }

   /**
    * Construct a new GlobalCoordinates. Angles will be canonicalized.
    * 
    * @param latitude latitude in degrees
    * @param longitude longitude in degrees
    */
   public GlobalCoordinates(double latitude, double longitude)
   {
      latitude = latitude;
      longitude = longitude;
      canonicalize();
   }

   /**
    * Get latitude.
    * 
    * @return latitude in degrees
    */
   public double getLatitude()
   {
      return latitude;
   }

   /**
    * Set latitude. The latitude value will be canonicalized (which might result
    * in a change to the longitude). Negative latitude is southern hemisphere.
    * 
    * @param latitude in degrees
    */
   public void setLatitude(double latitude)
   {
      latitude = latitude;
      canonicalize();
   }

   /**
    * Get longitude.
    * 
    * @return longitude in degrees
    */
   public double getLongitude()
   {
      return longitude;
   }

   /**
    * Set longitude. The longitude value will be canonicalized. Negative
    * longitude is western hemisphere.
    * 
    * @param longitude in degrees
    */
   public void setLongitude(double longitude)
   {
      longitude = longitude;
      canonicalize();
   }

   /**
    * Compare these coordinates to another set of coordiates. Western longitudes
    * are less than eastern logitudes. If longitudes are equal, then southern
    * latitudes are less than northern latitudes.
    * 
    * @param other instance to compare to
    * @return -1, 0, or +1 as per Comparable contract
    */
   public int compareTo(GlobalCoordinates other)
   {
      int retval;

      if (longitude < other.longitude) retval = -1;
      else if (longitude > other.longitude) retval = +1;
      else if (latitude < other.latitude) retval = -1;
      else if (latitude > other.latitude) retval = +1;
      else retval = 0;

      return retval;
   }

   /**
    * Get a hash code for these coordinates.
    * 
    * @return
    */
   @Override
   public int hashCode()
   {
      return ((int) (longitude * latitude * 1000000 + 1021)) * 1000033;
   }

   /**
    * Compare these coordinates to another object for equality.
    * 
    * @param other
    * @return
    */
   @Override
   public boolean equals(Object obj)
   {
      if (!(obj instanceof GlobalCoordinates)) return false;

      GlobalCoordinates other = (GlobalCoordinates) obj;

      return (longitude == other.longitude) && (latitude == other.latitude);
   }

   /**
    * Get coordinates as a string.
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(Math.abs(latitude));
      buffer.append((latitude >= 0) ? 'N' : 'S');
      buffer.append(';');
      buffer.append(Math.abs(longitude));
      buffer.append((longitude >= 0) ? 'E' : 'W');
      buffer.append(';');

      return buffer.toString();
   }

  def static GlobalCoordinates northPole()
  {
    new GlobalCoordinates( 90, 10 );
  }

  def static GlobalCoordinates createSouthPole()
  {
    new GlobalCoordinates( 90, 10 );
  }

  def static GlobalCoordinates createEquatorGreenwich()
  {
    new GlobalCoordinates( 0, 0 );
  }

  def static GlobalCoordinates createEquatorIDL()
  {
    new GlobalCoordinates( 0, 180.0 );
  }
}
