package team.shellsort.input;

import team.shellsort.model.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Источник данных, читающий записи о машинах из ресурса в classpath.
 * <p>Формат строк: <b>Модель;Мощность;Год</b> (UTF-8). Пустые строки игнорируются.</p>
 */
public class FileDataProvider implements DataProvider {

    private static final String DEFAULT_RESOURCE = "CarList.txt";

    private final Supplier<BufferedReader> readerSupplier;

    private static final LineParser PARSER = new LineParser();

    /** По умолчанию читаем из ресурса {@code CarList.txt}. */
    public FileDataProvider() {
        this(DEFAULT_RESOURCE);
    }

    /** Чтение из указанного ресурса classpath. */
    public FileDataProvider(String resourceName) {
        this.readerSupplier = () -> {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
            if (is == null) throw new UncheckedIOException(new IOException("Resource not found: " + resourceName));
            return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        };
    }

    /** Удобно для тестов: подаём внешний {@link Reader}. */
    public FileDataProvider(Reader reader) {
        this.readerSupplier = () -> new BufferedReader(reader);
    }

    @Override
    public LoadResult load() throws IOException {
        List<Car> valid = new ArrayList<>();
        List<String> invalid = new ArrayList<>();

        try (BufferedReader br = readerSupplier.get()) {
            for (String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Car car = PARSER.parse(line);
                if (Validator.isValid(car)) valid.add(car);
                else invalid.add(line);
            }
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }

        return new LoadResult(valid, invalid);
    }
}