package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public abstract class ByModel implements SortStrategy {
    public Comparator<Car> comparator() { return Comparator.comparing(Car::getModel); }
    public String name() { return "По модели"; }
}
