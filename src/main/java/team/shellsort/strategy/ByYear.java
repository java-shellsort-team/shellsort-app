package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public class ByYear implements SortStrategy {
    public Comparator<Car> comparator() { return Comparator.comparingInt(Car::getYear); }
    public String name() { return "По году"; }
}
