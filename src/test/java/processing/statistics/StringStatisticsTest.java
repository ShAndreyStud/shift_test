package processing.statistics;

import com.sheandstud.processing.statistics.StringStatistics;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringStatisticsTest {
    @Test
    void stringStats_EmptyData() {
        StringStatistics stats = new StringStatistics();
        String result = stats.format(true);
        assertTrue(result.contains("Count: 0"));
        assertTrue(result.contains("Min Length: N/A"));
        assertTrue(result.contains("Max Length: N/A"));
    }

    @Test
    void stringStats_MixedLengths() {
        StringStatistics stats = new StringStatistics();
        stats.update("a");
        stats.update("abcd");
        stats.update("");

        String result = stats.format(true);
        assertTrue(result.contains("Count: 3"));
        assertTrue(result.contains("Min Length: 0"));
        assertTrue(result.contains("Max Length: 4"));
    }
}
