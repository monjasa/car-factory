package org.monjasa.carfactory.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class SecondSceneController {

    @FXML private Label myLabel;

    public void initialize() {
        myLabel.textProperty().setValue("Hello!");
    }
}
