package com.sheandstud.processing.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс для сбора и форматирования статистики вещественных чисел.
 */
public class FloatStatistics implements Statistics {
    private int count;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum = BigDecimal.ZERO;

    /**
     * Обновляет статистику при обработке нового вещественного числа.
     * <p>
     * Обновляются значения минимума, максимума и суммы.
     *
     * @param number новое число для обновления статистики
     */
    public void update(BigDecimal number) {
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
        sb.append("Float Statistics:\n");
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
     * Убирает незначащие нули и использует обычный формат без экспоненты.
     *
     * @return минимальное значение или "N/A"
     */
    private String getMin() {
        return count == 0 ? "N/A" : min.stripTrailingZeros().toPlainString();
    }

    /**
     * Возвращает максимальное значение в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     * Убирает незначащие нули и использует обычный формат без экспоненты.
     *
     * @return максимальное значение или "N/A"
     */
    private String getMax() {
        return count == 0 ? "N/A" : max.stripTrailingZeros().toPlainString();
    }

    /**
     * Возвращает сумму всех чисел в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     * Убирает незначащие нули и использует обычный формат без экспоненты.
     *
     * @return сумма всех чисел или "N/A"
     */
    private String getSum() {
        return count == 0 ? "N/A" : sum.stripTrailingZeros().toPlainString();
    }

    /**
     * Возвращает среднее значение в виде строки.
     * <p>
     * Если чисел обработано не было, возвращает "N/A".
     * Среднее значение округляется до 10 знаков после запятой с использованием режима HALF_UP.
     *
     * @return среднее значение или "N/A"
     */
    private String getAverage() {
        return count == 0 ? "N/A" :
                sum.divide(
                        BigDecimal.valueOf(count),
                        10,
                        RoundingMode.HALF_UP
                ).toString();
    }
}