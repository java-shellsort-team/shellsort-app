package team.shellsort.sort;

import java.util.Comparator;
import java.util.List;

/**
 * Реализация сортировки Шелла (ShellSort) с несколькими стратегиями выбора шага (gap sequence).
 * Работает с любыми типами данных через Comparator<T>.
 */
public class ShellSort {

    /**
     * Типы последовательностей для выбора шага (gap)
     */
    public enum GapType {
        CLASSIC,    // Классическая версия (n/2, n/4, ..., 1)
        KNUTH,      // Последовательность Кнута (1, 4, 13, 40, ...)
        HIBBARD,    // Последовательность Хиббарда (1, 3, 7, 15, ...)
        SEDGEWICK,  // Последовательность Седжвика (701, 301, 132, ...)
        PROPORTIONAL // Эмпирическая (n, n/2.2, n/4.8, ...)
    }

    public static <T> void sort(List<T> list, Comparator<T> cmp, GapType type) {
        int n = list.size();
        int[] sedgewick = {701, 301, 132, 57, 23, 10, 4, 1};

        switch (type) {
            //  Классический алгоритм Шелла (1959)
            case CLASSIC -> {
                for (int gap = n / 2; gap > 0; gap /= 2) {
                    gapSort(list, cmp, gap);
                }
            }

            //  Последовательность Кнута (1, 4, 13, 40, ...)
            case KNUTH -> {
                int gap = 1;
                while (gap < n / 3)
                    gap = 3 * gap + 1;
                for (; gap > 0; gap /= 3)
                    gapSort(list, cmp, gap);
            }

            //  Последовательность Хиббарда (1, 3, 7, 15, ...)
            case HIBBARD -> {
                int gap = 1;
                while (gap < n)
                    gap = 2 * gap + 1;
                for (; gap > 0; gap = (gap - 1) / 2)
                    gapSort(list, cmp, gap);
            }

            //   Последовательность Седжвика (701, 301, 132, ...)
            case SEDGEWICK -> {
                for (int gap : sedgewick) {
                    if (gap < n)
                        gapSort(list, cmp, gap);
                }
            }

            //   Эмпирическая (приблизительно n, n/2.2, n/4.8, ...)
            case PROPORTIONAL -> {
                for (int gap = n; gap > 1; gap = (int) (gap / 2.2)) {
                    gapSort(list, cmp, gap);
                }
                gapSort(list, cmp, 1);
            }
        }
    }

    /**
     * Частичная сортировка элементов с заданным шагом (gap)
     */
    private static <T> void gapSort(List<T> list, Comparator<T> cmp, int gap) {
        int n = list.size();

        for (int i = gap; i < n; i++) {
            T temp = list.get(i);
            int j = i;

            while (j >= gap && cmp.compare(list.get(j - gap), temp) > 0) {
                list.set(j, list.get(j - gap));
                j -= gap;
            }

            list.set(j, temp);
        }
    }
}
