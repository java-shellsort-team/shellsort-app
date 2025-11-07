package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

/**
 * Общий контракт для стратегий сортировки.
 * Возвращает Comparator<Car> и связан с SortKey.
 */
public interface SortStrategy {

    Comparator<Car> comparator();

    String name();

    SortKey key();
}
