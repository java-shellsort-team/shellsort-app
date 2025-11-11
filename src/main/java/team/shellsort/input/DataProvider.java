package team.shellsort.input;

import java.io.IOException;

/**
 * Общий контракт для классов, загружающих данные о машинах
 * (например, из файла или случайным образом).
 */
public interface DataProvider {

    /**
     * Загружает данные о машинах.
     *
     * @return валидные объекты и исходные строки с ошибками
     * @throws IOException для источников I/O (файл/сеть)
     */
    LoadResult load() throws IOException;
}