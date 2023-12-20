import axios from "axios";

const MANAGEMENT_API_URL = "http://localhost:8085/management";

class ManagementService {
  // Update a teacher restriction status
  updateTeacherRestrictionStatus(teacherRut) {
    return axios.put(MANAGEMENT_API_URL + "/update-restriction/" + teacherRut);
  }
}

// eslint-disable-next-line import/no-anonymous-default-export
export default new ManagementService();
