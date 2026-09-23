package astro.practical.lib;

import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.models.MoonDistAngDiamHorParallax;
import astro.practical.models.MoonLcDMY;
import astro.practical.models.MoonLongLatHP;
import astro.practical.models.MoonPhase;
import astro.practical.models.MoonriseAndMoonset;
import astro.practical.models.PrecisePositionOfMoon;
import astro.practical.models.TimesOfNewMoonAndFullMoon;
import astro.practical.types.AccuracyLevel;

public class Moon {
    /** Calculate approximate position of the Moon. */
    public ApproximatePositionOfMoon approximatePositionOfMoon(double lctHour, double lctMin, double lctSec,
            boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
            int localDateYear) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double l0 = 91.9293359879052;
        double p0 = 130.143076320618;
        double n0 = 291.682546643194;
        double i = 5.145396;

        double gdateDay = Macros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateMonth = Macros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateYear = Macros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        double utHours = Macros.localCivilTimeToUniversalTime(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        double dDays = Macros.civilDateToJulianDate(gdateDay, gdateMonth, gdateYear)
                - Macros.civilDateToJulianDate(0.0, 1, 2010) + utHours / 24;
        double sunLongDeg = Macros.sunLong(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                localDateDay,
                localDateMonth, localDateYear);
        double sunMeanAnomalyRad = Macros.sunMeanAnomaly(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours,
                localDateDay, localDateMonth, localDateYear);
        double lmDeg = Macros.unwindDeg(13.1763966 * dDays + l0);
        double mmDeg = Macros.unwindDeg(lmDeg - 0.1114041 * dDays - p0);
        double nDeg = Macros.unwindDeg(n0 - (0.0529539 * dDays));
        double evDeg = 1.2739 * Math.sin(Math.toRadians(2.0 * (lmDeg - sunLongDeg) - mmDeg));
        double aeDeg = 0.1858 * Math.sin(sunMeanAnomalyRad);
        double a3Deg = 0.37 * Math.sin(sunMeanAnomalyRad);
        double mmdDeg = mmDeg + evDeg - aeDeg - a3Deg;
        double ecDeg = 6.2886 * Math.sin(Math.toRadians(mmdDeg));
        double a4Deg = 0.214 * Math.sin(2.0 * Math.toRadians(mmdDeg));
        double ldDeg = lmDeg + evDeg + ecDeg - aeDeg + a4Deg;
        double vDeg = 0.6583 * Math.sin(2.0 * Math.toRadians(ldDeg - sunLongDeg));
        double lddDeg = ldDeg + vDeg;
        double ndDeg = nDeg - 0.16 * Math.sin(sunMeanAnomalyRad);
        double y = Math.sin(Math.toRadians(lddDeg - ndDeg)) * Math.cos(Math.toRadians(i));
        double x = Math.cos(Math.toRadians(lddDeg - ndDeg));

        double moonLongDeg = Macros.unwindDeg(Macros.wToDegrees(Math.atan2(y, x)) + ndDeg);
        double moonLatDeg = Macros
                .wToDegrees(Math.asin(Math.sin(Math.toRadians(lddDeg - ndDeg))
                        * Math.sin(Math.toRadians(i))));
        double moonRAHours1 = Macros.decimalDegreesToDegreeHours(
                Macros.ecRA(moonLongDeg, 0, 0, moonLatDeg, 0, 0, gdateDay, gdateMonth, gdateYear));
        double moonDecDeg1 = Macros.ecDec(moonLongDeg, 0, 0, moonLatDeg, 0, 0, gdateDay, gdateMonth,
                gdateYear);

        int moonRAHour = Macros.decimalHoursHour(moonRAHours1);
        int moonRAMin = Macros.decimalHoursMinute(moonRAHours1);
        double moonRASec = Macros.decimalHoursSecond(moonRAHours1);
        double moonDecDeg = Macros.decimalDegreesDegrees(moonDecDeg1);
        double moonDecMin = Macros.decimalDegreesMinutes(moonDecDeg1);
        double moonDecSec = Macros.decimalDegreesSeconds(moonDecDeg1);

        return new ApproximatePositionOfMoon(moonRAHour, moonRAMin, moonRASec, moonDecDeg, moonDecMin,
                moonDecSec);
    }

