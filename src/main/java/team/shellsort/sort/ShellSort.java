package team.shellsort.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Реализация сортировки Шелла (ShellSort) с поддержкой различных стратегий выбора шага (gap sequence).
 * <p>
 * Работает с любыми типами данных через {@link Comparator}.
 * Эффективна для коллекций, поддерживающих быстрый случайный доступ (например, {@link java.util.ArrayList}).
 * Для {@link java.util.LinkedList} рекомендуется скопировать данные во временный список.
 * </p>
 */
public final class ShellSort {

    private ShellSort() {
        // утилитный класс — создание экземпляра не требуется
    }

    /** Типы последовательностей для выбора шага (gap)
     */
    public enum GapType {
        CLASSIC,     // Классическая версия (n/2, n/4, ..., 1)
        KNUTH,       // Последовательность Кнута (1, 4, 13, 40, ...)
        HIBBARD,     // Последовательность Хиббарда (1, 3, 7, 15, ...)
        SEDGEWICK,   // Последовательность Седжвика (701, 301, 132, ...)
        PROPORTIONAL // Эмпирическая (n, n/2.2, n/4.8, ...)
    }

    /** Основной метод сортировки ShellSort.
     *
     * @param list       список элементов для сортировки
     * @param comparator компаратор для сравнения элементов
     * @param type       тип последовательности gap
     * @param <T>        тип элементов списка
     */
    public static <T> void sort(List<T> list, Comparator<T> comparator, GapType type) {
        Objects.requireNonNull(list, "list must not be null");
        Objects.requireNonNull(comparator, "comparator must not be null");

        int n = list.size();
        int[] sedgewick = {701, 301, 132, 57, 23, 10, 4, 1};

        switch (type) {
            // Классический алгоритм Шелла (1959)
            case CLASSIC -> {
                for (int gap = n / 2; gap > 0; gap /= 2) {
                    gapSort(list, comparator, gap);
                }
            }

            // Последовательность Кнута (1, 4, 13, 40, ...)
            case KNUTH -> {
                int gap = initialKnuthGap(n);
                for (; gap > 0; gap /= 3) {
                    gapSort(list, comparator, gap);
                }
            }

            // Последовательность Хиббарда (1, 3, 7, 15, ...)
            case HIBBARD -> {
                int gap = initialHibbardGap(n);
                for (; gap > 0; gap = (gap - 1) / 2) {
                    gapSort(list, comparator, gap);
                }
            }

            // Последовательность Седжвика (701, 301, 132, ...), идём по убыванию до 1
            case SEDGEWICK -> {
                for (int gap : sedgewick) {
                    if (gap >= n) continue;
                    gapSort(list, comparator, gap);
                }
            }

            // Эмпирическая (примерно n, n/2.2, n/4.8, ...)
            case PROPORTIONAL -> {
                for (int gap = n; gap > 1; gap = (int) (gap / 2.2)) {
                    gapSort(list, comparator, gap);
                }
                gapSort(list, comparator, 1);
            }
        }
    }

    /** Частичная сортировка элементов с заданным шагом (gap).
     * Предполагается, что список поддерживает быстрый доступ по индексу.
     */
    private static <T> void gapSort(List<T> list, Comparator<T> comparator, int gap) {
        if (gap <= 0) return;

        int n = list.size();
        for (int i = gap; i < n; i++) {
            T temp = list.get(i);
            int j = i;

            while (j >= gap && comparator.compare(list.get(j - gap), temp) > 0) {
                list.set(j, list.get(j - gap));
                j -= gap;
            }
            list.set(j, temp);
        }
    }

    /** Начальный gap для последовательности Кнута (1, 4, 13, 40, ...). */
    private static int initialKnuthGap(int n) {
        int gap = 1;
        while (gap < n / 3) gap = 3 * gap + 1;
        return gap;
    }

    /** Начальный gap для последовательности Хиббарда (1, 3, 7, 15, ...). */
    private static int initialHibbardGap(int n) {
        int gap = 1;
        while (gap < n) gap = 2 * gap + 1;
        return gap;
    }
}