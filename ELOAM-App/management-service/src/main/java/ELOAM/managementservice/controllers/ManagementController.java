package ELOAM.managementservice.controllers;

import ELOAM.managementservice.models.TeacherModel;
import ELOAM.managementservice.services.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    ManagementService managementService;

    // Find a teacher by its RUT
    @GetMapping("/teacher/{teacherRUT}")
    public ResponseEntity<TeacherModel> getTeacherByRUT(@PathVariable String teacherRUT) {
        if (managementService.findByRut(teacherRUT) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(managementService.findByRut(teacherRUT));
        }
    }
}