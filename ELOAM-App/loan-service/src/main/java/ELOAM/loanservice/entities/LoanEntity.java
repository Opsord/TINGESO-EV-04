package ELOAM.loanservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanEntity {

    @Id
    private Long loanID;

    private String responsibleTeacherRUT;

    private Long equipmentID;

    private String loanMotivation;

    private LocalDateTime loanDate;

    private LocalDateTime loanReturnDate;

    private Duration loanDuration;

    // Int that indicates if the loan has an observation
    // 0 = No observation | 1 = Returned with delay | 2 = Equipment damaged
    private int loanObservation;
}
