package ELOAM.managementservice.controllers;

import ELOAM.managementservice.services.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    ManagementService managementService;

    Logger logger = Logger.getLogger(getClass().getName());

    // Update a teacher restriction status
    @GetMapping("/update-restriction/{teacherRUT}")
    public ResponseEntity<Void> updateTeacherLoanRestriction(@PathVariable String teacherRUT) {
        // Assuming that the teacher exists
        managementService.updateTeacherLoanRestrictionStatus(teacherRUT);
        logger.info("Teacher with RUT: " + teacherRUT + " has been updated");
        return ResponseEntity.ok().build();
    }
}