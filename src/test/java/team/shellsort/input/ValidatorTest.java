package team.shellsort.input;

import org.junit.jupiter.api.Test;
import team.shellsort.model.Car;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testValidCar() {
        Car validCar = new Car.CarBuilder().setModel("Toyota Corolla").setPower(130).setYear(2020).build();
        assertTrue(Validator.isValid(validCar), "Валидный автомобиль должен пройти проверку.");
    }

    @Test
    void testInvalidModel() {
        Car emptyModelCar = new Car.CarBuilder().setModel(" ").setPower(130).setYear(2020).build();
        assertFalse(Validator.isValid(emptyModelCar), "Пустая модель должна быть невалидной.");

        String longModel = "A".repeat(51);
        Car longModelCar = new Car.CarBuilder().setModel(longModel).setPower(130).setYear(2020).build();
        assertFalse(Validator.isValid(longModelCar), "Слишком длинная модель должна быть невалидной.");
    }

    @Test
    void testInvalidYear() {
        Car oldCar = new Car.CarBuilder().setModel("Toyota Corolla").setPower(130).setYear(1899).build();
        assertFalse(Validator.isValid(oldCar), "Год 1899 должен быть невалидным.");

        Car futureCar = new Car.CarBuilder().setModel("Toyota Corolla").setPower(130).setYear(3000).build();
        assertFalse(Validator.isValid(futureCar), "Год в будущем должен быть невалидным.");
    }

    @Test
    void testInvalidPower() {
        Car zeroPowerCar = new Car.CarBuilder().setModel("Toyota Corolla").setPower(0).setYear(2020).build();
        assertFalse(Validator.isValid(zeroPowerCar), "Мощность 0 должна быть невалидной.");

        Car negativePowerCar = new Car.CarBuilder().setModel("Toyota Corolla").setPower(-5).setYear(2020).build();
        assertFalse(Validator.isValid(negativePowerCar), "Отрицательная мощность должна быть невалидной.");
    }

    @Test
    void testBoundaryModelLength() {
        String invalidMaxModel = "A".repeat(Validator.MAX_MODEL_LENGTH + 1);
        Car overMaxLenCar = new Car.CarBuilder().setModel(invalidMaxModel).setPower(100).setYear(2000).build();
        assertFalse(Validator.isValid(overMaxLenCar), "Модель длиной > MAX_MODEL_LENGTH должна быть невалидной.");
    }

    @Test
    void testBoundaryYear() {
        Car minInvalidYearCar = new Car.CarBuilder().setModel("Toyota").setPower(100).setYear(Validator.MIN_YEAR - 1).build();
        assertFalse(Validator.isValid(minInvalidYearCar), "Год на 1 меньше 1900 должен быть невалидным.");

        Car maxInvalidYearCar = new Car.CarBuilder().setModel("Toyota").setPower(100).setYear(Validator.MAX_YEAR + 1).build();
        assertFalse(Validator.isValid(maxInvalidYearCar), "Следующий год должен быть невалидным.");
    }

    @Test
    void testBoundaryPower() {

        Car minValidPowerCar = new Car.CarBuilder().setModel("Toyota").setPower(1).setYear(2020).build();
        assertTrue(Validator.isValid(minValidPowerCar), "Мощность 1 должна быть валидной.");

        Car negativePowerCar = new Car.CarBuilder().setModel("Toyota").setPower(-1).setYear(2020).build();
        assertFalse(Validator.isValid(negativePowerCar), "Отрицательная мощность должна быть невалидной.");
    }
}