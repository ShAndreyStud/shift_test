package statistics;

import com.sheandstud.processing.statistics.IntegerStatistics;
import org.junit.jupiter.api.*;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerStatisticsTest {
    @Test
    void testEmptyStatistics() {
        IntegerStatistics stats = new IntegerStatistics();
        String result = stats.format(true);
        assertTrue(result.contains("Count: 0"));
    }

    @Test
    void testLargeNumbers() {
        IntegerStatistics stats = new IntegerStatistics();
        stats.update(new BigInteger("123456789012345678901234567890"));
        stats.update(new BigInteger("-9999999999999999999"));

        String result = stats.format(true);
        assertTrue(result.contains("Sum: 123456789002345678901234567891"));
    }
}
