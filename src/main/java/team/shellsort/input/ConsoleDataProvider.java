package team.shellsort.input;

import team.shellsort.model.Car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleDataProvider implements DataProvider {

    private static final String STOP_COMMAND = "stop";
    private final Scanner scanner;

    public ConsoleDataProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public LoadResult load() throws IOException {
        List<Car> validCars = new ArrayList<>();
        List<String> invalidLines = new ArrayList<>();
        LineParser parser = new LineParser();

        System.out.println("-----------------------------------------------------");
        System.out.println("ВВОД С КОНСОЛИ (формат строки: Модель;Мощность;Год)");
        System.out.printf("Введите количество элементов (или '%s' для выхода):%n", STOP_COMMAND);

        // Читаем первую строку: либо число, либо команда выхода
        String first = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        if (first.equalsIgnoreCase(STOP_COMMAND)) {
            return new LoadResult(validCars, invalidLines);
        }

        int count;
        try {
            count = Integer.parseInt(first);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: ожидалось число. Завершение ввода.");
            return new LoadResult(validCars, invalidLines);
        }

        System.out.printf("Ожидается %d строк. Для досрочного завершения вводите '%s'.%n", count, STOP_COMMAND);

        for (int i = 0; i < count; i++) {
            System.out.printf("Введите строку %d/%d: ", i + 1, count);
            if (!scanner.hasNextLine()) break;

            String line = scanner.nextLine();
            if (line == null || line.trim().equalsIgnoreCase(STOP_COMMAND)) {
                System.out.println("Ввод досрочно завершён командой 'stop'.");
                break;
            }

            Car car = parser.parse(line);
            if (car != null) {
                validCars.add(car);
            } else {
                invalidLines.add(line);
            }
        }

        return new LoadResult(validCars, invalidLines);
    }
}
