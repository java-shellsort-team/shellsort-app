import team.shellsort.model.Car;
import java.util.List;

public class Validator {

    public boolean isValid(Car car) {
        if (car == null) {
            return false;
        }
        return isModelValid(car.getModel()) &&
                isYearValid(car.getYear()) &&
                isPowerValid(car.getPower());
    }
    public boolean areAllValid(List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            return false;
        }
        return cars.stream().allMatch(this::isValid);
    }

    private boolean isModelValid(String model) {
        // Модель не должна быть пустой и должна быть не длиннее N символов
        return model != null && !model.trim().isEmpty() && model.length() <= 50;
    }

    private boolean isYearValid(int year) {
        // Год должен быть в разумном диапазоне
        final int MIN_YEAR = 1900;
        final int MAX_YEAR = java.time.Year.now().getValue();
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    private boolean isPowerValid(int power) {
        //Мощность должна быть положительной
        return power > 0;
    }
}