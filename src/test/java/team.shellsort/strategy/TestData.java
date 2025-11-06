package team.shellsort.strategy;

import team.shellsort.model.Car;

import java.util.List;

public class TestData {
    private TestData() {}

    static public List<Car> cars() {
        return List.of(
                new Car.CarBuilder().setModel("Audi").setYear(2011).setPower(120).build(),
                new Car.CarBuilder().setModel("bmw").setYear(2015).setPower(150).build(),
                new Car.CarBuilder().setModel("BMW").setYear(2012).setPower(130).build(),
                new Car.CarBuilder().setModel("citroen").setYear(2010).setPower(90).build(),
                new Car.CarBuilder().setModel(null).setYear(2009).setPower(80).build(),
                new Car.CarBuilder().setModel("audi").setYear(2009).setPower(110).build(),
                new Car.CarBuilder().setModel("BMW").setYear(2012).setPower(125).build()
        );
    }
}
