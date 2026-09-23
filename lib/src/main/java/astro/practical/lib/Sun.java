package astro.practical.lib;

import astro.practical.models.EquationOfTime;
import astro.practical.models.MorningAndEveningTwilight;
import astro.practical.models.PositionOfSun;
import astro.practical.models.SunDistanceAndAngularSize;
import astro.practical.models.SunriseAndSunset;
import astro.practical.types.AngleMeasure;
import astro.practical.types.RiseSetStatus;
import astro.practical.types.TwilightStatus;
import astro.practical.types.TwilightType;

public class Sun {
    /**
     * Calculate approximate position of the sun for a local date and time.
     */
    public PositionOfSun approximatePositionOfSun(double lctHours, double lctMinutes, double lctSeconds,
            double localDay, int localMonth, int localYear, boolean isDaylightSaving, int zoneCorrection) {
        int daylightSaving = (isDaylightSaving == true) ? 1 : 0;

        double greenwichDateDay = Macros.localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        int greenwichDateMonth = Macros.localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        int greenwichDateYear = Macros.localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        double utHours = Macros.localCivilTimeToUniversalTime(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        double utDays = utHours / 24;
        double jdDays = Macros.civilDateToJulianDate(greenwichDateDay, greenwichDateMonth, greenwichDateYear) + utDays;
        double dDays = jdDays - Macros.civilDateToJulianDate(0, 1, 2010);
        double nDeg = 360 * dDays / 365.242191;
        double mDeg1 = nDeg + Macros.sunELong(0, 1, 2010) - Macros.sunPeri(0, 1, 2010);
        double mDeg2 = mDeg1 - 360 * Math.floor(mDeg1 / 360);
        double eCDeg = 360 * Macros.sunEcc(0, 1, 2010) * Math.sin(Math.toRadians(mDeg2)) / Math.PI;
        double lSDeg1 = nDeg + eCDeg + Macros.sunELong(0, 1, 2010);
        double lSDeg2 = lSDeg1 - 360 * Math.floor(lSDeg1 / 360);
        double raDeg = Macros.ecRA(lSDeg2, 0, 0, 0, 0, 0, greenwichDateDay, greenwichDateMonth, greenwichDateYear);
        double raHours = Macros.decimalDegreesToDegreeHours(raDeg);
        double decDeg = Macros.ecDec(lSDeg2, 0, 0, 0, 0, 0, greenwichDateDay, greenwichDateMonth, greenwichDateYear);

        int sunRAHour = Macros.decimalHoursHour(raHours);
        int sunRAMin = Macros.decimalHoursMinute(raHours);
        double sunRASec = Macros.decimalHoursSecond(raHours);
        double sunDecDeg = Macros.decimalDegreesDegrees(decDeg);
        double sunDecMin = Macros.decimalDegreesMinutes(decDeg);
        double sunDecSec = Macros.decimalDegreesSeconds(decDeg);

        return new PositionOfSun(sunRAHour, sunRAMin, sunRASec, sunDecDeg, sunDecMin, sunDecSec);
    }

    /// <summary>
    /// Calculate precise position of the sun for a local date and time.
    /// </summary>
    public PositionOfSun precisePositionOfSun(double lctHours, double lctMinutes, double lctSeconds,
            double localDay, int localMonth, int localYear, boolean isDaylightSaving, int zoneCorrection) {
        int daylightSaving = (isDaylightSaving == true) ? 1 : 0;

        double gDay = Macros.localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        int gMonth = Macros.localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        int gYear = Macros.localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
                localDay, localMonth, localYear);
        double sunEclipticLongitudeDeg = Macros.sunLong(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        double raDeg = Macros.ecRA(sunEclipticLongitudeDeg, 0, 0, 0, 0, 0, gDay, gMonth, gYear);
        double raHours = Macros.decimalDegreesToDegreeHours(raDeg);
        double decDeg = Macros.ecDec(sunEclipticLongitudeDeg, 0, 0, 0, 0, 0, gDay, gMonth, gYear);

        int sunRAHour = Macros.decimalHoursHour(raHours);
        int sunRAMin = Macros.decimalHoursMinute(raHours);
        double sunRASec = Macros.decimalHoursSecond(raHours);
        double sunDecDeg = Macros.decimalDegreesDegrees(decDeg);
        double sunDecMin = Macros.decimalDegreesMinutes(decDeg);
        double sunDecSec = Macros.decimalDegreesSeconds(decDeg);

        return new PositionOfSun(sunRAHour, sunRAMin, sunRASec, sunDecDeg, sunDecMin, sunDecSec);
    }

    /**
     * Calculate distance to the Sun (in km), and angular size.
     */
    public SunDistanceAndAngularSize sunDistanceAndAngularSize(double lctHours, double lctMinutes, double lctSeconds,
            double localDay, int localMonth, int localYear, boolean isDaylightSaving, int zoneCorrection) {
        int daylightSaving = isDaylightSaving ? 1 : 0;
        double gDay = Macros.localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        int gMonth = Macros.localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving,
                zoneCorrection, localDay, localMonth, localYear);
        int gYear = Macros.localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
                localDay, localMonth, localYear);
        double trueAnomalyDeg = Macros.sunTrueAnomaly(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
                localDay, localMonth, localYear);
        double trueAnomalyRad = Math.toRadians(trueAnomalyDeg);
        double eccentricity = Macros.sunEcc(gDay, gMonth, gYear);
        double f = (1 + eccentricity * Math.cos(trueAnomalyRad)) / (1 - eccentricity * eccentricity);
        double rKm = 149598500 / f;
        double thetaDeg = f * 0.533128;

