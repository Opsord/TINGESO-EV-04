import React from "react";

import "../css/Footer.css";

const Footer = () => {
  return (
    <footer className="bg-warning text-center text-white">
      <div className="container p-4">
        <section className="mb-4">
          <p>
            Aplicación web que permite gestionar el proceso de inscripción de
            asignaturas de los alumnos de las diferentes carreras (tanto de
            Civil como de Ejecución) de la Facultad de Ingeniería de la
            Universidad de Santiago.
          </p>
        </section>
      </div>

      <div
        className="text-center p-3"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.2)" }}>
        © 2023 Copyright: Andres Zelaya
      </div>
    </footer>
  );
};

export default Footer;