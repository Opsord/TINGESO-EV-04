package ELOAM.managementservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentModel {

    private Long equipmentID;

    private String equipmentBrand;

    private Boolean equipmentAvailability;
}
