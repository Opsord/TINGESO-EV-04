package ELOAM.loanservice.controllers;

import ELOAM.loanservice.entities.LoanEntity;
import ELOAM.loanservice.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

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
    public ResponseEntity<List<LoanEntity>> getLoansByTeacherRUT(@PathVariable String responsibleTeacherRUT) {
        if (loanService.findLoansByTeacherRUT(responsibleTeacherRUT).isEmpty()) {
            logger.info("No loans found with teacher RUT: " + responsibleTeacherRUT);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some loans found with teacher RUT: " + responsibleTeacherRUT);
            return ResponseEntity.ok(loanService.findLoansByTeacherRUT(responsibleTeacherRUT));
        }
    }

    // Get a list of loans by the loan observation
    @GetMapping("/observation/{loanObservation}")
    public ResponseEntity<List<LoanEntity>> getLoansByLoanObservation(@PathVariable int loanObservation) {
        if (loanService.findLoansByLoanObservation(loanObservation).isEmpty()) {
            logger.info("No loans found with loan observation: " + loanObservation);
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Some loans found with loan observation: " + loanObservation);
            return ResponseEntity.ok(loanService.findLoansByLoanObservation(loanObservation));
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
}
