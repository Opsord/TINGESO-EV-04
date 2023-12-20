import axios from "axios";

const TEACHER_API_URL = "http://localhost:8082/teachers";

class TeacherService {
  // Get a list of all teachers
  getAllTeachers() {
    return axios.get(TEACHER_API_URL);
  }

  // Get a teacher by their RUT
  getTeacherByRut(teacherRUT) {
    return axios.get(TEACHER_API_URL + "/" + teacherRUT);
  }

  // Get a list of teachers by their loan restriction status
  getTeachersByRestrictionStatus(restrictionStatus) {
    return axios.get(TEACHER_API_URL + "/restriction/" + restrictionStatus);
  }

  // Set an end date of restriction to a teacher
  setTeacherRestrictionEndDate(teacherRUT, restrictionEndDate) {
    return axios.put(
      TEACHER_API_URL + "/end-date/" + teacherRUT + "/" + restrictionEndDate
    );
  }

  // Change the loan restriction status of a teacher
  changeTeacherRestriction(teacherRUT, restrictionStatus) {
    return axios.put(
      TEACHER_API_URL +
        "/change-restriction/" +
        teacherRUT +
        "/" +
        restrictionStatus
    );
  }

  // Delete a teacher from the database
  deleteTeacher(teacherRUT) {
    return axios.delete(TEACHER_API_URL + "/delete/" + teacherRUT);
  }

  // Save a teacher to the database
  saveTeacher(teacher) {
    return axios.post(TEACHER_API_URL, teacher);
  }
}

// eslint-disable-next-line import/no-anonymous-default-export
export default new TeacherService();
