package team.shellsort;

import team.shellsort.model.Car;
import team.shellsort.sort.ShellSort;
import team.shellsort.strategy.*;
import team.shellsort.input.DataProvider;
import team.shellsort.input.ConsoleDataProvider;
import team.shellsort.input.FileDataProvider;
import team.shellsort.input.RandomDataProvider;
import team.shellsort.input.LoadResult;

import java.io.IOException;
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
 *   <li>Загрузить данные (пока через mock, позже — через DataProvider’ы);</li>
 *   <li>Отсортировать список объектов {@code Car} выбранной стратегией;</li>
 *   <li>Вывести результат и предложить повтор.</li>
 * </ul>
 *
 * <p>Точки интеграции помечены {@code TODO}.
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
        System.out.println("=== ShellSort App (черновик интеграции) ===");
        runMenuLoop();
        System.out.println("Завершено. До встречи!");
    }

    /**
     * Основной цикл программы — отображает меню, обрабатывает выбор пользователя
     * и выполняет соответствующие действия (загрузка данных, сортировка, вывод и т.д.).
     *
     * <p>Цикл продолжается, пока пользователь не выберет пункт «Выход».
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
     * <p>Пользователь может выбрать:
     * <ul>
     *   <li>1 — ввод из консоли;</li>
     *   <li>2 — чтение из файла;</li>
     *   <li>3 — генерация случайных данных;</li>
     *   <li>4 — mock-данные (временные тестовые значения);</li>
     *   <li>0 — выход из программы.</li>
     * </ul>
     *
     * @return номер выбранного пункта меню (0–4)
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
     * <p>В зависимости от выбранного источника (1-консоль, 2-файл, 3-рандом) создаётся
     * соответствующий провайдер данных. Загруженные данные сортируются по выбранной
     * стратегии и выводятся пользователю.
     *
     * <p>В случае ошибки ввода-вывода пробрасывается исключение и управление
     * возвращается в главный цикл для повторной попытки.
     *
     * @param source идентификатор источника данных (1, 2, 3)
     */
    private static void processData(int source) {
        DataProvider dp = switch (source) {
            case 1 -> new ConsoleDataProvider(SC);
            // TODO: как считывать с определённого файла
            case 2 -> new FileDataProvider();
            // TODO: нужен ли управляемый рандом... думаю, что нет
            case 3 -> new RandomDataProvider();
            default -> throw new IllegalStateException("Неожиданный источник: " + source);
        };

        try {
            LoadResult lr = dp.load();
            List<Car> cars = lr.getValid();

            // ===== Сортировка =====
            SortStrategy strategy = askStrategy();
            SortDirection direction = askDirection();

            Comparator<Car> comparator = strategy.comparator();
            if (direction == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            ShellSort.sort(cars, comparator, ShellSort.GapType.CLASSIC);

            // ===== Вывод данных =====
            printCars(cars);

        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
        }
    }

    /**
     * Отображает меню выбора стратегии сортировки и возвращает выбранный вариант.
     *
     * <p>Доступные варианты:
     * <ul>
     *   <li>1 — сортировка по модели (ignore case, nulls last) → год → мощность;</li>
     *   <li>2 — сортировка по мощности → модель (ignore case) → год;</li>
     *   <li>3 — сортировка по году → модель (ignore case) → мощность.</li>
     * </ul>
     *
     * @return выбранная стратегия сортировки
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
     * Отображает меню выбора с сортировкой по убыванию или возрастанию
     *
     * <p>Доступные варианты:
     * <ul>
     *   <li>1 — сортировка по возрастанию</li>
     *   <li>2 — сортировка по убыванию</li>
     * </ul>
     *
     * @return выбранная стратегия сортировки
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
     * Выводит список автомобилей в консоль в удобочитаемом формате.
     *
     * <p>Каждый элемент выводится на новой строке в виде:
     * <pre>
     *  1) Машина {Модель='Audi', Год=2019, л.с.=200}
     * </pre>
     * Если список пуст — выводится сообщение об отсутствии данных.
     *
     * @param cars список автомобилей для вывода
     */
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

    /**
     * Запрашивает у пользователя целое число в указанном диапазоне.
     *
     * <p>Метод выполняет валидацию ввода: повторяет запрос,
     * пока не будет введено корректное значение в пределах {@code [min, max]}.
     * Если пользователь вводит некорректные данные — отображается сообщение об ошибке.
     *
     * @param prompt текст запроса, отображаемый пользователю
     * @param min    минимально допустимое значение
     * @param max    максимально допустимое значение
     * @return введённое пользователем число
     */
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

    /**
     * Запрашивает у пользователя ответ «да/нет».
     *
     * @param prompt текст запроса
     * @return {@code true}, если пользователь ответил «y/yes/д/да» (без учёта регистра),
     *         иначе {@code false} для «n/no/н/нет»
     */
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
