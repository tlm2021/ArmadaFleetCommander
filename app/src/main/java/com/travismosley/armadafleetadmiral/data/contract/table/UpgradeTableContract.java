package com.travismosley.armadafleetadmiral.data.contract.table;

/**
 * Base contract class for all upgrade-related views
 */
public interface UpgradeTableContract extends ComponentTableContract {

    String TYPE_ID = "type_id";
    String TYPE_NAME = "type_name";
    String TEXT = "text";
    String IS_UNIQUE = "is_unique";
    String SHIP_CLASS_ID = "ship_class_id";
}
