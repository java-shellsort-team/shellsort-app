package team.shellsort.input;
import team.shellsort.model.Car;

import java.util.List;

public final class Validator {

    final static int MIN_YEAR = 1900;
    final static int MAX_MODEL_LENGTH = 50;
    final static int MIN_POWER = 0;
    final static int MAX_YEAR = java.time.Year.now().getValue();

    public static boolean isValid(Car car) {
        if (car == null) {
            return false;
        }
        return isModelValid(car.getModel()) &&
                isYearValid(car.getYear()) &&
                isPowerValid(car.getPower());
    }
    public static boolean areAllValid(List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            return false;
        }
        return cars.stream().allMatch(Validator::isValid);
    }

    /**
     * Модель не должна быть пустой и должна быть не длиннее N символов
     */
    public static boolean isModelValid(String model) {
        if (model == null) {
            return false;
        }
        String trimmedModel = model.trim();
        return !trimmedModel.isEmpty() && trimmedModel.length() <= MAX_MODEL_LENGTH;
    }

    /**
     * Год должен быть в разумном диапазоне
     */
    public static boolean isYearValid(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    /**
     * Мощность должна быть положительной
     */
    public static boolean isPowerValid(int power) {
        return power > MIN_POWER;
    }
}
