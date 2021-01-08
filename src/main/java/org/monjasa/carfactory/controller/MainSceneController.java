package org.monjasa.carfactory.controller;

import org.monjasa.carfactory.service.SpeedService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class MainSceneController {

    private final SpeedService speedService;

    @FXML private Slider speedSlider;
    @FXML private Label weatherLabel;

    public void initialize() {
        speedSlider.valueProperty().bindBidirectional(speedService.getSpeed().getSpeedProperty());
        weatherLabel.textProperty().bind(speedService.getSpeed().getSpeedProperty().asString());
    }

    public void resetSpeed(ActionEvent actionEvent) {
        speedSlider.setValue(3);
    }
}