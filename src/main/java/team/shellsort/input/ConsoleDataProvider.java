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
        System.out.println("ВВОД С КОНСОЛИ (Формат: Модель;Мощность;Год)");
        System.out.println("Укажите количество элементов (например, 5):");

        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введено неверное число.");
            return new LoadResult(validCars, invalidLines);
        }

        System.out.printf("Ожидается %d строк. Для досрочного завершения введите '%s'.\n", count, STOP_COMMAND);

        // 2. Построчный ввод
        for (int i = 0; i < count; i++) {
            System.out.printf("Введите строку %d/%d: ", i + 1, count);

            // Проверка, есть ли еще ввод (на всякий случай)
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine();

            if (line == null || line.trim().equalsIgnoreCase(STOP_COMMAND)) {
                System.out.println("Ввод досрочно завершен командой 'stop'.");
                break;
            }

            Car car = parser.parse(line);
            if (car != null && Validator.isValid(car)) {
                validCars.add(car);
            } else {
                invalidLines.add(line);
            }
        }

        return new LoadResult(validCars, invalidLines);
    }
}
