package com.sheandstud.options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Класс для определения опций командной строки.
 * <p>
 * Используется для настройки доступных опций утилиты с помощью библиотеки Apache Commons CLI.
 */
public class Cli {

    /**
     * Возвращает список доступных опций командной строки.
     * <p>
     * Опции включают:
     * <ul>
     *   <li>-o: директория для выходных файлов;</li>
     *   <li>-p: префикс для имен выходных файлов;</li>
     *   <li>-a: режим добавления данных в существующие файлы;</li>
     *   <li>-s: вывод краткой статистики;</li>
     *   <li>-f: вывод полной статистики.</li>
     * </ul>
     *
     * @return объект {@link Options}, содержащий все доступные опции
     */
    public static Options getOptions() {
        Options options = new Options();
        options.addOption(Option.builder("o")
                .longOpt("output")
                .hasArg()
                .argName("DIR")
                .desc("Output directory for results")
                .build());
        options.addOption(Option.builder("p")
                .longOpt("prefix")
                .hasArg()
                .argName("PREFIX")
                .desc("Prefix for output filenames")
                .build());
        options.addOption(Option.builder("a")
                .longOpt("append")
                .desc("Append to existing files")
                .build());
        options.addOption(Option.builder("s")
                .longOpt("short-stat")
                .desc("Show short statistics")
                .build());
        options.addOption(Option.builder("f")
                .longOpt("full-stat")
                .desc("Show full statistics")
                .build());
        return options;
    }
}
