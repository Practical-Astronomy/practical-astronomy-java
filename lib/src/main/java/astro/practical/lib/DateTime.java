package astro.practical.lib;

import astro.practical.models.CivilDate;
import astro.practical.models.CivilDateTime;
import astro.practical.models.CivilTime;
import astro.practical.models.GreenwichSiderealTime;
import astro.practical.models.LocalSiderealTime;
import astro.practical.models.UniversalDateTime;
import astro.practical.models.UniversalTime;
import astro.practical.types.WarningFlag;

public class DateTime {
    /**
     * Gets the date of Easter for the year specified.
     */
    public CivilDate getDateOfEaster(int inputYear) {
        double year = inputYear;

        double a = year % 19;
        double b = Math.floor(year / 100);
        double c = year % 100;
        double d = Math.floor(b / 4);
        double e = b % 4;
        double f = Math.floor((b + 8) / 25);
        double g = Math.floor((b - f + 1) / 3);
        double h = ((19 * a) + b - d - g + 15) % 30;
        double i = Math.floor(c / 4);
        double k = c % 4;
        double l = (32 + 2 * (e + i) - h - k) % 7;
        double m = Math.floor((a + (11 * h) + (22 * l)) / 451);
        double n = Math.floor((h + l - (7 * m) + 114) / 31);
        double p = (h + l - (7 * m) + 114) % 31;

        double day = p + 1;
        double month = n;

        return new CivilDate((int) month, day, (int) year);
    }

    /**
     * Calculate day number for a date.
     */
    public int civilDateToDayNumber(int month, int day, int year) {
        if (month <= 2) {
            month = month - 1;
            month = (Util.isLeapYear(year)) ? month * 62 : month * 63;
            month = (int) Math.floor((double) month / 2);
        } else {
            month = (int) Math.floor(((double) month + 1) * 30.6);
            month = (Util.isLeapYear(year)) ? month - 62 : month - 63;
        }

        return month + day;
    }

    /**
     * Convert Civil Time (hours,minutes,seconds) to Decimal Hours
     */
    public double civilTimeToDecimalHours(double hours, double minutes, double seconds) {
        return Macros.hmsToDH(hours, minutes, seconds);
    }

    /**
     * Convert Decimal Hours to Civil Time (hours,minutes,seconds)
     */
    public CivilTime decimalHoursToCivilTime(double decimalHours) {
        double hours = Macros.decimalHoursHour(decimalHours);
        double minutes = Macros.decimalHoursMinute(decimalHours);
        double seconds = Macros.decimalHoursSecond(decimalHours);

        return new CivilTime(hours, minutes, seconds);
    }

    /**
     * Convert local Civil Time to Universal Time
     */
    public UniversalDateTime localCivilTimeToUniversalTime(double lctHours, double lctMinutes, double lctSeconds,
            boolean isDaylightSavings, int zoneCorrection, double localDay, int localMonth, int localYear) {
        double lct = this.civilTimeToDecimalHours(lctHours, lctMinutes, lctSeconds);

        int daylightSavingsOffset = (isDaylightSavings) ? 1 : 0;

        double utInterim = lct - daylightSavingsOffset - zoneCorrection;
        double gdayInterim = localDay + (utInterim / 24);

        double jd = Macros.civilDateToJulianDate(gdayInterim, localMonth, localYear);

        double gDay = Macros.julianDateDay(jd);
        int gMonth = Macros.julianDateMonth(jd);
        int gYear = Macros.julianDateYear(jd);

        double ut = 24 * (gDay - Math.floor(gDay));

        return new UniversalDateTime(Macros.decimalHoursHour(ut), Macros.decimalHoursMinute(ut),
                (int) Macros.decimalHoursSecond(ut), (int) Math.floor(gDay), gMonth, gYear);
    }

    /**
     * Convert Universal Time to local Civil Time
     */
    public CivilDateTime universalTimeToLocalCivilTime(double utHours, double utMinutes, double utSeconds,
            boolean isDaylightSavings, int zoneCorrection, int gwDay, int gwMonth, int gwYear) {
        int dstValue = (isDaylightSavings) ? 1 : 0;
        double ut = Macros.hmsToDH(utHours, utMinutes, utSeconds);
        double zoneTime = ut + zoneCorrection;
        double localTime = zoneTime + dstValue;
        double localJDPlusLocalTime = Macros.civilDateToJulianDate(gwDay, gwMonth, gwYear) + (localTime / 24);
        double localDay = Macros.julianDateDay(localJDPlusLocalTime);
        double integerDay = Math.floor(localDay);
        int localMonth = Macros.julianDateMonth(localJDPlusLocalTime);
        int localYear = Macros.julianDateYear(localJDPlusLocalTime);

        double lct = 24 * (localDay - integerDay);

        return new CivilDateTime(Macros.decimalHoursHour(lct), Macros.decimalHoursMinute(lct),
                (int) Macros.decimalHoursSecond(lct), (int) integerDay, localMonth, localYear);
    }

