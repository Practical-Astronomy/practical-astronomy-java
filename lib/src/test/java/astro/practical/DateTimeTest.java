package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.DateTime;
import astro.practical.lib.Util;
import astro.practical.models.CivilDate;
import astro.practical.models.CivilDateTime;
import astro.practical.models.CivilTime;
import astro.practical.models.GreenwichSiderealTime;
import astro.practical.models.LocalSiderealTime;
import astro.practical.models.UniversalDateTime;
import astro.practical.models.UniversalTime;
import astro.practical.types.WarningFlag;

class DateTimeTest {
        DateTime testDateTime;

        @BeforeEach
        void setUp() {
                testDateTime = new DateTime();
        }

        @Test
        void testDateOfEaster() {
                CivilDate dateOfEaster1 = testDateTime.getDateOfEaster(2003);

                assertAll("Easter 2003",
                                () -> assertEquals(4, dateOfEaster1.month, "month should be April"),
                                () -> assertEquals(20, dateOfEaster1.day, "day should be 20"),
                                () -> assertEquals(2003, dateOfEaster1.year, "year should be 2003"));

                CivilDate dateOfEaster2 = testDateTime.getDateOfEaster(2019);
                assertAll("Easter 2019",
                                () -> assertEquals(4, dateOfEaster2.month, "month should be April"),
                                () -> assertEquals(21, dateOfEaster2.day, "day should be 21"),
                                () -> assertEquals(2019, dateOfEaster2.year, "year should be 2019"));

                CivilDate dateOfEaster3 = testDateTime.getDateOfEaster(2020);
                assertAll("Easter 2020",
                                () -> assertEquals(4, dateOfEaster3.month, "month should be April"),
                                () -> assertEquals(12, dateOfEaster3.day, "day should be 12"),
                                () -> assertEquals(2020, dateOfEaster3.year, "year should be 2020"));
        }

        @Test
        void testDayNumber() {
                assertEquals(1, testDateTime.civilDateToDayNumber(1, 1, 2000), "day number for 1/1/2000");
                assertEquals(61, testDateTime.civilDateToDayNumber(3, 1, 2000), "day number for 3/1/2000");
                assertEquals(152, testDateTime.civilDateToDayNumber(6, 1, 2003), "day number for 6/1/2003");
                assertEquals(331, testDateTime.civilDateToDayNumber(11, 27, 2009), "day number for 11/27/2009");
        }

        @Test
        void testCivilTimeToFromDecimalHours() {
                assertEquals(18.52416667,
                                Util.round(testDateTime.civilTimeToDecimalHours((double) 18, (double) 31,
                                                (double) 27), 8),
                                "Convert Civil Time to Decimal Hours");

                CivilTime civilTime = testDateTime.decimalHoursToCivilTime(18.52416667);

                assertAll("Convert Decimal Hours to Civil Time",
                                () -> assertEquals(18, civilTime.hours),
                                () -> assertEquals(31, civilTime.minutes),
                                () -> assertEquals(27, civilTime.seconds));
        }

        @Test
        void testLocalCivilTimeToFromUniversalTime() {
                UniversalDateTime uDT = testDateTime.localCivilTimeToUniversalTime(3, 37, 0, true, 4, 1, 7, 2013);

                assertAll("Convert Local Civil Time to Universal Time",
                                () -> assertEquals(22, uDT.hours),
                                () -> assertEquals(37, uDT.minutes),
                                () -> assertEquals(0, uDT.seconds),
                                () -> assertEquals(30, uDT.day),
                                () -> assertEquals(6, uDT.month),
                                () -> assertEquals(2013, uDT.year));

                CivilDateTime cDT = testDateTime.universalTimeToLocalCivilTime(22, 37, 0, true, 4, 30, 6, 2013);

                assertAll("Convert Universal Time to Local Civil Time",
                                () -> assertEquals(3, cDT.hours),
                                () -> assertEquals(37, cDT.minutes),
                                () -> assertEquals(0, cDT.seconds),
                                () -> assertEquals(1, cDT.day),
                                () -> assertEquals(7, cDT.month),
                                () -> assertEquals(2013, cDT.year));
        }

        @Test
        void testUniversalTimeToFromGreenwichSiderealTime() {
                GreenwichSiderealTime gST = testDateTime.universalTimeToGreenwichSiderealTime(14, 36, 51.67, 22, 4,
                                1980);

                assertAll("Convert Universal Time to Greenwich Sidereal Time",
                                () -> assertEquals(4, gST.hours),
                                () -> assertEquals(40, gST.minutes),
                                () -> assertEquals(5.23, gST.seconds));

                UniversalTime uT = testDateTime.greenwichSiderealTimeToUniversalTime(4, 40, 5.23, 22, 4, 1980);

                assertAll("Convert Greenwich Sidereal Time to Universal Time",
                                () -> assertEquals(14, uT.hours),
                                () -> assertEquals(36, uT.minutes),
                                () -> assertEquals(51.67, uT.seconds),
                                () -> assertEquals(WarningFlag.OK, uT.warningFlag));
        }

        @Test
        void testGreenwichSiderealTimeToFromLocalSiderealTime() {
                LocalSiderealTime lST = testDateTime.greenwichSiderealTimeToLocalSiderealTime(4, 40, 5.23, -64);

                assertAll("Convert Greenwich Sidereal Time to Local Sidereal Time",
                                () -> assertEquals(0, lST.hours),
                                () -> assertEquals(24, lST.minutes),
                                () -> assertEquals(5.23, lST.seconds));

                GreenwichSiderealTime gST = testDateTime.localSiderealTimeToGreenwichSiderealTime(0, 24, 5.23,
                                -64);

                assertAll("Convert Local Sidereal Time to Greenwich Sidereal Time",
                                () -> assertEquals(4, gST.hours),
                                () -> assertEquals(40, gST.minutes),
                                () -> assertEquals(5.23, gST.seconds));
        }
}
