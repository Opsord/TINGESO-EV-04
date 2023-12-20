import axios from "axios";

const EQUIPMENT_API_URL = "http://localhost:8083/equipment";

class EquipmentService {
  // Get a list of all equipment
  getAllEquipment() {
    return axios.get(EQUIPMENT_API_URL);
  }

  // Get equipment by its code
  getEquipmentByCode(equipmentCode) {
    return axios.get(EQUIPMENT_API_URL + "/" + equipmentCode);
  }

  // Find a list of equipment by their availability status
  getEquipmentByAvailability(availabilityStatus) {
    return axios.get(EQUIPMENT_API_URL + "/availability/" + availabilityStatus);
  }

  // Find a list of equipment by their brand
  getEquipmentByBrand(equipmentBrand) {
    return axios.get(EQUIPMENT_API_URL + "/brand/" + equipmentBrand);
  }

  // Change the availability status of equipment
  changeEquipmentAvailability(equipmentCode) {
    return axios.get(
      EQUIPMENT_API_URL + "/change-availability/" + equipmentCode
    );
  }

  // Delete equipment from the database
  deleteEquipment(equipmentCode) {
    return axios.delete(EQUIPMENT_API_URL + "/delete/" + equipmentCode);
  }

  // Save equipment to the database
  saveEquipment(equipment) {
    return axios.post(EQUIPMENT_API_URL, equipment);
  }
}

// eslint-disable-next-line import/no-anonymous-default-export
export default new EquipmentService();
