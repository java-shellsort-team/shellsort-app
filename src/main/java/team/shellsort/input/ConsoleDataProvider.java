package team.shellsort.input;

import team.shellsort.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleDataProvider implements DataProvider {
    @Override
    public LoadResult load(int limit) {
        // TODO: реализовать ввод из консоли
        Scanner scanner = new Scanner(System.in);
        List<Car> cars = new ArrayList<>();
        
        String answer = "";
        String model = "";
        int year = 0;
        int power = 0;

        while (!answer.equals("n")) {
            try {

                System.out.println("Введите модель");
                model = scanner.nextLine();
                if (!Validator.isModelValid(model)) {
                    System.out.println("Модель не прошла валидность");
                    continue;
                }

                System.out.println("Введите год");
                year = Integer.parseInt(scanner.nextLine());
                if (!Validator.isYearValid(year)) {
                    System.out.println("Год не прошел валидность");
                    continue;
                }

                System.out.println("Введите мощность");
                power = Integer.parseInt(scanner.nextLine());
                if (!Validator.isPowerValid(power)) {
                    System.out.println("Мощность не прошла валидность");
                    continue;
                }

                cars.add(new Car.CarBuilder().setModel(model).setYear(year).setPower(power).build());

                System.out.println("Для выхода нажмите n для продолжение нажмите Enter");
                answer = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Для ввода года и мощности используйте цифры");
            }
        }


        List<String> errors = new ArrayList<>();
        return new LoadResult(cars, errors);
    }

}
