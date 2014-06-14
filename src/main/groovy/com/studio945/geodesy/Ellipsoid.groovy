package com.studio945.geodesy

class Ellipsoid implements Serializable
{
   def double semiMajorAxis
   def double semiMinorAxis
   def double flattening
   def double inverseFlattening

   /** The WGS84 ellipsoid. */
   static def final Ellipsoid WGS84 = fromAAndInverseF(6378137.0, 298.257223563)

   /** The GRS80 ellipsoid. */
   static def final Ellipsoid GRS80 = fromAAndInverseF(6378137.0, 298.257222101)

   /** The GRS67 ellipsoid. */
   static def final Ellipsoid GRS67 = fromAAndInverseF(6378160.0, 298.25)

   /** The ANS ellipsoid. */
   static def final Ellipsoid ANS = fromAAndInverseF(6378160.0, 298.25)

   /** The WGS72 ellipsoid. */
   static def final Ellipsoid WGS72 = fromAAndInverseF(6378135.0, 298.26)

   /** The Clarke1858 ellipsoid. */
   static def final Ellipsoid Clarke1858 = fromAAndInverseF(6378293.645, 294.26)

   /** The Clarke1880 ellipsoid. */
   static def final Ellipsoid Clarke1880 = fromAAndInverseF(6378249.145, 293.465)

   /** A spherical "ellipsoid". */
   static def final Ellipsoid Sphere = fromAAndF(6371000, 0.0)

   /**
    * Build an Ellipsoid from the semi major axis measurement and the inverse flattening.
    * @param semiMajor semi major axis (meters)
    * @param inverseFlattening
    * @return
    */
   static public Ellipsoid fromAAndInverseF(double semiMajor, double inverseFlattening)
   {
     double f = 1.0 / inverseFlattening
     double b = (1.0 - f) * semiMajor

     return new Ellipsoid(
       semiMajorAxis: semiMajor,
       semiMinorAxis: b,
       flattening: f,
       inverseFlattening: inverseFlattening)
   }

   /**
    * Build an Ellipsoid from the semi major axis measurement and the flattening.
    * @param semiMajor semi major axis (meters)
    * @param flattening
    * @return
    */
   static public Ellipsoid fromAAndF(double semiMajor, double flattening)
   {
     double inverseF = 1.0 / flattening
     double b = (1.0 - flattening) * semiMajor

     return new Ellipsoid(
       semiMajorAxis: semiMajor,
       semiMinorAxis: b,
       flattening: flattening,
       inverseFlattening: inverseF)
   }
}
