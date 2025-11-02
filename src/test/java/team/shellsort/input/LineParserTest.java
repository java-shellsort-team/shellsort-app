package team.shellsort.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LineParserTest {

    private final LineParser parser = new LineParser();


    @Test
    void testSuccessfulParse() {
        String validLine = "BMW 320i;150;2018";
        Car car = parser.parse(validLine);

        assertNotNull(car, "Парсинг валидной строки не должен возвращать null.");
        assertEquals("BMW 320i", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(2018, car.getYear());
    }

    @Test
    void testIncorrectDelimiterCount() {
        String invalidLine = "BMW 320i;150"; // Не хватает третьего элемента
        assertNull(parser.parse(invalidLine), "Неправильное количество разделителей должно вернуть null.");
    }

    @Test
    void testNumberFormatException() {
        String invalidLine = "Lada Granta;СТО;2011"; // "СТО" вместо числа
        assertNull(parser.parse(invalidLine), "Нечисловое значение мощности должно вернуть null.");
    }

    @Test
    void testValidationFailure() {
        String invalidLine = "Retro Car;50;1850";
        assertNull(parser.parse(invalidLine), "Строка, не прошедшая Validator, должна вернуть null.");
    }
}