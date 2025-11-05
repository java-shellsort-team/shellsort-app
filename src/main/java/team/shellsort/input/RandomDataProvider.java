package team.shellsort.input;

import team.shellsort.model.Car;

import java.util.*;
import java.util.Random;

public class RandomDataProvider implements DataProvider {
    @Override
    public LoadResult load(int limit) {
        // TODO: реализовать генерацию случайных данных
        String[] models = {"Almera", "Focus", "Lancer", "Camry", "Corolla"};
        int[] years = {2001, 2002, 2003, 2004, 2005};
        int[] power = {100, 200, 300, 400, 500};

        Car carBuilder = null;
        List<Car> cars = new ArrayList<>();

        int a = 0, b = 0, c = 0;
        while (limit > 0) {
            a = new Random().nextInt(5);
            b = new Random().nextInt(5);
            c = new Random().nextInt(5);

            carBuilder = new Car.CarBuilder()
                    .setModel(models[a])
                    .setYear(years[b])
                    .setPower(power[c])
                    .build();

            cars.add(carBuilder);
            limit--;
        }


        List<String> errors = new ArrayList<>();
        return new LoadResult(cars, errors);
    }
}
