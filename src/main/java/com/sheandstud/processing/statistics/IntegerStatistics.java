package com.sheandstud.processing.statistics;

import java.math.BigInteger;

/**
 * Класс для сбора и форматирования статистики целых чисел.
 */
public class IntegerStatistics implements Statistics{
    private int count;
    private BigInteger min;
    private BigInteger max;
    private BigInteger sum = BigInteger.ZERO;

    /**
     * Обновляет статистику при обработке нового числа.
     * <p>
     * Обновляются значения минимума, максимума и суммы.
     *
     * @param number новое число для обновления статистики
     */
    public void update(BigInteger number) {
        count++;
        if (min == null || number.compareTo(min) < 0) min = number;
        if (max == null || number.compareTo(max) > 0) max = number;
        sum = sum.add(number);
    }

    /**
     * Форматирует вывод статистики.
     * <p>
     * Если параметр {@code full} равен true, выбрана полная статистика,
     * включает детальную информацию: минимум, максимум, сумму и среднее значение.
     *
     * @param full флаг, указывающий, нужно ли включать полную статистику
     * @return строка с отформатированной статистикой
     */
    @Override
    public String format(boolean full) {
        StringBuilder sb = new StringBuilder();
        sb.append("Integer Statistics:\n");
        sb.append("  Count: ").append(count);

        if (full) {
            sb.append("\n  Min: ").append(getMin());
            sb.append("\n  Max: ").append(getMax());
            sb.append("\n  Sum: ").append(getSum());
            sb.append("\n  Avg: ").append(getAverage());
        }
        return sb.toString();
    }

    /**
     * Возвращает минимальное значение в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     *
     * @return минимальное значение или "N/A"
     */
    private String getMin() {
        return count == 0 ? "N/A" : min.toString();
    }

    /**
     * Возвращает максимальное значение в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     *
     * @return максимальное значение или "N/A"
     */
    private String getMax() {
        return count == 0 ? "N/A" : max.toString();
    }

    /**
     * Возвращает сумму всех чисел в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     *
     * @return сумма всех чисел или "N/A"
     */
    private String getSum() {
        return count == 0 ? "N/A" : sum.toString();
    }

    /**
     * Возвращает среднее значение в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     *
     * @return среднее значение или "N/A"
     */
    private String getAverage() {
        return count == 0 ? "N/A" : sum.divide(BigInteger.valueOf(count)).toString();
    }
}