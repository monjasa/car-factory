package org.monjasa.carfactory.config;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.monjasa.carfactory.controller.MainSceneController;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.event.StageReadyEvent;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/images/car-product-icon.png")));
        Scene mainScene = new Scene(fxWeaver.loadView(MainSceneController.class), 1600, 900);
        stage.setScene(mainScene);
        stage.show();
    }
}
