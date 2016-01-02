package com.travismosley.armadafleetadmiral.data.contract.table;

/**
 * Base table contract class for all vehicle-related views
 */
public interface VehicleTableContract extends ComponentTableContract {

    String CLASS_TITLE = "class_title";
    String CLASS_ID = "class_id";
    String HULL = "hull";
    String SPEED = "max_speed";
}
