package com.studio945.geodesy

@Singleton
class Angle
{
   def static final double piOver180 = Math.PI / 180.0;
   
   def static double toRadians( double degrees ) { degrees * piOver180 }
   
   def static double toDegrees( double radians ) { radians / piOver180 }
}
