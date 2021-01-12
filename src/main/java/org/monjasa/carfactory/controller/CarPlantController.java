package org.monjasa.carfactory.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.model.CarPlantModel;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class CarPlantController {

    private final CarPlantModel carPlantModel;

    @FXML private Label waitingCarCreationTasksCount;

    public void initialize() {
        waitingCarCreationTasksCount.textProperty().bind(
                new SimpleStringProperty("Waiting: +")
                        .concat(carPlantModel.getWaitingTaskCount().asString())
        );
    }
}
