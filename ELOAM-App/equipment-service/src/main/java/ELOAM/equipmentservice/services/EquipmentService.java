package ELOAM.equipmentservice.services;

import ELOAM.equipmentservice.entities.EquipmentEntity;
import ELOAM.equipmentservice.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    EquipmentRepository equipmentRepository;

    // Find equipment by its ID
    public EquipmentEntity findEquipmentByID(Long equipmentID) {
        return equipmentRepository.findEquipmentByID(equipmentID);
    }

    // Find all equipment
    public List<EquipmentEntity> findAllEquipment() {
        return (List<EquipmentEntity>) equipmentRepository.findAll();
    }

    // Save equipment to the database
    public void saveEquipment(EquipmentEntity equipmentEntity) {
        equipmentRepository.save(equipmentEntity);
    }

    // Find a list of equipment by their availability status
    public List<EquipmentEntity> findEquipmentByAvailability(Boolean equipmentAvailability) {
        return equipmentRepository.findEquipmentByAvailability(equipmentAvailability);
    }

    // Find a list of equipment by their brand
    public List<EquipmentEntity> findEquipmentByBrand(String equipmentBrand) {
        return equipmentRepository.findEquipmentByBrand(equipmentBrand);
    }

    // Change the availability status of equipment
    public void changeAvailabilityStatus(Long equipmentID, Boolean equipmentAvailability) {
        // Find the equipment by its ID
        EquipmentEntity equipmentEntity = equipmentRepository.findEquipmentByID(equipmentID);
        // Change the availability status
        equipmentEntity.setEquipmentAvailability(equipmentAvailability);
        // Save the changes to the database
        equipmentRepository.save(equipmentEntity);
    }

    // Delete equipment from the database
    public void deleteEquipment(Long equipmentID) {
        equipmentRepository.deleteById(equipmentID);
    }

}