package com.sheandstud.processing.handlers;

import com.sheandstud.processing.statistics.FloatStatistics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Класс для обработки вещественных чисел.
 * Используется регулярное выражение для проверки на вещественное число.
 * Примеры: -111.111, +111.111, 111.111, -111111E-3, -111111e-3, .111
 */
public class FloatHandler implements DataHandler {
    private static final Pattern PATTERN = Pattern.compile(
            "^[-+]?\\d+\\.\\d+([eE][-+]?\\d+)?$|" +           // Числа с десятичной точкой и экспонентой
                    "^[-+]?\\.\\d+([eE][-+]?\\d+)?$|" +       // Числа, начинающиеся с точки
                    "^[-+]?\\d+[eE][-+]?\\d+$"                // Числа в научной нотации без десятичной точки
    );

    private final FloatStatistics statistics = new FloatStatistics();
    private final Path outputPath;
    private final boolean appendMode;
    private BufferedWriter writer;

    /**
     * Конструктор для инициализации обработчика вещественных чисел.
     * <p>
     * Создает директории для выходного файла, если они не существуют.
     *
     * @param outputPath путь к выходному файлу
     * @param appendMode режим записи: true - добавление, false - перезапись
     */
    public FloatHandler(Path outputPath, boolean appendMode) throws IOException {
        Objects.requireNonNull(outputPath, "Output path cannot be null");
        Files.createDirectories(outputPath.getParent());
        this.outputPath = outputPath;
        this.appendMode = appendMode;
    }

    /**
     * Обрабатывает строку данных.
     * <p>
     * Если строка соответствует формату вещественного числа, она записывается в файл,
     * а также обновляется статистика.
     *
     * @param data строка для обработки
     * @return true, если строка была успешно обработана, иначе false
     */
    @Override
    public boolean handle(String data) throws IOException {
        if (PATTERN.matcher(data).matches()) {
            try {
                BigDecimal number = new BigDecimal(data);
                initializeWriter();
                updateStatistics(number);
                writeData(data);
                return true;
            } catch (NumberFormatException e) {
                System.err.println("Invalid float format: " + data);
                return false;
            }
        }
        return false;
    }

    /**
     * Инициализирует поток записи, если он еще не был создан.
     * <p>
     * Создает выходной файл и открывает его для записи в зависимости от режима.
     */
    private void initializeWriter() throws IOException {
        if (writer == null) {
            Files.createDirectories(outputPath.getParent());
            writer = Files.newBufferedWriter(
                    outputPath,
                    StandardOpenOption.CREATE,
                    appendMode ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING
            );
        }
    }

    /**
     * Обновляет статистику новым числом.
     *
     * @param number новое число для обновления статистики
     */
    private void updateStatistics(BigDecimal number) {
        statistics.update(number);
    }

    /**
     * Записывает данные в выходной файл.
     * <p>
     * Добавляет новую строку после записи данных.
     *
     * @param data строка для записи
     */
    private void writeData(String data) throws IOException {
        writer.write(data);
        writer.newLine();
    }

    /**
     * Возвращает объект статистики для вещественных чисел.
     *
     * @return объект {@link FloatStatistics}
     */
    @Override
    public FloatStatistics getStatistics() {
        return statistics;
    }

    /**
     * Закрывает поток записи.
     */
    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}