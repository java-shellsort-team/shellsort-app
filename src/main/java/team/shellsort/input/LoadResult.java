package team.shellsort.input;

import java.util.Collections;
import java.util.List;

public class LoadResult {
    private final List<Car> validCars;
    private final List<String> invalidLines;

    public LoadResult(List<Car> validCars, List<String> invalidLines) {
        this.validCars = validCars != null ? validCars : Collections.emptyList();
        this.invalidLines = invalidLines != null ? invalidLines : Collections.emptyList();
    }

    public List<Car> getValidCars() {
        return validCars;
    }

    public List<String> getInvalidLines() {
        return invalidLines;
    }

    public boolean isSuccess() {
        return !validCars.isEmpty() && invalidLines.isEmpty();
    }
}