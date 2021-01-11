package org.monjasa.carfactory.model.observer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.monjasa.carfactory.model.CarPlantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CarWarehouseObserver implements WarehouseObserver {

    public CarPlantModel carPlantModel;

    @Override
    public void update() {
        int warehouseSize = carPlantModel.getCarWarehouse().sizeBinding().get();
        int warehouseCapacity = carPlantModel.getCarWarehouse().capacityProperty().get();
        long waitingTaskCount = carPlantModel.getWaitingTaskCount().get();

        long warehouseStockDifference = warehouseCapacity - warehouseSize - waitingTaskCount;
        for (long i = 0; i < warehouseStockDifference; i++) carPlantModel.submitCarConstructionTask();
    }

    @Autowired
    @Lazy
    public void setCarPlantModel(CarPlantModel carPlantModel) {
        this.carPlantModel = carPlantModel;
    }
}
