package org.monjasa.carfactory;

import javafx.application.Application;
import org.monjasa.carfactory.config.JavaFxApplicationInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarFactoryApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplicationInitializer.class, args);
    }
}