package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Coordinates;
import astro.practical.lib.Util;
import astro.practical.models.Aberration;
import astro.practical.models.Angle;
import astro.practical.models.EclipticCoordinates;
import astro.practical.models.EquatorialCoordinatesHA;
import astro.practical.models.EquatorialCoordinatesRA;
import astro.practical.models.GalacticCoordinates;
import astro.practical.models.HeliographicCoordinates;
import astro.practical.models.HorizonCoordinates;
import astro.practical.models.HourAngle;
import astro.practical.models.Nutation;
import astro.practical.models.RightAscension;
import astro.practical.models.RightAscensionDeclination;
import astro.practical.models.RiseSet;
import astro.practical.models.SelenographicCoordinates1;
import astro.practical.models.SelenographicCoordinates2;
import astro.practical.types.AngleMeasure;
import astro.practical.types.CoordinateType;
import astro.practical.types.RiseSetStatus;

public class CoordinatesTest {
        private Coordinates testCoordinates;

        @BeforeEach
        void setUp() {
                testCoordinates = new Coordinates();
        }

        @Test
        void testAngleToFromDecimalDegrees() {
                double decimalDegrees = testCoordinates.angleToDecimalDegrees(182, 31, 27);

                assertEquals(182.524167, Util.round(decimalDegrees, 6), "Decimal Degrees for 182d 31m 27s");

                Angle angle = testCoordinates.decimalDegreesToAngle(182.524167);

                assertAll("Angle for 182.524167 decimal degrees",
                                () -> assertEquals(182, angle.degrees),
                                () -> assertEquals(31, angle.minutes),
                                () -> assertEquals(27, angle.seconds));
        }

        @Test
        void testRightAscensionToFromHourAngle() {
                HourAngle hourAngle = testCoordinates.rightAscensionToHourAngle(18, 32, 21, 14, 36, 51.67, false, -4,
                                22, 4,
                                1980, -64);

                assertAll(
                                "Hour Angle for Right Ascension 18h 32m 21s, Local Civil Time 14:36:51.67, Local Date 4/22/1980, Geographical Longitude -64d",
                                () -> assertEquals(9, hourAngle.hours),
                                () -> assertEquals(52, hourAngle.minutes),
                                () -> assertEquals(23.66, hourAngle.seconds));

                RightAscension rightAscension = testCoordinates.hourAngleToRightAscension(9, 52, 23.66, 14, 36, 51.67,
                                false,
                                -4, 22, 4, 1980, -64);

                assertAll(
                                "Right Ascension for Hour Angle 9h 52m 23.66s, Local Civil Time 14:36:51.67, Local Date 4/22/1980, Geographical Latitude -64d",
                                () -> assertEquals(18, rightAscension.hours),
                                () -> assertEquals(32, rightAscension.minutes),
                                () -> assertEquals(21, rightAscension.seconds));
        }

        @Test
        void testEquatorialCoordinatesToFromHorizonCoordinates() {
                HorizonCoordinates horizonCoordinates = testCoordinates.equatorialCoordinatesToHorizonCoordinates(5, 51,
                                44, 23, 13, 10, 52);

                assertAll("Horizon Coordinates for Hour Angle 05:51:44 and Declination 23d 13m 10s and Geographical Latitude 52d",
                                () -> assertEquals(283, horizonCoordinates.azimuthDegrees),
                                () -> assertEquals(16, horizonCoordinates.azimuthMinutes),
                                () -> assertEquals(15.7, horizonCoordinates.azimuthSeconds),
                                () -> assertEquals(19, horizonCoordinates.altitudeDegrees),
                                () -> assertEquals(20, horizonCoordinates.altitudeMinutes),
                                () -> assertEquals(3.64, horizonCoordinates.altitudeSeconds));

                EquatorialCoordinatesHA equatorialCoordinates = testCoordinates
                                .horizonCoordinatesToEquatorialCoordinates(283, 16, 15.7, 19, 20, 3.64, 52);

                assertAll("Equatorial Coordinates for Azimuth 283d 16m 15.7s and Altitude 19d 20m 3.64s and Geographical Latitude 52d",
                                () -> assertEquals(5, equatorialCoordinates.hourAngleHours),
                                () -> assertEquals(51, equatorialCoordinates.hourAngleMinutes),
                                () -> assertEquals(44, equatorialCoordinates.hourAngleSeconds),
                                () -> assertEquals(23, equatorialCoordinates.declinationDegrees),
                                () -> assertEquals(13, equatorialCoordinates.declinationMinutes),
                                () -> assertEquals(10, equatorialCoordinates.declinationSeconds));

        }

