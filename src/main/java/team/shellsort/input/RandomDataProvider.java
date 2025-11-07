package team.shellsort.input;

import team.shellsort.model.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataProvider implements DataProvider {
    private static final String[] models = {"Almera", "Focus", "Lancer", "Camry", "Corolla"};
    private static final int[] years = {2, 2002, 203, 2004, 2005};
    private static final int[] power = {100, 200, 300, 400, 0};

    @Override
    public LoadResult load() {
        // TODO: реализовать генерацию случайных данных


        List<Car> listCars = new ArrayList<Car>();
        List<String> invalidLines = new ArrayList<>();
        Car carBuilder;

        int a = 0, b = 0, c = 0, count = years.length;
        while (count > 0) {
            a = new Random().nextInt(5);
            b = new Random().nextInt(5);
            c = new Random().nextInt(5);

            carBuilder = new Car.CarBuilder()
                    .setModel(models[a])
                    .setYear(years[b])
                    .setPower(power[c])
                    .build();

            if (Validator.isValid(carBuilder)){
                listCars.add(carBuilder);
            }else {
                invalidLines.add(carBuilder.getModel() + ";" + carBuilder.getYear() + ";" + carBuilder.getPower());
            }
            count--;
        }

        return new LoadResult(listCars, invalidLines);
    }
}
