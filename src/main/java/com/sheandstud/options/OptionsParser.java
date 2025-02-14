package com.sheandstud.options;

import org.apache.commons.cli.*;

/**
 * Класс для парсинга аргументов командной строки.
 * <p>
 * Использует библиотеку Apache Commons CLI для анализа входных параметров.
 */
public class OptionsParser {

    /**
     * Парсит аргументы командной строки.
     * <p>
     * Проверяет, что указаны входные файлы. Если входные файлы не указаны,
     * выбрасывается исключение {@link ParseException}.
     *
     * @param args массив аргументов командной строки
     * @return объект {@link CommandLine}, содержащий разобранные параметры
     */
    public static CommandLine parse(String[] args) throws ParseException {
        Options options = Cli.getOptions();
        CommandLine cmd = new DefaultParser().parse(options, args);

        if (cmd.getArgList().isEmpty()) {
            throw new ParseException("No input files specified");
        }

        return cmd;
    }
}
