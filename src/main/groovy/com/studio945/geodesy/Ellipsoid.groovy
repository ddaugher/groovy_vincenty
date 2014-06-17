package com.studio945.geodesy

class Ellipsoid implements Serializable
{
   def double semiMajorAxis
   def double semiMinorAxis
   def double flattening
   def double inverseFlattening

   static def final Ellipsoid WGS84 = createFromSemiMajorAxisAndInverseFlattening(6378137.0, 298.257223563)
   static def final Ellipsoid GRS80 = createFromSemiMajorAxisAndInverseFlattening(6378137.0, 298.257222101)
   static def final Ellipsoid GRS67 = createFromSemiMajorAxisAndInverseFlattening(6378160.0, 298.25)
   static def final Ellipsoid ANS = createFromSemiMajorAxisAndInverseFlattening(6378160.0, 298.25)
   static def final Ellipsoid WGS72 = createFromSemiMajorAxisAndInverseFlattening(6378135.0, 298.26)
   static def final Ellipsoid Clarke1858 = createFromSemiMajorAxisAndInverseFlattening(6378293.645, 294.26)
   static def final Ellipsoid Clarke1880 = createFromSemiMajorAxisAndInverseFlattening(6378249.145, 293.465)
   static def final Ellipsoid Sphere = createFromSemiMajorAxisAndFlattening(6371000, 0.0)

   static public Ellipsoid createFromSemiMajorAxisAndInverseFlattening(double semiMajorAxis, double inverseFlattening)
   {
     double flattening = 1.0 / inverseFlattening
     double semiMinorAxis = (1.0 - flattening) * semiMajorAxis

     new Ellipsoid(
       semiMajorAxis: semiMajorAxis,
       semiMinorAxis: semiMinorAxis,
       flattening: flattening,
       inverseFlattening: inverseFlattening)
   }

   static public Ellipsoid createFromSemiMajorAxisAndFlattening(double semiMajorAxis, double flattening)
   {
     double inverseFlattening = 1.0 / flattening
     double semiMinorAxis = (1.0 - flattening) * semiMajorAxis

     new Ellipsoid(
       semiMajorAxis: semiMajorAxis,
       semiMinorAxis: semiMinorAxis,
       flattening: flattening,
       inverseFlattening: inverseFlattening)
   }
}