    /**
     * Convert Universal Time to Greenwich Sidereal Time
     */
    public GreenwichSiderealTime universalTimeToGreenwichSiderealTime(double utHours, double utMinutes,
            double utSeconds, double gwDay, int gwMonth, int gwYear) {
        double jd = Macros.civilDateToJulianDate(gwDay, gwMonth, gwYear);
        double s = jd - 2451545;
        double t = s / 36525;
        double t01 = 6.697374558 + (2400.051336 * t) + (0.000025862 * t * t);
        double t02 = t01 - (24.0 * Math.floor(t01 / 24));
        double ut = Macros.hmsToDH(utHours, utMinutes, utSeconds);
        double a = ut * 1.002737909;
        double gst1 = t02 + a;
        double gst2 = gst1 - (24.0 * Math.floor(gst1 / 24));

        double gstHours = Macros.decimalHoursHour(gst2);
        double gstMinutes = Macros.decimalHoursMinute(gst2);
        double gstSeconds = Macros.decimalHoursSecond(gst2);

        return new GreenwichSiderealTime(gstHours, gstMinutes, gstSeconds);
    }

    /**
     * Convert Greenwich Sidereal Time to Universal Time
     */
    public UniversalTime greenwichSiderealTimeToUniversalTime(double gstHours, double gstMinutes, double gstSeconds,
            double gwDay, int gwMonth, int gwYear) {
        double jd = Macros.civilDateToJulianDate(gwDay, gwMonth, gwYear);
        double s = jd - 2451545;
        double t = s / 36525;
        double t01 = 6.697374558 + (2400.051336 * t) + (0.000025862 * t * t);
        double t02 = t01 - (24 * Math.floor(t01 / 24));
        double gstHours1 = Macros.hmsToDH(gstHours, gstMinutes, gstSeconds);

        double a = gstHours1 - t02;
        double b = a - (24 * Math.floor(a / 24));
        double ut = b * 0.9972695663;
        double utHours = Macros.decimalHoursHour(ut);
        double utMinutes = Macros.decimalHoursMinute(ut);
        double utSeconds = Macros.decimalHoursSecond(ut);

        WarningFlag warningFlag = (ut < 0.065574) ? WarningFlag.WARNING : WarningFlag.OK;

        return new UniversalTime(utHours, utMinutes, utSeconds, warningFlag);
    }

    /**
     * Convert Greenwich Sidereal Time to Local Sidereal Time
     */
    public LocalSiderealTime greenwichSiderealTimeToLocalSiderealTime(double gstHours, double gstMinutes,
            double gstSeconds, double geographicalLongitude) {
        double gst = Macros.hmsToDH(gstHours, gstMinutes, gstSeconds);
        double offset = geographicalLongitude / 15;
        double lstHours1 = gst + offset;
        double lstHours2 = lstHours1 - (24 * Math.floor(lstHours1 / 24));

        double lstHours = Macros.decimalHoursHour(lstHours2);
        double lstMinutes = Macros.decimalHoursMinute(lstHours2);
        double lstSeconds = Macros.decimalHoursSecond(lstHours2);

        return new LocalSiderealTime(lstHours, lstMinutes, lstSeconds);
    }

    /**
     * Convert Local Sidereal Time to Greenwich Sidereal Time
     */
    public GreenwichSiderealTime localSiderealTimeToGreenwichSiderealTime(double lstHours, double lstMinutes,
            double lstSeconds, double geographicalLongitude) {
        double gst = Macros.hmsToDH(lstHours, lstMinutes, lstSeconds);
        double longHours = geographicalLongitude / 15;
        double gst1 = gst - longHours;
        double gst2 = gst1 - (24 * Math.floor(gst1 / 24));

        int gstHours = Macros.decimalHoursHour(gst2);
        int gstMinutes = Macros.decimalHoursMinute(gst2);
        double gstSeconds = Macros.decimalHoursSecond(gst2);

        return new GreenwichSiderealTime(gstHours, gstMinutes, gstSeconds);
    }
}
