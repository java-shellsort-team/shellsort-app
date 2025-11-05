package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public class ByModel implements SortStrategy {
    public Comparator<Car> comparator() {
        return Comparator.comparing(Car::getModel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                .thenComparing(Car::getYear)
                .thenComparing(Car::getPower);

    }
    public String name() { return "По модели"; }
}
