package team.shellsort.input;

import team.shellsort.model.Car;

public interface DataProvider {
    LoadResult<Car> load(int limit);
}
