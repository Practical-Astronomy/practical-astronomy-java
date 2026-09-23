package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Comet;
import astro.practical.models.PositionOfEllipticalComet;
import astro.practical.models.PositionOfParabolicComet;

public class CometTest {
    private Comet testComet;

    @BeforeEach
    void setUp() {
        testComet = new Comet();
    }

    @Test
    void testPositionOfEllipticalComet() {
        PositionOfEllipticalComet positionOfEllipticalComet = testComet.positionOfEllipticalComet(0, 0, 0, false, 0, 1,
                1, 1984, "Halley");

        assertAll("Position of Elliptical Comet",
                () -> assertEquals(6, positionOfEllipticalComet.cometRAHour),
                () -> assertEquals(29, positionOfEllipticalComet.cometRAMin),
                () -> assertEquals(10, positionOfEllipticalComet.cometDecDeg),
                () -> assertEquals(13, positionOfEllipticalComet.cometDecMin),
                () -> assertEquals(8.13, positionOfEllipticalComet.cometDistEarth));
    }

    @Test
    void testPositionOfParabolicComet() {
        PositionOfParabolicComet positionOfParabolicComet = testComet.positionOfParabolicComet(0, 0, 0, false, 0, 25,
                12, 1977, "Kohler");

        assertAll("Position of Parabolic Comet",
                () -> assertEquals(23, positionOfParabolicComet.cometRAHour),
                () -> assertEquals(17, positionOfParabolicComet.cometRAMin),
                () -> assertEquals(11.53, positionOfParabolicComet.cometRASec),
                () -> assertEquals(-33, positionOfParabolicComet.cometDecDeg),
                () -> assertEquals(42, positionOfParabolicComet.cometDecMin),
                () -> assertEquals(26.42, positionOfParabolicComet.cometDecSec),
                () -> assertEquals(1.11, positionOfParabolicComet.cometDistEarth));
    }
}
