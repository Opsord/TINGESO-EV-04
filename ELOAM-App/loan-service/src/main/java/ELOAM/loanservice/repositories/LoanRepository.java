package ELOAM.loanservice.repositories;

import ELOAM.loanservice.entities.LoanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<LoanEntity, Long> {
    // Custom queries can be added here

    // Find a loan by its ID
    @Query("SELECT l FROM LoanEntity l WHERE l.loanID = ?1")
    LoanEntity findLoanByID(@Param("loanID") Long loanID);

    // Find loans by the equipment ID
    @Query("SELECT l FROM LoanEntity l WHERE l.equipmentID = ?1")
    List<LoanEntity> findLoansByEquipmentID(@Param("equipmentID") Long equipmentID);

    // Find loans by the teacher RUT
    @Query("SELECT l FROM LoanEntity l WHERE l.responsibleTeacherRUT = ?1")
    List<LoanEntity> findLoansByTeacherRUT(@Param("responsibleTeacherRUT") String responsibleTeacherRUT);

    // Find loans by the loan observation
    @Query("SELECT l FROM LoanEntity l WHERE l.loanObservation = ?1")
    List<LoanEntity> findLoansByLoanObservation(@Param("loanObservation") int loanObservation);
}
