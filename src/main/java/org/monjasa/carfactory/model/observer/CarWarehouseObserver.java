package org.monjasa.carfactory.model.observer;

import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.model.CarPlantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarWarehouseObserver implements WarehouseObserver {

    public CarPlantModel carPlantModel;

    @Override
    public void update() {

        int warehouseSize = carPlantModel.getCarWarehouse().sizeBinding().get();
        int warehouseCapacity = carPlantModel.getCarWarehouse().capacityProperty().get();
        int waitingTaskCount = (int) carPlantModel.getWaitingTaskCount().get();

        int warehouseStockDifference = warehouseCapacity - warehouseSize - waitingTaskCount;
        for (int i = 0; i < warehouseStockDifference; i++) carPlantModel.submitCarConstructionTask();
    }

    @Autowired
    @Lazy
    public void setCarPlantModel(CarPlantModel carPlantModel) {
        this.carPlantModel = carPlantModel;
    }
}
