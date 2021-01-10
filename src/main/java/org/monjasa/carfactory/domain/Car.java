package org.monjasa.carfactory.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Car implements Product {

    @NonNull private CarAccessory carAccessory;
    @NonNull private CarBody carBody;
    @NonNull private CarEngine carEngine;

    Car(CarAccessory carAccessory, CarBody carBody, CarEngine carEngine) {
        this.carAccessory = carAccessory;
        this.carBody = carBody;
        this.carEngine = carEngine;
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public static class CarBuilder {

        private CarAccessory carAccessory;
        private CarBody carBody;
        private CarEngine carEngine;

        CarBuilder() {
        }

        public CarBuilder carAccessory(CarAccessory carAccessory) {
            this.carAccessory = carAccessory;
            return this;
        }

        public CarBuilder carBody(CarBody carBody) {
            this.carBody = carBody;
            return this;
        }

        public CarBuilder carEngine(CarEngine carEngine) {
            this.carEngine = carEngine;
            return this;
        }

        public Car build() {
            return new Car(carAccessory, carBody, carEngine);
        }

        public String toString() {
            return "Car.CarBuilder(carAccessory=" + this.carAccessory + ", carBody=" + this.carBody + ", carEngine=" + this.carEngine + ")";
        }
    }
}
