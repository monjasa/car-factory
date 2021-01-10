package org.monjasa.carfactory.config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.monjasa.carfactory.CarFactoryApplication;
import org.monjasa.carfactory.event.StageReadyEvent;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public final class JavaFxApplicationInitializer extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(CarFactoryApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Factory Management");
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
