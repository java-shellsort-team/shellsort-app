package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public interface SortStrategy {
    Comparator<Car> comparator();
    String name();
}

