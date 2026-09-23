package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Eclipses;
import astro.practical.models.LunarEclipseCircumstances;
import astro.practical.models.LunarEclipseOccurrenceDetails;
import astro.practical.models.SolarEclipseCircumstances;
import astro.practical.models.SolarEclipseOccurrence;
import astro.practical.types.EclipseOccurrence;

public class EclipsesTest {
    private Eclipses testEclipse;

    @BeforeEach
    void setUp() {
        testEclipse = new Eclipses();
    }

    @Test
    void testLunarEclipseOccurrenceDetails() {
        LunarEclipseOccurrenceDetails lunarEclipseOccurrenceDetails = testEclipse.lunarEclipseOccurrenceDetails(1, 4,
                2015, false, 10);

        assertAll("Lunar Eclipse Occurrence Details",
                () -> assertEquals(EclipseOccurrence.ECLIPSE_CERTAIN, lunarEclipseOccurrenceDetails.status),
                () -> assertEquals(4, lunarEclipseOccurrenceDetails.eventDateDay),
                () -> assertEquals(4, lunarEclipseOccurrenceDetails.eventDateMonth),
                () -> assertEquals(2015, lunarEclipseOccurrenceDetails.eventDateYear));
    }

    @Test
    void testLunarEclipseCircumstances() {
        LunarEclipseCircumstances lunarEclipseCircumstances = testEclipse.lunarEclipseCircumstances(1, 4, 2015, false,
                10);

        assertAll("Lunar Eclipse Circumstances",
                () -> assertEquals(4, lunarEclipseCircumstances.lunarEclipseCertainDateDay),
                () -> assertEquals(4, lunarEclipseCircumstances.lunarEclipseCertainDateMonth),
                () -> assertEquals(2015, lunarEclipseCircumstances.lunarEclipseCertainDateYear),
                () -> assertEquals(9, lunarEclipseCircumstances.utStartPenPhaseHour),
                () -> assertEquals(0, lunarEclipseCircumstances.utStartPenPhaseMinutes),
                () -> assertEquals(10, lunarEclipseCircumstances.utStartUmbralPhaseHour),
                () -> assertEquals(16, lunarEclipseCircumstances.utStartUmbralPhaseMinutes),
                () -> assertEquals(11, lunarEclipseCircumstances.utStartTotalPhaseHour),
                () -> assertEquals(55, lunarEclipseCircumstances.utStartTotalPhaseMinutes),
                () -> assertEquals(12, lunarEclipseCircumstances.utMidEclipseHour),
                () -> assertEquals(1, lunarEclipseCircumstances.utMidEclipseMinutes),
                () -> assertEquals(12, lunarEclipseCircumstances.utEndTotalPhaseHour),
                () -> assertEquals(7, lunarEclipseCircumstances.utEndTotalPhaseMinutes),
                () -> assertEquals(13, lunarEclipseCircumstances.utEndUmbralPhaseHour),
                () -> assertEquals(46, lunarEclipseCircumstances.utEndUmbralPhaseMinutes),
                () -> assertEquals(15, lunarEclipseCircumstances.utEndPenPhaseHour),
                () -> assertEquals(1, lunarEclipseCircumstances.utEndPenPhaseMinutes),
                () -> assertEquals(1.01, lunarEclipseCircumstances.eclipseMagnitude));
    }

    @Test
    void testSolarEclipseOccurrence() {
        SolarEclipseOccurrence solarEclipseOccurrence = testEclipse.solarEclipseOccurrence(1, 4, 2015, false, 0);

        assertAll("Solar Eclipse Occurrence",
                () -> assertEquals(EclipseOccurrence.ECLIPSE_CERTAIN, solarEclipseOccurrence.status),
                () -> assertEquals(20, solarEclipseOccurrence.eventDateDay),
                () -> assertEquals(3, solarEclipseOccurrence.eventDateMonth),
                () -> assertEquals(2015, solarEclipseOccurrence.eventDateYear));
    }

    @Test
    void testSolarEclipseCircumstances() {
        SolarEclipseCircumstances solarEclipseCircumstances = testEclipse.solarEclipseCircumstances(20, 3, 2015, false,
                0, 0, 68.65);

        assertAll("Solar Eclipse Circumstances",
                () -> assertEquals(20, solarEclipseCircumstances.solarEclipseCertainDateDay),
                () -> assertEquals(3, solarEclipseCircumstances.solarEclipseCertainDateMonth),
                () -> assertEquals(2015, solarEclipseCircumstances.solarEclipseCertainDateYear),
                () -> assertEquals(8, solarEclipseCircumstances.utFirstContactHour),
                () -> assertEquals(55, solarEclipseCircumstances.utFirstContactMinutes),
                () -> assertEquals(9, solarEclipseCircumstances.utMidEclipseHour),
                () -> assertEquals(57, solarEclipseCircumstances.utMidEclipseMinutes),
                () -> assertEquals(10, solarEclipseCircumstances.utLastContactHour),
                () -> assertEquals(58, solarEclipseCircumstances.utLastContactMinutes),
                () -> assertEquals(1.016, solarEclipseCircumstances.eclipseMagnitude));
    }
}
