package ELOAM.teacherservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int teacherLoanRestriction;
}
