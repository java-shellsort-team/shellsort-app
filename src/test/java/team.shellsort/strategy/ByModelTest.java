package team.shellsort.strategy;

import org.junit.jupiter.api.Test;
import team.shellsort.model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByModelTest {
    @Test
    void sortsByModelIgnoreCase_nullsLast_thenByYear_thenByPower() {
        SortStrategy strategy = new ByModel();
        Comparator<Car> cmp = strategy.comparator();

        List<Car> list = new ArrayList<>(TestData.cars());
        list.sort(cmp);

        List<String> expected = List.of(
                "audi:2009:110",
                "Audi:2011:120",
                "BMW:2012:125",
                "BMW:2012:130",
                "bmw:2015:150",
                "citroen:2010:90",
                "null:2009:80"
        );

        assertEquals(expected, toTriples(list));
    }

    private static List<String> toTriples(List<Car> cars) {
        return cars.stream()
                .map(c -> (c.getModel() == null ? "null" : c.getModel()) +
                        ":" + c.getYear() + ":" + c.getPower())
                .toList();
    }
}
