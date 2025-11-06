package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public final class ByYear implements SortStrategy {
    @Override
    public Comparator<Car> comparator() {
        return Comparator.comparingInt(Car::getYear)
                .thenComparing(Car::getModel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                .thenComparingInt(Car::getPower);
    }
    @Override
    public String name() { return "По году"; }
}
