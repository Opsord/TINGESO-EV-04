import axios from "axios";

const LOAN_API_URL = "http://localhost:8084/loans";

class LoanService {
  // Get a list of all loans
  getAllLoans() {
    return axios.get(LOAN_API_URL);
  }

  // Find a loan by its code
  getLoanByCode(loanCode) {
    return axios.get(LOAN_API_URL + "/" + loanCode);
  }

  // Get a list of loans by the equipment ID
  getLoansByEquipmentId(equipmentId) {
    return axios.get(LOAN_API_URL + "/equipment/" + equipmentId);
  }

  // Get a list of loans by the teacher RUT
  getLoansByTeacherRut(teacherRut) {
    return axios.get(LOAN_API_URL + "/teacher/" + teacherRut);
  }

  // Get a list of loans by observation and RUT
  getLoansByObservationAndRut(teacherRut, observation) {
    return axios.get(
      LOAN_API_URL + "/observation-rut/" + observation + "/" + teacherRut
    );
  }

  // Delete a loan from the database
  deleteLoan(loanCode) {
    return axios.delete(LOAN_API_URL + "/delete/" + loanCode);
  }

  // Update loan duration
  updateLoanDuration(loanCode) {
    return axios.get(LOAN_API_URL + "/update/" + loanCode);
  }

  // Mark a loan as returned
  markLoanAsReturned(loanCode) {
    return axios.get(LOAN_API_URL + "/mark-returned/" + loanCode);
  }
}

// eslint-disable-next-line import/no-anonymous-default-export
export default new LoanService();
