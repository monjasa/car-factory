package org.monjasa.carfactory.controller;

import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@FxmlView
public class MainSceneController {

    @FXML
    PipelineControllerBox enginePipelineController;
    @FXML
    PipelineControllerBox bodyPipelineController;
    @FXML
    PipelineControllerBox accessoryPipelineController;
    @FXML
    PipelineControllerBox carPipelineController;
    @FXML
    PipelineControllerBox dealerPipelineController;

    Pipeline enginePipeline;
    Pipeline bodyPipeline;
    Pipeline accessoryPipeline;
    Pipeline carPipeline;
    Pipeline dealerPipeline;

    @Resource(name = "enginePipeline")
    public void setEnginePipeline(Pipeline enginePipeline) {
        this.enginePipeline = enginePipeline;
    }

    @Resource(name = "bodyPipeline")
    public void setBodyPipeline(Pipeline bodyPipeline) {
        this.bodyPipeline = bodyPipeline;
    }

    @Resource(name = "accessoryPipeline")
    public void setAccessoryPipeline(Pipeline accessoryPipeline) {
        this.accessoryPipeline = accessoryPipeline;
    }

    @Resource(name = "carPipeline")
    public void setCarPipeline(Pipeline carPipeline) {
        this.carPipeline = carPipeline;
    }

    @Resource(name = "dealerPipeline")
    public void setDealerPipeline(Pipeline dealerPipeline) {
        this.dealerPipeline = dealerPipeline;
    }

    public void initialize() {
        enginePipelineController.setPipeline(enginePipeline);
        bodyPipelineController.setPipeline(bodyPipeline);
        accessoryPipelineController.setPipeline(accessoryPipeline);

        carPipelineController.setPipeline(carPipeline);
        dealerPipelineController.setPipeline(dealerPipeline);
    }
}