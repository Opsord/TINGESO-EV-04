import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import swal from "sweetalert";
// import { useNavigate } from "react-router-dom";
import TeacherService from "../services/TeacherService";
import ManagementService from "../services/ManagementService";
import HeaderComponent from "./HeaderComponent";
import "../css/HomeComponent.css";

export default function TeacherListFunComponent() {
  const initialState = {
    teacherRUT: "",
    teacherList: [],
  };

  // const navigate = useNavigate();

  const [input, setInput] = useState(initialState);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [entitiesPerPage] = useState(20); // Adjust the number of entities per page as needed
  const [maxPagesToShow] = useState(5); // Adjust the number of pages to show in the pagination index

  useEffect(() => {
    TeacherService.getAllTeachers()
      .then((res) => {
        setInput((prevInput) => ({ ...prevInput, teacherList: res.data }));
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // Handle search input
  const handleSearch = (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    setCurrentPage(1); // Reset to the first page when searching

    if (query.trim() === "") {
      // If the search query is empty, reset the table to its original state
      TeacherService.getAllTeachers()
        .then((res) => {
          setInput((prevInput) => ({ ...prevInput, teacherList: res.data }));
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      // If there's a search query, filter the table based on the query
      const filteredTeachers = input.teacherList.filter((teacher) => {
        const lowerCaseRUT = teacher.teacherRUT.toLowerCase();
        const lowerCaseName = teacher.teacherName.toLowerCase();
        const lowerCaseLastName = teacher.teacherLastName.toLowerCase();

        return (
          lowerCaseRUT.includes(query.toLowerCase()) ||
          lowerCaseName.includes(query.toLowerCase()) ||
          lowerCaseLastName.includes(query.toLowerCase())
        );
      });

      setInput((prevInput) => ({
        ...prevInput,
        teacherList: filteredTeachers,
      }));
    }
  };

  const handleUpdateRestriction = (teacherRUT) => {
    swal({
      title: "¿Está seguro de que desea actualizar la restricción del profesor?",
      icon: "warning",
      buttons: ["Cancelar", "Actualizar restricción"],
      dangerMode: true,
    }).then((confirmed) => {
      if (confirmed) {
        ManagementService.updateTeacherRestrictionStatus(teacherRUT)
          .then(() => {
            swal("Restricción del profesor actualizada", {
              icon: "success",
              timer: "3000",
            });

            TeacherService.getAllTeachers()
              .then((res) => {
                setInput((prevInput) => ({ ...prevInput, teacherList: res.data }));
              })
              .catch((error) => {
                console.log(error);
              });
          })
          .catch((error) => {
            console.log(error);
            swal("Error al actualizar la restricción del profesor", {
              icon: "error",
            });
          });
      }
    });
  };

  // Calculate indexes for pagination
  const indexOfLastEntity = currentPage * entitiesPerPage;
  const indexOfFirstEntity = indexOfLastEntity - entitiesPerPage;
  const currentEntity = input.teacherList.slice(
    indexOfFirstEntity,
    indexOfLastEntity
  );

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  const renderPageNumbers = () => {
    const totalPages = Math.ceil(input.teacherList.length / entitiesPerPage);
    const halfMaxPagesToShow = Math.floor(maxPagesToShow / 2);

    if (totalPages <= maxPagesToShow) {
      // Show all pages if total pages are less than or equal to maxPagesToShow
      return Array.from({ length: totalPages }, (_, index) => (
        <li
          key={index}
          className={`page-item ${currentPage === index + 1 ? "active" : ""}`}>
          <button onClick={() => paginate(index + 1)} className="page-link">
            {index + 1}
          </button>
        </li>
      ));
    }

    const pages = [];
    let startPage, endPage;

    if (currentPage <= halfMaxPagesToShow) {
      startPage = 1;
      endPage = maxPagesToShow;
    } else if (currentPage + halfMaxPagesToShow >= totalPages) {
      startPage = totalPages - maxPagesToShow + 1;
      endPage = totalPages;
    } else {
      startPage = currentPage - halfMaxPagesToShow;
      endPage = currentPage + halfMaxPagesToShow;
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(
        <li
          key={i}
          className={`page-item ${currentPage === i ? "active" : ""}`}>
          <button onClick={() => paginate(i)} className="page-link">
            {i}
          </button>
        </li>
      );
    }

    if (startPage > 1) {
      pages.unshift(
        <li key="startEllipsis" className="page-item disabled">
          <span className="page-link">...</span>
        </li>
      );
    }

    if (endPage < totalPages) {
      pages.push(
        <li key="endEllipsis" className="page-item disabled">
          <span className="page-link">...</span>
        </li>
      );
    }

    return pages;
  };

  return (
    <div className="MainDiv">
      <div className="Header">
        <HeaderComponent />
      </div>

      <div className="PageBody">
        <div className="TableContainer">
          <h1>Listado de profesores</h1>
          <input
            className="search-bar"
            type="text"
            placeholder="Buscar..."
            value={searchQuery}
            onChange={handleSearch}
          />
          <Table className="content-table" data-search="true">
            <thead>
              <tr>
                <th>RUT</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Estado de restriccion</th>
                <th>Termino de restriccion</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {currentEntity.length > 0 ? (
                currentEntity.map((teacher) => (
                  <tr key={teacher.teacherRUT}>
                    <td> {teacher.teacherRUT}</td>
                    <td> {teacher.teacherName} </td>
                    <td> {teacher.teacherLastName} </td>
                    <td> {teacher.teacherLoanRestriction} </td>
                    <td> {teacher.teacherLoanRestrictionDate} </td>
                    <td>
                    <button
                        className="input-plan-boton"
                        onClick={() => handleUpdateRestriction(teacher.teacherRUT)}>
                        Actualizar restricción
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6">Sin profesores</td>
                </tr>
              )}
            </tbody>
          </Table>
          {/* Pagination controls */}
          <nav>
            <ul className="pagination">{renderPageNumbers()}</ul>
          </nav>
        </div>

      </div>
    </div>
  );
}