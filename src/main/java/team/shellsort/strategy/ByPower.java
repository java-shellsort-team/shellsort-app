package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public class ByPower implements SortStrategy {
    public Comparator<Car> comparator() {
        return Comparator.comparingInt(Car::getPower)
                .thenComparing(Car::getModel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                .thenComparing(Car::getYear);
    }
    public String name() { return "По мощности"; }
}
