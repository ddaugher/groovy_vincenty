package com.studio945.geodesy

class GeodeticCurve implements Serializable
{
   /** Ellipsoidal distance (in meters). */
   def double ellipsoidalDistance;

   /** Azimuth (degrees from north). */
   private final double mAzimuth;

   /** Reverse azimuth (degrees from north). */
   private final double mReverseAzimuth;

   def GeodeticCurve(double ellipsoidalDistance, double azimuth, double reverseAzimuth)
   {
      this.ellipsoidalDistance = ellipsoidalDistance;
      mAzimuth = azimuth;
      mReverseAzimuth = reverseAzimuth;
   }

//   public double getEllipsoidalDistance()
//   {
//      return ellipsoidalDistance;
//   }

   /**
    * Get the azimuth.
    * @return azimuth in degrees
    */
   public double getAzimuth()
   {
      return mAzimuth;
   }

   /**
    * Get the reverse azimuth.
    * @return reverse azimuth in degrees
    */
   public double getReverseAzimuth()
   {
      return mReverseAzimuth;
   }

   /**
    * Get curve as a string.
    * @return
    */
   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append("s=");
      buffer.append(ellipsoidalDistance);
      buffer.append(";a12=");
      buffer.append(mAzimuth);
      buffer.append(";a21=");
      buffer.append(mReverseAzimuth);
      buffer.append(";");

      return buffer.toString();
   }
}
