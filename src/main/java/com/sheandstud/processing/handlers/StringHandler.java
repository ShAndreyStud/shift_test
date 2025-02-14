package com.sheandstud.processing.handlers;

import com.sheandstud.processing.statistics.StringStatistics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * Класс для обработки строковых данных.
 * Проверка на строку происходит в последнюю очередь.
 * Всё, что не прошло проверку на числа,- строка.
 */
public class StringHandler implements DataHandler {
    private final StringStatistics statistics = new StringStatistics();
    private final Path outputPath;
    private final boolean appendMode;
    private BufferedWriter writer;

    /**
     * Конструктор для инициализации обработчика строк.
     * <p>
     * Создает директории для выходного файла, если они не существуют.
     *
     * @param outputPath путь к выходному файлу
     * @param appendMode режим записи: true - добавление, false - перезапись
     */
    public StringHandler(Path outputPath, boolean appendMode) throws IOException {
        Objects.requireNonNull(outputPath, "Output path cannot be null");
        Files.createDirectories(outputPath.getParent());
        this.outputPath = outputPath;
        this.appendMode = appendMode;
    }

    /**
     * Обрабатывает строку данных.
     * <p>
     * Записывает строку в файл и обновляет статистику.
     *
     * @param data строка для обработки
     * @return всегда true, так как все строки считаются допустимыми
     */
    @Override
    public boolean handle(String data) throws IOException {
        initializeWriter();
        updateStatistics(data);
        writeData(data);
        return true;
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
     * Обновляет статистику новой строкой.
     *
     * @param data новая строка для обновления статистики
     */
    private void updateStatistics(String data) {
        statistics.update(data);
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
     * Возвращает объект статистики для строк.
     *
     * @return объект {@link StringStatistics}
     */
    @Override
    public StringStatistics getStatistics() {
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