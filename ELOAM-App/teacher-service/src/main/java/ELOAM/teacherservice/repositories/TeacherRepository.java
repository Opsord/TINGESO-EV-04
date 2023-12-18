package ELOAM.teacherservice.repositories;

import ELOAM.teacherservice.entities.TeacherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends CrudRepository<TeacherEntity, String> {
    // Custom queries can be added here

    // Find a teacher by its RUT
    @Query("SELECT t FROM TeacherEntity t WHERE t.teacherRUT = ?1")
    TeacherEntity findTeacherByRUT(@Param ("teacherRUT") String teacherRUT);

    // Find a list of teacher by their loan restriction status
    @Query("SELECT t FROM TeacherEntity t WHERE t.teacherLoanRestriction = ?1")
    List<TeacherEntity> findTeachersByLoanRestriction(@Param ("teacherLoanRestriction") int teacherLoanRestriction);

}
