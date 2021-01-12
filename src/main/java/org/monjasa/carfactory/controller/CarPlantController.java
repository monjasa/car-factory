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

    @FXML private Label carProducersCount;
    @FXML private Label waitingCarCreationTasksCount;

    public void initialize() {
        carProducersCount.setText(String.format("Car Constructors | %d", carPlantModel.getConstructorsCount()));
        waitingCarCreationTasksCount.textProperty().bind(
                new SimpleStringProperty(waitingCarCreationTasksCount.getText())
                        .concat(carPlantModel.getWaitingTaskCount().asString())
        );
    }
}
