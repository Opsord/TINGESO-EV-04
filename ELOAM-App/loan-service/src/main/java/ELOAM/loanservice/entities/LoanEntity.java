package ELOAM.loanservice.entities;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long loanID;

    private String responsibleTeacherRUT;

    private Long equipmentID;

    private String equipmentBrand;

    private String loanMotivation;

    private LocalDateTime loanDate;

    private LocalDateTime loanReturnDate;

    private Duration loanDuration;

    // Int that indicates if the loan has an observation
    // 0 = No observation | 1 = Returned with delay | 2 = Equipment damaged
    private int loanObservation;
}
