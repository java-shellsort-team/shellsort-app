package team.shellsort.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarBuilder() {
        Car car = new Car.CarBuilder()
                .setModel("Lada Granta")
                .setYear(2011)
                .setPower(106)
                .build();

        assertNotNull(car);
        assertEquals("Lada Granta", car.getModel());
        assertEquals(2011, car.getYear());
        assertEquals(106, car.getPower());
    }

    @Test
    void testCarEquality() {
        Car car1 = new Car.CarBuilder()
                .setModel("BMW X5")
                .setYear(2020)
                .setPower(300)
                .build();

        Car car2 = new Car.CarBuilder()
                .setModel("BMW X5")
                .setYear(2020)
                .setPower(300)
                .build();

        Car car3 = new Car.CarBuilder()
                .setModel("Audi A4")
                .setYear(2020)
                .setPower(300)
                .build();

        assertEquals(car1, car2);
        assertNotEquals(car1, car3);
        assertEquals(car1.hashCode(), car2.hashCode());
    }

    @Test
    void testCarInequalityWithDifferentFields() {
        Car baseCar = new Car.CarBuilder()
                .setModel("Toyota Camry")
                .setYear(2018)
                .setPower(200)
                .build();

        Car differentModel = new Car.CarBuilder()
                .setModel("Honda Accord")
                .setYear(2018)
                .setPower(200)
                .build();

        Car differentYear = new Car.CarBuilder()
                .setModel("Toyota Camry")
                .setYear(2019)
                .setPower(200)
                .build();

        Car differentPower = new Car.CarBuilder()
                .setModel("Toyota Camry")
                .setYear(2018)
                .setPower(250)
                .build();

        assertNotEquals(baseCar, differentModel);
        assertNotEquals(baseCar, differentYear);
        assertNotEquals(baseCar, differentPower);
    }
}