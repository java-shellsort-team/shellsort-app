package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public final class ByPower implements SortStrategy {
    @Override
    public Comparator<Car> comparator() {
        return Comparator.comparingInt(Car::getPower)
                .thenComparing(Car::getModel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                .thenComparingInt(Car::getYear);
    }
    @Override
    public String name() { return "По мощности"; }
}
