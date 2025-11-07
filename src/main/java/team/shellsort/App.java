package team.shellsort;

import team.shellsort.model.Car;
import team.shellsort.strategy.ByModel;
import team.shellsort.strategy.ByPower;
import team.shellsort.strategy.ByYear;
import team.shellsort.strategy.SortStrategy;
// TODO (интеграция ввода): раскомментировать, когда будут готовы провайдеры
// import team.shellsort.input.DataProvider;
// import team.shellsort.input.ConsoleDataProvider;
// import team.shellsort.input.FileDataProvider;
// import team.shellsort.input.RandomDataProvider;
// import team.shellsort.input.LineParser;
// import team.shellsort.input.LoadResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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

            int limit = askInt("Введите количество элементов (1..1000): ", 1, 1000);
            SortStrategy strategy = askStrategy();

            // ===== Загрузка данных =====
            // TODO (Давид + Артём): заменить mock на реальные провайдеры после интеграции Validator’а
            // Пример будущего кода:
            // DataProvider dp = switch (source) {
            //     case 1 -> new ConsoleDataProvider(SC, new LineParser());
            //     case 2 -> new FileDataProvider(/* path или запрос пути */);
            //     case 3 -> new RandomDataProvider(/* seed? */);
            //     case 4 -> new MockDataProviderAdapter(); // если захотим оставить mock
            //     default -> throw new IllegalStateException("Неожиданный источник: " + source);
            // };
            // LoadResult lr = dp.load(); // либо dp.load(limit), если финальная сигнатура с лимитом
            // List<Car> cars = lr.validCars(); // имя метода — как договоримся
            List<Car> cars = loadMockData(limit);

            // ===== Сортировка =====
            cars.sort(strategy.comparator());

            // ===== Вывод =====
            printCars(cars);

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
        System.out.println("  4) Mock (временные данные)");
        System.out.println("  0) Выход");
        return askInt("Ваш выбор: ", 0, 4);
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
        System.out.println("  1) По модели (ignore case, nulls last) -> год -> мощность");
        System.out.println("  2) По мощности -> модель (ignore case) -> год");
        System.out.println("  3) По году -> модель (ignore case) -> мощность");
        int choice = askInt("Ваш выбор: ", 1, 3);
        return switch (choice) {
            case 1 -> new ByModel();
            case 2 -> new ByPower();
            case 3 -> new ByYear();
            default -> throw new IllegalStateException("Неожиданный выбор: " + choice);
        };
    }

    /**
     * Создаёт и возвращает список тестовых объектов {@code Car}.
     *
     * <p>Метод используется временно, пока не подключены реальные провайдеры.
     * Значения генерируются случайно из фиксированных наборов.
     *
     * @param n желаемое количество элементов
     * @return список сгенерированных объектов {@code Car}
     */
    private static List<Car> loadMockData(int n) {
        return MockDataProvider.of(n).load();
    }

    /**
     * Выводит список автомобилей в консоль в удобочитаемом формате.
     *
     * <p>Каждый элемент выводится на новой строке в виде:
     * <pre>
     *  1) Машина модели Audi, год выпуска 2011, л.с. 120
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

    // ===== Временный мок-провайдер вместо настоящих DataProvider’ов =====

    /**
     * Простой генератор списка {@code Car} для локального запуска и демонстрации.
     * Заменяется реальными DataProvider’ами после интеграции ввода и валидации.
     */
    private static final class MockDataProvider {
        private final int n;

        private MockDataProvider(int n) { this.n = n; }

        static MockDataProvider of(int n) { return new MockDataProvider(n); }

        List<Car> load() {
            List<Car> list = new ArrayList<>(n);
            String[] models = {"Audi", "BMW", "bmw", "citroen", "Lada", null, "Toyota", "audi"};
            int[] years = {2009, 2010, 2011, 2012, 2015, 2005, 2018, 2020};
            int[] powers = {80, 90, 110, 120, 125, 130, 150, 100};

            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            for (int i = 0; i < n; i++) {
                String m = models[rnd.nextInt(models.length)];
                int y = years[rnd.nextInt(years.length)];
                int p = powers[rnd.nextInt(powers.length)];
                Car car = new Car.CarBuilder()
                        .setModel(m)
                        .setYear(y)
                        .setPower(p)
                        .build();
                list.add(car);
            }
            return list;
        }
    }
}
