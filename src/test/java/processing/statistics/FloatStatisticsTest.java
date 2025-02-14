package processing.statistics;

import com.sheandstud.processing.statistics.FloatStatistics;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloatStatisticsTest {
    @Test
    void floatStats_ScientificNotation() {
        FloatStatistics stats = new FloatStatistics();
        stats.update(new BigDecimal("1.23E5"));
        stats.update(new BigDecimal("2.5E-3"));

        String result = stats.format(true);
        assertTrue(result.contains("Avg: 61500.00125"));
    }

    @Test
    void floatStats_PrecisionHandling() {
        FloatStatistics stats = new FloatStatistics();
        stats.update(new BigDecimal("0.333333"));
        stats.update(new BigDecimal("0.666667"));

        String result = stats.format(true);
        assertTrue(result.contains("Avg: 0.500000"));
    }
}
