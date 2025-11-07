package team.shellsort.strategy;

import team.shellsort.model.Car;

import java.util.List;

public final class TestUtils {
    private TestUtils() {}
    public static List<String> toTriples(List<Car> cars) {
        return cars.stream()
                .map(c -> java.util.Objects.toString(c.getModel(), "null")
                        + ":" + c.getYear() + ":" + c.getPower())
                .toList();
    }
}
