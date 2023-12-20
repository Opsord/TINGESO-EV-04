package ELOAM.managementservice.controllers;

import ELOAM.managementservice.models.TeacherModel;
import ELOAM.managementservice.services.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

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