package astro.practical;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import astro.practical.lib.Binary;
import astro.practical.models.BinaryStarOrbit;

public class BinaryTest {
    private Binary testBinary;

    @BeforeEach
    void setUp() {
        testBinary = new Binary();
    }

    @Test
    void testBinaryStarOrbit() {
        BinaryStarOrbit binaryStarOrbit = testBinary.binaryStarOrbit(1, 1, 1980, "eta-Cor");

        assertAll("Binary Star Orbit",
                () -> assertEquals(318.5, binaryStarOrbit.positionAngleDeg),
                () -> assertEquals(0.41, binaryStarOrbit.separationArcsec));
    }
}
