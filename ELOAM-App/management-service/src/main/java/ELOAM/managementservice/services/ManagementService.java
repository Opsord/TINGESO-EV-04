package ELOAM.managementservice.services;

import ELOAM.managementservice.models.EquipmentModel;
import ELOAM.managementservice.models.LoanModel;
import ELOAM.managementservice.models.TeacherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ManagementService {

    @Autowired
    RestTemplate restTemplate;

    Logger logger = Logger.getLogger(getClass().getName());


    // Communication with teacher service
    private static final String teacherServiceURL = "http://localhost:8082/teachers";

    // Get a teacher by its RUT
    public TeacherModel findByRut(String rut) {
        ResponseEntity<TeacherModel> response = restTemplate.exchange(
                teacherServiceURL + "/" + rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TeacherModel>() {
                }
        );
        return response.getBody();
    }

    // Update a teacher
    public void updateTeacher(TeacherModel teacherModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TeacherModel> request = new HttpEntity<>(teacherModel, headers);
        ResponseEntity<TeacherModel> response = restTemplate.exchange(
                teacherServiceURL,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<TeacherModel>() {
                }
        );
        response.getBody();
    }

    // Communication with equipment service
    private static final String equipmentServiceURL = "http://localhost:8082/equipment";

    // Get equipment by ID
    public EquipmentModel findEquipmentByID(Long equipmentID) {
        ResponseEntity<EquipmentModel> response = restTemplate.exchange(
                equipmentServiceURL + "/" + equipmentID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EquipmentModel>() {
                }
        );
        return response.getBody();
    }

    // Get all equipment
    public List<EquipmentModel> findAllEquipment() {
        ResponseEntity<List<EquipmentModel>> response = restTemplate.exchange(
                equipmentServiceURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EquipmentModel>>() {
                }
        );
        return response.getBody();
    }


    // Communication with loan service
    private static final String loanServiceURL = "http://localhost:8082/loans";

    // Get a loan by its ID
    public LoanModel findLoanByID(Long loanID) {
        ResponseEntity<LoanModel> response = restTemplate.exchange(
                loanServiceURL + "/" + loanID,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LoanModel>() {
                }
        );
        return response.getBody();
    }

    // Get all loans by teacher RUT
    public List<LoanModel> findAllLoansByTeacherRUT(String teacherRUT) {
        ResponseEntity<List<LoanModel>> response = restTemplate.exchange(
                loanServiceURL + "/teacher/" + teacherRUT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LoanModel>>() {
                }
        );
        return response.getBody();
    }

    // Get loans by observation and teacher RUT
    public List<LoanModel> findLoansByObservationAndRUT(int loanObservation, String teacherRUT) {
        ResponseEntity<List<LoanModel>> response = restTemplate.exchange(
                loanServiceURL + "/observation-rut/" + loanObservation + "/" + teacherRUT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LoanModel>>() {
                }
        );
        return response.getBody();
    }



    // Other methods

    // Check if a teacher should have a permanent loan restriction
    // 0 = No observation | 2 = Equipment damaged
    public TeacherModel checkPermanentRestriction(TeacherModel teacher, List<LoanModel> loanList) {
        // Count the number of loans with observation 2
        int damagedEquipmentLoans = 0;
        if (loanList != null) {
            for (LoanModel loanModel : loanList) {
                if (loanModel.getLoanObservation() == 2) {
                    damagedEquipmentLoans++;
                }
            }
        }
        // If the teacher has 3 or more loans with observation 2,
        // the teacher should have loan restriction status 2
        if (damagedEquipmentLoans >= 3) {
            // Change the loan restriction status
            teacher.setTeacherLoanRestriction(2);
            // Return the teacher
            return teacher;
        }
        // Return the teacher
        return teacher;
    }

    // Check if a teacher should have a temporal loan restriction
    // 0 = No observation | 1 = Returned with delay
    public TeacherModel checkTemporalRestriction(TeacherModel teacher, List<LoanModel> loanList) {
        // Count the number of loans with observation 1
        int lateLoans = 0;
        if (loanList != null) {
            for (LoanModel loanModel : loanList) {
                if (loanModel.getLoanObservation() == 1) {
                    lateLoans++;
                }
            }
        }
        // If the teacher has at least 1 loan with observation 1,
        // the teacher should have loan restriction status 1
        if (lateLoans >= 1) {
            // Change the loan restriction status
            teacher.setTeacherLoanRestriction(1);
            // Return the teacher
            return teacher;
        }
        // Return the teacher
        return teacher;
    }

    // Calculate restriction end date
    public LocalDate calculateEndDateOfRestriction(List<LoanModel> loanList) {
        // Find the last loan with observation 1
        LoanModel lastLateLoan = loanList.stream()
                .filter(loan -> loan.getLoanObservation() == 1)
                .max(Comparator.comparing(LoanModel::getLoanDate))
                .orElse(null);

        // Handle the case where there are no loans with observation 1
        if (lastLateLoan == null) {
            // There should not
            return null;
        }

        // Calculate the end date of restriction
        return lastLateLoan.getLoanDate().toLocalDate().plusDays(7);
    }

    // Check if a teacher should have a loan restriction
    // 0 = No observation | 1 = Returned with delay | 2 = Equipment damaged
    public void updateTeacherLoanRestrictionStatus(String teacherRUT) {
        // Find the teacher by its RUT
        TeacherModel teacher = findByRut(teacherRUT);
        // Find all loans by the teacher RUT
        List<LoanModel> loanList = findAllLoansByTeacherRUT(teacherRUT);

        // Check if the teacher should have a permanent loan restriction
        teacher = checkPermanentRestriction(teacher, loanList);
        // If the teacher has a permanent loan restriction, update the teacher
        if (teacher.getTeacherLoanRestriction() == 2) {
            updateTeacher(teacher);
        } else {
            // Check if the teacher should have a temporal loan restriction
            teacher = checkTemporalRestriction(teacher, loanList);
            // If the teacher has a temporal loan restriction, update the teacher
            if (teacher.getTeacherLoanRestriction() == 1) {
                // Calculate the end date of restriction
                LocalDate endDateOfRestriction = calculateEndDateOfRestriction(loanList);
                // Set the end date of restriction
                teacher.setTeacherLoanRestrictionDate(String.valueOf(endDateOfRestriction));
                updateTeacher(teacher);
            }
        }
    }

}