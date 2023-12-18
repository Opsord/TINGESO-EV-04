package ELOAM.equipmentservice.repositories;

import ELOAM.equipmentservice.entities.EquipmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends CrudRepository<EquipmentEntity, Long> {
    // Custom queries can be added here

    // Find equipment by its ID
    @Query("SELECT e FROM EquipmentEntity e WHERE e.equipmentID = ?1")
    EquipmentEntity findEquipmentByID(@Param("equipmentID") Long equipmentID);

    // Find a list of equipment by their availability status
    @Query("SELECT e FROM EquipmentEntity e WHERE e.equipmentAvailability = ?1")
    List<EquipmentEntity> findEquipmentByAvailability(@Param("equipmentAvailability") Boolean equipmentAvailability);

    // Find a list of equipment by their brand
    @Query("SELECT e FROM EquipmentEntity e WHERE e.equipmentBrand = ?1")
    List<EquipmentEntity> findEquipmentByBrand(@Param("equipmentBrand") String equipmentBrand);
}
