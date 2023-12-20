import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
// import { useNavigate } from "react-router-dom";
import EquipmentService from "../services/EquipmentService";
import HeaderComponent from "./HeaderComponent";
import "../css/HomeComponent.css";
import "../css/Table.css";

export default function TeacherListFunComponent() {
  const initialState = {
    equipmentID: "",
    equipmentList: [],
  };

  // const navigate = useNavigate();

  const [input, setInput] = useState(initialState);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [entitiesPerPage] = useState(20); // Adjust the number of entities per page as needed
  const [maxPagesToShow] = useState(5); // Adjust the number of pages to show in the pagination index

  useEffect(() => {
    EquipmentService.getAllEquipment()
      .then((res) => {
        setInput((prevInput) => ({ ...prevInput, equipmentList: res.data }));
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
      EquipmentService.getAllEquipment()
        .then((res) => {
          setInput((prevInput) => ({ ...prevInput, equipmentList: res.data }));
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      // If there's a search query, filter the table based on the query
      const filteredEquipment = input.equipmentList.filter((equipment) => {
        // Search by ID and Brand
        const lowerCaseID = equipment.equipmentID.toLowerCase();
        const lowerCaseBrand = equipment.equipmentBrand.toLowerCase();

        return (
          lowerCaseID.includes(query.toLowerCase()) ||
          lowerCaseBrand.includes(query.toLowerCase())
        );
      });

      setInput((prevInput) => ({
        ...prevInput,
        equipmentList: filteredEquipment,
      }));
    }
  };

  // Calculate indexes for pagination
  const indexOfLastEntity = currentPage * entitiesPerPage;
  const indexOfFirstEntity = indexOfLastEntity - entitiesPerPage;
  const currententity = input.equipmentList.slice(
    indexOfFirstEntity,
    indexOfLastEntity
  );

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  const renderPageNumbers = () => {
    const totalPages = Math.ceil(input.equipmentList.length / entitiesPerPage);
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
          <h1>Listado de equipos</h1>
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
                <th>ID</th>
                <th>Marca</th>
                <th>Disponibilidad</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {currententity.length > 0 ? (
                currententity.map((equipment) => (
                  <tr key={equipment.equipmentID}>
                    <td> {equipment.equipmentID}</td>
                    <td> {equipment.equipmentBrand} </td>
                    <td>
                      {equipment.equipmentAvailability ? (
                        <span role="img" aria-label="Check Correcto">
                          ✔
                        </span>
                      ) : (
                        <span role="img" aria-label="Check Incorrecto">
                          ❌
                        </span>
                      )}
                    </td>
                    <td>
                      <button className="action-button">Actualizar</button>
                      <button className="action-button">Cambiar disponibilidad</button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6">Sin equipos</td>
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
