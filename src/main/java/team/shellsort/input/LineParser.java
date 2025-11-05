package team.shellsort.input;

import team.shellsort.model.Car;

public class LineParser {
    public Car parseCar(String line) {
        // TODO: реализовать парсинг строки вида "model;power;year"

        Car.CarBuilder carBuilder = new Car.CarBuilder();
        String[] arr = line.split(" ");
        arr = line.split(" ");

        try {
            if (Validator.isModelValid(arr[0])) {
                carBuilder.setModel(arr[0]);
            } else {
                System.out.println("Модель не прошла валидность: " + arr[0]);
            }
            if (Validator.isPowerValid(Integer.parseInt(arr[1]))) {
                carBuilder.setPower(Integer.parseInt(arr[1]));
            } else {
                System.out.println("Год не прошел валидность: " + arr[1]);
            }
            if (Validator.isYearValid(Integer.parseInt(arr[2]))) {
                carBuilder.setYear(Integer.parseInt(arr[2]));
            } else {
                System.out.println("Мощность не прошла валидность: " + arr[2]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return carBuilder.build();
    }
}