    /** Calculate precise position of the Moon. */
    public PrecisePositionOfMoon precisePositionOfMoon(double lctHour, double lctMin, double lctSec,
            boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
            int localDateYear) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double gdateDay = Macros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateMonth = Macros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateYear = Macros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        MoonLongLatHP moonResult = Macros.moonLongLatHP(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        double nutationInLongitudeDeg = Macros.nutatLong(gdateDay, gdateMonth, gdateYear);
        double correctedLongDeg = moonResult.moonLongDeg + nutationInLongitudeDeg;
        double earthMoonDistanceKM = 6378.14 / Math.sin(Math.toRadians(moonResult.moonHorPara));
        double moonRAHours1 = Macros.decimalDegreesToDegreeHours(Macros.ecRA(correctedLongDeg, 0, 0,
                moonResult.moonLatDeg, 0, 0, gdateDay, gdateMonth, gdateYear));
        double moonDecDeg1 = Macros.ecDec(correctedLongDeg, 0, 0, moonResult.moonLatDeg, 0, 0, gdateDay,
                gdateMonth, gdateYear);

        int moonRAHour = Macros.decimalHoursHour(moonRAHours1);
        int moonRAMin = Macros.decimalHoursMinute(moonRAHours1);
        double moonRASec = Macros.decimalHoursSecond(moonRAHours1);
        double moonDecDeg = Macros.decimalDegreesDegrees(moonDecDeg1);
        double moonDecMin = Macros.decimalDegreesMinutes(moonDecDeg1);
        double moonDecSec = Macros.decimalDegreesSeconds(moonDecDeg1);
        double earthMoonDistKM = Util.round(earthMoonDistanceKM, 0);
        double moonHorParallaxDeg = Util.round(moonResult.moonHorPara, 6);

        return new PrecisePositionOfMoon(moonRAHour, moonRAMin, moonRASec, moonDecDeg, moonDecMin, moonDecSec,
                earthMoonDistKM, moonHorParallaxDeg);
    }

    /** Calculate Moon phase and position angle of bright limb. */
    public MoonPhase moonPhase(double lctHour, double lctMin, double lctSec, boolean isDaylightSaving,
            int zoneCorrectionHours, double localDateDay, int localDateMonth, int localDateYear,
            AccuracyLevel accuracyLevel) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double gdateDay = Macros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateMonth = Macros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateYear = Macros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        double sunLongDeg = Macros.sunLong(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                localDateDay, localDateMonth, localDateYear);
        MoonLongLatHP moonResult = Macros.moonLongLatHP(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        double dRad = Math.toRadians(moonResult.moonLongDeg - sunLongDeg);

        double moonPhase1 = (accuracyLevel == AccuracyLevel.PRECISE)
                ? Macros.moonPhase(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                        localDateDay, localDateMonth, localDateYear)
                : (1.0 - Math.cos(dRad)) / 2.0;

        double sunRARad = Math
                .toRadians(Macros.ecRA(sunLongDeg, 0, 0, 0, 0, 0, gdateDay, gdateMonth, gdateYear));
        double moonRARad = Math.toRadians(Macros.ecRA(moonResult.moonLongDeg, 0, 0, moonResult.moonLatDeg, 0,
                0, gdateDay, gdateMonth, gdateYear));
        double sunDecRad = Math
                .toRadians(Macros.ecDec(sunLongDeg, 0, 0, 0, 0, 0, gdateDay, gdateMonth, gdateYear));
        double moonDecRad = Math.toRadians(
                Macros.ecDec(moonResult.moonLongDeg, 0, 0, moonResult.moonLatDeg, 0, 0, gdateDay,
                        gdateMonth, gdateYear));

        double y = Math.cos(sunDecRad) * Math.sin(sunRARad - moonRARad);
        double x = Math.cos(moonDecRad) * Math.sin(sunDecRad)
                - Math.sin(moonDecRad) * Math.cos(sunDecRad) * Math.cos(sunRARad - moonRARad);

        double chiDeg = Macros.wToDegrees(Math.atan2(y, x));

        double moonPhase = Util.round(moonPhase1, 2);
        double paBrightLimbDeg = Util.round(chiDeg, 2);

        return new MoonPhase(moonPhase, paBrightLimbDeg);
    }

