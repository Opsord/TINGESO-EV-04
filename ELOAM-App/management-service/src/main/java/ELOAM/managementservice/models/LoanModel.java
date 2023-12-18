package ELOAM.managementservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanModel {

    private Long loanID;

    private String responsibleTeacherRUT;

    private Long equipmentID;

    private String loanMotivation;

    private LocalDateTime loanDate;

    private LocalDateTime loanReturnDate;

    // Int that indicates if the loan has an observation
    // 0 = No observation | 1 = Returned with delay | 2 = Equipment damaged
    private int loanObservation;
}
