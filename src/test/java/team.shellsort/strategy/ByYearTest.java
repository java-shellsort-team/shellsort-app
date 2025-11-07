package team.shellsort.strategy;

import org.junit.jupiter.api.Test;
import team.shellsort.model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByYearTest {
    @Test
    void sortsByYear_thenByModelIgnoreCase_thenByPower() {
        SortStrategy strategy = new ByYear();
        Comparator<Car> cmp = strategy.comparator();

        List<Car> list = new ArrayList<>(TestData.cars());
        list.sort(cmp);

        List<String> expected = List.of(
                "audi:2009:110",
                "null:2009:80",
                "citroen:2010:90",
                "citroen:2010:90",
                "Audi:2011:120",
                "BMW:2012:125",
                "BMW:2012:130",
                "bmw:2015:150"
        );

        assertEquals(expected, TestUtils.toTriples(list));
    }
}
