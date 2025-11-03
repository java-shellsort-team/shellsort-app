package team.shellsort.input;

import team.shellsort.model.Car;
import java.time.Year;
import java.util.List;

public final class Validator {

    private Validator() {}

    public static boolean isModelValid(String model) {
        return model != null && !model.trim().isEmpty() && model.length() <= 50;
    }

    public static boolean isPowerValid(int power) {
        return power > 0;
    }

    public static boolean isYearValid(int year) {
        int currentYear = Year.now().getValue();
        return year >= 1900 && year <= currentYear;
    }

    public static boolean areAllValid(List<Car> cars) {
        return cars.stream().allMatch(car ->
                isModelValid(car.getModel()) &&
                isPowerValid(car.getPower()) &&
                isYearValid(car.getYear()));
    }
}
