package team.shellsort.input;

import team.shellsort.model.Car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleDataProvider implements DataProvider {

    @Override
    public LoadResult load() {

        List<Car> listCars = new ArrayList<Car>();
        List<String> invalidLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Для выхода введите \"stop\"");
            while (scanner.hasNextLine()) {
                System.out.println("Формат ввода \"model;power;year\"");

                String line = scanner.nextLine();

                if (line.isEmpty() || line.equals("stop")) {
                    break;
                }

                if (Validator.isValid(LineParser.parse(line))){
                    listCars.add(LineParser.parse(line));
                }else {
                    invalidLines.add(line);
                }

            }
        }
        return new LoadResult(listCars, invalidLines);
    }
}
