package team.shellsort.input;

import team.shellsort.model.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDataProvider implements DataProvider {
    @Override
    public LoadResult load(int limit) {
        // TODO: реализовать чтение данных из файла

        List<Car> cars = new ArrayList<>();
        LineParser lineParser = new LineParser();

        try (BufferedReader reader = new BufferedReader(new FileReader("CarList"))) {
            String line;
            String[] arr = null;
            while ((line = reader.readLine()) != null) {

                cars.add(lineParser.parseCar(line));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        List<String> errors = new ArrayList<>();
        return new LoadResult(cars, errors);
    }
}
