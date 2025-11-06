package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public final class ByModel implements SortStrategy {
    @Override
    public Comparator<Car> comparator() {
        return Comparator.comparing(Car::getModel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                .thenComparingInt(Car::getYear)
                .thenComparingInt(Car::getPower);

    }
    @Override
    public String name() { return "По модели"; }
}
