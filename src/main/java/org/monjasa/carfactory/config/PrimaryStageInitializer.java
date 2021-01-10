package org.monjasa.carfactory.config;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.monjasa.carfactory.controller.MainSceneController;
import org.monjasa.carfactory.event.StageReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        Scene mainScene = new Scene(fxWeaver.loadView(MainSceneController.class), 600, 350);
        stage.setScene(mainScene);
        stage.show();
    }
}