        double sunDistKm = Util.round(rKm, 0);
        double sunAngSizeDeg = Macros.decimalDegreesDegrees(thetaDeg);
        double sunAngSizeMin = Macros.decimalDegreesMinutes(thetaDeg);
        double sunAngSizeSec = Macros.decimalDegreesSeconds(thetaDeg);

        return new SunDistanceAndAngularSize(sunDistKm, sunAngSizeDeg, sunAngSizeMin, sunAngSizeSec);
    }

    /**
     * Calculate local sunrise and sunset.
     */
    public SunriseAndSunset sunriseAndSunset(double localDay, int localMonth, int localYear, boolean isDaylightSaving,
            int zoneCorrection, double geographicalLongDeg, double geographicalLatDeg) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double localSunriseHours = Macros.sunriseLCT(localDay, localMonth, localYear, daylightSaving, zoneCorrection,
                geographicalLongDeg, geographicalLatDeg);
        double localSunsetHours = Macros.sunsetLCT(localDay, localMonth, localYear, daylightSaving, zoneCorrection,
                geographicalLongDeg, geographicalLatDeg);

        RiseSetStatus sunRiseSetStatus = Macros.eSunRS(localDay, localMonth, localYear, daylightSaving, zoneCorrection,
                geographicalLongDeg, geographicalLatDeg);

        double adjustedSunriseHours = localSunriseHours + 0.008333;
        double adjustedSunsetHours = localSunsetHours + 0.008333;

        double azimuthOfSunriseDeg1 = Macros.sunriseAZ(localDay, localMonth, localYear, daylightSaving, zoneCorrection,
                geographicalLongDeg, geographicalLatDeg);
        double azimuthOfSunsetDeg1 = Macros.sunsetAZ(localDay, localMonth, localYear, daylightSaving, zoneCorrection,
                geographicalLongDeg, geographicalLatDeg);

        int localSunriseHour = sunRiseSetStatus == RiseSetStatus.OK ? Macros.decimalHoursHour(adjustedSunriseHours) : 0;
        int localSunriseMinute = sunRiseSetStatus == RiseSetStatus.OK ? Macros.decimalHoursMinute(adjustedSunriseHours)
                : 0;

        int localSunsetHour = sunRiseSetStatus == RiseSetStatus.OK ? Macros.decimalHoursHour(adjustedSunsetHours) : 0;
        int localSunsetMinute = sunRiseSetStatus == RiseSetStatus.OK ? Macros.decimalHoursMinute(adjustedSunsetHours)
                : 0;

        double azimuthOfSunriseDeg = sunRiseSetStatus == RiseSetStatus.OK ? Util.round(azimuthOfSunriseDeg1, 2) : 0;
        double azimuthOfSunsetDeg = sunRiseSetStatus == RiseSetStatus.OK ? Util.round(azimuthOfSunsetDeg1, 2) : 0;

        RiseSetStatus status = sunRiseSetStatus;

        return new SunriseAndSunset(localSunriseHour, localSunriseMinute, localSunsetHour, localSunsetMinute,
                azimuthOfSunriseDeg, azimuthOfSunsetDeg, status);
    }

    /**
     * Calculate times of morning and evening twilight.
     */
    public MorningAndEveningTwilight morningAndEveningTwilight(double localDay, int localMonth, int localYear,
            boolean isDaylightSaving, int zoneCorrection, double geographicalLongDeg, double geographicalLatDeg,
            TwilightType twilightType) {
        int daylightSaving = isDaylightSaving ? 1 : 0;

        double startOfAMTwilightHours = Macros.twilightAMLCT(localDay, localMonth, localYear, daylightSaving,
                zoneCorrection, geographicalLongDeg, geographicalLatDeg, twilightType);

        double endOfPMTwilightHours = Macros.twilightPMLCT(localDay, localMonth, localYear, daylightSaving,
                zoneCorrection, geographicalLongDeg, geographicalLatDeg, twilightType);

        TwilightStatus twilightStatus = Macros.eTwilight(localDay, localMonth, localYear, daylightSaving,
                zoneCorrection, geographicalLongDeg, geographicalLatDeg, twilightType);

        double adjustedAMStartTime = startOfAMTwilightHours + 0.008333;
        double adjustedPMStartTime = endOfPMTwilightHours + 0.008333;

        double amTwilightBeginsHour = twilightStatus == TwilightStatus.OK ? Macros.decimalHoursHour(adjustedAMStartTime)
                : -99;
        double amTwilightBeginsMin = twilightStatus == TwilightStatus.OK
                ? Macros.decimalHoursMinute(adjustedAMStartTime)
                : -99;

        double pmTwilightEndsHour = twilightStatus == TwilightStatus.OK ? Macros.decimalHoursHour(adjustedPMStartTime)
                : -99;
        double pmTwilightEndsMin = twilightStatus == TwilightStatus.OK ? Macros.decimalHoursMinute(adjustedPMStartTime)
                : -99;

        TwilightStatus status = twilightStatus;

        return new MorningAndEveningTwilight(amTwilightBeginsHour, amTwilightBeginsMin, pmTwilightEndsHour,
                pmTwilightEndsMin, status);
    }

    /**
     * Calculate the equation of time. (The difference between the real Sun time and
     * the mean Sun time.)
     */
    public EquationOfTime equationOfTime(double gwdateDay, int gwdateMonth, int gwdateYear) {
        double sunLongitudeDeg = Macros.sunLong(12, 0, 0, 0, 0, gwdateDay, gwdateMonth, gwdateYear);
        double sunRAHours = Macros.decimalDegreesToDegreeHours(
                Macros.ecRA(sunLongitudeDeg, 0, 0, 0, 0, 0, gwdateDay, gwdateMonth, gwdateYear));
        double equivalentUTHours = Macros.greenwichSiderealTimeToUniversalTime(sunRAHours, 0, 0, gwdateDay, gwdateMonth,
                gwdateYear);
        double equationOfTimeHours = equivalentUTHours - 12;

        int equationOfTimeMin = Macros.decimalHoursMinute(equationOfTimeHours);
        double equationOfTimeSec = Macros.decimalHoursSecond(equationOfTimeHours);

        return new EquationOfTime(equationOfTimeMin, equationOfTimeSec);
    }

    /**
     * Calculate solar elongation for a celestial body.
     */
    public double solarElongation(double raHour, double raMin, double raSec, double decDeg, double decMin,
            double decSec, double gwdateDay, int gwdateMonth, int gwdateYear) {
        double sunLongitudeDeg = Macros.sunLong(0, 0, 0, 0, 0, gwdateDay, gwdateMonth, gwdateYear);
        double sunRAHours = Macros.decimalDegreesToDegreeHours(
                Macros.ecRA(sunLongitudeDeg, 0, 0, 0, 0, 0, gwdateDay, gwdateMonth, gwdateYear));
        double sunDecDeg = Macros.ecDec(sunLongitudeDeg, 0, 0, 0, 0, 0, gwdateDay, gwdateMonth, gwdateYear);
        double solarElongationDeg = Macros.angle(sunRAHours, 0, 0, sunDecDeg, 0, 0, raHour, raMin, raSec, decDeg,
                decMin, decSec, AngleMeasure.HOURS);

        return Util.round(solarElongationDeg, 2);
    }
}
