package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

public abstract class ByPower implements SortStrategy {
    public Comparator<Car> comparator() { return Comparator.comparingInt(Car::getPower); }
    public String name() { return "По мощности"; }
}
