package ELOAM.managementservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherModel {

        private String teacherRUT;

        private String teacherName;

        private String teacherLastName;

        // Int that indicates if the teacher has a loan restriction
        private int teacherLoanRestriction;

        // LocalDate that indicates the end of the temporary restriction
        private String teacherLoanRestrictionDate;
}
