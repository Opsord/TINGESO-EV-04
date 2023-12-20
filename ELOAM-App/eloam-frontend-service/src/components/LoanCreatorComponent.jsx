import React, { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import swal from "sweetalert";
// Service imports
import LoanService from "../services/LoanService";
import TeacherService from "../services/TeacherService";
import EquipmentService from "../services/EquipmentService";
// Component imports
import HeaderComponent from "./HeaderComponent";
import Footer from "./FooterComponent";
// CSS imports
import "../css/HomeComponent.css";

export default function CreateLoan() {
  // Import teacher and equipment list from database

  useEffect(() => {
    TeacherService.getTeachersByRestrictionStatus(0)
      .then((res) => {
        setInput((prevInput) => ({ ...prevInput, teacherList: res.data }));
      })
      .catch((error) => {
        console.log(error);
      });

    EquipmentService.getEquipmentByAvailability(true)
      .then((res) => {
        setInput((prevInput) => ({ ...prevInput, equipmentList: res.data }));
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    // Set current date as default loan date
    const currentDate = new Date().toISOString();
    setInput((prevInput) => ({ ...prevInput, loanDate: currentDate }));
  }, []);

  const initialState = {
    responsibleTeacherRUT: "",
    equipmentID: "",
    loanMotivation: "",
    loanDate: "",
    loanReturnDate: "",
    loanDuration: "",
    loanObservation: "",
    teacherList: [],
    equipmentList: [],
  };

  const [input, setInput] = useState(initialState);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setInput({ ...input, [name]: value });
  };

  const saveLoan = async (e) => {
    e.preventDefault();

    // Validar que los campos requeridos estén llenos antes de enviar
    if (
      !input.responsibleTeacherRUT ||
      !input.equipmentID ||
      !input.loanMotivation
    ) {
      swal({
        title: "Campos requeridos",
        text: "Por favor complete los campos obligatorios.",
        icon: "warning",
        buttons: {
          confirm: {
            text: "OK",
            value: true,
            visible: true,
            className: "btn btn-warning",
          },
        },
      });
      return;
    }

    const confirmation = await swal({
      title: "¿Está seguro de que desea enviar este préstamo?",
      text: "Una vez enviado, no podrá ser modificado.",
      icon: "warning",
      buttons: ["Cancelar", "Enviar"],
      dangerMode: true,
    });

    if (confirmation) {
      try {
        swal({
          title: "Guardando préstamo...",
          icon: "info",
          buttons: {
            cancel: "Cancelar",
          },
          closeOnClickOutside: false,
          closeOnEsc: false,
        });

        const loan = {
          responsibleTeacherRUT: input.responsibleTeacherRUT,
          equipmentID: input.equipmentID,
          loanMotivation: input.loanMotivation,
          loanDate: input.loanDate,
          loanReturnDate: input.loanReturnDate,
          loanDuration: input.loanDuration,
          loanObservation: input.loanObservation,
        };

        await LoanService.createLoan(loan);
        // Marcar equipo como no disponible
        await EquipmentService.changeEquipmentAvailability(loan.equipmentID);

        // Si la llamada fue exitosa, mostrar un mensaje de éxito
        swal({
          title: "Préstamo guardado correctamente",
          icon: "success",
          timer: 3000,
        });
      } catch (error) {
        // Manejar errores aquí
        console.error("Error al guardar el préstamo:", error);
        swal({
          title: "Error al guardar préstamo",
          text: "Hubo un problema al intentar guardar el préstamo.",
          icon: "error",
        });
      }
    } else {
      swal({ text: "El préstamo no pudo ser registrado", icon: "error" });
    }
  };

  return (
    <div className="MainDiv">
      <div className="Header">
        <HeaderComponent />
      </div>

      <div className="PageBody">
        <div className="LoanCreator">
          <h1>Crear Prestamo</h1>

          <Form>
            <Form.Group controlId="formBasicRUT">
              <Form.Label>RUT Profesor responsable</Form.Label>
              <Form.Control
                as="select"
                value={input.responsibleTeacherRUT}
                onChange={handleInputChange}
                name="responsibleTeacherRUT">
                <option value="" disabled>
                  Selecciona un RUT
                </option>
                {input.teacherList &&
                  input.teacherList.map((teacher) => (
                    <option key={teacher.teacherRUT} value={teacher.teacherRUT}>
                      {teacher.teacherRUT}
                    </option>
                  ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="formBasicEquipmentID">
              <Form.Label>ID de equipo</Form.Label>
              <Form.Control
                as="select"
                value={input.equipmentID}
                onChange={handleInputChange}
                name="equipmentID">
                <option value="" disabled>
                  Selecciona un ID de equipo
                </option>
                {input.equipmentList &&
                  input.equipmentList.map((equipment) => (
                    <option
                      key={equipment.equipmentID}
                      value={equipment.equipmentID}>
                      {`${equipment.equipmentID} - ${equipment.equipmentBrand}`}
                    </option>
                  ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="formBasicMotivation">
              <Form.Label>Motivo</Form.Label>
              <Form.Control
                as="select"
                value={input.loanMotivation}
                onChange={handleInputChange}
                name="loanMotivation">
                <option value="Dictado de Clases">Dictado de Clases</option>
                <option value="Reuniones varias">Reuniones varias</option>
                <option value="Examen de título">Examen de título</option>
              </Form.Control>
            </Form.Group>

            <Button variant="primary" onClick={saveLoan}>
              Crear
            </Button>
          </Form>
        </div>
      </div>

      <div className="Footer">
        <Footer />
      </div>
    </div>
  );
}
