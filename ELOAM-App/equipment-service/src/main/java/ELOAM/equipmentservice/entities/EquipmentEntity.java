package ELOAM.equipmentservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipment")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentEntity {

    @Id
    private Long equipmentID;

    private String equipmentBrand;

    private Boolean equipmentAvailability;
}

