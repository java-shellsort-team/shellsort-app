package team.shellsort.input;

import team.shellsort.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

/**
 * Источник данных, генерирующий случайные записи о машинах.
 * Используется для тестирования без внешних источников.
 * Поддерживает детерминированную генерацию при передаче seed.
 */
public class RandomDataProvider implements DataProvider {

    /** Доступные значения для генерации. */
    private static final String[] MODELS = {"Almera", "Focus", "Lancer", "Camry", "Corolla"};
    private static final int[] YEARS = {2009, 2010, 2011, 2012, 2013, 2014, 2015};
    private static final int[] POWERS = {80, 90, 100, 110, 120, 125, 150, 180, 200, 250, 300};

    /** Разделитель для сериализации «битых» строк. */
    private static final String SEP = ";";

    /** Сколько записей генерировать. */
    private final int count;

    /** Генератор случайных чисел (может быть детерминированным при заданном seed). */
    private final RandomGenerator random;

    /** По умолчанию — 10 записей в недетерминированном режиме. */
    public RandomDataProvider() {
        this(10, null);
    }

    /** Конструктор с указанием количества записей. */
    public RandomDataProvider(int count) {
        this(count, null);
    }

    /** Конструктор с количеством записей и опциональным seed. */
    public RandomDataProvider(int count, Long seed) {
        if (count < 0) throw new IllegalArgumentException("count must be >= 0");
        this.count = count;
        this.random = (seed == null) ? ThreadLocalRandom.current() : new Random(seed);
    }

    @Override
    public LoadResult load() {
        List<Car> valid = new ArrayList<>();
        List<String> invalid = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String model = MODELS[random.nextInt(MODELS.length)];
            int year = YEARS[random.nextInt(YEARS.length)];
            int power = POWERS[random.nextInt(POWERS.length)];

            Car car = new Car.CarBuilder()
                    .setModel(model)
                    .setYear(year)
                    .setPower(power)
                    .build();

            if (Validator.isValid(car)) {
                valid.add(car);
            } else {
                invalid.add(model + SEP + power + SEP + year);
            }
        }

        return new LoadResult(valid, invalid);
    }
}