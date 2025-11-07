package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

import static team.shellsort.strategy.StrategyUtils.NULL_LAST;

public final class ByPower implements SortStrategy {
    @Override
    public Comparator<Car> comparator() {
        return Comparator.comparingInt(Car::getPower)
                .thenComparing(Car::getModel, NULL_LAST)
                .thenComparingInt(Car::getYear);
    }
    @Override
    public String name() { return "По мощности"; }

    @Override
    public SortKey key() {
        return SortKey.BY_POWER;
    }
}
