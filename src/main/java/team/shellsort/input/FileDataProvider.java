package team.shellsort.input;

import team.shellsort.model.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDataProvider implements DataProvider {
    private static final String FILENAME = "CarList";
    @Override
    public LoadResult load() {
        // TODO: реализовать чтение данных из файла

        List<Car> listCars = new ArrayList<>();
        List<String> invalidLines = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {


            while ((line = reader.readLine()) != null) {

                if (Validator.isValid(LineParser.parse(line))){
                    listCars.add(LineParser.parse(line));
                }else {
                    invalidLines.add(line);
                }

            }
        } catch (IOException e) {
            invalidLines.add(e.getMessage());
        }

        return new LoadResult(listCars, invalidLines);
    }

}
