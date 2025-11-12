package team.shellsort.input;

import team.shellsort.model.Car;

import java.util.Collections;
import java.util.List;

public class LoadResult {
    private final List<Car> valid;
    private final List<String> invalid;

    public LoadResult(List<Car> validCars, List<String> invalidLines) {
        this.valid = validCars != null ? validCars : Collections.emptyList();
        this.invalid = invalidLines != null ? invalidLines : Collections.emptyList();
    }

    public List<Car> getValid() {
        return valid;
    }

    public List<String> getInvalid() {
        return invalid;
    }

    public boolean isSuccess() {
        return !valid.isEmpty() && invalid.isEmpty();
    }
}
