package ELOAM.loanservice.services;

import ELOAM.loanservice.entities.LoanEntity;
import ELOAM.loanservice.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Find loans by the loan observation
    public List<LoanEntity> findLoansByLoanObservation(int loanObservation) {
        return loanRepository.findLoansByLoanObservation(loanObservation);
    }

    // Delete a loan from the database
    public void deleteLoan(Long loanID) {
        loanRepository.deleteById(loanID);
    }
}
