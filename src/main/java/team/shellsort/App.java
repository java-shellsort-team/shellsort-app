package team.shellsort;

import team.shellsort.input.*;
import team.shellsort.model.Car;
import team.shellsort.sort.ShellSort;
import team.shellsort.strategy.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Точка входа и интеграционный каркас приложения ShellSort App.
 *
 * <p>Задачи класса:
 * <ul>
 *   <li>Отобразить меню и собрать пользовательский выбор;</li>
 *   <li>Загрузить данные из выбранного источника;</li>
 *   <li>Отсортировать список объектов {@link Car} выбранной стратегией;</li>
 *   <li>Вывести результат и предложить повтор.</li>
 * </ul>
 */
public class App {

    private static final Scanner SC = new Scanner(System.in);

    /**
     * Точка входа в приложение.
     *
     * <p>Инициализирует необходимые компоненты и запускает главный цикл меню.
     * Перед выходом из программы отображает сообщение о завершении работы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        System.out.println("=== ShellSort App ===");
        runMenuLoop();
        System.out.println("Завершено. До встречи!");
    }

    /**
     * Основной цикл программы — отображает меню, обрабатывает выбор пользователя
     * и выполняет соответствующие действия (загрузка данных, сортировка, вывод и т.д.).
     *
     * <p>Цикл продолжается, пока пользователь не выберет пункт «Выход».</p>
     */
    private static void runMenuLoop() {
        while (true) {
            int source = askSource();
            if (source == 0) {
                break;
            }

            processData(source);

            if (!askYesNo("Ещё раз? (y/n): ")) {
                break;
            }
        }
    }

    /**
     * Отображает меню выбора источника данных и возвращает выбранный пункт.
     *
     * <p>Пользователь может выбрать 1–3 или 0 для выхода.</p>
     *
     * @return номер выбранного пункта меню (0–3)
     */
    private static int askSource() {
        System.out.println();
        System.out.println("Выберите источник данных:");
        System.out.println("  1) Консоль");
        System.out.println("  2) Файл");
        System.out.println("  3) Случайные данные");
        System.out.println("  0) Выход");
        return askInt("Ваш выбор: ", 0, 3);
    }

    /**
     * Выполняет полный цикл обработки данных: загрузку, сортировку и вывод.
     *
     * <p>В зависимости от выбранного источника (1 — консоль, 2 — файл, 3 — рандом)
     * создаётся соответствующий провайдер данных. Загруженные данные сортируются
     * по выбранной стратегии и выводятся пользователю.</p>
     *
     * @param source идентификатор источника данных (1, 2, 3)
     */
    private static void processData(int source) {
        DataProvider dp = switch (source) {
            case 1 -> new ConsoleDataProvider(SC);
            case 2 -> {
                if (askYesNo("Использовать файл по умолчанию? (y/n): ")) {
                    yield new FileDataProvider();
                }
                String path = askFilePath();
                if (path == null) {
                    // пользователь отменил ввод — возвращаемся в меню
                    yield null;
                }
                yield new FileDataProvider(path);
            }
            case 3 -> new RandomDataProvider(
                    askInt("Введите количество генерируемых автомобилей: ", 1, 1000)
            );
            default -> throw new IllegalStateException("Неожиданный источник: " + source);
        };

        if (dp == null) {
            return; // ввод файла отменён пользователем
        }

        try {
            LoadResult lr = dp.load();

               if (lr == null) {
                return;
            }

            List<Car> cars = lr.getValid();

            // Если после загрузки нет валидных данных — нет смысла спрашивать стратегию
            if (cars == null || cars.isEmpty()) {
                System.out.println();
                System.out.println("Список автомобилей пуст. Возврат к выбору источника данных.");
                return;
            }

            // ===== Сортировка =====
            SortStrategy strategy = askStrategy();
            SortDirection direction = askDirection();

            Comparator<Car> comparator = strategy.comparator();
            if (direction == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            ShellSort.sort(cars, comparator, ShellSort.GapType.CLASSIC);

            // ===== Вывод валидных данных =====
            printCars(cars);

            // ===== Вывод невалидных данных, если таковые имеются =====
            List<String> invalidCars = lr.getInvalid();
            if (invalidCars != null && !invalidCars.isEmpty()) {
                printInvalidCars(invalidCars);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
        }
    }


    /**
     * Отображает меню выбора стратегии сортировки и возвращает выбранный вариант.
     */
    private static SortStrategy askStrategy() {
        System.out.println();
        System.out.println("Выберите стратегию сортировки:");
        System.out.println("  1) По модели -> год -> мощность");
        System.out.println("  2) По мощности -> модель -> год");
        System.out.println("  3) По году -> модель -> мощность");
        int choice = askInt("Ваш выбор: ", 1, 3);
        return switch (choice) {
            case 1 -> new ByModel();
            case 2 -> new ByPower();
            case 3 -> new ByYear();
            default -> throw new IllegalStateException("Неожиданный выбор: " + choice);
        };
    }

    /**
     * Отображает меню выбора направления сортировки (возрастание/убывание).
     */
    private static SortDirection askDirection() {
        System.out.println();
        System.out.println("Выберите сортировку по возрастанию или убыванию:");
        System.out.println("  1) По возрастанию");
        System.out.println("  2) По убыванию");

        int choice = askInt("Ваш выбор: ", 1, 2);
        return choice == 1 ? SortDirection.ASCENDING : SortDirection.DESCENDING;
    }

    /**
     * Запрашивает путь к файлу для загрузки данных.
     *
     * @return корректный путь к существующему файлу или {@code null},
     * если пользователь отменил ввод (ввёл {@code stop})
     */
    private static String askFilePath() {
        while (true) {
            System.out.print("Введите путь к файлу: ");
            String filePath = SC.nextLine().trim();

            if (filePath.isEmpty()) {
                System.out.println("Путь не может быть пустым. Попробуйте снова");
                continue;
            }

            if ("stop".equalsIgnoreCase(filePath)) {
                System.out.println("Выбор файла отменён.");
                return null;
            }

            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                return filePath;
            } else {
                System.out.println("Файл не найден: " + filePath);
                System.out.println("Попробуйте снова или введите 'stop'");
            }
        }
    }

    private static void printCars(List<Car> cars) {
        System.out.println();
        if (cars == null || cars.isEmpty()) {
            System.out.println("Список автомобилей пуст.");
            return;
        }
        System.out.println("Результат сортировки (" + cars.size() + " шт.):");
        for (int i = 0; i < cars.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, cars.get(i));
        }
        System.out.println();
    }

    private static void printInvalidCars(List<String> cars) {
        System.out.println("!!! Элементы, не прошедшие валидацию (" + cars.size() + " шт.) !!!");
        for (int i = 0; i < cars.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, cars.get(i));
        }
        System.out.println();
    }

    private static int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.printf("Введите число в диапазоне [%d..%d].%n", min, max);
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное целое число.");
            }
        }
    }

    private static boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim().toLowerCase(Locale.ROOT);
            if (s.equals("y") || s.equals("yes") || s.equals("д") || s.equals("да")) return true;
            if (s.equals("n") || s.equals("no") || s.equals("н") || s.equals("нет")) return false;
            System.out.println("Ответьте 'y' или 'n'.");
        }
    }
}
