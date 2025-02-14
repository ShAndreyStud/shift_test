package com.sheandstud.processing.handlers;

import com.sheandstud.processing.statistics.Statistics;

import java.io.Closeable;
import java.io.IOException;

/**
 * Интерфейс для обработки данных.
 * <p>
 * Определяет методы для классов, которые обрабатывают строки данных,
 * записывают их в файлы и предоставляют статистику.
 */
public interface DataHandler extends Closeable {
    /**
     * Обрабатывает строку данных.
     * <p>
     * Реализации определяют, как данные будут обрабатываться,
     * записываться и как будет обновляться статистика.
     *
     * @param data строка для обработки
     * @return true, если строка была успешно обработана, иначе false
     */
    boolean handle(String data) throws IOException;
    /**
     * Возвращает объект статистики для обработанных данных.
     * <p>
     * Реализации предоставляют объект статистики, который
     * содержит информацию о всех обработанных данных.
     *
     * @return объект {@link Statistics}, представляющий статистику
     */
    Statistics getStatistics();
}
