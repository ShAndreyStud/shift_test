package com.sheandstud;

import com.sheandstud.options.OptionsParser;
import com.sheandstud.processing.FileManager;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Главный класс утилиты, который обрабатывает входные файлы,
 * фильтрует данные и выводит статистику.
 */
public class Main {

    /**
     * Точка входа в программу.
     * <p>
     * Обрабатывает аргументы командной строки, создает менеджер файлов,
     * обрабатывает входные файлы, после чего выводит статистику.
     *
     * @param args аргументы командной строки, содержат в себе опции,
     * выбранные пользователем
     */
    public static void main(String[] args) {
        try {
            CommandLine cmd = OptionsParser.parse(args);
            try (FileManager manager = new FileManager(cmd)) {
                processFiles(cmd, manager);
                printStatistics(cmd, manager);
            }
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Обрабатывает список входных файлов.
     * <p>
     * Для каждого файла вызывается метод {@link #processFile(String, FileManager)}.
     *
     * @param cmd     объект командной строки с параметрами
     * @param manager менеджер файлов для обработки данных
     */
    private static void processFiles(CommandLine cmd, FileManager manager) {
        List<String> inputFiles = cmd.getArgList();

        for (String file : inputFiles) {
            processFile(file, manager);
        }
    }

    /**
     * Обрабатывает отдельно взятый файл.
     * <p>
     * Читает строки из файла и передает их в менеджер файлов для обработки.
     * Если файл не существует, выводится сообщение об ошибке.
     *
     * @param filename имя файла для обработки
     * @param manager  менеджер файлов для обработки данных
     */
    private static void processFile(String filename, FileManager manager) {
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            System.err.println("File not found: " + filename);
            return;
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                try {
                    manager.processLine(line);
                } catch (IOException e) {
                    System.err.println("Error processing line: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
    }

    /**
     * Выводит статистику по целым числам, вещественным числам и строкам в файлах.
     * <p>
     * Формат статистики зависит от выбранной пользователем опции:
     * f - полная статистика, s - краткая статистика
     *
     * @param cmd     объект командной строки с параметрами
     * @param manager менеджер файлов, содержащий статистику
     */
    private static void printStatistics(CommandLine cmd, FileManager manager) {
        boolean fullStats = cmd.hasOption("f");

        manager.getStatistics().forEach(stat ->
                System.out.println(stat.format(fullStats))
        );
    }
}