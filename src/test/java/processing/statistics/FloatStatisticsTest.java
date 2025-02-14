package statistics;

import com.sheandstud.processing.statistics.FloatStatistics;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloatStatisticsTest {
    @Test
    void testScientificNotation() {
        FloatStatistics stats = new FloatStatistics();
        stats.update(new BigDecimal("1.23E5"));
        stats.update(new BigDecimal("2.5E-3"));

        assertEquals(2, stats.getCount());
        assertTrue(stats.getMin().compareTo(new BigDecimal("0.0025")) == 0);
        assertTrue(stats.getMax().compareTo(new BigDecimal("123000")) == 0);
        assertTrue(stats.getSum().compareTo(new BigDecimal("123000.0025")) == 0);

        String result = stats.format(true);
        assertTrue(result.contains("Avg: 61500.00125"));
    }
}
