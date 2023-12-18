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
    private final String teacherServiceURL = "http://localhost:8080/teachers";

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
}
