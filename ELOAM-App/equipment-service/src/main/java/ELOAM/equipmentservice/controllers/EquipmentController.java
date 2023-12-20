package ELOAM.equipmentservice.controllers;

import ELOAM.equipmentservice.entities.EquipmentEntity;
import ELOAM.equipmentservice.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    Logger logger = Logger.getLogger(getClass().getName());

    // Get all equipment
    @GetMapping
    public ResponseEntity<List<EquipmentEntity>> getAllEquipment() {
        if (equipmentService.findAllEquipment().isEmpty()) {
            logger.info("No equipment found");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some equipment found");
            return ResponseEntity.ok(equipmentService.findAllEquipment());
        }
    }

    // Find equipment by its ID
    @GetMapping("/{equipmentID}")
    public ResponseEntity<EquipmentEntity> getEquipmentByID(@PathVariable Long equipmentID) {
        if (equipmentService.findEquipmentByID(equipmentID) == null) {
            logger.info("Equipment with ID: " + equipmentID + " not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Equipment found with ID: " + equipmentID);
            return ResponseEntity.ok(equipmentService.findEquipmentByID(equipmentID));
        }
    }

    // Find a list of equipment by their availability status
    @GetMapping("/availability/{equipmentAvailability}")
    public ResponseEntity<List<EquipmentEntity>> getEquipmentByAvailability(@PathVariable Boolean equipmentAvailability) {
        if (equipmentService.findEquipmentByAvailability(equipmentAvailability).isEmpty()) {
            logger.info("No equipment found with availability status: " + equipmentAvailability);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some equipment found with availability status: " + equipmentAvailability);
            return ResponseEntity.ok(equipmentService.findEquipmentByAvailability(equipmentAvailability));
        }
    }

    // Find a list of equipment by their brand
    @GetMapping("/brand/{equipmentBrand}")
    public ResponseEntity<List<EquipmentEntity>> getEquipmentByBrand(@PathVariable String equipmentBrand) {
        if (equipmentService.findEquipmentByBrand(equipmentBrand).isEmpty()) {
            logger.info("No equipment found with brand: " + equipmentBrand);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some equipment found with brand: " + equipmentBrand);
            return ResponseEntity.ok(equipmentService.findEquipmentByBrand(equipmentBrand));
        }
    }

    // Change the availability status of equipment
    @PutMapping("/change-availability/{equipmentID}/{equipmentAvailability}")
    public ResponseEntity<Void> changeAvailabilityStatus(@PathVariable Long equipmentID, @PathVariable Boolean equipmentAvailability) {
        if (equipmentService.findEquipmentByID(equipmentID) == null) {
            logger.info("Equipment not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Changing availability status of equipment with ID: " + equipmentID);
            equipmentService.changeAvailabilityStatus(equipmentID, equipmentAvailability);
            return ResponseEntity.ok().build();
        }
    }

    // Delete equipment from the database
    @GetMapping("/delete/{equipmentID}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long equipmentID) {
        if (equipmentService.findEquipmentByID(equipmentID) == null) {
            logger.info("Equipment not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Deleting equipment with ID: " + equipmentID);
            equipmentService.deleteEquipment(equipmentID);
            return ResponseEntity.ok().build();
        }
    }

    // Save equipment to the database
    @PostMapping
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentEntity equipmentEntity) {
        logger.info("Saving equipment");
        equipmentService.saveEquipment(equipmentEntity);
        return ResponseEntity.ok().build();
    }
}
