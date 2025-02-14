package processing.handlers;

import com.sheandstud.processing.handlers.IntegerHandler;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerHandlerTest {
    private Path testFile;
    private IntegerHandler handler;

    @BeforeEach
    void setUp() throws IOException {
        testFile = Files.createTempFile("test-int", ".txt");
        handler = new IntegerHandler(testFile, false);
    }

    @Test
    void testValidInteger() throws IOException {
        assertTrue(handler.handle("123456789"));
        assertTrue(handler.handle("+123456789"));
        assertTrue(handler.handle("-123456789"));
        handler.close();

        String content = Files.readString(testFile);
        assertTrue(content.contains("123456789"));
        assertTrue(content.contains("+123456789"));
        assertTrue(content.contains("-123456789"));
    }

    @Test
    void testInvalidInteger() throws IOException {
        assertFalse(handler.handle("12.34"));
        assertFalse(handler.handle("abc"));
        assertFalse(handler.handle("1 2 3"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
    }
}