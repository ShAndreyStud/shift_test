package processing;

import com.sheandstud.options.Cli;
import com.sheandstud.processing.FileManager;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    private Path tempDir;
    private FileManager fileManager;

    @BeforeEach
    void setUp() throws IOException, ParseException {
        tempDir = Files.createTempDirectory("filemanager-test");
    }

    @Test
    void testSelectiveFileCreation() throws Exception {
        String[] args = {
                "-o", tempDir.toString(),
                "-p", "test_",
                "input.txt"
        };
        CommandLine cmd = new DefaultParser().parse(Cli.getOptions(), args);

        fileManager = new FileManager(cmd);

        fileManager.processLine("123");
        fileManager.processLine("45.67");
        fileManager.processLine("hello");
        fileManager.close();

        assertTrue(Files.exists(tempDir.resolve("test_integers.txt")));
        assertTrue(Files.exists(tempDir.resolve("test_floats.txt")));
        assertTrue(Files.exists(tempDir.resolve("test_strings.txt")));
    }

    @Test
    void testNoEmptyFiles() throws Exception {
        String[] args = {"-o", tempDir.toString(), "empty.txt"};
        CommandLine cmd = new DefaultParser().parse(Cli.getOptions(), args);

        try (FileManager manager = new FileManager(cmd)) {
            manager.processLine("text data");
        }

        assertFalse(Files.exists(tempDir.resolve("integers.txt")));
        assertFalse(Files.exists(tempDir.resolve("floats.txt")));
        assertTrue(Files.exists(tempDir.resolve("strings.txt")));
    }

    @Test
    void testAppendMode() throws Exception {
        String[] args1 = {"-o", tempDir.toString(), "input.txt"};
        try (FileManager manager = new FileManager(new DefaultParser().parse(Cli.getOptions(), args1))) {
            manager.processLine("100");
        }

        String[] args2 = {"-a", "-o", tempDir.toString(), "input.txt"};
        try (FileManager manager = new FileManager(new DefaultParser().parse(Cli.getOptions(), args2))) {
            manager.processLine("200");
        }

        List<String> lines = Files.readAllLines(tempDir.resolve("integers.txt"));
        assertEquals(List.of("100", "200"), lines);
    }

    @Test
    void testWithoutAppendMode() throws Exception {
        String[] args1 = {"-o", tempDir.toString(), "input.txt"};
        try (FileManager manager = new FileManager(new DefaultParser().parse(Cli.getOptions(), args1))) {
            manager.processLine("100");
        }

        String[] args2 = {"-o", tempDir.toString(), "input.txt"};
        try (FileManager manager = new FileManager(new DefaultParser().parse(Cli.getOptions(), args2))) {
            manager.processLine("200");
        }

        List<String> lines = Files.readAllLines(tempDir.resolve("integers.txt"));
        assertEquals(List.of("200"), lines);
    }

    @Test
    void testStatisticsCollection() throws Exception {
        String[] args = {"-f", "-o", tempDir.toString(), "input.txt"};
        CommandLine cmd = new DefaultParser().parse(Cli.getOptions(), args);

        try (FileManager manager = new FileManager(cmd)) {
            manager.processLine("123");
            manager.processLine("45.67");
            manager.processLine("text");
            manager.processLine("3.1415");

            String stats = manager.getStatistics().stream()
                    .map(s -> s.format(true))
                    .collect(Collectors.joining("\n\n"));

            assertTrue(stats.contains("Integer Statistics:\n  Count: 1"));
            assertTrue(stats.contains("Float Statistics:\n  Count: 2"));
            assertTrue(stats.contains("String Statistics:\n  Count: 1"));
        }
    }

    @Test
    void testDirectoryAutoCreation() throws Exception {
        Path nestedDir = tempDir.resolve("deep/nested/dir");
        String[] args = {"-o", nestedDir.toString(), "input.txt"};

        try (FileManager manager = new FileManager(new DefaultParser().parse(Cli.getOptions(), args))) {
            manager.processLine("test");
        }

        assertTrue(Files.isDirectory(nestedDir));
        assertTrue(Files.exists(nestedDir.resolve("strings.txt")));
    }

    @AfterEach
    void tearDown() throws IOException {
        if (fileManager != null) {
            fileManager.close();
        }
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException ignored) {}
                });
    }
}
