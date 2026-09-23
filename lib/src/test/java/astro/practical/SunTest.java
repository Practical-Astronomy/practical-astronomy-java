package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Sun;
import astro.practical.models.EquationOfTime;
import astro.practical.models.MorningAndEveningTwilight;
import astro.practical.models.PositionOfSun;
import astro.practical.models.SunDistanceAndAngularSize;
import astro.practical.models.SunriseAndSunset;
import astro.practical.types.RiseSetStatus;
import astro.practical.types.TwilightStatus;
import astro.practical.types.TwilightType;

public class SunTest {
    private Sun testSun;

    @BeforeEach
    void setUp() {
        testSun = new Sun();
    }

    @Test
    void testApproximatePositionOfSun() {
        PositionOfSun approximatePositionOfSun = testSun.approximatePositionOfSun(0, 0, 0, 27, 7, 2003, false, 0);

        assertAll("Approximate Position of Sun",
                () -> assertEquals(8, approximatePositionOfSun.sunRAHour),
                () -> assertEquals(23, approximatePositionOfSun.sunRAMin),
                () -> assertEquals(33.73, approximatePositionOfSun.sunRASec),
                () -> assertEquals(19, approximatePositionOfSun.sunDecDeg),
                () -> assertEquals(21, approximatePositionOfSun.sunDecMin),
                () -> assertEquals(14.33, approximatePositionOfSun.sunDecSec));
    }

    @Test
    void testPrecisePositionOfSun() {
        PositionOfSun precisePositionOfSun = testSun.precisePositionOfSun(0, 0, 0, 27, 7, 1988, false, 0);

        assertAll("Precise Position of Sun",
                () -> assertEquals(8, precisePositionOfSun.sunRAHour),
                () -> assertEquals(26, precisePositionOfSun.sunRAMin),
                () -> assertEquals(3.83, precisePositionOfSun.sunRASec),
                () -> assertEquals(19, precisePositionOfSun.sunDecDeg),
                () -> assertEquals(12, precisePositionOfSun.sunDecMin),
                () -> assertEquals(49.72, precisePositionOfSun.sunDecSec));
    }

    @Test
    void testSunDistanceAndAngularSize() {
        SunDistanceAndAngularSize sunDistanceAndAngularSize = testSun.sunDistanceAndAngularSize(0, 0, 0, 27, 7, 1988,
                false, 0);

        assertAll("Sun Distance and Angular Size",
                () -> assertEquals(151920130, sunDistanceAndAngularSize.sunDistKm),
                () -> assertEquals(0, sunDistanceAndAngularSize.sunAngSizeDeg),
                () -> assertEquals(31, sunDistanceAndAngularSize.sunAngSizeMin),
                () -> assertEquals(29.93, sunDistanceAndAngularSize.sunAngSizeSec));
    }

    @Test
    void testSunriseAndSunset() {
        SunriseAndSunset sunriseAndSunset = testSun.sunriseAndSunset(10, 3, 1986, false, -5, -71.05, 42.37);

        assertAll("Sunrise and Sunset",
                () -> assertEquals(6, sunriseAndSunset.localSunriseHour),
                () -> assertEquals(5, sunriseAndSunset.localSunriseMinute),
                () -> assertEquals(17, sunriseAndSunset.localSunsetHour),
                () -> assertEquals(45, sunriseAndSunset.localSunsetMinute),
                () -> assertEquals(94.83, sunriseAndSunset.azimuthOfSunriseDeg),
                () -> assertEquals(265.43, sunriseAndSunset.azimuthOfSunsetDeg),
                () -> assertEquals(RiseSetStatus.OK, sunriseAndSunset.status));
    }

    @Test
    void testMorningAndEveningTwilight() {
        MorningAndEveningTwilight morningAndEveningTwilight = testSun.morningAndEveningTwilight(7, 9, 1979, false, 0, 0,
                52, TwilightType.ASTRONOMICAL);

        assertAll("Morning and Evening Twilight",
                () -> assertEquals(3, morningAndEveningTwilight.amTwilightBeginsHour),
                () -> assertEquals(17, morningAndEveningTwilight.amTwilightBeginsMin),
                () -> assertEquals(20, morningAndEveningTwilight.pmTwilightEndsHour),
                () -> assertEquals(37, morningAndEveningTwilight.pmTwilightEndsMin),
                () -> assertEquals(TwilightStatus.OK, morningAndEveningTwilight.status));
    }

    @Test
    void testEquationOfTime() {
        EquationOfTime equationOfTime = testSun.equationOfTime(27, 7, 2010);

        assertAll("Equation of Time",
                () -> assertEquals(6, equationOfTime.equationOfTimeMin),
                () -> assertEquals(31.52, equationOfTime.equationOfTimeSec));
    }

    @Test
    void testSolarElongation() {
        double solarElongation = testSun.solarElongation(10, 6, 45, 11, 57, 27, 27.8333333, 7, 2010);

        assertEquals(24.78, solarElongation, "Solar Elongation");
    }
}
