package astro.practical.lib;

import astro.practical.models.LunarEclipseCircumstances;
import astro.practical.models.LunarEclipseOccurrenceDetails;
import astro.practical.models.SolarEclipseCircumstances;
import astro.practical.models.SolarEclipseOccurrence;
import astro.practical.types.EclipseOccurrence;

public class Eclipses {
    /** Determine if a lunar eclipse is likely to occur. */
    public LunarEclipseOccurrenceDetails lunarEclipseOccurrenceDetails(double localDateDay, int localDateMonth,
            int localDateYear, boolean isDaylightSaving, int zoneCorrectionHours) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double julianDateOfFullMoon = Macros.fullMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                localDateMonth, localDateYear);

        double gDateOfFullMoonDay = Macros.julianDateDay(julianDateOfFullMoon);
        double integerDay = Math.floor(gDateOfFullMoonDay);
        int gDateOfFullMoonMonth = Macros.julianDateMonth(julianDateOfFullMoon);
        int gDateOfFullMoonYear = Macros.julianDateYear(julianDateOfFullMoon);
        double utOfFullMoonHours = gDateOfFullMoonDay - integerDay;

        double localCivilDateDay = Macros.universalTimeLocalCivilDay(utOfFullMoonHours, 0.0, 0.0,
                daylightSaving,
                zoneCorrectionHours, integerDay, gDateOfFullMoonMonth, gDateOfFullMoonYear);
        int localCivilDateMonth = Macros.universalTimeLocalCivilMonth(utOfFullMoonHours, 0.0, 0.0,
                daylightSaving,
                zoneCorrectionHours, integerDay, gDateOfFullMoonMonth, gDateOfFullMoonYear);
        int localCivilDateYear = Macros.universalTimeLocalCivilYear(utOfFullMoonHours, 0.0, 0.0,
                daylightSaving,
                zoneCorrectionHours, integerDay, gDateOfFullMoonMonth, gDateOfFullMoonYear);

        EclipseOccurrence eclipseOccurrence = Macros.lunarEclipseOccurrence(daylightSaving,
                zoneCorrectionHours,
                localDateDay, localDateMonth, localDateYear);

        EclipseOccurrence status = eclipseOccurrence;
        double eventDateDay = localCivilDateDay;
        int eventDateMonth = localCivilDateMonth;
        int eventDateYear = localCivilDateYear;

        return new LunarEclipseOccurrenceDetails(status, eventDateDay, eventDateMonth, eventDateYear);
    }

    /**
     * Calculate the circumstances of a lunar eclipse.
     */
    public LunarEclipseCircumstances lunarEclipseCircumstances(double localDateDay, int localDateMonth,
            int localDateYear, boolean isDaylightSaving, int zoneCorrectionHours) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double julianDateOfFullMoon = Macros.fullMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                localDateMonth, localDateYear);
        double gDateOfFullMoonDay = Macros.julianDateDay(julianDateOfFullMoon);
        double integerDay = Math.floor(gDateOfFullMoonDay);
        int gDateOfFullMoonMonth = Macros.julianDateMonth(julianDateOfFullMoon);
        int gDateOfFullMoonYear = Macros.julianDateYear(julianDateOfFullMoon);
        double utOfFullMoonHours = gDateOfFullMoonDay - integerDay;

        double localCivilDateDay = Macros.universalTimeLocalCivilDay(utOfFullMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfFullMoonMonth,
                gDateOfFullMoonYear);
        int localCivilDateMonth = Macros.universalTimeLocalCivilMonth(utOfFullMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfFullMoonMonth,
                gDateOfFullMoonYear);
        int localCivilDateYear = Macros.universalTimeLocalCivilYear(utOfFullMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfFullMoonMonth,
                gDateOfFullMoonYear);

        double utMaxEclipse = Macros.utMaxLunarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours);
        double utFirstContact = Macros.utFirstContactLunarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours);
        double utLastContact = Macros.utLastContactLunarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours);
        double utStartUmbralPhase = Macros.utStartUmbraLunarEclipse(localDateDay, localDateMonth,
                localDateYear, daylightSaving, zoneCorrectionHours);
        double utEndUmbralPhase = Macros.utEndUmbraLunarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours);
        double utStartTotalPhase = Macros.utStartTotalLunarEclipse(localDateDay, localDateMonth,
                localDateYear, daylightSaving, zoneCorrectionHours);
        double utEndTotalPhase = Macros.utEndTotalLunarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours);

        double eclipseMagnitude1 = Macros.magLunarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours);

        double lunarEclipseCertainDateDay = localCivilDateDay;
        int lunarEclipseCertainDateMonth = localCivilDateMonth;
        int lunarEclipseCertainDateYear = localCivilDateYear;

        double utStartPenPhaseHour = (utFirstContact == -99.0) ? -99.0
                : Macros.decimalHoursHour(utFirstContact + 0.008333);
        double utStartPenPhaseMinutes = (utFirstContact == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utFirstContact + 0.008333);

        double utStartUmbralPhaseHour = (utStartUmbralPhase == -99.0) ? -99.0
                : Macros.decimalHoursHour(utStartUmbralPhase + 0.008333);
        double utStartUmbralPhaseMinutes = (utStartUmbralPhase == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utStartUmbralPhase + 0.008333);

        double utStartTotalPhaseHour = (utStartTotalPhase == -99.0) ? -99.0
                : Macros.decimalHoursHour(utStartTotalPhase + 0.008333);
        double utStartTotalPhaseMinutes = (utStartTotalPhase == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utStartTotalPhase + 0.008333);

        double utMidEclipseHour = (utMaxEclipse == -99.0) ? -99.0
                : Macros.decimalHoursHour(utMaxEclipse + 0.008333);
        double utMidEclipseMinutes = (utMaxEclipse == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utMaxEclipse + 0.008333);

        double utEndTotalPhaseHour = (utEndTotalPhase == -99.0) ? -99.0
                : Macros.decimalHoursHour(utEndTotalPhase + 0.008333);
        double utEndTotalPhaseMinutes = (utEndTotalPhase == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utEndTotalPhase + 0.008333);

        double utEndUmbralPhaseHour = (utEndUmbralPhase == -99.0) ? -99.0
                : Macros.decimalHoursHour(utEndUmbralPhase + 0.008333);
        double utEndUmbralPhaseMinutes = (utEndUmbralPhase == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utEndUmbralPhase + 0.008333);

        double utEndPenPhaseHour = (utLastContact == -99.0) ? -99.0
                : Macros.decimalHoursHour(utLastContact + 0.008333);
        double utEndPenPhaseMinutes = (utLastContact == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utLastContact + 0.008333);

        double eclipseMagnitude = (eclipseMagnitude1 == -99.0) ? -99.0 : Util.round(eclipseMagnitude1, 2);

        return new LunarEclipseCircumstances(lunarEclipseCertainDateDay, lunarEclipseCertainDateMonth,
                lunarEclipseCertainDateYear, utStartPenPhaseHour, utStartPenPhaseMinutes,
                utStartUmbralPhaseHour, utStartUmbralPhaseMinutes, utStartTotalPhaseHour,
                utStartTotalPhaseMinutes, utMidEclipseHour, utMidEclipseMinutes, utEndTotalPhaseHour,
                utEndTotalPhaseMinutes, utEndUmbralPhaseHour, utEndUmbralPhaseMinutes,
                utEndPenPhaseHour, utEndPenPhaseMinutes, eclipseMagnitude);
    }

    /** Determine if a solar eclipse is likely to occur. */
    public SolarEclipseOccurrence solarEclipseOccurrence(double localDateDay, int localDateMonth, int localDateYear,
            boolean isDaylightSaving, int zoneCorrectionHours) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double julianDateOfNewMoon = Macros.newMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                localDateMonth, localDateYear);
        double gDateOfNewMoonDay = Macros.julianDateDay(julianDateOfNewMoon);
        double integerDay = Math.floor(gDateOfNewMoonDay);
        int gDateOfNewMoonMonth = Macros.julianDateMonth(julianDateOfNewMoon);
        int gDateOfNewMoonYear = Macros.julianDateYear(julianDateOfNewMoon);
        double utOfNewMoonHours = gDateOfNewMoonDay - integerDay;

        double localCivilDateDay = Macros.universalTimeLocalCivilDay(utOfNewMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);
        int localCivilDateMonth = Macros.universalTimeLocalCivilMonth(utOfNewMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);
        int localCivilDateYear = Macros.universalTimeLocalCivilYear(utOfNewMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);

        EclipseOccurrence eclipseOccurrence = Macros.solarEclipseOccurrence(daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        EclipseOccurrence status = eclipseOccurrence;
        double eventDateDay = localCivilDateDay;
        int eventDateMonth = localCivilDateMonth;
        int eventDateYear = localCivilDateYear;

        return new SolarEclipseOccurrence(status, eventDateDay, eventDateMonth, eventDateYear);
    }

    /** Calculate the circumstances of a solar eclipse. */
    public SolarEclipseCircumstances solarEclipseCircumstances(double localDateDay, int localDateMonth,
            int localDateYear, boolean isDaylightSaving, int zoneCorrectionHours, double geogLongitudeDeg,
            double geogLatitudeDeg) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double julianDateOfNewMoon = Macros.newMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                localDateMonth, localDateYear);
        double gDateOfNewMoonDay = Macros.julianDateDay(julianDateOfNewMoon);
        double integerDay = Math.floor(gDateOfNewMoonDay);
        int gDateOfNewMoonMonth = Macros.julianDateMonth(julianDateOfNewMoon);
        int gDateOfNewMoonYear = Macros.julianDateYear(julianDateOfNewMoon);
        double utOfNewMoonHours = gDateOfNewMoonDay - integerDay;
        double localCivilDateDay = Macros.universalTimeLocalCivilDay(utOfNewMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);
        int localCivilDateMonth = Macros.universalTimeLocalCivilMonth(utOfNewMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);
        int localCivilDateYear = Macros.universalTimeLocalCivilYear(utOfNewMoonHours, 0.0, 0.0,
                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);

        double utMaxEclipse = Macros.utMaxSolarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);
        double utFirstContact = Macros.utFirstContactSolarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);
        double utLastContact = Macros.utLastContactSolarEclipse(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);
        double magnitude = Macros.magSolarEclipse(localDateDay, localDateMonth, localDateYear, daylightSaving,
                zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);

        double solarEclipseCertainDateDay = localCivilDateDay;
        int solarEclipseCertainDateMonth = localCivilDateMonth;
        int solarEclipseCertainDateYear = localCivilDateYear;

        double utFirstContactHour = (utFirstContact == -99.0) ? -99.0
                : Macros.decimalHoursHour(utFirstContact + 0.008333);
        double utFirstContactMinutes = (utFirstContact == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utFirstContact + 0.008333);

        double utMidEclipseHour = (utMaxEclipse == -99.0) ? -99.0
                : Macros.decimalHoursHour(utMaxEclipse + 0.008333);
        double utMidEclipseMinutes = (utMaxEclipse == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utMaxEclipse + 0.008333);

        double utLastContactHour = (utLastContact == -99.0) ? -99.0
                : Macros.decimalHoursHour(utLastContact + 0.008333);
        double utLastContactMinutes = (utLastContact == -99.0) ? -99.0
                : Macros.decimalHoursMinute(utLastContact + 0.008333);

        double eclipseMagnitude = (magnitude == -99.0) ? -99.0 : Util.round(magnitude, 3);

        return new SolarEclipseCircumstances(solarEclipseCertainDateDay, solarEclipseCertainDateMonth,
                solarEclipseCertainDateYear, utFirstContactHour, utFirstContactMinutes,
                utMidEclipseHour, utMidEclipseMinutes, utLastContactHour, utLastContactMinutes,
                eclipseMagnitude);
    }
}
