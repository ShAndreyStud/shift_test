package com.sheandstud.processing.statistics;

/**
 * Класс для сбора и форматирования статистики строк.
 */
public class StringStatistics implements Statistics {
    private int count;
    private int minLength = Integer.MAX_VALUE;
    private int maxLength;

    /**
     * Обновляет статистику при обработке новой строки.
     * <p>
     * Обновляются значения минимальной и максимальной длины строки.
     *
     * @param data новая строка для обновления статистики
     */
    public void update(String data) {
        count++;
        int len = data.length();
        minLength = Math.min(minLength, len);
        maxLength = Math.max(maxLength, len);
    }

    /**
     * Форматирует вывод статистики.
     * <p>
     * Если параметр {@code full} равен true, выбрана полная статистика,
     * включает детальную информацию: минимальную и максимальную длину строк.
     *
     * @param full флаг, указывающий, нужно ли включать полную статистику
     * @return строка с отформатированной статистикой
     */
    @Override
    public String format(boolean full) {
        StringBuilder sb = new StringBuilder();
        sb.append("String Statistics:\n");
        sb.append("  Count: ").append(count);

        if (full) {
            sb.append("\n  Min Length: ").append(getMinLength());
            sb.append("\n  Max Length: ").append(getMaxLength());
        }
        return sb.toString();
    }

    /**
     * Возвращает количество обработанных строк.
     *
     * @return количество строк
     */
    public int getCount() {
        return count;
    }

    /**
     * Возвращает минимальную длину строки.
     * <p>
     * Если строк обработано не было, возвращает "N/A".
     *
     * @return минимальная длина строки или "N/A"
     */
    public String getMinLength() {
        return count == 0 ? "N/A" : String.valueOf(minLength);
    }

    /**
     * Возвращает максимальную длину строки.
     * <p>
     * Если строк обработано не было, возвращает "N/A".
     *
     * @return максимальная длина строки или "N/A"
     */
    public String getMaxLength() {
        return count == 0 ? "N/A" : String.valueOf(maxLength);
    }
}
