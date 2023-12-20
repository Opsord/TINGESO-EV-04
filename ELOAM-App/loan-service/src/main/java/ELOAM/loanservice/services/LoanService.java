package ELOAM.loanservice.services;

import ELOAM.loanservice.entities.LoanEntity;
import ELOAM.loanservice.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    // Find a loan by its ID
    public LoanEntity findLoanByID(Long loanID) {
        return loanRepository.findLoanByID(loanID);
    }

    // Find all loans
    public List<LoanEntity> findAllLoans() {
        return (List<LoanEntity>) loanRepository.findAll();
    }

    // Save a loan to the database
    public void saveLoan(LoanEntity loanEntity) {
        loanRepository.save(loanEntity);
    }

    // Find loans by the equipment ID
    public List<LoanEntity> findLoansByEquipmentID(Long equipmentID) {
        return loanRepository.findLoansByEquipmentID(equipmentID);
    }

    // Find loans by the teacher RUT
    public List<LoanEntity> findLoansByTeacherRUT(String responsibleTeacherRUT) {
        return loanRepository.findLoansByTeacherRUT(responsibleTeacherRUT);
    }

    // Find loans by observation and RUT
    public List<LoanEntity> findLoansByObservationAndRUT(int loanObservation, String responsibleTeacherRUT) {
        return loanRepository.findLoansByObservationAndRUT(loanObservation, responsibleTeacherRUT);
    }

    // Delete a loan from the database
    public void deleteLoan(Long loanID) {
        loanRepository.deleteById(loanID);
    }

    // Update loan duration
    public void updateLoanDuration(Long loanID) {
        // Find the loan by its ID
        LoanEntity loanEntity = findLoanByID(loanID);
        // Calculate the difference between the loan start date and the loan return date
        // If there is no return date,
        // the difference is calculated between the loan start date and the current date

        // Get the loan start date
        LocalDateTime loanStartDate = loanEntity.getLoanDate();
        // Check if the loan has a return date
        if (loanEntity.getLoanReturnDate() != null) {
            // Get the loan return date
            LocalDateTime loanReturnDate = loanEntity.getLoanReturnDate();
            // Calculate the difference between the loan start date and the loan return date
            Duration loanDuration = Duration.between(loanStartDate, loanReturnDate);
            // Update the loan duration
            loanEntity.setLoanDuration(loanDuration);
            // Save the loan to the database
            saveLoan(loanEntity);
        } else {
            // Calculate the difference between the loan start date and the current d   ate
            Duration loanDuration = Duration.between(loanStartDate, LocalDateTime.now());
            // Update the loan duration
            loanEntity.setLoanDuration(loanDuration);
            // Save the loan to the database
            saveLoan(loanEntity);
        }
    }

    // Mark a loan as returned
    public void markLoanAsReturned(Long loanID) {
        // Find the loan by its ID
        LoanEntity loanEntity = findLoanByID(loanID);
        // Set the loan return date to the current date
        loanEntity.setLoanReturnDate(LocalDateTime.now());
        // Update the loan duration
        updateLoanDuration(loanID);
        // If the duration of the loan is greater than 6 hours, set the loan observation to 1
        if (loanEntity.getLoanDuration().toHours() > 6) {
            loanEntity.setLoanObservation(1);
        }
        // Save the loan to the database
        saveLoan(loanEntity);
    }

    // Create and save a loan from a loan entity
    public void createLoan(LoanEntity loanEntity) {
        // Set the loan date to the current date
        loanEntity.setLoanDate(LocalDateTime.now());
        // Save the loan to the database
        saveLoan(loanEntity);
    }

}