package statistics;

import com.sheandstud.processing.statistics.StringStatistics;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringStatisticsTest {
    @Test
    void testEmptyInput() {
        StringStatistics stats = new StringStatistics();
        String result = stats.format(true);
        assertTrue(result.contains("Count: 0"));
        assertTrue(result.contains("Min Length: 2147483647"));
        assertTrue(result.contains("Max Length: 0"));
    }

    @Test
    void testMixedLengthStrings() {
        StringStatistics stats = new StringStatistics();
        stats.update("a");
        stats.update("hello");
        stats.update("this is a long text");
        stats.update("");

        assertEquals(4, stats.getCount());
        assertEquals(0, stats.getMinLength());
        assertEquals(16, stats.getMaxLength());

        String fullStats = stats.format(true);
        assertTrue(fullStats.contains("Min Length: 0"));
        assertTrue(fullStats.contains("Max Length: 16"));
    }

    @Test
    void testUnicodeCharacters() {
        StringStatistics stats = new StringStatistics();
        stats.update("こんにちは");
        stats.update("ru");

        assertEquals(2, stats.getCount());
        assertEquals(5, stats.getMaxLength());
        assertEquals(1, stats.getMinLength());
    }

}
