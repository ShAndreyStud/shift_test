package processing.handlers;

import com.sheandstud.processing.handlers.FloatHandler;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloatHandlerTest {
    private Path testFile;
    private FloatHandler handler;

    @BeforeEach
    void setUp() throws IOException {
        testFile = Files.createTempFile("test-float", ".txt");
        handler = new FloatHandler(testFile, false);
    }

    @Test
    void testValidFloats() throws IOException {
        assertTrue(handler.handle("3.1415"));
        assertTrue(handler.handle(".1415"));
        assertTrue(handler.handle("-123.456"));
        assertTrue(handler.handle("1.23e-4"));
        assertTrue(handler.handle("1.23E-4"));
        handler.close();

        String content = Files.readString(testFile);
        assertTrue(content.contains("3.1415"));
        assertTrue(content.contains(".1415"));
        assertTrue(content.contains("-123.456"));
        assertTrue(content.contains("1.23e-4"));
        assertTrue(content.contains("1.23E-4"));
    }

    @Test
    void testInvalidFloats() throws IOException {
        assertFalse(handler.handle("123"));
        assertFalse(handler.handle("abc"));
        assertFalse(handler.handle("1. 23"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
    }
}