        @Test
        void testMeanObliquityOfTheEcliptic() {
                double meanObliquityOfTheEcliptic = Util.round(testCoordinates.meanObliquityOfTheEcliptic(6, 7, 2009),
                                8);

                assertEquals(23.43805531, meanObliquityOfTheEcliptic, "Mean Obliquity of the Ecliptic for 7/6/2009");
        }

        @Test
        void testEclipticCoordinateToFromEquatorialCoordinate() {
                EquatorialCoordinatesRA equatorialCoordinatesRA = testCoordinates
                                .eclipticCoordinateToEquatorialCoordinate(139, 41, 10, 4, 52, 31, 6, 7, 2009);

                assertAll("Equatorial Coordinates for Ecliptic Longitude 139d 41m 10s and Ecliptic Latitude 4d 52m 31s and Greenwich Date 7/6/2009",
                                () -> assertEquals(9, equatorialCoordinatesRA.rightAscensionHours),
                                () -> assertEquals(34, equatorialCoordinatesRA.rightAscensionMinutes),
                                () -> assertEquals(53.4, equatorialCoordinatesRA.rightAscensionSeconds),
                                () -> assertEquals(19, equatorialCoordinatesRA.declinationDegrees),
                                () -> assertEquals(32, equatorialCoordinatesRA.declinationMinutes),
                                () -> assertEquals(8.52, equatorialCoordinatesRA.declinationSeconds));

                EclipticCoordinates eclipticCoordinates = testCoordinates.equatorialCoordinateToEclipticCoordinate(9,
                                34, 53.4, 19, 32, 8.52, 6, 7, 2009);

                assertAll("Ecliptic Coordinates for Right Ascension 9h 34m 53.4s and Declination 19d 32h 8.52s and Greenwich Date 7/6/2009",
                                () -> assertEquals(139, eclipticCoordinates.longitudeDegrees),
                                () -> assertEquals(41, eclipticCoordinates.longitudeMinutes),
                                () -> assertEquals(9.97, eclipticCoordinates.longitudeSeconds),
                                () -> assertEquals(4, eclipticCoordinates.latitudeDegrees),
                                () -> assertEquals(52, eclipticCoordinates.latitudeMinutes),
                                () -> assertEquals(30.99, eclipticCoordinates.latitudeSeconds));
        }

        @Test
        void testEquatorialCoordinateToFromGalacticCoordinate() {
                GalacticCoordinates galacticCoordinates = testCoordinates.equatorialCoordinateToGalacticCoordinate(10,
                                21, 0, 10, 3, 11);

                assertAll("Galactic Coordinates for Equatorial Longitude 10d 21m 0s and Equatorial Latitude 10d 3m 11s",
                                () -> assertEquals(232, galacticCoordinates.longitudeDegrees),
                                () -> assertEquals(14, galacticCoordinates.longitudeMinutes),
                                () -> assertEquals(52.38, galacticCoordinates.longitudeSeconds),
                                () -> assertEquals(51, galacticCoordinates.latitudeDegrees),
                                () -> assertEquals(7, galacticCoordinates.latitudeMinutes),
                                () -> assertEquals(20.16, galacticCoordinates.latitudeSeconds));

                EquatorialCoordinatesRA equatorialCoordinatesRA = testCoordinates
                                .galacticCoordinateToEquatorialCoordinate(232, 14, 52.38, 51, 7, 20.16);

                assertAll("Equatorial Coordinates for Galactic Longitude 232d 14m 52.38s and Galactic Latitude 51d 7m 20.16s",
                                () -> assertEquals(10, equatorialCoordinatesRA.rightAscensionHours),
                                () -> assertEquals(21, equatorialCoordinatesRA.rightAscensionMinutes),
                                () -> assertEquals(0, equatorialCoordinatesRA.rightAscensionSeconds),
                                () -> assertEquals(10, equatorialCoordinatesRA.declinationDegrees),
                                () -> assertEquals(3, equatorialCoordinatesRA.declinationMinutes),
                                () -> assertEquals(11, equatorialCoordinatesRA.declinationSeconds));
        }

