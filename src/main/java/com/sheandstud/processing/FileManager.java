package com.sheandstud.processing;

import com.sheandstud.Main;
import com.sheandstud.processing.handlers.*;
import com.sheandstud.processing.statistics.Statistics;
import org.apache.commons.cli.CommandLine;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для управления обработкой данных из файлов.
 */
public class FileManager implements AutoCloseable {
    private final List<DataHandler> handlers = new ArrayList<>();

    /**
     * Конструктор для инициализации менеджера файлов.
     * <p>
     * Определяет путь для выходных файлов, префикс имён файлов и режим записи (добавление или перезапись).
     * Создает обработчики для целых чисел, вещественных чисел и строк.
     * <p>
     * Сначала данные из строки файла проверяются на целые числа, если проверка не прошла,
     * то проверяются на вещественные числа, и в последнюю очередь проверяются на строку,
     * послкольку всё, что не относится к формату чисел - считается строкой.
     */
    public FileManager(CommandLine cmd) throws IOException {
        Path baseDir = getJarDirectory();

        Path outputDir;
        if (cmd.hasOption("o")) {
            outputDir = baseDir.resolve(cmd.getOptionValue("o")).normalize();
        } else {
            outputDir = baseDir;
        }

        String prefix = cmd.getOptionValue("p", "");
        boolean append = cmd.hasOption("a");

        Files.createDirectories(outputDir);

        handlers.add(new IntegerHandler(outputDir.resolve(prefix + "integers.txt"), append));
        handlers.add(new FloatHandler(outputDir.resolve(prefix + "floats.txt"), append));
        handlers.add(new StringHandler(outputDir.resolve(prefix + "strings.txt"), append));
    }

    /**
     * Возвращает путь к директории, где находится JAR-файл.
     * <p>
     * Если определить путь не удаётся, возвращается текущая рабочая директория.
     */
    private Path getJarDirectory() {
        try {
            Path jarPath = Paths.get(
                    Main.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );

            if (Files.isRegularFile(jarPath)) {
                return jarPath.getParent().toAbsolutePath().normalize();
            }
            return Paths.get("").toAbsolutePath().normalize();
        } catch (URISyntaxException | NullPointerException | IllegalArgumentException e) {
            return Paths.get("").toAbsolutePath().normalize();
        }
    }

    /**
     * Обрабатывает одну строку данных.
     * <p>
     * Передает строку каждому обработчику до тех пор, пока один из них не примет её.
     *
     * @param line строка для обработки
     */
    public void processLine(String line) throws IOException {
        for (DataHandler handler : handlers) {
            if (handler.handle(line)) {
                return;
            }
        }
    }

    /**
     * Возвращает список статистик для всех типов данных.
     * <p>
     * Каждый обработчик предоставляет свою статистику.
     *
     * @return список объектов {@link Statistics}
     */
    public List<Statistics> getStatistics() {
        return handlers.stream()
                .map(DataHandler::getStatistics)
                .collect(Collectors.toList());
    }

    /**
     * Закрывает все обработчики данных.
     */
    @Override
    public void close() throws IOException {
        for (DataHandler handler : handlers) {
            handler.close();
        }
    }
}
