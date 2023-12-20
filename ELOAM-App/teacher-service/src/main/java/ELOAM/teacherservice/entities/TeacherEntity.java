package ELOAM.teacherservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "teachers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherEntity {

    @Id
    private String teacherRUT;

    private String teacherName;

    private String teacherLastName;

    // Int that indicates if the teacher has a loan restriction
    // 0 = No restriction | 1 = Permanent restriction | 2 = Temporary restricted
    private int teacherLoanRestriction;

    // LocalDateTime that indicates the end of the temporary restriction
        private LocalDate teacherLoanRestrictionDate;
}