        @Test
        void testAngleBetweenTwoObjects() {
                Angle angle = testCoordinates.angleBetweenTwoObjects(5, 13, 31.7, -8, 13, 30, 6, 44, 13.4, -16, 41, 11,
                                AngleMeasure.HOURS);

                assertAll("Angle for RA (1) 5h 13m 31.7s and Declination (1) -8d 13m 30s and RA (2) 6h 44m 13.4s and Declination (2) -16d 41m 11s and measurement in hours",
                                () -> assertEquals(23, angle.degrees),
                                () -> assertEquals(40, angle.minutes),
                                () -> assertEquals(25.86, angle.seconds));
        }

        @Test
        void testRisingAndSetting() {
                RiseSet riseSet = testCoordinates.risingAndSetting(23, 39, 20, 21, 42, 0, 24, 8, 2010, 64, 30, 0.5667);

                assertAll("Rise and Set Times for Right Ascension 23h 39m 20s and Declination 21d 42m 0s and Greenwich Date 8/24/2010 and Geographical Longitude/Latitude 64d/30d and Vertical Shift 0.5667d",
                                () -> assertEquals(RiseSetStatus.OK, riseSet.riseSetStatus),
                                () -> assertEquals(14, riseSet.utRiseHour),
                                () -> assertEquals(16, riseSet.utRiseMin),
                                () -> assertEquals(4, riseSet.utSetHour),
                                () -> assertEquals(10, riseSet.utSetMin),
                                () -> assertEquals(64.36, riseSet.azRise),
                                () -> assertEquals(295.64, riseSet.azSet));
        }

        @Test
        void testCorrectForPrecession() {
                RightAscensionDeclination rightAscensionDeclination = testCoordinates.correctForPrecession(9, 10, 43,
                                14, 23, 25, 0.923, 1, 1950, 1, 6, 1979);

                assertAll("Corrected Precession for Right Ascension 9h 10m 43s and Declination 14d 23m 25s and Epoch 1 Date 1/0.923/1950 and Epoch 2 Date 6/1/1979",
                                () -> assertEquals(9, rightAscensionDeclination.rightAscensionHours),
                                () -> assertEquals(12, rightAscensionDeclination.rightAscensionMinutes),
                                () -> assertEquals(20.18, rightAscensionDeclination.rightAscensionSeconds),
                                () -> assertEquals(14, rightAscensionDeclination.declinationDegrees),
                                () -> assertEquals(16, rightAscensionDeclination.declinationMinutes),
                                () -> assertEquals(9.12, rightAscensionDeclination.declinationSeconds));
        }

        @Test
        void testNutationInEclipticLongitudeAndObliquity() {
                Nutation nutation = testCoordinates.nutationInEclipticLongitudeAndObliquity(1, 9, 1988);

                assertAll("Nutation in Ecliptic Longitude and Obliquity for Greenwich Date 9/1/1988",
                                () -> assertEquals(0.001525808, Util.round(nutation.eclipticLongitudeDegrees, 9)),
                                () -> assertEquals(0.0025671, Util.round(nutation.obliquityDegrees, 7)));
        }

