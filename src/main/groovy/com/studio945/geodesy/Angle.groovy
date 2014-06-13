package com.studio945.geodesy;

@Singleton
class Angle
{
   /** Degrees/Radians conversion constant. */
   static private final double PiOver180 = Math.PI / 180.0;
   
   /**
    * Convert degrees to radians.
    * @param degrees
    * @return
    */
   static public double toRadians( double degrees )
   {
      return degrees * PiOver180;
   }
   
   /**
    * Convert radians to degrees.
    * @param radians
    * @return
    */
   static public double toDegrees( double radians )
   {
      return radians / PiOver180;
   }
}