    /** Calculate new moon and full moon instances. */
    public TimesOfNewMoonAndFullMoon timesOfNewMoonAndFullMoon(boolean isDaylightSaving, int zoneCorrectionHours,
            double localDateDay, int localDateMonth, int localDateYear) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double jdOfNewMoonDays = Macros.newMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                localDateMonth, localDateYear);
        double jdOfFullMoonDays = Macros.fullMoon(3, zoneCorrectionHours, localDateDay, localDateMonth,
                localDateYear);

        double gDateOfNewMoonDay = Macros.julianDateDay(jdOfNewMoonDays);
        double integerDay1 = Math.floor(gDateOfNewMoonDay);
        int gDateOfNewMoonMonth = Macros.julianDateMonth(jdOfNewMoonDays);
        int gDateOfNewMoonYear = Macros.julianDateYear(jdOfNewMoonDays);

        double gDateOfFullMoonDay = Macros.julianDateDay(jdOfFullMoonDays);
        double integerDay2 = Math.floor(gDateOfFullMoonDay);
        int gDateOfFullMoonMonth = Macros.julianDateMonth(jdOfFullMoonDays);
        int gDateOfFullMoonYear = Macros.julianDateYear(jdOfFullMoonDays);

        double utOfNewMoonHours = 24.0 * (gDateOfNewMoonDay - integerDay1);
        double utOfFullMoonHours = 24.0 * (gDateOfFullMoonDay - integerDay2);
        double lctOfNewMoonHours = Macros.universalTimeToLocalCivilTime(utOfNewMoonHours + 0.008333, 0, 0,
                daylightSaving, zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth,
                gDateOfNewMoonYear);
        double lctOfFullMoonHours = Macros.universalTimeToLocalCivilTime(utOfFullMoonHours + 0.008333, 0, 0,
                daylightSaving, zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth,
                gDateOfFullMoonYear);

        int nmLocalTimeHour = Macros.decimalHoursHour(lctOfNewMoonHours);
        int nmLocalTimeMin = Macros.decimalHoursMinute(lctOfNewMoonHours);
        double nmLocalDateDay = Macros.universalTimeLocalCivilDay(utOfNewMoonHours, 0, 0, daylightSaving,
                zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth, gDateOfNewMoonYear);
        int nmLocalDateMonth = Macros.universalTimeLocalCivilMonth(utOfNewMoonHours, 0, 0, daylightSaving,
                zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth, gDateOfNewMoonYear);
        int nmLocalDateYear = Macros.universalTimeLocalCivilYear(utOfNewMoonHours, 0, 0, daylightSaving,
                zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth, gDateOfNewMoonYear);
        int fmLocalTimeHour = Macros.decimalHoursHour(lctOfFullMoonHours);
        int fmLocalTimeMin = Macros.decimalHoursMinute(lctOfFullMoonHours);
        double fmLocalDateDay = Macros.universalTimeLocalCivilDay(utOfFullMoonHours, 0, 0, daylightSaving,
                zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth, gDateOfFullMoonYear);
        int fmLocalDateMonth = Macros.universalTimeLocalCivilMonth(utOfFullMoonHours, 0, 0, daylightSaving,
                zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth, gDateOfFullMoonYear);
        int fmLocalDateYear = Macros.universalTimeLocalCivilYear(utOfFullMoonHours, 0, 0, daylightSaving,
                zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth, gDateOfFullMoonYear);

        return new TimesOfNewMoonAndFullMoon(nmLocalTimeHour, nmLocalTimeMin, nmLocalDateDay, nmLocalDateMonth,
                nmLocalDateYear, fmLocalTimeHour, fmLocalTimeMin, fmLocalDateDay, fmLocalDateMonth,
                fmLocalDateYear);
    }

    /** Calculate Moon's distance, angular diameter, and horizontal parallax. */
    public MoonDistAngDiamHorParallax moonDistAngDiamHorParallax(double lctHour, double lctMin, double lctSec,
            boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
            int localDateYear) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double moonDistance = Macros.moonDist(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                localDateDay, localDateMonth, localDateYear);
        double moonAngularDiameter = Macros.moonSize(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        double moonHorizontalParallax = Macros.moonHP(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        double earthMoonDist = Util.round(moonDistance, 0);
        double angDiameterDeg = Macros.decimalDegreesDegrees(moonAngularDiameter + 0.008333);
        double angDiameterMin = Macros.decimalDegreesMinutes(moonAngularDiameter + 0.008333);
        double horParallaxDeg = Macros.decimalDegreesDegrees(moonHorizontalParallax);
        double horParallaxMin = Macros.decimalDegreesMinutes(moonHorizontalParallax);
        double horParallaxSec = Macros.decimalDegreesSeconds(moonHorizontalParallax);

        return new MoonDistAngDiamHorParallax(earthMoonDist, angDiameterDeg, angDiameterMin, horParallaxDeg,
                horParallaxMin, horParallaxSec);
    }

    /**
     * Calculate date/time of local moonrise and moonset.
     */
    public MoonriseAndMoonset moonriseAndMoonset(double localDateDay, int localDateMonth, int localDateYear,
            boolean isDaylightSaving, int zoneCorrectionHours, double geogLongDeg, double geogLatDeg) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double localTimeOfMoonriseHours = Macros.moonRiseLCT(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongDeg, geogLatDeg);
        MoonLcDMY moonRiseLCResult = Macros.moonRiseLcDMY(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongDeg, geogLatDeg);
        double localAzimuthDeg1 = Macros.moonRiseAz(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongDeg, geogLatDeg);

        double localTimeOfMoonsetHours = Macros.moonSetLCT(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongDeg, geogLatDeg);
        MoonLcDMY moonSetLCResult = Macros.moonSetLcDMY(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongDeg, geogLatDeg);
        double localAzimuthDeg2 = Macros.moonSetAz(localDateDay, localDateMonth, localDateYear,
                daylightSaving, zoneCorrectionHours, geogLongDeg, geogLatDeg);

        int mrLTHour = Macros.decimalHoursHour(localTimeOfMoonriseHours + 0.008333);
        int mrLTMin = Macros.decimalHoursMinute(localTimeOfMoonriseHours + 0.008333);
        double mrLocalDateDay = moonRiseLCResult.dy1;
        int mrLocalDateMonth = moonRiseLCResult.mn1;
        int mrLocalDateYear = moonRiseLCResult.yr1;
        double mrAzimuthDeg = Util.round(localAzimuthDeg1, 2);
        int msLTHour = Macros.decimalHoursHour(localTimeOfMoonsetHours + 0.008333);
        int msLTMin = Macros.decimalHoursMinute(localTimeOfMoonsetHours + 0.008333);
        double msLocalDateDay = moonSetLCResult.dy1;
        int msLocalDateMonth = moonSetLCResult.mn1;
        int msLocalDateYear = moonSetLCResult.yr1;
        double msAzimuthDeg = Util.round(localAzimuthDeg2, 2);

        return new MoonriseAndMoonset(mrLTHour, mrLTMin, mrLocalDateDay, mrLocalDateMonth, mrLocalDateYear,
                mrAzimuthDeg, msLTHour, msLTMin, msLocalDateDay, msLocalDateMonth, msLocalDateYear,
                msAzimuthDeg);
    }
}
