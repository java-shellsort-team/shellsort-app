package team.shellsort.input;

import team.shellsort.model.Car;

public class LineParser {

    private static final String SEPARATOR = ";";

    public static Car parse(String line) {
        // TODO: реализовать парсинг строки вида "model;power;year"

        String[] tokens = line.split(SEPARATOR);

        String model;
        int year;
        int power;

        try{
            model = tokens[0];
            year = Integer.parseInt(tokens[1]);
            power = Integer.parseInt(tokens[2]);
        } catch(Exception e){
            return new Car.CarBuilder().setModel("").setYear(0).setPower(0).build();
        }

        return new Car.CarBuilder().setModel(model).setYear(year).setPower(power).build();

    }
}
