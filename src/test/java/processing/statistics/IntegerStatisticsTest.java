package processing.statistics;

import com.sheandstud.processing.statistics.IntegerStatistics;
import org.junit.jupiter.api.*;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerStatisticsTest {
    @Test
    void integerStats_LargeNumbers() {
        IntegerStatistics stats = new IntegerStatistics();
        stats.update(new BigInteger("12345678901234567890"));
        stats.update(new BigInteger("-987654321"));

        String result = stats.format(true);
        assertTrue(result.contains("Min: -987654321"));
        assertTrue(result.contains("Max: 12345678901234567890"));
        assertTrue(result.contains("Sum: 12345678900246913569"));
    }

    @Test
    void integerStats_NoData() {
        IntegerStatistics stats = new IntegerStatistics();
        assertTrue(stats.format(true).contains("N/A"));
    }
}
