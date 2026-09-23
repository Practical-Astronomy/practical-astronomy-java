package astro.practical.lib;

import astro.practical.data.BinaryInfo;
import astro.practical.models.BinaryStarOrbit;
import astro.practical.models.data.BinaryData;

public class Binary {
    /**
     * Calculate orbital data for binary star.
     */
    public BinaryStarOrbit binaryStarOrbit(double greenwichDateDay, int greenwichDateMonth, int greenwichDateYear,
            String binaryName) {
        BinaryInfo binaryInfo = new BinaryInfo();

        BinaryData binaryData = binaryInfo.getBinaryInfo(binaryName);

        double yYears = greenwichDateYear
                + (Macros.civilDateToJulianDate(greenwichDateDay, greenwichDateMonth, greenwichDateYear)
                        - Macros.civilDateToJulianDate(0, 1, greenwichDateYear)) / 365.242191
                - binaryData.EpochPeri;
        double mDeg = 360 * yYears / binaryData.Period;
        double mRad = Math.toRadians(mDeg - 360 * Math.floor(mDeg / 360));
        double eccentricity = binaryData.Ecc;
        double trueAnomalyRad = Macros.trueAnomaly(mRad, eccentricity);
        double rArcsec = (1 - eccentricity * Math.cos(Macros.eccentricAnomaly(mRad, eccentricity))) * binaryData.Axis;
        double taPeriRad = trueAnomalyRad + Math.toRadians(binaryData.LongPeri);

        double y = Math.sin(taPeriRad) * Math.cos(Math.toRadians(binaryData.Incl));
        double x = Math.cos(taPeriRad);
        double aDeg = Macros.wToDegrees(Math.atan2(y, x));
        double thetaDeg1 = aDeg + binaryData.PANode;
        double thetaDeg2 = thetaDeg1 - 360 * Math.floor(thetaDeg1 / 360);
        double rhoArcsec = rArcsec * Math.cos(taPeriRad) / Math.cos(Math.toRadians(thetaDeg2 - binaryData.PANode));

        double positionAngleDeg = Util.round(thetaDeg2, 1);
        double separationArcsec = Util.round(rhoArcsec, 2);

        return new BinaryStarOrbit(positionAngleDeg, separationArcsec);
    }
}
