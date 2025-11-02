package team.shellsort.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testValidCar() {
        Car validCar = new Car("Toyota Corolla", 130, 2020);
        assertTrue(Validator.isValid(validCar), "Валидный автомобиль должен пройти проверку.");
    }

    @Test
    void testInvalidModel() {
        Car emptyModelCar = new Car("", 150, 2020);
        assertFalse(Validator.isValid(emptyModelCar), "Пустая модель должна быть невалидной.");

        String longModel = "A".repeat(51);
        Car longModelCar = new Car(longModel, 150, 2020);
        assertFalse(Validator.isValid(longModelCar), "Слишком длинная модель должна быть невалидной.");
    }

    @Test
    void testInvalidYear() {
        Car oldCar = new Car("Model X", 150, 1899);
        assertFalse(Validator.isValid(oldCar), "Год 1899 должен быть невалидным.");

        Car futureCar = new Car("Model Z", 150, 3000);
        assertFalse(Validator.isValid(futureCar), "Год в будущем должен быть невалидным.");
    }

    @Test
    void testInvalidPower() {
        Car zeroPowerCar = new Car("Model Y", 0, 2020);
        assertFalse(Validator.isValid(zeroPowerCar), "Мощность 0 должна быть невалидной.");

        Car negativePowerCar = new Car("Model Y", -5, 2020);
        assertFalse(Validator.isValid(negativePowerCar), "Отрицательная мощность должна быть невалидной.");
    }
}