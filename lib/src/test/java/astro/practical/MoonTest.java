package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Moon;
import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.models.MoonDistAngDiamHorParallax;
import astro.practical.models.MoonPhase;
import astro.practical.models.MoonriseAndMoonset;
import astro.practical.models.PrecisePositionOfMoon;
import astro.practical.models.TimesOfNewMoonAndFullMoon;
import astro.practical.types.AccuracyLevel;

public class MoonTest {
    private Moon testMoon;

    @BeforeEach
    void setUp() {
        testMoon = new Moon();
    }

    @Test
    void testApproximatePositionOfMoon() {
        ApproximatePositionOfMoon approximatePositionOfMoon = testMoon.approximatePositionOfMoon(0, 0, 0, false, 0, 1,
                9, 2003);

        assertAll("Approximate Position of Moon",
                () -> assertEquals(14, approximatePositionOfMoon.moonRAHour),
                () -> assertEquals(12, approximatePositionOfMoon.moonRAMin),
                () -> assertEquals(42.31, approximatePositionOfMoon.moonRASec),
                () -> assertEquals(-11, approximatePositionOfMoon.moonDecDeg),
                () -> assertEquals(31, approximatePositionOfMoon.moonDecMin),
                () -> assertEquals(38.27, approximatePositionOfMoon.moonDecSec));
    }

    @Test
    void testPrecisePositionOfMoon() {
        PrecisePositionOfMoon precisePositionOfMoon = testMoon.precisePositionOfMoon(0, 0, 0, false, 0, 1, 9, 2003);

        assertAll("Precise Position of Moon",
                () -> assertEquals(14, precisePositionOfMoon.moonRAHour),
                () -> assertEquals(12, precisePositionOfMoon.moonRAMin),
                () -> assertEquals(10.21, precisePositionOfMoon.moonRASec),
                () -> assertEquals(-11, precisePositionOfMoon.moonDecDeg),
                () -> assertEquals(34, precisePositionOfMoon.moonDecMin),
                () -> assertEquals(57.83, precisePositionOfMoon.moonDecSec),
                () -> assertEquals(367964, precisePositionOfMoon.earthMoonDistKM),
                () -> assertEquals(0.993191, precisePositionOfMoon.moonHorParallaxDeg));
    }

    @Test
    void testMoonPhase() {
        MoonPhase moonPhase = testMoon.moonPhase(0, 0, 0, false, 0, 1, 9, 2003, AccuracyLevel.APPROXIMATE);

        assertAll("Moon Phase and Bright Limb",
                () -> assertEquals(0.22, moonPhase.moonPhase),
                () -> assertEquals(-71.58, moonPhase.paBrightLimbDeg));
    }

    @Test
    void testTimesOfNewMoonAndFullMoon() {
        TimesOfNewMoonAndFullMoon timesOfNewMoonAndFullMoon = testMoon.timesOfNewMoonAndFullMoon(false, 0, 1, 9, 2003);

        assertAll("Times of New Moon and Full Moon",
                () -> assertEquals(17, timesOfNewMoonAndFullMoon.nmLocalTimeHour),
                () -> assertEquals(27, timesOfNewMoonAndFullMoon.nmLocalTimeMin),
                () -> assertEquals(27, timesOfNewMoonAndFullMoon.nmLocalDateDay),
                () -> assertEquals(8, timesOfNewMoonAndFullMoon.nmLocalDateMonth),
                () -> assertEquals(2003, timesOfNewMoonAndFullMoon.nmLocalDateYear),
                () -> assertEquals(16, timesOfNewMoonAndFullMoon.fmLocalTimeHour),
                () -> assertEquals(36, timesOfNewMoonAndFullMoon.fmLocalTimeMin),
                () -> assertEquals(10, timesOfNewMoonAndFullMoon.fmLocalDateDay),
                () -> assertEquals(9, timesOfNewMoonAndFullMoon.fmLocalDateMonth),
                () -> assertEquals(2003, timesOfNewMoonAndFullMoon.fmLocalDateYear));
    }

    @Test
    void testMoonDistAngDiamHorParallax() {
        MoonDistAngDiamHorParallax moonDistAngDiamHorParallax = testMoon.moonDistAngDiamHorParallax(0, 0, 0, false, 0,
                1, 9, 2003);

        assertAll("Moon distance, angular diameter, and horizontal parallax",
                () -> assertEquals(367964, moonDistAngDiamHorParallax.earthMoonDist),
                () -> assertEquals(0, moonDistAngDiamHorParallax.angDiameterDeg),
                () -> assertEquals(32, moonDistAngDiamHorParallax.angDiameterMin),
                () -> assertEquals(0, moonDistAngDiamHorParallax.horParallaxDeg),
                () -> assertEquals(59, moonDistAngDiamHorParallax.horParallaxMin),
                () -> assertEquals(35.49, moonDistAngDiamHorParallax.horParallaxSec));
    }

    @Test
    void testMoonriseAndMoonset() {
        MoonriseAndMoonset moonriseAndMoonset = testMoon.moonriseAndMoonset(6, 3, 1986, false, -5, -71.05, 42.3667);

        assertAll("Moonrise and Moonset",
                () -> assertEquals(4, moonriseAndMoonset.mrLTHour),
                () -> assertEquals(21, moonriseAndMoonset.mrLTMin),
                () -> assertEquals(6, moonriseAndMoonset.mrLocalDateDay),
                () -> assertEquals(3, moonriseAndMoonset.mrLocalDateMonth),
                () -> assertEquals(1986, moonriseAndMoonset.mrLocalDateYear),
                () -> assertEquals(127.34, moonriseAndMoonset.mrAzimuthDeg),
                () -> assertEquals(13, moonriseAndMoonset.msLTHour),
                () -> assertEquals(8, moonriseAndMoonset.msLTMin),
                () -> assertEquals(6, moonriseAndMoonset.msLocalDateDay),
                () -> assertEquals(3, moonriseAndMoonset.msLocalDateMonth),
                () -> assertEquals(1986, moonriseAndMoonset.msLocalDateYear),
                () -> assertEquals(234.05, moonriseAndMoonset.msAzimuthDeg));
    }
}
