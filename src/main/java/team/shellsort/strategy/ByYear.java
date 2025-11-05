package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public class ByYear implements SortStrategy {
    public Comparator<Car> comparator() {
        return Comparator.comparingInt(Car::getYear)
                .thenComparing(Car::getModel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                .thenComparing(Car::getPower);
    }
    public String name() { return "По году"; }
}
