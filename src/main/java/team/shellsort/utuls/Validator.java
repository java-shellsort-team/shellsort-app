import java.util.List;

public final class Validator {

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

    /**
     * Модель не должна быть пустой и должна быть не длиннее N символов
     */
    private boolean isModelValid(String model) {
        return model != null && !model.trim().isEmpty() && model.length() <= 50;
    }

    /**
     * Год должен быть в разумном диапазоне
     */
    private boolean isYearValid(int year) {
        final int MIN_YEAR = 1900;
        final int MAX_YEAR = java.time.Year.now().getValue();
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    /**
     * Мощность должна быть положительной
     */
    private boolean isPowerValid(int power) {
        return power > 0;
    }
}