package processing.handlers;

import com.sheandstud.processing.handlers.StringHandler;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringHandlerTest {
    private Path testFile;
    private StringHandler handler;

    @BeforeEach
    void setUp() throws IOException {
        testFile = Files.createTempFile("test-str", ".txt");
        handler = new StringHandler(testFile, false);
    }

    @Test
    void testStringHandling() throws IOException {
        handler.handle("Hello World!");
        handler.handle("123");
        handler.close();

        String content = Files.readString(testFile);
        assertTrue(content.contains("Hello World!"));
        assertTrue(content.contains("123"));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
    }
}