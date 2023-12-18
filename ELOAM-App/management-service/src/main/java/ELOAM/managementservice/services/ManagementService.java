package ELOAM.managementservice.services;

import ELOAM.managementservice.models.TeacherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    // Get all teachers
    public List<TeacherModel> findAllTeachers() {
        ResponseEntity<List<TeacherModel>> response = restTemplate.exchange(
                teacherServiceURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeacherModel>>() {
                }
        );
        return response.getBody();
    }

    // Change the loan restriction status of a teacher
    public void changeLoanRestrictionStatus(String teacherRUT, int teacherLoanRestriction) {
        // Find the teacher by its RUT
        TeacherModel teacherModel = findByRut(teacherRUT);
        // Change the loan restriction status
        teacherModel.setTeacherLoanRestriction(teacherLoanRestriction);
        // Save the changes to the database
        restTemplate.put(teacherServiceURL + "/" + teacherRUT, teacherModel);
    }










}
