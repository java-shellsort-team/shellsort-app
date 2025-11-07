package team.shellsort.strategy;

import team.shellsort.model.Car;
import java.util.Comparator;

import static team.shellsort.strategy.StrategyUtils.NULL_LAST;

public final class ByModel implements SortStrategy {
    @Override
    public Comparator<Car> comparator() {
        return Comparator.comparing(Car::getModel, NULL_LAST)
                .thenComparingInt(Car::getYear)
                .thenComparingInt(Car::getPower);

    }
    @Override
    public String name() { return "По модели"; }

    @Override
    public SortKey key() {
        return SortKey.BY_MODEL;
    }
}