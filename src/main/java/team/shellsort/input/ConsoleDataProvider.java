package team.shellsort.input;

import team.shellsort.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Источник данных, читающий записи о машинах из консоли.
 * Формат строки: Модель;Мощность;Год
 */
public class ConsoleDataProvider implements DataProvider {

    private static final String STOP_COMMAND = "stop";
    private static final int MAX_ELEMENTS = 100_000;

    private final Scanner scanner;

    public ConsoleDataProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public LoadResult load() {
        System.out.println();
        System.out.println("ВВОД С КОНСОЛИ (Формат: Модель;Мощность;Год)");

        int count = askCountOrStop();
        if (count == -1) {
            System.out.println("Ввод с консоли отменён. Возврат к выбору источника данных.");
            return null;
        }

        System.out.printf("Ожидается %d строк. Для досрочного завершения введите '%s'.%n",
                count, STOP_COMMAND);

        List<Car> valid = new ArrayList<>();
        List<String> invalid = new ArrayList<>();
        LineParser parser = new LineParser();

        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine().trim();

            if (STOP_COMMAND.equalsIgnoreCase(line)) {
                System.out.println("Ввод досрочно завершён по команде '" + STOP_COMMAND + "'.");
                break;
            }

            if (line.isEmpty()) {
                System.out.println("Пустая строка пропущена.");
                continue;
            }

            Car car = parser.parse(line);
            if (Validator.isValid(car)) {
                valid.add(car);
            } else {
                invalid.add(line);
            }
        }

        return new LoadResult(valid, invalid);
    }

    /**
     * Спрашивает количество элементов.
     * Возвращает:
     * - положительное число — если пользователь ввёл корректное значение;
     * - -1 — если пользователь ввёл "stop".
     */
    private int askCountOrStop() {
        while (true) {
            System.out.print("Укажите количество элементов (от 1 до "
                             + MAX_ELEMENTS + ", или '" + STOP_COMMAND + "' для отмены): ");
            String raw = scanner.nextLine().trim();

            if (STOP_COMMAND.equalsIgnoreCase(raw)) {
                return -1;
            }

            int value;
            try {
                value = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число (например, 5). Попробуйте снова.");
                continue;
            }

            if (value <= 0) {
                System.out.println("Ошибка: число должно быть положительным. Попробуйте снова.");
                continue;
            }

            if (value > MAX_ELEMENTS) {
                System.out.println("Слишком большое число. Допустимый максимум: " + MAX_ELEMENTS +
                                   ". Попробуйте снова.");
                continue;
            }

            return value;
        }
    }
}
