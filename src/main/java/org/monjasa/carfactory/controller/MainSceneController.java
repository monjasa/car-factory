package org.monjasa.carfactory.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.service.MainService;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class MainSceneController {

    private final MainService mainService;

    @FXML
    private Label queueSizeLabel;

    public void initialize() {
        queueSizeLabel.textProperty().bind(
                Bindings.size(mainService.loadMainModel().getObservableBlockingQueue()).asString()
                        .concat(" / ")
                        .concat(String.valueOf(mainService.getEngineStorageCapacity()))
        );
    }

    public void handleClick(ActionEvent actionEvent) {

    }
}