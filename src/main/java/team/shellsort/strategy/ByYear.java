package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

import static team.shellsort.strategy.StrategyUtils.NULL_LAST;

public final class ByYear implements SortStrategy {
    @Override
    public Comparator<Car> comparator() {
        return Comparator.comparingInt(Car::getYear)
                .thenComparing(Car::getModel, NULL_LAST)
                .thenComparingInt(Car::getPower);
    }
    @Override
    public String name() { return "По году"; }

    @Override
    public SortKey key() {
        return SortKey.BY_YEAR;
    }
}
