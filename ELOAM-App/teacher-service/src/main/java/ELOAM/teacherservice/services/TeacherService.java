package ELOAM.teacherservice.services;

import ELOAM.teacherservice.entities.TeacherEntity;
import ELOAM.teacherservice.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    // Find a teacher by its RUT
    public TeacherEntity findTeacherByRUT(String teacherRUT) {
        return teacherRepository.findTeacherByRUT(teacherRUT);
    }

    // Find all teachers
    public List<TeacherEntity> findAllTeachers() {
        return (List<TeacherEntity>) teacherRepository.findAll();
    }

    // Save a teacher to the database
    public void saveTeacher(TeacherEntity teacherEntity) {
        teacherRepository.save(teacherEntity);
    }

    // Find a list of teachers by their loan restriction status
    public List<TeacherEntity> findTeachersByLoanRestriction(int teacherLoanRestriction) {
        return teacherRepository.findTeachersByLoanRestriction(teacherLoanRestriction);
    }

    // Change the loan restriction status of a teacher
    public void changeLoanRestrictionStatus(String teacherRUT, int teacherLoanRestriction) {
        // Find the teacher by its RUT
        TeacherEntity teacherEntity = findTeacherByRUT(teacherRUT);
        // Change the loan restriction status
        teacherEntity.setTeacherLoanRestriction(teacherLoanRestriction);
        // Save the changes to the database
        saveTeacher(teacherEntity);
    }

    // Delete a teacher from the database
    public void deleteTeacher(String teacherRUT) {
        teacherRepository.deleteById(teacherRUT);
    }

    // Set an end date of restriction to a teacher
    public void setEndDateOfRestriction(String teacherRUT, String endDateOfRestriction) {
        // Find the teacher by its RUT
        TeacherEntity teacherEntity = findTeacherByRUT(teacherRUT);
        // Format the end date of restriction from String to LocalDate
        LocalDate endDate = LocalDate.parse(endDateOfRestriction);
        // Set the end date of restriction
        teacherEntity.setTeacherLoanRestrictionDate(endDate);
        // Save the changes to the database
        saveTeacher(teacherEntity);
    }

}