        @Test
        void testCorrectForAberration() {
                Aberration aberration = testCoordinates.correctForAberration(0, 0, 0, 8, 9, 1988, 352, 37, 10.1, -1, 32,
                                56.4);

                assertAll("Ecliptic Coordinates, corrected for aberration, for UT 00:00:00 and Greenwich Date 9/8/1988 and Ecliptic Longitude 352d 37m 10.1s and Ecliptic Latitude -1d 32m 56.4s",
                                () -> assertEquals(352, aberration.apparentEclLongDeg),
                                () -> assertEquals(37, aberration.apparentEclLongMin),
                                () -> assertEquals(30.45, aberration.apparentEclLongSec),
                                () -> assertEquals(-1, aberration.apparentEclLatDeg),
                                () -> assertEquals(32, aberration.apparentEclLatMin),
                                () -> assertEquals(56.33, aberration.apparentEclLatSec));
        }

        @Test
        void testRefraction() {
                RightAscensionDeclination correctedCoordinates = testCoordinates.atmosphericRefraction(23, 14, 0, 40,
                                10, 0, CoordinateType.TRUE, 0.17, 51.2036110, 0, 0, 23, 3, 1987, 1, 1, 24, 1012, 21.7);

                assertAll("RA/Dec, corrected for atmospheric refraction",
                                () -> assertEquals(23, correctedCoordinates.rightAscensionHours),
                                () -> assertEquals(13, correctedCoordinates.rightAscensionMinutes),
                                () -> assertEquals(44.74, correctedCoordinates.rightAscensionSeconds),
                                () -> assertEquals(40, correctedCoordinates.declinationDegrees),
                                () -> assertEquals(19, correctedCoordinates.declinationMinutes),
                                () -> assertEquals(45.76, correctedCoordinates.declinationSeconds));
        }

        @Test
        void testParallax() {
                RightAscensionDeclination correctedCoordinates = testCoordinates.correctionsForGeocentricParallax(22,
                                35, 19, -7, 41, 13, CoordinateType.TRUE, 1.019167, -100, 50, 60, 0, -6, 26, 2, 1979, 10,
                                45, 0);

                assertAll("RA/Dec, corrected for geocentric parallax",
                                () -> assertEquals(22, correctedCoordinates.rightAscensionHours),
                                () -> assertEquals(36, correctedCoordinates.rightAscensionMinutes),
                                () -> assertEquals(43.22, correctedCoordinates.rightAscensionSeconds),
                                () -> assertEquals(-8, correctedCoordinates.declinationDegrees),
                                () -> assertEquals(32, correctedCoordinates.declinationMinutes),
                                () -> assertEquals(17.4, correctedCoordinates.declinationSeconds));
        }

        @Test
        void testHeliographicCoordinates() {
                HeliographicCoordinates heliographicCoordinates = testCoordinates.heliographicCoordinates(220, 10.5, 1,
                                5, 1988);

                assertAll("Heliographic Coordinates",
                                () -> assertEquals(142.59, heliographicCoordinates.helioLongDeg),
                                () -> assertEquals(-19.94, heliographicCoordinates.helioLatDeg));
        }

        @Test
        void testCarringtonRotationNumber() {
                int carringtonRotationNumber = testCoordinates.carringtonRotationNumber(27, 1, 1975);

                assertEquals(1624, carringtonRotationNumber, "Carrington Rotation Number");
        }

        @Test
        void testSelenographicCoordinates1() {
                SelenographicCoordinates1 selenographicCoordinates1 = testCoordinates.selenographicCoordinates1(1, 5,
                                1988);

                assertAll("Selenographic Coordinates (1)",
                                () -> assertEquals(-4.88, selenographicCoordinates1.subEarthLongitude),
                                () -> assertEquals(4.04, selenographicCoordinates1.subEarthLatitude),
                                () -> assertEquals(19.78, selenographicCoordinates1.positionAngleOfPole));
        }

        @Test
        void testSelenographicCoordinates2() {
                SelenographicCoordinates2 selenographicCoordinates2 = testCoordinates.selenographicCoordinates2(1, 5,
                                1988);

                assertAll("Selenographic Coordinates (2)",
                                () -> assertEquals(6.81, selenographicCoordinates2.subSolarLongitude),
                                () -> assertEquals(83.19, selenographicCoordinates2.subSolarColongitude),
                                () -> assertEquals(1.19, selenographicCoordinates2.subSolarLatitude));
        }
}
