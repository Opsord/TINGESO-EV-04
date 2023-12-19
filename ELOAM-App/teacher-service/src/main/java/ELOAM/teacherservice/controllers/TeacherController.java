package ELOAM.teacherservice.controllers;

import ELOAM.teacherservice.entities.TeacherEntity;
import ELOAM.teacherservice.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    Logger logger = Logger.getLogger(getClass().getName());

    // Get all teachers
    @GetMapping
    public ResponseEntity<List<TeacherEntity>> getAllTeachers() {
        if (teacherService.findAllTeachers().isEmpty()) {
            logger.info("No teachers found");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some teachers found");
            return ResponseEntity.ok(teacherService.findAllTeachers());
        }
    }

    // Get a teacher by its RUT
    @GetMapping("/{teacherRUT}")
    public ResponseEntity<TeacherEntity> getTeacherByRUT(@PathVariable String teacherRUT) {
        if (teacherService.findTeacherByRUT(teacherRUT) == null) {
            logger.info("Teacher not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Teacher found with RUT: " + teacherRUT);
            return ResponseEntity.ok(teacherService.findTeacherByRUT(teacherRUT));
        }
    }

    // Get a list of teachers by their loan restriction status
    @GetMapping("/restriction/{teacherLoanRestriction}")
    public ResponseEntity<List<TeacherEntity>> getTeachersByLoanRestriction(@PathVariable int teacherLoanRestriction) {
        if (teacherService.findTeachersByLoanRestriction(teacherLoanRestriction).isEmpty()) {
            logger.info("No teachers found with loan restriction status: " + teacherLoanRestriction);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some teachers found with loan restriction status: " + teacherLoanRestriction);
            return ResponseEntity.ok(teacherService.findTeachersByLoanRestriction(teacherLoanRestriction));
        }
    }

    // Set an end date of restriction to a teacher
    @GetMapping("/end-date/{teacherRUT}/{endDateOfRestriction}")
    public ResponseEntity<Void> setEndDateOfRestriction(@PathVariable String teacherRUT, @PathVariable String endDateOfRestriction) {
        if (teacherService.findTeacherByRUT(teacherRUT) == null) {
            logger.info("Teacher not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Setting end date of restriction to teacher with RUT: " + teacherRUT);
            teacherService.setEndDateOfRestriction(teacherRUT, endDateOfRestriction);
            return ResponseEntity.ok().build();
        }
    }

    // Change the loan restriction status of a teacher
    @GetMapping("/change-restriction/{teacherRUT}/{teacherLoanRestriction}")
    public ResponseEntity<Void> changeLoanRestrictionStatus(@PathVariable String teacherRUT, @PathVariable int teacherLoanRestriction) {
        if (teacherService.findTeacherByRUT(teacherRUT) == null) {
            logger.info("Teacher not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Changing loan restriction status of teacher with RUT: " + teacherRUT);
            teacherService.changeLoanRestrictionStatus(teacherRUT, teacherLoanRestriction);
            return ResponseEntity.ok().build();
        }
    }

    // Delete a teacher from the database
    @GetMapping("/delete/{teacherRUT}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String teacherRUT) {
        if (teacherService.findTeacherByRUT(teacherRUT) == null) {
            logger.info("Teacher not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Deleting teacher with RUT: " + teacherRUT);
            teacherService.deleteTeacher(teacherRUT);
            return ResponseEntity.ok().build();
        }
    }

    // Save a teacher to the database
    @PostMapping
    public ResponseEntity<TeacherEntity> saveTeacher(@RequestBody TeacherEntity teacherEntity) {
        logger.info("Saving teacher with RUT: " + teacherEntity.getTeacherRUT());
        teacherService.saveTeacher(teacherEntity);
        return ResponseEntity.ok(teacherEntity);
    }

}
