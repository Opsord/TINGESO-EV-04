package ELOAM.loanservice.controllers;

import ELOAM.loanservice.entities.LoanEntity;
import ELOAM.loanservice.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    Logger logger = Logger.getLogger(getClass().getName());

    // Get all loans
    @GetMapping
    public ResponseEntity<List<LoanEntity>> getAllLoans() {
        if (loanService.findAllLoans().isEmpty()) {
            logger.info("No loans found");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some loans found");
            return ResponseEntity.ok(loanService.findAllLoans());
        }
    }

    // Get a loan by its ID
    @GetMapping("/{loanID}")
    public ResponseEntity<LoanEntity> getLoanByID(@PathVariable Long loanID) {
        if (loanService.findLoanByID(loanID) == null) {
            logger.info("Loan not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Loan found with ID: " + loanID);
            return ResponseEntity.ok(loanService.findLoanByID(loanID));
        }
    }

    // Get a list of loans by the equipment ID
    @GetMapping("/equipment/{equipmentID}")
    public ResponseEntity<List<LoanEntity>> getLoansByEquipmentID(@PathVariable Long equipmentID) {
        if (loanService.findLoansByEquipmentID(equipmentID).isEmpty()) {
            logger.info("No loans found with equipment ID: " + equipmentID);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some loans found with equipment ID: " + equipmentID);
            return ResponseEntity.ok(loanService.findLoansByEquipmentID(equipmentID));
        }
    }

    // Get a list of loans by the teacher RUT
    @GetMapping("/teacher/{responsibleTeacherRUT}")
    public ResponseEntity<List<LoanEntity>> getLoansByTeacherRUT(@PathVariable String responsibleTeacherRUT) {
        if (loanService.findLoansByTeacherRUT(responsibleTeacherRUT).isEmpty()) {
            logger.info("No loans found with teacher RUT: " + responsibleTeacherRUT);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some loans found with teacher RUT: " + responsibleTeacherRUT);
            return ResponseEntity.ok(loanService.findLoansByTeacherRUT(responsibleTeacherRUT));
        }
    }

    // Get a list of loans by observation and RUT
    @GetMapping("/observation-rut/{loanObservation}/{responsibleTeacherRUT}")
    public ResponseEntity<List<LoanEntity>> getLoansByObservationAndRUT(@PathVariable int loanObservation, @PathVariable String responsibleTeacherRUT) {
        if (loanService.findLoansByObservationAndRUT(loanObservation, responsibleTeacherRUT).isEmpty()) {
            logger.info("No loans found with observation: " + loanObservation + " and teacher RUT: " + responsibleTeacherRUT);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some loans found with observation: " + loanObservation + " and teacher RUT: " + responsibleTeacherRUT);
            return ResponseEntity.ok(loanService.findLoansByObservationAndRUT(loanObservation, responsibleTeacherRUT));
        }
    }

    // Delete a loan from the database
    @GetMapping("/delete/{loanID}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanID) {
        if (loanService.findLoanByID(loanID) == null) {
            logger.info("Loan not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Deleting loan with ID: " + loanID);
            loanService.deleteLoan(loanID);
            return ResponseEntity.ok().build();
        }
    }

    // Update loan duration
    @GetMapping("/update/{loanID}")
    public ResponseEntity<Void> updateLoanDuration(@PathVariable Long loanID) {
        if (loanService.findLoanByID(loanID) == null) {
            logger.info("Loan not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Updating loan duration of loan with ID: " + loanID);
            loanService.updateLoanDuration(loanID);
            return ResponseEntity.ok().build();
        }
    }

    // Mark a loan as returned
    @GetMapping("/mark-returned/{loanID}")
    public ResponseEntity<Void> markLoanAsReturned(@PathVariable Long loanID) {
        if (loanService.findLoanByID(loanID) == null) {
            logger.info("Loan not found");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Marking loan with ID: " + loanID + " as returned");
            loanService.markLoanAsReturned(loanID);
            return ResponseEntity.ok().build();
        }
    }

    // Save a loan to the database
    @PostMapping
    public ResponseEntity<LoanEntity> saveLoan(@RequestBody LoanEntity loanEntity) {
        logger.info("Saving loan");
        loanService.saveLoan(loanEntity);
        return ResponseEntity.ok(loanEntity);
    }

    // Create a loan
    @PostMapping("/create")
    public ResponseEntity<LoanEntity> createLoan(@RequestBody LoanEntity loanEntity) {
        logger.info("Creating loan");
        loanService.createLoan(loanEntity);
        return ResponseEntity.ok(loanEntity);
    }
}
