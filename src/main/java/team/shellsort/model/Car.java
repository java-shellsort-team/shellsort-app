package team.shellsort.model;

public class Car {
    private final String model;
    private final int year;
    private final int power;

    private Car(CarBuilder builder) {
        this.model = builder.model;
        this.year = builder.year;
        this.power = builder.power;
    }

    /**
     * Паттерн Builder для создания объектов Car.
     * Пример вызова:
     * Car car = new CarBuilder()
     *                 .setModel("Lada Granta")
     *                 .setYear(2011)
     *                 .setPower(106)
     *                 .build();
     */
    public static class CarBuilder {
        private String model;
        private int power;
        private int year;

        public CarBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder setYear(int year) {
            this.year = year;
            return this;
        }

        public CarBuilder setPower(int power) {
            this.power = power;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "Машина модели " + model + ", год выпуска " + year + ", л.с. " + power;
    }
}
