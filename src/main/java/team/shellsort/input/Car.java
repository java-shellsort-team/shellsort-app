package team.shellsort.input;

public class Car {
    private final String model;
    private final int power;
    private final int year;

    public Car(String model, int power, int year) {
        this.model = model;
        this.power = power;
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%s (Год: %d, Мощность: %d л.с.)", model, year, power);
    }
}
