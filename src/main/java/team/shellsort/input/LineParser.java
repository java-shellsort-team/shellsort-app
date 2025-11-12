package team.shellsort.input;

import team.shellsort.model.Car;

public class LineParser {

    private static final String SEPARATOR = ";";

    public Car parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(SEPARATOR);
        if (parts.length != 3) {
            return null;
        }

        try {
            String model = parts[0].trim();
            int power = Integer.parseInt(parts[1].trim());
            int year = Integer.parseInt(parts[2].trim());

            return new Car.CarBuilder()
                    .setModel(model)
                    .setPower(power)
                    .setYear(year)
                    .build();

        } catch (NumberFormatException e) {
            return null;
        }
    }
}
