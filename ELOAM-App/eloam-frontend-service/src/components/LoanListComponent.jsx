import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
// import { useNavigate } from "react-router-dom";
import LoanService from "../services/LoanService";
import HeaderComponent from "./HeaderComponent";
import "../css/HomeComponent.css";

export default function TeacherListFunComponent() {
  const initialState = {
    loanID: "",
    loanList: [],
  };

  // const navigate = useNavigate();

  const [input, setInput] = useState(initialState);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [entitiesPerPage] = useState(20); // Adjust the number of entities per page as needed
  const [maxPagesToShow] = useState(5); // Adjust the number of pages to show in the pagination index

  useEffect(() => {
    LoanService.getAllLoans()
      .then((res) => {
        setInput((prevInput) => ({ ...prevInput, loanList: res.data }));
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
      LoanService.getAllLoans
        .then((res) => {
          setInput((prevInput) => ({ ...prevInput, loanList: res.data }));
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      // If there's a search query, filter the table based on the query
      const filteredLoans = input.loanList.filter((loan) => {
        // Serach by Teacher RUT, Name and Last Name and equipment brand
        const lowerCaseRUT = loan.teacherRUT.toLowerCase();
        const lowerCaseName = loan.teacherName.toLowerCase();
        const lowerCaseLastName = loan.teacherLastName.toLowerCase();
        const lowerCaseBrand = loan.equipmentBrand.toLowerCase();

        return (
          lowerCaseRUT.includes(query.toLowerCase()) ||
          lowerCaseName.includes(query.toLowerCase()) ||
          lowerCaseLastName.includes(query.toLowerCase()) ||
          lowerCaseBrand.includes(query.toLowerCase())
        );
      });

      setInput((prevInput) => ({
        ...prevInput,
        loanList: filteredLoans,
      }));
    }
  };

  // Calculate indexes for pagination
  const indexOfLastEntity = currentPage * entitiesPerPage;
  const indexOfFirstEntity = indexOfLastEntity - entitiesPerPage;
  const entitiesTeachers = input.loanList.slice(
    indexOfFirstEntity,
    indexOfLastEntity
  );

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  const renderPageNumbers = () => {
    const totalPages = Math.ceil(input.loanList.length / entitiesPerPage);
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
          <h1>Listado de prestamos</h1>
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
                <th>Profesor responsable</th>
                <th>ID de equipo</th>
                <th>Motivo</th>
                <th>Fecha de prestamo</th>
                <th>Fecha de devolucion</th>
                <th>Duracion de prestamo</th>
                <th>Observacion</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {entitiesTeachers.length > 0 ? (
                entitiesTeachers.map((loan) => (
                  <tr key={loan.loanID}>
                    <td> {loan.responsibleTeacherRUT}</td>
                    <td> {loan.equipmentID} </td>
                    <td> {loan.loanMotivation} </td>
                    <td> {loan.loanDate} </td>
                    <td> {loan.loanReturnDate} </td>
                    <td> {loan.loanDuration} </td>
                    <td> {loan.loanObservation} </td>
                    <td>
                      <button
                        className="input-plan-boton">
                        Detalles
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="8">Sin prestamos</td>
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