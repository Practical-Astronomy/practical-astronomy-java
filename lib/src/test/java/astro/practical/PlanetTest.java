package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Planet;
import astro.practical.models.PlanetPosition;
import astro.practical.models.VisualAspectsOfAPlanet;

public class PlanetTest {
    private Planet testPlanet;

    @BeforeEach
    void setUp() {
        testPlanet = new Planet();
    }

    @Test
    void testApproximatePositionOfPlanet() {
        PlanetPosition planetPosition = testPlanet.approximatePositionOfPlanet(0, 0, 0, false, 0, 22, 11, 2003,
                "Jupiter");

        assertAll("Approximate Position of Planet",
                () -> assertEquals(11, planetPosition.planetRAHour),
                () -> assertEquals(11, planetPosition.planetRAMin),
                () -> assertEquals(13.8, planetPosition.planetRASec),
                () -> assertEquals(6, planetPosition.planetDecDeg),
                () -> assertEquals(21, planetPosition.planetDecMin),
                () -> assertEquals(25.1, planetPosition.planetDecSec));
    }

    @Test
    void testPrecisePositionOfPlanet() {
        PlanetPosition planetPosition = testPlanet.precisePositionOfPlanet(0, 0, 0, false, 0, 22, 11, 2003, "Jupiter");

        assertAll("Precise Position of Planet",
                () -> assertEquals(11, planetPosition.planetRAHour),
                () -> assertEquals(10, planetPosition.planetRAMin),
                () -> assertEquals(30.99, planetPosition.planetRASec),
                () -> assertEquals(6, planetPosition.planetDecDeg),
                () -> assertEquals(25, planetPosition.planetDecMin),
                () -> assertEquals(49.46, planetPosition.planetDecSec));
    }

    @Test
    void testVisualAspectsOfAPlanet() {
        VisualAspectsOfAPlanet visualAspectsOfAPlanet = testPlanet.visualAspectsOfAPlanet(0, 0, 0, false, 0, 22, 11,
                2003, "Jupiter");

        assertAll("Visual Aspects of a Planet",
                () -> assertEquals(5.59829, visualAspectsOfAPlanet.distanceAU),
                () -> assertEquals(35.1, visualAspectsOfAPlanet.angDiaArcsec),
                () -> assertEquals(0.99, visualAspectsOfAPlanet.phase),
                () -> assertEquals(0, visualAspectsOfAPlanet.lightTimeHour),
                () -> assertEquals(46, visualAspectsOfAPlanet.lightTimeMinutes),
                () -> assertEquals(33.32, visualAspectsOfAPlanet.lightTimeSeconds),
                () -> assertEquals(113.2, visualAspectsOfAPlanet.posAngleBrightLimbDeg),
                () -> assertEquals(-2.0, visualAspectsOfAPlanet.approximateMagnitude));
    }
}
