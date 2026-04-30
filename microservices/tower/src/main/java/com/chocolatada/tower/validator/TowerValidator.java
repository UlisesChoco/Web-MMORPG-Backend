package com.chocolatada.tower.validator;

import com.chocolatada.tower.exception.InvalidTowerDataException;
import com.chocolatada.tower.configuration.resource.TowerResource;
import com.chocolatada.tower.configuration.resource.TowerResources;

import java.util.List;

public class TowerValidator {
    public static boolean validate(TowerResource towerResource) throws InvalidTowerDataException {
        if (!isValidFloor(towerResource.getFloor()))
            throw new InvalidTowerDataException("El piso debe ser un número positivo distinto de cero: " + towerResource.getFloor());

        if (!isValidLevelRange(towerResource.getLevelRange()))
            throw new InvalidTowerDataException("El rango de niveles no debe ser nulo o vacío: " + towerResource.getLevelRange());

        return true;
    }

    public static boolean validate(TowerResources towerResources) throws InvalidTowerDataException {
        List<TowerResource> floors = towerResources.getFloors();

        for(TowerResource resource : floors)
            validate(resource);

        return true;
    }

    public static boolean isValidFloor(Integer floor) {
        return floor != null && floor > 0;
    }

    public static boolean isValidLevelRange(String levelRange) {
        return levelRange != null && !levelRange.trim().isEmpty();
    }
}
